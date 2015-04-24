package de.herrmanno.simple_web.config.type;

import de.herrmanno.simple_web.typehandler.TypeHandler;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public interface TypeConfig {
	
	public <T> TypeHandler<T> getTypeHandler(Class<T> clazz);
	
	public void register(TypeHandler<?> typeHandler);

	default public void register(TypeHandler<?>... handler) {
		for(TypeHandler<?> h : handler)
			register(h);
	}
	
	
	@SuppressWarnings("unchecked")
	default <T> byte[] handle(Request req, Response resp, Class<T> c, Object obj) throws Exception {
		TypeHandler<T> handler = getTypeHandler(c);
		if(handler == null)
			throw new Exception("No Typehandler found!");
		return handler.handle((T) obj, req, resp);
	};
}
