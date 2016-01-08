package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.UserDao;
import cn.hp.oa.domain.Department;
import cn.hp.oa.domain.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	@Override
	public List<User> findAll() throws SQLException {
		String sql = "select * from itcast_user";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		return toUserList(mapList);
	}

	private List<User> toUserList(List<Map<String, Object>> mapList) {
		List<User> list = new ArrayList<User>();
		for (Map<String, Object> map : mapList) {
			User user = toUser(map);
			list.add(user);
		}
		return list;
	}

	private User toUser(Map<String, Object> map) {
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Long departmentId = (Long) map.get("departmentId");
		if (departmentId != null) {
			try {
				Department department = new DepartmentDaoImpl().getById(departmentId);
				user.setDepartment(department);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return user;
	}
}
