package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.route.DefaultRouteConfig;
import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.DefaultTypeConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;

public class DefaultConfig implements Config{

	protected RouteConfig routeConfig = new DefaultRouteConfig();
	protected TypeConfig typeConfig = new DefaultTypeConfig();
	
	
	@Override
	public RouteConfig getRouteConfig() {
		return routeConfig;
	}

	@Override
	public TypeConfig getTypeConfig() {
		return typeConfig;
	}

}
