package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uj.pr.basket.BasketManager;
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int productId = Integer.parseInt(request.getParameter("productid"));	//productid lowercase bigos params
		
		HttpSession session = request.getSession();
		BasketManager userBasket = (BasketManager)session.getAttribute("Basket");
		
		ProductDAO productdao = (ProductDAO) getServletContext()
				.getAttribute("ProductDAO");
		
		Product product = productdao.getProductById(productId);		//getting product instance based on productId provided as request param
			
		userBasket.removeFromBasket(product);
		
		response.sendRedirect("/showbasket");
	}

	public void destroy() {

	}
}
