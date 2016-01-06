package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hp.oa.domain.Department;
import cn.itcast.jdbc.TxQueryRunner;

public class DepartmentDao {

	private QueryRunner qr = new TxQueryRunner();
	
	public List<Department> findAll() throws SQLException {
		String sql = "select * from itcast_department";
		return qr.query(sql, new BeanListHandler<Department>(Department.class));
		
	}
	
	public void delete(Long id) throws SQLException {
		String sql = "delete from itcast_department where id=?";
		qr.update(sql, id);
	}
	
	public void save(Department Department) throws SQLException {
		String sql = "insert into itcast_department(name, description) values(?, ?)";
		Object[] params = {
				Department.getName(),
				Department.getDescription()
		};
		qr.update(sql, params);
	}
	
	public void update(Department Department) throws SQLException {
		String sql = "update itcast_department set name=?, description=? where id=?";
		Object[] params = {
				Department.getName(),
				Department.getDescription(),
				Department.getId()
		};
		qr.update(sql, params);
	}
	
	public Department getById(Long id) throws SQLException {
		String sql = "select * from itcast_department where id=?";
		return qr.query(sql, new BeanHandler<Department>(Department.class), id);
	}
	
	public List<Department> getByIds(Long[] ids) throws SQLException {
		List<Department> list = new ArrayList<Department>();
		for (Long id : ids) {
			Department Department = getById(id);
			list.add(Department);
		}
		return list;
	}
}
