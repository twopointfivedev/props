
package net.property.search;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.property.data.Filter;
import net.property.data.LocationFilter;
import net.property.data.Property;
import net.property.utils.GlobalConstants;
import net.property.utils.ImageUtils;
import net.property.utils.SourceConstants;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

@Deprecated
public class Results {
	private List<Property> results;
	NamedList<List<PivotField>> filterPivots;
	private List<FacetField> filters;
	private Map<String,Integer> facetQueries;
	private long total;
	private long page;
	
	// The search term used
	private String location = "";
	
	// Used for storing the current location filter and the level
	private String filterLoc = null;
	private String[] filterLocs = null;
	private int filterLocLevel = 1;
	
	// Location Filter
	List<LocationFilter> locationFilters = new ArrayList<LocationFilter>();
	
	// Type Filter
	List<Filter> typeFilters = new ArrayList<Filter>();
	
	// Rooms Filter - Number of bedroom
	List<Filter> roomsFilters = new ArrayList<Filter>();
	
	List<Filter> grossAreaFilters = new ArrayList<Filter>();
	
	List<Filter> saleableAreaFilters = new ArrayList<Filter>();
	
	// Filter by date posted
	List<Filter> postedFilters = new ArrayList<Filter>();
	
	// Agent Filter - Currently not used		
	List<Filter> agentFilters = new ArrayList<Filter>();
	
	// May throw null pointer exception
	// May throw cast exception
	public Results(QueryResponse response, Searcher searcher) {
		filters = response.getFacetFields();
		facetQueries = response.getFacetQuery();
		
		filterPivots = response.getFacetPivot();
		
		total = response.getResults().getNumFound();
		SolrDocumentList docs = response.getResults();
		
		this.page = searcher.getPropertyListContext().getPage();
		
		this.location = searcher.getLocation();
		
		this.filterLoc = searcher.getFilterLoc();
		
		try {
			this.filterLocLevel = Integer.parseInt(searcher.getFilterLocLevel());
		} catch(NumberFormatException  e) {
			this.filterLocLevel = 1;
		}
		
		
		// Set the results
		results = new ArrayList<Property>();
		for(SolrDocument doc:docs) {
			Property result = new Property();
			String propertyRef = (String)doc.get(Property.Fields.PROPERTY_REF.toString());
			result.setPropertyRef(propertyRef);
			result.setPropertyDetailsUrl((String)doc.get(Property.Fields.PROPERTY_DETAILS_URL.toString()));
			result.setTitle((String)doc.get(Property.Fields.TITLE.toString()));
			result.setAddress((String)doc.get(Property.Fields.formattedAddress.toString()));
			int locId = (Integer)doc.get(Property.Fields.locId.toString());
			if(locId>0){
			result.setNeighbourhood((String)doc.get(Property.Fields.neighbourhood.toString()));
			}
			else{
				result.setNeighbourhood("");//TODO fix this
			}
//			for(int i =1; i <= searcher.getLocationSize(); i++) {
//				if(doc.get("location" + "_" + i) !=null) {
//					result.setLocation((String)doc.get("location" + "_" + i), i);
//				}
//			}
			
			
			if (doc.get(Property.Fields.PROPERTY_DESCRIPTION.toString()) != null) {
				result.setPropertyDescription((String) doc
						.get(Property.Fields.PROPERTY_DESCRIPTION.toString()));
			}
			
			if(doc.get(Property.Fields.MONTHLY_RENTAL.toString())!=null)
				result.setMonthlyRental((Integer)doc.get(Property.Fields.MONTHLY_RENTAL.toString()));
			result.setBedRooms((Integer) doc.get(Property.Fields.BEDROOMS.toString()));
			result.setGrossArea((Integer) doc.get(Property.Fields.GROSS_AREA.toString()));
			result.setSaleableArea((Integer) doc.get(Property.Fields.SALEABLE_AREA.toString()));
			if(doc.get(Property.Fields.UNIT.toString()) !=null){
			result.setUnit((String) doc.get(Property.Fields.UNIT.toString()));
			}
			result.setCrawlTime((Long) doc.get(Property.Fields.CRAWL_TIME.toString()));
			result.setTotalPrice((Long) doc.get(Property.Fields.TOTALPRICE.toString()));
			if(doc.get(Property.Fields.CURRENCY.toString()) !=null){
			result.setCurrency((String) doc.get(Property.Fields.CURRENCY.toString()));
			}
			if(doc.get(Property.Fields.FURNISHED.toString()) !=null){
			result.setFurnished((String) doc.get(Property.Fields.FURNISHED.toString()));
			}
			result.setPropertyType((Integer) doc.get(Property.Fields.PROPERTY_TYPE.toString()));
			result.setBathRooms((Integer) doc.get(Property.Fields.BATHROOMS.toString()));
			result.setYearBuilt((Integer) doc.get(Property.Fields.YEAR_BUILT.toString()));
			result.setImageCount((Integer)doc.get(Property.Fields.IMAGE_COUNT.toString()));
			int propertyType = (Integer) doc.get(Property.Fields.PROPERTY_TYPE.toString());
			String source = (String) doc.get(Property.Fields.SOURCE.toString());
			String folder = ImageUtils.getPath(propertyRef, propertyType, source);
			result.setThumbnailPath(folder+"\\small\\"+propertyRef+"_1.jpg");
			result.setBigImagePath(folder + "\\large\\"+propertyRef+"_");
			result.setAgentPhoneNo((Long)doc.get(Property.Fields.AGENT_PHONE_NO.toString()));
			result.setAgentName((String)doc.get(Property.Fields.AGENT_NAME.toString()));
//			if(doc.get(Property.Fields.HOUSE_TYPE.toString()) !=null)
//				result.setHouseType((String)doc.get(Property.Fields.HOUSE_TYPE.toString()));
			
//			if(doc.get(Property.Fields.AGENT.toString()) !=null)
//				result.setAgent((String)doc.get(Property.Fields.AGENT.toString()));
			
//			if(doc.get(Property.Fields.IMAGE_TYPE.toString()) !=null)
//				result.setImageType((String)doc.get(Property.Fields.IMAGE_TYPE.toString()));
			
//			if(doc.get(Property.Fields.IMAGES.toString()) !=null)
//				result.setImages((Integer)doc.get(Property.Fields.IMAGES.toString()));
			
			if(doc.get(Property.Fields.SOURCE.toString()) !=null){
				result.setSource((String)doc.get(Property.Fields.SOURCE.toString()));
			}
			results.add(result);
		}
		
		if(filterPivots!=null) {
			{
				// First pivot fields like England, Scotland etc
				processPivot(locationFilters, filterPivots.getVal(0), 1, false);				
			}
		}
		
		// Work on the filters
		if(filters != null) {
            for(FacetField ff: filters) {
            	
//            	if(Property.Fields.HOUSE_TYPE.toString().equals(ff.getName())) {
//                	for(Count c:ff.getValues()) {
//                		typeFilters.add(new Filter(c.getName(), c.getName(), c.getCount()));
//                	}
//                }
//            	if(Property.Fields.AGENT.toString().equals(ff.getName())) {
//                	for(Count c:ff.getValues()) {
//                		agentFilters.add(new Filter(c.getName(), c.getName(), c.getCount()));
//                	}
//                }
                /*if("location_f".equals(ff.getName())) {
                	for(Count c:ff.getValues()) {
                		locationFilters.add(new Filter(c.getName(), c.getName(), c.getCount()));
                	}
                }*/
            }
        }
		
		// The following filters are implemented as facet queries
		// Filter by number of bedrooms
		// Number of properties by date posted
		if(facetQueries != null) {
            for(String ff: facetQueries.keySet()) {
            	if(ff.contains(Property.Fields.BEDROOMS.toString())) {
            		if(ff.contains(":1") && facetQueries.get(ff) > 0)
            			roomsFilters.add(new Filter("1","1",facetQueries.get(ff)));
            		else if(ff.contains(":2") && facetQueries.get(ff) > 0)
            			roomsFilters.add(new Filter("2","2",facetQueries.get(ff)));
            		else if(ff.contains(":3") && facetQueries.get(ff) > 0)
            			roomsFilters.add(new Filter("3","3",facetQueries.get(ff)));
            		else if(ff.contains(":4") && facetQueries.get(ff) > 0)
            			roomsFilters.add(new Filter("4","4",facetQueries.get(ff)));
            		else if(ff.contains(":5") && facetQueries.get(ff) > 0)
            			roomsFilters.add(new Filter("5+","5",facetQueries.get(ff)));
                }
//            	if(ff.contains(Property.Fields.GROSS_AREA.toString())){
//            		if()
//            	}
            	
//            	if(ff.contains(Property.Fields.POST_DATE.toString())) {
//            		if(ff.contains(DateUtil.daysBeforAfter(-1l)) && filtersQueries.get(ff) > 0)
//            			postedFilters.add(new Filter("Last 24 hours","1",filtersQueries.get(ff)));
//            		if(ff.contains(DateUtil.daysBeforAfter(-3l)) && filtersQueries.get(ff) > 0)
//            			postedFilters.add(new Filter("Last 3 days","3",filtersQueries.get(ff)));
//            		if(ff.contains(DateUtil.daysBeforAfter(-7l)) && filtersQueries.get(ff) > 0)
//            			postedFilters.add(new Filter("Last 7 days","7",filtersQueries.get(ff)));
//                }
            }
        }
	}
	
	/**
	 * 
	 * @param pivotFields
	 * 
	 */
	public void processPivot(List<LocationFilter> outsideLocFilters, List<PivotField> pivotFields, int level, boolean includeThisLevel) {
		boolean increaseLevel = true;
		if(pivotFields!=null) {
			for(PivotField pf:pivotFields) {
				String loc = (String)pf.getValue();
				LocationFilter lf2 = new LocationFilter((String)pf.getField(), loc, (long)pf.getCount());
				List<LocationFilter> locFilters2 = new ArrayList<LocationFilter>();
				
				boolean addToFilter = false;
				
				// Check the nested pivot proceed if null
				// Continue if the level is 1 or if the Pivot location matches the filtered location
				// Also continue if the search term matches the location name
				if(pf.getPivot()!=null && Arrays.asList(filterLocs).contains(loc) 
						|| loc.trim().equalsIgnoreCase(location.trim())) {
					if(increaseLevel) {
						++level;
						increaseLevel = false;
					}
					addToFilter = true;
					processPivot(locFilters2, pf.getPivot(), level, addToFilter);
				}
				if(addToFilter || includeThisLevel)
					lf2.setLocationFilters(locFilters2);
				
				// if(addToFilter || level > 2 || level == 1)
				// if(includeThisLevel)
				outsideLocFilters.add(lf2);
			}
		}
	}
	
	public List<Property> getResults() {
		return results;
	}
	
	public void setResults(List<Property> results) {
		this.results = results;
	}
	
	public List<FacetField> getFilters() {
		return filters;
	}
	
	public void setFilters(List<FacetField> filters) {
		this.filters = filters;
	}
	
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}

	public List<LocationFilter> getLocationFilters() {
		return locationFilters;
	}

	/*public void setLocationFilters(List<Filter> locationFilters) {
		this.locationFilters = locationFilters;
	}*/

	public List<Filter> getTypeFilters() {
		return typeFilters;
	}

	public void setTypeFilters(List<Filter> typeFilters) {
		this.typeFilters = typeFilters;
	}

	public List<Filter> getRoomsFilters() {
		return roomsFilters;
	}
	
	public List<Filter> getGrossAreaFilters(){
		return grossAreaFilters;
	}
	
	public List<Filter> getSaleableAreaFilters(){
		return saleableAreaFilters;
	}

	public void setRoomsFilters(List<Filter> roomsFilters) {
		this.roomsFilters = roomsFilters;
	}

	public List<Filter> getPostedFilters() {
		return postedFilters;
	}

	public void setPostedFilters(List<Filter> postedFilters) {
		this.postedFilters = postedFilters;
	}

	public List<Filter> getAgentFilters() {
		return agentFilters;
	}

	public void setAgentFilters(List<Filter> agentFilters) {
		this.agentFilters = agentFilters;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}
	
	public long getStart() {
		return (page - 1) * Searcher.PAGE_SIZE + 1;
	}
	
	public long getEnd() {
		long end = ((page - 1) * Searcher.PAGE_SIZE) + Searcher.PAGE_SIZE;
		if(end > total)
			end = total;
		return end;
	}
	
	public long getTotalPages() {
		return (long)Math.ceil((float)total/(float)Searcher.PAGE_SIZE);
	}
	
	public long getStartPage() {
		long startPage = page - 2l;
		if(startPage < 1)
			startPage = 1l;
		return startPage;
	}
	
	public long getEndPage() {
		long totalPages = getTotalPages();
		long endPage = page + 2l;
		if(endPage < 5)
			endPage = 5l;
		if(endPage > totalPages)
			endPage = totalPages;
		return endPage;
	}
}
