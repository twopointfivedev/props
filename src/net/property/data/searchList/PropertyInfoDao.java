package net.property.data.searchList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.property.bean.location.LocationBean;
import net.property.data.AbstractDao;
import net.property.data.Property;
import net.property.data.searchList.rowMapper.PropListResultRowMapper;
import net.property.data.utility.NamedParameterStatement;
import net.property.search.Results;
import net.property.search.ResultsContainer;
import net.property.search.Searcher;
import net.property.utils.SourceConstants;
import net.property.web.transactionContext.PropertyListContext;

public class PropertyInfoDao extends AbstractDao {
	private static PropertyInfoDao thisObj = null;
	private DataSource ds = null;
	
	private PropertyInfoDao(){

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/property");
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
	
	public static PropertyInfoDao getInstance() {
		if (thisObj == null) {
			thisObj = new PropertyInfoDao();
		}
		return thisObj;
	}

	public ResultsContainer getResult(PropertyListContext propertyListContext,
			List<String> filtersApplied,
			LocationBean loc) {
		Connection conn = null;
		ResultsContainer resultsContainer = null;
		try {
		conn = ds.getConnection();

		StringBuilder query = new StringBuilder();
		String selectionFields = "select pl.*, pd.*,gl.*, age.NAME, age.COUNTRY_CODE ";
		query.append( " from propertylist pl ")
		.append("inner join propertydetails pd  on pl.PROPERTY_REF=pd.PROPERTY_REF ")
		.append("left join geo_locations gl on pd.GEO_LOCATION_REF=gl.locationId  ")
		.append("left join all_locations al on gl.norm_location_id=al.id ")
		.append(" left join agent age on pd.AGENT_PHONE_NO=age.PHONE_NO ")
		.append("where 1=1 and gl.norm_location_id!=0 ");
		setLocFilter(query,loc);
		//TODO add more filters here
		setPriceFilter(query,propertyListContext);
		setAreaFilter(query, propertyListContext);
		int totalCount = getTotalCount(query.toString(), conn);
		setSort(query, propertyListContext);
		
		String limit = " limit :startIndex, :limit ";
		NamedParameterStatement pstmt = new NamedParameterStatement(conn,selectionFields+ query.toString()+ limit);
		pstmt.setInt("startIndex", (propertyListContext.getPage()-1)*SourceConstants.PAGE_SIZE);
		pstmt.setInt("limit", SourceConstants.PAGE_SIZE);
		ResultSet rs = pstmt.executeQuery();
		List<Property> results = new PropListResultRowMapper().map(rs);
		resultsContainer = new ResultsContainer(results, totalCount, propertyListContext.getPage());

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultsContainer;
	}

	private void setAreaFilter(StringBuilder query,
			PropertyListContext propertyListContext) {
		if(propertyListContext.getfLowerGrossArea()>0){
			query.append(" and pd.GROSS_AREA > "+ propertyListContext.getfLowerGrossArea());
		}
		if(propertyListContext.getfUpperGrossArea() > 0){
			query.append(" and pd.GROSS_AREA < "+propertyListContext.getfUpperGrossArea());
		}
		if(propertyListContext.getfLowerSaleableArea()>0){
			query.append(" and pd.SALEABLE_AREA > "+propertyListContext.getfLowerSaleableArea());
		}
		if(propertyListContext.getfUpperSaleableArea()>0){
			query.append(" and pd.SALEABLE_AREA < "+propertyListContext.getfUpperSaleableArea());
		}
		
	}

	private void setPriceFilter(StringBuilder query,
			PropertyListContext propertyListContext) {
		String fromColumn = null;
		if(propertyListContext.getType() == SourceConstants.RENT){
			fromColumn = "pd.MONTHLY_RENTAL";
		}
		else if(propertyListContext.getType() == SourceConstants.SALE){
			fromColumn = "pd.TOTALPRICE";
		}
		else{
			return;/**Should be either rent or sale */
		}
		if(propertyListContext.getfLowerPrice()>0){
			query.append(" and "+fromColumn+" > "+propertyListContext.getfLowerPrice());
		}
		if(propertyListContext.getfUpperPrice()>0){
			query.append(" and "+fromColumn+" < "+propertyListContext.getfUpperPrice());
		}
		
	}

	private int getTotalCount(String query, Connection conn) throws SQLException {
		String totalCountQuery = "select count(pl.property_ref) ";
		PreparedStatement totalQueryStmt = conn.prepareStatement(totalCountQuery +query);
		ResultSet rs = totalQueryStmt.executeQuery();
		int total = 0;
		while(rs.next()){
			total = rs.getInt(1);
		}
		return total;
	}

	private void setSort(StringBuilder query, PropertyListContext propertyListContext) {
		if(Searcher.SORT_PRICE_LOW.equals(propertyListContext.getSort())) {
			if(propertyListContext.getType() == SourceConstants.RENT){
			query.append(" ORDER BY pd.MONTHLY_RENTAL ");
			}
			else if(propertyListContext.getType() == SourceConstants.SALE){
				query.append(" ORDER BY pd.TOTALPRICE ");
			}
		} else if(Searcher.SORT_PRICE_HIGH.equals(propertyListContext.getSort())) {
			if(propertyListContext.getType() == SourceConstants.RENT){
			query.append(" ORDER BY pd.MONTHLY_RENTAL desc ");
			}
			else if(propertyListContext.getType() == SourceConstants.SALE){
				query.append(" ORDER BY pd.TOTALPRICE desc ");
			}
		}
	}

	private boolean hasLocFilter(LocationBean loc) {
		if (loc != null && loc.getId() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void setLocFilter(StringBuilder query, LocationBean loc) {
		if(hasLocFilter(loc)){
			query.append(" and gl.norm_location_id=  "+loc.getId()+" ");
		}
	}

	public int getTotalDocs() {
		Connection conn = null;
		ResultsContainer resultsContainer = null;
		try{
			conn = ds.getConnection();
			String totalCountQuery = "select count(PROPERTY_REF) from propertylist ";
			PreparedStatement totalQueryStmt = conn.prepareStatement(totalCountQuery);
			ResultSet rs = totalQueryStmt.executeQuery();
			int total = 0;
			while(rs.next()){
				total = rs.getInt(1);
				break;
			}
			return total;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
