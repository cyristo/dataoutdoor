package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataExtractorEngine;
import dataoutdoor.contract.DataLoaderEngine;
import dataoutdoor.contract.DataTransformerEngine;
import dataoutdoor.extractor.ExcelExtractor;
import dataoutdoor.loader.ExcelLoader;
import dataoutdoor.transformer.AbstractTransformer;

public class TestDataTransformation {

	
    
	@Test 
	public void testTransformation() {
		
		boolean exceptionThrown = false;

				
		DataExtractorEngine extractor = new ExcelExtractor();
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> transDatasets = null;
		
		DataTransformerEngine transformer = new ExampleTransformer();
		
		DataLoaderEngine loader = new ExcelLoader();

		
		try {
			extractor.setDataSource("src/test/resources/datasource.xls");
			loader.setDataDestination("src/test/resources/transdatasource.xls");
			loader.setDataTransformer(transformer);
			loader.addDatasets(extractor.getDatasets());	
			loader.save();
			extractor = new ExcelExtractor();
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
		public LinkedHashMap<String, Object> transformDataset(LinkedHashMap<String, Object> transformedDataset) {
			transformedDataset.remove("POPULATION");
			Double val = (Double) transformedDataset.get("AREA KM2");
			val = val * 2;
			transformedDataset.replace("AREA KM2", val.intValue());	
			transformedDataset.put("DATE TIME", new Date().toString());
			return transformedDataset;
		}
		
	}


}
