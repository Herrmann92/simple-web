package de.herrmanno.simple_web.exceptions;

public class NoRouteFoundException extends SimpleWebException {

	public NoRouteFoundException(String path) {
		super("No Route found for path '"+path+"'");
	}

}
