package dataoutdoor.loader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.DataOutdoorUtils;
import dataoutdoor.contract.DataLoaderEngine;
import dataoutdoor.contract.DataTransformerEngine;

public class JsonLoader implements DataLoaderEngine {

	private String dataDestination;
	private String jsonTableName = "Data Outdoor Table";
	private StringBuilder buf = new StringBuilder();
	private DataTransformerEngine transformer = null;
	
	public void setDataDestination(Object dataDestination) throws DataOutdoorException {
		this.dataDestination = dataDestination.toString();
	}

	public void setDataTransformer(DataTransformerEngine transformer) throws DataOutdoorException {
		this.transformer = transformer;	
	}
	
	public void setDataCategory(String dataCategory) {
		jsonTableName = dataCategory;
	}

	public void addDataset(LinkedHashMap<String, Object> dataset) throws DataOutdoorException {
		if (transformer != null) dataset = transformer.transform(dataset);
		buf.append(DataOutdoorUtils.hashMaptoJson(dataset));
		buf.append(",");
	}

	public void addDatasets(LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets) throws DataOutdoorException {
		Collection<Integer> rows = datasets.keySet();
		for (Iterator<Integer> iterator = rows.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			addDataset(datasets.get(key));
		}
	}

	public void save() throws DataOutdoorException {
		File file = new File(dataDestination);
		try {
			FileUtils.write(file, getPrettyJson(), Charset.defaultCharset());
		} catch (IOException e) {
			throw new DataOutdoorException(e);
		}
	}
	
	public String getPrettyJson() throws DataOutdoorException {
		String pretty = buf.toString();
		pretty = StringUtils.removeEnd(pretty, ",");
		pretty = "{\""+jsonTableName+"\":["+pretty+"]}";
		pretty = DataOutdoorUtils.formatPrettyJson(pretty);
		return pretty;
	}
	
}
