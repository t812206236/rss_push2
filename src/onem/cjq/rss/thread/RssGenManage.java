package onem.cjq.rss.thread;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import onem.cjq.rss.dao.FeedDAO;
import onem.cjq.rss.dao.impl.FeedDAOImpl;
import onem.cjq.rss.db.JDBCUtils;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.web.ConnectionContext;

public class RssGenManage implements Runnable {
	// 存储exectors添加的schedule任务，方便之后执行删除，更新等操作
	private volatile Map<Integer, ScheduledFuture<?>> tasks = new HashMap<Integer, ScheduledFuture<?>>();
	// 从数据库中提取等待执行的任务，不能把新的、将要执行的任务放到此处，因为当前线程正在逐个遍历任务并添加到线程池中
	private volatile List<Feed> feeds = null;
	private volatile ScheduledExecutorService ses = Executors.newScheduledThreadPool(20);
	private static RssGenManage rssGenManage = new RssGenManage();
	// 为了操作安全，同时锁住tasks和feeds操作，这个逻辑可能存在一点点效率问题，因为addTask时会去等待run方法中lock解锁
	// 不过应该不会耗时太多而且这是一个公平锁
	private Lock lock = new ReentrantLock(true);
	private FeedDAO fdi = new FeedDAOImpl();

	public static RssGenManage getInstance() {
		return rssGenManage;
	}

	/**
	 * 添加 或者 更新
	 */
	public void addTask(Feed feed) {
		ScheduledFuture<?> task = null;
		lock.lock();
		try {
			if ((task = tasks.get(feed.getId())) == null) {
				System.out.println("更新还未被调度的任务id " + feed.getId());
				// 这里不用考虑feed在数据库是否存在的问题，因为进入feed制作页面必定会创建一个带id的feed
				// 所以只要更新就好了
				fdi.updateFeed(feed);			
			} else {
				System.out.println("更新已经被调度的任务id "+feed.getId());
				task.cancel(true);
				task = ses.scheduleAtFixedRate(new RssGenTaskRunnable(feed), 0, 30, TimeUnit.MINUTES);
				tasks.put(feed.getId(), task);
			}
		} catch (Exception e) {
			System.out.println("添加任务时出现问题");
		}finally {
			lock.unlock();
		}
	}
	/**
	 * 
	 */
	public void delTask(int id) {
		ScheduledFuture<?> task = null;
		lock.lock();
		try {
			if((task=tasks.get(id))!=null) {
				System.out.println("删除正在执行的任务id "+id);
				task.cancel(true);
			}
			fdi.delFeed(id);
		}catch(Exception e) {
			System.out.println("删除任务时出现问题");
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(conn);

		feeds = fdi.getAll();
		Iterator<Feed> it = feeds.iterator();
		while (it.hasNext()) {
			lock.lock();
			try {
				final Feed feed = it.next();
				// 重新校验该任务的有效性，因为用户可能提前删除掉某个任务
				// 或者通过addTask去更新某个任务
				if (fdi.getFeed(feed.getId()) == null) {
					it.remove();
					System.out.println("更新时发现无效任务，取消");
					continue;
				}
				ScheduledFuture<?> sf = ses.scheduleAtFixedRate(new RssGenTaskRunnable(feed), 0, 30, TimeUnit.MINUTES);
				tasks.put(feed.getId(), sf);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("在更新调度时发生错误");
			} finally {
				lock.unlock();
			}
			// 等待十秒后执行下一个任务，目的为了错开任务，避免一次占用大量的connection和网络资源
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e1) {
				System.out.println("不可能到此处");
			}
		}
		System.out.println("所有任务已经更新完成！");
		JDBCUtils.release(conn);

	}

}
