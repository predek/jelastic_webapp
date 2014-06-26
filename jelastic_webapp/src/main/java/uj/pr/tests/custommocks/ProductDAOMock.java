package uj.pr.tests.custommocks;

import uj.pr.dao.ProductDAO;
import uj.pr.model.Product;

public class ProductDAOMock extends ProductDAO {
	public Product testProduct;

	public ProductDAOMock(Product testProduct) {
		this.testProduct = testProduct;
	}

	public Product getProductById(int id) {
		return testProduct;
	}
}
