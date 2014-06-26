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
import uj.pr.dao.PurchaseDAO;
import uj.pr.dao.PurchaseElementDAO;
import uj.pr.model.Purchase;
import uj.pr.model.PurchaseElement;
import uj.pr.templates.PurchaseTemplate;

public class PurchaseServlet extends HttpServlet {

	public PurchaseServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PurchaseTemplate purchaseTemplate = new PurchaseTemplate(this, request,
				response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String message = "blad";

		StringBuilder content = new StringBuilder();

		HttpSession session = request.getSession(); // add order
		BasketManager userBasket = (BasketManager) session
				.getAttribute("Basket");

		PurchaseDAO purchaseDAO = (PurchaseDAO) getServletContext()
				.getAttribute("PurchaseDAO");
		PurchaseElementDAO purchaseElementDAO = (PurchaseElementDAO) getServletContext()
				.getAttribute("PurchaseElementDAO");

		int userId = Integer.parseInt((String) session.getAttribute("userId"));
		// content.append("userId = " + Integer.toString(userId) + "<br>");

		Purchase purchase = new Purchase();
		purchase.setUserId(userId);

		int obtainedPurchaseId = purchaseDAO.add(purchase);
		purchase.setId(obtainedPurchaseId);

		if (obtainedPurchaseId != -1) {

			List<BasketElement> basketElements = userBasket.getBasketElements();

			for (int i = 0; i < basketElements.size(); i++) {

				BasketElement basketElement = basketElements.get(i);

				PurchaseElement purchaseElement = new PurchaseElement();
				purchaseElement.setPurchaseId(obtainedPurchaseId);
				purchaseElement.setProductId(basketElement.product.getId());
				purchaseElement.setAmount(basketElement.amount);

				int result = purchaseElementDAO.add(purchaseElement);

				// content.append("addOrderElement result = " +
				// Integer.toString(result) + "<br>");

			}

			userBasket.empty();
			message = "zamowienie przyjete do realizacji";
		}
		content.append(message);

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println(content);
		out.close();

	}

	public void destroy() {

	}
}
