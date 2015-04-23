package de.herrmanno.simple_web.core.filter;

import de.herrmanno.simple_web.util.Request;
import de.herrmanno.simple_web.util.Response;

@FunctionalInterface
public interface FilterFunction {

	public Object filter(Request req, Response resp);
}
