package onem.cjq.rss.generator;

public enum RssCallableType {
	WEB_CONTENT(0), WEB_INFO(1), RSS_FILL(2);

	@SuppressWarnings("unused")
	private final int value;

	private RssCallableType(int value) {
		this.value = value;
	}
}
