<%@page import="cn.hp.oa.domain.Privilege"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="hp" uri="/WEB-INF/hp.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<title>配置权限</title>
	<%@include file="/WEB-INF/jsp/public/commons.jspf" %>
	<script language="javascript" src="${pageContext.request.contextPath }/script/jquery_treeview/jquery.treeview.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/style/blue/file.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/script/jquery_treeview/jquery.treeview.css">

	<script type="text/javascript">
		$(document).ready(function() {
			$("[name=privilegeIds]").click(function() {
				/* 当选中或取消一个权限时，也同时选中或取消所有的下级权限 */
				$(this).siblings("ul").find("input").attr("checked", this.checked);
				/* 当选中一个权限时，也同时选中所有的直接上级权限 */
				if (this.checked == true) {
					$(this).parents("li").children("input").attr("checked", true);
				}
				
				/* 当复选框的长度等于选中的复选框的长度，表明全选，设置全选复选框为选中状态，其他情况全选复选框不选中 */
				var all = $(":checkbox[name=privilegeIds]").length;
				var select = $(":checkbox[name=privilegeIds][checked=true]").length;
				if (all == select) { // 
					$("#cbSelectAll").attr("checked", true);
				} else {
					$("#cbSelectAll").attr("checked", false);
				}
			});
			
			/* 使用jquery.treeview.js插件将ul/li列表显示成属性结构 */
			$("#tree").treeview();
		});
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" border="0" height="13" width="13"> 配置权限
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
    <form action="<c:url value='/RoleServlet'/>" method="post">
    	<input type="hidden" name="method" value="setPrivilege">
    	<input type="hidden" name="id" value="${role.id }">
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img src="${pageContext.request.contextPath }/style/blue/images/item_point.gif" border="0" height="7" width="4"> 正在为【${role.name }】配置权限 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="mainForm" cellpadding="0" cellspacing="0">
					<!--表头-->
					<thead>
						<tr id="TableTitle" align="LEFT" valign="MIDDLE">
							<td style="padding-left: 7px;" width="300px">
								<!-- 如果把全选元素的id指定为selectAll，并且有函数selectAll()，就会有错。因为有一种用法：可以直接用id引用元素 -->
								<input id="cbSelectAll" onclick="$('[name=privilegeIds]').attr('checked', this.checked)" type="CHECKBOX">
								<label for="cbSelectAll">全选</label>
							</td>
						</tr>
					</thead>
                   
			   		<!--显示数据列表-->
					<tbody id="TableData">
						<tr class="TableDetail1">
							<!-- 显示权限树 -->
							<td>
								<%--<%
                                	List<Privilege> privilegeList = (List<Privilege>) request.getAttribute("privilegeList");
                                	List<Privilege> privileges = (List<Privilege>) request.getAttribute("privileges");
                                	List<String> privilegeNames = new ArrayList<String>();
                                	for (Privilege privilege : privileges) {
                                		privilegeNames.add(privilege.getName());
                                	}
                                	for (int i = 0; i < privilegeList.size(); i++) {
                                		if (privilegeNames.contains(privilegeList.get(i).getName())) {
                                %>
											<input id="<%=privilegeList.get(i).getId() %>" type="checkbox" name="privilegeIds" value="<%=privilegeList.get(i).getId() %>" checked="checked"> <label for="<%=privilegeList.get(i).getId() %>"> <%=privilegeList.get(i).getName() %></label><br>
                                <%
                                		} else {
                                %>
											<input id="<%=privilegeList.get(i).getId() %>" type="checkbox" name="privilegeIds" value="<%=privilegeList.get(i).getId() %>"> <label for="<%=privilegeList.get(i).getId() %>"> <%=privilegeList.get(i).getName() %></label><br>
                                <%
                                		}
                            		}
                                %>
							--%>
							
								<%
									List<Privilege> privileges = (List<Privilege>) request.getAttribute("privileges");
									List<Long> idList = new ArrayList<Long>();
									int i = 0;
									for (Privilege priv : privileges) {
										idList.add(priv.getId());
										i++;
									}
									request.setAttribute("idList", idList);
								%>
								<ul id="tree">
									<c:forEach items="${topPrivilegeList }" var="topPrivilege">
										<li>
											<input type="checkbox" name="privilegeIds" value="${topPrivilege.id }" id="cb_${topPrivilege.id}" <c:if test="${hp:contains(idList, topPrivilege.id) }">checked</c:if> >
											<label for="cb_${topPrivilege.id}"><span class="folder">${topPrivilege.name }</span></label>
											<ul>
												<c:forEach items="${topPrivilege.children }" var="child">
													<li>
														<input type="checkbox" name="privilegeIds" value="${child.id }" id="cb_${child.id}"	<c:if test="${hp:contains(idList, child.id) }">checked</c:if> >
														<label for="cb_${child.id}"><span class="folder">${child.name }</span></label>
														<ul>
															<c:forEach items="${child.children }" var="grandson">
																<li>
																	<input type="checkbox" name="privilegeIds" value="${grandson.id }" id="cb_${grandson.id}" <c:if test="${hp:contains(idList, grandson.id) }">checked</c:if> >
																	<label for="cb_${grandson.id}"><span class="folder">${grandson.name }</span></label>
																</li>
															</c:forEach>
														</ul>
													</li>
												</c:forEach>
											</ul>
										</li>
									</c:forEach>
								</ul>
							
							</td>
						</tr>
					</tbody>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input src="${pageContext.request.contextPath }/style/images/save.png" type="image">
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath }/style/images/goBack.png"></a>
        </div>
    </form>
</div>

<div class="Description">
	说明：<br>
	1，选中一个权限时：<br>
	&nbsp;&nbsp;&nbsp;&nbsp; a，应该选中 他的所有直系上级。<br>
	&nbsp;&nbsp;&nbsp;&nbsp; b，应该选中他的所有直系下级。<br>
	2，取消选择一个权限时：<br>
	&nbsp;&nbsp;&nbsp;&nbsp; a，应该取消选择 他的所有直系下级。<br>
	&nbsp;&nbsp;&nbsp;&nbsp; b，如果同级的权限都是未选择状态，就应该取消选中他的直接上级，并递归向上做这个操作。<br>

	3，全选/取消全选。<br>
	4，默认选中当前岗位已有的权限。<br>
</div>



</body></html>
