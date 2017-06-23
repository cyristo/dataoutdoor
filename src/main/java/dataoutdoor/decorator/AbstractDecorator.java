package dataoutdoor.decorator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import dataoutdoor.contract.DataSheetDecoratorEngine;

public abstract class AbstractDecorator implements DataSheetDecoratorEngine {

	public Sheet decorate(Sheet sheet) {
		int rowNum = -1;
		for (Row row : sheet) {
			rowNum++;
			int cellNum = -1;
			for (Cell cell : row) {
				cellNum++;
				cell.setCellStyle(getStyle(sheet, rowNum, cellNum));
			}
		}
		return sheet;
	}

	public abstract CellStyle getStyle(Sheet sheet, int rowNum, int cellNum);
	
}
