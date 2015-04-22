package de.herrmanno.simple_web.core;

import java.util.function.BiFunction;
import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

@FunctionalInterface
public interface RouteFunction extends BiFunction<Request, Response, Object>{

	Object apply(Request req, Response resp);

}
