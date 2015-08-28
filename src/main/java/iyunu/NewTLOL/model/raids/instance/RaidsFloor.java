package iyunu.NewTLOL.model.raids.instance;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.EOrientation;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 副本层信息
 * 
 * @author SunHonglei
 * 
 */
public class RaidsFloor {

	private RaidsTeamInfo raidsTeamInfo; // 副本团队信息
	private int floor; // 层数
	private List<MapRaidsInfo> mapRaidsInfos = new ArrayList<MapRaidsInfo>(); // 格子
	private int state; // 状态
	private boolean isBoss = false; // 是否刷新BOSS

	/**
	 * 构造方法
	 * 
	 * @param floor
	 *            层数
	 * @param size
	 *            格子数量半径
	 * @param raidsTeamInfo
	 *            副本团队
	 * @param isLast
	 *            是否是最后关卡
	 */
	public RaidsFloor(int floor, int size, RaidsTeamInfo raidsTeamInfo, boolean isLast) {
		this.raidsTeamInfo = raidsTeamInfo;
		this.floor = floor;
		for (int i = size - 1; i >= 0; i--) {

			MapRaidsInfo mapRaidsInfo = MapJson.instance().getMapRaidsInfo(raidsTeamInfo.getRaidsInfo().getArriveMap());
			mapRaidsInfo.setState(0);
			mapRaidsInfo.setIndex(i);
			mapRaidsInfo.setRaidsTeamInfo(raidsTeamInfo);
			mapRaidsInfos.add(0, mapRaidsInfo);

			if (i == 0 && !isBoss) {
				mapRaidsInfo.addMonster(40, 20, raidsTeamInfo.getRaidsInfo().getBossByFloor(floor), EMonsterOnMap.raids);
				mapRaidsInfo.setBoss(true);
				isBoss = true;
				if (isLast) {
					mapRaidsInfo.setLastBoss(true);
				}
			} else if (!isBoss && i != 1 && Util.probable()) {
				mapRaidsInfo.addMonster(40, 20, raidsTeamInfo.getRaidsInfo().getBossByFloor(floor), EMonsterOnMap.raids);
				mapRaidsInfo.setBoss(true);
				isBoss = true;
				if (isLast) {
					mapRaidsInfo.setLastBoss(true);
				}
			} else {
				mapRaidsInfo.addMonster(40, 20, raidsTeamInfo.getRaidsInfo().randomMonster(floor), EMonsterOnMap.raids);
			}
		}
	}

	public MapRaidsInfo getCell(EOrientation orientation, int index) {
		if (index == -1) {
			return mapRaidsInfos.get(1);
		}
		return mapRaidsInfos.get(countIndex(orientation, index));
	}

	public int countIndex(EOrientation orientation, int index) {

		switch (orientation) {
		case east:
			return east(index);
		case west:
			return west(index);
		case south:
			return south(index);
		case north:
			return north(index);
		default:
			return 1;
		}
	}

	public int south(int index) {
		switch (index) {
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return index - 3;
		case 0:
		case 1:
		case 2:
			return index + 6;
		default:
			return 1;
		}
	}

	public int north(int index) {
		switch (index) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return index + 3;
		case 6:
		case 7:
		case 8:
			return index - 6;
		default:
			return 1;
		}
	}

	public int east(int index) {
		switch (index) {
		case 0:
		case 1:
		case 3:
		case 4:
		case 6:
		case 7:
			return index + 1;
		case 2:
		case 5:
		case 8:
			return index - 2;
		default:
			return 1;
		}
	}

	public int west(int index) {
		switch (index) {
		case 1:
		case 2:
		case 4:
		case 5:
		case 7:
		case 8:
			return index - 1;
		case 0:
		case 3:
		case 6:
			return index + 2;
		default:
			return 1;
		}
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
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
	 * @param floor
	 *            the floor to set
	 */
	public void setFloor(int floor) {
		this.floor = floor;
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
	 * @return the mapRaidsInfos
	 */
	public List<MapRaidsInfo> getMapRaidsInfos() {
		return mapRaidsInfos;
	}

	/**
	 * @param mapRaidsInfos
	 *            the mapRaidsInfos to set
	 */
	public void setMapRaidsInfos(List<MapRaidsInfo> mapRaidsInfos) {
		this.mapRaidsInfos = mapRaidsInfos;
	}

}
