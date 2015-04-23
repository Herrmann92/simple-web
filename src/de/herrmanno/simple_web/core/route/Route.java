package de.herrmanno.simple_web.core.route;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.Controller;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public class Route {

	//TODO Enable a kind of nesting for Routes
	
	
	public final HTTP_METHOD[] methods;
	public final String routeRegex;
	public final RouteFunction function;
	public final Collection<Annotation> filterAnnotations;
	
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

	public Route(Controller controller, Method m, HTTP_METHOD... methods) {
		this.routeRegex = controller.getPath() + "/" + m.getName().toLowerCase();
		this.methods = methods;
		this.function = createRouteFunction(controller, m);
		this.filterAnnotations = getFilterAnnotations(controller, m);
		/*
		this(
			controller.getClass().getSimpleName() + "/" + m.getName(), 
			createRouteFunction(controller, m),
			HTTP_METHOD.get(m)
		);
		*/
	}
	
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



}
