
package net.property.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.property.bean.location.LocationSuggestionBean;
import net.property.data.LocationDao;
import net.property.data.searchList.PropertyInfoDao;
import net.property.search.Results;
import net.property.search.Searcher;
import net.property.utils.GlobalConstants;
import net.property.web.transactionContext.PropertyListContext;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SearchController extends MultiActionController {
	protected static final Logger logger = 	Logger.getLogger(SearchController.class);
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * 
	 */
	public ModelAndView indexHandler(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			logger.info("inside index handler logger");
			System.out.println("inside index handler");
			PropertyInfoDao dao = PropertyInfoDao.getInstance();
			int total = dao.getTotalDocs();
			model.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(GlobalConstants.VERSION + "/index", "model",
				model);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * 
	 */
	/*	public ModelAndView searchHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Map<String,Object> model = new HashMap<String,Object>();
		// Possible search parameters
		// 
		// Location
		// Variable name: location / Parameter : l
		// 
		// Type - Whether for sale or rent 
		// Variable name: type -> Parameter -> t
		// Possible values: s -> Sale; r -> Rent
		
		// The default should go to UK search - ternary operator
		// String location = request.getParameter("l") != null ? request.getParameter("l") : "uk";
		
		// Default search type should be sale - ternary operator
		String type = request.getParameter("t") != null ? request.getParameter("t") : "s";
		
		String p = request.getParameter("p") != null ? request.getParameter("p") : "1";
		
		int page = 1;
		
		if(p.matches("\\d+")) {
			page = Integer.parseInt(p);
		}
		
		// Location ID in string
		// Default is England
		String sId = request.getParameter(Searcher.LOCATION_ID) != null ? request.getParameter(Searcher.LOCATION_ID) : "0";
		long locId = 0;
		
		// Location 
		if(sId.matches("\\d+")) {
			locId = Long.parseLong(sId);
		}
		
		String fLocations[] = {""};// = request.getParameterValues(Searcher.FILTER_LOCATION);
		
		if(request.getParameterValues(Searcher.FILTER_LOCATION) != null)
			fLocations = request.getParameterValues(Searcher.FILTER_LOCATION);
		
		String fLocation = fLocations[0];
		
		// Default location is england.
		if(fLocation.isEmpty())
			locId = 2;
		
		Location loc = LocationDB.getInstance().getNormalizedLocation(fLocation); //TODO fix
		
		if(locId > 0) {
			loc = LocationDB.getInstance().getLocation(locId);
		}
		
		// Sorting option
		// r = Default -> relevancy
		// d = Date
		// l -> Lowest price on top (Ascending)
		// h -> Highest price on top (Descending)
		String sort = request.getParameter("s") != null ? request.getParameter("s") : "r";
		
		// Possible filter options
		// Price
		// Location
		// Type -> Flat / House. Other are there as well like Bungalow, Detached, Maisonette etc
		// Last seen -> When the properly was found
		// Number of bedrooms
		// Real Estate Agent - Not being implemented
		// Also keep track of recent search.
		String fLowerPrice = request.getParameter(Searcher.FILTER_PRICE_LOWER) != null ? request.getParameter(Searcher.FILTER_PRICE_LOWER) : "0";
		String fUpperPrice = request.getParameter(Searcher.FILTER_PRICE_UPPER) != null ? request.getParameter(Searcher.FILTER_PRICE_UPPER) : "3000000";
		
		String fLevel = request.getParameter(Searcher.FILTER_LOCATION_LEVEL) != null ? request.getParameter(Searcher.FILTER_LOCATION_LEVEL) : "0";
		
		if(loc !=null && fLevel.equals("0")) {
			fLocations = loc.getLevels();
			fLevel = Integer.toString(loc.getLevel());
			fLocation = loc.getLocation(loc.getLevel());			
		}
		
		
		String fHouseType = request.getParameter(Searcher.FILTER_TYPE) != null ? request.getParameter(Searcher.FILTER_TYPE) : "";
		String fPosted = request.getParameter(Searcher.FILTER_POSTED) != null ? request.getParameter(Searcher.FILTER_POSTED) : "";
		String fNoRooms = request.getParameter(Searcher.FILTER_ROOMS) != null ? request.getParameter(Searcher.FILTER_ROOMS) : "";
		String fAgent = request.getParameter(Searcher.FILTER_AGENT) != null ? request.getParameter(Searcher.FILTER_AGENT) : "";
		
		// Page title. Really simple at the moment
		String pageTitle = "";
		if(type.equals("s")) {
			pageTitle = "Property for sale in " + fLocation;
			model.put("priceUpperLimit", Searcher.SELL_UPPER_LIMIT);
			model.put("priceStep", Searcher.SELL_STEP);
		} else {
			pageTitle = "Property to rent in " + fLocation;
			if(Long.toString(Searcher.SELL_UPPER_LIMIT).equals(fUpperPrice))
				fUpperPrice = Long.toString(Searcher.RENT_UPPER_LIMIT);
			model.put("priceUpperLimit", Searcher.RENT_UPPER_LIMIT);
			model.put("priceStep", Searcher.RENT_STEP);
		}
		
		model.put("page_title",pageTitle);
		
		int lowerPrice = 0;
		if(fLowerPrice.matches("\\d+"))
			lowerPrice = Integer.parseInt(fLowerPrice);
		int upperPrice = 0;
		if(fUpperPrice.matches("\\d+"))
			upperPrice = Integer.parseInt(fUpperPrice);
		
		Map<String,String> filters = new HashMap<String,String>();
		Map<String,String> filtersPage = new HashMap<String,String>();
		List<String> filtersApplied = new ArrayList<String>();
		
		if(!fLowerPrice.isEmpty() || !fUpperPrice.isEmpty()) {
			filtersPage.put(Searcher.FILTER_PRICE_LOWER, fLowerPrice);
			filtersPage.put(Searcher.FILTER_PRICE_UPPER, fUpperPrice);
		}		
		if(!fLocation.isEmpty()) {
			filters.put(Property.Fields.LOCATION.toString() + "_f_" + fLevel, fLocation);
			// filtersApplied.add(Searcher.FILTER_LOCATION);
			filtersPage.put(Searcher.FILTER_LOCATION, fLocation);
		}
		if(!fHouseType.isEmpty()) {
			filters.put(Property.Fields.HOUSE_TYPE.toString(), fHouseType);
			filtersApplied.add(Searcher.FILTER_TYPE);
			filtersPage.put(Searcher.FILTER_TYPE, fHouseType);
		}
		if(!fNoRooms.isEmpty()) {
			filters.put(Property.Fields.BEDROOMS.toString(), fNoRooms);
			filtersApplied.add(Searcher.FILTER_ROOMS);
			filtersPage.put(Searcher.FILTER_ROOMS, fNoRooms);
		}
		if(!fAgent.isEmpty()) {
			filters.put(Property.Fields.AGENT.toString(), fAgent);
			filtersApplied.add(Searcher.FILTER_AGENT);
			filtersPage.put(Searcher.FILTER_AGENT, fAgent);
		}
		
		long posted = 0;
		if(fPosted.matches("\\d+")) {
			posted = Long.parseLong(fPosted);
			filtersApplied.add(Searcher.FILTER_POSTED);
			filtersPage.put(Searcher.FILTER_POSTED, fPosted);
		}
		
		// Filter that will be displayed in the page if applied. 
		if(!filtersPage.isEmpty())
			model.put("filter_page",filtersPage);
		
		model.put("f_lower_price", getFormattedPrice(lowerPrice, type));
		model.put("f_upper_price", getFormattedPrice(upperPrice, type));
		
		model.put("lower_price", lowerPrice);
		model.put("upper_price", upperPrice);
		
		List<Filter> recentSearches = new ArrayList<Filter>();
		recentSearches.add(new Filter("Property to rent","",38394));
		recentSearches.add(new Filter("Property for sale","",38394));
		recentSearches.add(new Filter("Property to rent in london","",38394));
		recentSearches.add(new Filter("Property for sale in london","",38394));
		model.put("recent_searches", recentSearches);
		
		model.put("location", fLocation);
		model.put("type", type);
		
		if(fLocation.isEmpty())
			model.put("filtered_location", fLocation);
		else
			model.put("filtered_location", fLocation);
		
		String searchUrl = "search.html?t="+type;
		if(!"r".equals(sort))
			searchUrl = searchUrl+"&s="+sort;
		
		// Set to true if any filters other than price is applied.
		if(!filtersApplied.isEmpty())
			model.put("has_fitler", "true");
		
		model.put("searchUrl", searchUrl);
		
		Searcher searcher = new Searcher("GB");
		
		// variable location is now redundant.
		Results results = searcher.search(type, posted, lowerPrice, upperPrice, filters, page, "*", sort, fLocations);
		
		// There are matching properties
		if(results.getTotal() > 0) {
			// Some sample filters
			// Location Filter
			if(!filtersApplied.contains(Searcher.FILTER_LOCATION)) 
			model.put("location_filters", results.getLocationFilters());
			// model.put("location_filters", results.getLocationFilters("location_f_4"));
			
			// Type Filter
			if(!filtersApplied.contains(Searcher.FILTER_TYPE))
				model.put("type_filters", results.getTypeFilters());
			
			// Rooms Filter
			if(!filtersApplied.contains(Searcher.FILTER_ROOMS))
				model.put("rooms_filters", results.getRoomsFilters());
			
			// Posted Filter
			if(!filtersApplied.contains(Searcher.FILTER_POSTED))
				model.put("posted_filters", results.getPostedFilters());
			
			
			int subListSize = 10;
			if(results.getAgentFilters().size() < 10)
				subListSize = results.getAgentFilters().size(); 
			if(!filtersApplied.contains(Searcher.FILTER_AGENT))
				model.put("agent_filters", results.getAgentFilters().subList(0, subListSize));
			
			model.put("sort", sort);
			
			// model.put("agent_filters", results.getAgentFilters());
			
			model.put("results", results.getResults());
			
			// Current page
			model.put("page", results.getPage());
			
			// Total number of pages
			model.put("totalPages", results.getTotalPages());
			
			model.put("start", results.getStart());
			model.put("end", results.getEnd());
			
			model.put("startPage", results.getStartPage());
			model.put("endPage", results.getEndPage());			
		} else {
			// There were no matching property found
			model.put("error", "no_matching");
		}
		
		model.put("total", results.getTotal());
		
		return new ModelAndView(GlobalConstants.VERSION + "/search", "model", model);
	}
	*/
	public ModelAndView redirectHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Map<String,Object> model = new HashMap<String,Object>();
		logger.info("inside redirect handler logger");
		System.out.println("inside redirect handler");
		String ref = request.getParameter("ref");
		PropertyListContext context = new PropertyListContext(request);
		Searcher s = new Searcher(context);
		Results results = s.search(ref, "*");
		
		if(results.getResults().size() > 0) {
			model.put("url", results.getResults().get(0).getPropertyDetailsUrl());
			model.put("site", results.getResults().get(0).getSource());
		} else {
			return new ModelAndView(GlobalConstants.VERSION + "/redirect-error", "model", model);
		}
		
		return new ModelAndView(GlobalConstants.VERSION + "/redirect", "model", model);
	}
	
	public ModelAndView locationSuggestHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Map<String,Object> model = new HashMap<String,Object>();
		
		String query = request.getParameter("q")==null?"":request.getParameter("q");
		
		List<LocationSuggestionBean> locations = null;
		if (!query.isEmpty()) {
			locations = LocationDao.getInstance().getLocationSuggestions(query);
		}
		model.put("locations", locations);
		
		return new ModelAndView(GlobalConstants.VERSION + "/loc-suggest", "model", model);
	}
	
	private static String getFormattedPrice(int price, String type) {
		String priceString = Integer.toString(price);
	    if(price > 1000000) {
	    	int millPlace = price/1000000;
	    	int thouPlace = (price%1000000)/10000;
	    	if(price == Searcher.SELL_UPPER_LIMIT && type.equals("s"))
	    		return  millPlace +"."+ thouPlace +"M+";
	    	return millPlace +"."+ thouPlace +"M";
	    } else if (price > 1000) { // place a comma between
	    	int hundredPlace = (price%1000)/100;
	    	if(price == Searcher.RENT_UPPER_LIMIT && type.equals("r"))
	    		return (price/1000)+"." + hundredPlace + "k+";
	    	return (price/1000)+"." + hundredPlace + "k";
	    }
	    // divide and format
	    return priceString;
	}
	
	public static void main(String args[]) {
		
	}
}
