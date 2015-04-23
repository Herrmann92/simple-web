package de.herrmanno.simple_web.util;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import de.herrmanno.simple_web.constants.HTTP_METHOD;

public class Request {

	@SuppressWarnings("unused")
	private HttpServletRequest req;
	
	public final String path;

	public final HTTP_METHOD method;

	public final Map<Class<? extends Annotation>, Annotation> filterAnnotations = new HashMap<Class<? extends Annotation>, Annotation>();

	public Request(HttpServletRequest req) {
		this.req = req;
		this.path = req.getPathInfo().startsWith("/") ? req.getPathInfo().substring(1) : req.getPathInfo();
		this.method = HTTP_METHOD.get(req);
	}
}
