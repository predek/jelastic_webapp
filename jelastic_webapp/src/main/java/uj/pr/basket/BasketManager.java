package uj.pr.basket;

import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Product;

public class BasketManager {
	private ArrayList<BasketElement> basketElements;

	public BasketManager() {
		basketElements = new ArrayList<BasketElement>();
	}

	public void addToBasket(Product product, int amount) {

		if (amount > 0) {
			boolean isProductInBasket = false;
			for (int i = 0; i < basketElements.size(); i++) {
				BasketElement basketElement = (BasketElement) basketElements
						.get(i);
				if (basketElement.product.getId() == product.getId()) {
					// product already in basket, change only amount
					isProductInBasket = true;

					basketElement.amount += amount;
				}
			}

			if (!isProductInBasket) {
				// add new product to basket
				BasketElement basketElement = new BasketElement(product, amount);

				basketElements.add(basketElement);
			}
		}
	}

	public void removeFromBasket(Product product) {

		for (int i = 0; i < basketElements.size(); i++) {
			BasketElement basketElement = (BasketElement) basketElements.get(i);
			if (basketElement.product.getId() == product.getId()) {
				basketElements.remove(basketElement);
			}
		}
	}

	public void setBasketElements(ArrayList<BasketElement> basketElements) {
		this.basketElements = basketElements;
	}

	public ArrayList<BasketElement> getBasketElements() {
		return basketElements;
	}

	public void empty() {
		basketElements = new ArrayList<BasketElement>();
	}
}
