package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.extractors.ExcelExtractEngine;
import dataoutdoor.loaders.ExcelLoadEngine;
import dataoutdoor.loaders.JsonLoadEngine;

public class TestJsonDataLoader {

	


	@Test
	public synchronized void should_add_datasets_to_a_category() {
		
		boolean exceptionThrown = false;
		DataLoadEngine loadEngine = new JsonLoadEngine();
	
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets = new LinkedHashMap<Integer, LinkedHashMap<String, Object>>();
		
		LinkedHashMap<String, Object> dataset1 = new LinkedHashMap<String, Object>();
		dataset1.put("COL 1","STRING 1");
		dataset1.put("COL 2", new Double(22.0));
		dataset1.put("COL 3", new Integer(33));
		dataset1.put("COL 4", true);
		
		LinkedHashMap<String, Object> dataset2 = new LinkedHashMap<String, Object>();
		dataset2.put("COL 1","STRING 11");
		dataset2.put("COL 2", new Double(222.0));
		dataset2.put("COL 3", new Integer(333));
		dataset2.put("COL 4", false);
		
		datasets.put(0, dataset1);
		datasets.put(1, dataset2);
		
		try {
			loadEngine.setDataDestination("src/test/resources/datadest.json");
			loadEngine.setDataCategory("Category");
			loadEngine.addDatasets(datasets);
			loadEngine.save();
			
		} catch (Exception e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		

		String json = "{not a valid json!!!<>";
		try {
			//FileUtils.readFileToString(new File("src/test/resources/datadest.json"));
			json = FileUtils.readFileToString(new File("src/test/resources/datadest.json"), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		
		assertTrue(Utils.isValidJson(json));
		
		
	}
}
