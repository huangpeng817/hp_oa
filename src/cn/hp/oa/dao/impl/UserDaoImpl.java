package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.UserDao;
import cn.hp.oa.domain.Department;
import cn.hp.oa.domain.Role;
import cn.hp.oa.domain.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	
	@Override
	public void update(User entity) throws SQLException {
		String sql = "update itcast_user set name=?,loginName=?,gender=?,phoneNumber=?,email=?,description=?,departmentId=? where id=?";
		Long departmentId = null;
		if (entity.getDepartment() != null) {
			departmentId = entity.getDepartment().getId();
		}
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getName());
		params.add(entity.getLoginName());
		if (entity.getPassword() != null && !entity.getPassword().trim().isEmpty()) {
			sql = "update itcast_user set name=?,loginName=?,password=?,gender=?,phoneNumber=?,email=?,description=?,departmentId=? where id=?";
			params.add(entity.getPassword());
		}
		params.add(entity.getGender());
		params.add(entity.getPhoneNumber());
		params.add(entity.getEmail());
		params.add(entity.getDescription());
		params.add(departmentId);
		params.add(entity.getId());
		
		qr.update(sql, params.toArray());
		
		/**
		 * 修改用户后，维护和岗位的关联关系，即：修改itcast_user_role记录 
		 * 策略1： 删除所有和该用户有关的记录，删除插入新纪录
		 * 
		 * 策略2： 查询与此用户相关的记录
		 * 	> 如果有该记录，修改之
		 *  > 如果没有记录，插入新纪录
		 */
		
		/* 使用策略1 */
		sql = "delete from itcast_user_role where userId=?";
		qr.update(sql, entity.getId());
		
		Set<Role> roles = entity.getRoles();
		if (roles != null) {
			Long[] roleIds = new Long[roles.size()];
			int i = 0;
			for (Role role : roles) {
				Long roleId = role.getId();
				roleIds[i] = roleId;
				i++;
			}
			for (int j = 0; j < roleIds.length; j++) {
				sql = "insert into itcast_user_role values(?,?)";
				qr.update(sql, entity.getId(), roleIds[j]);
			}
		}
		
	}
	
	@Override
	public User getById(Long id) throws SQLException {
		String sql = "select * from itcast_user where id=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), id);
		if (mapList != null && mapList.size() !=0) {
			Map<String, Object> map = mapList.get(0);
			return toUser(map);
		}
		return null;
	}
	
	@Override
	public void save(User entity) throws SQLException {
		String sql = "insert into itcast_user(name,loginName,password,gender,phoneNumber,email,description,departmentId) values(?,?,?,?,?,?,?,?)";
		Long departmentId = null;
		if (entity.getDepartment() != null) {
			departmentId = entity.getDepartment().getId();
		}
		Object[] params = {
			entity.getName(),
			entity.getLoginName(),
			entity.getPassword(),
			entity.getGender(),
			entity.getPhoneNumber(),
			entity.getEmail(),
			entity.getDescription(),
			departmentId
		};
		qr.update(sql, params);
		
		/* 新增用户后，维护和岗位的管理关系，即：向itcast_user_role插入记录 */
		sql = "SELECT MAX(id) FROM itcast_user";
		Number idNum = (Number) qr.query(sql, new ScalarHandler());
		Long userId = idNum.longValue();
		
		Set<Role> roles = entity.getRoles();
		if (roles != null) {
			Long[] roleIds = new Long[roles.size()];
			int i = 0;
			for (Role role : roles) {
				Long roleId = role.getId();
				roleIds[i] = roleId;
				i++;
			}
			for (int j = 0; j < roleIds.length; j++) {
				sql = "insert into itcast_user_role values(?,?)";
				qr.update(sql, userId, roleIds[j]);
			}
		}
		
	}
	
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
		
		/**
		 * 处理用户的岗位
		 */
		if (user != null && user.getId() != null) {
			Long id = user.getId();
			String sql = "select roleId from itcast_user_role where userId=?";
			try {
				List<Object[]> list = qr.query(sql, new ArrayListHandler(), id);
				if (list != null && list.size() != 0) {
					Set<Long> roleIds = new HashSet<Long>();
					for (Object[] obj : list) {
						roleIds.add((Long) obj[0]);
					}
					Long[] ids = new Long[roleIds.size()];
					roleIds.toArray(ids);
					List<Role> roleList = new RoleDaoImpl().getByIds(ids);
//					System.out.println(roleList);
					Set<Role> roleSet = new HashSet<Role>(roleList);
					user.setRoles(roleSet);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		return user;
	}

	@Override
	public User findByLoginNameAndPassword(String loginName, String password) throws SQLException {
		String sql = "select * from itcast_user where loginName=? and password=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(), loginName, password);
		return toUser(map);
	}
}
