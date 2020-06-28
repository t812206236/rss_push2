<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rss编辑</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/feedItem.css">

<script type="text/javascript">
	$(function() {
		var url = "FeedServlet";

		$("#reloadBtn").click(function() {
			var args = {
				"method" : "processFeed",
				"type" : "reload",
				"url" : $("#url").val(),
				"encode" : $("#encode").val()
			};
			$(this).after("<strong id=\"loading\" >Loading......</strong>");
			$.post(url, args, function(data) {
				$("#loading").remove();
				$("#feed_title").val(data.title);
				$("#feed_link").val($("#url").val());
				$("#feed_desr").val(data.desc);
				$("#raw_data").html("<xmp>" + data.rawSrc + "</xmp>");
			}, "json");
		});

		$("#extractBtn").click(function() {
			var args = {
				"method" : "processFeed",
				"type" : "extract",
				"globalReg" : $("#global_regex").val(),
				"itemReg" : $("#item_regex").val()
			};

			$(this).after("<strong id=\"loading\" >Loading......</strong>");
			$.post(url, args, function(data) {
				$("#loading").remove();
				$("#extract_data").html("<xmp>" + data.result + "</xmp>");
			}, "json");

		});

		$("#genBtn").click(function() {
			var args = {
				"method" : "processFeed",
				"type" : "generate",
				"titleReg" : $("#item_title").val(),
				"linkReg" : $("#item_link").val(),
				"contentReg" : $("#item_desr").val()
			};
			$(this).after("<strong id=\"loading\" >Loading......</strong>");
			$.post(url, args, function(data) {
				$("#loading").remove();
				$("#generate_data").html("<xmp>" + data.result+ "</xmp>");
			}, "json");
		});

	});
</script>


</head>
<body>

	<div id="wrap">
		<p>地址</p>
		<input id="url" class="text" value="${sessionScope.feed.webPath}">
		<p>编码</p>
		<input id="encode" class="text" value="${sessionScope.feed.webEncode}">
		<input id="reloadBtn" value="Reload" type="button">
		<div id="raw_data" class="textarea">Click [Reload] to see
			contents</div>

		<p>全局匹配</p>
		<textarea id="global_regex" class="text">${sessionScope.feed.webGlobalReg}</textarea>
		<p>单项匹配</p>
		<textarea id="item_regex" class="text">${sessionScope.feed.webItemReg}</textarea>
		<input id="extractBtn" value="Extract" type="button">
		<div id="extract_data" class="textarea">Click [Extract] to see
			contents</div>

		<p>Feed标题</p>
		<input id="feed_title" class="text"
			value="${sessionScope.feed.webRawTitleName}" disabled>
		<p>Feed连接</p>
		<input id="feed_link" class="text"
			value="${sessionScope.feed.webPath}" disabled>
		<p>Feed介绍</p>
		<textarea id="feed_desr" class="text"  disabled>${sessionScope.feed.webRawDesc}</textarea>

		<p>定义每项标题</p>
		<input id="item_title" class="text"
			value="${sessionScope.feed.composeTitleReg}">
		<p>定义每项连接</p>
		<input id="item_link" class="text"
			value="${sessionScope.feed.composeLinkReg}">
		<p>定义每项介绍</p>
		<textarea id="item_desr" class="text">${sessionScope.feed.composeContentReg}</textarea>
		<input id="genBtn" value="Generate" type="button">
		<div id="generate_data" class="textarea">Click [Generate] to see
			contents</div>
	</div>

</body>
</html>