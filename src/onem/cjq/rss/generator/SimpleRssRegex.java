package onem.cjq.rss.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import onem.cjq.rss.generator.domain.RssEachItem;
import onem.cjq.rss.generator.domain.RssMatchResult;
import onem.cjq.rss.generator.utils.InterruptibleCharSequence;

public class SimpleRssRegex implements RssRegex {

	private static Logger logger = Logger.getLogger(SimpleRssRegex.class); 
	
	// 输入一个建立rss时使用的正则，为了对用户友好，我们要让regex尽量简单, 在这里我们规定{*}表示不必要得东西，{%}表示需要的东西
	private String convert2NormalRegex(String src) {
		if (src.trim().equals("{*}") || src.trim().equals("{%}")) {
			src = "(.*)";
		} else {
			src = src.replace("{*}", ".*?").replace("{%}", "(.*?)");// ?代表非贪婪
		}
		return src;
	}

	// 请不要直接调用这个方法，因为正则表达式会死循环卡住当前线程，请优先使用SafeRssRegex
	@Override
	public List<RssMatchResult> exportMatchContent(String src, String rssRegex) {
		// TODO Auto-generated method stub
		String reg;
		Pattern pa;
		Matcher ma;
		List<RssMatchResult> list = new ArrayList<RssMatchResult>();
		reg = convert2NormalRegex(rssRegex);
		pa = Pattern.compile(reg);
		src = src.replace("\n", "").replace("\r", "");
		// avoid hanging
		InterruptibleCharSequence ics = new InterruptibleCharSequence(src);
		ma = pa.matcher(ics);
		while (ma.find()) {
			for (int i = 1; i <= ma.groupCount(); i++) {
				RssMatchResult rr = new RssMatchResult();
				rr.setMatchIndex(i);
				rr.setMatchContent(ma.group(i));
				list.add(rr);
			}
		}
		return list;
	}

	// 请不要直接调用这个方法，因为正则表达式会死循环卡住当前线程，请优先使用SafeRssRegex
	@Override
	public String[] exportWebInfo(String src) {
		// TODO Auto-generated method stub
		String title = null;
		String desc = null;
		Pattern p = Pattern.compile("<title>(.*?)</title>");
		Matcher m = p.matcher(src);
		while (m.find()) {
			title = m.group(1);
		}
		p = Pattern.compile("<meta.*?name=\"description\".*?content=\"(.*?)\"");
		InterruptibleCharSequence ics = new InterruptibleCharSequence(src);
		m = p.matcher(ics);
		while (m.find()) {
			desc = m.group(1);
		}
		title = (title == null) ? "" : title;
		desc = (desc == null) ? "" : desc;
		return new String[] { title, desc };
	}

	// 请不要直接调用这个方法，因为正则表达式会死循环卡住当前线程，请优先使用SafeRssRegex
	@Override
	public List<RssEachItem> exportRssMainList(List<RssMatchResult> list, String[] format) {
		// TODO Auto-generated method stub
		List<RssEachItem> li = new ArrayList<RssEachItem>();
		int prevIndex = 0, currIndex = 0;
		RssEachItem rei = new RssEachItem();
		String formatFilled[] = format;
		formatFilled = format.clone();
		list.add(new RssMatchResult()); // for end of the list

		for (RssMatchResult rmr : list) // cycle match result
		{
			currIndex = rmr.getMatchIndex();

			if (currIndex < prevIndex) // meet next rss item
			{
				rei.setTitle(formatFilled[0]);
				rei.setLink(formatFilled[1]);
				rei.setDesr(formatFilled[2]);
				li.add(rei); // add prev item into list
				// reset
				rei = new RssEachItem();
				formatFilled = format.clone();
			}
			prevIndex = currIndex;

			for (int i = 0; i < 3; i++) {
				try {
					if(formatFilled[i]==null || rmr.getMatchContent()==null)
						continue;
					
					InterruptibleCharSequence ics = new InterruptibleCharSequence(formatFilled[i]);
					formatFilled[i] = ics.replace("{%" + rmr.getMatchIndex() + "}", rmr.getMatchContent());
				} catch (Exception e) {
					logger.error(e);
					logger.error("text:"+formatFilled[i]+" index:"+rmr.getMatchIndex()+" content:"+rmr.getMatchContent());
				} finally {
				}
			}

		}
		return li;
	}

}
