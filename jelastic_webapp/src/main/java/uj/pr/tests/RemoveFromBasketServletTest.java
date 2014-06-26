package uj.pr.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import uj.pr.basket.BasketElement;
import uj.pr.basket.BasketManager;
import uj.pr.dao.ProductDAO;
import uj.pr.model.Product;
import uj.pr.servlets.RemoveFromBasketServlet;
import uj.pr.tests.custommocks.ProductDAOMock;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class RemoveFromBasketServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPost() {

		BasketManager basket = new BasketManager();

		Product testProduct1 = new Product();
		testProduct1.setId(6);
		int amountOfProduct1 = 4;
		basket.addToBasket(testProduct1, amountOfProduct1);

		Product testProduct2 = new Product();
		testProduct2.setId(7);
		int amountOfProduct2 = 2;
		basket.addToBasket(testProduct2, amountOfProduct2);

		tester.setSessionAttribute("Basket", basket);

		tester.createServlet(RemoveFromBasketServlet.class);

		int productIdParam = 7;

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));

		ProductDAOMock productDAO = new ProductDAOMock(testProduct2);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(1, updatedBasket.getBasketElements().size());

		BasketElement element = updatedBasket.getBasketElements().get(0);

		Assert.assertEquals(amountOfProduct1, element.amount);
		Assert.assertEquals(testProduct1.getId(), element.product.getId());

	}

	public void testDoPostEmptyBasket() {

		BasketManager basket = new BasketManager();
		tester.setSessionAttribute("Basket", basket);

		tester.createServlet(RemoveFromBasketServlet.class);

		Product testProduct2 = new Product();
		testProduct2.setId(7);
		int amountOfProduct2 = 2;

		ProductDAOMock productDAO = new ProductDAOMock(testProduct2);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		int productIdParam = 7;

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(0, updatedBasket.getBasketElements().size());

		// Assert.assertEquals(testProduct1.getId(), element.product.getId());

	}

	public void testDoPostWrongParams() {

		BasketManager basket = new BasketManager();

		Product testProduct1 = new Product();
		testProduct1.setId(6);
		int amountOfProduct1 = 4;
		basket.addToBasket(testProduct1, amountOfProduct1);

		Product testProduct2 = new Product();
		testProduct2.setId(7);
		int amountOfProduct2 = 2;
		basket.addToBasket(testProduct2, amountOfProduct2);

		tester.setSessionAttribute("Basket", basket);

		tester.createServlet(RemoveFromBasketServlet.class);

		int productIdParam = -1;

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));

		ProductDAOMock productDAO = new ProductDAOMock(testProduct2);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(1, updatedBasket.getBasketElements().size());

		BasketElement element = updatedBasket.getBasketElements().get(0);

		Assert.assertEquals(amountOfProduct1, element.amount);
		Assert.assertEquals(testProduct1.getId(), element.product.getId());

	}
}
