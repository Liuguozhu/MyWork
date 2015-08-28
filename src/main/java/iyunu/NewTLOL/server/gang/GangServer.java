package iyunu.NewTLOL.server.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.gang.EGangLog;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.gang.event.EGangActiveEvent;
import iyunu.NewTLOL.model.gang.event.EGangSXLevel;
import iyunu.NewTLOL.model.gang.event.EGangTributeEvent;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.map.Site;
import iyunu.NewTLOL.model.monster.res.MonsterInvasionRes;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

public class GangServer {

	/**
	 * 帮派刷怪
	 * 
	 * @param gang
	 *            帮派对象
	 * @param num
	 *            数量
	 */
	public static void refreshMonster(Gang gang, int num) {
		List<MonsterOnMap> monsterOnMaps = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Site site = ActivityManager.randomSite();
			List<MonsterInvasionRes> list = MonsterJson.instance().getMonsterInvasion(gang.getLevel());
			if(!list.isEmpty()){
				int groupId = list.get(Util.getRandom(list.size())).getGroupId();
				MonsterOnMap monsterOnMap = new MonsterOnMap(gang.getMapGangInfo().getId(), site.getX(), site.getY(), groupId, EMonsterOnMap.invasion);
				gang.getMapGangInfo().getMonsters().put(monsterOnMap.getUid(), monsterOnMap);
				monsterOnMaps.add(monsterOnMap);
			}
		}

		MapMessage.sendMonsterOnMap(gang.getMapGangInfo(), monsterOnMaps);
	}

	/**
	 * 删除帮派上怪物
	 * 
	 * @param gang
	 *            帮派对象
	 */
	public static void removeMonster(Gang gang) {
		gang.getMapGangInfo().removeMonster();
	}

	/**
	 * 帮派战操作
	 * 
	 * @param role
	 *            角色对象
	 * @param gang
	 *            帮派对象
	 * @param gangTributeEvent
	 */
	public static void countGangFight(Role role, Gang gang, EGangTributeEvent gangTributeEvent) {
		RoleServer.addTribute(role, gangTributeEvent.getTribute());
		gang.addActive(EGangActiveEvent.帮战.getActive(), EGangActiveEvent.帮战.name(), role.getNick());
	}

	/**
	 * 烧香所做的操作
	 * 
	 * @param role
	 *            角色对象
	 * @param gang
	 *            帮派
	 * @param gangTributeEvent
	 *            帮派贡献类型
	 */
	public static void count(Role role, Gang gang, EGangTributeEvent gangTributeEvent) {

		role.setShaoXiangNum(role.getShaoXiangNum() + 1); // 消耗一次烧香次数

		RoleServer.addTribute(role, gangTributeEvent.getTribute());
		gang.addTribute(gangTributeEvent.getGangTribute(), role);// 帮派经验
		RoleServer.addShaoXiangExp(role, gangTributeEvent.getExp());
		addLog(gang, role, gangTributeEvent.getGangTribute(), gangTributeEvent); // 帮派日志记录
		// 增加烧香BUFF
		BuffServer.addBuff(role, EGangSXLevel.getEGangSXLevel(role.getShaoXiangLevel()).getGoldBuffId());
		BuffServer.addBuff(role, EGangSXLevel.getEGangSXLevel(role.getShaoXiangLevel()).getExpBuffId());
		// 烧香增加活跃值//已没用
		gang.addActive(EGangActiveEvent.拜神.getActive(), EGangActiveEvent.拜神.name(), role.getNick());
		// 加个人活跃值
		RoleServer.addGangActivity(role, gangTributeEvent.getGangActivity());
	}

	/**
	 * 添加日志
	 * 
	 * @param gang
	 *            帮派对象
	 * @param log
	 *            日志内容
	 */
	public static void addLog(Gang gang, Role role, int num, EGangTributeEvent type) {
		switch (type) {
		case 贡酒:
			gang.addLog(StringControl.yel(role.getNick()), "捐献一次贡酒获得贡献" + StringControl.grn(num + "") + "点", EGangLog.shaoxiang);
			break;
		case 香炉:
			gang.addLog(StringControl.yel(role.getNick()), "捐献一次香炉获得贡献" + StringControl.grn(num + "") + "点", EGangLog.shaoxiang);
			break;
		case 烧鸡:
			gang.addLog(StringControl.yel(role.getNick()), "捐献一次烧鸡获得贡献" + StringControl.grn(num + "") + "点", EGangLog.shaoxiang);
			break;
		default:
			break;
		}
		// if (gang.getGangLog().size() > 20) {
		// gang.getGangLog().remove(0);
		// }
	}

	/**
	 * 加入帮派
	 * 
	 * @param role
	 *            角色对象
	 * @param gang
	 *            帮派对象
	 * @param gangJobTitle
	 *            职位
	 */
	public static void jionGang(Role role, Gang gang, GangJobTitle gangJobTitle) {
		role.setGang(gang);
		role.setGangId(gang.getId());
		role.setTribute(0);
		role.setTotalTribute(0);
		role.setGangActivity(0);
		// role.setJobTitle(gangJobTitle);

		// TODO 检查任务
		TaskServer.finishTaskByType(role, ETaskType.jionGangTask);

		// 刷新角色帮派信息
		GangMessage.sendJoinGang(role);
	}

	/**
	 * 退出帮派
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void quitGang(Role role) {
		role.setGang(null);
		role.setGangId(0l);
		role.setTotalTribute(0);
		role.setTribute(0);
		role.setGangActivity(0);
		role.setLeaveGangTime(System.currentTimeMillis());
		// 刷新角色帮派信息
		GangMessage.sendJoinGang(role);
		// 刷新周围状态
		MapManager.instance().addGangStateQueue(role);
	}

	/**
	 * 帮派升级
	 * 
	 * @param gang
	 *            帮派对象
	 */
	public static void gangUpgrade(Gang gang) {
		boolean ifUpLevel = GangJson.instance().getUpdate(gang.getTotalTribute(), gang.getLevel());
		if (ifUpLevel == true) {
			gang.setLevel(gang.getLevel() + 1);

			if (gang.getLevel() >= 2) {
				String content = "热烈祝贺帮派" + gang.getName() + "升级成" + StringControl.grn(gang.getLevel() + "级") + "帮派，更多福利，更多功能。";
				BulletinManager.instance().addBulletinRock(content, 1);
			}

			GangMessage.refreshGangLevel(gang);
			GangMessage.refreshGangSize(gang);

			if (gang.getLevel() == 2 && GangManager.championId == 0) {
				BulletinManager.instance().addBulletinRock("恭喜" + StringControl.red(gang.getName()) + "帮派率先升到" + StringControl.grn("2级") + "帮派，成为本周" + StringControl.red("冠军帮！"), 1);
				GangManager.instance().changeChampion(gang.getId()); // 转变冠军榜
			}
		}

	}

	/**
	 * 增加帮派经验 用于M4J命令
	 * 
	 * @param tributeNum
	 *            要加的帮贡
	 */
	public static void addTribute(long roleId, int tributeNum) {
		Gang gang = null;
		RoleCard rc = RoleManager.getRoleCardByRoleId(roleId);
		if (rc != null && rc.getGangId() != 0) {
			gang = GangManager.instance().getGang(rc.getGangId());
		}
		if (gang != null) {
			// 加上总帮贡
			gang.setTotalTribute(gang.getTotalTribute() + tributeNum);
			GangMessage.refreshGangExp(gang);
			// 尝试升级帮派
			GangServer.gangUpgrade(gang);
		}
	}

}
