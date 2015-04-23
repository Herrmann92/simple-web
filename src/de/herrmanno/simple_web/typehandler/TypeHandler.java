package de.herrmanno.simple_web.typehandler;

public interface TypeHandler<T> {
	
	public byte[] handle(T obj) throws Exception;
	
	default public Class<? extends T> getHandledType() {
		return null;
	}
	
}
