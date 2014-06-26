package uj.pr.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(AddToBasketServletTest.class);
		suite.addTestSuite(LoginServletTest.class);
		suite.addTestSuite(LogoutServletTest.class);
		suite.addTestSuite(PurchaseServletTest.class);
		suite.addTestSuite(RegisterServletTest.class);
		suite.addTestSuite(RemoveFromBasketServletTest.class);
		//$JUnit-END$
		return suite;
	}

}
