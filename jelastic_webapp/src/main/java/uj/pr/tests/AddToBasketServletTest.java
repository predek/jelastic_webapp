package uj.pr.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import uj.pr.basket.BasketManager;
import uj.pr.dao.ProductDAO;
import uj.pr.model.Product;
import uj.pr.servlets.AddToBasketServlet;
import uj.pr.tests.custommocks.ProductDAOMock;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class AddToBasketServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPostNewBasketElement() {

		int productIdParam = 7;
		int amountParam = 5;

		Product testProduct = new Product();
		testProduct.setId(productIdParam);

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));
		tester.addRequestParameter("amount", Integer.toString(amountParam));

		tester.createServlet(AddToBasketServlet.class);

		BasketManager basket = new BasketManager();
		tester.setSessionAttribute("Basket", basket);

		ProductDAOMock productDAO = new ProductDAOMock(testProduct);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		Assert.assertEquals(0, basket.getBasketElements().size());

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(1, updatedBasket.getBasketElements().size());

		Assert.assertNotNull(updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(productIdParam, updatedBasket.getBasketElements()
				.get(0).product.getId());

		Assert.assertEquals(testProduct,
				updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(amountParam,
				updatedBasket.getBasketElements().get(0).amount);
	}

	public void testDoPostUpdateAmount() {

		int productIdParam = 7;
		int baseAmount = 3;
		int amountParam = 5;

		Product testProduct = new Product();
		testProduct.setId(productIdParam);

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));
		tester.addRequestParameter("amount", Integer.toString(amountParam));

		tester.createServlet(AddToBasketServlet.class);

		BasketManager basket = new BasketManager();
		basket.addToBasket(testProduct, baseAmount);
		tester.setSessionAttribute("Basket", basket);

		ProductDAOMock productDAO = new ProductDAOMock(testProduct);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		Assert.assertEquals(1, basket.getBasketElements().size());

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(1, updatedBasket.getBasketElements().size());

		Assert.assertNotNull(updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(productIdParam, updatedBasket.getBasketElements()
				.get(0).product.getId());

		Assert.assertEquals(testProduct,
				updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(baseAmount + amountParam, updatedBasket
				.getBasketElements().get(0).amount);
	}

	public void testDoPostNegativeAmount() {

		int productIdParam = 7;
		int baseAmount = 3;
		int amountParam = -5;

		Product testProduct = new Product();
		testProduct.setId(productIdParam);

		tester.addRequestParameter("productid",
				Integer.toString(productIdParam));
		tester.addRequestParameter("amount", Integer.toString(amountParam));

		tester.createServlet(AddToBasketServlet.class);

		BasketManager basket = new BasketManager();
		basket.addToBasket(testProduct, baseAmount);
		tester.setSessionAttribute("Basket", basket);

		ProductDAOMock productDAO = new ProductDAOMock(testProduct);

		factory.getMockServletContext().setAttribute("ProductDAO",
				(ProductDAO) productDAO);

		Assert.assertEquals(1, basket.getBasketElements().size());

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(1, updatedBasket.getBasketElements().size());

		Assert.assertNotNull(updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(productIdParam, updatedBasket.getBasketElements()
				.get(0).product.getId());

		Assert.assertEquals(testProduct,
				updatedBasket.getBasketElements().get(0).product);

		Assert.assertEquals(baseAmount, updatedBasket.getBasketElements()
				.get(0).amount);
	}
}
