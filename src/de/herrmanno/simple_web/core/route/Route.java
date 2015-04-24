package de.herrmanno.simple_web.core.route;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.RouteMethod;
import de.herrmanno.simple_web.core.controller.Controller;

public class Route implements Routable {

	//TODO Enable a kind of nesting for Routes
	
	
	public final HTTP_METHOD[] methods;
	public final String routeRegex;
	public final Controller controller;
	public final RouteMethod method;
	public final Collection<Annotation> filterAnnotations;
	public final List<Parameter> routeParams = new LinkedList<Parameter>();
	public String prefix;
	

	public Route(Controller controller, RouteMethod m) throws Exception {
		this.controller = controller;
		this.method = m;
		this.routeRegex = createRouteRegex(controller, m);
		this.methods = m.getMethods();
		this.filterAnnotations = new HashSet<Annotation>(); 
		this.filterAnnotations.addAll(controller.getAnnotations());
		this.filterAnnotations.addAll(m.getAnnotations());
		//getFilterAnnotations(controller, m.getMethod());

		//this.function = createRouteFunction(controller, m);
		/*
		this(
			controller.getClass().getSimpleName() + "/" + m.getName(), 
			createRouteFunction(controller, m),
			HTTP_METHOD.get(m)
		);
		*/
	}

	protected String createRouteRegex(Controller controller, RouteMethod m) throws Exception {
		String regex = controller.getContext();
		regex += "/";
		regex += m.getRoute();
		
		return regex;
	}

	public String getFullRoute() {
		return (prefix != null ? prefix +"/" : "") + routeRegex;
	}

	public void addPrefix(String p) {
		if(prefix == null) 
			prefix = p;
		else {
			prefix += "/" + p;
		}
	}
	
	
	/*
	public Route(String route, RouteFunction function) {
		this(route, function, HTTP_METHOD.ALL);
	}
	
	public Route(String route, RouteFunction function, HTTP_METHOD method) {
		this(route, function, new HTTP_METHOD[]{method});
	}
	
	public Route(String route, RouteFunction function, HTTP_METHOD... methods) {
		this.routeRegex = route;
		this.function = function;
		this.methods = methods;
	}
	
	
	public Route(String route, RouteFunction function, FilterFunction... filters) {
		this(route, function, new HTTP_METHOD[]{ALL}, filters);
	}
	
	public Route(String route, RouteFunction function, HTTP_METHOD[] methods, FilterFunction... filters) {
		this(route, function, methods);
		this.filterF = filters;
	}
	*/
	
	/*
	private String createParameterRegex(Parameter p) {
		String r = "";
		
		String name = p.getName();
		Class<?> type = p.getType();
		String group = null;
		
		if(Number.class.isAssignableFrom(type))
			group = "[0-9]+";
		if(String.class.isAssignableFrom(type))
			group = "[A-Za-z0-9]+";
		
		r += "(?<"+name+">"+group+")";
		
		
		if(group == null) {
			return null;
		} else{
			this.routeParams.add(p);
			return r;
		}
	}
	*/

	/*
	private Set<Annotation> getFilterAnnotations(Controller controller, Method m) {
		Set<Annotation> ann = new HashSet<Annotation>();
		//HashMap<Class<? extends Annotation>, Annotation> ann = new HashMap<Class<? extends Annotation>, Annotation>();
		
		for(Annotation a : controller.getClass().getDeclaredAnnotations())
			//ann.put(a.getClass(), a); 
			ann.add(a);
		
		for(Annotation a : m.getDeclaredAnnotations())
			//ann.put(a.getClass(), a);
			ann.add(a);
		
		return ann;
	}
	*/

	/*
	private static RouteFunction createRouteFunction(Controller controller, Method m) {
		return new RouteFunction() {
			
			@Override
			public Object apply(Request req, Response resp) {
				try {
					return m.invoke(controller, req, resp);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}
	*/



}
