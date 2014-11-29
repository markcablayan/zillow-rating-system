package org.sjsu.cmpe.api.manager;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RManager {
	
	public static final String MODEL_EQUATION = "fit <- lm(page_view_count ~ tax_assessment + finished_sq_ft + bathroom_count + bedroom_count + total_room_count + last_sold_price + zestimate_amount + valuation_low + valuation_high + percentile + image_count,data=zillowdata)";
	
	public RConnection getRConnection() {
		try {
			REXP x;
			RConnection c = new RConnection();
			x = c.eval("R.version.string");
			System.out.println(x.asString());
			REXP directoryChange = c.eval("setwd('" + System.getProperty("user.dir") + "')");
			System.out.println(directoryChange.asString());
			REXP currentDirectory = c.eval("getwd()");
			System.out.println(currentDirectory.asString());
			c.voidEval("zillowdata = read.csv('rData2.csv')");
			if(c != null && c.isConnected()) {
				return c;
			}
		} catch (RserveException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public String getDataFrame(Double taxAssessment, Integer finishedSqFt, Double bathrooms,
			Double bedrooms, Double totalRooms, Double lastSoldPrice, Double zestimateAmount,
			Double valuationLow, Double valuationHigh, Double percentile, Integer imageCount) {
		String dataFrame = "newdata = data.frame("
				+ "tax_assessment=" + taxAssessment + ","
				+ "finished_sq_ft=" + finishedSqFt + ","
				+ "bathroom_count=" + bathrooms + ","
				+ "bedroom_count=" + bedrooms + ","
				+ "total_room_count=" + totalRooms + ","
				+ "last_sold_price=" + lastSoldPrice + ","
				+ "zestimate_amount=" + zestimateAmount + ","
				+ "valuation_low=" + valuationLow + ","
				+ "valuation_high=" + valuationHigh + ","
				+ "percentile=" + percentile + ","
				+ "image_count=" + imageCount 
				+ ")";
		return dataFrame;
	}
}
