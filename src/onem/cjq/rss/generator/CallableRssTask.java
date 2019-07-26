package onem.cjq.rss.generator;

import java.util.List;
import java.util.concurrent.Callable;

import onem.cjq.rss.generator.domain.RssMatchResult;

public class CallableRssTask implements Callable<Object> {
	private RssCallableType type;
	private String src;
	private String rssRegex;
	private List<RssMatchResult> list;
	private String[] format;

	public CallableRssTask(String src) {
		this.type = RssCallableType.WEB_INFO;
		this.src = src;
	}

	public CallableRssTask(String src, String rssRegex) {
		this.type = RssCallableType.WEB_CONTENT;
		this.src = src;
		this.rssRegex = rssRegex;
	}

	public CallableRssTask(List<RssMatchResult> list, String[] format) {
		this.type = RssCallableType.RSS_FILL;
		this.list = list;
		this.format = format;
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		switch (type) {
		case WEB_CONTENT:
			return new SimpleRssRegex().exportMatchContent(src, rssRegex);
		case WEB_INFO:
			return new SimpleRssRegex().exportWebInfo(src);
		case RSS_FILL:
			return new SimpleRssRegex().exportRssMainList(list, format);
		}
		return null;
	}

}
