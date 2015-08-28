package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JuHunManager {
	public static JuHunManager instance = new JuHunManager();
	public static final int JUNHUN_NUM = 10;
	public static final int REICEIVE_JUNHUN = 10;

	private Map<Long, List<Integer>> map = new HashMap<Long, List<Integer>>();// 上次的牌型
	private Map<Long, Integer> wuHunMap = new HashMap<Long, Integer>();// 本次应得的武魂数
	private Map<Integer, Integer> hunNumber = new HashMap<Integer, Integer>(); // 获得的魂数

	private JuHunManager() {
		hunNumber.put(0, 100);
		hunNumber.put(1, 200);
		hunNumber.put(2, 600);
		hunNumber.put(3, 2000);
		hunNumber.put(4, 8000);
		hunNumber.put(5, 20000);
		hunNumber.put(6, 60000);
	}

	/**
	 * 重置聚魂信息
	 * 
	 * @param role
	 */
	public static void reset(Role role) {
		// 刷新重置聚魂次数
		role.setJuHunNum(JuHunManager.JUNHUN_NUM);
		// 刷新收获聚魂次数
		role.setReceiveJuhun(JuHunManager.REICEIVE_JUNHUN);
	}

	/**
	 * 重置魂
	 * 
	 * @param list
	 *            客户端发上来的锁定与否 0锁定 1不锁定
	 * @return 数组
	 */
	public List<Integer> getRandom(Role role) {
		List<Integer> lastList = getWuHun(role.getId());
		if (lastList != null) {
			for (int i = 0; i < lastList.size(); i++) {
				if (lastList.get(i) != 6) {
					lastList.set(i, Util.getRandom(6) + 1);
				}
			}
			map.put(role.getId(), lastList);
			// 存入应得的武魂值
			wuHunMap.put(role.getId(), getNewWuhunNum(lastList));

			return lastList;
		} else {
			List<Integer> lastList1 = new ArrayList<Integer>();
			for (int j = 0; j < 6; j++) {
				lastList1.add(Util.getRandom(6) + 1);
			}
			map.put(role.getId(), lastList1);
			// 存入应得的武魂值
			wuHunMap.put(role.getId(), getNewWuhunNum(lastList1));
			return lastList1;
		}

	}

	/**
	 * @param list
	 *            本次结果集
	 * @return 根据结果算出应得武魂数
	 */
	public int getNewWuhunNum(List<Integer> list) {
		int num = 0;
		for (Integer i : list) {
			if (i == 6) {
				num++;
			}
		}
		return hunNumber.get(num);
	}

	/**
	 * @param roleId
	 *            角色ID
	 * @return 角色上一次武魂结果
	 */
	public List<Integer> getWuHun(long roleId) {
		return map.get(roleId);
	}

	/**
	 * @return the map
	 */
	public Map<Long, List<Integer>> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<Long, List<Integer>> map) {
		this.map = map;
	}

	/**
	 * @return the wuHunMap
	 */
	public Map<Long, Integer> getWuHunMap() {
		return wuHunMap;
	}

	/**
	 * @param wuHunMap
	 *            the wuHunMap to set
	 */
	public void setWuHunMap(Map<Long, Integer> wuHunMap) {
		this.wuHunMap = wuHunMap;
	}

	/**
	 * @return the hunNumber
	 */
	public Map<Integer, Integer> getHunNumber() {
		return hunNumber;
	}

}