package org.sjsu.cmpe.api.manager;

import java.util.HashMap;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.sjsu.cmpe.beans.ZillowProperty;

public class PageViewPredictionManager {
	
	public static final double MODEL_DELTA_VALUE = 1000;
	private static final int minImageCount = 20;
	private static final int imageScaleValue = 10;
	
	protected RManager rmanager;
	
	public HashMap<String,Double> calculateVariableIncreaseImageCount(RConnection c,ZillowProperty zillowProperty) {
		if(zillowProperty.getImageCount() <= minImageCount)
			return null;
		try {
			REXP prediction = c.eval("predict(fit,newdata)");
			Double predictionValue = null;
			if(prediction != null)
				predictionValue = prediction.asDouble();
			if(predictionValue != null) {
				int currentImageCount = zillowProperty.getImageCount();
				while((predictionValue + MODEL_DELTA_VALUE) < zillowProperty.getPageViewCount() + 500) {
					currentImageCount += imageScaleValue;
					String dataFrame = rmanager.getDataFrame(zillowProperty.getTaxAssessment(), zillowProperty.getFinishedSquareFt(), 
							zillowProperty.getBathrooms(), zillowProperty.getBedrooms(), 
							zillowProperty.getTotalRooms(), zillowProperty.getLastSoldPrice(), 
							zillowProperty.getZestimateAmount(), zillowProperty.getValuationLow(), 							zillowProperty.getValuationHigh(), zillowProperty.getZeestimatePercentile(), currentImageCount);
					c.eval(dataFrame);
            		predictionValue = c.eval("predict(fit,newdata)").asDouble();
				}
				HashMap<String,Double> predictionMap = new HashMap<String, Double>();
				predictionMap.put("newImageCount", (double) currentImageCount);
				predictionMap.put("predictedViewCount", predictionValue + MODEL_DELTA_VALUE);
				return predictionMap;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public RManager getRmanager() {
		return rmanager;
	}

	public void setRmanager(RManager rmanager) {
		this.rmanager = rmanager;
	}
	
}
