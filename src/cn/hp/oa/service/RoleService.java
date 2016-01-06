package cn.hp.oa.service;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.dao.RoleDao;
import cn.hp.oa.domain.Role;

public class RoleService {

	private RoleDao roleDao = new RoleDao();
	
	public List<Role> findAll() {
		try {
			return roleDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Long id) {
		try {
			roleDao.delete(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(Role role) {
		try {
			roleDao.save(role);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(Role role) {
		try {
			roleDao.update(role);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Role getById(Long id) {
		try {
			return roleDao.getById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Role> getByIds(Long[] ids) {
		try {
			return roleDao.getByIds(ids);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
