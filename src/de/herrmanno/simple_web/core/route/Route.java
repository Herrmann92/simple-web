package de.herrmanno.simple_web.core.route;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.herrmanno.simple_web.config.parameter.MethodParameter;
import de.herrmanno.simple_web.core.RouteMethod;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public class Route implements Routable {


	public final Pattern pattern;
	
	public final Controller controller;
	public final RouteMethod method;
	public final Collection<Annotation> filterAnnotations;
	public String prefix;
	
	//public final List<Parameter> routeParams = new LinkedList<Parameter>();
	//public final HTTP_METHOD[] methods;
	

	public Route(Controller controller, RouteMethod m) throws Exception {
		this.controller = controller;
		this.method = m;
		
		this.pattern = createPattern(controller, m);
		
		this.filterAnnotations = new HashSet<Annotation>(); 
		this.filterAnnotations.addAll(controller.getAnnotations());
		this.filterAnnotations.addAll(m.getAnnotations());

		//this.methods = m.getMethods();
		
	}

	
	protected Pattern createPattern(Controller controller, RouteMethod m) throws Exception {
		String regex = controller.getContext();
		regex += "/";
		regex += method.getRoute();
		
		
		regex = (prefix != null ? prefix +"/" : "") + regex;
		
		while(regex.startsWith("/")) {
			regex = regex.substring(1);
		}
		
		while(regex.endsWith("/")) {
			regex = regex.substring(0, regex.length()-1);
		}
		
		if(regex.matches("[/]+"))
			regex = "";
		
		return Pattern.compile(regex);
		//return regex;
	}
	
	public Pattern getPattern() {
		return pattern;
	}

	/*
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
	*/
	

	public void addPrefix(String p) {
		if(prefix == null) 
			prefix = p;
		else {
			prefix += "/" + p;
		}
	}
	
	public Object invoke(Request req, Response resp) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> args = new LinkedList<Object>();
		args.add(req);
		args.add(resp);
		
		Matcher matcher = getPattern().matcher(req.path);
		matcher.matches();
		for(Parameter p : method.getParams()) {
			//args.add(ParameterHelper.getValue(p.getType(), matcher.group(p.getName())));
			args.add(MethodParameter.getValue(p.getType(), matcher.group(p.getName())));
		}
		
		return method.getMethod().invoke(controller, args.toArray());
	}


	public static Route getHigherByPrecedence(Route r1, Route r2) {
		List<Parameter> p1 = r1.method.getParams();
		List<Parameter> p2 = r2.method.getParams();
		
		for(int i = 0; i  < Math.max(p1.size(), p2.size()); i++) {
			
			if(p1.size() >= i && p2.size() < i)
				return r1;
			else if(p2.size() >= i && p1.size() < i)
				return r2;
			
			if(MethodParameter.get(p1.get(i).getType()).precedence < (MethodParameter.get(p2.get(i).getType()).precedence))
				return r1;
			else if(MethodParameter.get(p1.get(i).getType()).precedence > (MethodParameter.get(p2.get(i).getType()).precedence))
				return r2;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return "Route: " + getPattern() + " @ " + controller.getClass().getSimpleName() + "#" + method.getMethod().getName();
	}
}