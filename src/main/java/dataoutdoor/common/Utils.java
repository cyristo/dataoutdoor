package dataoutdoor.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	//private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	//private static final String LINE_SEPARATOR = "";
	
	public static String hashMaptoJson(LinkedHashMap<String, Object> map) throws DataOutdoorException {

		StringBuilder buf = new StringBuilder();
		
		buf.append("{");
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = (Object) map.get(key);
			buf.append("\""+key+"\":");
			if (isDouble(value) || isInteger(value)) {
				buf.append(value+",");
			} else {
				buf.append("\""+value+"\",");
			}
		}
		buf.append("}");
		String json = buf.toString();
		int index = StringUtils.lastIndexOf(json, ",");
		json = json.substring(0, index) + json.substring(index+1);
		//json = formatPrettyJson(json);
		return json;
		
	}
	
	public static String formatPrettyJson(String txt) throws  DataOutdoorException {
		ObjectMapper mapper = new ObjectMapper();
		String ret = txt;
		try {
			Object json = mapper.readValue(txt, Object.class);
			ret = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		} catch (JsonProcessingException e) {
			throw new DataOutdoorException(e);
		} catch (IOException e) {
			throw new DataOutdoorException(e);
		}
		return ret;
	}
	
	public static boolean isInteger(Object o) {
		try { 
			Integer.parseInt(o.toString()); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(Object o) {
		try { 
			Double.parseDouble(o.toString()); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidJson(String json) {
		boolean valid = true;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;

		try {
			rootNode = mapper.readTree(json);
		} catch (Exception e) {
			System.out.println(e);
			valid = false;
			//throw new DataOutdoorException(e);
		}

		return valid;
	}
	
	
}
