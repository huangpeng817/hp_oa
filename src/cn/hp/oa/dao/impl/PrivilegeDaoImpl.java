package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.hp.oa.base.BaseDaoImpl;
import cn.hp.oa.dao.PrivilegeDao;
import cn.hp.oa.domain.Privilege;
import cn.itcast.commons.CommonUtils;

public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao {
	
	@Override
	public List<Privilege> findAll() throws SQLException {
		String sql = "select * from itcast_privilege";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		List<Privilege> privilegeList = toPrivilegeList(mapList);
		return privilegeList;
	}

	private List<Privilege> toPrivilegeList(List<Map<String, Object>> mapList) {
		List<Privilege> list = new ArrayList<Privilege>();
		for (Map<String, Object> map : mapList) {
			Privilege privilege = toPrivilege(map);
			list.add(privilege);
		}
		return list;
	}

	private Privilege toPrivilege(Map<String, Object> map) {
		Privilege privilege = CommonUtils.toBean(map, Privilege.class);
		Long parentId = (Long) map.get("parentId");
		if (parentId != null) {
			try {
				Privilege parent = getById(parentId);
				privilege.setParent(parent);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		List<Privilege> childrenList;
		try {
			if (privilege != null) {
				childrenList = findChildren(privilege.getId());
				Set<Privilege> children = new HashSet<Privilege>(childrenList);
				for (Privilege child : children) {
					List<Privilege> grandsons = findChildren(child.getId());
					child.setChildren(new HashSet<Privilege>(grandsons));
				}
				privilege.setChildren(children);
			}
		} catch (SQLException e) {
		}
		
		return privilege;
	}
	
	@Override
	public Privilege getById(Long id) throws SQLException {
		String sql = "select * from itcast_privilege where id=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(), id);
		return toPrivilege(map);
	}

	@Override
	public List<Privilege> findTopList() throws SQLException {
		String sql = "select * from itcast_privilege where parentId is null";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		return toPrivilegeList(mapList);
	}
	
	private List<Privilege> findChildren(Long id) throws SQLException {
		String sql = "select * from itcast_privilege where parentId=?";
		return qr.query(sql, new BeanListHandler<Privilege>(Privilege.class), id);
	}
	
	
}
