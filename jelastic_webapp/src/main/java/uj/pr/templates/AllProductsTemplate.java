package uj.pr.templates;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.dao.CategoryDAO;
import uj.pr.dao.ProductDAO;
import uj.pr.misc.DiscountManager;
import uj.pr.misc.TemplateRenderer;
import uj.pr.model.Category;
import uj.pr.model.Product;

public class AllProductsTemplate {
	TemplateRenderer renderer;

	public AllProductsTemplate(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		renderer = new TemplateRenderer(servlet, request, response);

		StringBuilder content = new StringBuilder();

		Header headerTemplate = new Header(servlet, request, response);

		content.append(headerTemplate.getContent());

		content.append("<h1>Produkty</h1>");

		ProductDAO productdao = (ProductDAO) servlet.getServletContext()
				.getAttribute("ProductDAO");
		CategoryDAO categoryDAO = (CategoryDAO) servlet.getServletContext()
				.getAttribute("CategoryDAO");
		List<Product> products = productdao.getAllProducts();
		
		boolean isLogged = request.getSession().getAttribute("isLogged") != null;
		
		DiscountManager discountManager = new DiscountManager(servlet);

		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			Category category = categoryDAO.getCategoryById(product
					.getCategoryId());
			content.append(product.getName() +
					" (kategoria: "	+ category.getName() + ") "
					+ product.getDescription());
			
			double preDiscountPrice = product.getPrice();
			int productDiscountPercent = discountManager
					.calculateCurrentProductDiscount(product); // e0..100
			double discountedPrice = Math.floor(preDiscountPrice * 0.01 * (100 - productDiscountPercent));
			
			boolean isProductDiscounted = productDiscountPercent > 0;
			
			content.append("cena: ");
			if(isProductDiscounted){		//discounted product
				content.append("<strike>" + Double.toString(preDiscountPrice) + "</strike> ");
				content.append(discountedPrice + "zl ");
			} else {	//no product discount
				content.append(discountedPrice + "zl ");
			}
			
			if(isLogged){
			content.append("<form method=\"post\" action=\"./addtobasket\">"
					+ "amount: <input type=\"text\" name=\"amount\"><br/>"
					+ "<input type=\"hidden\" name=\"productid\" value=\"" + product.getId() + "\">"
					+ "<input type=\"submit\" value=\"add to cart\"></form>");
			}
			
			content.append("<br><br>");
		}

		renderer.setContent(content.toString());
		renderer.render();
	}
}
