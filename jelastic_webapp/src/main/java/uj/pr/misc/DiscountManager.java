package uj.pr.misc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;

import uj.pr.basket.BasketElement;
import uj.pr.basket.BasketManager;
import uj.pr.dao.CategoryDAO;
import uj.pr.model.Category;
import uj.pr.model.Product;
import uj.pr.model.User;

public class DiscountManager {
	HttpServlet servlet; // for accesing categoryDAO

	public DiscountManager(HttpServlet servlet) {
		this.servlet = servlet;
	}

	public int calculateCurrentProductDiscount(Product product) {

		Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());

		Category productCategory = getProductCategory(product);

		return calculateProductDiscount(product, productCategory, currentDate);
	}

	private Category getProductCategory(Product product) {

		int categoryId = product.getCategoryId();

		CategoryDAO categoryDAO = (CategoryDAO) servlet.getServletContext()
				.getAttribute("CategoryDAO");

		return categoryDAO.getCategoryById(categoryId);

	}

	public int calculateProductDiscount(Product product,
			Category basketElementCategory, Date currentDate) {

		// seasonal discount (based on product category)
		// e 0..100

		int percentDiscount = 0;

		boolean isDiscounted;

		try {
			Date discountStartDate = Date.valueOf(basketElementCategory
					.getDiscountStart());
			Date discountEndDate = Date.valueOf(basketElementCategory
					.getDiscountEnd());
			
			isDiscounted = (currentDate.after(discountStartDate) && currentDate
					.before(discountEndDate));

		} catch (Exception e) {
			isDiscounted = false;
		}

		if (isDiscounted) { // seasonal discount in progress
			percentDiscount = (int) Math.round(basketElementCategory
					.getPercentDiscount());
		} else {
			percentDiscount = 0; // no seasonal discount
		}

		return percentDiscount;
	}

	public int calculateBasketDiscount(BasketManager basket) {
		int percentDiscount;

		double totalBasketPrice = 0;

		ArrayList<BasketElement> basketContent = basket.getBasketElements();

		for (int i = 0; i < basketContent.size(); i++) {
			BasketElement basketElement = (BasketElement) basketContent.get(i);

			int discountPercent = calculateCurrentProductDiscount(basketElement.product);
			double discountedProductPrice = Math.floor(basketElement.product
					.getPrice()
					* basketElement.amount
					* 0.01
					* (100 - discountPercent));
			totalBasketPrice += discountedProductPrice;
		}

		boolean isDiscounted = totalBasketPrice > 100;

		if (isDiscounted) {
			percentDiscount = 5;
		} else {
			percentDiscount = 0;
		}
		return percentDiscount;
	}

	public int calculateUserDiscount(User user) {

		int percentDiscount;

		// UserDAO userDAO = (UserDAO) servlet.getServletContext()
		// .getAttribute("UserDAO");
		//
		// User user = userDAO.getUserById(userId);

		boolean isDiscounted = Double.parseDouble(user.getTotalSpent()) > 500;

		if (isDiscounted) {
			percentDiscount = 5;
		} else {
			percentDiscount = 0;
		}

		return percentDiscount;
	}

}
