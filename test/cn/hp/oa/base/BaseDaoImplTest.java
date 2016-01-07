package cn.hp.oa.base;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.hp.oa.domain.Department;

public class BaseDaoImplTest {

	DepartmentDaoImpl daoImpl = new DepartmentDaoImpl();
	
	@Test
	public void testSave() throws SQLException {
		Department entity = new Department();
		entity.setName("test_dept");
		entity.setDescription("test_desc");
		daoImpl.save(entity);
	}

	@Test
	public void testDelete() throws SQLException {
		daoImpl.delete(25L);
	}

	@Test
	public void testUpdate() throws SQLException {
		Department entity = new Department();
		entity.setId(17L);
		entity.setName("WWWW");
		entity.setDescription("WWWW");
		daoImpl.update(entity);
	}
 
	@Test
	public void testGetById() throws SQLException {
		Department department = daoImpl.getById(16L);
		System.out.println(department);
	}

	@Test
	public void testGetByIds() throws SQLException {
		Long[] ids = {9L, 10L};
		List<Department> list = daoImpl.getByIds(ids);
		System.out.println(list);
	}

	@Test
	public void testFindAll() throws SQLException {
		List<Department> list = daoImpl.findAll();
		System.out.println(list);
	}

}
