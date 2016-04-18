package net.property.utils;

public class ImageUtils {

	public static String getPath(String propertyRef, int flatType, String source) {
		int subFolder = Math.abs(propertyRef.hashCode()%100);
		String s = GlobalConstants.THUMBNAIL_IMG_DIR+source +"\\"+flatType+"\\"+subFolder+"\\"+propertyRef;
		return s;
	}

}
