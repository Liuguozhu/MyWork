package iyunu.NewTLOL.model.map.instance;

import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;

/**
 * 副本地图信息
 * 
 * @author SunHonglei
 * 
 */
public class MapRaidsInfo extends BaseMap {

	private int state; // 状态
	private RaidsTeamInfo raidsTeamInfo;
	private boolean isBoss = false; // 是否是BOSS关卡
	private boolean isLastBoss = false; // 是否是最终BOSS关卡
	private int index = 0;

//	@Override
//	public void init() {
//		rolesAtGrids.add(new HashSet<Long>());
//	}

//	@Override
//	public int calcGrid(final Role role) {
//		return 0;
//	}

//	@Override
//	public List<Integer> countGrid(Role role) {
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(calcGrid(role));
//		return list;
//	}

	@Override
	public MapRaidsInfo copy() {
		MapRaidsInfo mapRaidsInfo = new MapRaidsInfo();
		mapRaidsInfo.setId(id);
		mapRaidsInfo.setName(name);
		mapRaidsInfo.setType(type);
		mapRaidsInfo.setPve(pve);
		mapRaidsInfo.setPvp(pvp);
		mapRaidsInfo.setTeam(isTeam);
		mapRaidsInfo.setWidth(width);
		mapRaidsInfo.setHeight(height);
		mapRaidsInfo.setNpcs(npcs);
		mapRaidsInfo.setTransfers(transfers);
		mapRaidsInfo.setTransmitX(transmitX);
		mapRaidsInfo.setTransmitY(transmitY);
//		mapRaidsInfo.init();
		return mapRaidsInfo;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the raidsTeamInfo
	 */
	public RaidsTeamInfo getRaidsTeamInfo() {
		return raidsTeamInfo;
	}

	/**
	 * @param raidsTeamInfo
	 *            the raidsTeamInfo to set
	 */
	public void setRaidsTeamInfo(RaidsTeamInfo raidsTeamInfo) {
		this.raidsTeamInfo = raidsTeamInfo;
	}

	/**
	 * @return the isBoss
	 */
	public boolean isBoss() {
		return isBoss;
	}

	/**
	 * @param isBoss
	 *            the isBoss to set
	 */
	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the isLastBoss
	 */
	public boolean isLastBoss() {
		return isLastBoss;
	}

	/**
	 * @param isLastBoss the isLastBoss to set
	 */
	public void setLastBoss(boolean isLastBoss) {
		this.isLastBoss = isLastBoss;
	}

}
