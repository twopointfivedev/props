package net.property.crawler;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.property.data.Location;
import net.property.data.LocationDB;
import net.property.data.Property;
import net.property.data.SearchDataManager;
import net.property.utils.CrawlerUtils;
import net.property.utils.HttpUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NethousepricesInjector {/*


	// Checks to stop infinite looping
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	private int fails = 0;
	
	private String site = "nethouseprices.com";
	private String urls[] = {
		"http://www.nethouseprices.com/index.php?con=property_results&search=London&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=London&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Bristol&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Bristol&order=date_desc&no_of_results=100&area=rent&page=1",		
		"http://www.nethouseprices.com/index.php?con=property_results&search=Birmingham&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Birmingham&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Liverpool&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Liverpool&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Manchester&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Manchester&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Leeds&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Leeds&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Nottingham&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Nottingham&order=date_desc&no_of_results=100&area=rent&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Newcastle%20Upon%20Tyne&order=date_desc&no_of_results=100&area=sale&page=1",
		"http://www.nethouseprices.com/index.php?con=property_results&search=Newcastle%20Upon%20Tyne&order=date_desc&no_of_results=100&area=rent&page=1"
		
	};
		
	public NethousepricesInjector() {
		
	}
	
	public NethousepricesInjector(String site, String[] urls) {
		this.site = site;
		this.urls = urls;
	}
	
	public void start() {
		for (String url: urls) {
			processSite(url);
		}
	}
	
	private void processSite(String url) {
		try {
			String content = "";

			System.out.println("Processing site: " + site);

			content = HttpUtils.getHTMLcontent(url);

			processPage(content, new URI(url));			

			for (int i=2; i < 50; i++) {
				url = url.replaceAll("(?<=page=)(\\d+)", String.valueOf(i));
				content = HttpUtils.getHTMLcontent(url);

				processPage(content, new URI(url));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 
	 * @param content
	 * @param url
	 * @return false when no job links are found on the page
	 *//*
	public boolean processPage(String content, URI url) {
		int itemsFound = 0;
		int newItems = 0;
		currentPageItems.clear();
		for (Element e : CrawlerUtils.getElements(content,"#ListView table tr tr")) {
			String itemLink = e.select(".detailLinks a:contains(More Detail)").attr("href");
			itemLink = itemLink.replaceAll("(.*?)&return_url.*","$1");
			try {
				itemLink = itemLink.replaceAll(" ", "%20");
				itemLink = itemLink.replaceAll("\t", "%09");
				itemLink = url.resolve(itemLink).toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (itemLink.isEmpty())
				continue;

			Property item = new Property();

			// URL
			item.setUrl(itemLink);
			currentPageItems.add(item.getRef());

			itemsFound++;

			if (!previousPageItems.contains(item.getRef())) {
				newItems++;
			}

			// check for existing items
			if (SearchDataManager.getInstance().hasItem(item.getRef())) {	
				continue;
			}
			
			String location = e.select("h3 a:eq(2),h3 a:eq(3)").text();
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}
			
			String streetaddress = CrawlerUtils.getTextAdvanced(e,	"h3 a:eq(2),h3 a:eq(3)");
			streetaddress = streetaddress.replaceAll("Brand New 2 Bedroom Flat For Sale Westbourne Drive", "Westbourne Drive");
			Matcher m = Pattern.compile("([^,]{1,30}),.*").matcher(streetaddress);
			if (m.find()) {
				try {
					streetaddress = m.group(1);
					String[] stTokens = streetaddress.split(",");
					if(stTokens.length > 0){
						Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
						if(area.getId()==-1)
							item.setLocation(stTokens[0], 100);
					}else{	
						item.setLocation(streetaddress, 100);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			//Photo
			String photo = e.select(".property_image_container img").attr("src");
			
			try {
				new URL(photo);
			} catch (MalformedURLException mui) {
				// TODO Auto-generated catch block
				// mui.printStackTrace();
				photo = "http://" + url.getHost() + photo;
			}
			
			// Ignore if the image contains noimage -> Probably photos are missing.
			if(!photo.contains("noimage")) {
				String ext = CrawlerUtils.saveImage(item.getRef(),photo);
				item.setImageType(ext);
			}

			//Description
			String description = HttpUtils.getHTMLcontent(itemLink);
			Elements descMarkup = new Elements();
			Document doc = CrawlerUtils.getDocument(description);
			descMarkup.addAll(CrawlerUtils.getElements(doc, ".propertyContent"));
			descMarkup.select("#gallery").remove();		
			descMarkup.select("p:contains(First listed on)").remove();	
			descMarkup.select("div:contains(Interested in this property)").remove();	
			String propDesc = CrawlerUtils.getSafeHtml(descMarkup.outerHtml());
			item.setDescription(propDesc);
			
			//Agent
			String agent = CrawlerUtils.getText(description, ".agentName");
			item.setAgent(agent);
			
			//House type
			String houseType = "";
			houseType = CrawlerUtils.getTextAdvanced(e,	"h3");
			houseType = CrawlerUtils.getHouseType(houseType);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(descMarkup.text());
				item.setHouseType(houseType);
			} else {
				item.setHouseType(houseType);
			}
			
			//Bedrooms
			String bedrooms = "";
			bedrooms = CrawlerUtils.getTextAdvanced(e, "h3");
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				CrawlerUtils.getBedRooms(descMarkup.text());
			} else {
				item.setBedrooms(bedrooms);
			}
			//Price
			String price = CrawlerUtils.getTextAdvanced(e, "h3 span");
			price = price.replaceAll(",", "").trim();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);
			//Ad-Type
			if (url.toString().contains("sale")) {
				item.setAdType("s");
			} else if (url.toString().contains("rent")) {
				item.setAdType("r");
			}
		
			//Features
			Set<String> features = CrawlerUtils.getFeatureType(description);
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}
			
			//Image count
			int images = CrawlerUtils.getElements(description,"#photos img").size();
			item.setImages(images);

			item.setRegion("GB");
			item.setSource(site);
			
			String location = CrawlerUtils.getElements(description,"[property=og:locality]").attr("content");
			item.setLocation(location, 4);

			String zipcode = CrawlerUtils.getElements(description,"[name=postcode]").attr("value");
			item.setZipcode(zipcode);

			// COMMIT JOB
			SearchDataManager.getInstance().addItem(item);
		}

		// Check for sites with issues. We will break after few attempts.
		previousPageItems = new HashSet<String>(currentPageItems);
		if (itemsFound == 0 || newItems == 0) {
			return false;
		}else{
			return true;
		}
	}

*/}
