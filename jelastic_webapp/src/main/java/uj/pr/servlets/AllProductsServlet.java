package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uj.pr.templates.ProductsTemplate;

public class AllProductsServlet extends HttpServlet {

	public AllProductsServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductsTemplate productsTemplate = new ProductsTemplate(this, request, response);
	}

	public void destroy() {

	}
}
