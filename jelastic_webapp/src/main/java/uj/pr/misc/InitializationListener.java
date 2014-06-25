package uj.pr.misc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uj.pr.dao.*;

public class InitializationListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent e) {
		
		CategoryDAO categorydao = new CategoryDAO();
		e.getServletContext().setAttribute("CategoryDAO", categorydao);
		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		e.getServletContext().setAttribute("PurchaseDAO", purchaseDAO);
		
		PurchaseElementDAO purchaseElementDAO = new PurchaseElementDAO();
		e.getServletContext().setAttribute("PurchaseElementDAO", purchaseElementDAO);
		
		ProductDAO productdao = new ProductDAO();
		e.getServletContext().setAttribute("ProductDAO", productdao);
		
		UserDAO userdao = new UserDAO();
		e.getServletContext().setAttribute("UserDAO", userdao);
		

	}

	public void contextDestroyed(ServletContextEvent e) {

	}

}
