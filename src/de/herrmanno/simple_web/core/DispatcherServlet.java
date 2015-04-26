package de.herrmanno.simple_web.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.core.filter.AnnotationFilter;
import de.herrmanno.simple_web.core.route.Route;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;
import de.herrmanno.simple_web.util.RouteAndRegex;

public abstract class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -164636491368179633L;
	
	public static Config config;
	//private LinkedList<Route> routes;
	private LinkedList<RouteAndRegex> routes = new LinkedList<RouteAndRegex>();

	
	@Override
	public void init() throws ServletException {
		try {
			DispatcherServlet.config = getConfig().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServletException("Your Config is bad!");
		}
		super.init();
		//routes = config.getRouteConfig().getRoutes();
		createRoutes();
	}

	protected void createRoutes() {
		for(Route r : config.getRouteConfig().getRoutes()) {
			RouteAndRegex rr = new RouteAndRegex(r, r.getFullRoute(config.getParameterConfig()));
			for(RouteAndRegex rr2 : routes) {
				if(rr.regex.pattern().equals(rr2.regex.pattern())) {
					String msg = "Unambigious Routes: " 
							+ rr.route.controller.getClass().getSimpleName() 
							+ "#"
							+ rr.route.method.method.getName()
							+ " And "
							+ rr2.route.controller.getClass().getSimpleName() 
							+ "#"
							+ rr2.route.method.method.getName();
					System.err.println(msg);
				}
			}
			
			routes.add(rr);
		}
		
		System.out.println("Routes:");
		for(RouteAndRegex rr : routes) {
			System.out.println(
				rr.regex + " -> " 
				+ rr.route.controller.getClass().getSimpleName() 
				+ "#"
				+ rr.route.method.method.getName()
			);
		}
	}

	abstract protected Class<? extends Config> getConfig();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(new Request(req), new Response(resp));
	}
	
	private void service(Request req, Response resp) throws ServletException, IOException {
		
		try {
			Object out = "";
			byte[] bytes = null;
			
			Matcher matcher;
			boolean found = false;
			for(RouteAndRegex rr : routes) {
				matcher = rr.regex.matcher(req.path); //Pattern.compile(route.getFullRoute()).matcher(req.path);
				if(matcher.matches()) {
					out = filter(req, resp, rr.route);
					if(out == null) 
						//out = route.function.apply(req, resp);
						//out = route.method.invoke(route.controller, createArgs(req, resp, route, matcher));
						out = rr.route.method.invoke(rr.route.controller, req, resp, matcher, config.getParameterConfig());
					bytes = config.getTypeConfig().handle(req, resp, out.getClass(), out);
					found = true;
					break;
				}
			}
			if(!found)
				throw new Exception("No matching route found");
			resp.send(bytes);
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.send(500, e.getMessage().getBytes());
		}
		
	}

	private Object filter(Request req, Response resp, Route route) {
		Object ret = null;
		for(AnnotationFilter f : config.getFilterConfig().getAnnotationFilter()) {
			for(Annotation a : route.filterAnnotations) {
				if(a.annotationType() == f.annotation) {
					req.filterAnnotations.put(a.annotationType(), a);
					if((ret = f.function.filter(req, resp)) != null) {
						return ret;
					}
					
				}
			}
		}
		
		return ret;
	}

	/*
	private Object[] createArgs(Request req, Response resp, Route route, Matcher matcher) throws Exception {
		List<Object> args = new LinkedList<Object>();
		args.add(req);
		args.add(resp);
		
		for(Parameter p : route.routeParams) {
			args.add(ParameterHelper.getValue(p.getType(), matcher.group(p.getName())));
		}
		
		return args.toArray();
	}
	*/
	
}
