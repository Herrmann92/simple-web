package de.herrmanno.simple_web.parameterhandler;


public interface ParameterHandler<T> {

	public String regex();
	
	public T handle(String str);
	
	default public Class<? extends T> getHandledType() {
		return null;
	}
}
