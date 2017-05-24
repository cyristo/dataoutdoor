package dataoutdoor.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
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
        	    {"src/test/resources/datasource.xls"}, {"src/test/resources/datasource.xlsx"}});
    }
	
	@Test
	public void should_get_correct_dataset_for_category_and_id_with_xls_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		Object id = "France";
		//String fileName = "src/test/resources/datasource.xls";
				
		DataEngine engine = new ExcelEngine();
		HashMap<String, Object> dataset1 = null;
		HashMap<String, Object> dataset2 = null;
		
		try {
			engine.setDataSource(fileName);
			dataset1 = engine.getDatasetById(category, id);
			dataset2 = engine.getDatasetById(id);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double result = (Double) dataset1.get("COUNTRY CODE");
		assertEquals(33, result.intValue());
		result = (Double) dataset2.get("COUNTRY CODE");
		assertEquals(33, result.intValue());
	}
	
	@Test
	public void should_get_correct_dataset_for_category_and_row_num_with_xlsx_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		int rowNum = 73;
		//String fileName = "src/test/resources/datasource.xlsx";
		
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
		assertEquals(33, result.intValue());
	}
	
	@Test
	public void should_get_cell_data_by_reference() {
		
		boolean exceptionThrown = false;
		String sheetName = "COUNTRY";
		String cellReference = "B74";
		//String fileName = "src/test/resources/datasource.xlsx";
				
		ExcelEngine engine = new ExcelEngine();
		Object cellVal = null;
		
		try {
			engine.setDataSource(fileName);
			cellVal = engine.getCellValueByExcelReference(sheetName, cellReference);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double db = new Double((Double) cellVal);
		assertEquals(33, db.intValue());
	}
	
}
