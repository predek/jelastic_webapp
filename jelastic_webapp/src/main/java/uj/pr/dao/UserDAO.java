package uj.pr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uj.pr.model.User;

public class UserDAO {
	private String URL = "jdbc:mysql://mysql-testaccount.jelastic.dogado.eu/onlineshop";
	private String login = "root";
	private String pass = "xQC2F8GtcU";

	private Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL, login, pass);
	}

	public boolean registerUser(User user) {

		if (hasUser(user)) {
			return false;
		} else {

			Connection c = null;
			PreparedStatement s = null;
			String sql = "INSERT INTO user (username, password, totalSpent) VALUES (?, ?, ?)";
			try {
				c = getConnection();
				s = c.prepareStatement(sql);
				s.setString(1, user.getUsername());
				s.setString(2, user.getPassword());
				s.setString(3, "0");
				s.execute(); // dla INSERT s.execute() zwraca false, bo
								// statement zwraca pusty resultset
				return true;
			} catch (Exception e) {
				return false;
			} finally {
				closeQuietly(s, c);
			}
		}

	}

	private boolean hasUser(User user) {

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM user WHERE username = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, user.getUsername());
			ResultSet r = s.executeQuery();
			while (r.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			closeQuietly(s, c);
		}
	}

	public int authenticateUser(User user) {

		//boolean userExists = false;
		int userId = -1;

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, user.getUsername());
			s.setString(2, user.getPassword());
			ResultSet r = s.executeQuery();
			while (r.next()) {
				// return r.getString("id");
				//userExists = true;
				userId = Integer.parseInt(r.getString("id"));
				// returnValue = returnValue + r.getString("id");
			}
			// returnValue = returnValue + "1";
		} catch (SQLException e) {
			// return e.getMessage() + e.getErrorCode() + e.getSQLState();
			// returnValue = returnValue + "2";
		} catch (ClassNotFoundException e) {
			// return e.getMessage() + e.getCause() + e.getStackTrace();
			// returnValue = returnValue + "3";
		} finally {
			closeQuietly(s, c);
			// returnValue = returnValue + "4";
		}

		return userId;
	}

	public User getUserById(int userId) {

		User user = new User();

		Connection c = null;
		PreparedStatement s = null;
		String sql = "SELECT * FROM user WHERE id = ?";
		try {
			c = getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, Integer.toString(userId));
			ResultSet r = s.executeQuery();
			while (r.next()) {

				user.setUsername(r.getString("username"));
				user.setPassword(r.getString("password"));
				return user;
			}
			return user;
		} catch (Exception e) {
			return user;
		} finally {
			closeQuietly(s, c);
		}
	}

	public boolean isAdmin(User user) {
		boolean isAdmin = user.getUsername() == "admin"
				&& user.getPassword() == "admin";

		return isAdmin;
	}

	private void closeQuietly(PreparedStatement s, Connection c) {
		try {
			s.close();
			c.close();
		} catch (Exception e) {

		}
	}
}
