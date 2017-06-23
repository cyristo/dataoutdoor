package dataoutdoor.contract;

import java.util.LinkedHashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataLoaderEngine {

	public void setDataDestination(Object dataDestination) throws DataOutdoorException;
	
	public void setDataTransformer(DataTransformerEngine transformer) throws DataOutdoorException;
	
	public void setDataCategory(String dataCategory);
	
	public void addDataset(LinkedHashMap<String, Object> dataset) throws DataOutdoorException;
	
	public void addDatasets(LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets) throws DataOutdoorException;
	
	public void save() throws DataOutdoorException;
	
}
