package uj.pr.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uj.pr.basket.BasketManager;
import uj.pr.dao.UserDAO;
import uj.pr.model.User;
import uj.pr.templates.LoginTemplate;

public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginTemplate loginTemplate = new LoginTemplate(this, request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String message;

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		UserDAO userdao = (UserDAO) getServletContext().getAttribute("UserDAO");
		int userId = userdao.authenticateUser(user);

		if (userId != -1) {
			message = "user logged in";

			HttpSession session = request.getSession();
			session.setAttribute("isLogged", "true");
			session.setAttribute("userId", userId);

			BasketManager basket = new BasketManager(); // add/update user basket after login
			session.setAttribute("Basket", basket);

			response.sendRedirect("/allproducts"); // po zalogowaniu
													// przekierowuje na liste
													// produktow

		} else {
			message = "wrong username/password";
		}

		message = message + "(userId = " + Integer.toString(userId) + ")";
		out.println(message);
		out.close();
	}

	public void destroy() {

	}
}
