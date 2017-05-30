package dataoutdoor.contract;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataLoadEngine {

	public void setDataDestination(Object dataDestination) throws DataOutdoorException;
	
	public void setDataCategory(String dataCategory);
	
	public void setDatasetTransformer(DatasetTransformer transformer);
	
	public void addDataset(LinkedHashMap<String, Object> dataset);
	
	public void save() throws DataOutdoorException;
	
}
