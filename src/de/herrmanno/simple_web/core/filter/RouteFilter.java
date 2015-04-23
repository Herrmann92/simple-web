package de.herrmanno.simple_web.core.filter;

import de.herrmanno.simple_web.constants.HTTP_METHOD;

public class RouteFilter implements Filter {

	private final String route;
	private final FilterFunction function;
	private final HTTP_METHOD method;

	public RouteFilter(String route, FilterFunction function) {
		this(route, function, HTTP_METHOD.ALL);
	}
	
	public RouteFilter(String route, FilterFunction function, HTTP_METHOD method) {
		this.route = route;
		this.function = function;
		this.method = method;
	}
}
