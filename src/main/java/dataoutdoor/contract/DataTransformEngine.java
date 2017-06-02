package dataoutdoor.contract;

import java.util.LinkedHashMap;

public interface DataTransformEngine {

	public LinkedHashMap<String, Object> transform(LinkedHashMap<String, Object> dataset);
	
}
