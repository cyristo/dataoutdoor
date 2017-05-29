package dataoutdoor.contract;

import java.util.HashMap;

public interface DatasetTransformer {

	public HashMap<String, Object> transformDataset(HashMap<String, Object> dataset);
	
}
