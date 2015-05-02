package de.herrmanno.simple_web.constants;

public enum ContentType {
	TEXT("text/plain"),
	HTML("text/html"),
	CSS("text/css");
	
	public final String mime;
	
	private ContentType(String mime) {
		this.mime = mime;
	}

}
