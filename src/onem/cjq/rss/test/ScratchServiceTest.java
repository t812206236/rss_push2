package onem.cjq.rss.test;

import org.junit.Test;

import onem.cjq.rss.db.JDBCUtils;
import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.service.ScratchService;
import onem.cjq.rss.web.ConnectionContext;

public class ScratchServiceTest {

	@Test
	public void test() {
		ScratchService ss=new ScratchService();
		Feed feed=new Feed();
		feed.setId(2);
		feed.setWebPath("https://acg.is/");
		feed.setWebEncode("utf-8");
		feed.setWebGlobalReg("<div class=\"container\">{%}</div>");
		feed.setWebItemReg("<a href=\"{%}\">{%}</a>");
		feed.setComposeTitleReg("{%2}");
		feed.setComposeLinkReg("{%1}");
		feed.setComposeContentReg("reserve");
		
		ConnectionContext.getInstance().bind(JDBCUtils.getConnection());
		try {
			ss.generateXMLAndSave(feed);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionContext.getInstance().remove();
		}
	}

}
