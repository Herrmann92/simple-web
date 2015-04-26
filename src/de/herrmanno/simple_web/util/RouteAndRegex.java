package de.herrmanno.simple_web.util;

import java.util.regex.Pattern;

import de.herrmanno.simple_web.core.route.Route;

public class RouteAndRegex {

	public final Route route;
	public final Pattern regex;
	
	public RouteAndRegex(Route route, String regex) {
		this.route = route;
		this.regex = Pattern.compile(regex);
	}
	
}
