package uj.pr.templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.uj.basket.Basket;
import pr.uj.basket.BasketElement;
import uj.pr.dao.CategoryDAO;
import uj.pr.dao.ProductDAO;
import uj.pr.misc.Renderer;
import uj.pr.model.Category;
import uj.pr.model.Product;

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
		
		if(isLogged)
		{		
			HttpSession session = request.getSession();
			Basket userBasket = (Basket)session.getAttribute("Basket");
			
			ArrayList<BasketElement> basketElements = userBasket.showBasket();
	
			for (int i = 0; i < basketElements.size(); i++) {
				BasketElement basketElement = basketElements.get(i);
				
				float basketElementPrice = basketElement.product.getPrice() * basketElement.amount;			
				content.append(basketElement.product.getName() + " x " + basketElement.amount + " = " + basketElementPrice + " zl<br>");
				
				content.append("<form method=\"post\" action=\"./removefrombasket\">"
						+ "<input type=\"hidden\" name=\"productid\" value=\"" + basketElement.product.getId() + "\">"
						+ "<input type=\"submit\" value=\"usun\"></form><br><br>");			
			}
		} else{
			content.append("zaloguj sie, aby dodawac produkty do koszyka");
		}

		renderer.setContent(content.toString());
		renderer.render();
	}
}
