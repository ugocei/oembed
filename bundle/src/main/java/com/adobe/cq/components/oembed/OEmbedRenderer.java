package com.adobe.cq.components.oembed;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.Link;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


public class OEmbedRenderer {


	private JSONObject data;
	private HttpClient client = new HttpClient();
	private LinkFinder linkFinder = new LinkFinder();
	private final Logger logger = LoggerFactory. getLogger(OEmbedRenderer.class);

	public boolean discoverLink(String url) {
		HttpMethod method = null;
        try {
			method = new GetMethod(url);
	    	method.setFollowRedirects(true);
	    	client.executeMethod(method);
	    	InputStream input = method.getResponseBodyAsStream();
	    	List<Link> links = linkFinder.findLinks(input);
	    	if (links.isEmpty()) {
	    		return false;
	    	}
	    	return fetchResponse(links.get(0).getUri());
		} catch (IOException e) {
			throw new OEmbedException(e);
		} catch (SAXException e) {
			throw new OEmbedException(e);
		} catch (TikaException e) {
			throw new OEmbedException(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		
	}
	
	public boolean fetchResponse(String endpoint, String url, Integer maxWidth, Integer maxHeight) {
        	try {
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new OEmbedException(e);
			}
        	String requestUrl = endpoint + "?url=" + url +
        			"&format=json";
        	if (maxWidth != null) {
        		requestUrl += "&maxwidth=" + maxWidth;
        	}
        	if (maxHeight != null) {
        		requestUrl += "&maxheight=" + maxHeight;
        	}
        	return fetchResponse(requestUrl);
	}
	
	public boolean fetchResponse(String requestUrl) {
		logger.debug("Requesting URL " + requestUrl);
		HttpMethod method = null;
        try {
			method = new GetMethod(requestUrl);
	    	method.setFollowRedirects(true);
	        // Create a response handler
	    	client.executeMethod(method);
	    	String responseBody = method.getResponseBodyAsString();
	    	data = new JSONObject(responseBody);
	    	return true;
		} catch (IOException e) {
			throw new OEmbedException(e);
		} catch (JSONException e) {
			throw new OEmbedException("Input is not well-formed JSON", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	public OEmbedType getType() {
		try {
			return OEmbedType.valueOf(data.getString("type").toUpperCase());
		} catch (JSONException e) {
			throw new OEmbedException("No 'type' attribute in JSON input", e);
		}
	}
	
	public String getTitle() {
		try {
			return data.has("title") ? data.getString("title") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getURL() {
		try {
			return data.has("url") ? data.getString("url") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getHTML() {
		try {
			return data.has("html") ? data.getString("html") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public int getWidth() {
		try {
			return data.getInt("width");
		} catch (JSONException e) {
			throw new OEmbedException("No 'width' attribute in JSON input", e);
		}
	}
	
	public int getHeight() {
		try {
			return data.getInt("height");
		} catch (JSONException e) {
			throw new OEmbedException("No 'height' attribute in JSON input", e);
		}
	}
	
	public String getAuthorName() {
		try {
			return data.has("author_name") ? data.getString("author_name") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getAuthorURL() {
		try {
			return data.has("author_url") ? data.getString("author_url") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getProviderName() {
		try {
			return data.has("provider_name") ? data.getString("provider_name") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getProviderURL() {
		try {
			return data.has("provider_url") ? data.getString("provider_url") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public String getThumbnailURL() {
		try {
			return data.has("thumbnail_url") ? data.getString("thumbnail_url") : null; 
		} catch (JSONException e) {
			throw new OEmbedException(e);
		}
	}
	
	public int getThumbnailWidth() {
		try {
			return data.getInt("thumbnail_width");
		} catch (JSONException e) {
			throw new OEmbedException("No 'thumbnail_width' attribute in JSON input", e);
		}
	}
	
	public int getThumbnailHeight() {
		try {
			return data.getInt("thumbnail_height");
		} catch (JSONException e) {
			throw new OEmbedException("No 'thumbnail_height' attribute in JSON input", e);
		}
	}
	
}
