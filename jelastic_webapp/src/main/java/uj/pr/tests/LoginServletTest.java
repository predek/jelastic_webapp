package uj.pr.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import uj.pr.basket.BasketManager;
import uj.pr.dao.UserDAO;
import uj.pr.model.User;
import uj.pr.servlets.LoginServlet;
import uj.pr.tests.custommocks.UserDAOMock;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class LoginServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPostExistingUserLogin() {

		String testUsername = "rw34g3";
		String testPassword = "fj3444";
		String testTotalSpent = "555";

		User testUser = new User();
		testUser.setUsername(testUsername);
		testUser.setPassword(testPassword);
		testUser.setTotalSpent(testTotalSpent);

		UserDAOMock userDAO = new UserDAOMock(testUser);

		factory.getMockServletContext().setAttribute("UserDAO",
				(UserDAO) userDAO);

		Assert.assertNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNull(tester.getSessionAttribute("userId"));
		Assert.assertNull(tester.getSessionAttribute("Basket"));

		tester.createServlet(LoginServlet.class);

		tester.addRequestParameter("username", testUsername);
		tester.addRequestParameter("password", testPassword);

		tester.doPost();

		Assert.assertNotNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNotNull(tester.getSessionAttribute("userId"));
		Assert.assertNotNull(tester.getSessionAttribute("Basket"));

		Assert.assertEquals(true, Boolean.parseBoolean((String) tester
				.getSessionAttribute("isLogged")));
		Assert.assertEquals(0, tester.getSessionAttribute("userId"));

		BasketManager createdBasket = (BasketManager) tester
				.getSessionAttribute("Basket");

		Assert.assertEquals(0, createdBasket.getBasketElements().size());
	}

	public void testDoPostWrongUserLogin() {

		String testUsername = "rw34g3";
		String testPassword = "fj3444";
		String testTotalSpent = "555";

		User testUser = new User();
		testUser.setUsername(testUsername);
		testUser.setPassword(testPassword);
		testUser.setTotalSpent(testTotalSpent);

		UserDAOMock userDAO = new UserDAOMock(testUser);

		factory.getMockServletContext().setAttribute("UserDAO",
				(UserDAO) userDAO);

		Assert.assertNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNull(tester.getSessionAttribute("userId"));
		Assert.assertNull(tester.getSessionAttribute("Basket"));

		tester.createServlet(LoginServlet.class);

		tester.addRequestParameter("username", "");
		tester.addRequestParameter("password", "");

		tester.doPost();

		Assert.assertNull(tester.getSessionAttribute("isLogged"));
		Assert.assertNull(tester.getSessionAttribute("userId"));
		Assert.assertNull(tester.getSessionAttribute("Basket"));
	}

}
