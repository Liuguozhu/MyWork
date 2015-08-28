package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;

public class EmoMap2ProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
		// 删除怪
		BaseMap baseMap = MapManager.instance().getMapById(MiningJson.instance().getEmoMapInfoRes().getEmo2Map());
		Iterator<MonsterOnMap> it = baseMap.getMonsters().values().iterator();
		while (it.hasNext()) {
			MonsterOnMap m = it.next();
			MapMessage.removeMonsterOnMap(baseMap, m.getUid());
			it.remove();
		}
		EmoMapManager.instance().getMonsterIds().clear();
		EmoMapManager.instance().getHasKillMonsterIds().clear();
		for (Long roleId : baseMap.allInMap()) {
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			if (role != null) {
				// 踢出地图
				if (role.getTeam() != null) {
					MapServer.changeMap(role.getTeam(), RoleManager.BASE_X, RoleManager.BASE_Y, MapManager.instance().getMapById(RoleManager.BASE_MAP), baseMap);
				} else {
					MapServer.changeMap(role, RoleManager.BASE_X, RoleManager.BASE_Y, MapManager.instance().getMapById(RoleManager.BASE_MAP), baseMap);
				}
			}
		}
	}

	public void run() {

		if (process) {
			ChatMessage.sendChat(new Chat(EChat.system, 0, "恶魔岛—幻影谷", StringControl.yel("恶魔已降临，让我们快快乐乐的杀戮吧！")));
			EmoMapManager.instance().fresh2(1);
		}

		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;
			if (process) {
				EmoMapManager.instance().fresh2(0);
			}
			try {
				Thread.sleep(4 * 1000 * 60);
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
		LogManager.info("【恶魔岛—幻影谷处理器】已关闭");
	}

}
