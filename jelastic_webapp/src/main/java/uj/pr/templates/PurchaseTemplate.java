package uj.pr.templates;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.dao.ProductDAO;
import uj.pr.dao.PurchaseDAO;
import uj.pr.dao.PurchaseElementDAO;
import uj.pr.dao.UserDAO;
import uj.pr.misc.Renderer;
import uj.pr.model.Product;
import uj.pr.model.Purchase;
import uj.pr.model.PurchaseElement;
import uj.pr.model.User;

public class PurchaseTemplate {
	Renderer renderer;

	public PurchaseTemplate(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		renderer = new Renderer(servlet, request, response);

		StringBuilder content = new StringBuilder();

		Header headerTemplate = new Header(servlet, request, response);

		content.append(headerTemplate.getContent());

		content.append("<h1>Zamowienia</h1>");

		UserDAO userdao = (UserDAO) servlet.getServletContext().getAttribute(
				"UserDAO");

		Object userIdAttr = request.getSession().getAttribute("userId");
		if (userIdAttr != null) {
			
			String sessionIdAttribute = Integer.toString((Integer)userIdAttr);
			
			int sessionUserId = Integer.parseInt(sessionIdAttribute);
			User sessionUser = userdao.getUserById(sessionUserId);

			boolean isAdmin = sessionUser.getUsername().equals("admin");

			if (isAdmin) {

				PurchaseDAO purchaseDAO = (PurchaseDAO) servlet.getServletContext()
						.getAttribute("PurchaseDAO");
				PurchaseElementDAO purchaseElementDAO = (PurchaseElementDAO) servlet
						.getServletContext().getAttribute("PurchaseElementDAO");
				UserDAO userDAO = (UserDAO) servlet.getServletContext()
						.getAttribute("UserDAO");
				ProductDAO productDAO = (ProductDAO) servlet
						.getServletContext().getAttribute("ProductDAO");

				List<Purchase> purchases = purchaseDAO.getAll();

				for (int i = 0; i < purchases.size(); i++) {

					Purchase purchase = (Purchase) purchases.get(i);

					User user = userDAO.getUserById(purchase.getUserId());
					String username = user.getUsername();

					String userId = Integer.toString(purchase.getUserId());

					content.append("uzytkownik: " + username + " ");
					content.append("(id zamowienia: " + purchase.getId() + ")<br>");

					List<PurchaseElement> purchaseElements = purchaseElementDAO
							.getPurchaseElements(purchase.getId());

					for (int j = 0; j < purchaseElements.size(); j++) {

						PurchaseElement purchaseElement = purchaseElements.get(j);

						Product product = productDAO
								.getProductById(purchaseElement.getProductId());

						String productName = product.getName();
						String amount = Integer.toString(purchaseElement
								.getAmount());

						content.append("*" + productName + " x " + amount + "<br>");
					}
					content.append("<br>");

				}

			} else {
				content.append("zawartoœæ dostêpna tylko dla administratora");
			}
		}

		renderer.setContent(content.toString());
		renderer.render();
	}
}
