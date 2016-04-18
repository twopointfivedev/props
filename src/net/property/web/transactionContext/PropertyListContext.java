package net.property.web.transactionContext;

import javax.servlet.http.HttpServletRequest;

import net.property.search.Searcher;

public class PropertyListContext {
	public static final int PROPERTY_TYPE_RENT = 1;
	public static final int PROPERTY_TYPE_SALE = 2;
	private int type;
	private int page;
	private int locId;
	private String[] fLocations;
	private int fLowerPrice;
	private int fUpperPrice;
	private int fLowerGrossArea;
	private int fUpperGrossArea;
	private int fLowerSaleableArea;
	private int fUpperSaleableArea;
//	private String fLevel;
	private String sort;
	String fHouseType;
	long fPosted;
	String fNoRooms;
	String fAgent;
	
	public PropertyListContext(HttpServletRequest request) {
		type = getType(request);
		page = getPage(request);
		locId = getLocId(request);
		fLocations = getFLocations(request);
		fLowerPrice = getFLowerPrice(request);
		fUpperPrice = getFUpperPrice(request);
		fLowerGrossArea = getFLowerGrossArea(request);
		fUpperGrossArea = getFUpperGrossArea(request);
		fLowerSaleableArea = getFLowerSaleableArea(request);
		fUpperSaleableArea = getFUpperSaleableArea(request);
//		fLevel = getFLevel(request);
		sort = getSort(request);
		fHouseType = getFHouseType(request);
		fPosted = getPosted(request);
		fNoRooms = getFNoRooms(request);
		fAgent = getFAgent(request);
	}
	private String getFAgent(HttpServletRequest request) {
		return request.getParameter(Searcher.FILTER_AGENT) != null ? request.getParameter(Searcher.FILTER_AGENT) : "";
	}
	private String getFNoRooms(HttpServletRequest request) {
		return request.getParameter(Searcher.FILTER_ROOMS) != null ? request.getParameter(Searcher.FILTER_ROOMS) : "";
	}
	private long getPosted(HttpServletRequest request) {
		long posted = -1; 
		String postedStr = request.getParameter(Searcher.FILTER_POSTED) != null ? request.getParameter(Searcher.FILTER_POSTED) : "";
		if (postedStr.matches("\\d+")) {
			posted = Long.parseLong(postedStr);
		}
		return posted;
	}
	private String getFHouseType(HttpServletRequest request) {
		return request.getParameter(Searcher.FILTER_TYPE) != null ? request.getParameter(Searcher.FILTER_TYPE) : "";
	}
	/** Sorting option
	 r = Default -> relevancy
	 d = Date
	 l -> Lowest price on top (Ascending)
	 h -> Highest price on top (Descending) */
	private String getSort(HttpServletRequest request) {
		String sort = request.getParameter(Searcher.FILTER_SORT) != null ? request.getParameter(Searcher.FILTER_SORT) : "r";	
		return sort;
	}

	private String getFLevel(HttpServletRequest request) {
		String fLevel = request.getParameter(Searcher.FILTER_LOCATION_LEVEL) != null ? request.getParameter(Searcher.FILTER_LOCATION_LEVEL) : "0";
		return fLevel;
	}

	private int getFUpperPrice(HttpServletRequest request) {
		int fUpperPrice = -1; // Default
		String upperLimitParam = request.getParameter(Searcher.FILTER_PRICE_UPPER);
		if (upperLimitParam != null && upperLimitParam.matches("\\d+")) {
			fUpperPrice = Integer.parseInt(upperLimitParam);
		}
//		} else if (type == PROPERTY_TYPE_RENT) {
//			fUpperPrice = (int) Searcher.RENT_UPPER_LIMIT;
//		} else if (type == PROPERTY_TYPE_SALE) {
//			fUpperPrice = (int) Searcher.SELL_UPPER_LIMIT;
//		}
		return fUpperPrice;
	}

	private int getFLowerPrice(HttpServletRequest request) {
		int lowerPrice = 0;
		String fLowerPrice = request.getParameter(Searcher.FILTER_PRICE_LOWER) != null ? request.getParameter(Searcher.FILTER_PRICE_LOWER) : "0";
		if(fLowerPrice.matches("\\d+")){
			lowerPrice = Integer.parseInt(fLowerPrice);
		}
		return lowerPrice;
	}
	
	private int getAreaInIntFromParams(HttpServletRequest request, String paramName, int defaultValueIfNull){
		int area = defaultValueIfNull;
		String areaStr = request.getParameter(paramName) != null ? request.getParameter(paramName) : Integer.toString(defaultValueIfNull);
		if(areaStr.matches("\\d+")){
			area = Integer.parseInt(areaStr);
		}
		return area;
	}
	
	private int getFLowerGrossArea(HttpServletRequest request) {
		return getAreaInIntFromParams(request,
				Searcher.FILTER_GROSS_AREA_LOWER, -1);
	}

	private int getFUpperGrossArea(HttpServletRequest request) {
		return getAreaInIntFromParams(request,
				Searcher.FILTER_GROSS_AREA_UPPER, -1);
	}
	
	private int getFLowerSaleableArea(HttpServletRequest request){
		return getAreaInIntFromParams(request,
				Searcher.FILTER_SALEABLE_AREA_LOWER, -1);
	}
	
	private int getFUpperSaleableArea(HttpServletRequest request){
		return getAreaInIntFromParams(request,
				Searcher.FILTER_SALEABLE_AREA_UPPER, -1);
	}

	private String[] getFLocations(HttpServletRequest request) {
		String fLocations[] = { "" };
		if (request.getParameterValues(Searcher.FILTER_LOCATION) != null
				&& request.getParameterValues(Searcher.FILTER_LOCATION).length != 0) {
			fLocations = request.getParameterValues(Searcher.FILTER_LOCATION);
		}
		return fLocations;
	}

	private int getLocId(HttpServletRequest request) {
		String sId = request.getParameter(Searcher.LOCATION_ID) != null ? request.getParameter(Searcher.LOCATION_ID) : "0";
		int locId = 0;
		
		// Location 
		if(sId.matches("\\d+")) {
			locId = Integer.parseInt(sId);
		}
		return locId;
	}

	private int getPage(HttpServletRequest request) {
		String p = request.getParameter(Searcher.PAGE) != null ? request
				.getParameter(Searcher.PAGE) : "1";
		int page = 1;

		if (p.matches("\\d+")) {
			page = Integer.parseInt(p);
		}
		return page;
	}

	private int getType(HttpServletRequest request) {
		type=1;
		try{
			type = request.getParameter(Searcher.PROPERTY_TYPE) != null ? Integer.parseInt(request.getParameter(Searcher.PROPERTY_TYPE)) : PROPERTY_TYPE_RENT;
		}catch(Exception e){
			e.printStackTrace();
		}
		return type;
	}

	public int getType(){
		return type;
	}

	public int getPage() {
		return page;
	}

	public int getLocId() {
		return locId;
	}

	public String[] getfLocations() {
		return fLocations;
	}

	public int getfLowerPrice() {
		return fLowerPrice;
	}

	public int getfUpperPrice() {
		return fUpperPrice;
	}

//	public String getfLevel() {
//		return fLevel;
//	}

//	public void setfLevel(String fLevel) {
//		this.fLevel = fLevel;
//	}
	public String getSort() {
		return sort;
	}
	public String getfHouseType() {
		return fHouseType;
	}
	public long getfPosted() {
		return fPosted;
	}
	public String getfNoRooms() {
		return fNoRooms;
	}
	public String getfAgent() {
		return fAgent;
	}
	public int getfLowerGrossArea() {
		return fLowerGrossArea;
	}
	public int getfUpperGrossArea() {
		return fUpperGrossArea;
	}
	public int getfLowerSaleableArea() {
		return fLowerSaleableArea;
	}
	public int getfUpperSaleableArea() {
		return fUpperSaleableArea;
	}

}
