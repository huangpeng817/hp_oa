package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.domain.Role;
import cn.hp.oa.service.RoleService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class RoleServlet extends BaseServlet {

	private RoleService roleService = new RoleService();
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Role> roleList = roleService.findAll();
		req.setAttribute("roleList", roleList);
		return "/WEB-INF/jsp/role/list.jsp";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		roleService.delete(Long.parseLong(id));
		return list(req, resp);
	}
	
	public String addUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return "/WEB-INF/jsp/role/saveUI.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Role role = CommonUtils.toBean(req.getParameterMap(), Role.class);
		roleService.save(role);
		return list(req, resp);
	}

	public String editUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		Role role = roleService.getById(Long.parseLong(id));
		req.setAttribute("role", role);
		return "/WEB-INF/jsp/role/saveUI.jsp";
	}
	
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Role role = CommonUtils.toBean(req.getParameterMap(), Role.class);
		roleService.update(role);
		return list(req, resp);
	}

}
