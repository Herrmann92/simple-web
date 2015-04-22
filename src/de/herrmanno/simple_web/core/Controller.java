package de.herrmanno.simple_web.core;


public interface Controller {

	//void register(RouteConfig routeConfig);
	
	default public Route[] getRoutes() {
		//TODO create Routes based on reflection
		return null;
	};

}
