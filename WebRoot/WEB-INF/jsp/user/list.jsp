<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
    <title>用户列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script language="javascript" src="${pageContext.request.contextPath }/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath }/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath }/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/style/blue/pageCommon.css">
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head"> 
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" border="0" height="13" width="13"> 用户管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table class="TableStyle" cellpadding="0" cellspacing="0">
       
        <!-- 表头-->
        <thead>
            <tr id="TableTitle" align="center" valign="middle">
                <td width="100">登录名</td>
                <td width="100">姓名</td>
                <td width="100">所属部门</td>
                <td width="200">岗位</td>
                <td>备注</td>
                <td>相关操作</td>
            </tr>
        </thead>
        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
            
        <tr class="TableDetail1 demodata_record">
                <td>zs&nbsp;</td>
                <td>张三&nbsp;</td>
                <td>研发部&nbsp;</td>
                <td>程序员&nbsp;</td>
                <td>&nbsp;</td>
                <td><a onclick="return delConfirm()" href="list.html">删除</a>
                    <a href="saveUI.html">修改</a>
					<a href="#" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码</a>
                </td>
            </tr><tr class="TableDetail1 demodata_record">
                <td>ls&nbsp;</td>
                <td>李四&nbsp;</td>
                <td>研发部&nbsp;</td>
                <td>程序员&nbsp;</td>
                <td>&nbsp;</td>
                <td><a onclick="return delConfirm()" href="list.html">删除</a>
                    <a href="saveUI.html">修改</a>
					<a href="#" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码</a>
                </td>
            </tr><tr class="TableDetail1 demodata_record">
                <td>ww&nbsp;</td>
                <td>王五&nbsp;</td>
                <td>测试部&nbsp;</td>
                <td>测试员&nbsp;</td>
                <td>&nbsp;</td>
                <td><a onclick="return delConfirm()" href="list.html">删除</a>
                    <a href="saveUI.html">修改</a>
					<a href="#" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码</a>
                </td>
            </tr></tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="saveUI.html"><img src="${pageContext.request.contextPath }/style/images/createNew.png"></a>
        </div>
    </div>
</div>



</body></html>