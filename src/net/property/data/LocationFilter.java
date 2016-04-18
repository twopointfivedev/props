
package net.property.data;

import java.util.List;

public class LocationFilter {
	// Name is the display name of the filter
	private String name = "";
	// Value used in the actual filter value
	private String value = "";
	// Number of matching property
	private long count = 0;
	
	private List<LocationFilter> locationFilters = null;
	
	public LocationFilter(String name, String value, long count) {
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
		return value;
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

	public List<LocationFilter> getLocationFilters() {
		return locationFilters;
	}

	public void setLocationFilters(List<LocationFilter> locationFilters) {
		this.locationFilters = locationFilters;
	}
}
