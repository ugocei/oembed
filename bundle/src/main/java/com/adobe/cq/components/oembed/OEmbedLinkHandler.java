package com.adobe.cq.components.oembed;

import static org.apache.tika.sax.XHTMLContentHandler.XHTML;

import java.util.ArrayList;
import java.util.List;

import org.apache.tika.sax.Link;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class OEmbedLinkHandler extends DefaultHandler {

	public static final String LINK_TYPE_OEMBED_JSON = "application/json+oembed";
	
	/** Collected links */
	private final List<Link> links = new ArrayList<Link>();

    /**
     * Returns the list of collected links.
     *
     * @return collected links
     */
    public List<Link> getLinks() {
        return links;
    }

	@Override
	public void startElement(String uri, String local, String name, Attributes attributes) {
		if (XHTML.equals(uri)) {
			if ("link".equals(local)) {
				String type = attributes.getValue("", "type");
				if (LINK_TYPE_OEMBED_JSON.equals(type)) {
					String href = attributes.getValue("", "href");
					String title = attributes.getValue("", "title");
					String rel = attributes.getValue("", "rel");
					Link link = new Link(type, href, title, "", rel);
					links.add(link);
				}
			}
		}
	}
}
