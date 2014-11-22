package org.sjsu.cmpe.beans;

public class ZillowPropertyStage {
	
	public long zpID;
	public int taxAssessmentYear;
	public double taxAssessment;
	public int yearBuild;
	public int lotSizeSquareFt;
	public int finishedSquareFt;
	public double bathrooms;
	public double bedrooms;
	public double totalRooms;
	public long soldDate;
	public double lastSoldPrice;
	public String addressStreet;
	public int addressZip;
	public String addressCity;
	public String addressState;
	public double addressLatitude;
	public double addressLongitude;
	public int regionId;
	public int regionType;
	public String regionName;
	public double zestimateAmount;
	public long zestimateLastUpdate;
	public double zestimateValueChange;
	public double zeestimatePercentile;
	public int valueChangeDuration;
	public double valuationLow;
	public double valuationHigh;
	
	public ZillowPropertyStage ( long zpID, int taxAssessmentYear,
			double taxAssessment, int yearBuild, int lotSizeSquareFt,
			int finishedSquareFt, double bathrooms, double bedrooms,
			double totalRooms, long soldDate, double lastSoldPrice,
			String addressStreet, int addressZip, String addressCity,
			String addressState, double addressLatitude,
			double addressLongitude, int regionId, int regionType,
			String regionName, double zestimateAmount,
			long zestimateLastUpdate, double zestimateValueChange,
			double zeestimatePercentile, int valueChangeDuration,
			double valuationLow, double valuationHigh) {
		
		this.zpID = zpID;
		this.taxAssessmentYear = taxAssessmentYear;
		this.taxAssessment = taxAssessment;
		this.yearBuild = yearBuild;
		this.lotSizeSquareFt = lotSizeSquareFt;
		this.finishedSquareFt = finishedSquareFt;
		this.bathrooms = bathrooms;
		this.bedrooms = bedrooms;
		this.totalRooms = totalRooms;
		this.soldDate = soldDate;
		this.lastSoldPrice = lastSoldPrice;
		this.addressStreet = addressStreet;
		this.addressZip = addressZip;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressLatitude = addressLatitude;
		this.addressLongitude = addressLongitude;
		this.regionId = regionId;
		this.regionType = regionType;
		this.regionName = regionName;
		this.zestimateAmount = zestimateAmount;
		this.zestimateLastUpdate = zestimateLastUpdate;
		this.zestimateValueChange = zestimateValueChange;
		this.zeestimatePercentile = zeestimatePercentile;
		this.valueChangeDuration = valueChangeDuration;
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
	public int getYearBuild() {
		return yearBuild;
	}
	public void setYearBuild(int yearBuild) {
		this.yearBuild = yearBuild;
	}
	public int getLotSizeSquareFt() {
		return lotSizeSquareFt;
	}
	public void setLotSizeSquareFt(int lotSizeSquareFt) {
		this.lotSizeSquareFt = lotSizeSquareFt;
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
	public long getSoldDate() {
		return soldDate;
	}
	public void setSoldDate(long soldDate) {
		this.soldDate = soldDate;
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
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getRegionType() {
		return regionType;
	}
	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public double getZestimateAmount() {
		return zestimateAmount;
	}
	public void setZestimateAmount(double zestimateAmount) {
		this.zestimateAmount = zestimateAmount;
	}
	public long getZestimateLastUpdate() {
		return zestimateLastUpdate;
	}
	public void setZestimateLastUpdate(long zestimateLastUpdate) {
		this.zestimateLastUpdate = zestimateLastUpdate;
	}
	public double getZestimateValueChange() {
		return zestimateValueChange;
	}
	public void setZestimateValueChange(double zestimateValueChange) {
		this.zestimateValueChange = zestimateValueChange;
	}
	public double getZeestimatePercentile() {
		return zeestimatePercentile;
	}
	public void setZeestimatePercentile(double zeestimatePercentile) {
		this.zeestimatePercentile = zeestimatePercentile;
	}
	public int getValueChangeDuration() {
		return valueChangeDuration;
	}
	public void setValueChangeDuration(int valueChangeDuration) {
		this.valueChangeDuration = valueChangeDuration;
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
