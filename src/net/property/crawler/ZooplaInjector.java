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

import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class ZooplaInjector {/*
	// Checks to stop infinite looping
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	private int fails = 0;
	
	private String site = "zoopla.co.uk";
	private String urls[] = {
		"http://www.zoopla.co.uk/for-sale/property/england/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/to-rent/property/england/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/for-sale/property/scotland/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/to-rent/property/scotland/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/for-sale/property/wales/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/to-rent/property/wales/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/for-sale/property/northern-ireland/?&pn=1&page_size=100&results_sort=newest_listings",
		"http://www.zoopla.co.uk/to-rent/property/northern-ireland/?&pn=1&page_size=100&results_sort=newest_listings"

	};
		
	public ZooplaInjector() {
		
	}
	
	public ZooplaInjector(String site, String[] urls) {
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
			int pages = 0;
			int jobsperpage = 100;
			String paginginfo = CrawlerUtils.getText(content,".result-count");
			Matcher m = Pattern.compile("(?ius).*?of ([0-9,]+).*").matcher(paginginfo);
			if (m.find()) {
				try {
					String pageCount = m.group(1).replaceAll("(?ius)[,.\u00A0\\s]", "").trim();
					if(pageCount.matches("\\d+")){
						pages = Integer.parseInt(pageCount);
						pages = (int) Math.ceil(pages / (float) jobsperpage);
					}else {
						pages = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i=2; i < 100; i++) {
				url = url.replaceAll("(?<=pn=)(\\d+)", String.valueOf(i));
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
		for (Element e : CrawlerUtils.getElements(content,"ul.listing-results li[id^=listing]")) {
			String itemLink = e.select(".listing-results-right h2 a,.pl-list-res-r a.pl-list-res-address").attr("href");
			itemLink = itemLink.replaceAll("(.*?)search_identifier.*","$1");
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
			
			String location = e.select("[itemprop=address],.pl-list-res-address").text();
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}
			
			String streetaddress = e.select(".pl-list-res-address,[itemprop=streetAddress]").text();
			String[] stTokens = streetaddress.split(",");
			if (stTokens.length > 0) {
				Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
				if (area.getId()==-1) {
					item.setLocation(stTokens[0], 100);
				}
			} else {
				item.setLocation(streetaddress, 100);
			}
			
			//Photo
			String photo = e.select("[itemprop=photo],.photo-hover-pl img").attr("src");
			
			try {
				new URL(photo);
				String ext = CrawlerUtils.saveImage(item.getRef(),photo);
				item.setImageType(ext);
			} catch (MalformedURLException mui) {
			
			}

			//Description
			String description = HttpUtils.getHTMLcontent(itemLink);
			Elements descMarkup = new Elements();
			Document doc = CrawlerUtils.getDocument(description);
			descMarkup.addAll(CrawlerUtils.getElements(doc, "[itemprop=description],div#tab-details"));
			descMarkup.select("#images").remove();		
			descMarkup.select("#interested-1").remove();	
			descMarkup.select(".social-media-share-buttons").remove();	
			String propDesc = CrawlerUtils.getSafeHtml(descMarkup.outerHtml());
			if (propDesc.isEmpty()) {
				// markup is broken in homes and property site.
				// [itemprop=description] comesup as <pitemprop="description"> This is a fix for that
				propDesc = CrawlerUtils.getHtml(description, "h3:matches(Property description) + *");
			}
			item.setDescription(propDesc);
			
			//Agent
			String agent = CrawlerUtils.getText(description, ".sidebar strong a[href^=/find-agents],.sidebar strong a[href~=developers]");
			item.setAgent(agent);
			
			//House type
			String houseType = "";
			houseType = CrawlerUtils.getTextAdvanced(e,	".listing-results-right h2 a,.pl-list-res-r .pl-list-res-attr");
			houseType = CrawlerUtils.getHouseType(houseType);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(descMarkup.text());
				item.setHouseType(houseType);
			} else {
				item.setHouseType(houseType);
			}
			
			//Bedrooms
			String bedrooms = "";
			bedrooms = CrawlerUtils.getTextAdvanced(e, ".listing-results-right h2 a,.pl-list-res-r .pl-list-res-attr");
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				CrawlerUtils.getBedRooms(descMarkup.text());
			} else {
				item.setBedrooms(bedrooms);
			}
			//Price
			String price = CrawlerUtils.getText(description, ".listing-details-price");
			price = price.replaceAll(",", "").trim();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);
			
			//Ad-Type
			if (url.toString().contains("for-sale")) {
				item.setAdType("s");
			} else if (url.toString().contains("to-rent")) {
				item.setAdType("r");
			}
			if (site.equals("smartnewhomes.com")) {
				item.setAdType("s");
			}
		
			//Features
			Set<String> features = CrawlerUtils.getFeatureType(description);
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}
			
			//Image count
			int images = CrawlerUtils.getElements(description,"[property=og:image]").size();
			item.setImages(images);

			item.setRegion("GB");
			item.setSource(site);
			
			String location = CrawlerUtils.getElements(description,"[property=og:locality]").attr("content");
			item.setLocation(location, 4);

			String zipcode = CrawlerUtils.getElements(description,"[property=og:postal-code]").attr("content");
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
