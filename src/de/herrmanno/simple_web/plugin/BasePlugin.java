package de.herrmanno.simple_web.plugin;

import de.herrmanno.simple_web.core.DispatcherServlet;
import de.herrmanno.simple_web.typehandler.IntTypeHandler;
import de.herrmanno.simple_web.typehandler.SimpleWebExceptionTypeHandler;
import de.herrmanno.simple_web.typehandler.StringTypeHandler;
import de.herrmanno.simple_web.typehandler.TypeHandler;

public class BasePlugin implements Plugin {

	private final TypeHandler<?>[] tHandlers = {
			new StringTypeHandler(),
			new IntTypeHandler(),
			new SimpleWebExceptionTypeHandler()
		};
	
	
	@Override
	public void register(DispatcherServlet servlet) {
		
		servlet.register(tHandlers);
		
	}

}
