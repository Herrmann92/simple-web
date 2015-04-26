package de.herrmanno.simple_web.config.route;

import java.util.LinkedList;

import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.core.route.Routable;
import de.herrmanno.simple_web.core.route.Route;

public interface RouteConfig {

	public LinkedList<Route> getRoutes();
	
	public void register(Routable route);
	
	default public void register(Routable... routes) {
		for(Routable route : routes) {
			register(route);
		}
	};
	
	default public void register(Controller controller) {
		register(controller.getRoutes());
	}
	
}
