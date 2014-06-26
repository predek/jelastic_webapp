package uj.pr.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import uj.pr.dao.UserDAO;
import uj.pr.model.User;
import uj.pr.servlets.RegisterServlet;
import uj.pr.tests.custommocks.UserDAOMock;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

public class RegisterServletTest extends TestCase {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);
	}

	public void testDoPost() {

		UserDAOMock userDAO = new UserDAOMock(new User());

		factory.getMockServletContext().setAttribute("UserDAO",
				(UserDAO) userDAO);

		String testUsername = "rw34g3";
		String testPassword = "fj3444";

		User testUser = new User();
		testUser.setUsername(testUsername);
		testUser.setPassword(testPassword);

		tester.createServlet(RegisterServlet.class);

		tester.addRequestParameter("username", testUsername);
		tester.addRequestParameter("password", testPassword);

		tester.doPost();

		UserDAOMock updatedUserDAO = (UserDAOMock) factory
				.getMockServletContext().getAttribute("UserDAO");

		Assert.assertEquals(1, updatedUserDAO.users.size());
		Assert.assertNotNull(updatedUserDAO.users.get(0));
		Assert.assertEquals(testUsername, updatedUserDAO.users.get(0)
				.getUsername());
		Assert.assertEquals(testPassword, updatedUserDAO.users.get(0)
				.getPassword());
	}
}
