package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.base.BaseDao;
import cn.hp.oa.domain.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege> {

	/**
	 * 查询顶级权限列表
	 */
	List<Privilege> findTopList() throws SQLException;
}
