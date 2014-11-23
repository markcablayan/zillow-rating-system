package org.sjsu.cmpe.api.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;



public class RestApiManager {
	
	private final String ZILLOW_API_URI = "http://www.zillow.com/webservice/";
	private final String PROPERTY_DETAILS_WS = "GetUpdatedPropertyDetails.htm";
	private final String PROPERTY_COMPS_WS = "GetDeepComps.htm";
	
	
	public Document executePOST(String webServiceURI,String... parameters) {
    	PostMethod postMethod = null;
    	Document response = null;
    	try {
        	postMethod = new PostMethod(webServiceURI);
        	int i=0;
        	while (i < parameters.length) {
                postMethod.addParameter(parameters[i],parameters[i+1]);
                i+=2;
            }
            HttpClient client = new HttpClient();
            client.executeMethod(postMethod);

            SAXBuilder saxReader = new SAXBuilder();
            response = saxReader.build(postMethod.getResponseBodyAsStream());
            
        } catch (Exception e) {
        	
        }
        finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        
        return response;
    }
	
	public Document executeGET(String webServiceURI,String... parameters) {
    	GetMethod getMethod = null;
    	Document response = null;
    	try {
        	getMethod = new GetMethod(ZILLOW_API_URI + webServiceURI);
        	ArrayList<NameValuePair>queryArr = new ArrayList<NameValuePair>();
        	int i=0;
        	while (i < parameters.length) {
                queryArr.add(new NameValuePair(parameters[i],parameters[i+1]));
                i+=2;
            }
        	
        	
        	NameValuePair[] nvpArr = new NameValuePair[queryArr.size()];
        	for(int l = 0; l < queryArr.size(); l++) {
        		nvpArr[l] = queryArr.get(l);
        	}
        	getMethod.setQueryString(nvpArr);
        	
            HttpClient client = new HttpClient();
            client.executeMethod(getMethod);

            SAXBuilder saxReader = new SAXBuilder();
            response = saxReader.build(getMethod.getResponseBodyAsStream());
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        
        return response;
    }
	
	public Document getProperyDetails(String zwsId, String zpId) {
		return this.executeGET(PROPERTY_DETAILS_WS,"zws-id",zwsId,"zpid",zpId);
	}
	
	public Document getPropertyComparables(String zwsId, String zpId, Integer numberOfComps) {
		return this.executeGET(PROPERTY_COMPS_WS, "zws-id",zwsId, "zpid",zpId, "count", numberOfComps.toString());
	}
	
	public HashSet<String> getZpidsFromComparables(Document document) {
		XPathFactory xFactory = XPathFactory.instance();
        XPathExpression<Element> expr = xFactory.compile("//comp/zpid", Filters.element());
        List<Element> comps = expr.evaluate(document);
        
//        ArrayList<String> compsStrFormat = new ArrayList<String>();
        HashSet<String> compsStrFormat = new HashSet<String>();
        for(Element element : comps) {
        	compsStrFormat.add(element.getValue());
        }
        return compsStrFormat;
	}
	
	public Cookie createSession(String redirectUrl) {
        Document response = executePOST(
                "a",
                "session/Create",
                "redirect_url",
                redirectUrl
        );
        
        if ((response != null) && (response.hasRootElement())) {
        	Element rootElement = response.getRootElement();
            String sessionId = rootElement.getChildText("id");
            String expiration = rootElement.getChildText("expiry_time");
            String name = rootElement.getChildText("name");
            String path = rootElement.getChildText("path");
            String domain = rootElement.getChildText("domain");
            String secure = rootElement.getChildText("secure");

	        Cookie cookie = new Cookie(
	                name,
	                sessionId
	        );
	        cookie.setDomain(domain);
	        cookie.setMaxAge(new Integer(expiration));
	        cookie.setPath(path);
	        cookie.setVersion(1);
	        
	        if ((secure != null) && (((secure.isEmpty())? 0 : new Integer(secure)) == 1)) {
	        	cookie.setSecure(true);
	        } else {
	            cookie.setSecure(false);
	        }
            return cookie;
        } else {
        	return null;
        }
        
    }
}