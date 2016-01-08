package cn.hp.oa.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
		Long parentId = null;
		if (entity.getParent() != null) {
			parentId = entity.getParent().getId();
		}
		String sql = "insert into itcast_department(name,description,parentId) values(?,?,?)";
		Object[] params = {
				entity.getName(),
				entity.getDescription(),
				parentId
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
		// 方式一：封装BeanUtils的populate函数
//		Department department = CommonUtils.toBean(map, Department.class);
		
		// 方式二： 使用原生的populate函数，通过引用传递，department自动封装，并且函数外部使用是封装过后的department对象
		Department department = new Department();
		try {
			BeanUtils.populate(department, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	/**
	 * 获取顶级部门列表(parentId为null)
	 */
	@Override
	public List<Department> findTopList() throws SQLException {
		String sql = "select * from itcast_department where parentId is null";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		List<Department> topList = toDepartmentList(mapList);
		return topList;
	}

	/**
	 * 获取当前部门的子部门列表
	 */
	@Override
	public List<Department> findChildren(Long parentId) throws SQLException {
		String sql = "select * from itcast_department where parentId=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), parentId);
		List<Department> children = toDepartmentList(mapList);
		return children;
	}
}
