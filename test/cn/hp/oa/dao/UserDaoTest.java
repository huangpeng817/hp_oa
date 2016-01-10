package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.Test;

import cn.hp.oa.dao.impl.DepartmentDaoImpl;
import cn.hp.oa.dao.impl.RoleDaoImpl;
import cn.hp.oa.dao.impl.UserDaoImpl;
import cn.hp.oa.domain.Department;
import cn.hp.oa.domain.Role;
import cn.hp.oa.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

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
	
	@Test
	public void test2() throws Exception {
		QueryRunner qr = new TxQueryRunner();
		String sql = "select roleId from itcast_user_role where userId=?";
		try {
			List<Object[]> list = qr.query(sql, new ArrayListHandler(), 100);
			if (list != null && list.size() != 0) {
				
				Set<Long> roleIds = new HashSet<Long>();
				for (Object[] obj : list) {
					roleIds.add((Long) obj[0]);
				}
				Long[] ids = new Long[roleIds.size()];
				roleIds.toArray(ids);
				List<Role> roleList = new RoleDaoImpl().getByIds(ids);
				System.out.println(roleList);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
