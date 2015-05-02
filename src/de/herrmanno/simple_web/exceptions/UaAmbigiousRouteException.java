package de.herrmanno.simple_web.exceptions;

public class UaAmbigiousRouteException extends SimpleWebException {

	public UaAmbigiousRouteException(String route) {
		super("More than one possible RouteMethod found for route '"+route+"'");
	}

}
