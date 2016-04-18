

package net.property.search;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.property.bean.location.LocationBean;
import net.property.data.PropertiesForIndexingDao;
import net.property.data.Property;
import net.property.utils.DateUtil;
import net.property.web.transactionContext.PropertyListContext;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;



@Deprecated
public class Searcher {
	
//	private static final List<String> lowerBoostedSites =  Arrays.asList("homesandproperty.co.uk","zoopla.co.uk", "primelocation.com");
	private String location;
	private String region;
	
	public final static int PAGE_SIZE = 10;
	
	public final static int FACET_LIMIT = 100;
	
	public final static String LOCATION_ID = "id";
	
	// List of filter that can be applied
	//TODO move these to Filter.java  class
	public final static String FILTER_LOCATION = "f_l";
	public final static String FILTER_LOCATION_LEVEL = "f_l_l";
	public final static String FILTER_TYPE = "f_t";
	public final static String FILTER_ROOMS = "f_r";
	public final static String FILTER_POSTED = "f_p";
	public final static String FILTER_AGENT = "f_a";
	public final static String FILTER_PRICE = "f_price";
	public final static String FILTER_PRICE_LOWER = "f_pl";
	public final static String FILTER_PRICE_UPPER = "f_pu";
	public final static String FILTER_GROSS_AREA = "f_ga";
	public final static String FILTER_GROSS_AREA_UPPER = "f_gau";
	public final static String FILTER_GROSS_AREA_LOWER = "f_gal";
	public final static String FILTER_SALEABLE_AREA = "f_sa";
	public final static String FILTER_SALEABLE_AREA_UPPER = "f_sau";
	public final static String FILTER_SALEABLE_AREA_LOWER = "f_sal";
	public final static String FILTER_SORT = "f_sort";
	
	// Limits related to Price filter
	public final static long SELL_UPPER_LIMIT = 3000000l;
	public final static long SELL_STEP = 50000l;
	public final static long RENT_UPPER_LIMIT = 200000l;
	public final static long RENT_STEP = 1000l;
	public final static int AREA_STEP = 200;
	
	/** Sort filters*/
	public static final String SORT_PRICE_LOW = "l";
	public static final String SORT_PRICE_HIGH = "h";
	
	//SEARCH params
	public final static String PROPERTY_TYPE="t";
	public final static String PAGE="p";
	
	// Used for storing the current location filter and the level
	private String filterLoc = null;
	
	private String filterLocLevel = "1";
	
	
	
	// The following are the list of location field that will be used for filtering 
	// The number of location fields are well dynamic so based on region different location values will be set.
	private int locationSize = 4;
	private PropertyListContext propertyListContext;
	
	public Searcher(PropertyListContext context) {
		this.propertyListContext = context;
	}


	/**
	 * @param params
	 * @return
	 */
	private Results search(SolrParams params) {
		QueryResponse response = new QueryResponse();
		Results results = null;
		try {
			SolrClient server = SolrServerHolder.getNormalSolrServer();
			response = server.query(params,METHOD.POST);
			results = new Results(response, this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return results; 
	}
	
	
	/**
	 * 
	 * @param q
	 * @param region
	 * @param p
	 * @param fields
	 * @return
	 */
	public Results search(PropertyListContext propertyListContext,
			List<String> filtersApplied, Map<String, String> filters, String fields,
			LocationBean loc) {
		
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("defType", "edismax");
		params.set("rows", PAGE_SIZE);
		setQ(params,loc);
		
		//TODO below is the handler for multi-locations
		/*for(int i=1; i<=locationSize; i++) {
			if(locationFields.isEmpty())
				locationFields = Property.Fields.LOCATION.toString() + "_" + i;
			else
				locationFields = locationFields + " " + Property.Fields.LOCATION.toString() + "_" + i;
		}
		
		params.set("qf", locationFields);*/
		setSort(params, propertyListContext);
		
		// Mandatory Filters
		// region and ad_type will always be used
		
		
	//	params.add("fq", Property.Fields.REGION.toString() + ":" + region); //TODO fix this later
		
		params.set("start", (propertyListContext.getPage()-1) * PAGE_SIZE);
		
	    /*String lowerBoostedQuery = "(source:* ";
        for(String s:lowerBoostedSites) {
        	lowerBoostedQuery = lowerBoostedQuery + " -source:" + s;
        }
        lowerBoostedQuery = lowerBoostedQuery + ")^2.0";
        params.add("bq", lowerBoostedQuery);*/
        
		applyFilters(params,filtersApplied, propertyListContext);
		setupFilters(params);
		 
		params.set("fl", fields);
        
		Results results = search(params);
		return results;
	}
	
	
	private void setSort(ModifiableSolrParams params,
			PropertyListContext propertyListContext2) {
		// Sorting
				// r = Default -> relevancy
				// d = Date
				// l -> Lowest price on top (Ascending)
				// h -> Highest price on top (Descending)
//				if("d".equals(sort)) {
//					params.set("sort", Property.Fields.POST_DATE.toString() + " desc");
//				} else 
				if("l".equals(propertyListContext.getSort())) {
					params.set("sort", Property.Fields.MONTHLY_RENTAL.toString() + " asc");
				} else if("h".equals(propertyListContext.getSort())) {
					params.set("sort", Property.Fields.MONTHLY_RENTAL.toString() + " desc");
				} else {
					params.set("sort", "score desc");
				//TODO fix	params.set("sort", "score desc,"+PropertiesForIndexingDao.Fields.PROPERTY_REF.toString()+" asc");
				}
				
		
	}


	private void setQ(ModifiableSolrParams params, LocationBean loc) {
		if(loc!=null && loc.getId()>0){
			params.set("q","locId:"+loc.getId());
		}
		else{
		params.set("q", "*:*");
		}
		
	}


	/**
	 * 
	 * @param ref
	 * @param fields
	 * @return
	 * 
	 */
	public static long getTotalDocs() {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "*:*");
		
		QueryResponse resp = new QueryResponse();
		try {
			SolrClient server = SolrServerHolder.getNormalSolrServer();
			resp = server.query(params,METHOD.POST);			
		} catch(Exception e) {
			e.printStackTrace();
		}
		 
		if(resp != null &&  resp.getResults()!=null && resp.getResults().getNumFound() > 0)
			return resp.getResults().getNumFound();
		else 
			return 0l;
	}
	
	/**
	 * 
	 * @param ref
	 * @param fields
	 * @return
	 * 
	 */
	public Results search(String ref, String fields) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("defType", "edismax");
		params.set("rows", PAGE_SIZE);
		params.set("q", "ref:" + ref);			
		params.set("fl", fields);
        Results results = search(params);
		return results;
	}
	
	/**
	 * 
	 * @param params
	 */
	public void applyFilters(ModifiableSolrParams params,
			List<String> filtersApplied, PropertyListContext propertyListContext) {
		params.add("fq", Property.Fields.PROPERTY_TYPE.toString() + ":" + propertyListContext.getType());
		// For Sale
		long upperLimit = SELL_UPPER_LIMIT;
//		if(filters != null) {
//			for(String filter:filters.keySet()) {
//				if(filter.contains("location")) {
//					params.add("fq", "{!tag=loc}" + filter + ":\"" + filters.get(filter) + "\"");
//					filterLocLevel = filter.replaceAll("[^0-9]", "");
//					filterLoc = filters.get(filter);
//					this.location= filters.get(filter); 
//				} else {	
//					params.add("fq", filter + ":\"" + filters.get(filter) + "\"");
//				}
//			}
//		}
		
		if(propertyListContext.getType()==PropertyListContext.PROPERTY_TYPE_RENT){
			upperLimit = RENT_UPPER_LIMIT;
		}
		String lowerPriceLimit = propertyListContext.getfLowerPrice() > 0 ? Integer
				.toString(propertyListContext.getfLowerPrice()) : "*";
		String upperPriceLimit = propertyListContext.getfLowerPrice() > 0 ? Integer
				.toString(propertyListContext.getfUpperPrice()) : "*";
		params.add("fq",Property.Fields.MONTHLY_RENTAL.toString() +":[" + lowerPriceLimit + " TO " + upperPriceLimit + "]");
		
		
		if (filtersApplied.contains(Searcher.FILTER_SALEABLE_AREA)) {
			addAreaFilter(Property.Fields.SALEABLE_AREA.toString(),
					propertyListContext.getfLowerSaleableArea(),
					propertyListContext.getfUpperSaleableArea(), params);
		}
		if (filtersApplied.contains(Searcher.FILTER_GROSS_AREA)) {
			addAreaFilter(Property.Fields.GROSS_AREA.toString(),
					propertyListContext.getfLowerGrossArea(),
					propertyListContext.getfUpperGrossArea(), params);
		}
		if(filtersApplied.contains(Searcher.FILTER_ROOMS)){
			params.add("fq", Property.Fields.BEDROOMS.toString() + ":" + propertyListContext.getfNoRooms());
		}
			
		//if(postDate > 0)
		//	params.add("fq", Property.Fields.POST_DATE.toString() + ":[" +DateUtil.daysBeforAfter(-postDate)+ " TO *]");		
	}
	
	private void addAreaFilter(String solrFieldName, int lowerAreaParam,
			int upperAreaParam, ModifiableSolrParams params) {
		String areaLowerLimit = "*";
		String areaUpperLimit = "*";
		if(lowerAreaParam>0){
			areaLowerLimit = Integer.toString(lowerAreaParam);
		}
		if(upperAreaParam >0){
			areaUpperLimit = Integer.toString(upperAreaParam);
		}
		params.add("fq", solrFieldName+ ":[" + areaLowerLimit + " TO "+areaUpperLimit+"]");
		
	}

	/**
	 * 
	 * @param params
	 */
	public void setupFilters(ModifiableSolrParams params) {
		params.set("facet.limit", FACET_LIMIT);
		params.set("facet.mincount", "1");
		params.set("facet.sort", "count");
        
		// Set facet to true to get the filter values
		params.set("facet", "true");
		
		// Set up filter for location type
		// Dynamic so could be different based on the region.
		String locationPivots = "";
		for(int i=1; i<=locationSize; i++) {
//			if(locationPivots.isEmpty())
//				locationPivots = Property.Fields.LOCATION.toString() + "_f_" + i;
//			else
//				locationPivots = locationPivots + "," + Property.Fields.LOCATION.toString() + "_f_" + i;
		}
		
//		params.add("facet.pivot", "{!ex=loc}" +locationPivots);
		
		// Set up filter for house type
//		params.add("facet.field", Property.Fields.HOUSE_TYPE.toString());
		
		// Set up filter for agency
//		params.add("facet.field", Property.Fields.AGENT.toString());
		
		// Set up filter for Bedrooms
		params.add("facet.query", Property.Fields.BEDROOMS.toString() + ":1");
		params.add("facet.query", Property.Fields.BEDROOMS.toString() + ":2");
		params.add("facet.query", Property.Fields.BEDROOMS.toString() + ":3");
		params.add("facet.query", Property.Fields.BEDROOMS.toString() + ":4");
		params.add("facet.query", Property.Fields.BEDROOMS.toString() + ":[5 TO *]");		
		
		// Set up filter for Post date
//		params.add("facet.query", Property.Fields.POST_DATE.toString() + ":[" + DateUtil.daysBeforAfter(-1l) + " TO " + DateUtil.today() + "]");
//		params.add("facet.query", Property.Fields.POST_DATE.toString() + ":[" + DateUtil.daysBeforAfter(-3l) + " TO " + DateUtil.today() + "]");
//		params.add("facet.query", Property.Fields.POST_DATE.toString() + ":[" + DateUtil.daysBeforAfter(-7l) + " TO " + DateUtil.today() + "]");		
//	
		}
	
	public int getLocationSize() {
		return locationSize;
	}

	public void setLocationSize(int locationSize) {
		this.locationSize = locationSize;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getFilterLoc() {
		return filterLoc;
	}

	public void setFilterLoc(String filterLoc) {
		this.filterLoc = filterLoc;
	}

	public String getFilterLocLevel() {
		return filterLocLevel;
	}

	public void setFilterLocLevel(String filterLocLevel) {
		this.filterLocLevel = filterLocLevel;
	}

//TODO delete the below
//	public String[] getFilterLocs() {
//		return filterLocs;
//	}


	public PropertyListContext getPropertyListContext() {
		return propertyListContext;
	}
}
