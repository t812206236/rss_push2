package onem.cjq.rss.generator.domain;

public class RssMatchResult {
	private int matchIndex;
	private String matchContent;

	public int getMatchIndex() {
		return matchIndex;
	}

	public void setMatchIndex(int matchIndex) {
		this.matchIndex = matchIndex;
	}

	public String getMatchContent() {
		return matchContent;
	}

	public void setMatchContent(String matchContent) {
		this.matchContent = matchContent;
	}

	public RssMatchResult(int matchIndex, String matchContent) {
		super();
		this.matchIndex = matchIndex;
		this.matchContent = matchContent;
	}

	public RssMatchResult() {
		super();
	}

	@Override
	public String toString() {
		return "RssMatchResult matchIndex=" + matchIndex + ", matchContent=" + matchContent;
	}

}
