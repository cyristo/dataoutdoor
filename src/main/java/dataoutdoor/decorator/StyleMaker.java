package dataoutdoor.decorator;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class StyleMaker {

	private CellStyle style;
	private Font font;
	
	public StyleMaker(Workbook workbook) {
		this.style = workbook.createCellStyle();
		this.font = workbook.createFont();
	}
	
	public CellStyle getStyle() {
		return style;
	}
	
	public StyleMaker border() {
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return this;
	}
	
	public StyleMaker bold() {
		font.setBold(true);
		style.setFont(font);
		return this;
	}
	
	public StyleMaker italic() {
		font.setItalic(true);
		style.setFont(font);
		return this;
	}
	
	public StyleMaker fontColor(short color) {
		font.setColor(color);
		style.setFont(font);
		return this;
	}
	
	public StyleMaker fillColor(short color) {
		style.setFillForegroundColor(color);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return this;
	}
}
