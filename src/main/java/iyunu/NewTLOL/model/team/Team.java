package iyunu.NewTLOL.model.team;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.TeamManager;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Team {

	private static int autoincreaseid = 1; // 队伍编号计数器
	public static final byte MAX_MEMBER = 3; // 队伍最大成员数量

	private int id; // 队伍编号
	private String name; // 队伍名称
	private Role owner; // 队长
	private ArrayList<Role> member; // 队伍成员列表
	private Set<Long> applyList = new HashSet<Long>(); // 申请列表

	/**
	 * 构造方法
	 */
	public Team(Role role, String name) {
		this.owner = role;
		this.name = name;
		member = new ArrayList<Role>(MAX_MEMBER); // 小队中最多有3个成员
		member.add(role);
		this.id = autoincreaseid++;
		if (autoincreaseid > 1000000000) {
			autoincreaseid = 1;
		}
		MapManager.instance().addTeamQueue(role);
	}

	/**
	 * 申请队伍
	 * 
	 * @param roleId
	 *            申请角色编号
	 * @return 已在队伍中
	 */
	public boolean apply(Long roleId) {
		if (applyList.size() <= 8) {
			// if (applyList.contains(roleId)) {
			// return true;
			// }
			applyList.add(roleId);
			// applyList = applyList.subList(0, 8);
		}
		return false;
	}

	/**
	 * 添加队员
	 * 
	 * @param role
	 */
	public final void addMember(final Role role) {
		if (member.size() < 3) {
			if (!member.contains(role)) {
				member.add(role);
			}
		}
	}

	/**
	 * 删除队员
	 * 
	 * @param role
	 *            角色对象
	 * @return 成功
	 */
	public void removeMember(Role role) {
		if (member.remove(role)) {
			TeamManager.instance().clearTeam(role);
			TeamMessage.sendTeamAllMsg(this); // 通知队中其他人有人离开队伍
		}
	}

	/**
	 * 获取队长，第一个人是队长
	 * 
	 * @return 队长
	 */
	public Role getLeader() {
		return owner;
	}

	/**
	 * 获取队伍人数
	 * 
	 * @return 队伍人数
	 */
	public int size() {
		return member.size();
	}

	/**
	 * 解散小队
	 */
	public void dismiss() {
		member.clear();
	}

	/**
	 * 更改队长
	 * 
	 * @param role
	 *            角色对象
	 */
	public void changeLeader(Role role) {
		int index = member.indexOf(role);
		Role old = member.get(0);
		member.set(0, role);
		member.set(index, old);
		owner = role;
	}

	/**
	 * 获取队伍成员数量
	 * 
	 * @return 人数
	 */
	public int getMemberSize() {
		return member.size();
	}

	/**
	 * 获得小队成员
	 * 
	 * @return 小队成员列表
	 */
	public ArrayList<Role> getMember() {
		return member;
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * 是否在队伍中
	 * 
	 * @param role
	 *            角色对象
	 * @return 是
	 */
	public boolean isTeammate(Role role) {
		return member.contains(role);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the applyList
	 */
	public Set<Long> getApplyList() {
		return applyList;
	}

	/**
	 * @param applyList
	 *            the applyList to set
	 */
	public void setApplyList(Set<Long> applyList) {
		this.applyList = applyList;
	}

	/**
	 * @return the owner
	 */
	public Role getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Role owner) {
		this.owner = owner;
	}

}