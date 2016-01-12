package cn.hp.oa.service;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.dao.PrivilegeDao;
import cn.hp.oa.dao.impl.PrivilegeDaoImpl;
import cn.hp.oa.domain.Privilege;

public class PrivilegeService {

	private PrivilegeDao privilegeDao = new PrivilegeDaoImpl();
	
	public List<Privilege> findAll() {
		try {
			return privilegeDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Long id) {
		try {
			privilegeDao.delete(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(Privilege privilege) {
		try {
			privilegeDao.save(privilege);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(Privilege privilege) {
		try {
			privilegeDao.update(privilege);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Privilege getById(Long id) {
		try {
			return privilegeDao.getById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Privilege> getByIds(Long[] ids) {
		try {
			return privilegeDao.getByIds(ids);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
