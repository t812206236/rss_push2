package onem.cjq.rss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import onem.cjq.rss.thread.RssGenManage;

public class WebContextListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(WebContextListener.class); 

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		String contextRealPath = arg0.getServletContext().getRealPath("/");
		logger.debug("context initial,context real path: " + contextRealPath);
		
		logger.debug("Thread RssGenManage start~");
		new Thread(RssGenManage.getInstance()).start();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.debug("context destroyed");
	}

}
