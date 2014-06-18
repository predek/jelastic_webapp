package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uj.pr.model.OrderElement;

public class OrderElementDAO {
	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public List<OrderElement> getElementsOfOrder(int orderId) {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM orderElement WHERE orderId = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			List<OrderElement> list = new ArrayList<OrderElement>();
			ResultSet r = s.executeQuery();
			while (r.next()) {

				OrderElement orderElement = new OrderElement();
				
				int id = Integer.parseInt(r.getString("id"));
				orderElement.setId(id);
				
				orderElement.setOrderId(orderId);
				
				int productId = Integer.parseInt(r.getString("productId"));
				orderElement.setProductId(productId);
				
				int amount = Integer.parseInt(r.getString("amount"));
				orderElement.setProductId(amount);

				list.add(orderElement);
			}
			return list;
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
