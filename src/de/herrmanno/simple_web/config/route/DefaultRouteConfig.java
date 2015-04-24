package de.herrmanno.simple_web.config.route;

import java.util.LinkedList;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.core.route.Nested;
import de.herrmanno.simple_web.core.route.Routable;
import de.herrmanno.simple_web.core.route.Route;

public class DefaultRouteConfig implements RouteConfig {

	protected LinkedList<Route> list = new LinkedList<Route>();
	protected Config config;

	
	public DefaultRouteConfig(Config config) {
		this.config = config;
	}

	@Override
	public Config getConfig() {
		return this.config;
	}

	public LinkedList<Route> getRoutes() {
		return list;
	}

	@Override
	public void register(Routable route) {
		if(route instanceof Route)
			register((Route) route);
		else if(route instanceof Nested)
			register((Nested) route);
		else if(route instanceof Controller)
			register((Controller) route);
	}
	
	protected void register(Route route) {
		list.add(route);
	}
	
	protected void register(Nested nested) {
		for(Routable r : nested.getRoutables()) {
			if(r instanceof Route) {
				((Route) r).addPrefix(nested.getPrefix());
			}
			else if(r instanceof Controller) {
				for(Route route : ((Controller) r).getRoutes(config.getParameterConfig()))
					route.addPrefix(nested.getPrefix());
			}
			else if(r instanceof Nested) {
				((Nested) r).addPrefix(nested.getPrefix());
			}
			
			register(r);
		}
	}

}
