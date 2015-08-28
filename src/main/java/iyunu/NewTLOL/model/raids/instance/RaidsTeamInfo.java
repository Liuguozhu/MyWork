package iyunu.NewTLOL.model.raids.instance;

import iyunu.NewTLOL.model.role.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * 副本团队
 * 
 * @author SunHonglei
 * 
 */
public class RaidsTeamInfo {

	private int raidsState = 0; // 副本状态（0.未通关，1.通关）
	private List<Long> memberIds = new ArrayList<>(); // 副本成员编号

	private RaidsFloor raidsFloor; // 副本层信息
	private RaidsInfo raidsInfo; // 副本信息
	private long startTime; // 副本开启时间

	/**
	 * 构造方法
	 * 
	 * @param raidsInfo
	 *            副本信息对象
	 * @param role
	 *            角色对象
	 */
	public RaidsTeamInfo(RaidsInfo raidsInfo, Role role) {
		role.setRaidsTeamInfo(this);
		memberIds.add(role.getId());
		this.raidsInfo = raidsInfo;
		this.startTime = System.currentTimeMillis();
		resetRaidsFloor(1);
	}

	/**
	 * 构造方法
	 * 
	 * @param raidsInfo
	 *            副本信息对象
	 * @param roles
	 *            角色集合
	 */
	public RaidsTeamInfo(RaidsInfo raidsInfo, List<Role> roles) {
		for (Role role : roles) {
			role.setRaidsTeamInfo(this);
			memberIds.add(role.getId());
		}
		this.raidsInfo = raidsInfo;
		this.startTime = System.currentTimeMillis();
		resetRaidsFloor(1);
	}

	/**
	 * 重置副本层信息
	 * 
	 * @param floor
	 */
	public boolean resetRaidsFloor(int floor) {
		if (this.raidsInfo.getFloorMax() > floor) {
			raidsFloor = new RaidsFloor(floor, raidsInfo.getSize(), this, false);
			return true;
		} else if (this.raidsInfo.getFloorMax() == floor) { // 最后关卡
			raidsFloor = new RaidsFloor(floor, raidsInfo.getSize(), this, true);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @function 检查副本层信息
	 * @author LuoSR
	 * @param floor
	 *            层信息
	 * @return
	 * @date 2014年5月15日
	 */
	public boolean checkRaidsFloor(int floor) {
		boolean isFloor = true;
		if (raidsFloor.getFloor() >= floor) {
			isFloor = false;
		}
		return isFloor;
	}

	/**
	 * @return the memberIds
	 */
	public List<Long> getMemberIds() {
		return memberIds;
	}

	/**
	 * @param memberIds
	 *            the memberIds to set
	 */
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	/**
	 * @return the raidsFloor
	 */
	public RaidsFloor getRaidsFloor() {
		return raidsFloor;
	}

	/**
	 * @param raidsFloor
	 *            the raidsFloor to set
	 */
	public void setRaidsFloor(RaidsFloor raidsFloor) {
		this.raidsFloor = raidsFloor;
	}

	/**
	 * @return the raidsInfo
	 */
	public RaidsInfo getRaidsInfo() {
		return raidsInfo;
	}

	/**
	 * @param raidsInfo
	 *            the raidsInfo to set
	 */
	public void setRaidsInfo(RaidsInfo raidsInfo) {
		this.raidsInfo = raidsInfo;
	}

	/**
	 * @return the raidsState
	 */
	public int getRaidsState() {
		return raidsState;
	}

	/**
	 * @param raidsState
	 *            the raidsState to set
	 */
	public void setRaidsState(int raidsState) {
		this.raidsState = raidsState;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
