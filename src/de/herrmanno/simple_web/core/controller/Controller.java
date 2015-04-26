package de.herrmanno.simple_web.core.controller;

import java.lang.annotation.Annotation;
import java.util.Collection;

import de.herrmanno.simple_web.core.route.Routable;
import de.herrmanno.simple_web.core.route.Route;


public interface Controller extends Routable {

	public String getContext();
	
	public Route[] getRoutes();
	
	public Collection<? extends Annotation> getAnnotations();

}
