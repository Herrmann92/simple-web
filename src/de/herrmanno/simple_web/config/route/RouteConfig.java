package de.herrmanno.simple_web.config.route;

import java.util.LinkedList;

import de.herrmanno.simple_web.core.Controller;
import de.herrmanno.simple_web.core.route.Route;

public interface RouteConfig {

	public LinkedList<Route> getRoutes();
	
	public void register(Route route);
	
	default public void register(Route... routes) {
		for(Route route : routes) {
			register(route);
		}
	};
	
	default public void register(Controller controller) {
		register(controller.getRoutes());
	}
	
}
