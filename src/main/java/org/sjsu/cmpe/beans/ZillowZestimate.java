package org.sjsu.cmpe.beans;

public class ZillowZestimate {
	public double amount;
	public long lastUpdate;
	public double valueChange;
	public int valueChangeDuration;
	public ZillowValuationRange zillowValuationRange;
	public double percentile;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public double getValueChange() {
		return valueChange;
	}
	public void setValueChange(double valueChange) {
		this.valueChange = valueChange;
	}
	public int getValueChangeDuration() {
		return valueChangeDuration;
	}
	public void setValueChangeDuration(int valueChangeDuration) {
		this.valueChangeDuration = valueChangeDuration;
	}
	public ZillowValuationRange getZillowValuationRange() {
		return zillowValuationRange;
	}
	public void setZillowValuationRange(ZillowValuationRange zillowValuationRange) {
		this.zillowValuationRange = zillowValuationRange;
	}
	public double getPercentile() {
		return percentile;
	}
	public void setPercentile(double percentile) {
		this.percentile = percentile;
	}
}
