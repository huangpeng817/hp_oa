package cn.hp.oa.util;

import java.util.List;

import cn.hp.oa.domain.Privilege;
import cn.hp.oa.domain.Role;
import cn.hp.oa.domain.User;

public class JudgePrivilegeUtils {

	/**
	 * 根据权限名称判断当前是否有此权限
	 * @param name
	 * @return
	 */
	public static boolean hasPrivilegeByName(User user, String name) {
		if ("admin".equals(user.getLoginName())) { // 如果是管理员，拥有所有权限
			return true;
		}
		for (Role role : user.getRoles()) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilege.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断当前的权限id是否在已经拥有的权限id数组中(!!!页面中的第二级和第三极中的判断无法使用！！！)
	 * @param ids
	 * @param id
	 * @return
	 */
	public static boolean isContain(Long[] ids, Long id) {
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断当前的权限id是否在已经拥有的权限id的List集合中
	 * @param idList
	 * @param id
	 * @return
	 */
	public static boolean contains(List<Long> idList, Long id) {
		if (idList.contains(id)) {
			return true;
		}
		return false;
	}
	
}
