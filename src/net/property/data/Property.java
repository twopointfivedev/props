package net.property.data;


/**
 * Bean class for holding each property's data ,retrieved from solr
 * @author Saurabh
 *
 */
	/** Constsnts */
public class Property extends Item {
	final static int SUMMARY_LENGTH = 350;
	
	/**Property Fields */
	private String propertyRef;
	private String title;
	private String propertyDetailsUrl;
	private long crawlTime; 
	private String source;
	private Integer monthlyRental; 
	private Long totalPrice;
	private String currency;
	private Integer grossArea;
	private Integer saleableArea; 
	private String unit;
	private String furnished;
	private String moreFeatures1;
	private String moreFeatures2;
	private String moreFeatures3;
	private Integer bedRooms;
	private Integer propertyType;
	private Integer bathRooms;
	private Integer yearBuilt; 
	private String propertyDescription;
	private String city;
	private String state;
	private String country;
	private String address;
	private String locality;
	private String neighbourhood;
	private int locId;
	private String subLocality1;
	private String subLocality2;
	private int imageCount;
	private String thumbnailPath;
	private String bigImagePath;
	private int locationSize = 5; //TODO Maximum locations that can be chosen. To be implemented
	private long agentPhoneNo;
	private String agentName;
	/**
	 * These are field names for solr fields, defined in schema.xml
	 * @author Saurabh
	 *
	 */
	public static enum Fields {
		PROPERTY_REF("PROPERTY_REF"), TITLE("title"), PROPERTY_DETAILS_URL(
				"PROPERTY_DETAILS_URL"), CRAWL_TIME("CRAWL_TIME"),SOURCE("SOURCE"),

		/** Property Details */
		MONTHLY_RENTAL("MONTHLY_RENTAL"), TOTALPRICE("TOTALPRICE"), CURRENCY(
				"CURRENCY"), GROSS_AREA("GROSS_AREA"), SALEABLE_AREA(
				"SALEABLE_AREA"), FURNISHED("FURNISHED"), UNIT("UNIT"), MORE_FEATURES_1(
				"MORE_FEATURES_1"), MORE_FEATURES_2("MORE_FEATURES_2"), MORE_FEATURES_3(
				"MORE_FEATURES_3"),

		BEDROOMS("BEDROOMS"), PROPERTY_TYPE("PROPERTY_TYPE"), BATHROOMS(
				"BATHROOMS"), YEAR_BUILT("YEAR_BUILT"), PROPERTY_DESCRIPTION(
				"PROPERTY_DESCRIPTION"),
		/** Location details */
		city("city"), locality("locality"), country("country"), neighbourhood("neighbourhood")
		, formattedAddress("formattedAddress"), rawAddress(
				"rawAddress"), locId("locId"), 
		/** Images */
		IMAGE_COUNT("IMAGE_COUNT"),
		/** Agent */
		AGENT_PHONE_NO("AGENT_PHONE_NO"),
		AGENT_NAME("AGENT_NAME");
	    /**
	     * @param text
	     */
	    private Fields(final String text) {
	        this.text = text;
	    }

	    private final String text;

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	public String getPropertyRef() {
		return propertyRef;
	}
	public void setPropertyRef(String propertyRef) {
		this.propertyRef = propertyRef;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPropertyDetailsUrl() {
		return propertyDetailsUrl;
	}
	public void setPropertyDetailsUrl(String propertyDetailsUrl) {
		this.propertyDetailsUrl = propertyDetailsUrl;
	}
	public long getCrawlTime() {
		return crawlTime;
	}
	public void setCrawlTime(long crawlTime) {
		this.crawlTime = crawlTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getMonthlyRental() {
		return monthlyRental;
	}
	public void setMonthlyRental(Integer monthlyRental) {
		this.monthlyRental = monthlyRental;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getGrossArea() {
		return grossArea;
	}
	public void setGrossArea(Integer grossArea) {
		this.grossArea = grossArea;
	}
	public Integer getSaleableArea() {
		return saleableArea;
	}
	public void setSaleableArea(Integer saleableArea) {
		this.saleableArea = saleableArea;
	}
	
	/** To show area in the jsp (results.jsp) */
	public String getSaleableAreaDisplay(){
		String areaToDisplay = "";
		if(saleableArea!=null){
			areaToDisplay += saleableArea;
		}
		return areaToDisplay;
		
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getFurnished() {
		return furnished;
	}
	public void setFurnished(String furnished) {
		this.furnished = furnished;
	}
	public String getMoreFeatures1() {
		return moreFeatures1;
	}
	public void setMoreFeatures1(String moreFeatures1) {
		this.moreFeatures1 = moreFeatures1;
	}
	public String getMoreFeatures2() {
		return moreFeatures2;
	}
	public void setMoreFeatures2(String moreFeatures2) {
		this.moreFeatures2 = moreFeatures2;
	}
	public String getMoreFeatures3() {
		return moreFeatures3;
	}
	public void setMoreFeatures3(String moreFeatures3) {
		this.moreFeatures3 = moreFeatures3;
	}
	public int getBedRooms() {
		return bedRooms;
		/*
		String bedRoomsDisplay = "";
		if(bedRooms==1){
			bedRoomsDisplay +=bedRooms + " "+"Bedroom";
		}
		else{
			bedRoomsDisplay += bedRooms+" "+"Bedrooms";
		}
		return bedRoomsDisplay;
	*/}
	public void setBedRooms(Integer bedRooms) {
		this.bedRooms = bedRooms;
	}
	public Integer getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}
	public Integer getBathRooms() {
		return bathRooms;
	}
	public void setBathRooms(Integer bathRooms) {
		this.bathRooms = bathRooms;
	}
	public Integer getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(Integer yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public String getPropertyDescription() {
		return propertyDescription;
	}
	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getSubLocality1() {
		return subLocality1;
	}
	public void setSubLocality1(String subLocality1) {
		this.subLocality1 = subLocality1;
	}
	public String getSubLocality2() {
		return subLocality2;
	}
	public void setSubLocality2(String subLocality2) {
		this.subLocality2 = subLocality2;
	}
	public int getLocationSize() {
		return locationSize;
	}
	public void setLocationSize(int locationSize) {
		this.locationSize = locationSize;
	}
	public String getSummary() {
		String summary = getPropertyDescription();
		if(summary != null) {
			if(summary.length() > SUMMARY_LENGTH){
				summary = summary.substring(0,SUMMARY_LENGTH) + " ...";
			}
			return summary;
		}
		return "";
	}
	public int getImageCount() {
		return imageCount;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public long getAgentPhoneNo() {
		return agentPhoneNo;
	}
	public void setAgentPhoneNo(long agentPhoneNo) {
		this.agentPhoneNo = agentPhoneNo;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getNeighbourhood() {
		return neighbourhood;
	}
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	public int getLocId() {
		return locId;
	}
	public void setLocId(int locId) {
		this.locId = locId;
	}
	public String getBigImagePath() {
		return bigImagePath;
	}
	public void setBigImagePath(String bigImagePath) {
		this.bigImagePath = bigImagePath;
	}
}
