package dataoutdoor.decorator;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

public class TableDecorator extends AbstractDecorator {

	@Override
	public CellStyle getStyle(Sheet sheet, int rowNum, int cellNum) {
		StyleMaker styleMaker = new StyleMaker(sheet.getWorkbook());
		if (rowNum == 0) {
			styleMaker = new StyleMaker(sheet.getWorkbook());
			styleMaker.border().bold().fillColor(IndexedColors.GREY_25_PERCENT.getIndex());
		} else {
			styleMaker = new StyleMaker(sheet.getWorkbook());
			styleMaker.border();
		}
		return styleMaker.getStyle();
	}

}
