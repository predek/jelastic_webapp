package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.uj.basket.Basket;
import uj.pr.dao.ProductDAO;
import uj.pr.model.Product;
import uj.pr.templates.LoginTemplate;

public class RemoveFromBasketServlet extends HttpServlet {

	public RemoveFromBasketServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int productId = Integer.parseInt(request.getParameter("productid"));	//productid lowercase bigos params
		
		HttpSession session = request.getSession();
		Basket userBasket = (Basket)session.getAttribute("Basket");
		
		ProductDAO productdao = (ProductDAO) getServletContext()
				.getAttribute("ProductDAO");
		
		Product product = productdao.getProductById(productId);		//getting product instance based on productId provided as request param
			
		userBasket.removeFromBasket(product);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/showbasket");
		dispatcher.forward(request, response);
	}

	public void destroy() {

	}
}
