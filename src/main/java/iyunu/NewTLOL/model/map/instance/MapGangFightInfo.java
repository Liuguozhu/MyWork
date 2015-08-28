package iyunu.NewTLOL.model.map.instance;

import iyunu.NewTLOL.model.map.BaseMap;

/**
 * @function 帮派战地图信息
 * @author LuoSR
 * @date 2014年6月30日
 */
public class MapGangFightInfo extends BaseMap {

	/** 地图索引 **/
//	private int index;

	public MapGangFightInfo() {

	}

	@Override
	public MapGangFightInfo copy() {
		MapGangFightInfo mapGangFightInfo = new MapGangFightInfo();
		mapGangFightInfo.setId(id);
		mapGangFightInfo.setName(name);
		mapGangFightInfo.setType(type);
		mapGangFightInfo.setPve(pve);
		mapGangFightInfo.setPvp(pvp);
		mapGangFightInfo.setTeam(isTeam);
		mapGangFightInfo.setWidth(width);
		mapGangFightInfo.setHeight(height);
		mapGangFightInfo.setNpcs(npcs);
		mapGangFightInfo.setTransfers(transfers);
		mapGangFightInfo.setTransmitX(transmitX);
		mapGangFightInfo.setTransmitY(transmitY);
		return mapGangFightInfo;
	}

//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}

}
