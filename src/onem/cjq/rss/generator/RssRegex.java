package onem.cjq.rss.generator;

import java.util.List;
import onem.cjq.rss.generator.domain.RssEachItem;
import onem.cjq.rss.generator.domain.RssMatchResult;

public interface RssRegex {
	public List<RssMatchResult> exportMatchContent(String src, String rssRegex);

	public String[] exportWebInfo(String src);

	public List<RssEachItem> exportRssMainList(List<RssMatchResult> list, String format[]);
}
