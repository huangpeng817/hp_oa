package cn.hp.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.hp.oa.domain.Department;
import cn.hp.oa.service.DepartmentService;

public class DepartmentUtils {

	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTreeList(topList, "├", list);
		return list;
	}

	private static void walkDepartmentTreeList(Collection<Department> topList,
			String prefix, List<Department> list) {
		for (Department top : topList) {
			top.setName(prefix + top.getName());
			list.add(top);
			
			DepartmentService departmentService = new DepartmentService();
			List<Department> children = departmentService.findChildren(top.getId());
			walkDepartmentTreeList(children, "　" + prefix, list);
		}
	}
}
