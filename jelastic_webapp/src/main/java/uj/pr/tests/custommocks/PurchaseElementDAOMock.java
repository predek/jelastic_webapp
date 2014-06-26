package uj.pr.tests.custommocks;

import java.util.ArrayList;

import uj.pr.dao.PurchaseElementDAO;
import uj.pr.model.PurchaseElement;

public class PurchaseElementDAOMock extends PurchaseElementDAO {

	public ArrayList<PurchaseElement> elements = new ArrayList<PurchaseElement>();

	public PurchaseElementDAOMock() {
	}

	public int add(PurchaseElement element) {

		elements.add(element);
		return elements.size();
	}

	public int size() {
		return elements.size();
	}

	public ArrayList<PurchaseElement> getList() {
		return elements;
	}
}
