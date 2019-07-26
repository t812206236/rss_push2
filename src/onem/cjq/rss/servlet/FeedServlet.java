package onem.cjq.rss.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import onem.cjq.rss.domain.Feed;
import onem.cjq.rss.domain.LastestRss;
import onem.cjq.rss.service.FeedService;
import onem.cjq.rss.service.ScratchService;
import onem.cjq.rss.thread.RssGenManage;
import onem.cjq.rss.web.FeedCache;
import onem.cjq.rss.web.Page;

public class FeedServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FeedService fs = new FeedService();
	private ScratchService ss = new ScratchService();
	private RssGenManage rgm = RssGenManage.getInstance();

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("servlet init");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodName = req.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			// throw exception for filter rollback tranaction
			throw new RuntimeException(e);
		}
	}

	protected void getRss(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String feedIdStr = req.getParameter("feedId");
		LastestRss lastestRss=null;
		int feedId = 1;
		try {
			feedId = Integer.parseInt(feedIdStr);
		} catch (NumberFormatException e) {
			System.out.println("getRss---非法id出现"+feedIdStr);
		}
		
		lastestRss=fs.getRss(feedId);
		if(lastestRss==null) {
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
			return;
		}
		resp.getWriter().print(lastestRss.getFullContent());
	}
	
	protected void getFeeds(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pageNoStr = req.getParameter("pageNum");
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
			System.out.println("getFeeds---非法页码出现"+pageNoStr);
		}
		// 此处的非法页码值会在获取时矫正
		Page<Feed> page = fs.getPage(pageNum);
		req.setAttribute("feedpage", page);
		req.getRequestDispatcher("/WEB-INF/pages/feedMain.jsp").forward(req, resp);
	}

	protected void editFeed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String feedIdStr = req.getParameter("feedId");
		int feedId = 0;
		try {
			feedId = Integer.parseInt(feedIdStr);
		} catch (NumberFormatException e) {
			System.out.println("editFeed---feed码非法，可能是新建feed请求"+feedIdStr);
		}
		// 此处的码值非法可能会新建一个feed
		Feed feed = fs.getFeed(feedId);
		req.getSession().setAttribute("feed", feed);
		// 清空创建feed时用到的cache
		req.getSession().setAttribute("feedCache", new FeedCache());
		req.getRequestDispatcher("/WEB-INF/pages/feedItem.jsp").forward(req, resp);
	}

	protected void delFeed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String feedIdStr = req.getParameter("feedId");
		int feedId = 0;
		try {
			feedId = Integer.parseInt(feedIdStr);
		} catch (NumberFormatException e) {
			System.out.println("delFeed---feed码参数非法"+feedIdStr);
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
			return;
		}
		fs.delFeed(feedId);
		rgm.delTask(feedId);
		//保证获取分页是在任务被删除后执行，否则得到的页码会有参数问题
		getFeeds(req, resp);
	}

	protected void processFeed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String typeStr = req.getParameter("type");
		String jsonStr = null;
		Feed feed = null;
		FeedCache feedCache = null;
		Map<String, Object> map = null;

		// todo 需要检查feed不存在的情况，此处需要抛出异常并且跳转页面
		if (((feed = (Feed) req.getSession().getAttribute("feed")) == null)
				|| ((feedCache = (FeedCache) req.getSession().getAttribute("feedCache")) == null)) {
			System.out.println("在feed处理时丢是feed相关信息，这是一个灾难性错误，请检查!");
			resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
			return;
		}

		switch (typeStr) {
		case "reload":
			String urlStr = req.getParameter("url");
			String encodeStr = req.getParameter("encode");
			String src = "";
			map = new HashMap<>();

			feed.setWebPath(urlStr);
			feed.setWebEncode(encodeStr);
			try {
				src = ss.loadWeb(feed,feedCache);
				map.put("title", feed.getWebRawTitleName());
				map.put("desc", feed.getWebRawDesc());
				map.put("rawSrc", src);
				// System.out.println(fri.getTitle()+" "+ fri.getDesc());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("网络请求错误 " + urlStr);
				e.printStackTrace();
				map.put("rawSrc", "无法获取该网页的内容，可能被墙，也有可能是获取方式存在问题，待优化！");
			}
			break;
		case "extract":
			String globalRegStr = req.getParameter("globalReg");
			String itemRegStr = req.getParameter("itemReg");

			feed.setWebGlobalReg(globalRegStr);
			feed.setWebItemReg(itemRegStr);
			map = new HashMap<>();
			// System.out.println(globalRegStr+" "+itemRegStr );
			try {
				String extractResult = ss.extract(feed,feedCache);
				map.put("result", extractResult);
				// System.out.println(extractResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("正则表达式提取异常 global" + globalRegStr + " item" + itemRegStr);
				map.put("result", "表达式异常导致处理超时，请检查你的表达式是否正确！");
			}

			break;
		case "generate":
			String titleRegStr = req.getParameter("titleReg");
			String linkRegStr = req.getParameter("linkReg");
			String contentRegStr = req.getParameter("contentReg");
			feed.setComposeTitleReg(titleRegStr);
			feed.setComposeLinkReg(linkRegStr);
			feed.setComposeContentReg(contentRegStr);
			map = new HashMap<>();
			try {
				String result=ss.generateTempXML(feed, feedCache);
				map.put("result", result);
				rgm.addTask(feed);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("生成xml预览失败");
				map.put("result", "生成失败，请确定之前的操作Reload和Extract都已经正常执行并且已经得到你想要的结果！");
			}
			
			break;
		}
		Gson gson = new Gson();
		jsonStr = gson.toJson(map);
		req.getSession().setAttribute("feed", feed);
		req.getSession().setAttribute("feedCache", feedCache);
		resp.setContentType("text/javascript;charset=utf-8");
		resp.getWriter().print(jsonStr);

	}
}
