package onem.cjq.rss.dao;

import onem.cjq.rss.domain.LastestRss;

public interface LastestRssDAO {
	public abstract LastestRss getRss(int id);
	public void updateRss(LastestRss lastestRss);
	public long insertRss(LastestRss lastestRss);
}
