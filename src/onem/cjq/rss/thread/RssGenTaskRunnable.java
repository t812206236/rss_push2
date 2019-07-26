package onem.cjq.rss.thread;

import java.sql.Connection;
import java.sql.SQLException;

import onem.cjq.rss.db.JDBCUtils;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.service.ScratchService;
import onem.cjq.rss.web.ConnectionContext;

public class RssGenTaskRunnable implements Runnable {

	private Feed feed = null;

	public RssGenTaskRunnable(Feed feed) {
		// TODO Auto-generated constructor stub
		this.feed = feed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Feed id " + feed.getId() + " 开始执行更新任务");
		Connection conn = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(conn);
		try {
			conn.setAutoCommit(false);
			new ScratchService().generateXMLAndSave(feed);
			conn.commit();
			// 上面的三条指令都可能产生不同的异常，可能需要分开来各自处理
		} catch (Exception e) {
			System.out.println("RssGenTaskRunnable--task process fail,try rollback it");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("RssGenTaskRunnable--rollback fail!");
			}
		} finally {
			JDBCUtils.release(conn);

		}

	}

}
