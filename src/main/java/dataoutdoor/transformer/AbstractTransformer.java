package dataoutdoor.transformer;

import java.util.Iterator;
import java.util.LinkedHashMap;

import dataoutdoor.contract.DataTransformerEngine;

public abstract class AbstractTransformer implements DataTransformerEngine {

	public LinkedHashMap<String, Object> transform(LinkedHashMap<String, Object> dataset) {

		LinkedHashMap<String, Object> transformedDataset = new LinkedHashMap<String, Object>();

		Iterator<String> iter = dataset.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = (Object) dataset.get(key);
			value = transformValue(key, value);
			transformedDataset.put(key, value);
		}

		return transformDataset(transformedDataset);
	}

	public abstract Object transformValue(String key, Object value);
	
	public abstract LinkedHashMap<String, Object> transformDataset(LinkedHashMap<String, Object> dataset);
}
