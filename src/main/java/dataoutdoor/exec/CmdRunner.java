package dataoutdoor.exec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.Utils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.extractors.ExcelExtractEngine;

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
		DataExtractEngine engine = new ExcelExtractEngine();
		boolean exceptionThrown = false;
		
		try {
			engine.setDataSource(source);
			engine.setDataCategory(category);
			dataset = engine.getDatasetById(id);
		} catch (DataOutdoorException e) {
			System.out.println("ERROR : " + e.getMessage());
			exceptionThrown = true;
		}
		
		if (!exceptionThrown) {
			if (dataset == null || dataset.isEmpty()) {
				System.out.println("ERROR : Dataset not found in the specified datasource");
			} else {
				try {
					System.out.println(Utils.hashMaptoJson(dataset));
				} catch (DataOutdoorException e) {
					System.out.println("ERROR : " + e.getMessage());
				}
			}
		}


	}

}
