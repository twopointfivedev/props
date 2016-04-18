package net.property.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.property.bean.location.LocationSuggestionBean;
@Deprecated
public class LocationDB {
	private static LocationDB meObj = null;
	private DataSource ds = null;
	
	private Hashtable<Long,LocationEntry> locations = new Hashtable<Long, LocationDB.LocationEntry>();
	private Hashtable<String,Set<Long>> locationNameMap = new Hashtable<String, Set<Long>>();
	
	public static synchronized LocationDB getInstance() {
		if(meObj == null) {
			meObj = new LocationDB();
		}
		
		return meObj;
	}
	
	private LocationDB() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/property");
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		loadLocationMap();
	}
	
	public class LocationEntry {
		private long id;
		private String location;
		private long parentID;
		private short level;
		private boolean majorLocation;
		
		public LocationEntry(long id, String location, long parentID,
				short level, boolean majorLocation) {
			this.id = id;
			this.location = location;
			this.parentID = parentID;
			this.level = level;
			this.majorLocation = majorLocation;
		}
		
		public long getId() {
			return id;
		}
		public String getLocation() {
			return location;
		}
		public long getParentID() {
			return parentID;
		}
		public short getLevel() {
			return level;
		}
		public boolean isMajorLocation() {
			return majorLocation;
		}
		
		@Override
		public String toString() {
			return location;
		}
	}
	
	private void loadLocationMap() {
		try {
			Connection conn = ds.getConnection();
			try {
				String query = "SELECT * FROM locations ORDER BY major_location DESC, level ASC";
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					long id = rs.getLong("id");
					String location = rs.getString("location");
					
					LocationEntry e = new LocationEntry (id, location, rs.getLong("parent_id"), rs.getShort("level"), rs.getBoolean("major_location"));
					locations.put(id, e);
					
					// Prepare list of keys/aliases for each location
					// Ex: St. John
					// Keys: stjohn, saintjohn
					// EX: Stoke-on-Kent
					// Keys: stokeonkent, stoke-on-kent
					Set<String> keys = new HashSet<String>();
		
					String tmpkey = location.toLowerCase().replaceAll(" |'|\\.|\\(|\\)", "");					
					keys.add(tmpkey);
					
					if (location.contains("St.")) {
						tmpkey = location.replaceAll("St.", "Saint");
						tmpkey = tmpkey.toLowerCase().replaceAll(" |'|\\.|\\(|\\)", "");
						keys.add(tmpkey);

						// For locations with both "St." and "-" 
						if (location.contains("-")) {
							tmpkey = tmpkey.toLowerCase().replaceAll(" |'|\\.|\\(|\\)|-", "");
							keys.add(tmpkey);
						}
					}
					if (location.contains("-")) {
						tmpkey = tmpkey.toLowerCase().replaceAll(" |'|\\.|\\(|\\)|-", "");
						keys.add(tmpkey);
					}
					
					for (String key: keys) {
						if (locationNameMap.containsKey(key)) {
							locationNameMap.get(key).add(id);							
						} else {
							Set<Long> ids = new LinkedHashSet<Long>();
							ids.add(id);
							locationNameMap.put(key,ids);							
						}					
					}
				}
				
				rs.close();
				stmt.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Location normalizeLocation(String data) {
		data = removeAccents(data);
		data = data.replaceAll("\u00A0", " ");
		
		String zipcode = "";
		Pattern p = Pattern.compile("\\b([A-Z]{1,2}[0-9]{1,2}[A-Z]? ?([0-9][A-Z]+)?)\\b");
		Matcher m = p.matcher(data);
		if(m.find()) {
			zipcode = m.group(1);
		}
		
		String[] tokens = data.split(",");
		
		Map<Location, Float> detectedLocations = new Hashtable<Location, Float>();
		for (String token: tokens) {
			// Remove zipcode
			token = token.replaceAll("\\b[A-Z]{1,2}[0-9]{1,2}[A-Z]?\\b", "");
			token = token.toLowerCase().replaceAll(" |'|\\.|\\(|\\)", "");
			
			// Match tokens and identify possible locations
			if (locationNameMap.containsKey(token)) {
				// Identify the correct locations using scoring/weighting
				Set<Long> ids = locationNameMap.get(token);
				int size = ids.size();
				float score = size<=3?1/(float)size:0.25f;
				
				for (Long id:ids) {
					Location tmpLoc = getLocation(id);
					LocationEntry entry = locations.get(id);
					if (entry.isMajorLocation())
						score += 0.25f;
					
					if (detectedLocations.containsKey(id)) {
						float weight = detectedLocations.get(id) + score;
						detectedLocations.put(tmpLoc, weight);
					} else {
						detectedLocations.put(tmpLoc, score);
					}
				}				
			}
		}
		
		Map<Location, Float> scoredLocations = new Hashtable<Location, Float>(detectedLocations);
		Map<Location, Float> tmpLocations = new Hashtable<Location, Float>(detectedLocations);
		for (Map.Entry<Location, Float> e:detectedLocations.entrySet()) {
			for (Map.Entry<Location, Float> tmp:tmpLocations.entrySet()) {
				if (tmp.getKey().containsID(e.getKey().getId()) 
						&& tmp.getKey().getId() != e.getKey().getId()) {
					float weight = scoredLocations.get(tmp.getKey()) + e.getValue();
					scoredLocations.put(tmp.getKey(), weight);
				}
			}
		}
		
		Location finalLocation = new Location();
		float highestScore = 0;
		for (Map.Entry<Location, Float> e:scoredLocations.entrySet()) {
			if (e.getValue() > highestScore) {
				highestScore = e.getValue();
				finalLocation = e.getKey();
			}
		}
		
		if (!zipcode.isEmpty()) {
			finalLocation.setZipcode(zipcode);
		}
		
		return finalLocation;
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	
	public Location getNormalizedLocation(String location) {
		Set<Long> locIds = locationNameMap.get(location.toLowerCase().replaceAll(" |'|\\.|\\(|\\)|-", "").trim());
		long locId = 0;
		if(locIds != null && locIds.size() > 0)
			locId = locIds.iterator().next();
		if(locId > 0)
			return getLocation(locId);
		return null;
	}
	
	public Location getLocation(Long id) {
		Location location = new Location();
		location.setId(id);
		int level = 1;
		long parentID = id;
		do {
			if (locations.containsKey(parentID)) {
				LocationEntry tmp = locations.get(parentID);
				level = tmp.getLevel(); 
				location.setLocation(tmp.getLocation(), tmp.getLevel(),tmp.getId());
				parentID = tmp.getParentID();
			}
		} while (level > 1);
		
		return location;
	}
	
	public List<LocationSuggestionBean> getLocationSuggestions(String query) {
		//Map<String,Long> locations = new LinkedHashMap<String,Long>();
		List<LocationSuggestionBean> locSuggestions = new ArrayList<LocationSuggestionBean>();
		try {
			Connection conn = ds.getConnection();
			try {
				//String sqlquery = "SELECT * FROM locations where location like '"+query+"%' ORDER BY major_location DESC,level ASC LIMIT 10";
				String selectMatchingLocations = "SELECT id, locality, neighbourhood from all_locations "
						+ "where neighbourhood like ? "
						+ "ORDER BY neighbourhood DESC";
				PreparedStatement stmt = conn.prepareStatement(selectMatchingLocations);
				stmt.setString(1, "%"+query+"%");
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					int id = rs.getInt("id");
					String locality = rs.getString("locality");
					String neighbourhood = rs.getString("neighbourhood");
					locSuggestions.add(new LocationSuggestionBean(id, locality, neighbourhood));
				}
				
				rs.close();
				stmt.close();
			
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return locSuggestions;
	}
	
	
	public static String removeAccents(String text) {
	    return text == null ? null
	        : Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}
