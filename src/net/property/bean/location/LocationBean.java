package net.property.bean.location;

public class LocationBean {
	private int id;
	private String locality;
	private String city;
	private String state;
	private String country;
	private String region;
	private String neighbourhood;
	
	public LocationBean(int id, String locality, String city, String state,
			String country, String region, String neighbourhood) {
		super();
		this.id = id;
		this.locality = locality;
		this.city = city;
		this.state = state;
		this.country = country;
		this.region = region;
		this.neighbourhood = neighbourhood;
	}

	public int getId() {
		return id;
	}

	public String getLocality() {
		return locality;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getRegion() {
		return region;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}
}
