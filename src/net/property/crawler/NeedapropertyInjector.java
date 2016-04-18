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

public class NeedapropertyInjector {/*
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	
	public String site = "needaproperty.com";
	public String[] urls = {"http://www.needaproperty.com/property-search/location/east-midlands/lincolnshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/derbyshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/northamptonshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/nottinghamshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/leicestershire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/rutland/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/norfolk/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/suffolk/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/essex/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/cambridgeshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/hertfordshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/bedfordshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/london/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/northumberland/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/county-durham/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/tyne-and-wear/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/north-yorkshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/cumbria/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/lancashire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/cheshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/greater-manchester/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/shropshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/merseyside/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/hampshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/kent/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/oxfordshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/west-sussex/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/buckinghamshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/surrey/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/east-sussex/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/berkshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/isle-of-wight/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/somerset/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/devon/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/dorset/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/gloucestershire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/cornwall/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/wiltshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/worcestershire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/staffordshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/warwickshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/shropshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/herefordshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/west-midlands-(county)/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/gloucestershire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/northamptonshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/cheshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/north-yorkshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/south-yorkshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/lincolnshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/west-yorkshire/radius/40/type/to-rent/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/lincolnshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/derbyshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/northamptonshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/nottinghamshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/leicestershire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-midlands/rutland/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/norfolk/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/suffolk/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/essex/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/cambridgeshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/hertfordshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/bedfordshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/east-of-england/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/london/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/northumberland/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/county-durham/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/tyne-and-wear/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-east-england/north-yorkshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/cumbria/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/lancashire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/cheshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/greater-manchester/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/shropshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/merseyside/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/north-west-england/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/hampshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/kent/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/oxfordshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/west-sussex/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/buckinghamshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/surrey/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/east-sussex/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/berkshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/isle-of-wight/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-east-england/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/somerset/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/devon/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/dorset/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/gloucestershire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/cornwall/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/wiltshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/south-west-england/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/worcestershire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/staffordshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/warwickshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/shropshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/herefordshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/west-midlands-(county)/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/gloucestershire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/northamptonshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/west-midlands/cheshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/north-yorkshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/south-yorkshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/lincolnshire/radius/40/type/for-sale/page/1",
		"http://www.needaproperty.com/property-search/location/yorkshire-and-the-humber/west-yorkshire/radius/40/type/for-sale/page/1"
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
			int jobsperpage = 10;
			String paginginfo = CrawlerUtils.getText(content,".total");
			Matcher m = Pattern.compile("(?ius)of ([0-9,]+)").matcher(paginginfo);
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

			if (pages > 200) {
				pages = 200;
			} 
			for (int i=2; i < pages; i++) {
				url = url.replaceAll("(?<=/page/)(\\d+)", String.valueOf(i));
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
		for (Element e : CrawlerUtils.getElements(content,"[itemtype=http://schema.org/Residence]")) {
			String itemLink = e.select("h2 a").attr("href");
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
			
			String location = "";
			String title = e.select("h2 a").text();
			Pattern locationPattern = Pattern.compile("in (.*?)");
			Matcher locMatcher = locationPattern.matcher(title);
			if (locMatcher.find()) {
				location = locMatcher.group(1);
			}
			
			if (location.isEmpty()) {
				location = e.select("[itemtype=http://schema.org/PostalAddress]").text();
			} else {
				location += "," + e.select("[itemtype=http://schema.org/PostalAddress]").text();
			}
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}
			
			String streetaddress = e.select("[itemtype=http://schema.org/PostalAddress]").text();
			String[] stTokens = streetaddress.split(",");
			if(stTokens.length > 0){
				Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
				if(area.getId()==-1)
					item.setLocation(stTokens[0], 100);
			} else {	
				item.setLocation(streetaddress, 100);
			}
			
			//Photo
			String photo = e.select("[itemprop=image] > img").attr("src");
			
			try {
				new URL(photo);
			} catch (MalformedURLException mui) {
				mui.printStackTrace();
			}
			
			// Image and extension
			String ext = CrawlerUtils.saveImage(item.getRef(),photo);
			item.setImageType(ext);
			
			//Agent
			String agent = e.select(".agent img").attr("alt");
			agent = agent.replaceAll(" - Estate Agents", "").trim();
			item.setAgent(agent);	
			
			//Price
			String price = e.select("[itemprop=price]").text();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);

			//Description
			String detailPageContent = HttpUtils.getHTMLcontent(itemLink);
			Document doc = CrawlerUtils.getDocument(detailPageContent);
			String propDesc = CrawlerUtils.getHtml(doc, ".description-text");
			item.setDescription(propDesc);
			
			//House type
			String houseType = title;
			houseType = CrawlerUtils.getHouseType(title);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(CrawlerUtils.getText(doc, ".description-text"));
				item.setHouseType(houseType);
			} 
			item.setHouseType(houseType);
			
			//Bedrooms
			String bedrooms = title;
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				bedrooms = CrawlerUtils.getBedRooms(CrawlerUtils.getText(doc, ".description-text"));
			} 
			item.setBedrooms(bedrooms);
			
			//Ad-Type
			if (itemLink.contains("for-sale")) {
				item.setAdType("s");
			} else if (url.toString().contains("to-rent")) {
				item.setAdType("r");
			}
		
			//Features
			Set<String> features = CrawlerUtils.getFeatureType(CrawlerUtils.getText(doc, ".description-text"));
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}
			
			//Image count
			int images = CrawlerUtils.getElements(doc,"[property=og:image]").size();
			item.setImages(images);

			item.setRegion("GB");
			item.setSource(site);
			
			// Zipcode
			String zipcode = CrawlerUtils.getElements(doc,"[property=og:postal-code]").attr("content");
			item.setZipcode(zipcode);

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
