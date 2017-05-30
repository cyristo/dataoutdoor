package dataoutdoor.contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataExtractEngine {

	public void setDataSource(Object dataSource) throws DataOutdoorException;
	
	public void setDataCategory(String dataCategory) throws DataOutdoorException;
	
	public void setDataIdCategory(String dataIdCategory);
	
	public void setDataIdFilter(String dataIdFilter);
	
	public Collection<String> getDataModel(); 
	
	public LinkedHashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException;
	
	public LinkedHashMap<String, Object> getDatasetByRowNum(int rowNum) throws DataOutdoorException;
	
	public Collection<Object[]> getDatasets() throws DataOutdoorException;
	
	public Object getDataByReference(Object dataReference) throws DataOutdoorException;
	
}