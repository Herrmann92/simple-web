package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.filter.FilterConfig;
import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;
import de.herrmanno.simple_web.constants.MODE;
import de.herrmanno.simple_web.plugin.Plugin;

public interface Config {

	public MODE getMote();
	
	public RouteConfig getRouteConfig();
	
	public FilterConfig getFilterConfig();

	public TypeConfig getTypeConfig();
	
	public ParameterConfig getParameterConfig();
	
	
	default public void register(Plugin plugin) {
		plugin.register(this);
	};
	
	default public void register(Plugin... plugins) {
		for(Plugin p : plugins)
			register(p);
	}
}
