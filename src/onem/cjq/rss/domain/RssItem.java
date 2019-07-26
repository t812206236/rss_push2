package onem.cjq.rss.domain;
/**
 * 请保证成员变量顺序和数据库中的顺序是一致的，这样才能方便查询
 * @author cjq
 *
 */
public class RssItem {
	private int feedId;
	private String guid;
	private String scratchDate;
	private String title;
	private String link;
	private String description;
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getScratchDate() {
		return scratchDate;
	}
	public void setScratchDate(String scratchDate) {
		this.scratchDate = scratchDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "RssItem [feedId=" + feedId + ", guid=" + guid + ", scratchDate=" + scratchDate + ", title=" + title
				+ ", link=" + link + ", description=" + description + "]";
	}

	
}
