package uj.pr;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
//@WebServlet(value = "/test")
//@WebServlet(value = { "/test" })

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String message;

	public TestServlet() {
		super();
	}

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
		out.close();
	}

	public void destroy() {
		// do nothing.
	}
}
