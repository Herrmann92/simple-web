package de.herrmanno.simple_web.typehandler;

import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public interface TypeHandler<T> {
	
	public byte[] handle(T t, Request req, Response resp) throws Exception;
	
	
	default public Class<? extends T> getHandledType() {
		return null;
	}
	
}
