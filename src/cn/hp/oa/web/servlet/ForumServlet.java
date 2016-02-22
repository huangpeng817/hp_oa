package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.domain.Forum;
import cn.hp.oa.domain.Topic;
import cn.hp.oa.service.ForumManageService;
import cn.itcast.servlet.BaseServlet;

public class ForumServlet extends BaseServlet {

	private ForumManageService forumManageService = new ForumManageService();
	
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Forum> forumList = forumManageService.findAll();
		request.setAttribute("forumList", forumList);
		return "/WEB-INF/jsp/forum/list.jsp";
	}
	
	public String show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Forum forum = forumManageService.getById(id);
		request.setAttribute("forum", forum);
		Set<Topic> topics = forum.getTopics();
		List<Topic> topicList = new ArrayList<Topic>(topics);
		request.setAttribute("topicList", topicList);
		return "/WEB-INF/jsp/forum/show.jsp";
	}

}
