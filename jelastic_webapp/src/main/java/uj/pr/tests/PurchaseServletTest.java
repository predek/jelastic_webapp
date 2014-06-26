package uj.pr.tests;

import sun.security.ssl.Debug;
import uj.pr.basket.BasketManager;
import uj.pr.dao.ProductDAO;
import uj.pr.dao.PurchaseDAO;
import uj.pr.dao.PurchaseElementDAO;
import uj.pr.model.Product;
import uj.pr.model.Purchase;
import uj.pr.servlets.AddToBasketServlet;
import uj.pr.servlets.PurchaseServlet;
import uj.pr.tests.custommocks.ProductDAOMock;
import uj.pr.tests.custommocks.PurchaseDAOMock;
import uj.pr.tests.custommocks.PurchaseElementDAOMock;
import junit.framework.Assert;
import junit.framework.TestCase;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class PurchaseServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPost() {

		tester.createServlet(PurchaseServlet.class);

		PurchaseDAOMock purchaseDAO = new PurchaseDAOMock();
		PurchaseElementDAOMock purchaseElementDAO = new PurchaseElementDAOMock();

		factory.getMockServletContext().setAttribute("PurchaseDAO",
				(PurchaseDAO) purchaseDAO);

		factory.getMockServletContext().setAttribute("PurchaseElementDAO",
				(PurchaseElementDAO) purchaseElementDAO);

		BasketManager basket = new BasketManager();

		Product testProduct1 = new Product();
		testProduct1.setId(5);		//irrelevant
		testProduct1.setCategoryId(0);
		testProduct1.setDescription("");
		testProduct1.setName("test1");
		testProduct1.setPrice((float) 10);

		Product testProduct2 = new Product();
		testProduct2.setId(6);
		testProduct2.setCategoryId(1);
		testProduct2.setDescription("");
		testProduct2.setName("test2");
		testProduct2.setPrice((float) 15);

		basket.addToBasket(testProduct1, 2);
		basket.addToBasket(testProduct2, 3);

		tester.setSessionAttribute("Basket", basket);
		int testUserId = 5;
		tester.setSessionAttribute("userId", Integer.toString(testUserId));

		Assert.assertEquals(2, basket.getBasketElements().size());

		tester.doPost();

		BasketManager updatedBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(0, updatedBasket.getBasketElements().size());

		PurchaseDAOMock updatedPurchaseDAO = (PurchaseDAOMock) factory.getMockServletContext()
				.getAttribute("PurchaseDAO");
		PurchaseElementDAOMock updatedPurchaseElementDAO = (PurchaseElementDAOMock) factory
				.getMockServletContext().getAttribute("PurchaseElementDAO");

		Assert.assertEquals(1, updatedPurchaseDAO.size());
		Assert.assertEquals(2, updatedPurchaseElementDAO.size());
		
		Assert.assertEquals(testProduct1.getId(), updatedPurchaseElementDAO.getList().get(0).getProductId());
		Assert.assertEquals(testProduct2.getId(), updatedPurchaseElementDAO.getList().get(1).getProductId());
		
		Assert.assertEquals(2, updatedPurchaseElementDAO.getList().get(0).getAmount());
		Assert.assertEquals(3, updatedPurchaseElementDAO.getList().get(1).getAmount());
		
		//System.out.println(Integer.toString(updatedPurchaseElementDAO.getList().get(0).getProductId()));
	}
}
