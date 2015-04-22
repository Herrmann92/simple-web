package de.herrmanno.simple_web.util;

import javax.servlet.http.HttpServletRequest;

public class Request {

	@SuppressWarnings("unused")
	private HttpServletRequest req;
	
	public final String path;

	public Request(HttpServletRequest req) {
		this.req = req;
		
		path = req.getPathInfo().startsWith("/") ? req.getPathInfo().substring(1) : req.getPathInfo();
	}
}
