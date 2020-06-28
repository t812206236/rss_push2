package onem.cjq.rss.service;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;

import onem.cjq.rss.dao.LastestRssDAO;
import onem.cjq.rss.dao.RssItemDAO;
import onem.cjq.rss.dao.impl.LastestRssDAOImpl;
import onem.cjq.rss.dao.impl.RssItemDAOImpl;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.domain.LastestRss;
import onem.cjq.rss.domain.RssItem;
import onem.cjq.rss.generator.SafeRssRegex;
import onem.cjq.rss.generator.SimpleRssGenerateXML;
import onem.cjq.rss.generator.WebGetter;
import onem.cjq.rss.generator.domain.RssEachItem;
import onem.cjq.rss.generator.domain.RssMatchResult;
import onem.cjq.rss.utils.Rfc822Time;
import onem.cjq.rss.web.FeedCache;

public class ScratchService {
	
	/**
	 * 加载网页的信息，并将相关数据填充到feed中
	 * @param feed
	 * @return 返回该网页的源代码
	 * @throws Exception
	 */
	public String loadWeb(Feed feed,FeedCache feedCache) throws Exception {
		String src=new WebGetter().get(feed.getWebPath(), feed.getWebEncode());
		//?m开启多行模式，^是行开始，\\s*代表任意个空格，$代表行结束，System.lineSeparator()则是为了跨平台的换行符。
		src=src.replaceAll("(?m)^\\s*$"+System.lineSeparator(), "");
		//标题 网页描述
		String[] webInfo=new SafeRssRegex().exportWebInfo(src);
		feed.setWebRawTitleName(webInfo[0]);
		feed.setWebRawDesc(webInfo[1]);
		feedCache.setWebSrc(src);
		return src;
	}
	
	/**
	 * 根据表达式抽取网页内容
	 * @param feed
	 * @param feedCache
	 * @return 返回抽取的列表
	 * @throws Exception
	 */
	public String extract(Feed feed,FeedCache feedCache) throws Exception{
		String result="";
		List<RssMatchResult> list1=new SafeRssRegex().exportMatchContent(feedCache.getWebSrc(), feed.getWebGlobalReg());
		for(RssMatchResult rmr:list1)
			result+=rmr.getMatchContent();
		List<RssMatchResult> list2=new SafeRssRegex().exportMatchContent(result, feed.getWebItemReg());
		result="";
		for(RssMatchResult rmr:list2)
			result+="{%"+rmr.getMatchIndex()+"}"+rmr.getMatchContent()+"\n";
		feedCache.setExtractList(list2);
		return result;
	}
	
	/**
	 * 生成一个简单的rss xml给前端预览
	 * @param feed
	 * @param feedCache
	 * @return 返回一个xml
	 * @throws Exception
	 */
	public String generateTempXML(Feed feed,FeedCache feedCache) throws Exception{
		String result="";
		result=new SimpleRssGenerateXML().generate(feedCache.getExtractList(), 
				new String[] {feed.getComposeTitleReg(),feed.getComposeLinkReg(),feed.getComposeContentReg()}, 
				new String[] {feed.getWebRawTitleName(),feed.getWebRawDesc()});
		return result;
	}
	
	/**
	 * 生成正式的rss主列表内容
	 * @param feed 需要传入生成列表里所有需要的所有东西
	 * @throws Exception
	 */
	public List<RssItem> generateItemAndSave(Feed feed,FeedCache feedCache) throws Exception{
		//提取网页内容
		
		feedCache.setWebSrc(loadWeb(feed, feedCache));
		extract(feed,feedCache);
		
		//生成所需要的列表
		SafeRssRegex srr=new SafeRssRegex();
		List<RssEachItem> list1=srr.exportRssMainList(feedCache.getExtractList(), 
				new String[] {feed.getComposeTitleReg(),feed.getComposeLinkReg(),feed.getComposeContentReg()});
		
		//对每项内容进行处理
		RssItemDAO rid=new RssItemDAOImpl();
		List<RssItem> list2=new ArrayList<>();
		for (RssEachItem rssEachItem : list1) {
			//System.out.println(rssEachItem);
			RssItem ri=null;
			//item不在数据库中
			if((ri=rid.getRssItem(feed.getId(), rssEachItem.getLink()))==null) {
				ri=new RssItem();
				ri.setFeedId(feed.getId());
				ri.setGuid(UUID.randomUUID().toString().replace("-", ""));
				ri.setScratchDate(Rfc822Time.dateToStr());
				ri.setTitle(rssEachItem.getTitle());
				ri.setLink(rssEachItem.getLink());
				ri.setDescription(rssEachItem.getDesr());
				rid.insertRssItem(ri);
			}else {
				//do nothing
			}
			list2.add(ri);
		}
		
		//按时间排序
		Collections.sort(list2, new Comparator<RssItem>() {
			@Override
			public int compare(RssItem o1, RssItem o2) {
				// TODO Auto-generated method stub
				try {
					return Rfc822Time.dateCompare(o1.getScratchDate(), o2.getScratchDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//这里的异常应该是不用调用者去捕捉的，因为上面直接生成，中间没有更改
					System.out.println("time convert error"+o1.getScratchDate()+" "+o2.getScratchDate());
					return -1;
				}
			}
		});
		
		//从最新的开始显示
		Collections.reverse(list2);
		return list2;
	}
	
	/**
	 * 包装rss主列表，生成xml文件
	 * @param feed
	 * @throws Exception
	 */
	public void generateXMLAndSave(Feed feed) throws Exception {
		String result="";
		FeedCache feedCache=new FeedCache();
		LastestRssDAO lrd=new LastestRssDAOImpl();
		LastestRss lr=new LastestRss();
		//rss 头
		result="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
		result+="<rss version=\"2.0\">\r\n";
		result+="<channel>\r\n";
		result+="<title><![CDATA["+feed.getWebRawTitleName()+"]]></title>\r\n";
		result+="<link>"+StringEscapeUtils.escapeXml10(feed.getWebPath())+"</link>\r\n";
		result+="<description><![CDATA["+feed.getWebRawDesc()+"]]></description>\r\n";
		result+="<lastBuildDate>"+Rfc822Time.dateToStr()+"</lastBuildDate>\r\n";
		result+="<generator>No Generator</generator>\r\n";
		result+="<ttl>30</ttl>\r\n";
		//rss 中间列表
		List<RssItem> list=generateItemAndSave(feed,feedCache);
		for (RssItem rssItem : list) {
			result+="<item>\r\n";
			result+="<guid isPermaLink=\"false\">"+rssItem.getGuid()+"</guid>\r\n";
			result+="<pubDate>"+rssItem.getScratchDate()+"</pubDate>\r\n";
			result+="<title><![CDATA["+rssItem.getTitle()+"]]></title>\r\n";
			result+="<link>"+StringEscapeUtils.escapeXml10(rssItem.getLink())+"</link>\r\n";
			result+="<description><![CDATA["+rssItem.getDescription()+"]]></description>\r\n";
			result+="</item>\r\n";
		}
		//rss 尾
		result+="</channel>\r\n";
		result+="</rss>\r\n";
		lr.setFeedId(feed.getId());
		lr.setLastBuildDate(Rfc822Time.dateToStr());
		lr.setFullContent(result);
		if(lrd.getRss(feed.getId())==null)
		{
			lrd.insertRss(lr);
		}else {
			lrd.updateRss(lr);
		}
		
	}
	
}
