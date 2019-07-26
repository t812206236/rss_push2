package onem.cjq.rss.dao.impl;

import onem.cjq.rss.dao.RssItemDAO;
import onem.cjq.rss.domain.RssItem;

public class RssItemDAOImpl extends BaseDAO<RssItem> implements RssItemDAO{

	@Override
	public long insertRssItem(RssItem rssItem) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO rssItems (feedId,guid,scratchDate,title,link,description)VALUES(?,?,?,?,?,?)";
		return insert(sql,rssItem.getFeedId(),rssItem.getGuid(),rssItem.getScratchDate(),rssItem.getTitle(),rssItem.getLink(),rssItem.getDescription());
	}

	@Override
	public RssItem getRssItem(int feedId, String webLink) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM rssItems WHERE feedId = ? AND link = ?";
		return query(sql,feedId,webLink);
	}

}
