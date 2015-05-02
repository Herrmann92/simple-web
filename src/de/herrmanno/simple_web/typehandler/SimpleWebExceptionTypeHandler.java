package de.herrmanno.simple_web.typehandler;

import de.herrmanno.simple_web.exceptions.SimpleWebException;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

public class SimpleWebExceptionTypeHandler implements TypeHandler<SimpleWebException> {

	@Override
	public byte[] handle(SimpleWebException t, Request req, Response resp) throws Exception {
		return t.getMessage().toString().getBytes();
	}

}
