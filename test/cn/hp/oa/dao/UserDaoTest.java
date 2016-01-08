package cn.hp.oa.dao;

import java.util.List;

import org.junit.Test;

import cn.hp.oa.dao.impl.DepartmentDaoImpl;
import cn.hp.oa.dao.impl.UserDaoImpl;
import cn.hp.oa.domain.Department;
import cn.hp.oa.domain.User;

public class UserDaoTest {

	@Test
	public void test() throws Exception {
		UserDao dao = new UserDaoImpl();
		List<User> list = dao.findAll();
		System.out.println(list.get(0).getDepartment());
	}
	
	@Test
	public void test1() throws Exception {
		Department department = new DepartmentDaoImpl().getById(4L);
		System.out.println(department);
	}
}
