package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Product;
import uj.pr.model.Purchase;
import uj.pr.model.User;

public class PurchaseDAO {
	public String status;

	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public List<Purchase> getAll() {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM purchase";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			List<Purchase> list = new ArrayList<Purchase>();
			ResultSet r = s.executeQuery();
			while (r.next()) {

				Purchase purchase = new Purchase();

				int id = Integer.parseInt(r.getString("id"));
				purchase.setId(id);

				int userId = Integer.parseInt(r.getString("userId"));
				purchase.setUserId(userId);

				list.add(purchase);
			}
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			closeQuietly(s, c);
		}

	}

	public int add(Purchase purchase) {

		int result = -1;

		Connection c = null;
		PreparedStatement s = null;
		
		String sql = "INSERT INTO purchase (id, userId) VALUES (NULL, ?)";
		try {
			c = getConnection();
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			s.setString(1, Integer.toString(purchase.getUserId()));
			
			int insertResult = s.executeUpdate();

			if (insertResult != -1) {
				ResultSet generatedKeys = s.getGeneratedKeys();
				if (generatedKeys.next()) {
					result = ((Long)generatedKeys.getLong(1)).intValue();
				}
			}
			
		} catch (Exception e) {
			status = e.getMessage();
			result = -1;
		} finally {
			closeQuietly(s, c);
		}

		return result;

	}

	private void closeQuietly(PreparedStatement s, Connection c) {
		try {
			s.close();
			c.close();
		} catch (Exception e) {

		}
	}

}
