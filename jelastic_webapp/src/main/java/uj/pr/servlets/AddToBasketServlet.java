package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.templates.LoginTemplate;

public class AddToBasketServlet extends HttpServlet {

	public AddToBasketServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginTemplate loginTemplate = new LoginTemplate(this, request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginTemplate loginTemplate = new LoginTemplate(this, request, response);
	}

	public void destroy() {

	}
}