package dataoutdoor.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static String hashMaptoJson(LinkedHashMap<String, Object> map) {

		StringBuilder buf = new StringBuilder();
		
		buf.append("{").append(LINE_SEPARATOR);
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = (Object) map.get(key);
			buf.append("  "+"\""+key+"\": ");
			if (isDouble(value) || isInteger(value)) {
				buf.append(value+",");
			} else {
				buf.append("\""+value+"\",");
			}
			buf.append(LINE_SEPARATOR);
		}
		buf.append("}");
		String json = buf.toString();
		int index = StringUtils.lastIndexOf(json, ",");
		json = json.substring(0, index) + json.substring(index+1);
		return json;
		
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
