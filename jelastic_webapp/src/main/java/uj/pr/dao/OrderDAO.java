package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public ArrayList<Order> getAllOrders() {

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM order";

		ArrayList<Order> list = new ArrayList<Order>();

		try {
			c = getConnection();
			s = c.prepareStatement(sql);

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

	public int addOrder(Order order) {
		
		int result = -1;

		Connection c = null;
		PreparedStatement s = null;
		String sql = "INSERT INTO order (id, userId) VALUES (NULL, ?)";
		
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, Integer.toString(order.getUserId()));
			s.execute(); // returns false for insert
			s.exe
			result = 1;
		} catch (Exception e) {
			result = -1;
		} finally {
			closeQuietly(s, c);
		}
		
		return result;
	}
	
	public int lastInsertedId(){
		
		int result = -1;

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT last_inserted_id() AS last_id FROM order";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			result = Integer.parseInt(rs.getString("last_id"));
		} catch (Exception e) {
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
