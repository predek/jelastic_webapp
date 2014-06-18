package uj.pr.misc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Renderer {
	public String content;

	private HttpServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public Renderer(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.servlet = servlet;
		this.request = request;
		this.response = response;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	public void render() throws IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println(content);
		out.close();
	}
}
