package uj.pr.tests.custommocks;

import java.util.ArrayList;

import uj.pr.dao.PurchaseDAO;
import uj.pr.model.Purchase;

public class PurchaseDAOMock extends PurchaseDAO {

	public ArrayList<Purchase> purchases = new ArrayList<Purchase>();

	public PurchaseDAOMock() {

	}

	public int add(Purchase purchase) {
		purchases.add(purchase);

		return purchases.size();
	}

	public int size() {
		return purchases.size();
	}

	public ArrayList<Purchase> getList() {
		return purchases;
	}

}
