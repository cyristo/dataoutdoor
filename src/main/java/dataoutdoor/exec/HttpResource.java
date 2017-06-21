package dataoutdoor.exec;

import java.util.LinkedHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dataoutdoor.common.DataOutdoorException;
import dataoutdoor.common.DataOutdoorUtils;
import dataoutdoor.contract.DataExtractEngine;
import dataoutdoor.extractors.ExcelExtractEngine;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("{datasource}/{datacategory}/{datasetid}")
public class HttpResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(@PathParam("datasource") String datasource, 
    				@PathParam("datacategory") String datacategory, 
    					@PathParam("datasetid") String datasetid) {
        //return "Dataset for " + datasource + " - " + datacategory + " - " + datasetid;
    	
    	String ret = "";
    	
    	LinkedHashMap<String, Object> dataset = null;
		DataExtractEngine engine = new ExcelExtractEngine();
		boolean exceptionThrown = false;
		
		try {
			engine.setDataSource(DataOutdoorUtils.getProperty("dataoutdoor.datasource."+datasource));
			engine.setDataCategory(datacategory);
			dataset = engine.getDatasetById(datasetid);
		} catch (DataOutdoorException e) {
			ret = "ERROR : " + e.getMessage();
			exceptionThrown = true;
		}
		
		if (!exceptionThrown) {
			if (dataset == null || dataset.isEmpty()) {
				ret = "ERROR : Dataset not found in the specified datasource";
			} else {
				try {
					ret = DataOutdoorUtils.hashMaptoJson(dataset);
					ret = DataOutdoorUtils.formatPrettyJson(ret);
				} catch (DataOutdoorException e) {
					ret = "ERROR : " + e.getMessage();
				}
			}
		}

		return ret;
    	
    }
}
