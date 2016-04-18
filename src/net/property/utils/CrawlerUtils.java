package net.property.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class CrawlerUtils {
	public static Whitelist whiteList = getRNWhiteList();
	
	public static String generateRef(String data) {
		return DigestUtils.md5Hex(data).substring(8,24);
	}
	
	public static Whitelist getRNWhiteList() {
		Whitelist whitelist = new Whitelist();
		whitelist.addTags("b");
		whitelist.addTags("blockquote");
		whitelist.addTags("br");
		whitelist.addTags("caption");
		whitelist.addTags("cite");
		whitelist.addTags("code");
		whitelist.addTags("col");
		whitelist.addTags("colgroup");
		whitelist.addTags("div");
		whitelist.addTags("dd");
		whitelist.addTags("dl");
		whitelist.addTags("dt");
		whitelist.addTags("em");
		whitelist.addTags("h1");
		whitelist.addTags("h2");
		whitelist.addTags("h3");
		whitelist.addTags("h4");
		whitelist.addTags("h5");
		whitelist.addTags("h6");
		whitelist.addTags("i");
		whitelist.addTags("li");
		whitelist.addTags("ol");
		whitelist.addTags("p");
		whitelist.addTags("q");
		whitelist.addTags("small");
		whitelist.addTags("strike");
		whitelist.addTags("strong");
		whitelist.addTags("sub");
		whitelist.addTags("sup");
		whitelist.addTags("table");
		whitelist.addTags("tbody");
		whitelist.addTags("td");
		whitelist.addTags("tfoot");
		whitelist.addTags("th");
		whitelist.addTags("thead");
		whitelist.addTags("tr");
		whitelist.addTags("u");
		whitelist.addTags("ul");
		return whitelist;
	}
	
	public static String getSafeHtml(String data) {
		return Jsoup.clean(data, CrawlerUtils.whiteList);
	}
	
	public static Document getDocument(String data) {
		return Jsoup.parse(data, "UTF-8");
	}
	
	public static Document getXMLDocument(String data) {
		return Jsoup.parse(data, "UTF-8", Parser.xmlParser());
	}
	
	public static Elements getElements(Document doc, String selector) {
		return doc.select(selector);
	}
	
	public static Elements getElements(String data, String selector) {
		return getDocument(data).select(selector);
	}
	
	public static Elements getXMLElements(String data, String selector) {
		return getXMLDocument(data).select(selector);
	}
	
	public static String getText(Document doc, String selector) {
		return getElements(doc,selector).text();
	}
	
	public static String getText(String data, String selector) {
		return getElements(data,selector).text();
	}
	
	public static String getTextAdvanced (String data, String selector, boolean outerHtml) {
		if(selector.matches("\".*?\",\".*?\"")) {
			String[] selectors = selector.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			Elements e = getElements(data,selectors[0].replaceAll("\"(.*?)\"","$1"));
			for(int i=0; i < e.size(); i++) {
				for(Node n: e.get(i).textNodes()) {
					String temp = ((TextNode) n).text();
					Element custom = new Element(Tag.valueOf("textNode"), "");
					custom.appendText(temp);
					n.replaceWith(custom);
				}
			}
			if (outerHtml) {
				return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).outerHtml();
			} else {
				return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).text();
			}
		} else {
			if (outerHtml) {
				return getElements(data,selector).outerHtml();
			} else {
				return getText(data,selector);
			}
		}
	}
	
	public static String getTextAdvanced (Element data, String selector, boolean outerHtml) {
		if(selector.matches("^\".*?\",\".*?\"$")) {
			String[] selectors = selector.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			Elements e = data.select(selectors[0].replaceAll("\"(.*?)\"","$1"));
			for(int i=0; i < e.size(); i++) {
				for(Node n: e.get(i).textNodes()) {
					String temp = ((TextNode) n).text();
					Element custom = new Element(Tag.valueOf("textNode"), "");
					custom.appendText(temp);
					n.replaceWith(custom);
				}
			}
			if (outerHtml) {
				return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).outerHtml();
			} else {
				return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).text();
			}
		} else {
			if (outerHtml) {
				return data.select(selector).outerHtml();
			} else {
				return data.select(selector).text();
			}
		}
	}
	
	public static String getTextAdvanced (String data, String selector) {
		if(selector.matches("\".*?\",\".*?\"")) {
			String[] selectors = selector.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			Elements e = getElements(data,selectors[0].replaceAll("\"(.*?)\"","$1"));
			for(int i=0; i < e.size(); i++) {
				for(Node n: e.get(i).textNodes()) {
					String temp = ((TextNode) n).text();
					Element custom = new Element(Tag.valueOf("textNode"), "");
					custom.appendText(temp);
					n.replaceWith(custom);
				}
			}
			
			return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).text();
		} else {
			return getText(data,selector);
		}
	}

	public static String getTextAdvanced (Element data, String selector) {
		if(selector.matches("^\".*?\",\".*?\"$")) {
			String[] selectors = selector.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			Elements e = data.select(selectors[0].replaceAll("\"(.*?)\"","$1"));
			for(int i=0; i < e.size(); i++) {
				for(Node n: e.get(i).textNodes()) {
					String temp = ((TextNode) n).text();
					Element custom = new Element(Tag.valueOf("textNode"), "");
					custom.appendText(temp);
					n.replaceWith(custom);
				}
			}
			
			return e.select(selectors[1].replaceAll("\"(.*?)\"","$1")).text();
		} else {
			return data.select(selector).text();
		}
	}

	/**
	 * Utility to clean field based on regex patterns
	 * 
	 * @param data - String to be clean
	 * @param patterns - List of CSV regex patterns. <br>These regexes are java style.<br>
	 * 					patterns not matching this format will not be processed. <br>
	 * 					Any malformatted patterns are discarded. <br>
	 * 				 Ex:  "matchstring","replacestring"
	 * @return - return a clean data replace all patterns provided
	 */
	public static String regexCleanup(String data, List<String> patterns) {
		for(String pattern: patterns) {
			try {		
				String cleaner[] = pattern.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				String regex = cleaner[0].replaceAll("^\"(.*?)\"$", "$1");
				String replacement = cleaner[1].replaceAll("^\"(.*?)\"$", "$1");
				data = data.replaceAll(regex, replacement);
			} catch (Exception e) { e.printStackTrace(); } 
		}
		return data;
	}
	
	public static String getHtml(Document doc, String selector) {
		return getSafeHtml(getElements(doc,selector).html());
	}
	
	public static String getHtml(String data, String selector) {
		return getSafeHtml(getElements(data,selector).html());
	}
	
	public static void savePhoto(String url) {
		
	}	

	public static String getPrice(String price) {
		String toReturn = "";
		if(!price.isEmpty()){
			Pattern p1 = Pattern.compile("(?ius)([0-9,\\.]+)");
			Matcher m1 = p1.matcher(price);
			if(m1.find()) {
				toReturn = m1.group(1);
				toReturn = toReturn.replaceAll(",|\\.", "");
			} 
		} 		
		return toReturn;
	}
	
	public static String getHouseType (String data, boolean strict) {
		String type= "";
		if (strict) {
			if (data.matches("(?i).*?\\bterraced?(.{1,15})?house\\b.*")) {
				type = "Terraced House";
			} else if (data.matches("(?i).*?\\bBungalow\\b.*")) {
				type = "Bungalow";
			} else if (data.matches("(?i).*?\\bDetached(.{1,15})?house\\b.*")) {
				type = "Detached House";
			} else if (data.matches("(?i).*?\\bSemi[- ]detached(.{1,15})?house\\b.*")) {
				type = "Semi-detached House";
			} else if (data.matches("(?i).*?\\bMaisonette\\b.*")) {
				type = "Maisonette";
			} else if (data.matches("(?i).*?\\bflat\\b.*") || data.matches("(?i).*?\\bapartment\\b.*")) {
				type = "Flat";
			} else if (data.matches("(?i).*?\\bstudio\\b.*")) {
				type = "Studio";
			} else if (data.matches("(?i).*?\\bcottage\\b.*")) {
				type = "Cottage";
			} else if (data.matches("(?i).*?\\bPenthouse\\b.*")) {
				type = "Penthouse";
			} else if (data.matches("(?i).*?\\bhouse\\b.*")) {
				type = "House";
			}   
			
		} else {
			if (data.matches("(?i).*?\\bterraced?( house)?.*")) {
				type = "Terraced House";
			} else if (data.matches("(?i).*?\\bBungalow?.*")) {
				type = "Bungalow";
			} else if (data.matches("(?i).*?\\bDetached( house)?.*")) {
				type = "Detached House";
			} else if (data.matches("(?i).*?\\bSemi[- ]detached( house)?.*")) {
				type = "Semi-detached House";
			} else if (data.matches("(?i).*?\\bMaisonette\\b.*")) {
				type = "Maisonette";
			} else if (data.matches("(?i).*?\\bflat\\b.*") || data.matches("(?i).*?\\bapartment\\b.*")) {
				type = "Flat";
			} else if (data.matches("(?i).*?\\bstudio\\b.*")) {
				type = "Studio";
			} else if (data.matches("(?i).*?\\bcottage\\b.*")) {
				type = "Cottage";
			} else if (data.matches("(?i).*?\\bPenthouse\\b.*")) {
				type = "Penthouse";
			} else if (data.matches("(?i).*?\\bhouse\\b.*")) {
				type = "House";
			}  
		}
		return type;
	}
	
	public static String getHouseType (String data) {
		return getHouseType(data, false);
	}
	
	public static String getBedRooms (String data) {
		String bedrooms = "";
		if (data.matches("\\d+"))
			return data;

		Pattern p1 = Pattern.compile("(?ius)(\\d+) ?bed(rooms?)?");
		Matcher m1 = p1.matcher(data);
		if (m1.find()) {
			bedrooms=m1.group(1);
		} else if (data.matches(".*?one (single|double)? ?bedroom.*")) {
			bedrooms = "1";
		} else if (data.contains(".*?two (single|double)? ?bedroom.*")) {
			bedrooms = "2";
		} else if (data.contains(".*?three (single|double)? ?bedroom.*")) {
			bedrooms = "3";
		} else if (data.contains(".*?four (single|double)? ?bedroom.*")) {
			bedrooms = "4";
		} else if (data.contains("five (single|double)? ?bedroom")) {
			bedrooms = "4";
		}

		return bedrooms;
	}	
	
	public static String cleanDescription(String description) {
		// get plain text. Remove all html formatting
		description = Jsoup.clean(description, Whitelist.none());
		
		// remove extra new lines
		description = description.replaceAll("(?uis)[\n]{3,100}","\n\n");
		
		return description;
	}
	
	public static String saveImage (String ref, String url) {
		String ext = "";
		try {
			HttpUriRequest request = new HttpGet(url);
		
			ext = url.replaceAll(".*?([^.]+)$", "$1");

			if (!ext.isEmpty()) {
				HttpResponse response = HttpUtils.getHttpClient().execute(request);
				InputStream is = response.getEntity().getContent();
				byte[] buffer = new byte[8 * 1024];
				try {
					OutputStream output = new FileOutputStream(GlobalConstants.THUMBNAIL_IMG_DIR+ref.substring(0,2)+"/"+ref+"."+ext);
					try {
						int bytesRead;
						while ((bytesRead = is.read(buffer)) != -1) {
							output.write(buffer, 0, bytesRead);
						}
					} finally {
						output.close();
					}
				} finally {
					is.close();
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ext != null)
			ext = ext.toLowerCase();
		return ext;
	}
	
	public static Set<String> getFeatureType (String data) {
		Set<String> features = new HashSet<String>();
		if (data.matches("(?i).*?\\bFurnished\\b.*")) {
			features.add("Furnished");
		}
		if (data.matches("(?i).*?\\bParking\\b.*")) {
			features.add("Parking");
		}
		if (data.matches("(?i).*?\\bNewly[- ]built\\b.*")) {
			features.add("Newly-built");
		}

		return features;
	}
	
	public static void main(String[] args) {
		String unsafe = 
				  "<p><a href='http://example.com/' onclick='stealCookies()' class='abc'>Link</a></p>";
				String safe = Jsoup.clean(unsafe, CrawlerUtils.whiteList);
		System.out.println(safe);
	}
}
