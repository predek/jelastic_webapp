package uj.pr.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.dao.PurchaseDAO;
import uj.pr.dao.PurchaseElementDAO;
import uj.pr.model.Purchase;
import uj.pr.model.PurchaseElement;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public TestServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuilder content = new StringBuilder();

		// OrderDAO orderDAO =
		// (OrderDAO)getServletContext().getAttribute("OrderDAO");

		// //////////////////////////////////////

		// List<Order> allOrders = orderDAO.getAllOrders();

		PurchaseDAO purchaseDAO = new PurchaseDAO();		//purchase

		List<Purchase> allPurchase = purchaseDAO.getAll(); // test .all

		content.append(allPurchase == null);
		if (allPurchase != null) {
			content.append(allPurchase.size());
			content.append(allPurchase);
		}

		Purchase purchase = new Purchase();
		purchase.setUserId(7);
		int result = purchaseDAO.add(purchase); // test .add
		content.append(Integer.toString(result));
		content.append(purchaseDAO.status);
		
		result = purchaseDAO.add(purchase); // test .add
		content.append(Integer.toString(result));
		content.append(purchaseDAO.status);
		
		PurchaseElementDAO purchaseElementDAO = new PurchaseElementDAO();		//purchaseElement

		List<PurchaseElement> allPurchaseElement = purchaseElementDAO.getAll(); // test .all

		content.append(allPurchaseElement == null);
		if (allPurchaseElement != null) {
			content.append(allPurchaseElement.size());
			content.append(allPurchaseElement);
		}

		PurchaseElement purchaseElement = new PurchaseElement();
		purchaseElement.setPurchaseId(2);
		purchaseElement.setProductId(1);
		purchaseElement.setAmount(5);
		purchaseElementDAO.add(purchaseElement); // test .add
		
		content.append(purchaseElementDAO.status);
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(content.toString());
		out.close();
	}

	public void destroy() {

	}
}

// OrderDAO orderDAO = (OrderDAO) getServletContext().getAttribute(
// "OrderDAO");
// OrderElementDAO orderElementDAO = (OrderElementDAO) getServletContext()
// .getAttribute("OrderElementDAO");
//
// content.append(orderDAO);
// content.append("<br>");
// content.append(orderElementDAO);
// content.append("<br>");
//
// Order testOrder = new Order();
// testOrder.setUserId(7);
//
// int result = orderDAO.addOrder(testOrder);
//
// content.append(Integer.toString(result));
// content.append("<br>");
//
// testOrder.setId(result);
//
// content.append(testOrder);
// content.append("<br>");
//
// //
//
// OrderElement testOrderElement = new OrderElement();
// testOrderElement.setOrderId(testOrder.getId());
// testOrderElement.setProductId(2);
// testOrderElement.setAmount(3);
//
// boolean result2 = orderElementDAO.addOrderElement(testOrderElement);
//
// content.append(Boolean.toString(result2));
// content.append("<br>");
//
// //
//
// List<Order> allOrders = orderDAO.getAllOrders();
//
// for (int i = 0; i < allOrders.size(); i++) {
// Order order = allOrders.get(i);
// content.append(order.getId());
// content.append(order.getUserId());
// content.append("--");
// content.append("<br>");
// }
//
// //
//
// List<OrderElement> allElements =
// orderElementDAO.getElementsOfOrder(testOrder.getId());
//
// for (int i = 0; i < allElements.size(); i++) {
// OrderElement element = allElements.get(i);
//
// content.append(element.getId());
// content.append(element.getProductId());
// content.append(element.getAmount());
// content.append("--");
// content.append("<br>");
// }
