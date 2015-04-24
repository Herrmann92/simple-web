package de.herrmanno.simple_web.typehandler;

import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;


public class IntTypeHandler implements TypeHandler<Integer> {

	@Override
	public byte[] handle(Integer t, Request req, Response resp) {
		return t.toString().getBytes();
	}
	
	@Override
	public Class<? extends Integer> getHandledType() {
		return int.class;
	}

}