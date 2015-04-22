package de.herrmanno.simple_web.config;

import de.herrmanno.simple_web.config.route.RouteConfig;
import de.herrmanno.simple_web.config.type.TypeConfig;
import de.herrmanno.simple_web.plugin.Plugin;

public interface Config {

	public RouteConfig getRouteConfig();
	
	public TypeConfig getTypeConfig();
	
	default public void register(Plugin plugin) {
		for(String p : plugin.dependencies()) {
			Package pckg = Package.getPackage(p);
			if(null == pckg) {
				System.err.println("Error while registering Plugin '" + plugin.getClass().getName() + "':");
				System.err.println("Required Package '"+p+"' not found!");
			}
		}
		plugin.register(this);
	};
}
