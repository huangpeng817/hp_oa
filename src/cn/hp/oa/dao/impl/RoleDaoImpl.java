package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.ArrayListHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.RoleDao;
import cn.hp.oa.domain.Privilege;
import cn.hp.oa.domain.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao  {

	@Override
	public void save(Role entity) throws SQLException {
		String sql = "insert into itcast_role(name, description) values(?,?)";
		Object[] params = {
				entity.getName(),
				entity.getDescription()
		};
		qr.update(sql, params);
	}
	
	@Override
	public void update(Role entity) throws SQLException {
		/**
		 * 对普通字段的修改
		 */
//		super.update(entity); 
		String sql = "update itcast_role set name=?,description=? where id=?";
		Object[] params = {
				entity.getName(),
				entity.getDescription(),
				entity.getId()
		};
		qr.update(sql, params);
		
		/**
		 * 维护和权限的关联关系，即：修改itcast_role_privilege的记录 
		 * 策略1： 删除所有和该岗位有关的记录，插入新纪录
		 * 
		 * 策略2： 查询与此岗位相关的记录
		 * 	> 如果有该记录，修改之
		 *  > 如果没有该记录，插入新纪录
		 */
		
		/* 使用策略1 */
		sql = "delete from itcast_role_privilege where roleId=?";
		qr.update(sql, entity.getId());
		
		Set<Privilege> privileges = entity.getPrivileges();
		if (privileges != null) {
			Long[] privilegeIds = new Long[privileges.size()];
			int i = 0;
			for (Privilege privilege : privileges) {
				Long privilegeId = privilege.getId();
				privilegeIds[i] = privilegeId;
				i++;
			}
			for (int j = 0; j < privilegeIds.length; j++) {
				sql = "insert into itcast_role_privilege values(?,?)";
				qr.update(sql, entity.getId(), privilegeIds[j]);
			}
		}
		
	}
	
	@Override
	public Role getById(Long id) throws SQLException {
		Role role = super.getById(id);
		
		/**
		 * 处理岗位的权限--关联关系
		 */
		String sql = "select privilegeId from itcast_role_privilege where roleId=?";
		try {
			List<Object[]> list = qr.query(sql, new ArrayListHandler(), id);
			if (list != null && list.size() != 0) {
				Set<Long> privilegeIds = new HashSet<Long>();
				for (Object[] obj : list) {
					privilegeIds.add((Long) obj[0]);
				}
				Long[] ids = new Long[privilegeIds.size()];
				privilegeIds.toArray(ids);
				List<Privilege> privilegeList = new PrivilegeDaoImpl().getByIds(ids);
				Set<Privilege> privilegeSet = new HashSet<Privilege>(privilegeList);
				role.setPrivileges(privilegeSet);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return role;
	}
	
//	private Role toRole(Map<String, Object> map) {
//		
//	}
}
