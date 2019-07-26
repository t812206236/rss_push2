package onem.cjq.rss.generator.domain;

public class RssEachItem {
	private String title;
	private String link;
	private String desr;

	public RssEachItem(String title, String link, String desr) {
		super();
		this.title = title;
		this.link = link;
		this.desr = desr;
	}

	public RssEachItem() {
		// TODO Auto-generated constructor stub
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

	public String getDesr() {
		return desr;
	}

	public void setDesr(String desr) {
		this.desr = desr;
	}

	@Override
	public String toString() {
		return "RssEachItem title=" + title + ", link=" + link + ", desr=" + desr;
	}

}
