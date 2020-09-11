package onem.cjq.rss.thread;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import onem.cjq.rss.db.JDBCUtils;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.service.ScratchService;
import onem.cjq.rss.web.ConnectionContext;

public class RssGenTaskRunnable implements Runnable {
	
	private static Logger logger = Logger.getLogger(RssGenTaskRunnable.class); 

	private Feed feed = null;

	public RssGenTaskRunnable(Feed feed) {
		// TODO Auto-generated constructor stub
		this.feed = feed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.debug("Feed id " + feed.getId() + " 开始执行更新任务");
		Connection conn = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(conn);
		try {
			conn.setAutoCommit(false);
			new ScratchService().generateXMLAndSave(feed);
			conn.commit();
			logger.debug(feed.getId()+"事务已提交~");
			// 上面的三条指令都可能产生不同的异常，可能需要分开来各自处理
		} catch (SocketTimeoutException exception) {
			logger.error("更新任务时连接超时！");
			logger.error(exception);
		}catch (ConnectException e) {
			logger.error("更新任务时被拒绝连接！");
			logger.error(e);
		}catch (Exception e) {
			logger.error("RssGenTaskRunnable--task process fail,try rollback it");
			logger.error(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("RssGenTaskRunnable--rollback fail!");
				logger.error(e1);
			}
		} finally {
			JDBCUtils.release(conn);

		}

	}

}
