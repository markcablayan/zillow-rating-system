package org.group.binary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.sjsu.cmpe.beans.ZillowPropertyDetailStage;
import org.sjsu.cmpe.beans.ZillowPropertyStage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ZillowPropertyDAOImpl {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ZillowPropertyStage> getZillowStage() {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		List<ZillowPropertyStage> zillowProperties = this.jdbcTemplate.query("SELECT * FROM zillowproperty_stage;", new ZillowStageRowMapper());
		return zillowProperties;
	}
	
	public void insert(Integer zpid, String addressStreet, Integer addressZip, String addressCity, 
			String addressState,Double addressLatitude, Double addressLongitude,
			Integer taxAssessmentYear, Double taxAssessment, Integer yearBuild,
			Integer finishedSquareFeet, Double bathrooms, Double bedrooms,
			Double totalRooms, String soldDate, Double lastSoldPrice,
			Double zestimateAmount, Double valuationLow, Double valuationHigh,
			Double percentile) {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.jdbcTemplate.update("insert into zillowproperty_stage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{
				 zpid,  addressStreet,  addressZip, addressCity,
				 addressState, addressLatitude,  addressLongitude,
				 taxAssessmentYear,  taxAssessment,  yearBuild,
				 finishedSquareFeet,  bathrooms,  bedrooms,
				 totalRooms,  soldDate,  lastSoldPrice,
				 zestimateAmount,  valuationLow,  valuationHigh,
				 percentile
		});
	}
	
	public void insertPropertyDetail(Integer zpid, Integer totalPageViewCount, Integer imageCount, String homeDescription) {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.jdbcTemplate.update("insert into zillowproperty_detail_stage values (?,?,?,?)",new Object[]{zpid,totalPageViewCount,imageCount,homeDescription});
	}
	
	private class ZillowStageRowMapper implements RowMapper<ZillowPropertyStage> {
		
	    public ZillowPropertyStage mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ZillowPropertyStage zillowPropertyStage = new ZillowPropertyStage();
			zillowPropertyStage.setZpID(rs.getInt("zpid"));
			zillowPropertyStage.setAddressStreet(rs.getString("address_street"));
			zillowPropertyStage.setAddressCity(rs.getString("address_city"));
			zillowPropertyStage.setAddressState(rs.getString("address_state"));
			zillowPropertyStage.setAddressZip(rs.getInt("address_zip"));
			zillowPropertyStage.setAddressLatitude(rs.getDouble("address_latitude"));
			zillowPropertyStage.setAddressLongitude(rs.getDouble("address_longitude"));
			zillowPropertyStage.setTaxAssessmentYear(rs.getInt("tax_assessment_year"));
			zillowPropertyStage.setTaxAssessment(rs.getDouble("tax_assessment"));
			zillowPropertyStage.setYearBuilt(rs.getInt("year_built"));
			zillowPropertyStage.setFinishedSquareFt(rs.getInt("finished_sq_ft"));
			zillowPropertyStage.setBathrooms(rs.getDouble("bathroom_count"));
			zillowPropertyStage.setBedrooms(rs.getDouble("bedroom_count"));
			zillowPropertyStage.setTotalRooms(rs.getDouble("total_room_count"));
			zillowPropertyStage.setSoldDate(rs.getString("last_sold_date"));
			zillowPropertyStage.setLastSoldPrice(rs.getDouble("last_sold_price"));
			zillowPropertyStage.setZestimateAmount(rs.getDouble("zestimate_amount"));
			zillowPropertyStage.setValuationLow(rs.getDouble("valuation_low"));
			zillowPropertyStage.setValuationHigh(rs.getDouble("valuation_high"));
			zillowPropertyStage.setZeestimatePercentile(rs.getDouble("percentile"));
			return zillowPropertyStage;
	    }

	}
	private class ZillowPropertyDetailStageRowMapper implements RowMapper<ZillowPropertyDetailStage> {
		
	    public ZillowPropertyDetailStage mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ZillowPropertyDetailStage zillowPropertyDetailStage = new ZillowPropertyDetailStage();
			zillowPropertyDetailStage.setZpid(rs.getInt("zpid"));
			zillowPropertyDetailStage.setTotalPageViewCount(rs.getInt("page_view_count"));
			zillowPropertyDetailStage.setImageCount(rs.getInt("image_count"));
			zillowPropertyDetailStage.setHomeDescription(rs.getString("home_description"));
			return zillowPropertyDetailStage;
	    }

	}
}
