package dataoutdoor.exec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.contract.DataLoadEngine;
import dataoutdoor.extractors.ExcelExtractEngine;
import dataoutdoor.loaders.JsonLoadEngine;

public class CmdRunner {

	public static void main(String[] args) {
		
		String source = args[0];
		String category = null;
		String id = null;
		if (StringUtils.contains(args[1], ":")) {
			String split[] = StringUtils.split(args[1], ":");
			category = split[0];
			id = split[1];
		} else {
			id = args[1];
		}

		LinkedHashMap<String, Object> dataset = null;
		DataExtractEngine extractor = new ExcelExtractEngine();
		JsonLoadEngine loader = new JsonLoadEngine();
		boolean exceptionThrown = false;
		
		try {
			extractor.setDataSource(source);
			extractor.setDataCategory(category);
			dataset = extractor.getDatasetById(id);
		} catch (DataOutdoorException e) {
			System.out.println("ERROR : " + e.getMessage());
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
				}
			}
		}


	}

}
