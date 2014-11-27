package org.sjsu.cmpe;

import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.sjsu.cmpe.api.manager.RestApiManager;
import org.sjsu.cmpe.beans.ZillowProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlgorithmController {

	public static final String ZWS_ID = "X1-ZWz1dx2j09otmz_4rdd9";
	private static final String MODEL_EQUATION = "fit <- lm(page_view_count ~ tax_assessment + finished_sq_ft + bathroom_count + bedroom_count + total_room_count + last_sold_price + zestimate_amount + valuation_low + valuation_high + percentile + image_count,data=zillowdata)";
	private static final double MODEL_DELTA_VALUE = 1000;
	private static final int minImageCount = 20;
	private static final int imageScaleValue = 10;
    @RequestMapping("/Rserve")
    public String getRserveData(@RequestParam(value="zpid", required=false, defaultValue="-") String zpid, Model model) {
    	RestApiManager restApiManager = new RestApiManager();
    	Document doc = restApiManager.getProperyDetails(ZWS_ID, zpid);
    	XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("//response", Filters.element());
        //SAXBuilder builder = new SAXBuilder();
        List<Element> compElements = expr.evaluate(doc);
        Element e = null;
		if(compElements != null && compElements.size() > 0)
			e = compElements.get(0);

		Document comparableDoc = restApiManager.getPropertyComparables(ZWS_ID, zpid, 1);
		XPathFactory compXPathFactory = XPathFactory.instance();
        XPathExpression<Element> compExpression = compXPathFactory.compile("//principal", Filters.element());
		List<Element> comparableElements = compExpression.evaluate(comparableDoc);
		Element c = null;
		if(comparableElements != null && comparableElements.size() > 0)
			c = comparableElements.get(0);
		
        if(e != null && c != null) {
        	//Get zpid
        	Integer id = toInteger(e.getChildText("zpid"));
        	
        	//Get address info
        	Element address = e.getChild("address");
        	String addressStreet = address.getChildText("street");
        	Integer addressZipCode = toInteger(address.getChildText("zipcode"));
        	String addressCity = address.getChildText("city");
        	String addressState = address.getChildText("state");
        	Double addressLatitude = toDouble(address.getChildText("latitude"));
        	Double addressLongitude = toDouble(address.getChildText("longitude"));
        	
        	//Get view count info
        	Element pageViewCount = e.getChild("pageViewCount");
        	Integer totalPageViewCount = toInteger(pageViewCount.getChildText("total"));
        	
        	Element images = e.getChild("images");
        	
        	Integer imageCount = 0;
        	if(images != null) {
        		imageCount = toInteger(images.getChildText("count"));
        	}
        	
        	String homeDescription = e.getChildText("homeDescription");
        	
        	model.addAttribute("totalPageViewCount",totalPageViewCount);
        	model.addAttribute("imageCount",imageCount);
        	model.addAttribute("homeDescription",homeDescription);
        	model.addAttribute("addressStreet",addressStreet);
        	model.addAttribute("addressZipCode",addressZipCode);
        	model.addAttribute("addressCity",addressCity);
        	model.addAttribute("addressState",addressState);
        	model.addAttribute("addressLatitude",addressLatitude);
        	model.addAttribute("addressLongitude",addressLongitude);

        	Double taxAssessment = toDouble(c.getChildText("taxAssessment"));
        	Integer finishedSqFt = toInteger(c.getChildText("finishedSqFt"));
        	Double bathrooms = toDouble(c.getChildText("bathrooms"));
        	Double bedrooms = toDouble(c.getChildText("bedrooms"));
        	Double totalRooms = toDouble(c.getChildText("totalRooms"));
        	Double lastSoldPrice = (toDouble(c.getChildText("lastSoldPrice")) == null)? 0 : toDouble(c.getChildText("lastSoldPrice"));
        	Element zestimate = c.getChild("zestimate");
        	Double zestimateAmount = toDouble(zestimate.getChildText("amount"));
        	Element valuationRange = zestimate.getChild("valuationRange");
        	Double valuationLow = toDouble(valuationRange.getChildText("low"));
        	Double valuationHigh = toDouble(valuationRange.getChildText("high"));
        	Double percentile = toDouble(zestimate.getChildText("percentile"));
        	
        	//zillowDao.insertPropertyDetail(zpid, totalPageViewCount, (imageCount == null)? 0 : imageCount, homeDescription);
        	ZillowProperty zp = new ZillowProperty();
        	zp.setTaxAssessment(taxAssessment);
        	zp.setFinishedSquareFt(finishedSqFt);
        	zp.setBathrooms(bathrooms);
        	zp.setBedrooms(bedrooms);
        	zp.setTotalRooms(totalRooms);
        	zp.setLastSoldPrice(lastSoldPrice);
        	zp.setZestimateAmount(zestimateAmount);
        	zp.setValuationLow(valuationLow);
        	zp.setValuationHigh(valuationHigh);
        	zp.setZeestimatePercentile(percentile);
        	zp.setImageCount(imageCount);
        	zp.setPageViewCount(totalPageViewCount);
        	
        	
        	RConnection r = getRConnection();
            if(r != null) {
            	try {
            		r.voidEval(MODEL_EQUATION);
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
            		r.eval(dataFrame);
            		REXP prediction = r.eval("predict(fit,newdata)");
            		
            		try {
            			calculateVariableIncreaseImageCount(r, zp);
						System.out.println(prediction.asDouble() + MODEL_DELTA_VALUE);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
    			} catch (RserveException e1) {
    				e1.printStackTrace();
    			}
            }
        }
        
        model.addAttribute("name", zpid);
        
        
        return "rserve";
    }
    
    private static Integer toInteger(String integerStr) {
		if(integerStr == null || integerStr.isEmpty())
			return null;
		else
			return new Integer(integerStr);
	}
	
	private static Double toDouble(String doubleStr) {
		if(doubleStr == null || doubleStr.isEmpty())
			return null;
		else
			return new Double(doubleStr);
	}
	
	private RConnection getRConnection() {
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
	
	private HashMap<String,Double> calculateVariableIncreaseImageCount(RConnection c,ZillowProperty zillowProperty) {
		if(zillowProperty.getImageCount() <= 0)
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
					String dataFrame = getDataFrame(zillowProperty.getTaxAssessment(), zillowProperty.getFinishedSquareFt(), 
							zillowProperty.getBathrooms(), zillowProperty.getBedrooms(), 
							zillowProperty.getTotalRooms(), zillowProperty.getLastSoldPrice(), 
							zillowProperty.getZestimateAmount(), zillowProperty.getValuationLow(), 							zillowProperty.getValuationHigh(), zillowProperty.getZeestimatePercentile(), currentImageCount);
					c.eval(dataFrame);
            		predictionValue = c.eval("predict(fit,newdata)").asDouble();
				}
				HashMap<String,Double> predictionMap = new HashMap<String, Double>();
				predictionMap.put("newImageCount", (double) currentImageCount);
				predictionMap.put("predictedViewCount", predictionValue);
				return predictionMap;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getDataFrame(Double taxAssessment, Integer finishedSqFt, Double bathrooms,
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