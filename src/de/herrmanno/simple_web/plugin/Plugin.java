package de.herrmanno.simple_web.plugin;

import de.herrmanno.simple_web.config.Config;

public interface Plugin {
	
	default public void startUp() {};

	public void register(Config config);
	
}
