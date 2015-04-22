package de.herrmanno.simple_web.typehandler;

public interface TypeHandler<T extends Object> {
	
	public byte[] handle(T obj) throws Exception;
}
