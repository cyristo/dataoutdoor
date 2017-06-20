package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.extractors.MultipleExcelExtractEngine;

public class TestExtendedExcelCapabilities {

	@Test
	public void should_get_data_from_merged_files() {
		
		boolean exceptionThrown = false;
		String category = "CoUNTRY";
		Object id = "FraNce";
		
		String[] extenstions = new String[2];
		extenstions[0] = "xls";
		extenstions[1] = "xlsx";
		Collection<File> files = FileUtils.listFiles(new File("src/test/resources/multiple"), extenstions, false);
		
		MultipleExcelExtractEngine engine = new MultipleExcelExtractEngine();
		Collection<Object> cellList = new ArrayList<Object>();
		Collection<Object[]> datatab = new ArrayList<Object[]>();
		try {
			engine.setDataSource(files);
			cellList = (Collection<Object>) engine.getDataByReference("B2");
			datatab = engine.getDataTab();
		} catch (DataOutdoorException e) {
			exceptionThrown = true;
			e.printStackTrace();
		}
		
		assertFalse(exceptionThrown);
		
		assertThat(cellList.size(), is(2));
		assertThat(cellList.toArray()[0].toString(), is("93.0"));
		assertThat(cellList.toArray()[1].toString(), is("93.0"));
		
		assertThat(datatab.size(), is(240*2));
	}
	
	@Test
	public void should_set_the_row_to_be_considered_as_header() {
		//TODO implement setFirstRow
		
	}
	
	@Test
	public void should_get_a_selected_area() {
		//TODO implement
		//dataSheet.setAutoFilter(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)):
		
	}

}
