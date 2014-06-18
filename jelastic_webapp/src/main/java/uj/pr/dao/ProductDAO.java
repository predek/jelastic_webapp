package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Product;

public class ProductDAO {
	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public List<Product> getAllProducts() {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM product";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			List<Product> list = new ArrayList<Product>();
			ResultSet r = s.executeQuery();
			while (r.next()) {

				Product product = new Product();

				int id = Integer.parseInt(r.getString("id"));
				product.setId(id);

				int categoryId = Integer.parseInt(r.getString("categoryId"));
				product.setCategoryId(categoryId);

				String name = r.getString("name");
				product.setName(name);

				String description = r.getString("description");
				product.setDescription(description);

				Float price = Float.parseFloat(r.getString("price"));
				product.setPrice(price);

				list.add(product);
			}
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			closeQuietly(s, c);
		}

	}

	public Product getProductById(int id) {

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM product WHERE id = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			while (r.next()) {

				Product product = new Product();

				product.setId(id);

				int categoryId = Integer.parseInt(r.getString("categoryId"));
				product.setCategoryId(categoryId);

				String name = r.getString("name");
				product.setName(name);

				String description = r.getString("description");
				product.setDescription(description);

				Float price = Float.parseFloat(r.getString("price"));
				product.setPrice(price);

				return product;
			}
			return null;
		} catch (Exception e) {
			return null;
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
