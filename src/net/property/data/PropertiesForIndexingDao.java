package net.property.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.property.utils.SourceConstants;

import org.apache.solr.common.SolrInputDocument;

public class PropertiesForIndexingDao extends AbstractDao {
	private static PropertiesForIndexingDao thisObj = null;
	private DataSource ds = null;
	// "SELECT ref, source, region, post_date, expiry_date "; //TODO change
	// these fields
	private static String DOCUMENT_FIELDS_SELECT = "select pl.PROPERTY_REF, pl.SOURCE, TITLE, PROPERTY_DETAILS_URL, pl.CRAWL_TIME, "
			+ "MONTHLY_RENTAL, TOTALPRICE, CURRENCY, GROSS_AREA, SALEABLE_AREA, FURNISHED, UNIT, MORE_FEATURES_1, "
			+ "MORE_FEATURES_2, MORE_FEATURES_3, "
			+ "BEDROOMS, PROPERTY_TYPE, BATHROOMS, YEAR_BUILT, PROPERTY_DESCRIPTION, "
			+ "all_loc.city, all_loc.locality,all_loc.neighbourhood, all_loc.country, "
			+ "formattedAddress, rawAddress, id,  "
			+"agent.PHONE_NO,agent.COUNTRY_CODE, agent.NAME, "
			+ "IMAGE_COUNT ";
	private static String FROM_TABLES = "from propertylist pl inner join propertydetails pd "
			+ "on pl.PROPERTY_REF=pd.PROPERTY_REF "
			+ "inner join geo_locations geo_loc on pd.GEO_LOCATION_REF=geo_loc.locationid "
			+ "inner join all_locations all_loc on geo_loc.norm_location_id=all_loc.id "
			+"left join agent on pd.AGENT_PHONE_NO=agent.PHONE_NO ";
	private static String WHERE_CLAUSE = "where pd.CRAWL_STATUS="
			+ SourceConstants.FETCHED;
	private static int LIMIT_INT = 500;
	private static String LIMIT = " limit ";

	private PropertiesForIndexingDao() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/property");
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}

	public static PropertiesForIndexingDao getInstance() {
		if (thisObj == null) {
			thisObj = new PropertiesForIndexingDao();
		}
		return thisObj;
	}

/*	public static enum Fields {
		*//**
		 * Solr field names
		 * which are indexed
		 *//*
		PROPERTY_REF("PROPERTY_REF"),SOURCE("SOURCE"), TITLE("title"), PROPERTY_DETAILS_URL(
				"PROPERTY_DETAILS_URL"), CRAWL_TIME("CRAWL_TIME"),

		*//** Property Details *//*
		MONTHLY_RENTAL("MONTHLY_RENTAL"), TOTALPRICE("TOTALPRICE"), CURRENCY(
				"CURRENCY"), GROSS_AREA("GROSS_AREA"), SALEABLE_AREA(
				"SALEABLE_AREA"), FURNISHED("FURNISHED"), UNIT("UNIT"), MORE_FEATURES_1(
				"MORE_FEATURES_1"), MORE_FEATURES_2("MORE_FEATURES_2"), MORE_FEATURES_3(
				"MORE_FEATURES_3"),

		BEDROOMS("BEDROOMS"), PROPERTY_TYPE("PROPERTY_TYPE"), BATHROOMS(
				"BATHROOMS"), YEAR_BUILT("YEAR_BUILT"), PROPERTY_DESCRIPTION(
				"PROPERTY_DESCRIPTION"),
		*//** Location details *//*
		city("city"), state("state"), country("country"), address("address"), locality(
				"locality"), subLocality1("subLocality1"), subLocality2(
				"subLocality2");
		private Fields(final String text) {
			this.text = text;
		}

		private final String text;

		@Override
		public String toString() {
			return text;
		}
	}*/

	public Set<SolrInputDocument> getSolrDocumentsToAdd() {
		Connection conn = null;
		int initCapacity = (int) (LIMIT_INT/0.6);
		Set<SolrInputDocument> addedDocs = new HashSet<SolrInputDocument>(initCapacity);
		try {
			conn = ds.getConnection();

			String query = DOCUMENT_FIELDS_SELECT + FROM_TABLES + WHERE_CLAUSE
					+ LIMIT+LIMIT_INT;
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				try {
					SolrInputDocument p = new SolrInputDocument();
					PropStatus propStatus = addFieldsToDoc(rs, p);

					// TODO add this in separate method
					// updateCrawlStatus()
					String updatequery = "UPDATE propertydetails SET CRAWL_STATUS=? WHERE "
							+ Property.Fields.PROPERTY_REF.toString() + "=?";
					PreparedStatement updateStmt = conn
							.prepareStatement(updatequery);
					updateStmt.setShort(1, propStatus.getStatus());
					updateStmt.setString(2, propStatus.getPropertyRef());
					updateStmt.executeUpdate();
					updateStmt.close();
					addedDocs.add(p);
					i++;
					if(i>25){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return addedDocs;
	}

	private PropStatus addFieldsToDoc(ResultSet rs, SolrInputDocument p)
			throws SQLException {
		// REQUIRED FIELDS
		String ref = rs.getString(Property.Fields.PROPERTY_REF.toString());
		p.addField(Property.Fields.PROPERTY_REF.toString(), ref);
		String source = rs.getString(Property.Fields.SOURCE.toString());
		p.addField(Property.Fields.SOURCE.toString(), source);
		String title = rs.getString(Property.Fields.TITLE.toString());
		p.addField(Property.Fields.TITLE.toString(), title);
		String detailsUrl = rs
				.getString(Property.Fields.PROPERTY_DETAILS_URL.toString());
		p.addField(Property.Fields.PROPERTY_DETAILS_URL.toString(), detailsUrl);
		// non-mandatory fields
		Long crawltime = rs.getLong(Property.Fields.CRAWL_TIME.toString());
		p.addField(Property.Fields.CRAWL_TIME.toString(), crawltime);
		Integer monthlyRental = rs.getInt(Property.Fields.MONTHLY_RENTAL.toString());
		p.addField(Property.Fields.MONTHLY_RENTAL.toString(), monthlyRental);
		Long totalPrice = rs.getLong(Property.Fields.TOTALPRICE.toString());
		p.addField(Property.Fields.TOTALPRICE.toString(), totalPrice);
		String currency = rs.getString(Property.Fields.CURRENCY.toString());
		p.addField(Property.Fields.CURRENCY.toString(), currency);
		Integer grossArea = rs.getInt(Property.Fields.GROSS_AREA.toString());
		p.addField(Property.Fields.GROSS_AREA.toString(), grossArea);
		Integer saleableArea = rs.getInt(Property.Fields.SALEABLE_AREA.toString());
		p.addField(Property.Fields.SALEABLE_AREA.toString(), saleableArea);
		String furnished = rs.getString(Property.Fields.FURNISHED.toString());
		p.addField(Property.Fields.FURNISHED.toString(), furnished);
		String unit = rs.getString(Property.Fields.UNIT.toString());
		p.addField(Property.Fields.UNIT.toString(), unit);
		String moreFeatures1 = rs.getString(Property.Fields.MORE_FEATURES_1.toString());
		p.addField(Property.Fields.MORE_FEATURES_1.toString(), moreFeatures1);
		String moreFeatures2 = rs.getString(Property.Fields.MORE_FEATURES_2.toString());
		p.addField(Property.Fields.MORE_FEATURES_2.toString(), moreFeatures2);
		String moreFeatures3 = rs.getString(Property.Fields.MORE_FEATURES_3.toString());
		p.addField(Property.Fields.MORE_FEATURES_3.toString(), moreFeatures3);

		Short bedRooms = rs.getShort(Property.Fields.BEDROOMS.toString());
		p.addField(Property.Fields.BEDROOMS.toString(), bedRooms);
		Short bathRooms = rs.getShort(Property.Fields.BATHROOMS.toString());
		p.addField(Property.Fields.BATHROOMS.toString(), bathRooms);
		Short propertyType = rs.getShort(Property.Fields.PROPERTY_TYPE.toString());
		p.addField(Property.Fields.PROPERTY_TYPE.toString(), propertyType);
		Short yearBuilt = rs.getShort(Property.Fields.YEAR_BUILT.toString());
		p.addField(Property.Fields.YEAR_BUILT.toString(), yearBuilt);
		String propertyDescription = rs.getString(Property.Fields.PROPERTY_DESCRIPTION
				.toString());
		p.addField(Property.Fields.PROPERTY_DESCRIPTION.toString(), propertyDescription);
		//TODO ad the below location entities to solr schema
		String city = rs.getString(Property.Fields.city.toString());
		p.addField(Property.Fields.city.toString(), city);
		String locality = rs.getString(Property.Fields.locality.toString());
		p.addField(Property.Fields.locality.toString(), locality);
		String country = rs.getString(Property.Fields.country.toString());
		p.addField(Property.Fields.country.toString(), country);
		String neighbourhood = rs.getString(Property.Fields.neighbourhood.toString());
		p.addField(Property.Fields.neighbourhood.toString(), neighbourhood);
		String formattedAddress = rs.getString(Property.Fields.formattedAddress.toString());
		p.addField(Property.Fields.formattedAddress.toString(), formattedAddress);
		String rawAddress = rs.getString(Property.Fields.rawAddress.toString());
		p.addField(Property.Fields.rawAddress.toString(), rawAddress);
		int id = rs.getInt("id");
		p.addField(Property.Fields.locId.toString(), id);
		p.addField(Property.Fields.IMAGE_COUNT.toString(), rs.getShort(Property.Fields.IMAGE_COUNT.toString()));
		long agentPhoneNo = rs.getLong("PHONE_NO");
		p.addField(Property.Fields.AGENT_PHONE_NO.toString(), agentPhoneNo);
		p.addField(Property.Fields.AGENT_NAME.toString(), rs.getString("NAME"));
		

		return new PropStatus(ref, SourceConstants.INDEXED); // TODO if one of
																// required
																// fields is not
																// available,
																// set this ti
																// failFetch,
																// and throw
																// exception and
																// proceed with
																// next document
	}

}
