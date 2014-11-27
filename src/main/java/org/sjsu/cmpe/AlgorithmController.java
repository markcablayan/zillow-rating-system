package org.sjsu.cmpe;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.sjsu.cmpe.api.manager.PageViewPredictionManager;
import org.sjsu.cmpe.api.manager.RManager;
import org.sjsu.cmpe.api.manager.RestApiManager;
import org.sjsu.cmpe.beans.ZillowProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlgorithmController {
	
	protected RManager rmanager;
	protected PageViewPredictionManager pageViewPredictionManager;
	
    @RequestMapping("/Rserve")
    public String getRserveData(@RequestParam(value="zpid", required=false, defaultValue="-") String zpid, Model model) {
    	RestApiManager restApiManager = new RestApiManager();
    	Document doc = restApiManager.getProperyDetails(ZillowGlobals.ZWS_ID, zpid);
    	XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("//response", Filters.element());
        //SAXBuilder builder = new SAXBuilder();
        List<Element> compElements = expr.evaluate(doc);
        Element e = null;
		if(compElements != null && compElements.size() > 0)
			e = compElements.get(0);

		Document comparableDoc = restApiManager.getPropertyComparables(ZillowGlobals.ZWS_ID, zpid, 1);
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
        	
        	
        	RConnection r = rmanager.getRConnection();
            if(r != null) {
            	try {
            		r.voidEval(RManager.MODEL_EQUATION);
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
            			pageViewPredictionManager.calculateVariableIncreaseImageCount(r, zp);
						System.out.println(prediction.asDouble() + PageViewPredictionManager.MODEL_DELTA_VALUE);
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

	public RManager getRmanager() {
		return rmanager;
	}

	public void setRmanager(RManager rmanager) {
		this.rmanager = rmanager;
	}

	public PageViewPredictionManager getPageViewPredictionManager() {
		return pageViewPredictionManager;
	}

	public void setPageViewPredictionManager(
			PageViewPredictionManager pageViewPredictionManager) {
		this.pageViewPredictionManager = pageViewPredictionManager;
	}
	
}