package uj.pr.templates;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.misc.Renderer;

public class LoginTemplate {
	Renderer renderer;

	public LoginTemplate(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		renderer = new Renderer(servlet, request, response);

		StringBuilder content = new StringBuilder();
		
		Header headerTemplate = new Header(servlet, request, response);

		content.append(headerTemplate.getContent());
		
		content.append("<h1>Logowanie</h1>");
		
		content.append("<form method=\"post\" action=\"./login\">"
				+ "username: <input type=\"text\" name=\"username\"><br/>"
				+ "password: <input type=\"password\" name=\"password\"><br/>"
				+ "<input type=\"submit\" value=\"login\"></form>");
		
		renderer.setContent(content.toString());
		renderer.render();
	}
}
