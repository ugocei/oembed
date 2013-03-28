package com.adobe.cq.components.oembed;

public class OEmbedException extends RuntimeException {

	public OEmbedException() {
		super();
	}

	public OEmbedException(String msg, Throwable t) {
		super(msg, t);
	}

	public OEmbedException(String msg) {
		super(msg);
	}

	public OEmbedException(Throwable t) {
		super(t);
	}

}
