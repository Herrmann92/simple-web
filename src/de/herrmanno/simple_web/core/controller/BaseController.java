package de.herrmanno.simple_web.core.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.core.RouteMethod;
import de.herrmanno.simple_web.core.route.Route;

public class BaseController implements Controller {

	protected final String context;
	protected final LinkedList<Route> routes;
	protected final Collection<? extends Annotation> annotations;
	
	public BaseController(ParameterConfig pconfig) {
		context = createContext();
		routes = createRoutes(pconfig);
		annotations = createAnnotations();
	}
	
	protected String createContext() {
		de.herrmanno.simple_web.annotation.Controller a = getClass().getAnnotation(de.herrmanno.simple_web.annotation.Controller.class);
		if(a != null && !a.context().isEmpty())
			return a.context();
		
		String n = this.getClass().getSimpleName();
		
		if(n.toLowerCase().endsWith("controller")) n = n.substring(0, n.length()-"controller".length());
		
		return n.toLowerCase();
		
		/*
		String[] parts = n.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
		
		n = "";
		for(String p : parts) {
			n+= p.toLowerCase() + "/";
		}
		return n.substring(0, n.length()-1);
		
		*/
	}
	
	protected LinkedList<Route> createRoutes(ParameterConfig pconfig) {
		Class<?> clazz = this.getClass();
		Set<Method> methods = new HashSet<Method>();
		do {
			Method[] ms = clazz.getDeclaredMethods();
			for(Method m : ms) {
				if(RouteMethod.isRouteMethod(m)) {
					methods.add(m);
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		LinkedList<Route> routes = new LinkedList<Route>();
		for(Method m : methods) {
			try {
				routes.add(new Route(this, new RouteMethod(m, pconfig)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return routes;
	};
	

	protected Collection<? extends Annotation> createAnnotations() {
		return Arrays.asList(getClass().getAnnotations());
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public Route[] getRoutes(ParameterConfig pconfig) {
		return routes.toArray(new Route[]{});
	}

	@Override
	public Collection<? extends Annotation> getAnnotations() {
		return annotations;
	};
}
