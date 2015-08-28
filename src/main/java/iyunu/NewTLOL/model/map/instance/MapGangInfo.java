package iyunu.NewTLOL.model.map.instance;

import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 帮派地图信息
 * 
 * @author SunHonglei
 * 
 */
public class MapGangInfo extends BaseMap {

	private Gang gang;

	public MapGangInfo() {

	}

	public void removeMonster() {
		Iterator<Entry<Long, MonsterOnMap>> it = monsters.entrySet().iterator();
		List<Long> list = new ArrayList<Long>();
		while (it.hasNext()) {
			Entry<Long, MonsterOnMap> entry = it.next();
			it.remove();
			list.add(entry.getKey());
		}
		MapMessage.removeMonsterOnMap(this, list);
	}

	// @Override
	// public void init() {
	// rolesAtGrids.add(new HashSet<Long>());
	// }

	// @Override
	// public int calcGrid(final Role role) {
	// return 0;
	// }

	// @Override
	// public List<Integer> countGrid(Role role) {
	// List<Integer> list = new ArrayList<Integer>();
	// list.add(calcGrid(role));
	// return list;
	// }

	@Override
	public MapGangInfo copy() {
		MapGangInfo mapGangInfo = new MapGangInfo();
		mapGangInfo.setId(id);
		mapGangInfo.setName(name);
		mapGangInfo.setType(type);
		mapGangInfo.setPve(pve);
		mapGangInfo.setPvp(pvp);
		mapGangInfo.setTeam(isTeam);
		mapGangInfo.setWidth(width);
		mapGangInfo.setHeight(height);
		mapGangInfo.setNpcs(npcs);
		mapGangInfo.setTransfers(transfers);
		mapGangInfo.setTransmitX(transmitX);
		mapGangInfo.setTransmitY(transmitY);
		// mapGangInfo.init(); // 初始化
		return mapGangInfo;
	}

	/**
	 * @return the gang
	 */
	public Gang getGang() {
		return gang;
	}

	/**
	 * @param gang
	 *            the gang to set
	 */
	public void setGang(Gang gang) {
		this.gang = gang;
	}
}
