package onem.cjq.rss.generator;

import java.util.List;

import onem.cjq.rss.generator.domain.RssEachItem;
import onem.cjq.rss.generator.domain.RssMatchResult;

public class SimpleRssGenerateXML {
	public SimpleRssGenerateXML() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 生成简单的rss xml内容，仅用做预览，并不是实际效果
	 * @param rmr 接收一个已经剥离内容的列表
	 * @param format 标题 连接 内容的表达式
	 * @param webInfo 网页的内容 标题，描述
	 * @return
	 */
	public String generate(List<RssMatchResult> rmr, String format[], String webInfo[]) {
		String xmlResult = "";
		List<RssEachItem> mainList = new SafeRssRegex().exportRssMainList(rmr, format);

		xmlResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
		xmlResult += "<rss version=\"2.0\">\r\n";
		xmlResult += "<channel>\r\n";
		xmlResult += "<title><![CDATA[" + webInfo[0] + "]]></title>\r\n";
		xmlResult += "<description><![CDATA[" + webInfo[1] + "]]></description>\r\n";
		for (RssEachItem rei : mainList) {
			xmlResult += "<item>\r\n";
			xmlResult += "<title><![CDATA[" + rei.getTitle() + "]]></title>\r\n";
			xmlResult += "<link>" + rei.getLink() + "</link>\r\n";
			xmlResult += "<description><![CDATA[" + rei.getDesr() + "]]></description>\r\n";
			xmlResult += "</item>\r\n";
		}
		xmlResult += "</channel>\r\n";
		xmlResult += "</rss>\r\n";
		return xmlResult;
	}
}
