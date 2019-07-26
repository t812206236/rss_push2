package onem.cjq.rss.dao.impl;

import onem.cjq.rss.dao.LastestRssDAO;
import onem.cjq.rss.domain.LastestRss;

public class LastestRssDAOImpl extends BaseDAO<LastestRss> implements LastestRssDAO{

	@Override
	public LastestRss getRss(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM lastestRss WHERE feedId = ?";
		return query(sql,id);
	}

	@Override
	public void updateRss(LastestRss lastestRss) {
		// TODO Auto-generated method stub
		String sql = "UPDATE lastestRss SET lastBuildDate=? ,fullContent=? WHERE feedId=?";
		update(sql, lastestRss.getLastBuildDate(),lastestRss.getFullContent(),lastestRss.getFeedId());
	}

	@Override
	public long insertRss(LastestRss lastestRss) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO lastestRss (feedId,lastBuildDate,fullContent) VALUES (?,?,?)";
		return insert(sql,lastestRss.getFeedId(),lastestRss.getLastBuildDate(),lastestRss.getFullContent());
	}

}
