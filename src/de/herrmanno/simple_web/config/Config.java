package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.filter.FilterConfig;
import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;
import de.herrmanno.simple_web.plugin.Plugin;

public interface Config {

	public RouteConfig getRouteConfig();
	
	public TypeConfig getTypeConfig();
	
	public FilterConfig getFilterConfig();
	
	default public void register(Plugin plugin) {
		plugin.register(this);
	};
}
