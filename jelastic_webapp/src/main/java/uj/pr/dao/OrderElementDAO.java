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

	public ArrayList<OrderElement> getElementsOfOrder(int orderId) {
		
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM `orderElement` WHERE orderId = ?";
		
		ArrayList<OrderElement> result = new ArrayList<OrderElement>();
		
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			
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

				result.add(orderElement);
			}
		} catch (Exception e) {
			
		} finally {
			closeQuietly(s, c);
		}
		
		return result;

	}
	
	public boolean addOrderElement(OrderElement orderElement) {
		
		Connection c = null;
		PreparedStatement s = null;
		boolean result;
		
		String sql = "INSERT INTO orderElement (id, orderId, productId, amount) VALUES (null, ?, ?, ?)";
		
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setInt(1, orderElement.getOrderId());
			s.setInt(2, orderElement.getProductId());
			s.setInt(3, orderElement.getAmount());
			
			result = s.execute();
			
		} catch (Exception e) {
			result = false;
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
