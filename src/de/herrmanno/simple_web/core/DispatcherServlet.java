package de.herrmanno.simple_web.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.core.filter.AnnotationFilter;
import de.herrmanno.simple_web.core.route.Route;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public abstract class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -164636491368179633L;
	
	public static Config config;
	private LinkedList<Route> routes;
	//private LinkedList<RouteAndRegex> routes = new LinkedList<RouteAndRegex>();

	
	@Override
	public void init() throws ServletException {
		createConfig();
		super.init();
		//routes = config.getRouteConfig().getRoutes();
		createRoutes();
	}

	protected void createConfig() throws ServletException {
		try {
			DispatcherServlet.config = getConfig().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServletException("Your Config is bad!");
		}
	}

	protected void createRoutes() {
		routes = config.getRouteConfig().getRoutes();
		routes.forEach(System.out::println);
	}
	
	/*
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
	*/

	abstract protected Class<? extends Config> getConfig();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(new Request(req), new Response(resp));
	}
	
	protected void doRequest(Request req, Response resp) throws ServletException, IOException {
		
		try {

			Object out = "";
			byte[] bytes = null;
			
			/*
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
			*/
			
			Route route = findRoute(req);
			
			out = filter(req, resp, route);
			if(out == null) {
				//Matcher matcher = route.pattern.matcher(req.path);
				//out = route.method.invoke(route.controller, req, resp);
				out = route.invoke(req, resp);
				bytes = config.getTypeConfig().handle(req, resp, out);
			}
			resp.send(bytes);
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.send(500, e.getMessage().getBytes());
		}
		
	}

	protected Route findRoute(Request req) throws Exception {
		Route route = null;
		for(Route r : routes) {
			if(r.pattern.matcher(req.path).matches() && (r.method.methods.contains(req.method) || r.method.methods.contains(HTTP_METHOD.ALL))) {
				if(route == null)
					route = r;
				else {
					route = Route.getHigherByPrecedence(route, r);
					if(route == null)
						throw new Exception("Ambigious Routes");
				}
			}
		}
		
		if(route == null)
			throw new Exception("No route found");
		
		return route;
	}
	
	/*
	protected RouteAndRegex findRoute(Request req) throws Exception {
		Matcher matcher;
		boolean found = false;
		RouteAndRegex r = null;
		for(RouteAndRegex rr : routes) {
			matcher = rr.regex.matcher(req.path);
			if(matcher.matches()) {
				if(!found) {
					r = rr;
					found = true;
				} else {
					throw new Exception("Unambigious Route '" + req.path + "'");
				}
			}
		}
		
		return r;
	}
	*/

	protected Object filter(Request req, Response resp, Route route) {
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
