package iyunu.NewTLOL.base.mx4j;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.RankingManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.redis.RedisCenter;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

	public static void excute(String command) {
		String[] strs = command.split(" ");

		String title = strs[0];

		switch (title) {
		case "kick": // 踢人 [kick 角色编号]
			if (strs.length == 2) {
				ServerManager.instance().kickOffline(Translate.stringToLong(strs[1]));
			}
			break;
		case "kickAll": // 全部踢人 [kickAll]
			if (strs.length == 1) {
				ServerManager.instance().kickAll();
			}
			break;

		case "addGold": // 增加绑银 [addGold 角色编号 绑银数量]
			if (strs.length == 3 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
				RoleServer.addGold(role, Translate.stringToInt(strs[2]), EGold.sys);
			}
			break;
		case "addCoin": // 增加银两 [addCoin 角色编号 绑银数量]
			if (strs.length == 3 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
				RoleServer.addCoin(role, Translate.stringToInt(strs[2]), EGold.sys);
			}
			break;
		case "addExp": // 增加经验 [addExp 角色编号 经验值]
			if (strs.length == 3 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
				RoleServer.addExp(role, Translate.stringToInt(strs[2]), EExp.sys);
			}
			break;
		case "addWuhun": // 增加武魂 [addWuhun 角色编号 武魂]
			if (strs.length == 3 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
				RoleServer.addWuhun(role, Translate.stringToInt(strs[2]));
				SendMessage.sendJuhun(role);
			}
			break;
		case "addItem": // 增加物品 [addItem 角色编号 物品编号 数量 是否绑定（0.不绑定，1.绑定）]
			if (strs.length >= 4 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
				Item item = ItemJson.instance().getItem(Translate.stringToInt(strs[2]));

				if (item != null) {
					if (strs.length == 5) {
						item.setIsDeal(Translate.stringToInt(strs[4]));
					} else {
						item.setIsDeal(1); // 发送物品不可交易
					}

					Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
					// role.getBag().add(item, Translate.stringToInt(strs[3]),
					// cellsMap, EItemGet.sys);
					BagServer.add(role, item, Translate.stringToInt(strs[3]), cellsMap, EItemGet.sys);
					BagMessage.sendBag(role, cellsMap); // 刷新背包
					LogManager.info("发送物品【" + item.getName() + "】数量：" + Translate.stringToInt(strs[3]));
				} else {
					LogManager.info("发送物品失败【" + Translate.stringToInt(strs[2]) + "】");
				}
			}
			break;
		case "addTask": // 增加任务 [addTask 角色编号 任务编号]
			if (strs.length == 3 && ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
				try {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					BaseTask ba = TaskJson.instance().getTask(Translate.stringToInt(strs[2]));
					TaskServer.getTask(ba, role);

					role.getTasks().put(Translate.stringToInt(strs[2]), ba);
					TaskMessage.sendTask(role);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		case "mute": // 禁言 [mute 角色编号 禁言时间]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					RoleServer.addMute(role, Translate.stringToInt(strs[2]));
				} else {
					RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);
					roleService.addMute(Translate.stringToLong(strs[1]), Translate.stringToInt(strs[2]));
				}
			}

			break;
		case "addPartner": // 发送伙伴 [addPartner 角色编号 伙伴索引]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));

					RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
					long partnerId = redisCenter.getPartnerId();
					Partner newPartner = PartnerJson.instance().getNewPartner(Translate.stringToInt(strs[2]));
					PartnerServer.countPotential(newPartner); // 计算资质
					newPartner.setId(partnerId);
					newPartner.setRole(role);
					newPartner.setHp(newPartner.getHpMax());
					role.getPartnerMap().put(partnerId, newPartner);
					List<Partner> partnerList = new ArrayList<>();
					newPartner.setOperateFlag(EpartnerOperate.add);
					partnerList.add(newPartner);
					PartnerMessage.sendPartners(role, partnerList);
				}
			}

			break;
		case "addTribute": // 加帮贡 [addTribute 角色编号 帮贡值]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					RoleServer.addTribute(role, Translate.stringToInt(strs[2]));
				}
			}
			break;
		case "addGangTribute": // 加帮贡，给帮派加贡，但参数要填角色ID [addTribute 角色编号 帮贡值]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					GangServer.addTribute(Translate.stringToLong(strs[1]), Translate.stringToInt(strs[2]));
				}
			}
			break;
		case "resetRaids": // 重置副本次数 [resetRaids 角色编号 副本编号]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					RoleServer.addTribute(role, Translate.stringToInt(strs[2]));
				}
			}
			break;
		case "transmit": // 传送 [transmit 角色编号 地图编号 x坐标 y坐标]
			if (strs.length == 5) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					int mapId = Translate.stringToInt(strs[2]);
					int x = Translate.stringToInt(strs[3]);
					int y = Translate.stringToInt(strs[4]);
					MapServer.transmit(role, mapId, x, y);
				}
			}
			break;
		case "resetTrials": // 重置试炼次数 [resetTrials 角色编号 副本编号]
			break;
		case "finishTask": // 完成任务 [finishTask 角色编号 任务编号]
			if (strs.length == 3) {
				if (ServerManager.instance().isOnline(Translate.stringToLong(strs[1]))) {
					Role role = ServerManager.instance().getOnlinePlayer(Translate.stringToLong(strs[1]));
					if (role.getTasks().containsKey(Translate.stringToInt(strs[2]))) {
						BaseTask baseTask = role.getTasks().get(Translate.stringToInt(strs[2]));

						baseTask.setState(ETaskState.finish);
						// ======刷新任务信息======
						TaskMessage.sendTask(role);
					}
				}
			}
			break;
		case "showOnline":// 显示当前在线玩家信息
			for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				LogManager.info(role.getId() + "=====>>" + role.getNick() + "=====>>" + role.getLevel());
			}
			break;
		case "cancelFbl":// 传送 [cancelFbl 角色编号]
			if (strs.length == 2) {
				ActivityManager.cancelActivityFbl(Translate.stringToLong(strs[1]));
			}
			break;

		case "printRanking":// 打印排行榜 [printRanking]
			RankingManager.printRanking();
			break;

		default:
			break;
		}
	}
}
