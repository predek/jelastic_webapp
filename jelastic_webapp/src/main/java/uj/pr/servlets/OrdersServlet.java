package uj.pr.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uj.pr.basket.BasketElement;
import uj.pr.basket.BasketManager;
import uj.pr.dao.OrderDAO;
import uj.pr.dao.OrderElementDAO;
import uj.pr.model.Order;
import uj.pr.model.OrderElement;
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

		OrdersTemplate ordersTemplate = new OrdersTemplate(this, request,
				response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuilder content = new StringBuilder();
		
		HttpSession session = request.getSession();			//add order
		BasketManager userBasket = (BasketManager) session.getAttribute("Basket");
		
		OrderDAO orderDAO = (OrderDAO) getServletContext().getAttribute("OrderDAO");
		OrderElementDAO orderElementDAO = (OrderElementDAO) getServletContext().getAttribute("OrderElementDAO");
		
		int userId = (Integer)session.getAttribute("userId");
		content.append("userId = " + Integer.toString(userId) + "<br>");
		
		Order order = new Order();
		order.setUserId(userId);
		
		int orderId = orderDAO.addOrder(order);
		order.setId(orderId);
		
		content.append("obtained orderId = " + Integer.toString(orderId) + "<br>");
		
		List<BasketElement> basketElements = userBasket.getBasketElements();
		
		for (int i = 0; i < basketElements.size(); i++) {
			
			BasketElement basketElement = basketElements.get(i);			
			
			OrderElement orderElement = new OrderElement();
			orderElement.setOrderId(orderId);
			orderElement.setProductId(basketElement.product.getId());
			orderElement.setAmount(basketElement.amount);
			
			boolean result = orderElementDAO.addOrderElement(orderElement);

			content.append("addOrderElement result = " + Boolean.toString(result) + "<br>");
			
		}
		
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println(content);
		out.close();
		
		
	}

	public void destroy() {

	}
}
