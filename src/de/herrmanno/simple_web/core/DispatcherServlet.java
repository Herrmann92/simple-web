package de.herrmanno.simple_web.core;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public abstract class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -164636491368179633L;
	private LinkedList<Route> routes;

	
	abstract protected Config getConfig();
	
	@Override
	public void init() throws ServletException {
		super.init();
		routes = getConfig().getRouteConfig().getRoutes();
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(new Request(req), new Response(resp));
	}
	
	private void service(Request req, Response resp) throws ServletException, IOException {
		
		try {
			Object out = "";
			byte[] bytes = null;
			
			for(Route route : routes) {
				if(route.routeRegex.equals(req.path)) {
					out = route.function.apply(req, resp);
					bytes = getConfig().getTypeConfig().handle(out.getClass(), out);
					break;
				}
			}
			
			resp.send(bytes);
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.send(500, e.getMessage().getBytes());
		}
		
	}
	
}
