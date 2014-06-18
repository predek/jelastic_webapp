package pr.uj.basket;

import uj.pr.model.Product;

public class BasketElement {
	public Product product;
	public int amount;
	
	public BasketElement(Product product, int amount){
		this.product = product;
		this.amount = amount;
	}

}
