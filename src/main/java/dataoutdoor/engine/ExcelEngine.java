package dataoutdoor.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataEngine;

public class ExcelEngine implements DataEngine {

	private Workbook dataSource;

	/**
	 * Set the Excel Workbook as the datasource for the instance
	 * @param dataSource
	 * @throws IOException 
	 */
	public void setDataSource(Object dataSource) throws DataOutdoorException {
		//get the workBook 
		try {
			this.dataSource = readFile(dataSource.toString());
		} catch (Exception e) {
			throw new DataOutdoorException(e);
		}
	}

	/**
	 * Get the dataset by its ID, for the first sheet
	 * @param id cell value of the first column
	 * @return the row
	 * @throws IOException
	 */
	public HashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException {
		return getDatasetById(null, id);
	}
	/**
	 * Get the dataset by its ID. 
	 * @param category name of the sheet
	 * @param id cell value of the first column
	 * @return the row
	 * @throws IOException
	 */
	public HashMap<String, Object> getDatasetById(String category, Object id) throws DataOutdoorException {

		//object returned
		HashMap<String, Object> dataset = new HashMap<String, Object>();
		if (dataSource == null) return dataset;

		//get the sheet 
		int index = 0;
		if (category != null) index = dataSource.getSheetIndex(category);
		Sheet sheet = dataSource.getSheetAt(index);

		//get the header row
		ArrayList<String> headers = getHeaderRow(sheet);

		//get the row by its id and feed the dataset
		int nbColl = headers.size();
		int rowStart = sheet.getFirstRowNum();
		int rowEnd = sheet.getLastRowNum();
		for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row != null) {
				if (row.getCell(0) != null && row.getCell(0).getStringCellValue().equals(id)) {
					for (int colNum = 0; colNum < nbColl; colNum++) {
						Cell cell = row.getCell(colNum);
						dataset.put(headers.get(colNum), getCellObject(cell));
						//feedDataset(dataset, headers.get(colNum), cell);
					}
				}
			}
		}

		return dataset;
	}

	/**
	 * Get the dataset by its row number, for the first sheet
	 * @param rowNum the number of the row - first row is header, so it is not considered as a row. 
	 * For example, to get the excel row number 10 then search for the 9th rownum
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, Object> getDatasetByRowNum(int rowNum) throws DataOutdoorException {
		return getDatasetByRowNum(null, rowNum);
	}
	/**
	 * Get the dataset by its row number
	 * @param category name of the sheet
	 * @param rowNum the number of the row - first row is header, so it is not considered as a row. 
	 * For example, to get the excel row number 10 then search for the 9th rownum
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, Object> getDatasetByRowNum(String category, int rowNum) throws DataOutdoorException {

		//object returned
		HashMap<String, Object> dataset = new HashMap<String, Object>();
		if (dataSource == null) return dataset;

		//get the sheet 
		int index = 0;
		if (category != null) index = dataSource.getSheetIndex(category);
		Sheet sheet = dataSource.getSheetAt(index);

		//get the header row
		ArrayList<String> headers = getHeaderRow(sheet);

		//get the row by its number and feed the dataset
		int nbColl = headers.size();
		Row row = sheet.getRow(rowNum);
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
	 */
	public Object getCellValueByExcelReference(String cellReference) {
		return getCellValueByExcelReference(null, cellReference);
	}
	/**
	 * Get the cell content by its Excel reference (A1, BC27, ..)
	 * @param sheetName name of the sheet
	 * @param cellReference the cell reference
	 * @return the value as an object
	 */
	public Object getCellValueByExcelReference(String sheetName, String cellReference) {

		CellReference ref = new CellReference(cellReference);

		//get the sheet 
		int index = 0;
		if (sheetName != null) index = dataSource.getSheetIndex(sheetName);
		Sheet sheet = dataSource.getSheetAt(index);

		//Get the row
		Row row = sheet.getRow(ref.getRow());
		
		//Get the cell
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(ref.getCol());
		}

		Object obj = getCellObject(cell);
		return obj;
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

	private ArrayList<String> getHeaderRow(Sheet sheet) {
		ArrayList<String> headers = new ArrayList<String>();
		Row headerRow = sheet.getRow(0);
		int colStart = headerRow.getFirstCellNum();
		int colEnd = headerRow.getLastCellNum();
		for (int colNum = colStart; colNum < colEnd; colNum++) {
			Cell c = headerRow.getCell(colNum);
			headers.add(c.getStringCellValue());
		}
		return headers;
	}

	private Workbook readFile(String filename) throws Exception {
		FileInputStream fis = new FileInputStream(filename);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(fis);
		} finally {
			fis.close();
		}
		return wb;
	}


}
