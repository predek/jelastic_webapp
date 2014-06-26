package uj.pr.tests.custommocks;

import java.util.ArrayList;

import uj.pr.dao.UserDAO;
import uj.pr.model.User;

public class UserDAOMock extends UserDAO {

	public ArrayList<User> users = new ArrayList<User>();

	public UserDAOMock(User user) {
		if (user != null) {
			users.add(user);
		}
	}

	public int authenticateUser(User user) {

		int result = -1;

		User existingUser = users.get(0);

		if (user.getUsername() == existingUser.getUsername()
				&& user.getPassword() == existingUser.getPassword()) {
			result = 0;
		}

		return result;
	}

	public boolean registerUser(User user) {

		users = new ArrayList<User>();
		users.add(user);

		return true;
	}

}
