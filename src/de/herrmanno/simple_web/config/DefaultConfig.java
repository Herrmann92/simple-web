package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.filter.DefaultFilterConfig;
import de.herrmanno.simple_web.config.filter.FilterConfig;
import de.herrmanno.simple_web.config.route.DefaultRouteConfig;
import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.DefaultTypeConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;

public class DefaultConfig implements Config{

	protected RouteConfig routeConfig = new DefaultRouteConfig();
	protected TypeConfig typeConfig = new DefaultTypeConfig();
	protected FilterConfig filterConfig = new DefaultFilterConfig();
	
	
	@Override
	public RouteConfig getRouteConfig() {
		return routeConfig;
	}

	@Override
	public TypeConfig getTypeConfig() {
		return typeConfig;
	}

	@Override
	public FilterConfig getFilterConfig() {
		return filterConfig ;
	}

}
