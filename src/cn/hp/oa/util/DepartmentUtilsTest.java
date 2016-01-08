package cn.hp.oa.util;

import java.util.List;

import org.junit.Test;

import cn.hp.oa.domain.Department;
import cn.hp.oa.service.DepartmentService;

public class DepartmentUtilsTest {

	@Test
	public void test1() throws Exception {
	
		DepartmentService departmentService = new DepartmentService();
		Department top = new Department();
		top.setId(1L);
		top.setName("研发部");
		top.setDescription("研发部");
		Long parentId = top.getId();
		System.out.println(parentId);
		List<Department> children = departmentService.findChildren(parentId);
//		walkDepartmentTreeList(children, " " + prefix, list);
		
	}
}
