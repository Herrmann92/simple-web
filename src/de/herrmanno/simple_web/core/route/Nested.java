package de.herrmanno.simple_web.core.route;

public class Nested implements Routable {

	protected String prefix;
	protected final Routable[] routables;

	public Nested(String prefix, Routable... routables) {
		this.prefix = prefix;
		this.routables = routables;
	}
	
	public String getPrefix() { return prefix; }
	
	public Routable[] getRoutables() { return routables; }

	public void addPrefix(String p) {
		prefix = p + "/" + prefix;
	}
}
