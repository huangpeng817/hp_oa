package cn.hp.oa.domain;

import java.util.HashSet;
import java.util.Set;

public class Privilege implements java.io.Serializable {

	private Long id;
	private String url;
	private String name;

	/* 关联关系 */
	private Privilege parent;
	private Set<Privilege> children = new HashSet<Privilege>();

	private Set<Role> roles = new HashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public Set<Privilege> getChildren() {
		return children;
	}

	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", url=" + url + ", name=" + name
				+ ", parent=" + parent + ", children=" + children + ", roles="
				+ roles + "]";
	}

}
