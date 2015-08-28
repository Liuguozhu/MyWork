package iyunu.NewTLOL.manager.gang;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.gang.GangTaskModel;
import iyunu.NewTLOL.model.gang.GangTaskSingle;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.redis.RedisGangTask;
import iyunu.NewTLOL.redis.RedisStopTime;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fenghaiyu 帮派任务管理类
 * 
 */
public class GangTaskManager {
	private static GangTaskManager instance = new GangTaskManager();

	public static GangTaskManager instance() {
		return instance;
	}

	private GangTaskManager() {

	}

	/** 帮派任务完成情况 */
	private ConcurrentMap<Long, GangTaskModel> gangTaskMap = new ConcurrentHashMap<>();

	/**
	 * 随机三个全完成的奖励
	 * */
	public int randomAward(long roleId) {
		Map<Integer, MonsterDropItem> allAwardMap = GangJson.instance().getGangTaskAllAward();
		return Util.RANDOM.nextInt(allAwardMap.size()) + 1;
	}

	/**
	 * 根据区间随机一个帮派任务index
	 * 
	 * @param zone
	 *            区间 1 2 3
	 * @return
	 */
	public int randomTask(int zone) {
		List<Integer> list = GangJson.instance().getGangTaskZone().get(zone);
		return list.get(Util.RANDOM.nextInt(list.size()));
	}

	/**
	 * 开服时把gangTaskMap,从redis里读出来,如果没读到，new一个
	 */
	public void load() {
		// 如果停服期间有刷新时间 ，就全刷
		RedisStopTime redisStopTime = Spring.instance().getBean("redisStopTime", RedisStopTime.class);
		long stopTime = redisStopTime.getStopTime();
		if (Time.getIfOverTime(stopTime, 0, 6, 12, 18)) {
			gangTaskMap = new ConcurrentHashMap<Long, GangTaskModel>();
			return;
		}

		RedisGangTask redisGangTask = Spring.instance().getBean("redisGangTask", RedisGangTask.class);
		gangTaskMap = redisGangTask.getGangTask();
		if (gangTaskMap == null) {
			gangTaskMap = new ConcurrentHashMap<Long, GangTaskModel>();
		}
	}

	/**
	 * 刷新请求
	 * 
	 * @param roleId
	 */
	public GangTaskModel initRoleGangTask(long roleId) {
		List<GangTaskSingle> list = new ArrayList<>();
		GangTaskModel gangTaskModel = new GangTaskModel();
		for (int i = 1; i < 4; i++) {
			GangTaskSingle gangTask = new GangTaskSingle();
			// 在1，2，3区间，各刷新一个任务
			int index = randomTask(i);
			gangTask.setIndex(index);
			gangTask.setFinish(0);
			list.add(gangTask);
		}
		gangTaskModel.setTaskList(list);
		gangTaskModel.setAwardIndex(randomAward(roleId));
		gangTaskMap.put(roleId, gangTaskModel);
		return gangTaskModel;
	}

	/**
	 * 全部刷新请求
	 */
	public void initRoleGangTaskAll() {
		// 发生全部刷新时，清空MAP，当前在线的有效玩家进行刷新，
		// 不在线的玩家再次上线后请求时会刷新，保持map不过分大
		Set<Long> haveSet = gangTaskMap.keySet();
		Set<Long> newSet = new HashSet<>();
		for (Long roleId : haveSet) {
			if (ServerManager.instance().isOnline(roleId)) {
				newSet.add(roleId);
			}
		}
		gangTaskMap.clear();
		for (Long roleId : newSet) {
			initRoleGangTask(roleId);
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			if (role != null) {
				GangMessage.refreshGangTaskList(role);
			}
		}
	}

	/**
	 * @return the gangTaskMap
	 */
	public ConcurrentMap<Long, GangTaskModel> getGangTaskMap() {
		return gangTaskMap;
	}

	/**
	 * @param gangTaskMap
	 *            the gangTaskMap to set
	 */
	public void setGangTaskMap(ConcurrentMap<Long, GangTaskModel> gangTaskMap) {
		this.gangTaskMap = gangTaskMap;
	}

}