package cn.hp.oa.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import cn.hp.oa.domain.Department;
import cn.hp.oa.domain.Role;
import cn.hp.oa.domain.User;
import cn.hp.oa.service.DepartmentService;
import cn.hp.oa.service.RoleService;
import cn.hp.oa.service.UserService;
import cn.hp.oa.util.DepartmentUtils;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService userService = new UserService();
	private DepartmentService departmentService = new DepartmentService();
	private RoleService roleService = new RoleService();
	
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String loginName = req.getParameter("loginName");
		String password = DigestUtils.md5Hex(req.getParameter("password"));
		User user = userService.findByLoginNameAndPassword(loginName, password);
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/WEB-INF/jsp/user/loginUI.jsp";
		} else {
			req.getSession().setAttribute("user", user);
			return "r:/index.jsp";
		}
		
	}
	
	public String loginUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return "/WEB-INF/jsp/user/loginUI.jsp";
	}
	
	public String logout(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		return "/WEB-INF/jsp/user/logout.jsp"; // 由于WEB-INF目录下的资源不能直接方法，所以不能使用重定向(地址栏直接定位到此目录下)
	}
	
	public String list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> userList = userService.findAll();
		req.setAttribute("userList", userList);
		return "/WEB-INF/jsp/user/list.jsp";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		userService.delete(Long.parseLong(id));
		return list(req, resp);
	}
	
	public String addUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		req.setAttribute("departmentList", departmentList);
		List<Role> roleList = roleService.findAll();
		req.setAttribute("roleList", roleList);
		return "/WEB-INF/jsp/user/saveUI.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		user.setPassword(DigestUtils.md5Hex("1234")); // 初始密码为1234，采用MD5方式加密存储
		String departmentId = req.getParameter("departmentId");
		Department department = departmentService.getById(Long.parseLong(departmentId));
		user.setDepartment(department);
		String[] roleIds = req.getParameterValues("roleIdList");
		if (roleIds != null) {
			Long[] ids = new Long[roleIds.length];
			for (int i = 0; i < roleIds.length; i++) {
				ids[i] = Long.parseLong(roleIds[i]);
			}
			List<Role> roleList = roleService.getByIds(ids);
			user.setRoles(new HashSet<Role>(roleList));
		}
		userService.save(user);
		return list(req, resp);
	}
	
	public String editUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		req.setAttribute("departmentList", departmentList);
		List<Role> roleList = roleService.findAll();
		req.setAttribute("roleList", roleList);
		String id = req.getParameter("id");
		User user = userService.getById(Long.parseLong(id));
		req.setAttribute("user", user);
		Set<Role> roleSet = user.getRoles();
		List<Role> roles = new ArrayList<Role>(roleSet);
		req.setAttribute("roles", roles);
		
		return "/WEB-INF/jsp/user/saveUI.jsp";
	}
	
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		
		String departmentId = req.getParameter("departmentId");
		Department department = departmentService.getById(Long.parseLong(departmentId));
		user.setDepartment(department);
		String[] roleIds = req.getParameterValues("roleIdList");
		if (roleIds != null) {
			Long[] ids = new Long[roleIds.length];
			for (int i = 0; i < roleIds.length; i++) {
				ids[i] = Long.parseLong(roleIds[i]);
			}
			List<Role> roleList = roleService.getByIds(ids);
			user.setRoles(new HashSet<Role>(roleList));
		}
		
		userService.update(user);
		return list(req, resp);
	}
	
	public String initPassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		System.out.println(id);
		User user = userService.getById(Long.parseLong(id));
		user.setPassword(DigestUtils.md5Hex("1234"));
		System.out.println(user);  
		userService.update(user);
		return list(req, resp);
	}

}
