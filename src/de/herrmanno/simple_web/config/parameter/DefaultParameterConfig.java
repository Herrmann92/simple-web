package de.herrmanno.simple_web.config.parameter;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.herrmanno.simple_web.parameterhandler.ParameterHandler;

public class DefaultParameterConfig implements ParameterConfig {

	HashMap<Class<?>, ParameterHandler<?>> handlers = new HashMap<Class<?>, ParameterHandler<?>>();


	@Override
	public void register(ParameterHandler<?> parameterHandler) {
		Class<?> clazz = getHandleType(parameterHandler);
		handlers.put(clazz, parameterHandler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParameterHandler<T> getParameterHandler(Class<T> clazz) {
		ParameterHandler<T> handler = (ParameterHandler<T>) handlers.get(clazz);
		
		if(handler == null) {
			if((clazz = (Class<T>) clazz.getSuperclass()) != null) {
				return getParameterHandler(clazz);
			} else {
				return null;
			}
		}
		
		return handler;
	}
	
	private Class<?> getHandleType(ParameterHandler<?> parameterHandler) {
		Class<?> clazz;
		if((clazz = parameterHandler.getHandledType()) != null)
			return clazz;
		
		clazz = parameterHandler.getClass();
		do {
			for(Method m : clazz.getDeclaredMethods()) {
				if(m.getName().equals("handle")) {
					return m.getParameterTypes()[0];
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		return null;
	}

}
