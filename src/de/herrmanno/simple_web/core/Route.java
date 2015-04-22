package de.herrmanno.simple_web.core;

import de.herrmanno.simple_web.constants.HTTP_METHOD;

public class Route {

	HTTP_METHOD[] methods;
	String routeRegex;
	RouteFunction function;
	
	public Route(String route, RouteFunction function, HTTP_METHOD method) {
		this(route, function, new HTTP_METHOD[]{method});
	}
	
	Route(String route, RouteFunction function, HTTP_METHOD... methods) {
		this.routeRegex = route;
		this.function = function;
		this.methods = methods;
	}

}
