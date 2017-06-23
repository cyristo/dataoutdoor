package dataoutdoor.ut;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.extractor.ExcelExtractor;
import dataoutdoor.extractor.MultipleExcelExtractor;

public class TestExtendedExcelCapabilities {

	@Test
	public void should_get_data_from_merged_files() {

		boolean exceptionThrown = false;
		String category = "CoUNTRY";
		Object id = "FraNce";

		String[] extenstions = new String[2];
		extenstions[0] = "xls";
		extenstions[1] = "xlsx";
		Collection<File> files = FileUtils.listFiles(new File("src/test/resources/multiple"), extenstions, false);

		MultipleExcelExtractor engine = new MultipleExcelExtractor();
		Collection<Object> cellList = new ArrayList<Object>();
		Collection<Object[]> datatab = new ArrayList<Object[]>();
		try {
			engine.setDataSource(files);
			cellList = (Collection<Object>) engine.getDataByReference("B2");
			datatab = engine.getDataTab();
		} catch (DataOutdoorException e) {
			exceptionThrown = true;
			e.printStackTrace();
		}

		assertFalse(exceptionThrown);

		assertThat(cellList.size(), is(2));
		assertThat(cellList.toArray()[0].toString(), is("93.0"));
		assertThat(cellList.toArray()[1].toString(), is("93.0"));

		assertThat(datatab.size(), is(240*2));
	}

	@Test
	public void should_set_the_row_to_be_considered_as_header() {
		boolean exceptionThrown = false;

		ExcelExtractor engine = new ExcelExtractor();
		LinkedHashMap<String, Object> dataset1 = null;
		LinkedHashMap<String, Object> dataset2 = null;
		Object cell = null;
		Collection<String> datamodel = null;
		LinkedHashMap<Integer, LinkedHashMap<String, Object>> allDatasets = null;
		LinkedHashMap<Integer, LinkedHashMap<String, Object>>filteredDatasets = null;

		try {
			engine.setDataSource("src/test/resources/BIP_Assessment 130617.xlsx");
			engine.setDataCategory("BSC proposals");
			engine.setIdCategory("1");
			engine.setHeaderRow(5);
			dataset1 = engine.getDatasetById("Tools");
			cell = engine.getDataByReference("B24"); //Security
			datamodel = engine.getDataModel();
			dataset2 = engine.getDatasetByRowNum(10);
			allDatasets = engine.getDatasets();
			engine.setIdFilter("Comprehensive Testing");
			filteredDatasets = engine.getDatasets();


		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);

		String result1 = (String) dataset1.get("Applicable to project (Y/N)");
		assertThat(result1, is("TOTO"));

		assertThat(cell.toString(), is("Security"));

		assertThat(datamodel.size(), is(12));
		assertThat(datamodel.toArray()[0].toString(), is("Theme"));

		String result2 = (String) dataset2.get("Applicable to project (Y/N)");
		assertThat(result2, is("THIS ONE"));

		assertThat(allDatasets.size(), is(34));
		assertThat(filteredDatasets.size(), is(4));
	}

	@Test
	public void should_get_a_selected_area() {
		boolean exceptionThrown = false;

		ExcelExtractor engine = new ExcelExtractor();
		Collection<Object[]> datatab = null;

		try {
			engine.setDataSource("src/test/resources/BIP_Assessment 130617.xlsx");
			datatab = engine.getRangedDataTab(6, 26, 1, 2);

		} catch (DataOutdoorException e) {
			e.printStackTrace();
			exceptionThrown = true;
		}

		assertFalse(exceptionThrown);

		assertThat(datatab.size(), is(24));
		Object[] tab = datatab.toArray();

		Object[] row0 = (Object[]) tab[0];
		Object[] row10 = (Object[]) tab[10];
		assertThat(row0[0].toString(), is("Item"));
		assertThat(row10[1].toString(), is("Antoine BABOEUF"));

	}

}
