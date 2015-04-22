package de.herrmanno.simple_web.config.type;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.herrmanno.simple_web.typehandler.TypeHandler;

public class DefaultTypeConfig implements TypeConfig {

	HashMap<Class<?>, TypeHandler<?>> handlers = new HashMap<Class<?>, TypeHandler<?>>();
	
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
		Class<?> clazz = null;
		for(Method m : typeHandler.getClass().getDeclaredMethods()) {
			if(m.getName().equals("handle")) {
				clazz = m.getParameterTypes()[0];
				break;
			}
		}
		handlers.put(clazz, typeHandler);
	}

	
}
