package iyunu.NewTLOL.model.gang;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.map.instance.MapGangFightInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * @function 帮派战报名信息
 * @author LuoSR
 * @date 2014年6月25日
 */
public class FightApplyInfo {

	private long gangId; // 帮派编号
	private String gangName; // 帮派名称
	private int score; // 帮派战积分
	private long time; // 比赛用时
	private boolean isNull; // 是否空帮派
	private int upOrDown; // 帮派地图中的位置（1为上，2为下）
//	private int winNumber; // 胜利次数
	private int index; // 淘汰赛索引位置
	private int roleNum; // 地图实时人数

	private Set<Long> roleIds = new HashSet<Long>(); // 当前参赛角色编号
	private MapGangFightInfo mapGangFightInfo; // 帮战地图

	/**
	 * 添加胜利次数
	 */
//	public void addWin() {
//		winNumber++;
//	}

	/**
	 * 清除胜利次数
	 * 
	 * @return
	 */
//	public FightApplyInfo clearWin() {
//		winNumber = 0;
//		return this;
//	}

	/**
	 * 帮派成员进场记录
	 * 
	 * @param roleId
	 */
	public void enter(long roleId) {
		roleIds.add(roleId);
		GangFightManager.参战角色编号.add(roleId);
		roleNum++;
	}

	public void out(int num) {
		roleNum -= num;
		if (roleNum < 0) {
			roleNum = 0;
		}
	}

	public void clearRoleIds() {
		roleIds.clear();
		roleNum = 0;
	}

	public int nowInMap() {
		return roleNum;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public MapGangFightInfo mapGangFightInfo() {
		return mapGangFightInfo;
	}

	public void initMapGangFightInfo(MapGangFightInfo mapGangFightInfo) {
		this.mapGangFightInfo = mapGangFightInfo;
	}

	public int getUpOrDown() {
		return upOrDown;
	}

	public void setUpOrDown(int upOrDown) {
		this.upOrDown = upOrDown;
	}

	public Set<Long> roleIds() {
		return roleIds;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the winNumber
	 */
//	public int getWinNumber() {
//		return winNumber;
//	}

	/**
	 * @param winNumber
	 *            the winNumber to set
	 */
//	public void setWinNumber(int winNumber) {
//		this.winNumber = winNumber;
//	}

	/**
	 * @return the gangId
	 */
	public long getGangId() {
		return gangId;
	}

	/**
	 * @param gangId
	 *            the gangId to set
	 */
	public void setGangId(long gangId) {
		this.gangId = gangId;
	}

	/**
	 * @return the gangName
	 */
	public String getGangName() {
		return gangName;
	}

	/**
	 * @param gangName
	 *            the gangName to set
	 */
	public void setGangName(String gangName) {
		this.gangName = gangName;
	}

	public int getRoleNum() {
		return roleNum;
	}

	public void setRoleNum(int roleNum) {
		this.roleNum = roleNum;
	}

}
