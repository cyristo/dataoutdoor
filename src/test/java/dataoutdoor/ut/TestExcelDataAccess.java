package dataoutdoor.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataEngine;
import dataoutdoor.engine.ExcelEngine;

public class TestExcelDataAccess {

	@Test
	public void should_get_correct_dataset_for_category_and_id_with_xls_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		Object id = "France";
		String fileName = "src/test/resources/datasource.xls";
				
		DataEngine engine = new ExcelEngine();
		HashMap<String, Object> dataset = null;
		
		try {
			engine.setDataSource(fileName);
			dataset = engine.getDatasetById(category, id);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double result = (Double) dataset.get("COUNTRY CODE");
		assertEquals(33, result.intValue());
	}
	
	@Test
	public void should_get_correct_dataset_for_category_and_id_with_xlsx_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		Object id = "France";
		String fileName = "src/test/resources/datasource.xlsx";
				
		DataEngine engine = new ExcelEngine();
		HashMap<String, Object> dataset = null;
		
		try {
			engine.setDataSource(fileName);
			dataset = engine.getDatasetById(category, id);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		
		Double result = (Double) dataset.get("COUNTRY CODE");
		assertEquals(33, result.intValue());
	}
	
	@Test
	public void should_get_correct_dataset_for_category_and_row_num_with_xls_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		int rowNum = 73;
		String fileName = "src/test/resources/datasource.xls";
				
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
	public void should_get_correct_dataset_for_category_and_row_num_with_xlsx_file() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		int rowNum = 73;
		String fileName = "src/test/resources/datasource.xlsx";
				
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
}
