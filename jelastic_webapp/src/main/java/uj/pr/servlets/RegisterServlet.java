package uj.pr.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uj.pr.dao.UserDAO;
import uj.pr.model.*;
import uj.pr.templates.*;

public class RegisterServlet extends HttpServlet {

	public RegisterServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RegisterTemplate registerTemplate = new RegisterTemplate(this, request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		UserDAO userdao = (UserDAO) getServletContext().getAttribute("UserDAO");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		
		boolean successResult = userdao.registerUser(newUser);
		String message;
		if(successResult){
			message = "dodano u¿ytkownika";
		    
			response.sendRedirect("/login");		//po register przekierowuje na login
			
		} else {
			message = "b³¹d";
		}
		
		PrintWriter out = response.getWriter();
		out.println(message);
		out.close();
	}

	public void destroy() {

	}
}
