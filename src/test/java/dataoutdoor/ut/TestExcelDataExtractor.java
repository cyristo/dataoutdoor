package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.engine.ExcelExtractEngine;

@RunWith(Parameterized.class)
public class TestExcelDataExtractor {

	private String fileName;
	
	public TestExcelDataExtractor(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	@Parameters(name = "Test with {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
        	    {"src/test/resources/datasource.xls"}, 
        	    {"src/test/resources/datasource.xlsx"}});
    }
	
	@Test
	public void should_get_correct_dataset_for_category_and_id() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		Object id = "France";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		LinkedHashMap<String, Object> dataset1 = null;
		LinkedHashMap<String, Object> dataset2 = null;
		LinkedHashMap<String, Object> dataset3 = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(category);
			dataset1 = engine.getDatasetById(id);
			dataset2 = engine.getDatasetById(id);
			engine.setDataIdCategory("2");
			dataset3 = engine.getDatasetById("BS / BHS");
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double result = (Double) dataset1.get("COUNTRY CODE");
		assertThat(result.intValue(), is(33));
		
		result = (Double) dataset2.get("COUNTRY CODE");
		assertThat(result.intValue(), is(33));
		
		assertThat(dataset3.get("COUNTRY CODE").toString(), is("1-242"));
	}
	
	@Test
	public void should_get_correct_dataset_for_category_and_row_num() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		int rowNum = 73;
		
		DataExtractEngine engine = new ExcelExtractEngine();
		LinkedHashMap<String, Object> dataset = null;
				
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(category);
			dataset = engine.getDatasetByRowNum(rowNum);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double result = (Double) dataset.get("COUNTRY CODE");
		assertThat(result.intValue(), is(33));
	}
	
	@Test
	public void should_get_cell_data_by_reference() {
		
		boolean exceptionThrown = false;
		String sheetName = "COUNTRY";
		String cellReference1 = "B74";
		String cellReference2 = "A1";
		String cellReference3 = "F241";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		Object cellVal1 = null;
		Object cellVal2 = null;
		Object cellVal3 = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(sheetName);
			cellVal1 = engine.getDataByReference(cellReference1);
			cellVal2 = engine.getDataByReference(cellReference2);
			engine.setDataCategory(null);
			cellVal3 = engine.getDataByReference(cellReference3);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double db = (Double) cellVal1;
		assertThat(db.intValue(), is(33));
		assertThat(cellVal2.toString(), is("COUNTRY"));
		assertThat(cellVal3.toString(), is("10.48 Billion"));
	}
	
	@Test
	public void should_get_data_collection() {
		
		boolean exceptionThrown = false;
		String sheetName = "COUNTRY";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		Collection<Object[]> allDatasets = null;
		Collection<Object[]> filteredDatasets = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(sheetName);
			allDatasets = engine.getDataTab();
			engine.setDataIdFilter("Ca.*");
			filteredDatasets = engine.getDataTab();
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		assertThat(allDatasets.size(), is(240));
		Object[] tab = allDatasets.toArray();
		
		Object[] row0 = (Object[]) tab[0];
		Object[] row240 = (Object[]) tab[239];
		assertThat(row0[0].toString(), is("Afghanistan"));
		assertThat(row240[5].toString(), is("10.48 Billion"));
		
		assertThat(filteredDatasets.size(), is(5));
		
	}
	
	@Test
	public void should_get_datasets() {
		
		boolean exceptionThrown = false;
		String sheetName = "COUNTRY";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> allDatasets = null;
		LinkedHashMap<Integer, LinkedHashMap<String, Object>>filteredDatasets = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(sheetName);
			allDatasets = engine.getDatasets();
			engine.setDataIdFilter("Ca.*");
			filteredDatasets = engine.getDatasets();
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		assertThat(allDatasets.size(), is(240));
		
		LinkedHashMap<String, Object> row0 = allDatasets.get(0);
		LinkedHashMap<String, Object> row240 = allDatasets.get(239);
		assertThat(row0.get("COUNTRY").toString(), is("Afghanistan"));
		assertThat(row240.get("GDP $USD").toString(), is("10.48 Billion"));
		
		assertThat(filteredDatasets.size(), is(5));
		
	}
	
	@Test
	public void should_handle_bad_category() {
		
		boolean exceptionThrown = false;
		String category = "BAD";
		Object id = "France";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		LinkedHashMap<String, Object> dataset1 = null;
		LinkedHashMap<String, Object> dataset2 = null;
		LinkedHashMap<String, Object> dataset3 = null;
		
		String message = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(category);
			dataset1 = engine.getDatasetById(id);
			dataset2 = engine.getDatasetById(id);
			engine.setDataIdCategory("2");
			dataset3 = engine.getDatasetById("BS / BHS");
		} catch (DataOutdoorException e) {
			message = e.getMessage();
			exceptionThrown = true;
		}

		assertTrue(exceptionThrown);
		assertThat(message, is("Unknown category"));
		
		
	}
}
