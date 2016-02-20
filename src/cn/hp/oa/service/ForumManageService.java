package cn.hp.oa.service;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.dao.ForumManageDao;
import cn.hp.oa.dao.impl.ForumManageDaoImpl;
import cn.hp.oa.domain.Forum;

public class ForumManageService {

	private ForumManageDao forumManageDao = new ForumManageDaoImpl();
	
	public List<Forum> findAll() {
		try {
			return forumManageDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Long id) {
		try {
			forumManageDao.delete(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(Forum forum) {
		try {
			forumManageDao.save(forum);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(Forum forum) {
		try {
			forumManageDao.update(forum);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Forum getById(Long id) {
		try {
			return forumManageDao.getById(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Forum> getByIds(Long[] ids) {
		try {
			return forumManageDao.getByIds(ids);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
