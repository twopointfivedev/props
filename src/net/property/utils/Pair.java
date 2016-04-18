package net.property.utils;

public class Pair {
	 private int key;
	 private String value;
	public Pair(int key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public static int rentMinRange = 2500;
	public static int rentMaxRange = 100000;
	public static int saleMinRange = 2000000;
	public static int saleMaxRange = 50000000;
	public static int areaMinRange = 200;
	public static int areaMaxRange = 5000;

public int getKey() {
	return key;
}
public String getValue() {
	return value;
}
}
