package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uj.pr.model.Category;

public class CategoryDAO {
	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public Category getCategoryById(int categoryId) {
		
		Category category = new Category();
		
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM category WHERE id = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);			
			s.setString(1, Integer.toString(categoryId));
			ResultSet r = s.executeQuery();
			while (r.next()) {				
				
				category.setId(categoryId);
				category.setName(r.getString("name"));
				category.setDescription(r.getString("description"));
				category.setDiscountStart(Float.parseFloat(r.getString("discountStart")));
				category.setDiscountEnd(Float.parseFloat(r.getString("discountEnd")));
				category.setPercentDiscount(Float.parseFloat(r.getString("percentDiscount")));
				
				return category;
			}
			return category;
		} catch (Exception e) {
			return category;
		} finally {
			closeQuietly(s, c);
		}
	}

	private void closeQuietly(PreparedStatement s, Connection c) {
		try {
			s.close();
			c.close();
		} catch (Exception e) {

		}
	}
}
