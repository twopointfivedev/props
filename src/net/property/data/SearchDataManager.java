package net.property.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.property.utils.CrawlerUtils;
import net.property.utils.DateUtil;

import org.apache.solr.common.SolrInputDocument;

public class SearchDataManager {
	private static SearchDataManager meObj = null;
	private DataSource ds = null;
	
	public static synchronized SearchDataManager getInstance() {
		if(meObj == null) {
			meObj = new SearchDataManager();
		}
		
		return meObj;
	}
	
	public static enum MetaFields {
		// Following are the fields that we get from metadata table	

	    URL("url"),
	    // location starts with 1. Smaller means higher level. 
	    // So location with order 1 so would be usually state, county or province.
	    LOCATION("location"),

	    
	    PRICE("price"),
		ZIPCODE("zipcode"),
		AREA("area"),
		BEDROOMS("bedrooms"),
	    BATHROOMS("bathrooms"),
	    HOUSE_TYPE("house_type"),
	    // No of photos for this listing
	    IMAGES("images"),
	    DESCRIPTION("description"),
	    IMAGE_TYPE("image_type"),
	    AGENT("agent"),
	    AD_TYPE("ad_type"),
	    Features("feature");
	    

	    /**
	     * @param text
	     */
	    private MetaFields(final String text) {
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
	
	private SearchDataManager() {
	/*	try {/
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/property");
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		*/
	}
	
	public Set<SolrInputDocument> getPropertyToIndex() {
 		Set<SolrInputDocument> list = new HashSet<SolrInputDocument>();
//		try {
//			Connection conn = ds.getConnection();
//			try {
				PropertiesForIndexingDao propDao = PropertiesForIndexingDao.getInstance();
				list = propDao.getSolrDocumentsToAdd();
				
/*				String query = "SELECT ref, source, region, post_date, expiry_date "
						+ "FROM properties WHERE status='F' LIMIT 1000";
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();*/
								
				/*	while (rs.next()) {
					SolrInputDocument p = new SolrInputDocument();
					
					String ref =  rs.getString(Property.Fields.REF.toString());
					// REQUIRED FIELDS
					p.addField(Property.Fields.REF.toString(), ref);
					p.addField(Property.Fields.SOURCE.toString(), rs.getString(Property.Fields.SOURCE.toString()));
					p.addField(Property.Fields.REGION.toString(), rs.getString(Property.Fields.REGION.toString()));
					
					p.addField("post_date", DateUtil.getDateInOtherFormat(rs.getString(Property.Fields.POST_DATE.toString()), "yyyy-MM-dd","yyyyMMdd"));
					p.addField("expiry_date", DateUtil.getDateInOtherFormat(rs.getString(Property.Fields.EXPIRY_DATE.toString()), "yyyy-MM-dd","yyyyMMdd"));
					
					String metaQuery = "SELECT `key`, `value`, `order` FROM metadata WHERE ref=?";
					PreparedStatement metastmt = conn.prepareStatement(metaQuery); 
					metastmt.setString(1, ref);
					ResultSet metaResult = metastmt.executeQuery();
					
					while (metaResult.next()) {
						if (metaResult.getString("key").contains(Property.Fields.URL.toString())) {
							p.addField(Property.Fields.URL.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").contains(Property.Fields.LOCATION.toString())) {
							// If the field is location then do things differently
							//p.addField(Property.Fields.LOCATION + "_" + metaResult.getString("order"), metaResult.getString("value"));
							//p.addField(Property.Fields.LOCATION + "_f_" + metaResult.getString("order"), metaResult.getString("value"));
							
							//p.addField(Property.Fields.LOCATION + "_f", metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.PRICE.toString())) {
							p.addField(Property.Fields.PRICE.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.ZIPCODE.toString())) {
							p.addField(Property.Fields.ZIPCODE.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.AREA.toString())) {
							p.addField(Property.Fields.AREA.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.BEDROOMS.toString())) {
							p.addField(Property.Fields.BEDROOMS.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.BATHROOMS.toString())) {
							p.addField(Property.Fields.BATHROOMS.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.HOUSE_TYPE.toString())) {
							p.addField(Property.Fields.HOUSE_TYPE.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.DESCRIPTION.toString())) {
							String description = CrawlerUtils.cleanDescription(metaResult.getString("value"));
							p.addField(Property.Fields.DESCRIPTION.toString(), description);
							
						} else if (metaResult.getString("key").equals(Property.Fields.IMAGES.toString())) {
							p.addField(Property.Fields.IMAGES.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.IMAGE_TYPE.toString())) {
							p.addField(Property.Fields.IMAGE_TYPE.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.AGENT.toString())) {
							p.addField(Property.Fields.AGENT.toString(),metaResult.getString("value"));
							
						} else if (metaResult.getString("key").equals(Property.Fields.AD_TYPE.toString())) {
							p.addField(Property.Fields.AD_TYPE.toString(),metaResult.getString("value"));
							
						} 
					}					
					
					metastmt.close();
					metaResult.close();
					
					String updatequery = "UPDATE properties SET status='I' WHERE ref=?";
					PreparedStatement updateStmt = conn.prepareStatement(updatequery);
					updateStmt.setString(1, ref);
					updateStmt.executeUpdate();
					updateStmt.close();
					
					p.setCurrency(rs.getString("currency"));
					p.setRent(rs.getString("rent"));
					p.setPrice(rs.getString("price"));
					
					list.add(p);
				}
				*/
/*				pstmt.close();
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		return list;
	}
	
	public void addItem(Item item) {
		try {
			Connection conn = ds.getConnection();
			try {
				String query = "INSERT INTO properties (ref, source, "
						+ " region, post_date, expiry_date,"
						+ "status, last_update)"
						+ " VALUES (?,?,?,?,?,"
						+ "'F',NOW())";

				PreparedStatement stmt = conn.prepareStatement(query);
				int index = 1;
				String ref = item.getProperty("ref");
				stmt.setString(index++, ref);
				stmt.setString(index++, item.getProperty("source").trim());
				stmt.setString(index++, item.getProperty("region").trim());
				stmt.setString(index++, DateUtil.today());
				stmt.setString(index++, DateUtil.daysBeforAfter(30));
				stmt.executeUpdate();

				stmt.close();	
				
				for (MetaFields property: MetaFields.values()) {
					String key = property.toString();
					String value = "";
					int order = 0;
					if(key.equalsIgnoreCase("location") || key.equalsIgnoreCase("feature")){
						List<String> items = item.getFieldNames(key, true);
						for(String itemname:items){
							value = item.getProperty(itemname);
							String orderNumber = itemname.replaceAll(".*?([0-9]+)", "$1");
							if(!orderNumber.isEmpty())
								order = Integer.parseInt(orderNumber);
							
							if (value != null && !value.isEmpty()) {
								query = "INSERT INTO metadata (ref, `key`, value, `order`) VALUES (?,?,?,?)";

								stmt = conn.prepareStatement(query);
								index = 1;
								stmt.setString(index++, ref);
								stmt.setString(index++, key);
								stmt.setString(index++, item.getProperty(itemname));
								stmt.setInt(index++, order);

								stmt.executeUpdate();

								stmt.close();						
							}
						}
					}else{
						value = item.getProperty(key.toString());
						if (value != null && !value.isEmpty()) {
							query = "INSERT INTO metadata (ref, `key`, value, `order`) VALUES (?,?,?,0)";

							stmt = conn.prepareStatement(query);
							index = 1;
							stmt.setString(index++, ref);
							stmt.setString(index++, key);
							stmt.setString(index++, item.getProperty(key));


							stmt.executeUpdate();

							stmt.close();						
						}
					}

				}

			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasItem(String ref) {
		boolean toReturn = false;
		try {
			Connection conn = ds.getConnection();
			try {
				String query = "SELECT * FROM properties WHERE ref=?";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, ref);
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					toReturn=true;
				}
				
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
}
