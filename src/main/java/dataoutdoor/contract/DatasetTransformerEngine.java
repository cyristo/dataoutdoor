package dataoutdoor.contract;

import java.util.LinkedHashMap;

public interface DatasetTransformerEngine {

	public LinkedHashMap<String, Object> transform(LinkedHashMap<String, Object> dataset);
	
}
