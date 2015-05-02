package de.herrmanno.simple_web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import de.herrmanno.simple_web.constants.ContentType;

public class Response {

	private HttpServletResponse resp;
	private int status = -1;
	
	public Response(HttpServletResponse resp) {
		this.resp = resp;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int code) {
		if(status == -1 && code > 0 && code < 600)
			this.status = code;
	}
	
	public void setContentType(ContentType type) {
		this.resp.setContentType(type.mime);
	}
	
	public void setContentType(String type) {
		this.resp.setContentType(type);
	}
	
	public void send(String str) throws IOException {
		send(str.getBytes());
	}
	
	public void send(byte[] b) throws IOException {
		resp.getOutputStream().write(b);
	}
	
	public void send(int statusCode, String str) throws IOException {
		send(statusCode, str.getBytes());
	}
	
	public void send(int statusCode, byte[] b) throws IOException {
		resp.setStatus(statusCode);
		resp.getOutputStream().write(b);
	}
}
