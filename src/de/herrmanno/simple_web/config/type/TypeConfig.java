package de.herrmanno.simple_web.config.type;

import de.herrmanno.simple_web.typehandler.TypeHandler;

public interface TypeConfig {
	
	public <T> TypeHandler<T> getTypeHandler(Class<T> clazz);
	
	public void register(TypeHandler<?> typeHandler);

	
	@SuppressWarnings("unchecked")
	default <T> byte[] handle(Class<T> c, Object obj) throws Exception {
		TypeHandler<T> handler = getTypeHandler(c);
		return handler.handle((T) obj);
	};
}
