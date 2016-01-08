package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hp.oa.domain.Department;
import cn.hp.oa.service.DepartmentService;
import cn.hp.oa.util.DepartmentUtils;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class DepartmentServlet extends BaseServlet {

	private DepartmentService departmentService = new DepartmentService();
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		List<Department> departmentList = departmentService.findAll();
		List<Department> departmentList = null;
		String parentId = req.getParameter("parentId");
		// 如果parentId为0,即：添加部门是没有选择指定的部门，默认为parentId=0的"请选择部门"，同样返回顶级部门
		if (parentId == null || parentId.trim().isEmpty() || parentId.equals("0")) { // 顶级部门列表(默认加载顶级部门列表)
			departmentList = departmentService.findTopList();
		} else { // 子部门列表
			Department parent = departmentService.getById(Long.parseLong(parentId)); // 找到父部门
			req.setAttribute("parent", parent);
			departmentList = departmentService.findChildren(Long.parseLong(parentId)); // 当前部门的子部门
		}
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
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		req.setAttribute("departmentList", departmentList);
//		String pId = req.getParameter("pId");
//		Department editDept = departmentService.getById(Long.parseLong(pId));
//		req.setAttribute("editDept", editDept); // 和修改页面统一名称，方面新建回显上级部门，默认为当前层级的上级部门
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
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
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
