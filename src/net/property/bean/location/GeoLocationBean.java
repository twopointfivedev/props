package net.property.bean.location;
/**
 * Matching fields from google geocoding api are mapped this object
 * @author user
 *
 */
public class GeoLocationBean {
private String locationId;
private double latitude;
private double longitude;
private boolean partialMatch;
private String formattedAddress;
private String rawAddress;
private String neighbourhood;
private	String district;
private	String country;
private	String streetNumber;
private	String route;

public String getLocationId() {
	return locationId;
}
public void setLocationId(String locationId) {
	this.locationId = locationId;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public boolean isPartialMatch() {
	return partialMatch;
}
public void setPartialMatch(boolean partialMatch) {
	this.partialMatch = partialMatch;
}
public String getFormattedAddress() {
	return formattedAddress;
}
public void setFormattedAddress(String formatterAddress) {
	this.formattedAddress = formatterAddress;
}
public String getRawAddress() {
	return rawAddress;
}
public void setRawAddress(String rawAddress) {
	this.rawAddress = rawAddress;
}
public String getNeighbourhood() {
	return neighbourhood;
}
public void setNeighbourhood(String neighbourhood) {
	this.neighbourhood = neighbourhood;
}
public String getDistrict() {
	return district;
}
public void setDistrict(String district) {
	this.district = district;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getStreetNumber() {
	return streetNumber;
}
public void setStreetNumber(String streetNumber) {
	this.streetNumber = streetNumber;
}
public String getRoute() {
	return route;
}
public void setRoute(String route) {
	this.route = route;
}
@Override
public String toString() {
	return "GeoLocationBean [locationId=" + locationId + ", latitude="
			+ latitude + ", longitude=" + longitude + ", partialMatch="
			+ partialMatch + ", formatterAddress=" + formattedAddress
			+ ", rawAddress=" + rawAddress + ", neighbourhood=" + neighbourhood
			+ ", district=" + district + ", country=" + country
			+ ", streetNumber=" + streetNumber + ", route=" + route + "]";
}
}
