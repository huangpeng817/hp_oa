package cn.hp.oa.dao;

import java.sql.SQLException;

import cn.hp.oa.base.BaseDao;
import cn.hp.oa.domain.Forum;

public interface ForumManageDao extends BaseDao<Forum> {

	void moveUp(Long id) throws SQLException;

	void moveDown(Long id) throws SQLException;

}
