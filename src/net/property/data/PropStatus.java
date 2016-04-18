package net.property.data;

public class PropStatus {
private String propertyRef;
private Short status;
public PropStatus(String propertyRef, Short status) {
	super();
	this.propertyRef = propertyRef;
	this.status = status;
}
public String getPropertyRef() {
	return propertyRef;
}
public Short getStatus() {
	return status;
}
}
