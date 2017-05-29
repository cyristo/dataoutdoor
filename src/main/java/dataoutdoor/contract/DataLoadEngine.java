package dataoutdoor.contract;

import java.util.Collection;
import java.util.HashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataLoadEngine {

	public void setDataDestination(Object dataDestination) throws DataOutdoorException;
	
	public void setDataModel(Collection<String> dataModel);
	
	public void setDatasetTransformer(DatasetTransformer transformer);
	
	public void addDataset(HashMap<String, Object> dataset);
	
	public void addDatasets(Collection<Object[]> datasets);
	
	public void updateDataByReference(Object dataReference, Object data);
	
	public void save() throws DataOutdoorException;
	
}