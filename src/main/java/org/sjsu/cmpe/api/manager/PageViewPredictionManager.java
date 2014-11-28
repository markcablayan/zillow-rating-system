package org.sjsu.cmpe.api.manager;

import java.util.HashMap;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.sjsu.cmpe.beans.ZillowProperty;

public class PageViewPredictionManager {
	
	public static final double MODEL_DELTA_VALUE = 1000;
	private static final int minImageCount = 10;
	private static final int imageScaleValue = 10;
	private static final double BATHROOM_SCALE_VALUE = 0.5;
	private static final double BEDROOM_SCALE_VALUE = 1.0;
	
	
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
	
	public HashMap<String,Double> calculateVariableIncreaseBedroomCount(RConnection c, ZillowProperty zillowProperty) {
		try {
			Double predictionValue = this.getInitialPredictionModel(c);
			if(predictionValue != null) {
				Double currentBedroomCount = zillowProperty.getBedrooms();
				while((predictionValue + 900) < zillowProperty.getPageViewCount() + 500) {
					currentBedroomCount += BEDROOM_SCALE_VALUE;
					String dataFrame = rmanager.getDataFrame(zillowProperty.getTaxAssessment(), zillowProperty.getFinishedSquareFt(), 
							zillowProperty.getBathrooms(), currentBedroomCount, 
							zillowProperty.getTotalRooms(), zillowProperty.getLastSoldPrice(), 
							zillowProperty.getZestimateAmount(), zillowProperty.getValuationLow(), 							zillowProperty.getValuationHigh(), zillowProperty.getZeestimatePercentile(), zillowProperty.getImageCount());
					c.eval(dataFrame);
					predictionValue = c.eval("predict(fit,newdata)").asDouble();
				}
				HashMap<String,Double> predictionMap = new HashMap<String, Double>();
				predictionMap.put("newBedroomCount", currentBedroomCount);
				predictionMap.put("predictedViewCount", predictionValue + MODEL_DELTA_VALUE);
				return predictionMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HashMap<String,Double> calculateVariableIncreaseBathroomCount(RConnection c, ZillowProperty zillowProperty) {
		try {
			Double predictionValue = this.getInitialPredictionModel(c);
			if(predictionValue != null) {
				Double currentBathroomCount = zillowProperty.getBathrooms();
				while((predictionValue + 900) < zillowProperty.getPageViewCount() + 500) {
					currentBathroomCount += BATHROOM_SCALE_VALUE;
					String dataFrame = rmanager.getDataFrame(zillowProperty.getTaxAssessment(), zillowProperty.getFinishedSquareFt(), 
							currentBathroomCount, zillowProperty.getBedrooms(), 
							zillowProperty.getTotalRooms(), zillowProperty.getLastSoldPrice(), 
							zillowProperty.getZestimateAmount(), zillowProperty.getValuationLow(), 							zillowProperty.getValuationHigh(), zillowProperty.getZeestimatePercentile(), zillowProperty.getImageCount());
					c.eval(dataFrame);
					predictionValue = c.eval("predict(fit,newdata)").asDouble();
				}
				HashMap<String,Double> predictionMap = new HashMap<String, Double>();
				predictionMap.put("newBathroomCount", currentBathroomCount);
				predictionMap.put("predictedViewCount", predictionValue + MODEL_DELTA_VALUE);
				return predictionMap;
			}
		} catch (Exception e) {
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
	
	private Double getInitialPredictionModel(RConnection c) {
		REXP prediction = null;
		try {
			prediction = c.eval("predict(fit,newdata)");
			if(prediction != null)
				return prediction.asDouble();
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
