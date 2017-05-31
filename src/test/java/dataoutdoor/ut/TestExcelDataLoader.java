package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.extractors.ExcelExtractEngine;
import dataoutdoor.loaders.ExcelLoadEngine;


public class TestExcelDataLoader {

	@Test
	public void should_save_a_new_excel_file() {
		
		Date date = new Date();
		boolean exceptionThrown = false;
		DataLoadEngine engine = new ExcelLoadEngine();
		
		try {
			engine.setDataDestination("src/test/resources/datadest.xls");
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		
		LinkedHashMap<String, Object> dataset = new LinkedHashMap<String, Object>();
		dataset.put("COL1", "simple value");
		try {
			engine.addDataset(dataset);
			engine.save();
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);

		assertTrue(FileUtils.isFileNewer(new File("src/test/resources/datadest.xls"), date));
		
	}
	
	@Test
	public synchronized void should_add_dataset_to_a_category() {
		
		boolean exceptionThrown = false;
		DataLoadEngine loadEngine = new ExcelLoadEngine();
		DataExtractEngine extractEngine = new ExcelExtractEngine();
	
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
		
		LinkedHashMap<String, Object> resultDataset1 = null;
		LinkedHashMap<String, Object> resultDataset2 = null;
		
		try {
			loadEngine.setDataDestination("src/test/resources/datadestTest1.xls");
			loadEngine.setDataCategory("Category");
			loadEngine.addDataset(dataset1);
			loadEngine.addDataset(dataset2);
			loadEngine.save();
			extractEngine.setDataSource("src/test/resources/datadestTest1.xls");
			resultDataset1 = extractEngine.getDatasetByRowNum(1);
			resultDataset2 = extractEngine.getDatasetByRowNum(2);
		} catch (Exception e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		
		assertThat(resultDataset1.get("COL 1").toString(), is("STRING 1"));
		assertThat(new Double(resultDataset1.get("COL 2").toString()), is(22.0));
		Double db = new Double(resultDataset1.get("COL 3").toString()); 
		assertThat(new Integer(db.intValue()), is(33));
		assertThat(new Boolean(resultDataset1.get("COL 4").toString()), is(true));

		assertThat(resultDataset2.get("COL 1").toString(), is("STRING 11"));
		assertThat(new Double(resultDataset2.get("COL 2").toString()), is(222.0));
		db = new Double(resultDataset2.get("COL 3").toString()); 
		assertThat(new Integer(db.intValue()), is(333));
		assertThat(new Boolean(resultDataset2.get("COL 4").toString()), is(false));
		
	}

	@Test
	public synchronized void should_add_datasets_to_a_category() {
		
		boolean exceptionThrown = false;
		DataLoadEngine loadEngine = new ExcelLoadEngine();
		DataExtractEngine extractEngine = new ExcelExtractEngine();
	
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
		
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> resultDatasets = null;
		LinkedHashMap<String, Object> resultDataset1 = null;
		LinkedHashMap<String, Object> resultDataset2 = null;
		
		try {
			loadEngine.setDataDestination("src/test/resources/datadestTest2.xls");
			loadEngine.setDataCategory("Category");
			loadEngine.addDatasets(datasets);
			loadEngine.save();
			extractEngine.setDataSource("src/test/resources/datadestTest2.xls");
			resultDatasets = extractEngine.getDatasets();
			resultDataset1 = resultDatasets.get(0);
			resultDataset2 = resultDatasets.get(1);
		} catch (Exception e) {
			e.printStackTrace();
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		
		assertThat(resultDataset1.get("COL 1").toString(), is("STRING 1"));
		assertThat(new Double(resultDataset1.get("COL 2").toString()), is(22.0));
		Double db = new Double(resultDataset1.get("COL 3").toString()); 
		assertThat(new Integer(db.intValue()), is(33));
		assertThat(new Boolean(resultDataset1.get("COL 4").toString()), is(true));

		assertThat(resultDataset2.get("COL 1").toString(), is("STRING 11"));
		assertThat(new Double(resultDataset2.get("COL 2").toString()), is(222.0));
		db = new Double(resultDataset2.get("COL 3").toString()); 
		assertThat(new Integer(db.intValue()), is(333));
		assertThat(new Boolean(resultDataset2.get("COL 4").toString()), is(false));
		
	}
}
