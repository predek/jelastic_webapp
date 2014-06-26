package uj.pr.tests.custommocks;

import java.util.ArrayList;

import uj.pr.dao.CategoryDAO;
import uj.pr.model.Category;

public class CategoryDAOMock extends CategoryDAO {
	ArrayList<Category> categories;

	public CategoryDAOMock(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public Category getCategoryById(int id) {

		for (Category category : categories) {
			if (category.getId() == id)
				return category;
		}

		return null;
	}
}
