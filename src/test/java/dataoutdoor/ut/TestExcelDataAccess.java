package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataEngine;
import dataoutdoor.engine.ExcelEngine;

@RunWith(Parameterized.class)
public class TestExcelDataAccess {

	private String fileName;
	
	public TestExcelDataAccess(String fileName) {
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
				
		ExcelEngine engine = new ExcelEngine();
		HashMap<String, Object> dataset1 = null;
		HashMap<String, Object> dataset2 = null;
		HashMap<String, Object> dataset3 = null;
		
		try {
			engine.setDataSource(fileName);
			dataset1 = engine.getDatasetById(category, id);
			dataset2 = engine.getDatasetById(id);
			engine.setIdColumnIndex(2);
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
		
		DataEngine engine = new ExcelEngine();
		HashMap<String, Object> dataset = null;
				
		try {
			engine.setDataSource(fileName);
			dataset = engine.getDatasetByRowNum(category, rowNum);
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
				
		ExcelEngine engine = new ExcelEngine();
		Object cellVal1 = null;
		Object cellVal2 = null;
		Object cellVal3 = null;
		
		try {
			engine.setDataSource(fileName);
			cellVal1 = engine.getCellByReference(sheetName, cellReference1);
			cellVal2 = engine.getCellByReference(sheetName, cellReference2);
			cellVal3 = engine.getCellByReference(sheetName, cellReference3);
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
	public void should_get_dataset_list() {
		
		boolean exceptionThrown = false;
		String sheetName = "COUNTRY";
				
		ExcelEngine engine = new ExcelEngine();
		Collection<Object[]> allDatasets = null;
		Collection<Object[]> filteredDatasets = null;
		
		try {
			engine.setDataSource(fileName);
			allDatasets = engine.getDatasets(sheetName);
			engine.setIdFilter("Ca.*");
			filteredDatasets = engine.getDatasets(sheetName);
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
}
