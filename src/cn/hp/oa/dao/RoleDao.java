package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hp.oa.domain.Role;
import cn.itcast.jdbc.TxQueryRunner;

public class RoleDao {

	private QueryRunner qr = new TxQueryRunner();
	
	public List<Role> findAll() throws SQLException {
		String sql = "select * from itcast_role";
		return qr.query(sql, new BeanListHandler<Role>(Role.class));
		
	}
	
	public void delete(Long id) throws SQLException {
		String sql = "delete from itcast_role where id=?";
		qr.update(sql, id);
	}
	
	public void save(Role role) throws SQLException {
		String sql = "insert into itcast_role(name, description) values(?, ?)";
		Object[] params = {
				role.getName(),
				role.getDescription()
		};
		qr.update(sql, params);
	}
	
	public void update(Role role) throws SQLException {
		String sql = "update itcast_role set name=?, description=? where id=?";
		Object[] params = {
				role.getName(),
				role.getDescription(),
				role.getId()
		};
		qr.update(sql, params);
	}
	
	public Role getById(Long id) throws SQLException {
		String sql = "select * from itcast_role where id=?";
		return qr.query(sql, new BeanHandler<Role>(Role.class), id);
	}
	
	public List<Role> getByIds(Long[] ids) throws SQLException {
		List<Role> list = new ArrayList<Role>();
		for (Long id : ids) {
			Role role = getById(id);
			list.add(role);
		}
		return list;
	}
}
