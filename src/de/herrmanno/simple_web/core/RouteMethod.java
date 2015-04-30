package de.herrmanno.simple_web.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import de.herrmanno.simple_web.annotation.Route;
import de.herrmanno.simple_web.config.parameter.MethodParameter;
import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public class RouteMethod {

	protected final Method method;
	protected final LinkedList<Parameter> params;
	protected final Set<HTTP_METHOD> methods;
	protected final Route annotation;
	protected final Collection<? extends Annotation> annotations;
	//protected final String route;
	//private ParameterConfig pconfig;
	
	public RouteMethod(Method m) throws Exception {
		//this.pconfig = pconfig;
		this.method = m;
		this.params = createRouteParams();
		//this.route = createRouteRegex();
		this.methods = createMethods();
		this.annotation = method.getAnnotation(Route.class);
		this.annotations = createAnnotations();
	}
	
	/*
	protected String createRouteRegex() throws Exception {
		String r = "";
		if(annotation != null && !annotation.regex().isEmpty()) {
			r += annotation.regex();
		}
		else if(!method.getName().toLowerCase().equals("index"))
			r += method.getName().toLowerCase();
		
		for(Parameter p : routeParameters) {
			r += "/";
			r += "(?<" + p.getName() + ">";
			r += pconfig.getParameterHandler(p.getType()).regex();
			r += ")";
		}
		
		return r;
	}
	*/

	protected LinkedList<Parameter> createRouteParams() throws Exception {
		if(!isRouteMethod(method))
			throw new Exception("Method is not acceptable as RouteMethod");
		Parameter[] params = method.getParameters();
		//return Arrays.copyOfRange(params, 2, params.length);
		LinkedList<Parameter> list = new LinkedList<Parameter>();
		for(int i = 2; i < params.length; i++) {
			list.add(params[i]);
		}
		
		return list;
	}

	protected Set<HTTP_METHOD> createMethods() {
		Set<HTTP_METHOD> set = new HashSet<HTTP_METHOD>();
		if(annotation != null && annotation.methods().length > 0) {
			//return annotation.methods()
			set.addAll(Arrays.asList(annotation.methods()));
		} else {
			String n = method.getName().toLowerCase();
			if(n.startsWith("get")) set.add(HTTP_METHOD.GET); //return new HTTP_METHOD[] {HTTP_METHOD.GET};
			else if(n.startsWith("post")) set.add(HTTP_METHOD.POST); //return new HTTP_METHOD[] {HTTP_METHOD.POST};
			else if(n.startsWith("put")) set.add(HTTP_METHOD.PUT); //return new HTTP_METHOD[] {HTTP_METHOD.PUT};
			else if(n.startsWith("delete")) set.add(HTTP_METHOD.DELETE); //return new HTTP_METHOD[] {HTTP_METHOD.DELETE};
			else set.add(HTTP_METHOD.ALL); //return new HTTP_METHOD[] {HTTP_METHOD.ALL};
		}
		
		return set;
	}

	protected Collection<? extends Annotation> createAnnotations() {
		return Arrays.asList(method.getAnnotations());
	}

	public Method getMethod() {
		return method;
	}
	
	
	public String getRoute() {
		String r = "";
		if(annotation != null && !annotation.regex().isEmpty()) {
			r += annotation.regex();
		}
		else if(!method.getName().toLowerCase().equals("index"))
			r += method.getName().toLowerCase();
		
		for(Parameter p : params) {
			r += "/";
			r += "(?<" + p.getName() + ">";
			r += MethodParameter.getRegex(p.getType());
			r += ")";
		}
		
		while(r.startsWith("/")) {
			r = r.substring(1);
		}
		
		while(r.endsWith("/")) {
			r = r.substring(0, r.length()-1);
		}
		
		return r;
		//return route;
	}
	
	/*
	public String getRoute(ParameterConfig pconfig) {
		String r = "";
		if(annotation != null && !annotation.regex().isEmpty()) {
			r += annotation.regex();
		}
		else if(!method.getName().toLowerCase().equals("index"))
			r += method.getName().toLowerCase();
		
		for(Parameter p : routeParameters) {
			r += "/";
			r += "(?<" + p.getName() + ">";
			r += pconfig.getParameterHandler(p.getType()).regex();
			r += ")";
		}
		
		return r;
		//return route;
	}
	*/

	public LinkedList<Parameter> getParams() {
		return params;
	}


	public Set<HTTP_METHOD> getMethods() {
		return methods;
	}

	public Collection<? extends Annotation> getAnnotations() {
		return annotations;
	}
	

	public Object invoke(Controller controller, Request req, Response resp, Matcher matcher) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> args = new LinkedList<Object>();
		args.add(req);
		args.add(resp);
		
		for(Parameter p : params) {
			//args.add(ParameterHelper.getValue(p.getType(), matcher.group(p.getName())));
			args.add(MethodParameter.getValue(p.getType(), matcher.group(p.getName())));
		}
		
		return method.invoke(controller, args.toArray());
	}
	
	/*
	public Object invoke(Controller controller, Request req, Response resp, Matcher matcher, ParameterConfig pconfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> args = new LinkedList<Object>();
		args.add(req);
		args.add(resp);
		
		for(Parameter p : routeParameters) {
			//args.add(ParameterHelper.getValue(p.getType(), matcher.group(p.getName())));
			args.add(pconfig.getParameterHandler(p.getType()).handle(matcher.group(p.getName())));
		}
		
		return method.invoke(controller, args.toArray());
	}
	*/

	public static boolean isRouteMethod(Method m) {
		Class<?>[] p = m.getParameterTypes();
		Class<?> r = m.getReturnType();
		if(p.length >= 2 && p[0].equals(Request.class) && p[1].equals(Response.class) && !r.equals(Void.class)) {
			return true;
		} else {
			return false;
		}
	}
}
