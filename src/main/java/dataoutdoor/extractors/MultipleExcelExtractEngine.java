package dataoutdoor.extractors;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataExtractEngine;

public class MultipleExcelExtractEngine implements DataExtractEngine {

	private Collection<ExcelExtractEngine> engines;
	
	public void setDataSource(Object dataSource) throws DataOutdoorException {
		engines = new ArrayList<ExcelExtractEngine>();
		ExcelExtractEngine engine = null;
		@SuppressWarnings("unchecked")
		Collection<File> files = (Collection<File>) dataSource;
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = (File) iterator.next();
			engine = new ExcelExtractEngine();
			engine.setDataSource(file);
			engines.add(engine);
		}
		
	}

	public void setDataCategory(String dataCategory) throws DataOutdoorException {
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			engine.setDataCategory(dataCategory);
		}
	}

	public void setIdCategory(String IdCategory) {
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			engine.setIdCategory(IdCategory);
		}
	}

	public void setIdFilter(String IdFilter) {
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			engine.setIdFilter(IdFilter);
		}
	}

	public Collection<String> getDataModel() {
		//TODO not really interesting implementation, see how to do something better
		Collection<String> dataModel = null;
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			dataModel = engine.getDataModel();
			break;
		}
		return dataModel;
	}

	public LinkedHashMap<String, Object> getDatasetById(Object id) throws DataOutdoorException {
		//TODO not really interesting implementation, see how to do something better
		LinkedHashMap<String, Object> dataset = null;
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			dataset = engine.getDatasetById(id);
			break;
		}
		return dataset;
	}

	public LinkedHashMap<String, Object> getDatasetByRowNum(Integer rowNum) throws DataOutdoorException {
		//TODO not really interesting implementation, see how to do something better
		LinkedHashMap<String, Object> dataset = null;
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			dataset = engine.getDatasetByRowNum(rowNum);
			break;
		}
		return dataset;
	}

	public LinkedHashMap<Integer, LinkedHashMap<String, Object>> getDatasets() throws DataOutdoorException {
		//TODO not really interesting implementation, see how to do something better
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> datasets = null;
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			datasets = engine.getDatasets();
			break;
		}
		return datasets;
	}

	public Collection<Object[]> getDataTab() throws DataOutdoorException {
		Collection<Object[]> datatab = new ArrayList<Object[]>();
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			datatab.addAll(engine.getDataTab());
		}
		return datatab;
	}

	public Object getDataByReference(Object dataReference) throws DataOutdoorException {
		Collection<Object> cellList = new ArrayList<Object>();
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			cellList.add(engine.getDataByReference(dataReference));
		}
		return cellList;
	}
	
	public Collection<Object[]> getRangedDataTab(int firstRow, int lastRow, int firstCol, int lastCol) throws DataOutdoorException {
		Collection<Object[]> datatab = new ArrayList<Object[]>();
		for (Iterator<ExcelExtractEngine> iterator = engines.iterator(); iterator.hasNext();) {
			ExcelExtractEngine engine = (ExcelExtractEngine) iterator.next();
			datatab.addAll(engine.getRangedDataTab(firstRow, lastRow, firstCol, lastCol));
		}
		return datatab;
	}

}
