package dataoutdoor.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataExtractorEngine;

public class ExcelExtractor implements DataExtractorEngine {

	private Workbook dataSource = null;
	private Sheet dataSheet;
	private ArrayList<String> headers = null;
	private Integer idColumnIndex = 0;
	private String idFilter = null;
	private int headerRowNum = 0;

	/**
	 * Set the Excel Workbook as the datasource for the instance
	 * @param dataSource
	 * @throws IOException 
	 */
	public void setDataSource(Object dataSource) throws DataOutdoorException {
		//get the workBook 
		try {
			if (dataSource instanceof File) {
				this.dataSource = WorkbookFactory.create((File) dataSource);
			} else {
				this.dataSource = WorkbookFactory.create(new File(dataSource.toString()));
			}
		} catch (Exception e) {
			throw new DataOutdoorException(e);
		}
	}
	/**
	 * Set the Excel sheet and the row header (will be the first sheet by default)
	 * @param dataSheet name
	 */
	public void setDataCategory(String dataCategory)  throws DataOutdoorException {
		if (dataSource == null) throw new DataOutdoorException("Data source is not set");
		//get the sheet 
		int index = 0;
		if (dataCategory != null) {
			index = dataSource.getSheetIndex(dataCategory);
		}
		if (index == -1) throw new DataOutdoorException("Unknown category");
		dataSheet = dataSource.getSheetAt(index);
		//set the header row
		setHeaders();
	}
	/**
	 * Set the index for the column to be considered as id for the rows
	 * @param idCol
	 */
	public void setIdCategory(String dataIdCategory) {
		if (dataIdCategory == null) {
			this.idColumnIndex = 0;
		} else {
			this.idColumnIndex = new Integer(dataIdCategory);
		}

	}

	/**
	 * Set the filter to be used on Id column when retrieving the dataset list
	 * @param idFilter regex
	 */
	public void setIdFilter(String idFilter) {
		this.idFilter = idFilter;
	}

	/**
	 * Set the row number to be considered as the header row
	 * @param firstRow
	 */
	public void setHeaderRow(int firstRow) {
		this.headerRowNum = firstRow;
		//reset the header row
		setHeaders();
	}

	/**
	 * Get the header row (first row)
	 * @return
	 */
	public Collection<String> getDataModel() {
		return headers;
	}

	/**
	 * Get the dataset by its ID
	 * @param cell value of the column set to be the id column (default is first column)
	 * @return the row
	 * @throws IOException
	 */
	public LinkedHashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		//object returned
		LinkedHashMap<String, Object> dataset = new LinkedHashMap<String, Object>();

		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		//get the row by its id and feed the dataset
		int nbColl = headers.size();
		int rowEnd = dataSheet.getLastRowNum();
		for (int rowNum = headerRowNum+1; rowNum <= rowEnd; rowNum++) {
			Row row = dataSheet.getRow(rowNum);
			if (row != null) {
				if (row.getCell(idColumnIndex) != null 
						&& row.getCell(idColumnIndex).getStringCellValue().toUpperCase().equals(id.toString().toUpperCase())) {
					for (int colNum = 0; colNum < nbColl; colNum++) {
						Cell cell = row.getCell(colNum);
						dataset.put(headers.get(colNum), getCellObject(cell));
					}
				}
			}
		}

		return dataset;
	}

	/**
	 * Get the dataset by its row number
	 * @param rowNum the number of the row - first row is 0. 
	 * For example, to get the excel row number 10 then search for the 9th rownum
	 * @return
	 * @throws IOException
	 */
	public LinkedHashMap<String, Object> getDatasetByRowNum(Integer rowNum) throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		//object returned
		LinkedHashMap<String, Object> dataset = new LinkedHashMap<String, Object>();

		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		//get the row by its number and feed the dataset
		int nbColl = headers.size();
		Row row = dataSheet.getRow(rowNum);
		if (row != null) {
			if (row.getCell(0) != null) {
				for (int colNum = 0; colNum < nbColl; colNum++) {
					Cell cell = row.getCell(colNum);
					dataset.put(headers.get(colNum), getCellObject(cell));
				}
			}
		}
		return dataset;
	}

	/**
	 * Get the cell content by its Excel reference (A1, BC27, ..), for the first sheet
	 * @param cellReference the cell reference
	 * @return the value as an object
	 * @throws DataOutdoorException 
	 */
	public Object getDataByReference(Object cellReference) throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		CellReference ref = new CellReference(cellReference.toString());

		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		//Get the row
		Row row = dataSheet.getRow(ref.getRow());

		//Get the cell
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(ref.getCol());
		}

		Object obj = getCellObject(cell);
		return obj;
	}
	/**
	 * Get all data for the sheet. If filters are set, then get all datasets that match the filters
	 * @return
	 * @throws DataOutdoorException
	 */
	public Collection<Object[]> getDataTab() throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		//object returned
		Collection<Object[]> datasets = new ArrayList<Object[]>();

		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		//get the row by its id and feed the dataset
		int rowEnd = dataSheet.getLastRowNum();
		int nbColl = headers.size();
		for (int rowNum = headerRowNum+1; rowNum <= rowEnd; rowNum++) {
			Row row = dataSheet.getRow(rowNum);
			if (row != null) {
				if (row.getCell(0) != null && matchFilter(row.getCell(idColumnIndex).getStringCellValue())) {
					ArrayList<Object> rowList = new ArrayList<Object>();	
					for (int colNum = 0; colNum < nbColl; colNum++) {
						Cell cell = row.getCell(colNum);
						rowList.add(getCellObject(cell));
					}
					datasets.add(rowList.toArray());
				}
			}
		}
		return datasets;
	}

	public Collection<Object[]> getRangedDataTab(int firstRow, int lastRow, int firstCol, int lastCol) throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		//object returned
		Collection<Object[]> datasets = new ArrayList<Object[]>();
		
		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		dataSheet.setAutoFilter(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));

		for (Row row : dataSheet) {
			ArrayList<Object> rowList = new ArrayList<Object>();	
			for (Cell cell : row) {
				rowList.add(getCellObject(cell));
			}
			datasets.add(rowList.toArray());
		}
		return datasets;
	}

	public LinkedHashMap<Integer, LinkedHashMap<String, Object>> getDatasets() throws DataOutdoorException {

		if (dataSource == null) throw new DataOutdoorException("Data source is not set");

		//object returned
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets = new LinkedHashMap<Integer, LinkedHashMap<String, Object>>() ;

		//set the sheet if not done
		if (dataSheet == null) setDataCategory(null);

		//get the row by its id and feed the dataset
		int nbColl = headers.size();
		int rowEnd = dataSheet.getLastRowNum();
		for (int rowNum = headerRowNum+1; rowNum <= rowEnd; rowNum++) {
			Row row = dataSheet.getRow(rowNum);
			if (row != null) {
				if (row.getCell(0) != null && matchFilter(row.getCell(idColumnIndex).getStringCellValue())) {
					LinkedHashMap<String, Object> rowList = new LinkedHashMap<String, Object>();	
					for (int colNum = 0; colNum < nbColl; colNum++) {
						Cell cell = row.getCell(colNum);
						rowList.put(headers.get(colNum), getCellObject(cell));
					}
					datasets.put(rowNum-1, rowList);
				}
			}
		}
		return datasets;
	}
	private boolean matchFilter(String value) {
		boolean matchFilter = true;
		if (idFilter != null) {
			matchFilter = value.matches(idFilter);
		}
		return matchFilter;
	}

	private Object getCellObject(Cell cell) {
		Object ret = null;
		if (cell == null) return ret;
		switch (cell.getCellTypeEnum()) {
		case STRING:
			ret = cell.getRichStringCellValue().getString();
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				ret = cell.getDateCellValue();
			} else {
				ret = cell.getNumericCellValue();
			}
			break;
		case BOOLEAN:
			ret = cell.getBooleanCellValue();
			break;
		case FORMULA:
			ret = cell.getStringCellValue();
			break;
		case BLANK:
			ret = cell.getStringCellValue();
			break;
		default:
			ret = cell.getStringCellValue();
		}
		return ret;
	}

	private void setHeaders() {
		headers = new ArrayList<String>();
		Row headerRow = dataSheet.getRow(headerRowNum);
		if (headerRow == null) return;
		int colStart = headerRow.getFirstCellNum();
		int colEnd = headerRow.getLastCellNum();
		for (int colNum = colStart; colNum < colEnd; colNum++) {
			Cell c = headerRow.getCell(colNum);
			if (c != null) headers.add(c.getStringCellValue());
		}
	}


}
