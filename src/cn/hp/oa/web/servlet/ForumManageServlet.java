package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.dao.impl.ForumManageDaoImpl;
import cn.hp.oa.domain.Forum;
import cn.hp.oa.service.ForumManageService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class ForumManageServlet extends BaseServlet {

	private ForumManageService forumManageService = new ForumManageService();
	
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Forum> forumList = forumManageService.findAll();
		request.setAttribute("forumList", forumList);
		return "/WEB-INF/jsp/forumManage/list.jsp";
	}
	
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/WEB-INF/jsp/forumManage/saveUI.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		Forum forum = CommonUtils.toBean(map, Forum.class);
		forumManageService.save(forum);
		return list(request, response);
	}

	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		forumManageService.delete(id);
		return list(request, response);
	}

	public String editUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Forum forum = forumManageService.getById(id);
		request.setAttribute("forum", forum);
		return "/WEB-INF/jsp/forumManage/saveUI.jsp";
	}

	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		Forum forum = CommonUtils.toBean(map, Forum.class);
		System.out.println(forum);
		forumManageService.update(forum);
		return list(request, response);
	}
	
	public String moveUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		forumManageService.moveUp(id);
		return list(request, response);
	}
	
	public String moveDown(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		forumManageService.moveDown(id);
		return list(request, response);
	}

}
