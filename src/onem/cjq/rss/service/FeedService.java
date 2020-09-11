package onem.cjq.rss.service;

import org.apache.log4j.Logger;

import onem.cjq.rss.dao.LastestRssDAO;
import onem.cjq.rss.dao.impl.FeedDAOImpl;
import onem.cjq.rss.dao.impl.LastestRssDAOImpl;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.domain.LastestRss;
import onem.cjq.rss.web.Page;

public class FeedService {
	private static Logger logger = Logger.getLogger(FeedService.class); 
	private FeedDAOImpl f=new FeedDAOImpl();
	private LastestRssDAO lrd=new LastestRssDAOImpl();

	public Page<Feed> getPage(int pageNum){
		return f.getPage(pageNum);
	}
	
	public Feed getFeed(int id) {
		Feed feed=null;
		feed=f.getFeed(id);
		
		if(feed==null) {
			feed=f.getFeed((int) f.newFeed());
			logger.debug("新建feed，id"+feed.getId());
		}
		return feed;
	}
	
	public void delFeed(int id) {
		f.delFeed(id);
	}
	
	public LastestRss getRss(int feedId) {
		return lrd.getRss(feedId);
	}
}
