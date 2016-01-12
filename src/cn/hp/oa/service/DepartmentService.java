package cn.hp.oa.service;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.dao.DepartmentDao;
import cn.hp.oa.dao.impl.DepartmentDaoImpl;
import cn.hp.oa.domain.Department;

public class DepartmentService {

	private DepartmentDao departmentDao = new DepartmentDaoImpl();
	
	public List<Department> findAll() {
		try {
			return departmentDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Long id) {
		try {
			departmentDao.delete(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(Department department) {
		try {
			departmentDao.save(department);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(Department department) {
		try {
			departmentDao.update(department);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Department getById(Long id) {
		try {
			return departmentDao.getById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Department> getByIds(Long[] ids) {
		try {
			return departmentDao.getByIds(ids);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Department> findTopList() {
		try {
			return departmentDao.findTopList();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Department> findChildren(Long parentId) {
		try {
			return departmentDao.findChildren(parentId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
