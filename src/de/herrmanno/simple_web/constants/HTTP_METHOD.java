package de.herrmanno.simple_web.constants;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public enum HTTP_METHOD {
	
	//TODO Add Methods
	GET, POST, PUT, DELETE, ALL;
	
	public static HTTP_METHOD get(Method m) {
		String n = m.getName();
		
		if(n.startsWith("get")) return HTTP_METHOD.GET;
		
		else if(n.startsWith("post")) return HTTP_METHOD.POST;
		
		else if(n.startsWith("put")) return HTTP_METHOD.PUT;
		
		else if(n.startsWith("delete")) return HTTP_METHOD.DELETE;
		
		else return HTTP_METHOD.ALL;
	}

	public static HTTP_METHOD get(HttpServletRequest req) {
		String m = req.getMethod();
		
		if(m.equals("GET")) return HTTP_METHOD.GET;
		
		else if(m.equals("POST")) return HTTP_METHOD.POST;
		
		else if(m.equals("PUT")) return HTTP_METHOD.PUT;
		
		else if(m.equals("DELETE")) return HTTP_METHOD.DELETE;
		
		else return HTTP_METHOD.ALL;
		
	}
}
