package uj.pr.templates;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.misc.TemplateRenderer;

public class RegisterTemplate {
	TemplateRenderer renderer;

	public RegisterTemplate(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		renderer = new TemplateRenderer(servlet, request, response);

		StringBuilder content = new StringBuilder();
		
		Header headerTemplate = new Header(servlet, request, response);
		String headerContent = headerTemplate.getContent();
		content.append(headerContent);
		
		content.append("<h1>Rejestracja</h1>");
		
		content.append("<form method=\"post\" action=\"./register\">"
				+ "username: <input type=\"text\" name=\"username\"><br/>"
				+ "password: <input type=\"password\" name=\"password\"><br/>"
				+ "<input type=\"submit\" value=\"register\"></form>");

		renderer.setContent(content.toString());
		renderer.render();
	}

}
