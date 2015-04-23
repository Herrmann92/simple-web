package de.herrmanno.simple_web.core;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import de.herrmanno.simple_web.core.route.Route;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public interface Controller {

	default public String getPath() {
		String n = this.getClass().getSimpleName();
		
		if(n.toLowerCase().endsWith("controller")) n = n.substring(0, n.length()-"controller".length());
		
		String[] parts = n.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
		
		n = "";
		for(String p : parts) {
			n+= p.toLowerCase() + "/";
		}
		return n.substring(0, n.length()-1);
	}
	
	default public Route[] getRoutes() {
		Class<?> clazz = this.getClass();
		Set<Method> methods = new HashSet<Method>();
		do {
			Method[] ms = clazz.getDeclaredMethods();
			for(Method m : ms) {
				Class<?>[] p = m.getParameterTypes();
				Class<?> r = m.getReturnType();
				if(p.length == 2 && p[0].equals(Request.class) && p[1].equals(Response.class) && r.equals(Object.class)) {
					methods.add(m);
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		Set<Route> routes = methods.stream().map((m) -> new Route(this, m)).collect(Collectors.toSet());
		
		return routes.toArray(new Route[]{});
		
	};
	
	default public Method method(String name) {
		Class<?> clazz = this.getClass();
		Class<?>[] p;
		Class<?> r;
		
		do {
			for(Method m : clazz.getDeclaredMethods()) {
				p = m.getParameterTypes();
				r = m.getReturnType();
				if(p.length == 2 && p[0].equals(Request.class) && p[1].equals(Response.class) && r.equals(Object.class)) {
					return m;
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		return null;
	}

}
