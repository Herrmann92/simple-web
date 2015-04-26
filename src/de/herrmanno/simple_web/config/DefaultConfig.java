package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.filter.DefaultFilterConfig;
import de.herrmanno.simple_web.config.filter.FilterConfig;
import de.herrmanno.simple_web.config.parameter.DefaultParameterConfig;
import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.config.route.DefaultRouteConfig;
import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.DefaultTypeConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;
import de.herrmanno.simple_web.constants.MODE;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.plugin.BasePlugin;
import de.herrmanno.simple_web.plugin.Plugin;

public class DefaultConfig implements Config {

	protected RouteConfig routeConfig;
	protected FilterConfig filterConfig;
	protected TypeConfig typeConfig;
	protected ParameterConfig parameterConfig;
	
	protected Plugin[] plugins = {new BasePlugin()};
	protected Controller[] controllers = {};
	
	public DefaultConfig() {
		routeConfig = new DefaultRouteConfig();
		filterConfig = new DefaultFilterConfig();
		typeConfig = new DefaultTypeConfig();
		parameterConfig = new DefaultParameterConfig();
		
		init();
	}

	protected void init() {
		register(plugins);
		routeConfig.register(controllers);
	}
	
	@Override
	public RouteConfig getRouteConfig() {
		return routeConfig;
	}

	@Override
	public FilterConfig getFilterConfig() {
		return filterConfig ;
	}

	@Override
	public TypeConfig getTypeConfig() {
		return typeConfig;
	}

	@Override
	public ParameterConfig getParameterConfig() {
		return parameterConfig;
	}

	@Override
	public MODE getMote() {
		return MODE.DEV;
	}
}
