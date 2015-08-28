package iyunu.NewTLOL.model.arena;

import iyunu.NewTLOL.model.role.Role;

public class ArenaModel {
	private Role role = new Role();
	private int type = 0;// 0机器人 1人

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
