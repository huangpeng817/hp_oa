package cn.hp.oa.service;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.dao.UserDao;
import cn.hp.oa.dao.impl.UserDaoImpl;
import cn.hp.oa.domain.User;

public class UserService {

	private UserDao userDao = new UserDaoImpl();
	
	public List<User> findAll() {
		try {
			return userDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Long id) {
		try {
			userDao.delete(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(User user) {
		try {
			userDao.save(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(User user) {
		try {
			userDao.update(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User getById(Long id) {
		try {
			return userDao.getById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<User> getByIds(Long[] ids) {
		try {
			return userDao.getByIds(ids);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User findByLoginNameAndPassword(String loginName, String password) {
		try {
			return userDao.findByLoginNameAndPassword(loginName, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
