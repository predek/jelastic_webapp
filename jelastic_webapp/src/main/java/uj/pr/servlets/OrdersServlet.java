package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.templates.LoginTemplate;
import uj.pr.templates.OrdersTemplate;

public class OrdersServlet extends HttpServlet {

	public OrdersServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OrdersTemplate ordersTemplate = new OrdersTemplate(this, request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//LoginTemplate loginTemplate = new LoginTemplate(this, request, response);
		//dodawanie zamowien
	}

	public void destroy() {

	}
}
