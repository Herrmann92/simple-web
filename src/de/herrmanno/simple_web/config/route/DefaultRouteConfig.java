package de.herrmanno.simple_web.config.route;

import java.util.LinkedList;

import de.herrmanno.simple_web.core.route.Route;

public class DefaultRouteConfig implements RouteConfig {

	private LinkedList<Route> list = new LinkedList<Route>();

	
	public void register(Route route) {
		list.add(route);
	}
	
	public LinkedList<Route> getRoutes() {
		return list;
	}

}
