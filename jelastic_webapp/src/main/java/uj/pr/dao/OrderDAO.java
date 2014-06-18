package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uj.pr.model.Order;

public class OrderDAO {
	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public List<Order> getAllOrders() {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM order";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			List<Order> list = new ArrayList<Order>();
			ResultSet r = s.executeQuery();
			while (r.next()) {

				Order order = new Order();

				int id = Integer.parseInt(r.getString("id"));
				order.setId(id);

				int userId = Integer.parseInt(r.getString("userId"));
				order.setUserId(userId);

				list.add(order);
			}
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			closeQuietly(s, c);
		}

	}
	
	public boolean addOrder(Order order) {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "INSERT INTO order (id, userId) VALUES (?, ?)";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, Integer.toString(order.getId()));
			s.setString(2, Integer.toString(order.getUserId()));
			return s.execute();		
		} catch (Exception e) {
			return false;
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
