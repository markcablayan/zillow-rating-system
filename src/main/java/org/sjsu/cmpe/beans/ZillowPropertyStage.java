package org.sjsu.cmpe.beans;

public class ZillowPropertyStage {
	
	public long zpID;
	public String addressStreet;
	public int addressZip;
	public String addressCity;
	public String addressState;
	public double addressLatitude;
	public double addressLongitude;
	public int taxAssessmentYear;
	public double taxAssessment;
	public int yearBuilt;
	public int finishedSquareFt;
	public double bathrooms;
	public double bedrooms;
	public double totalRooms;
	public String lastSoldDate;
	public double lastSoldPrice;
	public double zestimateAmount;
	public String zestimateLastUpdate;
	public double valuationLow;
	public double valuationHigh;
	public double zestimatePercentile;
	
	public ZillowPropertyStage(){}
	
	public ZillowPropertyStage ( long zpID, int taxAssessmentYear,
			double taxAssessment, int yearBuilt,
			int finishedSquareFt, double bathrooms, double bedrooms,
			double totalRooms, String lastSoldDate, double lastSoldPrice,
			String addressStreet, int addressZip, String addressCity,
			String addressState, double addressLatitude,
			double addressLongitude, double zestimateAmount,
			String zestimateLastUpdate,
			double zestimatePercentile,
			double valuationLow, double valuationHigh) {
		
		this.zpID = zpID;
		this.taxAssessmentYear = taxAssessmentYear;
		this.taxAssessment = taxAssessment;
		this.yearBuilt = yearBuilt;
		this.finishedSquareFt = finishedSquareFt;
		this.bathrooms = bathrooms;
		this.bedrooms = bedrooms;
		this.totalRooms = totalRooms;
		this.lastSoldDate = lastSoldDate;
		this.lastSoldPrice = lastSoldPrice;
		this.addressStreet = addressStreet;
		this.addressZip = addressZip;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressLatitude = addressLatitude;
		this.addressLongitude = addressLongitude;
		this.zestimateAmount = zestimateAmount;
		this.zestimateLastUpdate = zestimateLastUpdate;
		this.zestimatePercentile = zestimatePercentile;
		this.valuationLow = valuationLow;
		this.valuationHigh = valuationHigh;
	}
	
	public long getZpID() {
		return zpID;
	}
	public void setZpID(long zpID) {
		this.zpID = zpID;
	}
	public int getTaxAssessmentYear() {
		return taxAssessmentYear;
	}
	public void setTaxAssessmentYear(int taxAssessmentYear) {
		this.taxAssessmentYear = taxAssessmentYear;
	}
	public double getTaxAssessment() {
		return taxAssessment;
	}
	public void setTaxAssessment(double taxAssessment) {
		this.taxAssessment = taxAssessment;
	}
	public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	public int getFinishedSquareFt() {
		return finishedSquareFt;
	}
	public void setFinishedSquareFt(int finishedSquareFt) {
		this.finishedSquareFt = finishedSquareFt;
	}
	public double getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(double bathrooms) {
		this.bathrooms = bathrooms;
	}
	public double getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(double bedrooms) {
		this.bedrooms = bedrooms;
	}
	public double getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(double totalRooms) {
		this.totalRooms = totalRooms;
	}
	public String getSoldDate() {
		return lastSoldDate;
	}
	public void setSoldDate(String lastSoldDate) {
		this.lastSoldDate = lastSoldDate;
	}
	public double getLastSoldPrice() {
		return lastSoldPrice;
	}
	public void setLastSoldPrice(double lastSoldPrice) {
		this.lastSoldPrice = lastSoldPrice;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public int getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(int addressZip) {
		this.addressZip = addressZip;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	public double getAddressLatitude() {
		return addressLatitude;
	}
	public void setAddressLatitude(double addressLatitude) {
		this.addressLatitude = addressLatitude;
	}
	public double getAddressLongitude() {
		return addressLongitude;
	}
	public void setAddressLongitude(double addressLongitude) {
		this.addressLongitude = addressLongitude;
	}
	public double getZestimateAmount() {
		return zestimateAmount;
	}
	public void setZestimateAmount(double zestimateAmount) {
		this.zestimateAmount = zestimateAmount;
	}
	public String getZestimateLastUpdate() {
		return zestimateLastUpdate;
	}
	public void setZestimateLastUpdate(String zestimateLastUpdate) {
		this.zestimateLastUpdate = zestimateLastUpdate;
	}
	public double getZeestimatePercentile() {
		return zestimatePercentile;
	}
	public void setZeestimatePercentile(double zestimatePercentile) {
		this.zestimatePercentile = zestimatePercentile;
	}
	public double getValuationLow() {
		return valuationLow;
	}
	public void setValuationLow(double valuationLow) {
		this.valuationLow = valuationLow;
	}
	public double getValuationHigh() {
		return valuationHigh;
	}
	public void setValuationHigh(double valuationHigh) {
		this.valuationHigh = valuationHigh;
	}
}
