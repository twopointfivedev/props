package net.property.web.viewContext;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.property.bean.location.GeoLocationBean;
import net.property.bean.location.LocationBean;
import net.property.data.Location;
import net.property.data.LocationDB;
import net.property.search.Results;
import net.property.search.ResultsContainer;
import net.property.search.Searcher;
import net.property.utils.Pair;
import net.property.web.transactionContext.PropertyListContext;
/**
 * Sets the values that would be visible in the view
 * @author Saurabh
 *
 */
public class PropertyListViewContext {

	private PropertyListContext propertyListContext;
	private Map<String, Object> model;

	public PropertyListViewContext(PropertyListContext propertyListContext,
			Map<String, Object> model) {
		this.propertyListContext = propertyListContext;
		this.model = model;
	}
	
	public void setTitleAndPriceLimits(String fLocation) {
		// Page title. s
		String pageTitle = "";
		if (propertyListContext.getType() == 2) {
			
			if(fLocation==null || fLocation.isEmpty()){
				pageTitle = "Properties for sale";
			}
			else{
			pageTitle = "Properties for sale in " + fLocation;
			}
			model.put("priceUpperLimit", Searcher.SELL_UPPER_LIMIT);
			model.put("priceStep", Searcher.SELL_STEP);
			model.put("rent_range", getSaleRange());
		} else {
			if(fLocation==null || fLocation.isEmpty()){
				pageTitle = "Properties for rental";
			}
			else{
			pageTitle = "Properties for rental in " + fLocation;
			}
			model.put("priceUpperLimit", Searcher.RENT_UPPER_LIMIT);
			model.put("priceStep", Searcher.RENT_STEP);
			model.put("rent_range", getRentRange());
		}

		model.put("page_title", pageTitle);

	}
	
	public void setFilterLimits() {//TODO 
//		model.put("areaStep", Searcher.AREA_STEP);
//		model.put("", );
	}

	private List<Pair> getRentRange() {
		// TODO Auto-generated method stub
		List<Pair> range = new ArrayList<Pair>();
		int step = 2500;
		range.add(new Pair(0,"0"));
		for(int i=Pair.rentMinRange;i<=Pair.rentMaxRange; ){
			float f = (float)i/1000;
			DecimalFormat df = new DecimalFormat("###.#");
			range.add(new Pair(i, (df.format(f) +"k") ));
			if(i==10000){
				step= 5000;
			}
			else if(i==30000){
				step=30000;
			}
			else if(i==60000){
				step=40000;
			}
			i+=step;
		}
		return range;
	}
	
	private List<Pair> getSaleRange() {
		// TODO Auto-generated method stub
		List<Pair> range = new ArrayList<Pair>();
		int step = 1000000;
		for(int i=Pair.saleMinRange;i<=Pair.saleMaxRange; ){
			float f = (float)i/1000;
			DecimalFormat df = new DecimalFormat("###.#");
			range.add(new Pair(i, (df.format(f) +"M" )));
			if(i==10000000){
				step= 10000000;
			}
			else if(i==30000000){
				step=20000000;
			}
			i+=step;
		}
		return range;
	}

	public void handleFilters(List<String> filtersApplied, Map<String, String> filtersPage) {
		handlePriceFilter(filtersApplied, filtersPage);
		handleAreaFilters(filtersApplied, filtersPage);
		handleHouseTypefilter(filtersApplied, filtersPage);
		handleBedroomFilter(filtersApplied, filtersPage);
		handleAgentFilter(filtersApplied, filtersPage);
		handlePostDateFilter(filtersApplied, filtersPage);
		if (!filtersPage.isEmpty())
			model.put("filter_page", filtersPage);
		if (!filtersApplied.isEmpty())
			model.put("has_fitler", "true");
	}

	private void handlePostDateFilter(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		if (propertyListContext.getfPosted() > 0) {
			filtersApplied.add(Searcher.FILTER_POSTED);
			filtersPage.put(Searcher.FILTER_POSTED,
					Long.toString(propertyListContext.getfPosted()));
		}
	}

	//TODO implement this
	private void handleAgentFilter(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		if (!(propertyListContext.getfAgent().isEmpty())) { // TODO impmlement this
			// filters.put(Property.Fields.AGENT.toString(),
			// context.getfAgent());
			filtersApplied.add(Searcher.FILTER_AGENT);
			filtersPage.put(Searcher.FILTER_AGENT, propertyListContext.getfAgent());
		}
		
	}

	private void handleBedroomFilter(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		if (!propertyListContext.getfNoRooms().isEmpty()) {
			filtersApplied.add(Searcher.FILTER_ROOMS);
			filtersPage.put(Searcher.FILTER_ROOMS, propertyListContext.getfNoRooms());
		}
		
	}

	private void handleHouseTypefilter(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		if (!propertyListContext.getfHouseType().isEmpty()) {// TODO fix this
			// filters.put(Property.Fields.HOUSE_TYPE.toString(),
			// context.getfHouseType()); //TODO change property class. It will
			// be
			// removed
			filtersApplied.add(Searcher.FILTER_TYPE);
			filtersPage.put(Searcher.FILTER_TYPE, propertyListContext.getfHouseType());
		}
		
	}

	public String handleLocationFilter(List<String> filtersApplied,
			Map<String, String> filtersPage, LocationBean geoLoc) {
		if(geoLoc!=null && geoLoc.getId()>0){
			filtersPage.put(Searcher.FILTER_LOCATION, geoLoc.getNeighbourhood());
			model.put("location", geoLoc.getNeighbourhood());
			model.put("filtered_location", geoLoc.getNeighbourhood());
		}
		return geoLoc.getNeighbourhood();
	}

	//TODO refactor this method
	private void handleAreaFilters(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		String showInFilterPageMin = "Min";
		String showInFilterPageMax = "Max";
		if (propertyListContext.getfLowerGrossArea()>=0) {
			showInFilterPageMin = Integer.toString(propertyListContext.getfLowerGrossArea());
			filtersPage.put(Searcher.FILTER_GROSS_AREA, showInFilterPageMin
					+ " TO " + showInFilterPageMax);
			filtersApplied.add(Searcher.FILTER_GROSS_AREA);
		}
		
		if (propertyListContext.getfUpperGrossArea()>0) {
			showInFilterPageMax = Integer.toString(propertyListContext.getfUpperGrossArea());
			filtersPage.put(Searcher.FILTER_GROSS_AREA, showInFilterPageMin
					+ " TO " + showInFilterPageMax);
			if (!filtersApplied.contains(Searcher.FILTER_GROSS_AREA)) {
				filtersApplied.add(Searcher.FILTER_GROSS_AREA);
			}
		}
		if (filtersApplied.contains(Searcher.FILTER_GROSS_AREA)) {
			model.put("grossarea_filter", true);
		}

		String showSaleableInFilterPageMin = "Min";
		String showSaleableInFilterPageMax = "Max";
		if (propertyListContext.getfLowerSaleableArea()>0) {
			showSaleableInFilterPageMin = Integer.toString(propertyListContext.getfLowerSaleableArea());
			filtersPage.put(Searcher.FILTER_SALEABLE_AREA,
					showSaleableInFilterPageMin + " TO "
							+ showSaleableInFilterPageMax);
			filtersApplied.add(Searcher.FILTER_SALEABLE_AREA);
		}
		if (propertyListContext.getfUpperSaleableArea()>0) {
			showSaleableInFilterPageMax = Integer.toString(propertyListContext.getfUpperSaleableArea());
			filtersPage.put(Searcher.FILTER_SALEABLE_AREA,
					showSaleableInFilterPageMin + " TO "
							+ showSaleableInFilterPageMax);
			if (!filtersApplied.contains(Searcher.FILTER_SALEABLE_AREA)) {
				filtersApplied.add(Searcher.FILTER_SALEABLE_AREA);
			}
		}
		if (filtersApplied.contains(Searcher.FILTER_SALEABLE_AREA)) {
			model.put("saleablearea_filter", true);
		}
	}

	private void handlePriceFilter(List<String> filtersApplied,
			Map<String, String> filtersPage) {
		int lowerPrice = 0;
		String showMinPriceInFilterPage = "min";
		String showMaxPriceInFilterPage = "max";
	//	if (context.getfLowerPrice().matches("\\d+")) {
		//	lowerPrice = Integer.parseInt(context.getfLowerPrice());
			if (propertyListContext.getfLowerPrice() > 0) {
				showMinPriceInFilterPage = Integer.toString(propertyListContext.getfLowerPrice());
				filtersPage.put(Searcher.FILTER_PRICE, showMinPriceInFilterPage
						+ " TO " + showMaxPriceInFilterPage);
				filtersPage.put(Searcher.FILTER_PRICE_LOWER,
						Integer.toString(propertyListContext.getfLowerPrice()));
				filtersApplied.add(Searcher.FILTER_PRICE);
				lowerPrice = propertyListContext.getfLowerPrice();
			}
			model.put("f_lower_price", lowerPrice);
	//	}
		int upperPrice = (int) Searcher.RENT_UPPER_LIMIT;
	//	if (context.getfUpperPrice().matches("\\d+")) {
		//	upperPrice = Integer.parseInt(context.getfUpperPrice());
			if (propertyListContext.getfUpperPrice() >0) {
				showMaxPriceInFilterPage = Integer.toString(propertyListContext.getfUpperPrice());
				if (!filtersApplied.contains(Searcher.FILTER_PRICE)) {
					filtersApplied.add(Searcher.FILTER_PRICE);
				}
				filtersPage.put(Searcher.FILTER_PRICE, showMinPriceInFilterPage
						+ " TO " + showMaxPriceInFilterPage);
				filtersPage.put(Searcher.FILTER_PRICE_UPPER,
						Integer.toString(propertyListContext.getfUpperPrice()));
				upperPrice = propertyListContext.getfUpperPrice();
			}
			model.put("f_upper_price", upperPrice);
			model.put("upper_price", upperPrice);
			model.put("lower_price", lowerPrice);		
	}

	@Deprecated
	public void setSearchUrl() {
		String searchUrl = "search.html?t=" + propertyListContext.getType();
		if (!"r".equals(propertyListContext.getSort()))
			searchUrl = searchUrl + "&s=" + propertyListContext.getSort();
		model.put("searchUrl", searchUrl);
		
	}

	public void handleFilterResults(ResultsContainer results, List<String> filtersApplied) {
		if (results.getTotal() > 0) {


			// Some sample filters
			// Location Filter
			/* if(!filtersApplied.contains(Searcher.FILTER_LOCATION)) */
		//	model.put("location_filters", results.getLocationFilters());
			// model.put("location_filters",
			// results.getLocationFilters("location_f_4"));

			// Type Filter
/*			if (!filtersApplied.contains(Searcher.FILTER_TYPE))
				model.put("type_filters", results.getTypeFilters());

			// Rooms Filter
			if (!filtersApplied.contains(Searcher.FILTER_ROOMS))
				model.put("rooms_filters", results.getRoomsFilters());*/

			/*
			 * if (!filtersApplied.contains(Searcher.FILTER))
			 * model.put("gross_area_filters", results.getRoomsFilters());
			 */

			// Posted Filter
//			if (!filtersApplied.contains(Searcher.FILTER_POSTED))
//				model.put("posted_filters", results.getPostedFilters());
			
			// Agents filter
/*			int subListSize = 10;
			if (results.getAgentFilters().size() < 10)
				subListSize = results.getAgentFilters().size();
			if (!filtersApplied.contains(Searcher.FILTER_AGENT))
				model.put("agent_filters",
						results.getAgentFilters().subList(0, subListSize));*/
			
		
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
	}

	public void setGeneralParams() {
		model.put("sort", propertyListContext.getSort());
		
	}

	public static void main(String args[]){
		PropertyListViewContext plc = new PropertyListViewContext(null, null);
		List<Pair> pairs = plc.getRentRange();
		for(Pair p: pairs){
			System.out.println("key:"+p.getKey()+" value:"+p.getValue());
		}
	//	System.out.println(plc.getRentRange());
	}
}
