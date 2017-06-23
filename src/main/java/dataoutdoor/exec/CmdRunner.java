package dataoutdoor.exec;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.DataOutdoorUtils;
import dataoutdoor.contract.DataExtractorEngine;
import dataoutdoor.extractor.ExcelExtractor;
import dataoutdoor.loader.JsonLoader;

public class CmdRunner {

	private String source;
	private String category;
	private String id;

	public static void main(String[] args) {

		CmdRunner runner = new CmdRunner();
		runner.init(args);
		runner.exec();

	}

	private void init(String[] args) {

		source = DataOutdoorUtils.getProperty("dataoutdoor.datasource");

		category = null;
		id = null;
		if (StringUtils.contains(args[0], ":")) {
			String split[] = StringUtils.split(args[0], ":");
			category = split[0];
			id = split[1];
		} else {
			id = args[1];
		}
	}

	private void exec() {


		LinkedHashMap<String, Object> dataset = null;
		DataExtractorEngine extractor = new ExcelExtractor();
		JsonLoader loader = new JsonLoader();
		boolean exceptionThrown = false;

		try {
			extractor.setDataSource(source);
			extractor.setDataCategory(category);
			dataset = extractor.getDatasetById(id);
		} catch (DataOutdoorException e) {
			System.out.println("ERROR : " + e.getMessage());
			e.printStackTrace();
			exceptionThrown = true;
		}

		if (!exceptionThrown) {
			if (dataset == null || dataset.isEmpty()) {
				System.out.println("ERROR : Dataset not found in the specified datasource");
			} else {
				try {
					loader.addDataset(dataset);
					System.out.println(loader.getPrettyJson());
				} catch (DataOutdoorException e) {
					System.out.println("ERROR : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
