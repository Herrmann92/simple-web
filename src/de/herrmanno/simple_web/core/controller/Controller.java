package de.herrmanno.simple_web.core.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.core.RouteMethod;
import de.herrmanno.simple_web.core.route.Routable;
import de.herrmanno.simple_web.core.route.Route;


public interface Controller extends Routable {

	public String getContext();
	
	public Route[] getRoutes(ParameterConfig pconfig);
	
	public Collection<? extends Annotation> getAnnotations();

}
