package dataoutdoor.contract;

import java.util.Collection;
import java.util.LinkedHashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataExtractorEngine {

	public void setDataSource(Object dataSource) throws DataOutdoorException;
	
	public void setDataCategory(String dataCategory) throws DataOutdoorException;
	
	public void setIdCategory(String IdCategory);
	
	public void setIdFilter(String IdFilter);
	
	public Collection<String> getDataModel(); 
	
	public LinkedHashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException;
	
	public LinkedHashMap<String, Object> getDatasetByRowNum(Integer rowNum) throws DataOutdoorException;
	
	public LinkedHashMap<Integer, LinkedHashMap<String, Object>> getDatasets() throws DataOutdoorException;
	
	public Collection<Object[]> getDataTab() throws DataOutdoorException;
	
	public Object getDataByReference(Object dataReference) throws DataOutdoorException;
	
}