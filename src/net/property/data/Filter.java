
package net.property.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Filter {
	// Name is the display name of the filter
	private String name = "";
	// Value used in the actual filter value
	private String value = "";
	// Number of matching property
	private long count = 0;
	
	public Filter(String name, String value, long count) {
		this.name = name;
		this.value = value;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		try {
			return URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return URLEncoder.encode(value);
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}	
}
