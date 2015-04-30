package de.herrmanno.simple_web.plugin;

import de.herrmanno.simple_web.config.Config;
import de.herrmanno.simple_web.parameterhandler.IntParameterHandler;
import de.herrmanno.simple_web.parameterhandler.ParameterHandler;
import de.herrmanno.simple_web.parameterhandler.StringParameterHandler;
import de.herrmanno.simple_web.typehandler.IntTypeHandler;
import de.herrmanno.simple_web.typehandler.StringTypeHandler;
import de.herrmanno.simple_web.typehandler.TypeHandler;

public class BasePlugin implements Plugin {

	private final TypeHandler<?>[] tHandlers = {
			new StringTypeHandler(),
			new IntTypeHandler(),
		};
	
	private final ParameterHandler<?>[] pHandlers = {
		new StringParameterHandler(),
		new IntParameterHandler(),
	};
	
	
	@Override
	public void register(Config config) {
		
		config.getTypeConfig().register(tHandlers);
		
		//config.getParameterConfig().register(pHandlers);

	}

}
