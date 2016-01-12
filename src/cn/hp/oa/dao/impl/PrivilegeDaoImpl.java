package cn.hp.oa.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		
		return privilege;
	}
	
	@Override
	public Privilege getById(Long id) throws SQLException {
		String sql = "select * from itcast_privilege where id=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(), id);
		return toPrivilege(map);
	}
	
}
