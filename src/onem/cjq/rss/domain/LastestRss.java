package onem.cjq.rss.domain;
/**
 * 请保证成员变量顺序和数据库中的顺序是一致的，这样才能方便查询
 * @author cjq
 *
 */
public class LastestRss {
	private int feedId;
	private String lastBuildDate;
	private String fullContent;
	
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public String getFullContent() {
		return fullContent;
	}
	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}
	@Override
	public String toString() {
		return "LastestRss [feedId=" + feedId + ", lastBuildDate=" + lastBuildDate + ", fullContent=" + fullContent
				+ "]";
	}
	

}
