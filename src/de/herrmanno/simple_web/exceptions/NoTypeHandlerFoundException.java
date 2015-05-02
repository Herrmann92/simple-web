package de.herrmanno.simple_web.exceptions;

public class NoTypeHandlerFoundException extends SimpleWebException {

	public NoTypeHandlerFoundException(String type) {
		super("No Typehandler found for return Type '"+type+"'");
	}

}
