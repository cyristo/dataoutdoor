package dataoutdoor.ut;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.engine.ExcelExtractEngine;

@RunWith(Parameterized.class)
public class TestDataConvertion {

	private String fileName;
	
	public TestDataConvertion(String fileName) {
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
	public void test() {
		
		boolean exceptionThrown = false;
		String category = "COUNTRY";
		Object id = "France";
				
		DataExtractEngine engine = new ExcelExtractEngine();
		LinkedHashMap<String, Object> dataset = null;
		
		try {
			engine.setDataSource(fileName);
			engine.setDataCategory(category);
			dataset = engine.getDatasetById(id);
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);
		String json = Utils.hashMaptoJson(dataset);
		
		assertTrue(Utils.isValidJson(json));
		
	}
	


}
