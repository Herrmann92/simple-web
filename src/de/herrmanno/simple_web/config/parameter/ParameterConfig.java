package de.herrmanno.simple_web.config.parameter;

import de.herrmanno.simple_web.parameterhandler.ParameterHandler;

public interface ParameterConfig {

	public void register(ParameterHandler<?> parameterHandler);

	default public void register(ParameterHandler<?>... handler) {
		for(ParameterHandler<?> h : handler)
			register(h);
	}
	
	public <T> ParameterHandler<T> getParameterHandler(Class<T> clazz);
	
}
