package net.property.bean.location;

public class LocationSuggestionBean {
private int id;
private String locality;
private String neighbourhood;
public LocationSuggestionBean(int id, String locality, String neighbourhood) {
	super();
	this.id = id;
	this.locality = locality;
	this.neighbourhood = neighbourhood;
}
public int getId() {
	return id;
}
public String getLocality() {
	return locality;
}
public String getNeighbourhood() {
	return neighbourhood;
}

}
