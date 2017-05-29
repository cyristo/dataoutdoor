package dataoutdoor.ut;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.engine.ExcelLoadEngine;

@RunWith(Parameterized.class)
public class TestExcelDataLoader {

	private String fileName;
	
	public TestExcelDataLoader(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	@Parameters(name = "Test with {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
        	    {"src/test/resources/datadest.xls"}, 
        	    {"src/test/resources/datadest.xlsx"}});
    }
	
	@Test
	public void should_create_an_excel_file_with_one_dataset() {
		
		
		Date date = new Date();
		boolean exceptionThrown = false;
		DataLoadEngine engine = new ExcelLoadEngine();
		
		try {
			engine.setDataDestination(fileName);
		} catch (DataOutdoorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HashMap<String, Object> dataset = new HashMap<String, Object>();
		dataset.put("COL1", "simple value");
		engine.addDataset(dataset);
		try {
			engine.save();
		} catch (DataOutdoorException e) {
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);

		assertTrue(FileUtils.isFileNewer(new File(fileName), date));
		//fileUtils.
		
	}

}
