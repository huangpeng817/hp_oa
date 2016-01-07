package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.DepartmentDao;
import cn.hp.oa.domain.Department;
import cn.itcast.commons.CommonUtils;

public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {
	
	@Override
	public Department getById(Long id) throws SQLException {
		String sql = "select * from itcast_department where id=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), id);
		if (mapList != null && mapList.size() != 0) {
			Map<String, Object> map = mapList.get(0);
			Department department = toDepartment(map);
			return department;
		}
		return null;
	}
	
	@Override
	public void save(Department entity) throws SQLException {
		String sql = "insert into itcast_department(name,description,parentId) values(?,?,?)";
		Object[] params = {
				entity.getName(),
				entity.getDescription(),
				entity.getParent().getId()
		};
		qr.update(sql, params);
	}
	
	@Override
	public List<Department> findAll() throws SQLException {
		String sql = "select * from itcast_department";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		List<Department> departmentList = toDepartmentList(mapList);
		return departmentList;
	}

	private List<Department> toDepartmentList(List<Map<String, Object>> mapList) {
		List<Department> list = new ArrayList<Department>();
		for (Map<String, Object> map : mapList) {
			Department department = toDepartment(map);
			list.add(department);
		}
		return list;
	}

	private Department toDepartment(Map<String, Object> map) {
		Department department = CommonUtils.toBean(map, Department.class);
		Long parentId = (Long) map.get("parentId");
		if (parentId != null) {
			try {
				Department parent = getById(parentId);
				parent.setId(parentId);
				department.setParent(parent);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return department;
	}
}
