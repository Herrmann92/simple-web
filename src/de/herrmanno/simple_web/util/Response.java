package de.herrmanno.simple_web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Response {

	private HttpServletResponse resp;
	private int status;
	private boolean statusSet = false;

	
	public Response(HttpServletResponse resp) {
		this.resp = resp;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int code) {
		this.statusSet  = true;
		this.status = code;
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
