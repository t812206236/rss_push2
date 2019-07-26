package onem.cjq.rss.dao;

import onem.cjq.rss.domain.RssItem;

public interface RssItemDAO {
	/**
	 * 
	 * @param rssItem
	 * @return
	 */
	public abstract long insertRssItem(RssItem rssItem);
	/**
	 * 查询rss条目是否存在，我们将webLink当做是条目是否存在的为一条件，另外也要确保feedId
	 * @param feedId
	 * @param webLink
	 * @return
	 */
	public abstract RssItem getRssItem(int feedId,String webLink);
}
