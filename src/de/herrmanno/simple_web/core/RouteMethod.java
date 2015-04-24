package de.herrmanno.simple_web.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import de.herrmanno.simple_web.annotation.Route;
import de.herrmanno.simple_web.config.parameter.ParameterConfig;
import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public class RouteMethod {

	protected final Method method;
	protected final Parameter[] routeParameters;
	protected final String route;
	protected final HTTP_METHOD[] methods;
	protected final Route annotation;
	protected final Collection<? extends Annotation> annotations;
	private ParameterConfig pconfig;
	
	public RouteMethod(Method m, ParameterConfig pconfig) throws Exception {
		this.pconfig = pconfig;
		this.method = m;
		this.routeParameters = createRouteParams();
		this.route = createRouteRegex();
		this.methods = createMethods();
		this.annotation = method.getAnnotation(Route.class);
		this.annotations = createAnnotations();
	}
		
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

	protected Parameter[] createRouteParams() throws Exception {
		if(!isRouteMethod(method))
			throw new Exception("Method is not acceptable as RouteMethod");
		Parameter[] params = method.getParameters();
		return Arrays.copyOfRange(params, 2, params.length);
	}

	protected HTTP_METHOD[] createMethods() {
		if(annotation != null && annotation.methods().length > 0) {
			return annotation.methods();
		} else {
			String n = method.getName().toLowerCase();
			if(n.startsWith("get")) return new HTTP_METHOD[] {HTTP_METHOD.GET};
			else if(n.startsWith("post")) return new HTTP_METHOD[] {HTTP_METHOD.POST};
			else if(n.startsWith("put")) return new HTTP_METHOD[] {HTTP_METHOD.PUT};
			else if(n.startsWith("delete")) return new HTTP_METHOD[] {HTTP_METHOD.DELETE};
			else return new HTTP_METHOD[] {HTTP_METHOD.ALL};
		}
	}

	protected Collection<? extends Annotation> createAnnotations() {
		return Arrays.asList(method.getAnnotations());
	}

	public Method getMethod() {
		return method;
	}
	
	public String getRoute() {
		return route;
	}

	public Parameter[] getRouteParams() {
		return routeParameters;
	}


	public HTTP_METHOD[] getMethods() {
		return methods;
	}

	public Collection<? extends Annotation> getAnnotations() {
		return annotations;
	}

	public Object invoke(Controller controller, Request req, Response resp, Matcher matcher) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> args = new LinkedList<Object>();
		args.add(req);
		args.add(resp);
		
		for(Parameter p : routeParameters) {
			//args.add(ParameterHelper.getValue(p.getType(), matcher.group(p.getName())));
			args.add(pconfig.getParameterHandler(p.getType()).handle(matcher.group(p.getName())));
		}
		
		return method.invoke(controller, args.toArray());
	}

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
