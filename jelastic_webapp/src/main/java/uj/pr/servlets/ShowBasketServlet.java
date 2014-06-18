package uj.pr.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.templates.ShowBasketTemplate;

public class ShowBasketServlet extends HttpServlet {

	public ShowBasketServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ShowBasketTemplate showBasketTemplate = new ShowBasketTemplate(this, request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void destroy() {
		
	}
}
