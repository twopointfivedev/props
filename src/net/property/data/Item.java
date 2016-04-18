package net.property.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Item {
	protected Properties data;
	
	public Item() {
		data = new Properties();
	}
	
	public void addData(String key, String value) {
		data.setProperty(key, value);
	}
	
	public String getProperty(String key) {
		return data.getProperty(key, "");
	}
	
	public List<String> getFieldNames(String key, boolean startsWith) {
		List<String> toReturn = new ArrayList<String>();
		for (String name:data.stringPropertyNames()) {
			if (name.startsWith(key)) {
				toReturn.add(name);
			}
		}
		return toReturn;
	}
	
	public Set<String> getFieldNames() {
		return data.stringPropertyNames();
	}
	
	/** @deprecated use getFieldNames()
	 * 
	 * @return
	 */
	public Set<String> getProperties() {
		return data.stringPropertyNames();
	}
}
