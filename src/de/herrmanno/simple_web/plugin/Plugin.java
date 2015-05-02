package de.herrmanno.simple_web.plugin;

import de.herrmanno.simple_web.core.DispatcherServlet;

public interface Plugin {
	
	default public void startUp() {};

	public void register(DispatcherServlet dispatcherServlet);
	
}
