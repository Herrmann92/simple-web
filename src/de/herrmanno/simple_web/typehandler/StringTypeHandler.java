package de.herrmanno.simple_web.typehandler;

import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public class StringTypeHandler implements TypeHandler<String> {

	@Override
	public byte[] handle(String str, Request req, Response resp) {
		return str.getBytes();
	}

	
}
