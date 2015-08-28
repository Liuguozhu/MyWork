package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.blood.BloodRes1;
import iyunu.NewTLOL.model.blood.BloodRes2;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BloodProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private List<MonsterOnMap> monsterIds1 = new ArrayList<>();
	private List<MonsterOnMap> monsterIds2 = new ArrayList<>();

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
		// 清除角色状态
		Set<Long> set = BloodManager.instance().getBloodMap().keySet();
		for (Long roleId : set) {
			if (ServerManager.instance().isOnline(roleId)) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				role.setBlood(0);
				SendMessage.sendBlood(role);
				// 加入血战状态改变队列
				MapManager.instance().addBloodQueue(role);
			}
		}
		// 发奖
		BloodManager.instance().sendWard();
		// 清除退出列表
		BloodManager.instance().getQuitList().clear();
		// 删除怪
		deleteMonster(monsterIds1);
		deleteMonster(monsterIds2);
		// monsterIds1.clear();
		// monsterIds2.clear();

	}

	/**
	 * 删除地图上的怪
	 * 
	 * @param list
	 */
	public void deleteMonster(List<MonsterOnMap> list) {
		for (int i = 0; i < list.size(); i++) {
			if (MapManager.instance().getMapById(list.get(i).getMapId()).getMonsters().containsKey(list.get(i).getUid())) {
				MapManager.instance().getMapById(list.get(i).getMapId()).removeMonster(list.get(i).getUid());
			}
		}
	}

	/**
	 * 判断地图上是否守护怪已消失
	 * 
	 * @param list
	 * @return
	 */
	public boolean isEmpty(List<MonsterOnMap> list) {
		for (int i = 0; i < list.size(); i++) {
			if (MapManager.instance().getMapById(list.get(i).getMapId()).getMonsters().containsKey(list.get(i).getUid())) {
				return false;
			}
		}
		return true;
	}

	public void fresh1() {
		List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
		BaseMap baseMap = null;
		for (BloodRes1 b : BloodJson.instance().getBlood1()) {
			baseMap = MapManager.instance().getMapById(b.getMapid());
			MonsterOnMap monsterOnMap = new MonsterOnMap(b.getMapid(), b.getX(), b.getY(), b.getMonster(), EMonsterOnMap.xuezhan);
			monsterIds1.add(monsterOnMap);
			mons.add(monsterOnMap);
			baseMap.addMonsterNoSend(monsterOnMap);
		}
		if (baseMap != null) {
			MapMessage.sendMonsterOnMap(baseMap, mons);
		}
	}

	public void fresh2() {
		List<MonsterOnMap> mons = new ArrayList<MonsterOnMap>();
		BaseMap baseMap = null;
		for (BloodRes2 b : BloodJson.instance().getBlood2()) {
			baseMap = MapManager.instance().getMapById(b.getMapid());
			MonsterOnMap monsterOnMap = new MonsterOnMap(b.getMapid(), b.getX(), b.getY(), b.getMonster(), EMonsterOnMap.xuezhan);
			monsterIds2.add(monsterOnMap);
			mons.add(monsterOnMap);
			baseMap.addMonsterNoSend(monsterOnMap);
		}
		if (baseMap != null) {
			MapMessage.sendMonsterOnMap(baseMap, mons);
		}
	}

	public void run() {

		if (process) {
			List<String> wordList = BloodJson.instance().getBloodWord();
			for (int i = 0; i < wordList.size(); i++) {
				ChatMessage.sendChat(new Chat(EChat.system, 0, "血战", StringControl.yel(wordList.get(i))));
				try {
					Thread.sleep(6 * 100l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ChatMessage.sendChat(new Chat(EChat.system, 0, "血战", StringControl.yel("守护者已降临，让我们快快乐乐的杀戮吧！")));
			fresh1();
			fresh2();
		}

		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;

			if (isEmpty(monsterIds1)) {
				monsterIds1.clear();
				fresh1();
			}
			if (isEmpty(monsterIds2)) {
				monsterIds2.clear();
				fresh2();
			}
			// 睡一分钟，减缓战斗节奏
			try {
				Thread.sleep(60000l);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			spent = System.currentTimeMillis() - startTime;
			if (spent < PERIOD_WAIT) {
				synchronized (this) {
					try {
						wait(PERIOD_WAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		LogManager.info("【血战处理器】已关闭");
	}

	/**
	 * @return the monsterIds1
	 */
	public List<MonsterOnMap> getMonsterIds1() {
		return monsterIds1;
	}

	/**
	 * @param monsterIds1
	 *            the monsterIds1 to set
	 */
	public void setMonsterIds1(List<MonsterOnMap> monsterIds1) {
		this.monsterIds1 = monsterIds1;
	}

	/**
	 * @return the monsterIds2
	 */
	public List<MonsterOnMap> getMonsterIds2() {
		return monsterIds2;
	}

	/**
	 * @param monsterIds2
	 *            the monsterIds2 to set
	 */
	public void setMonsterIds2(List<MonsterOnMap> monsterIds2) {
		this.monsterIds2 = monsterIds2;
	}

}
