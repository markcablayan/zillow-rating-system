package org.sjsu.cmpe.beans;

public class ZillowPropertyDetailStage {
	Integer zpid;
	Integer totalPageViewCount;
	Integer imageCount;
	String homeDescription;
	Long pageViewCount;
	
	public Integer getZpid() {
		return zpid;
	}
	public void setZpid(Integer zpid) {
		this.zpid = zpid;
	}
	public Integer getTotalPageViewCount() {
		return totalPageViewCount;
	}
	public void setTotalPageViewCount(Integer totalPageViewCount) {
		this.totalPageViewCount = totalPageViewCount;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	public String getHomeDescription() {
		return homeDescription;
	}
	public void setHomeDescription(String homeDescription) {
		this.homeDescription = homeDescription;
	}
	public void setPageViewCount(Long pageViewCount) {
		this.pageViewCount = pageViewCount;
	}
	public Long getPageViewCount() {
		return this.pageViewCount;
	}
}
