package onem.cjq.rss.dao;

import java.util.List;

import onem.cjq.rss.domain.Feed;

public interface FeedDAO extends CommonPageDAO<Feed>{
	/**
	 * 
	 * @param id
	 * @return
	 */
	public abstract Feed getFeed(int id);
	/**
	 * 
	 * @return
	 */
	public abstract long newFeed();
	/**
	 * 
	 * @param feed
	 */
	public abstract void updateFeed(Feed feed);
	/**
	 * 
	 * @param id
	 */
	public abstract void delFeed(int id);
	/**
	 * 
	 * @return
	 */
	public abstract List<Feed> getAll();
}
