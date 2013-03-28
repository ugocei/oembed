package com.adobe.cq.components.oembed;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.Link;
import org.xml.sax.SAXException;

public class LinkFinder {

	public List<Link> findLinks(InputStream input) throws IOException, SAXException, TikaException {
		OEmbedLinkHandler linkHandler = new OEmbedLinkHandler();
		Metadata metadata = new Metadata();
		ParseContext parseContext = new ParseContext();
		HtmlParser parser = new HtmlParser();
		parser.parse(input, linkHandler, metadata, parseContext);
		return linkHandler.getLinks();
	}
	
}
