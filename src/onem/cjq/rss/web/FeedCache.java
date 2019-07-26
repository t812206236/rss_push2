package onem.cjq.rss.web;

import java.util.List;

import onem.cjq.rss.generator.domain.RssMatchResult;

public class FeedCache {
	private String webSrc;
	private List<RssMatchResult> extractList;
	public String getWebSrc() {
		return webSrc;
	}
	public void setWebSrc(String webSrc) {
		this.webSrc = webSrc;
	}
	public List<RssMatchResult> getExtractList() {
		return extractList;
	}
	public void setExtractList(List<RssMatchResult> list2) {
		this.extractList = list2;
	}
	@Override
	public String toString() {
		return "FeedCache [webSrc=" + webSrc + ", extractList=" + extractList + "]";
	}
	
}
