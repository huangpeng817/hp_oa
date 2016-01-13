package cn.hp.oa.util;

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
	
}
