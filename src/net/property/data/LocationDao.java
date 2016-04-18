package net.property.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.property.bean.location.LocationBean;
import net.property.bean.location.LocationSuggestionBean;

public class LocationDao extends AbstractDao{
	private static LocationDao thisObj = null;
	private DataSource ds = null;
	
	private LocationDao(){

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/property");
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
	
	public static LocationDao getInstance() {
		if (thisObj == null) {
			thisObj = new LocationDao();
		}
		return thisObj;
	}

	public LocationBean getLocation(int locId) {
		LocationBean location = null;
		Connection conn = null;
		try {
		conn = ds.getConnection();

		String query = "select * from all_locations where id=?"; 
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, locId);
		ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String locality = rs.getString("locality");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String region = rs.getString("region");
				String neighbourhood = rs.getString("neighbourhood");
				location = new LocationBean(id, locality, city, state, country,
						region, neighbourhood);
			}
			pstmt.close();
	}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
}