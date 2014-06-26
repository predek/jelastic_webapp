package uj.pr.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	public LogoutServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.removeAttribute("isLogged");
		session.removeAttribute("userId");
		session.removeAttribute("Basket");

		response.sendRedirect("/login");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void destroy() {

	}
}
