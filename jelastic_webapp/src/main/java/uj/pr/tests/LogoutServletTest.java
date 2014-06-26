package uj.pr.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import uj.pr.basket.BasketManager;
import uj.pr.servlets.LogoutServlet;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class LogoutServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPost() {

		tester.setSessionAttribute("isLogged", true);
		tester.setSessionAttribute("userId", 0);
		tester.setSessionAttribute("Basket", new BasketManager());

		Assert.assertNotNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNotNull(tester.getSessionAttribute("userId"));
		Assert.assertNotNull(tester.getSessionAttribute("Basket"));

		tester.createServlet(LogoutServlet.class);

		tester.doGet();

		Assert.assertNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNull(tester.getSessionAttribute("userId"));
		Assert.assertNull(tester.getSessionAttribute("Basket"));
	}
}
