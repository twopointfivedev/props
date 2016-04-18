package net.property.data.searchList.rowMapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.property.data.Property;
import net.property.utils.ImageUtils;

public class PropListResultRowMapper {

	public List<Property> map(ResultSet rs) throws SQLException {
		List<Property> properties = new ArrayList<Property>();
		if(rs!=null){
			while(rs.next()){
				Property property = new Property();
				String propertyRef = (String)rs.getString(Property.Fields.PROPERTY_REF.toString());
				property.setPropertyRef(propertyRef);
				property.setPropertyDetailsUrl((String)rs.getString(Property.Fields.PROPERTY_DETAILS_URL.toString()));
				property.setTitle((String)rs.getString(Property.Fields.TITLE.toString()));
				property.setAddress((String)rs.getString(Property.Fields.formattedAddress.toString()));
				int locId = (Integer)rs.getInt("norm_location_id");
				property.setNeighbourhood((String)rs.getString(Property.Fields.neighbourhood.toString()));
				
				if (rs.getString(Property.Fields.PROPERTY_DESCRIPTION.toString()) != null) {
					property.setPropertyDescription((String) rs.getString
							(Property.Fields.PROPERTY_DESCRIPTION.toString()));
				}
				
					property.setMonthlyRental(rs.getInt(Property.Fields.MONTHLY_RENTAL.toString()));
				property.setBedRooms( rs.getInt(Property.Fields.BEDROOMS.toString()));
				property.setGrossArea( rs.getInt(Property.Fields.GROSS_AREA.toString()));
				property.setSaleableArea((Integer) rs.getInt(Property.Fields.SALEABLE_AREA.toString()));
				property.setUnit( rs.getString(Property.Fields.UNIT.toString()));
				property.setCrawlTime(rs.getLong(Property.Fields.CRAWL_TIME.toString()));
				property.setTotalPrice(rs.getLong(Property.Fields.TOTALPRICE.toString()));
				property.setCurrency((String) rs.getString(Property.Fields.CURRENCY.toString()));
				property.setFurnished((String) rs.getString(Property.Fields.FURNISHED.toString()));
				property.setPropertyType(rs.getInt(Property.Fields.PROPERTY_TYPE.toString()));
				property.setBathRooms(rs.getInt(Property.Fields.BATHROOMS.toString()));
				property.setYearBuilt(rs.getInt(Property.Fields.YEAR_BUILT.toString()));
				property.setImageCount(rs.getInt(Property.Fields.IMAGE_COUNT.toString()));
				int propertyType = rs.getInt(Property.Fields.PROPERTY_TYPE.toString());
				String source = rs.getString(Property.Fields.SOURCE.toString());
				String folder = ImageUtils.getPath(propertyRef, propertyType, source);
				property.setThumbnailPath(folder+File.separator+"small"+File.separator+propertyRef+"_1.jpg");
				property.setBigImagePath(folder + File.separator+"large"+File.separator+propertyRef+"_");
				property.setAgentPhoneNo(rs.getLong(Property.Fields.AGENT_PHONE_NO.toString()));
				property.setAgentName(rs.getString("name"));
//				if(doc.get(Property.Fields.HOUSE_TYPE.toString()) !=null)
//					result.setHouseType((String)doc.get(Property.Fields.HOUSE_TYPE.toString()));
				
//				if(doc.get(Property.Fields.AGENT.toString()) !=null)
//					result.setAgent((String)doc.get(Property.Fields.AGENT.toString()));
				
//				if(doc.get(Property.Fields.IMAGE_TYPE.toString()) !=null)
//					result.setImageType((String)doc.get(Property.Fields.IMAGE_TYPE.toString()));
				
//				if(doc.get(Property.Fields.IMAGES.toString()) !=null)
//					result.setImages((Integer)doc.get(Property.Fields.IMAGES.toString()));
				
				if(rs.getString(Property.Fields.SOURCE.toString()) !=null){
					property.setSource(rs.getString(Property.Fields.SOURCE.toString()));
				}
				properties.add(property);
			}
		}
		return properties;
	}

}
