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
				+ "<a href=\"./register\">rejestracja</a><br>"
				+ "<a href=\"./login\">logowanie</a><br><br>";

		boolean isLogged = request.getSession().getAttribute("isLogged") != null;

		if (isLogged) {

			UserDAO userdao = (UserDAO) servlet.getServletContext()
					.getAttribute("UserDAO");

			int userId = Integer.parseInt((String) request.getSession()
					.getAttribute("userId").toString());
			User user = userdao.getUserById(userId);
			
			//boolean isAdmin  = userdao.isAdmin(user);
			
			html = html + "<a href=\"./orders\">zamówienia</a><br><br>";
			
			html = html + "<a href=\"./logout\">logout</a><br><br>";

			html = html + "(zalogowany jako " + user.getUsername() + ")<br><br>";
		} else {
			html = html + "(niezalogowany)<br><br>";
		}
	}

	public String getContent() {
		return html;
	}

}
