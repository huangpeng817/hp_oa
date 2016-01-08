package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.domain.User;
import cn.hp.oa.service.UserService;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService userService = new UserService();
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> userList = userService.findAll();
		req.setAttribute("userList", userList);
		return "/WEB-INF/jsp/user/list.jsp";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "";
	}
	
	public String addUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "";
	}
	
	public String editUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "";
	}
	
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		return "";
	}

}
