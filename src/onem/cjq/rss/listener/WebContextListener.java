package onem.cjq.rss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import onem.cjq.rss.thread.RssGenManage;

public class WebContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

		String contextRealPath = arg0.getServletContext().getRealPath("/");
		System.out.println("context initial,context real path: " + contextRealPath);
		
		System.out.println("Thread RssGenManage start~");
		new Thread(RssGenManage.getInstance()).start();
		
		/*
		 * InputStream in =
		 * getClass().getClassLoader().getResourceAsStream("/sqlite.config"); Properties
		 * properties = new Properties();
		 * 
		 * try { properties.load(in); } catch (IOException e1) { // TODO Auto-generated
		 * catch block System.out.println("sqlite.config read error"); return; }
		 * 
		 * String dbPath = properties.getProperty("db.path"); File f = new File(dbPath);
		 * JDBCUtils.setDBPath(dbPath); System.out.println("sqlite db path " + dbPath);
		 * 
		 * if (!f.exists()) { Statement stat = null; Connection conn = null;
		 * 
		 * System.out.println("sqlite not exist ,need create");
		 * 
		 * try { conn = JDBCUtils.getConnection(); stat = conn.createStatement(); String
		 * sql = "CREATE TABLE RSS " + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
		 * " XML TEXT ," + "WEB_LINK TEXT ," + "ENCODE TEXT ," + "GLOBAL_REG TEXT," +
		 * "ITEM_REG TEXT ," + "FEED_TITLE TEXT ," + "FEED_LINK TEXT ," +
		 * "FEED_DESR TEXT ," + "ITEM_TITLE_FORMAT TEXT ," + "ITEM_LINK_FORMAT TEXT ," +
		 * "ITEM_DESR_FORMAT TEXT " + ")"; stat.executeUpdate(sql);
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } finally { try { stat.close(); JDBCUtils.release(conn);
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * } } else {
		 * System.out.println("sqlite db is already exist,no need create again"); }
		 */
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("context destroyed");
	}

}
