package uj.pr.templates;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.dao.UserDAO;
import uj.pr.model.User;

public class Header {

	private String html;

	public Header(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) {

		html = "";

		html = html + "<a href=\"./allproducts\">produkty</a><br>"
				+ "<a href=\"./showbasket\">koszyk</a><br>"
				+ "<a href=\"./orders\">zamówienia</a><br>"
				+ "<a href=\"./register\">rejestracja</a><br>"
				+ "<a href=\"./login\">logowanie</a><br><br>";

		boolean isLogged = request.getSession().getAttribute("isLogged") != null;

		if (isLogged) {

			int userId = Integer.parseInt((String) request.getSession()
					.getAttribute("userId").toString());

			UserDAO userdao = (UserDAO) servlet.getServletContext()
					.getAttribute("UserDAO");
			User user = userdao.getUserById(userId);

			html = html + "(zalogowany jako " + user.getUsername() + ")<br><br>";
		} else {
			html = html + "(niezalogowany)<br><br>";
		}
	}

	public String getContent() {
		return html;
	}

}
