package dataoutdoor.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataEngine;

public class ExcelEngine implements DataEngine {

	private HSSFWorkbook dataSource;

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
		Sheet sheet = dataSource.getSheetAt(dataSource.getSheetIndex(category));

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
						feedDataset(dataset, headers.get(colNum), cell);
					}
				}
			}
		}

		return dataset;
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
		Sheet sheet = dataSource.getSheetAt(dataSource.getSheetIndex(category));

		//get the header row
		ArrayList<String> headers = getHeaderRow(sheet);
		
		//get the row by its number and feed the dataset
		int nbColl = headers.size();
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			if (row.getCell(0) != null) {
				for (int colNum = 0; colNum < nbColl; colNum++) {
					Cell cell = row.getCell(colNum);
					feedDataset(dataset, headers.get(colNum), cell);
				}
			}
		}
		return dataset;
	}
	
	/**
	 * Set the path to Excel file
	 * @param dataSource
	 * @throws IOException 
	 */
	public void setDataSource(Object dataSource) throws DataOutdoorException {
		//get the workBook 
		try {
			this.dataSource = readFile(dataSource.toString());
		} catch (IOException e) {
			throw new DataOutdoorException(e);
		}
	}
	
	private void feedDataset(HashMap<String, Object> dataset, String headerName, Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case STRING:
			dataset.put(headerName, cell.getRichStringCellValue().getString());
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				dataset.put(headerName, cell.getDateCellValue());
			} else {
				dataset.put(headerName, cell.getNumericCellValue());
			}
			break;
		case BOOLEAN:
			dataset.put(headerName, cell.getBooleanCellValue());
			break;
		case FORMULA:
			dataset.put(headerName, cell.getStringCellValue());
			break;
		case BLANK:
			dataset.put(headerName, cell.getStringCellValue());
			break;
		default:
			dataset.put(headerName, cell.getStringCellValue());
		}
		
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

	private HSSFWorkbook readFile(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		try {
			return new HSSFWorkbook(fis);		
		} finally {
			fis.close();
		}
	}

}
