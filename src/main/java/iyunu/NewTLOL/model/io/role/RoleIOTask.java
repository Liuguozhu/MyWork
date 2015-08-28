package iyunu.NewTLOL.model.io.role;

import iyunu.NewTLOL.model.role.Role;

public abstract class RoleIOTask {

	/** 任务编号 **/
	protected ERoleIOTask id;
	/** 角色对象 **/
	protected Role role;
	/** 是否回调 **/
	protected boolean isCallBack = true;

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            任务编号
	 * @param role
	 *            角色对象
	 */
	public RoleIOTask(ERoleIOTask id, Role role) {
		this.id = id;
		this.role = role;
	}

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            任务编号
	 * @param role
	 *            角色对象
	 * @param isCallBack
	 *            是否回调
	 */
	public RoleIOTask(ERoleIOTask id, Role role, boolean isCallBack) {
		this.id = id;
		this.role = role;
		this.isCallBack = isCallBack;
	}

	/**
	 * 执行方法
	 */
	public abstract void excute();

	/**
	 * 回调方法
	 */
	public abstract void callBack();

	/**
	 * @return the id
	 */
	public ERoleIOTask getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ERoleIOTask id) {
		this.id = id;
	}

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
	 * @return the isCallBack
	 */
	public boolean isCallBack() {
		return isCallBack;
	}

	/**
	 * @param isCallBack
	 *            the isCallBack to set
	 */
	public void setCallBack(boolean isCallBack) {
		this.isCallBack = isCallBack;
	}

}
