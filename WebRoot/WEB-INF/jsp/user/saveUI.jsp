<%@page import="cn.hp.oa.domain.Role"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
	<title>用户信息</title>
	<%@include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" border="0" height="13" width="13"> 用户信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
    <form action="<c:url value='/UserServlet?'/>" method="post">
    	<input type="hidden" name="method" value="${user.id eq null ? 'add' : 'edit' }">
    	<input type="hidden" name="id" value="${user.id }">
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img src="${pageContext.request.contextPath }/style/blue/images/item_point.gif" border="0" height="7" width="4"> 用户信息 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="mainForm" cellpadding="0" cellspacing="0">
                    <tbody><tr><td width="100">所属部门</td>
                        <td><select name="departmentId" class="SelectStyle">
                                <option value="0" selected="selected">请选择部门</option>
                                <c:forEach items="${departmentList }" var="department">
	                                <option value="${department.id }" <c:if test="${user.department.id eq department.id }">selected="selected"</c:if> >${department.name }</option>
                                </c:forEach>
                            </select> 
                        </td>
                    </tr>
                    <tr><td>登录名</td>
                        <td><input name="loginName" value="${user.loginName }" class="InputStyle" type="text"> *
							（登录名要唯一）
						</td>
                    </tr>
                    <tr><td>姓名</td>
                        <td><input name="name" value="${user.name }" class="InputStyle" type="text"> *</td>
                    </tr>
					<tr><td>性别</td>
                        <td><input name="gender" value="男" id="male" type="RADIO" <c:if test="${user.gender eq '男' }">checked="checked"</c:if> ><label for="male">男</label>
							<input name="gender" value="女" id="female" type="RADIO" <c:if test="${user.gender eq '女' }">checked="checked"</c:if> ><label for="female">女</label>
						</td>
                    </tr>
					<tr><td>联系电话</td>
                        <td><input name="phoneNumber" value="${user.phoneNumber }" class="InputStyle" type="text"></td>
                    </tr>
                    <tr><td>E-mail</td>
                        <td><input name="email" value="${user.email }" class="InputStyle" type="text"></td>
                    </tr>
                    <tr><td>备注</td>
                        <td><textarea name="description" class="TextareaStyle">${user.description }</textarea></td>
                    </tr>
                </tbody></table>
            </div>
        </div>
        
		<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img src="${pageContext.request.contextPath }/style/blue/images/item_point.gif" border="0" height="7" width="4"> 岗位设置 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table class="mainForm" cellpadding="0" cellspacing="0">
                    <tbody><tr>
						<td width="100">岗位</td>
                        <td><select name="roleIdList" multiple="true" size="10" class="SelectStyle">
                                <%
                                	List<Role> roleList = (List<Role>) request.getAttribute("roleList");
                                	List<Role> roles = (List<Role>) request.getAttribute("roles");
                                	List<String> roleNames = new ArrayList<String>();
                                	if (roles != null) {
	                                	for (Role role : roles) {
	                                		roleNames.add(role.getName());
	                                	}
                                	}
                                	for (int i = 0; i < roleList.size(); i++) {
                                		if (roleNames != null && roleNames.contains(roleList.get(i).getName())) {
                                %>
                                			<option value="<%=roleList.get(i).getId() %>" selected="selected"><%=roleList.get(i).getName() %></option>
                                <%
                                		} else {
                                %>
                                			<option value="<%=roleList.get(i).getId() %>"><%=roleList.get(i).getName() %></option>	
                                <%
                                		}
                            		}
                                %>
                            </select>
                            按住Ctrl键可以多选或取消选择
                        </td>
                    </tr>
                </tbody></table>
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
	1，用户的登录名要唯一，在填写时要同时检测是否可用。<br>
	2，新建用户后，密码被初始化为"1234"。<br>
	3，密码在数据库中存储的是MD5摘要（不是存储明文密码）。<br>
	4，用户登录系统后可以使用“个人设置→修改密码”功能修改密码。<br>
	5，新建用户后，会自动指定默认的头像。用户可以使用“个人设置→个人信息”功能修改自已的头像<br>
	6，修改用户信息时，登录名不可修改。
</div>



</body></html>