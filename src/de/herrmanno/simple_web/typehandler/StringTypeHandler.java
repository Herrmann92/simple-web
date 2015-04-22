package de.herrmanno.simple_web.typehandler;


public class StringTypeHandler implements TypeHandler<String> {

	@Override
	public byte[] handle(String obj) {
		return obj.getBytes();
	}

	
}
