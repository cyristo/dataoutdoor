package dataoutdoor.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataEngine;
import dataoutdoor.engine.ExcelEngine;

public class DataConvertion {

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

		//TODO see how to validate json format
		//assertEquals("", null, null);
	}
	


}
