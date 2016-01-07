package cn.hp.oa.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hp.oa.base.BaseDao;
import cn.hp.oa.domain.Department;

public interface DepartmentDao extends BaseDao<Department> {

	List<Department> findTopList() throws SQLException;

	List<Department> findChildren(Long parentId) throws SQLException;

}
