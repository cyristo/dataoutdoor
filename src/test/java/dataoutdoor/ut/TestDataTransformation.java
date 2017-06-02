package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.contract.DatasetTransformerEngine;
import dataoutdoor.extractors.ExcelExtractEngine;
import dataoutdoor.loaders.ExcelLoadEngine;
import dataoutdoor.transformers.AbstractTransformer;

public class TestDataTransformation {

	
    
	@Test 
	public void testTransformation() {
		
		boolean exceptionThrown = false;

				
		DataExtractEngine extractor = new ExcelExtractEngine();
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> transDatasets = null;
		
		DatasetTransformerEngine transformer = new ExampleTransformer();
		
		DataLoadEngine loader = new ExcelLoadEngine();

		
		try {
			extractor.setDataSource("src/test/resources/datasource.xls");
			loader.setDataDestination("src/test/resources/transdatasource.xls");
			loader.setDatasetTransformer(transformer);
			loader.addDatasets(extractor.getDatasets());	
			loader.save();
			extractor = new ExcelExtractEngine();
			extractor.setDataSource("src/test/resources/transdatasource.xls");
			transDatasets = extractor.getDatasets();
		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);

		assertThat(transDatasets.size(), is(240));
		
		LinkedHashMap<String, Object> row72 = transDatasets.get(72);
		assertThat(row72.get("ISO CODES").toString(), is("FR"));
		Integer val = 547030 * 2;
		Double dbl = (Double) row72.get("AREA KM2");
		assertThat(dbl.intValue()+"", is(val.toString()));
		assertNull(row72.get("POPULATION"));
		assertNotNull(row72.get("DATE TIME"));
				
	}
	
	private class ExampleTransformer extends AbstractTransformer {

		@Override
		public Object transformValue(String key, Object value) {
			Object newValue = value;
			if (key.equals("ISO CODES")) {
				newValue = StringUtils.substring((String) value, 0, 2);
			}
			return newValue;
		}

		@Override
		public LinkedHashMap<String, Object> transformDataModel(LinkedHashMap<String, Object> transformedDataset) {
			transformedDataset.remove("POPULATION");
			Double val = (Double) transformedDataset.get("AREA KM2");
			val = val * 2;
			transformedDataset.replace("AREA KM2", val.intValue());	
			transformedDataset.put("DATE TIME", new Date().toString());
			return transformedDataset;
		}
		
	}


}
