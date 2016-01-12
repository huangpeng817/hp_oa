package cn.hp.oa.dao;

import java.sql.SQLException;

import cn.hp.oa.base.BaseDao;
import cn.hp.oa.domain.User;

public interface UserDao extends BaseDao<User> {

	User findByLoginNameAndPassword(String loginName, String password) throws SQLException;

}
