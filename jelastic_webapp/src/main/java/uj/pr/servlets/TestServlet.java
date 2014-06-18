package uj.pr.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

//import uj.pr.dao.TestDAO;
//import uj.pr.dao.UserDAO;

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
		
		PrintWriter out = response.getWriter();
		
		////////////////////////////////////////
		
		//TestDAO testdao = new TestDAO();		
		//out.println(testdao.test());
		
		////////////////////////////////////////
		
		//out.println("<h1>" + message + "</h1>");
		out.close();
	}

	public void destroy() {
		// do nothing.
	}
}
