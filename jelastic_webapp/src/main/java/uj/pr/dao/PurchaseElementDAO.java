package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uj.pr.model.PurchaseElement;

public class PurchaseElementDAO {
	public String status;

	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public List<PurchaseElement> getAll() {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM purchaseElement";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			List<PurchaseElement> list = new ArrayList<PurchaseElement>();
			ResultSet r = s.executeQuery();
			while (r.next()) {

				PurchaseElement purchaseElement = new PurchaseElement();

				int id = Integer.parseInt(r.getString("id"));
				purchaseElement.setId(id);

				int productId = Integer.parseInt(r.getString("productId"));
				purchaseElement.setProductId(productId);

				int purchaseId = Integer.parseInt(r.getString("purchaseId"));
				purchaseElement.setPurchaseId(purchaseId);

				int amount = Integer.parseInt(r.getString("amount"));
				purchaseElement.setAmount(amount);

				list.add(purchaseElement);
			}
			return list;
		} catch (Exception e) {
			status = e.getMessage();
			return null;
		} finally {
			closeQuietly(s, c);
		}

	}

	public int add(PurchaseElement purchaseElement) {

		int result = -1;

		Connection c = null;
		PreparedStatement s = null;
		String sql = "INSERT INTO purchaseElement (id, purchaseId, productId, amount) VALUES (NULL, ?, ?, ?)";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, Integer.toString(purchaseElement.getPurchaseId()));
			s.setString(2, Integer.toString(purchaseElement.getProductId()));
			s.setString(3, Integer.toString(purchaseElement.getAmount()));
			s.execute(); // returns false for insert
			result = 1;
		} catch (Exception e) {
			status = e.getMessage();
			result = -1;
		} finally {
			closeQuietly(s, c);
		}

		return result;

	}

	public List<PurchaseElement> getPurchaseElements(int purchaseId) {

		// find purchase elements by purchase id

		List<PurchaseElement> result = new ArrayList<PurchaseElement>();

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM purchaseElement WHERE purchaseId = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, Integer.toString(purchaseId));
			ResultSet r = s.executeQuery();
			while (r.next()) {

				PurchaseElement purchaseElement = new PurchaseElement();

				int id = Integer.parseInt(r.getString("id"));
				purchaseElement.setId(id);

				purchaseElement.setPurchaseId(purchaseId);

				int productId = Integer.parseInt(r.getString("productId"));
				purchaseElement.setProductId(productId);
				int amount = Integer.parseInt(r.getString("amount"));
				purchaseElement.setAmount(amount);

				result.add(purchaseElement);
			}
		} catch (Exception e) {
			status = e.getMessage();
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
