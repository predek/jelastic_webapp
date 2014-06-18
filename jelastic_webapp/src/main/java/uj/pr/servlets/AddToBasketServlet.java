package uj.pr.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.uj.basket.Basket;
import uj.pr.dao.ProductDAO;
import uj.pr.dao.UserDAO;
import uj.pr.model.Product;
import uj.pr.model.User;
import uj.pr.templates.LoginTemplate;

public class AddToBasketServlet extends HttpServlet {

	public AddToBasketServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		////debug
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println(request.getParameter("productid"));
		out.println(request.getParameter("amount"));
		out.close();
		
		//
		
		
		
		int productId = Integer.parseInt(request.getParameter("productid"));
		int amount;
		try {
			amount = Integer.parseInt(request.getParameter("amount"));
		} catch (NumberFormatException e) {
			amount = 1;		//gdy nie mozna zparsowac string na integera
		}
		
		HttpSession session = request.getSession();
		Basket userBasket = (Basket)session.getAttribute("Basket");
		
		ProductDAO productdao = (ProductDAO) getServletContext()
				.getAttribute("ProductDAO");
		
		Product product = productdao.getProductById(productId);		//getting product instance based on productId provided as request param
			
		userBasket.addToBasket(product, amount);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/showbasket");
		dispatcher.forward(request, response);
	}

	public void destroy() {

	}
}
