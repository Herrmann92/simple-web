package de.herrmanno.simple_web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.herrmanno.simple_web.core.route.Route;

public class RouteAndRegex {

	public final Route route;
	public final Pattern regex;
	protected Matcher matcher;
	
	public RouteAndRegex(Route route, String regex) {
		this.route = route;
		this.regex = Pattern.compile(regex);
	}
	
	public void setMatcher(String path) {
		this.matcher = this.regex.matcher(path);
	}
	/*
	public void invoke(Request req, Response resp) {
		return route.method.invoke(this.route.controller, req, resp, this.matcher, pconfig);
	}
	*/
	
}
