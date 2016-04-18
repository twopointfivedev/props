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

public class LettingwebInjector {/*
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	
	public String site = "lettingweb.com";
	public String[] urls = {"http://www.lettingweb.com/flats-to-rent/perth-kinross?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/glasgow?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/aberdeen?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/dundee?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/stirling?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/stirling?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/inverness-loch-ness-and-nairn/inverness?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/falkirk?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/manchester?currentPage=1&perpage=100",
			"http://www.lettingweb.com/flats-to-rent/perth-kinross?currentPage=1&perpage=100",
		};
	
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
			String paginginfo = CrawlerUtils.getText(content,"#MainContent_normalPageLocationHeader");
			Matcher m = Pattern.compile("(?ius)([0-9,]+) properties").matcher(paginginfo);
			if (m.find()) {
				try {
					String pageCount = m.group(1).replaceAll("(?ius)[,.\u00A0\\s]", "").trim();
					if(pageCount.matches("\\d+")){
						pages = Integer.parseInt(pageCount);
						pages = (int) Math.ceil(pages / (float) jobsperpage);
					} else { 
						pages = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (pages > 10) {
				pages = 10;
			} 
			for (int i=2; i < pages; i++) {
				url = url.replaceAll("(?<=currentpage=)(\\d+)", String.valueOf(i));
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
		for (Element e : CrawlerUtils.getElements(content,"[itemtype=http://schema.org/Residence/offer]")) {
			String itemLink = e.select("[itemprop=offers] a").attr("href");
			itemLink = itemLink.replaceAll("\\?path=.*$", "");
			try {
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
			
			String location = e.select("[id~=_addressLink]").attr("title");
			location = location.replaceAll("View details of ", "");
			
			location += "," + e.select("[itemprop=addressRegion]").attr("content");
			location += "," + e.select("[itemprop=addressLocality]").attr("content");
			
			
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}
			
			// Street address
			String streetaddress = e.select("[id~=_addressLink]").attr("title");
			streetaddress = streetaddress.replaceAll("View details of ", "");
			String[] stTokens = streetaddress.split(",");
			if(stTokens.length > 0){
				Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
				if(area.getId()==-1)
					item.setLocation(stTokens[0], 100);
			}
			
			// Zipcode
			String zipcode = e.select("[itemprop=postalCode]").attr("content");
			if (loc.getZipcode().length() > zipcode.length()) {
				item.setZipcode(loc.getZipcode());
			} else {
				item.setZipcode(zipcode);
			}
						
			//Photo
			String photo = e.select("[itemprop=image]").attr("src");
			
			try {
				new URL(photo);
			} catch (MalformedURLException mui) {
				//mui.printStackTrace();
			}
			
			// Image and extension
			String ext = CrawlerUtils.saveImage(item.getRef(),photo);
			item.setImageType(ext);
			
			//Image count
			String imagesText = e.select("[id~=_fullDetailsLink]").text();
			Matcher m = Pattern.compile("(\\d+) photos").matcher(imagesText);
			if (m.find()) {
				int images = Integer.parseInt(m.group(1));
				item.setImages(images);
			}
			
			//Agent
			String agent = e.select("[id~=agentLogo] img").attr("alt");
			agent = agent.replaceAll(" - Estate Agents", "").trim();
			item.setAgent(agent);	
			
			//Price
			String price = e.select("[itemprop=offers]").text();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);

			//Description
			String detailPageContent = HttpUtils.getHTMLcontent(itemLink);
			Document doc = CrawlerUtils.getDocument(detailPageContent);
			String propDesc = CrawlerUtils.getHtml(doc, "[itemprop=description]");
			item.setDescription(propDesc);
			
			//House type
			String houseType = doc.select("#property-title h2").text();
			houseType = CrawlerUtils.getHouseType(houseType);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(CrawlerUtils.getText(doc, "[itemprop=description]"));
				item.setHouseType(houseType);
			} 
			item.setHouseType(houseType);
			
			//Bedrooms
			String bedrooms = doc.select("#property-title h2").text();
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				bedrooms = CrawlerUtils.getBedRooms(CrawlerUtils.getText(doc, "[itemprop=description]"));
			} 
			item.setBedrooms(bedrooms);
			
			//Ad-Type
			item.setAdType("r");
					
			//Features
			Set<String> features = CrawlerUtils.getFeatureType(CrawlerUtils.getText(doc, ".features"));
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}
			
			item.setRegion("GB");
			item.setSource(site);
			
			// COMMIT Property
			SearchDataManager.getInstance().addItem(item);
		}

		// Check for sites with issues. We will break after few attempts.
		previousPageItems = new HashSet<String>(currentPageItems);
		if (itemsFound == 0 || newItems == 0) {
			return false;
		} else {
			return true;
		}
	}
*/}
