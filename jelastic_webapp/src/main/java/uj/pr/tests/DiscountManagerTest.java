package uj.pr.tests;

import java.sql.Date;

import javax.servlet.http.HttpServlet;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.easymock.EasyMock;

import uj.pr.basket.BasketManager;
import uj.pr.misc.DiscountManager;
import uj.pr.model.Category;
import uj.pr.model.Product;
import uj.pr.model.User;

public class DiscountManagerTest extends TestCase {

	private DiscountManager discountManager;
	private Product testProduct;
	private Category category;

	protected void setUp() throws Exception {
		super.setUp();

		HttpServlet servlet = EasyMock.createMock(HttpServlet.class);
		this.discountManager = new DiscountManager(servlet);

		this.testProduct = new Product();
		testProduct.setCategoryId(0);

		int year = 2014 - 1900;
		this.category = new Category(); // discount 1.06 - 30.06		
		category.setId(testProduct.getCategoryId());
		Date discountStartDate = new Date(year, 4, 31);	//bounds
		category.setDiscountStart(discountStartDate.toString());
		Date discountEndDate = new Date(year, 6, 1);
		category.setDiscountEnd(discountEndDate.toString());

		int percentDiscount = 5;
		category.setPercentDiscount(percentDiscount);

	}

	public void testCalculateProductDiscountInsideSeason() {

		int year = 2014 - 1900;
		Date dateToTest = new Date(year, 5, 1);
		int calculatedProductDiscount = discountManager
				.calculateProductDiscount(testProduct, category, dateToTest);
		Assert.assertEquals(5, calculatedProductDiscount);

		dateToTest = new Date(year, 5, 30);
		calculatedProductDiscount = discountManager.calculateProductDiscount(
				testProduct, category, dateToTest);
		Assert.assertEquals(5, calculatedProductDiscount);
	}

	public void testCalculateProductDiscountOutsideSeason() {

		int year = 2014 - 1900;
		Date dateToTest = new Date(year, 4, 31);
		int calculatedProductDiscount = discountManager
				.calculateProductDiscount(testProduct, category, dateToTest);
		Assert.assertEquals(0, calculatedProductDiscount);

		dateToTest = new Date(year, 6, 1);
		calculatedProductDiscount = discountManager.calculateProductDiscount(
				testProduct, category, dateToTest);
		Assert.assertEquals(0, calculatedProductDiscount);

	}

	public void testCalculateUserDiscount() {
		
		User user = new User();
		user.setTotalSpent("500");
		
		int calculatedUserDiscount = discountManager.calculateUserDiscount(user);
		Assert.assertEquals(0, calculatedUserDiscount);
		
		user.setTotalSpent("501");
		
		calculatedUserDiscount = discountManager.calculateUserDiscount(user);
		Assert.assertEquals(5, calculatedUserDiscount);

	}

}
