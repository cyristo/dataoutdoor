package dataoutdoor.ut;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataEngine;
import dataoutdoor.engine.ExcelEngine;

public class TestDataConvertion {

	@Test
	public void test() {
		
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
		String json = Utils.hashMaptoJson(dataset);
		
		System.out.println(json);
		
		assertTrue(Utils.isValidJson(json));
		
	}
	


}
