<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
	<title>论坛</title>
	<%@include file="/WEB-INF/jsp/public/commons.jspf" %>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/style/blue/forum.css">
</head>
<body>
<div id="Title_bar">
	<div id="Title_bar_Head">
		<div id="Title_Head"></div>
		<div id="Title">
			<!--页面标题-->
			<img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" height="13" border="0" width="13"> 论坛 </div>
		<div id="Title_End"></div>
	</div>
</div>
<div id="MainArea">
	<center>
		<div class="ForumPageTableBorder" style="margin-top: 25px;">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			
				<!--表头-->
				<tbody><tr align="center" valign="middle">
					<td colspan="3" class="ForumPageTableTitleLeft">版块</td>
					<td class="ForumPageTableTitle" width="80">主题数</td>
					<td class="ForumPageTableTitle" width="80">文章数</td>
					<td class="ForumPageTableTitle" width="270">最后发表的主题</td>
				</tr>
				<tr class="ForumPageTableTitleLine" height="1"><td colspan="9"></td></tr>
				<tr height="3"><td colspan="9"></td></tr>
			
				<!--版面列表-->
				</tbody>
				<tbody class="dataContainer" datakey="forumList">
				
				<c:forEach items="${forumList }" var="forum">
				<tr class="demodata_record" align="center" height="78">
					<td width="3"></td>
					<td class="ForumPageTableDataLine" width="75">
						<img src="${pageContext.request.contextPath }/style/images/forumpage3.gif">
					</td>
					<td class="ForumPageTableDataLine">
						<ul class="ForumPageTopicUl">
							<li class="ForumPageTopic"><a class="ForumPageTopic" href="${pageContext.request.contextPath }/ForumServlet?method=show&id=${forum.id }">${forum.name }</a></li>
							<li class="ForumPageTopicMemo">${forum.description }</li>
						</ul>
					</td>
					<td class="ForumPageTableDataLine"><b>${forum.topicCount }</b></td>
					<td class="ForumPageTableDataLine"><b>${forum.articleCount }</b></td>
					<td class="ForumPageTableDataLine">
						<ul class="ForumPageTopicUl">
							<li><font color="#444444">┌ 主题：</font> 
								<a class="ForumTitle" href="${pageContext.request.contextPath }/BBS_Topic/topicShow.html">OA是什么？ </a>
							</li>
							<li><font color="#444444">├ 作者：</font> 管理员</li>
							<li><font color="#444444">└ 时间：</font> 2010-06-12 17:47</li>
						</ul>
					</td>
					<td width="3"></td>
				</tr>
				</c:forEach>
				</tbody>
				<!-- 版面列表结束 -->
				
				<tbody><tr height="3"><td colspan="9"></td></tr>
			</tbody></table>
		</div>
	</center>
</div>


</body></html>