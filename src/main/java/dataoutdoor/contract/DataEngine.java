package dataoutdoor.contract;

import java.io.IOException;
import java.util.HashMap;

import dataoutdoor.common.DataOutdoorException;

public interface DataEngine {

	public void setDataSource(Object dataSource) throws DataOutdoorException;
	public HashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException;
	public HashMap<String, Object> getDatasetByRowNum(int rowNum) throws DataOutdoorException;
	public HashMap<String, Object> getDatasetById(String category, Object id) throws DataOutdoorException;
	public HashMap<String, Object> getDatasetByRowNum(String category, int rowNum) throws DataOutdoorException;

}