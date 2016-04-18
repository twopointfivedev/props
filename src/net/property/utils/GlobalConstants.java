

package net.property.utils;

import java.io.File;



public class GlobalConstants {
	public static final String VERSION = "1.0";
	public static final String STATIC_CONTENT_VERSION = "1.0";
    public static final long CRAWLING_INTERVAL = 0; // Wait for the specified time (ms) between page fetches
    
    /**Local server */
 //   public static String THUMBNAIL_IMG_DIR = "assets\\img\\crawlimages\\";
	
    
    /**server variables */
    //https://s3-ap-southeast-1.amazonaws.com/img.property/crawlimages
    public static String THUMBNAIL_IMG_DIR = "https://" + File.separator+ "s3-ap-southeast-1.amazonaws.com"
    		+File.separator+  "img.property"+File.separator+ "crawlimages"+ File.separator;
    
    /** Solr constants */
//	public static final String CDN_HOST = "propertyseeker.com";
//	public static final String SOLR_HOST = "localhost";
	
//	public static final String SOLR_URL = "http://localhost:8980/solr/prop";
//	public static final String SOLR_PATH = "/solr/prop/";
//	public static final int SOLR_PORT = 8980;
//    public static final String SOLR_USER = "manager"; //TODO setup uid password
//    public static final String SOLR_PASSWORD = "pe99RST";
//    public static final boolean SOLR_USE_MULTI_PART = false;
	
}
