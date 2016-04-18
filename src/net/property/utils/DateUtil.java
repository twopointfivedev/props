package net.property.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String format = "yyyyMMdd";
	
	public static String today() {
		return today(format);
	}
	
	/**
	 * 
	 * @return
	 * 
	 * Returns current date
	 * 
	 */
	public static String today(String format) {
		SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
		String date = sdfFormat.format(new java.util.Date(System.currentTimeMillis()));
		return date;
	}
	

	/**
	 * 
	 * @param dateString
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 * 
	 * Convert one format of date to another.
	 * 
	 */
	
	public static String getDateInOtherFormat(String dateString, String oldFormat, String newFormat) {
		SimpleDateFormat sdfOldFormat = new SimpleDateFormat(oldFormat);
		SimpleDateFormat sdfNewFormat = new SimpleDateFormat(newFormat);
		String toReturn;
		try {
			Date d = sdfOldFormat.parse(dateString);
			toReturn = sdfNewFormat.format(d);
		} catch(Exception e) {
			Date d= new Date();
			toReturn = sdfNewFormat.format(d);
		}
		return toReturn;
	}
	/**
	 * 
	 * @return
	 * 
	 * Returns current year
	 * 
	 */
	public static String year() {
		return new SimpleDateFormat("yyyy").format(new java.util.Date(System.currentTimeMillis()));
	}
	
	/**
	 * 
	 * @param days
	 * @return
	 */
	public static String daysBeforAfter(long days) {
		return days(format, days);
	}
	
	/**
	 * Gives RN standard date +/- days 
	 * days = -7 give date 7 days ago
	 * days = 7 give date 7 days after
	 * @param days number of days before or after today. Use negative values to indicate days before
	 * @return
	 */
	public static String days(String format, long days) {
		SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
		String date = sdfFormat.format(new java.util.Date(System.currentTimeMillis() + days * 86400000));
		return date;
	}
	
}
