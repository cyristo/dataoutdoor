package dataoutdoor.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.contract.DatasetTransformer;

public class ExcelLoadEngine implements DataLoadEngine {

	private Workbook workbook = null;
	private String dataDestination = null;
	private Sheet dataSheet;

	public void setDataDestination(Object dataDestination) throws DataOutdoorException {

		this.dataDestination = dataDestination.toString();

		if (StringUtils.endsWith(dataDestination.toString(), "xlsx")) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}

		dataSheet = workbook.createSheet("Generated sheet1");
	}

	public void setDataModel(Collection<String> dataModel) {
		// TODO Auto-generated method stub

	}

	public void setDatasetTransformer(DatasetTransformer transformer) {
		// TODO Auto-generated method stub

	}

	public void addDataset(HashMap<String, Object> dataset) {
		// TODO Auto-generated method stub

	}

	public void addDatasets(Collection<Object[]> datasets) {
		// TODO Auto-generated method stub

	}

	public void updateDataByReference(Object dataReference, Object data) {
		// TODO Auto-generated method stub

	}

	public void save() throws DataOutdoorException {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(dataDestination.toString());
		} catch (FileNotFoundException e1) {
			throw new DataOutdoorException(e1);
		}
		try {
			workbook.write(fileOut);
		} catch (IOException e2) {
			throw new DataOutdoorException(e2);
		} finally {
			try {
				fileOut.close();
			} catch (IOException e3) {
				throw new DataOutdoorException(e3);
			}
		}


	}

}
