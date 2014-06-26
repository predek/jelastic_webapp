package uj.pr.tests;

import uj.pr.basket.BasketManager;
import uj.pr.model.Product;
import junit.framework.Assert;
import junit.framework.TestCase;

public class BasketManagerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testAdd() {

		BasketManager basketManager = new BasketManager();

		Assert.assertEquals(0, basketManager.getBasketElements().size());

		Product product1 = new Product();
		product1.setId(0);
		Product product2 = new Product();
		product2.setId(1);

		basketManager.addToBasket(product1, 1);

		Assert.assertEquals(1, basketManager.getBasketElements().size());

		Assert.assertEquals(1, basketManager.getBasketElements().get(0).amount);

		basketManager.addToBasket(product1, 2);

		Assert.assertEquals(1, basketManager.getBasketElements().size());

		Assert.assertEquals(3, basketManager.getBasketElements().get(0).amount);

		basketManager.addToBasket(product2, 2);

		Assert.assertEquals(2, basketManager.getBasketElements().size());

	}

	public void testRemove() {

		BasketManager basketManager = new BasketManager();

		Product product1 = new Product();
		product1.setId(0);
		Product product2 = new Product();
		product2.setId(1);

		basketManager.addToBasket(product1, 1);
		basketManager.addToBasket(product2, 2);

		Assert.assertEquals(2, basketManager.getBasketElements().size());

		basketManager.removeFromBasket(product1);

		Assert.assertEquals(1, basketManager.getBasketElements().size());

		basketManager.removeFromBasket(product2);

		Assert.assertEquals(0, basketManager.getBasketElements().size());
	}

	public void testEmpty() {

		BasketManager basketManager = new BasketManager();

		Product product1 = new Product();
		product1.setId(0);
		Product product2 = new Product();
		product2.setId(1);

		basketManager.addToBasket(product1, 1);
		basketManager.addToBasket(product2, 2);

		Assert.assertEquals(2, basketManager.getBasketElements().size());

		basketManager.empty();

		Assert.assertEquals(0, basketManager.getBasketElements().size());

	}

}
