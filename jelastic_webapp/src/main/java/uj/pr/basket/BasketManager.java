package uj.pr.basket;

import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Product;

public class BasketManager {
	private ArrayList<BasketElement> basketElements;

	public BasketManager() {
		basketElements = new ArrayList<BasketElement>();
	}

	public void addToBasket(Product product, int amount) {		//make sure that product object contains id

		boolean isProductInBasket = false;
		for (int i = 0; i < basketElements.size(); i++) {
			BasketElement basketElement = (BasketElement) basketElements.get(i);
			if (basketElement.product.getId() == product.getId()) {		//product already in basket, change only amount
				isProductInBasket = true;
				
				basketElement.amount += amount;
			}
		}
		
		if(!isProductInBasket)
		{
			BasketElement basketElement = new BasketElement(product, amount);		//add new product to basket
			basketElements.add(basketElement);			
		}
	}

	public void removeFromBasket(Product product) {		//make sure that product object contains id
		
		for (int i = 0; i < basketElements.size(); i++) {
			BasketElement basketElement = (BasketElement) basketElements.get(i);
			if (basketElement.product.getId() == product.getId()) {
				basketElements.remove(basketElement);
			}
		}
	}
	
	public ArrayList<BasketElement> getBasketElements(){
		return basketElements;
	}
	
	public void empty(){
		basketElements = new ArrayList<BasketElement>();
	}
}
