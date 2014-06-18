package pr.uj.basket;

import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Product;

public class Basket {
	private ArrayList<BasketElement> basketContent;

	public Basket() {
		basketContent = new ArrayList<BasketElement>();
	}

	public void addToBasket(Product product, int amount) {		//make sure that product object contains id

		boolean isProductInBasket = false;
		for (int i = 0; i < basketContent.size(); i++) {
			BasketElement basketElement = (BasketElement) basketContent.get(i);
			if (basketElement.product.getId() == product.getId()) {		//product already in basket, change only amount
				isProductInBasket = true;
				
				basketElement.amount += amount;
			}
		}
		
		if(!isProductInBasket)
		{
			BasketElement basketElement = new BasketElement(product, amount);		//add new product to basket
			basketContent.add(basketElement);			
		}
	}

	public void removeFromBasket(Product product) {		//make sure that product object contains id
		
		for (int i = 0; i < basketContent.size(); i++) {
			BasketElement basketElement = (BasketElement) basketContent.get(i);
			if (basketElement.product.getId() == product.getId()) {
				basketContent.remove(basketElement);
			}
		}
	}
	
	public ArrayList<BasketElement> showBasket(){
		return basketContent;
	}
	
	public void calculatePrice(){		//todo //discounts etc
		
	}
}
