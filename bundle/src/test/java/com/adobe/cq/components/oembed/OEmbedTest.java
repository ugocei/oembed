package com.adobe.cq.components.oembed;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.sax.Link;
import org.junit.Test;

import com.adobe.cq.components.oembed.LinkFinder;
import com.adobe.cq.components.oembed.OEmbedRenderer;
import com.adobe.cq.components.oembed.OEmbedType;

public class OEmbedTest {

	@Test
	public void testPhoto() {
		String endpoint = "http://www.flickr.com/services/oembed";
		String url = "http://flickr.com/photos/bees/2362225867";
		OEmbedRenderer renderer = new OEmbedRenderer();
		renderer.fetchResponse(endpoint, url, 240, 180);
		assertEquals(OEmbedType.PHOTO, renderer.getType());
		assertEquals("http://farm4.staticflickr.com/3040/2362225867_4a87ab8baf_m.jpg", renderer.getURL());
		assertEquals(240, renderer.getWidth());
		assertEquals(180, renderer.getHeight());
	}

	@Test
	public void testVideo() {
		String endpoint = "http://www.youtube.com/oembed";
		String url = "http://www.youtube.com/watch?v=-UUx10KOWIE";
		OEmbedRenderer renderer = new OEmbedRenderer();
		renderer.fetchResponse(endpoint, url, null, null);
		assertEquals(OEmbedType.VIDEO, renderer.getType());
		System.out.println(renderer.getHTML());
	}
	
	@Test
	public void testReadLinks() throws Exception {
		LinkFinder finder = new LinkFinder();
		InputStream input = this.getClass().getResourceAsStream("/flickr.html");
		List<Link> links = finder.findLinks(input);
		assertEquals(1, links.size());
		Link link = links.get(0);
		assertEquals("http://www.flickr.com/services/oembed?url=http%3A%2F%2Fwww.flickr.com%2Fphotos%2Fugocei%2F8473777164%2F&format=json",
				link.getUri());
		
	}

	@Test
	public void testDiscoverPhoto() {
		String url = "http://flickr.com/photos/bees/2362225867";
		OEmbedRenderer renderer = new OEmbedRenderer();
		assertTrue(renderer.discoverLink(url));
		assertEquals(OEmbedType.PHOTO, renderer.getType());
		assertEquals("http://farm4.staticflickr.com/3040/2362225867_4a87ab8baf_b.jpg", renderer.getURL());
		assertEquals(1024, renderer.getWidth());
		assertEquals(768, renderer.getHeight());
	}

}
