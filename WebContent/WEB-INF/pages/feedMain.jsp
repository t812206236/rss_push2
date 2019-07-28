<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>

<script type="text/javascript">
	$(function() {

		$("#pageNo")
				.change(
						function() {
							var val = $(this).val();
							val = $.trim(val);

							//1. 校验 val 是否为数字 1, 2, 而不是 a12, b
							var flag = false;
							var reg = /^\d+$/g;
							var pageNum = 0;

							if (reg.test(val)) {
								//2. 校验 val 在一个合法的范围内： 1-totalPageNumber
								pageNum = parseInt(val);
								if (pageNum >= 1
										&& pageNum <= parseInt("${feedpage.totalPageNumber }")) {
									flag = true;
								}
							}

							if (!flag) {
								alert("输入的不是合法的页码.");
								$(this).val("");
								return;
							}

							//3. 页面跳转
							var href = "FeedServlet?method=getFeeds&pageNum="
									+ pageNum; //+ "&" + $(":hidden").serialize();
							window.location.href = href;
						});
	})
</script>

</head>
<body>
	<center>
		<input type="button" onClick="window.location.href='FeedServlet?method=editFeed'" value="Hello World~"/><br><br>
		<table cellpadding="10" border="1">

			<c:forEach items="${feedpage.list }" var="feed">
				<tr>

					<td>
						<a href="rss_${feed.id}.xml">${feed.webRawTitleName}</a>
					</td>
					<td>
						<a href="FeedServlet?method=editFeed&pageNum=${feedpage.pageNo}&feedId=${feed.id}">编辑</a>
					</td>
					<td>
						<a href="FeedServlet?method=delFeed&pageNum=${feedpage.pageNo}&feedId=${feed.id}">删除</a>
					</td>
				</tr>
			</c:forEach>

		</table>
		<br> 共 ${feedpage.totalPageNumber } 页 &nbsp;&nbsp; 当前第
		${feedpage.pageNo } 页 &nbsp;&nbsp;

		<c:if test="${feedpage.hasPrev }">
			<a href="FeedServlet?method=getFeeds&pageNum=1">首页</a>
			&nbsp;&nbsp;
			<a href="FeedServlet?method=getFeeds&pageNum=${feedpage.prevPage }">上一页</a>
		</c:if>

		&nbsp;&nbsp;

		<c:if test="${feedpage.hasNext }">
			<a href="FeedServlet?method=getFeeds&pageNum=${feedpage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a
				href="FeedServlet?method=getFeeds&pageNum=${feedpage.totalPageNumber }">末页</a>
		</c:if>

		&nbsp;&nbsp; 转到 <input type="text" size="1" id="pageNo" /> 页

	</center>

</body>
</html>