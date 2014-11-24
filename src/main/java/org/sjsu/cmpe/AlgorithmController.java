package org.sjsu.cmpe;

import java.util.List;

import org.sjsu.cmpe.api.manager.RestApiManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

@Controller
public class AlgorithmController {

	public static final String ZWS_ID = "X1-ZWz1dx2j09otmz_4rdd9";
    @RequestMapping("/Rserve")
    public String greeting(@RequestParam(value="zpid", required=false, defaultValue="-") String zpid, Model model) {
    	RestApiManager restApiManager = new RestApiManager();
    	Document doc = restApiManager.getProperyDetails(ZWS_ID, zpid);
    	XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("//response", Filters.element());
        //SAXBuilder builder = new SAXBuilder();
        List<Element> compElements = expr.evaluate(doc);
        Element e = null;
		if(compElements != null && compElements.size() > 0)
			e = compElements.get(0);

        if(e != null) {
        	//Get zpid
        	Integer id = toInteger(e.getChildText("zpid"));
        	
        	//Get address info
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
        	//zillowDao.insertPropertyDetail(zpid, totalPageViewCount, (imageCount == null)? 0 : imageCount, homeDescription);
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

}