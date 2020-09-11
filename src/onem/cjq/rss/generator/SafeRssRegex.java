package onem.cjq.rss.generator;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import onem.cjq.rss.generator.domain.RssEachItem;
import onem.cjq.rss.generator.domain.RssMatchResult;

/**
 * 应用层应该调用这个方法来使用正则表达式，可以帮助避免因为正则表达式进入死循环导致的线程卡死问题
 * 同时在正则发生异常时会向上抛出异常，应用层应该捕获并做出相应处理
 * @author cjq
 *
 */
public class SafeRssRegex implements RssRegex {

	private static Logger logger = Logger.getLogger(SafeRssRegex.class); 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RssMatchResult> exportMatchContent(String src, String rssRegex) {
		// TODO Auto-generated method stub
		Callable<Object> callable = new CallableRssTask(src, rssRegex);
		FutureTask<Object> task = new FutureTask<>(callable);
		new Thread(task).start();
		Object result = null;
		try {
			result = task.get(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			task.cancel(true);
			throw new RuntimeException(e);
		}
		return (List<RssMatchResult>) result;
	}

	@Override
	public String[] exportWebInfo(String src) {
		// TODO Auto-generated method stub
		Callable<Object> callable = new CallableRssTask(src);
		FutureTask<Object> task = new FutureTask<>(callable);
		new Thread(task).start();
		Object result = null;
		try {
			result = task.get(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			task.cancel(true);
			throw new RuntimeException(e);
		}
		return (String[]) result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RssEachItem> exportRssMainList(List<RssMatchResult> list, String[] format) {
		// TODO Auto-generated method stub
		Callable<Object> callable = new CallableRssTask(list, format);
		FutureTask<Object> task = new FutureTask<>(callable);
		new Thread(task).start();
		Object result = null;
		try {
			result = task.get(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			task.cancel(true);
			throw new RuntimeException(e);
		}
		return (List<RssEachItem>) result;
	}

}
