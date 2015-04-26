package de.herrmanno.simple_web.core.route;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.RouteMethod;
import de.herrmanno.simple_web.core.controller.Controller;

public class Route implements Routable {

	public final HTTP_METHOD[] methods;
	//public final String routeRegex;
	public final Controller controller;
	public final RouteMethod method;
	public final Collection<Annotation> filterAnnotations;
	public final List<Parameter> routeParams = new LinkedList<Parameter>();
	public String prefix;
	

	public Route(Controller controller, RouteMethod m) throws Exception {
		this.controller = controller;
		this.method = m;
		//this.routeRegex = createRouteRegex(controller, m);
		this.methods = m.getMethods();
		this.filterAnnotations = new HashSet<Annotation>(); 
		this.filterAnnotations.addAll(controller.getAnnotations());
		this.filterAnnotations.addAll(m.getAnnotations());
		
	}

	/*
	protected String createRouteRegex(Controller controller, RouteMethod m) throws Exception {
		String regex = controller.getContext();
		regex += "/";
		regex += m.getRoute();
		
		return regex;
	}
	*/

	public String getFullRoute(ParameterConfig pconfig) {
		String regex = controller.getContext();
		regex += "/";
		regex += method.getRoute(pconfig);
		
		
		regex = (prefix != null ? prefix +"/" : "") + regex;
		
		while(regex.startsWith("/")) {
			regex = regex.substring(1);
		}
		
		while(regex.endsWith("/")) {
			regex = regex.substring(0, regex.length()-1);
		}
		
		if(regex.matches("[/]+"))
			regex = "";
		
		
		return regex;
	}

	public void addPrefix(String p) {
		if(prefix == null) 
			prefix = p;
		else {
			prefix += "/" + p;
		}
	}
	

}
