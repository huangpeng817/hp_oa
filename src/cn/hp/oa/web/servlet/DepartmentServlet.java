package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.domain.Department;
import cn.hp.oa.service.DepartmentService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class DepartmentServlet extends BaseServlet {

	private DepartmentService departmentService = new DepartmentService();
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Department> departmentList = departmentService.findAll();
		req.setAttribute("departmentList", departmentList);
		return "/WEB-INF/jsp/department/list.jsp";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		departmentService.delete(Long.parseLong(id));
		return list(req, resp);
	}
	
	public String addUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Department> departmentList = departmentService.findAll();
		req.setAttribute("departmentList", departmentList);
		return "/WEB-INF/jsp/department/saveUI.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Department department = CommonUtils.toBean(req.getParameterMap(), Department.class);
		String parentId = req.getParameter("parentId");
		Department parent = departmentService.getById(Long.parseLong(parentId));
		department.setParent(parent);
		departmentService.save(department);
		return list(req, resp);
	}

	public String editUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		List<Department> departmentList = departmentService.findAll();
		req.setAttribute("departmentList", departmentList);
		Department editDept = departmentService.getById(Long.parseLong(id));
		req.setAttribute("editDept", editDept);
		return "/WEB-INF/jsp/department/saveUI.jsp";
	}
	
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Department department = CommonUtils.toBean(req.getParameterMap(), Department.class);
		departmentService.update(department);
		return list(req, resp);
	}

}
