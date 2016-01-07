package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.hp.oa.dao.impl.RoleDaoImpl;
import cn.hp.oa.domain.Role;

public class RoleDaoTest {

	private RoleDao dao = new RoleDaoImpl();
	
	@Test
	public void testFindAll() throws Exception {
		List<Role> list = dao.findAll();
		System.out.println(list);
	}
	
	@Test
	public void testDelete() throws Exception {
		dao.delete(7L);
	}

	@Test
	public void testSave() throws SQLException {
		Role role = new Role();
		role.setName("test1");
		role.setDescription("test1");
		dao.save(role);
	}
	
	@Test
	public void testUpdate() throws SQLException {
		Role role = new Role();
		role.setId(9L);
		role.setName("test1");
		role.setDescription("test1");
		dao.update(role);
	}

	@Test
	public void testGetById() throws SQLException {
		Role role = dao.getById(2L);
		System.out.println(role);
	}

	@Test
	public void testGetByIds() throws SQLException {
		Long[] ids = {1L, 2L};
		List<Role> list = dao.getByIds(ids);
		System.out.println(list);
	}

}
