package uj.pr.misc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uj.pr.dao.*;

public class InitListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent e) {
		
		CategoryDAO categorydao = new CategoryDAO();
		e.getServletContext().setAttribute("CategoryDAO", categorydao);
		
		OrderDAO orderdao = new OrderDAO();
		e.getServletContext().setAttribute("OrderDAO", orderdao);
		
		OrderElementDAO orderelementdao = new OrderElementDAO();
		e.getServletContext().setAttribute("OrderElementDAO", orderelementdao);
		
		ProductDAO productdao = new ProductDAO();
		e.getServletContext().setAttribute("ProductDAO", productdao);
		
		UserDAO userdao = new UserDAO();
		e.getServletContext().setAttribute("UserDAO", userdao);
		

	}

	public void contextDestroyed(ServletContextEvent e) {

	}

}
