package dataoutdoor.loader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.DataOutdoorUtils;
import dataoutdoor.contract.DataLoaderEngine;
import dataoutdoor.contract.DataSheetDecoratorEngine;
import dataoutdoor.contract.DataTransformerEngine;
import dataoutdoor.decorator.TableDecorator;

public class ExcelLoader implements DataLoaderEngine {

	private Workbook workbook = null;
	private String dataDestination = null;
	private Sheet dataSheet = null;
	private String sheetName = "Data Outdoor Sheet";
	private DataTransformerEngine transformer = null;
	private DataSheetDecoratorEngine decorator = null;
	private Collection<String> keys;
	private int nbColumn = 0;
	
	public void setDataDestination(Object dataDestination) throws DataOutdoorException {

		this.dataDestination = dataDestination.toString();

		if (StringUtils.endsWith(dataDestination.toString(), "xlsx")) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}

		dataSheet = workbook.createSheet(sheetName);
	}

	public void setDataTransformer(DataTransformerEngine transformer) throws DataOutdoorException {
		this.transformer = transformer;	
	}
	
	public void setDataDecorator(DataSheetDecoratorEngine decorator) throws DataOutdoorException {
		this.decorator = decorator;
	}
	
	public void setDataCategory(String dataCategory) {
		if (dataSheet != null) {
			workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
			sheetName = dataCategory;
			dataSheet = workbook.createSheet(sheetName);
		} else {
			sheetName = dataCategory;
		}
	}

	public void addDataset(LinkedHashMap<String, Object> dataset) {

		int lastRowNum = dataSheet.getLastRowNum();

		if (transformer != null) dataset = transformer.transform(dataset);
		keys = dataset.keySet();
		if (lastRowNum == 0) setHeaders(keys);

		Row row = dataSheet.createRow(lastRowNum+1);
		CellStyle style;
	    DataFormat format = workbook.createDataFormat();

		int i = 0;
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			Object value = dataset.get(iterator.next());
			if (value == null) {
				i++;
				continue;
			}
			if (value instanceof Double) {
				Cell cell = row.createCell(i++);
				cell.setCellValue((Double) value);
			    style = workbook.createCellStyle();
			    style.setDataFormat(format.getFormat(DataOutdoorUtils.getProperty("dataoutdoor.format.double")));
			    cell.setCellStyle(style);
			} else if (value instanceof Integer) {
				Cell cell = row.createCell(i++);
				cell.setCellValue((Integer) value);
			    style = workbook.createCellStyle();
			    style.setDataFormat(format.getFormat(DataOutdoorUtils.getProperty("dataoutdoor.format.integer")));
			    cell.setCellStyle(style);
			} else if (value instanceof Date) {
				row.createCell(i++).setCellValue((Date) value);
			} else if (value instanceof Calendar) {
				row.createCell(i++).setCellValue((Calendar) value);
			} else if (value instanceof Boolean) {
				row.createCell(i++).setCellValue((Boolean) value);
			} else {
				row.createCell(i++).setCellValue(value.toString());
			}
		}

	}

	public void addDatasets(LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets) {

		Collection<Integer> rows = datasets.keySet();
		for (Iterator<Integer> iterator = rows.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			addDataset(datasets.get(key));
		}
		
	}
	
	private void setHeaders(Collection<String> headers) {
		Row row = dataSheet.createRow(0);
		int i = 0;
		for (Iterator<String> iterator = headers.iterator(); iterator.hasNext();) {
			row.createCell(i++).setCellValue((String) iterator.next());
		}
		nbColumn = headers.size();
	}
	


	public void save() throws DataOutdoorException {
		adjust();
		filter();
		freeze();
		decorate();
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(dataDestination.toString());
		} catch (FileNotFoundException e1) {
			throw new DataOutdoorException(e1);
		}
		try {
			workbook.write(fileOut);
		} catch (IOException e2) {
			throw new DataOutdoorException(e2);
		} finally {
			try {
				fileOut.close();
			} catch (IOException e3) {
				throw new DataOutdoorException(e3);
			}
		}
	}

	private void adjust() {
		int i = 0;
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			iterator.next();
			dataSheet.autoSizeColumn(i++);
		}
	}
	
	private void decorate() {
		if (decorator == null) {
			decorator = new TableDecorator();
		}
		dataSheet = decorator.decorate(dataSheet);
	}
	
	private void filter() {
		if (nbColumn <= 26) {
			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String range = "A1:"+alphabet.charAt(nbColumn-1)+nbColumn;	
			dataSheet.setAutoFilter(CellRangeAddress.valueOf(range));
		}
	}
	
	private void freeze() {
		dataSheet.createFreezePane(0, 1, 0, 1 );
	}
}
