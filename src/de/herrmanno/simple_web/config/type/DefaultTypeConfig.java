package de.herrmanno.simple_web.config.type;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.typehandler.TypeHandler;

public class DefaultTypeConfig implements TypeConfig {

	HashMap<Class<?>, TypeHandler<?>> handlers = new HashMap<Class<?>, TypeHandler<?>>();
	protected Config config;


	public DefaultTypeConfig(Config config) {
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeHandler<T> getTypeHandler(Class<T> clazz) {
		TypeHandler<T> handler = (TypeHandler<T>) handlers.get(clazz);
		
		if(handler == null) {
			if((clazz = (Class<T>) clazz.getSuperclass()) != null) {
				return getTypeHandler(clazz);
			} else {
				return null;
			}
		}
		
		return handler;
	}

	@Override
	public void register(TypeHandler<?> typeHandler) {
		Class<?> clazz = getHandleType(typeHandler);
		handlers.put(clazz, typeHandler);
	}
	
	private Class<?> getHandleType(TypeHandler<?> typeHandler) {
		Class<?> clazz;
		if((clazz = typeHandler.getHandledType()) != null)
			return clazz;
		
		clazz = typeHandler.getClass();
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
