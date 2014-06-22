package uj.pr.templates;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uj.pr.basket.BasketElement;
import uj.pr.basket.BasketManager;
import uj.pr.dao.UserDAO;
import uj.pr.misc.DiscountManager;
import uj.pr.misc.Renderer;
import uj.pr.model.User;

public class ShowBasketTemplate {
	Renderer renderer;

	public ShowBasketTemplate(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		renderer = new Renderer(servlet, request, response);

		StringBuilder content = new StringBuilder();

		Header headerTemplate = new Header(servlet, request, response);

		content.append(headerTemplate.getContent());

		content.append("<h1>Koszyk</h1>");

		boolean isLogged = request.getSession().getAttribute("isLogged") != null;

		if (isLogged) {
			HttpSession session = request.getSession();
			BasketManager userBasket = (BasketManager) session
					.getAttribute("Basket");

			ArrayList<BasketElement> basketElements = userBasket
					.getBasketElements();

			DiscountManager discountManager = new DiscountManager(servlet);

			double totalProductsPrice = 0; // uwzglednia juz product discount
			if (basketElements.size() > 0) {

				// 1. product discount:
				for (int i = 0; i < basketElements.size(); i++) {
					BasketElement basketElement = basketElements.get(i);

					content.append(basketElement.product.getName() + " ");

					double preDiscountPrice = basketElement.product.getPrice();
					int productDiscountPercent = discountManager
							.calculateCurrentProductDiscount(basketElement.product); // e0..100
					double discountedPrice = Math.floor(preDiscountPrice * 0.01
							* (100 - productDiscountPercent));

					boolean isProductDiscounted = productDiscountPercent > 0;

					if (isProductDiscounted) { // discounted product
						content.append("<strike>"
								+ Double.toString(preDiscountPrice)
								+ "</strike> ");
						content.append(discountedPrice + "zl ");
					} else { // no product discount
						content.append(discountedPrice + "zl ");
					}
					// Math.round(value * 100000) / 100000
					double totalProductPrice = Math.round(discountedPrice
							* basketElement.amount * 100) / 100; // discounted
																	// price
																	// times
																	// amount
					totalProductsPrice += totalProductPrice;

					content.append(" x " + basketElement.amount + " = "
							+ Double.toString(totalProductPrice) + " zl");

					content.append("<form method=\"post\" action=\"./removefrombasket\">"
							+ "<input type=\"hidden\" name=\"productid\" value=\""
							+ basketElement.product.getId()
							+ "\">"
							+ "<input type=\"submit\" value=\"delete\"></form><br><br>");
				}

				// 2. basket discount
				int basketDiscount = discountManager
						.calculateBasketDiscount(userBasket);
				// content.append(Integer.toString(basketDiscount));

				boolean isBasketDiscounted = basketDiscount > 0;

				if (isBasketDiscounted) {
					content.append("przysluguje Ci 5% znizki za zakupy w kwocie powyzej 100 zl!<br>");
				}

				// 3.user discount

				UserDAO userdao = (UserDAO) servlet.getServletContext()
						.getAttribute("UserDAO");

				int userId = Integer.parseInt((String) request.getSession()
						.getAttribute("userId").toString());

				User user = userdao.getUserById(userId);
				int userDiscount = discountManager.calculateUserDiscount(user);

				// content.append(Integer.toString(userDiscount));

				boolean isUserDiscounted = userDiscount > 0;
				if (isUserDiscounted) {
					content.append("przysluguje Ci 5% znizka lojalnosciowa dla uzytkownikow, ktorzy wydali w naszym sklepie wiecej, niz 500 zl!<br>");
				}

				// summary

				double preDiscountTotalPrice = totalProductsPrice;

				double basketFactor;
				if (isBasketDiscounted) {
					basketFactor = 0.01 * (100 - basketDiscount);
				} else {
					basketFactor = 1;
				}

				double userFactor;
				if (isBasketDiscounted) {
					userFactor = 0.01 * (100 - userDiscount);
				} else {
					userFactor = 1;
				}

				double discountedTotalPrice = totalProductsPrice * basketFactor
						* userFactor;

				discountedTotalPrice = Math.round(discountedTotalPrice * 100) / 100;

				content.append("suma = ");

				if (isBasketDiscounted || isUserDiscounted) // show pre discount
															// price
				{
					content.append("<strike>"
							+ Double.toString(preDiscountTotalPrice)
							+ "</strike> ");
					content.append(Double.toString(discountedTotalPrice)
							+ "zl ");
				} else {
					content.append(Double.toString(preDiscountTotalPrice)
							+ "zl ");
				}

				// order button

				content.append("<form method=\"post\" action=\"./orders\">"
						+ "<input type=\"hidden\" name=\"userid\" value=\""
						+ userId
						+ "\">"
						+ "<input type=\"submit\" value=\"zamow\"></form><br><br>");

			} else {
				content.append("koszyk jest pusty");
			}
		} else {
			content.append("zaloguj sie, aby moc dodawac produkty do koszyka");
		}

		renderer.setContent(content.toString());
		renderer.render();
	}
}
