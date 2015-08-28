package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.enumeration.EDaily;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.daily.DailyModelRole;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.service.iface.payActivity.PayActivityService;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DailyManager {

	private static DailyManager instance = new DailyManager();
	public Map<Integer, DailyModel> map = new HashMap<>();

	// public static boolean FLAG = false;// 是否开启
	// public static long STARTTIME = 0;// 开启时间
	// public static long ENDTIME = 0;// 结束时间
	private Map<Long, List<Integer>> dailyMonsterMap = new HashMap<>();// 前是怪物ID，后是日常任务ID集合
	private Map<Integer, List<Integer>> dailyItemMap = new HashMap<>();// 前是物品ID，后是日常任务ID集合
	private List<Integer> dailyFuDiList = new ArrayList<>();// 福地任务的ID
	private List<Integer> dailyFuBen = new ArrayList<>();// 副本任务的ID
	private List<Integer> dailyPay = new ArrayList<>();// 充值任务的ID
	private List<Integer> dailySpend = new ArrayList<>();// 花费任务的ID

	public static DailyManager instance() {
		return instance;
	}

	private DailyManager() {
	}

	/**
	 * 开服加载日常任务 type 1开服时加载 ，2在线加载
	 */
	public void load(int type) {

		PayActivityService payActivityService = Spring.instance().getBean("payActivityService", PayActivityService.class);
		List<DailyModel> p = payActivityService.queryDaily();
		map.clear();
		dailyMonsterMap.clear();
		dailyItemMap.clear();
		dailyFuDiList.clear();
		dailyFuBen.clear();
		for (DailyModel dailyModel : p) {
			if (check(dailyModel)) {
				map.put(dailyModel.getId(), dailyModel);
				// 赋到类型中，用于事件触发时判断事件
				switch (dailyModel.getType()) {
				case 打怪:
					if (dailyMonsterMap.containsKey(dailyModel.getMonsterId())) {
						dailyMonsterMap.get(dailyModel.getMonsterId()).add(dailyModel.getId());
					} else {
						List<Integer> dailyMonster = new ArrayList<>();
						dailyMonster.add(dailyModel.getId());
						dailyMonsterMap.put(dailyModel.getMonsterId(), dailyMonster);
					}

					break;
				case 物品:
					if (dailyItemMap.containsKey(dailyModel.getItemId())) {
						dailyItemMap.get(dailyModel.getItemId()).add(dailyModel.getId());
					} else {
						List<Integer> dailyItem = new ArrayList<>();
						dailyItem.add(dailyModel.getId());
						dailyItemMap.put(dailyModel.getItemId(), dailyItem);
					}

					break;
				case 副本:
					dailyFuBen.add(dailyModel.getId());
					break;
				case 福地:
					dailyFuDiList.add(dailyModel.getId());
					break;
				case 充值:
					dailyPay.add(dailyModel.getId());
					break;
				case 花费:
					dailySpend.add(dailyModel.getId());
					break;
				default:
					break;
				}
			}
		}
		// 转换奖励
		StringToAward(map);

		if (type == 2) {
			// 更新所有在线角色的日常任务
			for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				compareDaily(role);
			}
		}
	}

	/**
	 * 加载时检查，只要结束时间在当前之后的，都加载
	 * 
	 * @param dailyId
	 * @return
	 */
	public boolean check(DailyModel daily) {
		long now = System.currentTimeMillis();
		if (now > daily.getEndTime().getTime()) {
			return false;
		}
		return true;
	}

	/**
	 * 事件触发时检查，只有开始时间在当前之前 ，结束时间在当前之后的，加载
	 * 
	 * @param dailyId
	 * @return
	 */
	public boolean checkEvent(int dailyId) {
		if (!map.containsKey(dailyId)) {
			return false;
		}
		long now = System.currentTimeMillis();

		if (now > map.get(dailyId).getEndTime().getTime() || now < map.get(dailyId).getStartTime().getTime()) {
			return false;
		}
		return true;
	}

	public void clear() {
		map.clear();
		dailyMonsterMap.clear();
		dailyItemMap.clear();
		dailyFuDiList.clear();
		dailyFuBen.clear();
		// 删除人物
		// for (Role role :
		// ServerManager.instance().getOnlinePlayers().values()) {
		// role.getDailyMap().clear();
		// }
		// // 删除数据库
		// PayActivityService payActivityService =
		// Spring.instance().getBean("payActivityService",
		// PayActivityService.class);
		// payActivityService.delDaily();

	}

	/**
	 * 检查人物的日常任务 和服务器的日常任务一致与否
	 * 
	 * @param role
	 *            角色对象
	 */
	public void compareDaily(Role role) {
		Map<Integer, DailyModelRole> temp = new HashMap<>();
		if (map.size() <= 0) {
			role.getDailyMap().clear();
			return;
		}
		// 循环服务器的任务map
		Iterator<Entry<Integer, DailyModel>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, DailyModel> d = it.next();
			if (checkEvent(d.getKey())) {
				Set<Integer> set = role.getDailyMap().keySet();
				// 如果角色上有和服务器相同的，把相同的存在tempMap上
				if (set.contains(d.getKey())) {
					DailyModelRole newd = new DailyModelRole();
					DailyModelRole oldd = role.getDailyMap().get(d.getKey());
					newd.setId(oldd.getId());
					newd.setTarget(oldd.getTarget());
					newd.setRec(oldd.getRec());
					newd.setCount(oldd.getCount());
					temp.put(newd.getId(), newd);
				} else {
					// 如果角色上有 和服务器不同的，把服务器的转为DailyModelRole，存到tempMap上
					DailyModel dm = d.getValue();
					DailyModelRole newd = new DailyModelRole();
					newd.setId(dm.getId());
					newd.setTarget(dm.getTarget());
					newd.setRec(0);
					newd.setCount(0);
					temp.put(dm.getId(), newd);
				}
			}
		}
		// 把tempMap整体赋给role
		role.setDailyMap(temp);
		// 检查如果是收集物品任务 ，检测个数
		for (DailyModelRole d : role.getDailyMap().values()) {
			if (DailyManager.instance.getMap().get(d.getId()).getType() == EDaily.物品) {
				finishDailyItem(d, role);
			}
		}
	}

	/**
	 * map的awardString 转换成award对象
	 * 
	 * @param map2
	 */
	public void StringToAward(Map<Integer, DailyModel> map2) {
		if (map2 != null && map2.size() > 0) {

			Iterator<DailyModel> it = map2.values().iterator();
			while (it.hasNext()) {
				List<MonsterDropItem> awardList = new ArrayList<MonsterDropItem>();
				DailyModel d = it.next();
				String[] s = d.getAwardString().split(";");
				for (int i = 0; i < s.length; i++) {
					String[] ss = s[i].split(":");
					awardList.add(new MonsterDropItem(Translate.stringToInt(ss[0]), Translate.stringToInt(ss[1]), 0, Translate.stringToInt(ss[2])));
				}
				d.setAward(awardList);
			}
		}
	}

	/**
	 * 日常任务计数:打怪:副本,充值 ,消费
	 * 
	 * @param dailyId
	 *            日常任务ID
	 * @param role
	 *            角色对象
	 */
	public void finishDaily(DailyModelRole dailyModelRole, Role role, int value) {
		if (dailyModelRole.getRec() == 0) {
			if (dailyModelRole.getCount() + value <= dailyModelRole.getTarget()) {
				dailyModelRole.setCount(dailyModelRole.getCount() + value);
				if (dailyModelRole.getCount() == dailyModelRole.getTarget()) {
					dailyModelRole.setCount(dailyModelRole.getTarget());
					dailyModelRole.setRec(1);
					// 刷新状态
					SendMessage.refreshRecDaily(role, dailyModelRole.getId(), dailyModelRole.getRec());
				}
			} else {
				dailyModelRole.setCount(dailyModelRole.getTarget());
				dailyModelRole.setRec(1);
				// 刷新状态
				SendMessage.refreshRecDaily(role, dailyModelRole.getId(), dailyModelRole.getRec());
			}
		}
	}

	/**
	 * 日常任务计数 物品带个数
	 * 
	 * @param dailyId
	 *            日常任务ID
	 * @param role
	 *            角色对象
	 */
	public void finishDailyItem(DailyModelRole dailyModelRole, Role role) {
		int rec = dailyModelRole.getRec();
		// 如果没有领取，加上
		if (dailyModelRole.getRec() != 2) {
			int num = BagServer.getTheItemNum(DailyManager.instance.getMap().get(dailyModelRole.getId()).getItemId(), role);
			dailyModelRole.setCount(num);
			if (dailyModelRole.getCount() >= dailyModelRole.getTarget()) {
				dailyModelRole.setCount(dailyModelRole.getTarget());
				dailyModelRole.setRec(1);
			} else {
				dailyModelRole.setRec(0);
			}
			if (rec != dailyModelRole.getRec()) {
				// 刷新状态
				SendMessage.refreshRecDaily(role, dailyModelRole.getId(), dailyModelRole.getRec());
			}
		}
	}

	/**
	 * 日常任务计数 福地
	 * 
	 * @param dailyId
	 *            日常任务ID
	 * @param role
	 *            角色对象
	 */
	public void finishDailyFudi(DailyModelRole dailyModelRole, Role role, int ceng) {
		if (dailyModelRole.getRec() == 0) {
			dailyModelRole.setCount(ceng);
			if (ceng < dailyModelRole.getTarget()) {
				if (dailyModelRole.getCount() == dailyModelRole.getTarget()) {
					dailyModelRole.setRec(1);
					dailyModelRole.setCount(dailyModelRole.getTarget());
					// 刷新状态
					SendMessage.refreshRecDaily(role, dailyModelRole.getId(), dailyModelRole.getRec());
				} else {
					dailyModelRole.setCount(ceng);
				}
			} else {
				dailyModelRole.setRec(1);
				dailyModelRole.setCount(dailyModelRole.getTarget());
				// 刷新状态
				SendMessage.refreshRecDaily(role, dailyModelRole.getId(), dailyModelRole.getRec());
			}
		}
	}

	/**
	 * @return the map
	 */
	public Map<Integer, DailyModel> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<Integer, DailyModel> map) {
		this.map = map;
	}

	/**
	 * @return the dailyMonsterMap
	 */
	public Map<Long, List<Integer>> getDailyMonsterMap() {
		return dailyMonsterMap;
	}

	/**
	 * @param dailyMonsterMap
	 *            the dailyMonsterMap to set
	 */
	public void setDailyMonsterMap(Map<Long, List<Integer>> dailyMonsterMap) {
		this.dailyMonsterMap = dailyMonsterMap;
	}

	/**
	 * @return the dailyItemMap
	 */
	public Map<Integer, List<Integer>> getDailyItemMap() {
		return dailyItemMap;
	}

	/**
	 * @param dailyItemMap
	 *            the dailyItemMap to set
	 */
	public void setDailyItemMap(Map<Integer, List<Integer>> dailyItemMap) {
		this.dailyItemMap = dailyItemMap;
	}

	/**
	 * @return the dailyFuDiList
	 */
	public List<Integer> getDailyFuDiList() {
		return dailyFuDiList;
	}

	/**
	 * @param dailyFuDiList
	 *            the dailyFuDiList to set
	 */
	public void setDailyFuDiList(List<Integer> dailyFuDiList) {
		this.dailyFuDiList = dailyFuDiList;
	}

	/**
	 * @return the dailyFuBen
	 */
	public List<Integer> getDailyFuBen() {
		return dailyFuBen;
	}

	/**
	 * @param dailyFuBen
	 *            the dailyFuBen to set
	 */
	public void setDailyFuBen(List<Integer> dailyFuBen) {
		this.dailyFuBen = dailyFuBen;
	}

	/**
	 * @return the dailyPay
	 */
	public List<Integer> getDailyPay() {
		return dailyPay;
	}

	/**
	 * @param dailyPay
	 *            the dailyPay to set
	 */
	public void setDailyPay(List<Integer> dailyPay) {
		this.dailyPay = dailyPay;
	}

	/**
	 * @return the dailySpend
	 */
	public List<Integer> getDailySpend() {
		return dailySpend;
	}

	/**
	 * @param dailySpend
	 *            the dailySpend to set
	 */
	public void setDailySpend(List<Integer> dailySpend) {
		this.dailySpend = dailySpend;
	}

}
