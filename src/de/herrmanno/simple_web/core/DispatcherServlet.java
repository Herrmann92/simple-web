package de.herrmanno.simple_web.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.herrmanno.simple_web.constants.HTTP_METHOD;
import de.herrmanno.simple_web.constants.MODE;
import de.herrmanno.simple_web.core.controller.Controller;
import de.herrmanno.simple_web.core.filter.AnnotationFilter;
import de.herrmanno.simple_web.core.filter.RouteFilter;
import de.herrmanno.simple_web.core.route.Route;
import de.herrmanno.simple_web.exceptions.NoRouteFoundException;
import de.herrmanno.simple_web.exceptions.NoTypeHandlerFoundException;
import de.herrmanno.simple_web.exceptions.SimpleWebException;
import de.herrmanno.simple_web.exceptions.UaAmbigiousRouteException;
import de.herrmanno.simple_web.plugin.BasePlugin;
import de.herrmanno.simple_web.plugin.Plugin;
import de.herrmanno.simple_web.typehandler.TypeHandler;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public abstract class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -164636491368179633L;
	
	protected final LinkedList<Route> routes = new LinkedList<Route>();
	protected final Set<AnnotationFilter> aFilter = new HashSet<AnnotationFilter>();
	protected final Set<RouteFilter> rFilter = new HashSet<RouteFilter>();
	protected final HashMap<Class<?>, TypeHandler<?>> handlers = new HashMap<Class<?>, TypeHandler<?>>();

	public final boolean PROD, TEST, DEV;
	
	public DispatcherServlet() {
		this(MODE.DEV);
	}
	
	public DispatcherServlet(MODE m) {
		switch (m) {
		case PROD:
			this.PROD = true;
			this.TEST = this.DEV = false;
			break;
		case TEST:
			this.TEST = true;
			this.PROD = this.DEV = false;
			break;
		case DEV:
			this.DEV = true;
			this.TEST = this.PROD = false;
			break;
		default:
			this.DEV = true;
			this.TEST = this.PROD = false;
			break;
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		register(new BasePlugin());
	}
	
	public void register(Plugin... plugins) {
		for(Plugin plugin : plugins)
			plugin.register(this);
	}

	public void register(RouteFilter... filters) {
		for(RouteFilter filter : filters)
			rFilter.add(filter);
	}

	public void register(AnnotationFilter... filters) {
		for(AnnotationFilter filter : filters)
			aFilter.add(filter);
	}
	
	public void register(Controller... controllers) {
		for(Controller controller : controllers)
			for(Route r : controller.getRoutes()) {
				routes.add(r);
			}
	}

	
	public void register(TypeHandler<?>... typeHandlers) {
		for(TypeHandler<?> typeHandler : typeHandlers) {
			Class<?> clazz = getHandleType(typeHandler);
			handlers.put(clazz, typeHandler);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> TypeHandler<T> getTypeHandler(Class<T> clazz) {
		TypeHandler<T> handler = (TypeHandler<T>) handlers.get(clazz);
		
		if(handler == null) {
			if((clazz = (Class<T>) clazz.getSuperclass()) != null) {
				return getTypeHandler(clazz);
			} else {
				return null;
			}
		}
		
		return handler;
	}

	protected Class<?> getHandleType(TypeHandler<?> typeHandler) {
		Class<?> clazz;
		if((clazz = typeHandler.getHandledType()) != null)
			return clazz;
		
		clazz = typeHandler.getClass();
		do {
			for(Method m : clazz.getDeclaredMethods()) {
				if(m.getName().equals("handle")) {
					return m.getParameterTypes()[0];
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> byte[] handle(Request req, Response resp, T obj) throws Exception {
		TypeHandler<T> handler = (TypeHandler<T>) getTypeHandler(obj.getClass());
		if(handler == null)
			throw new NoTypeHandlerFoundException(obj.getClass().getSimpleName());
		return handler.handle((T) obj, req, resp);
	};

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doRequest(new Request(req), new Response(resp));
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(500);
		}
	}
	
	protected void doRequest(Request req, Response resp) throws Exception {
		

		Object out = "";
		byte[] bytes = null;
		
		try {
			Route route = findRoute(req);
			out = filter(req, resp, route);
			if(out == null) {
				out = route.invoke(req, resp);
				bytes = handle(req, resp, out);
			}
		} catch(SimpleWebException e) {
			bytes = handle(req, resp, e);
		}
		
		
		resp.send(bytes);
			
		
	}

	protected Route findRoute(Request req) throws SimpleWebException {
		Route route = null;
		for(Route r : routes) {
			if(r.pattern.matcher(req.path).matches() && (r.method.methods.contains(req.method) || r.method.methods.contains(HTTP_METHOD.ALL))) {
				if(route == null)
					route = r;
				else {
					route = Route.getHigherByPrecedence(route, r);
					if(route == null)
						throw new UaAmbigiousRouteException(req.path);
				}
			}
		}
		
		if(route == null)
			throw new NoRouteFoundException(req.path);
		
		return route;
	}
	
	

	protected Object filter(Request req, Response resp, Route route) {
		Object ret = null;
		for(AnnotationFilter f : aFilter) {
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

}
