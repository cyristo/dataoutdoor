package dataoutdoor.common;

import java.util.HashMap;
import java.util.Hashtable;

import org.glassfish.grizzly.http.server.HttpServer;


public class DataOutdoorCache {

	private HttpServer server;
	private HashMap<String, String> cache;

	private DataOutdoorCache() {
	}

	/** Instance unique non préinitialisée */
	private static DataOutdoorCache INSTANCE = null;

	/** Point d'accès pour l'instance unique du singleton */
	public synchronized static DataOutdoorCache getInstance() {	
		if (INSTANCE == null) {	
			INSTANCE = new DataOutdoorCache();

		}
		return INSTANCE;
	}

	
}
