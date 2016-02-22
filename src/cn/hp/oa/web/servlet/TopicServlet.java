package cn.hp.oa.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

public class TopicServlet extends BaseServlet {

	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/WEB-INF/jsp/topic/addUI.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		return "";
	}

}
