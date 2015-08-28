package iyunu.NewTLOL.server.role;

import iyunu.NewTLOL.common.RoleForm;
import iyunu.NewTLOL.enumeration.EFigure;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.JingMaiJson;
import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ActivityMessage;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.message.HelperMessage;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.message.TrialsMessage;
import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.bag.Bag;
import iyunu.NewTLOL.model.bag.BagStone;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.bag.Warehouse;
import iyunu.NewTLOL.model.billboard.level.LevelBoard;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.gang.event.EGangSXLevel;
import iyunu.NewTLOL.model.gift.instance.LevelGift;
import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.item.instance.Stone;
import iyunu.NewTLOL.model.jingMai.instance.JingMai;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.MapInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.model.role.res.FigureRes;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.award.TipServer;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.base.ExpServer;
import iyunu.NewTLOL.server.base.GoldServer;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * role对象信息服务类 处理非IO数据
 * 
 * @author SunHonglei
 * 
 */
public final class RoleServer {

	/**
	 * 私有构造方法
	 */
	private RoleServer() {

	}

	/**
	 * 发送角色上线消息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendLogonMsg(Role role) {

		// ======发送角色信息======
		SendMessage.sendRole(role);
		// ======刷新新手引导======
		SendMessage.refreshGuide(role);
		// ======刷新银两======
		SendMessage.sendCoin(role);
		// ======刷新绑银使用上限======
		SendMessage.sendGoldLimit(role);
		// ======刷新今日已使用绑银数量======
		SendMessage.sendGoldNum(role);
		// ======刷新地图======
		MapMessage.sendMap(role, role.getMapInfo().getBaseMap());
		// ======刷新任务信息======
		TaskMessage.sendTask(role);
		// ======刷新背包======
		BagMessage.logonSendBag(role);
		// ======刷新宝石背包======
		BagMessage.sendStoneBag(role);
		// ======刷新装备======
		BagMessage.sendEquip(role);
		// ======刷新伙伴======

		List<Partner> partnerList = new ArrayList<>();
		for (Partner partner : role.getPartnerMap().values()) {
			partner.setOperateFlag(EpartnerOperate.add);
			partnerList.add(partner);
		}

		PartnerMessage.sendPartners(role, partnerList);
		// ======检查未读邮件======
		if (MailServer.hasNoRead(role)) {
			SendMessage.sendNewMail(role);
		}
		// ======发送角色技能======
		SendMessage.sendSkill(role);
		// ======发送部位强化======
		SendMessage.sendBodyIntensify(role);
		// ======发送部位镶嵌======
		SendMessage.sendBodyRabbet(role);
		// ==========发送聚魂=========
		// SendMessage.sendJuhun(role);
		// ======发送元气值======
		SendMessage.sendYuanQi(role);
		// ======发送充值比例======
		SendMessage.sendPayRatio(role);
		// ======刷新仓库======
		BagMessage.sendWarehouse(role);
		// ======发送剩余屏蔽怪时间======
		SendMessage.sendCloaking(role);
		// ======发送答题积分======
		SendMessage.sendScore(role);
		// ======发送玩家等级排行榜位置======
		SendMessage.sendRankingLevel(role, LevelBoard.INSTANCE.getRank(role.getId()));
		// ======发送VIP======
		SendMessage.sendRefreshVip(role);
		// ======刷新BUFF协议======
		SendMessage.sendNewBuff(role);
		// ======刷首充领奖状态======
		AwardMessage.sendPayFristState(role);
		// ======刷新小助手======
		// SendMessage.refreshHelper(role);
		// ======刷新活跃度======
		HelperMessage.refreshHelperAward(role);
		// ======刷新门派任务数量======
		TaskMessage.refreshGuildTaskNum(role);
		// ======刷新江湖追杀令任务数量======
		TaskMessage.refreshGhostTaskNum(role);
		// ======刷新寻宝次数======
		// SendMessage.refreshHuntTreasureNum(role);
		// ===========刷新血战信息==============
		SendMessage.sendBlood(role);
		// ======刷新新服活动数======
		ActivityMessage.refreshActivityNew(role);
		// ======刷新每日优惠活动数======
		ActivityMessage.refreshActivityPay(role);
		// ========刷新开服七天图标=======================
		SendMessage.refreshSevenEnd(role, Time.getDaysBetween(OperationManager.OPEN_FU) + 1);
		// =============刷新招伙伴标记以及时间======================
		PartnerMessage.sendPartnerRefresh(role);
		// ===================刷新VIP奖励标记====================
		AwardMessage.sendVipGift(role);
		// ===================刷新聊天发送VIP开关====================
		SendMessage.sendOpenVipChat(role);
		// ==============上线刷新帮派等级=====================
		if (role.getGangId() != 0 && GangManager.instance().getGang(role.getGangId()) != null) {
			GangMessage.refreshGangLevel2(role, GangManager.instance().getGang(role.getGangId()).getLevel());
		} else {
			GangMessage.refreshGangLevel2(role, 0);
		}
		// 刷新战力
		SendMessage.sendPower(role);
		// 刷新仓库银两
		SendMessage.refreshWareHouseCoin(role);
		// 刷新试炼状态
		TrialsMessage.refreshTrialsState(role);
		// 刷新试炼重置次数
		TrialsMessage.refreshTrialsReSetNum(role);
		// 刷新每日活动
		HelperMessage.refreshDailyInfo(role);
		// 刷新已领的双倍充值
		SendMessage.refreshFirstDouble(role);
		// 刷新环任务数量
		TaskMessage.refreshTaskCycleNum(role);
		// 刷新累计充值金额
		SendMessage.refreshPlusMoney(role);
		// 刷新积分榜开关
		if (PayExchangeManager.FLAG) {
			SendMessage.refreshPayExchange(role, 1);
		} else {
			SendMessage.refreshPayExchange(role, 0);
		}

		// 上线检查日常任务
		DailyManager.instance().compareDaily(role);
		// 上线检查日常任务图标
		if (role.getDailyMap().size() < 1) {
			SendMessage.refreshDailyTask(role, 0);
		} else {
			SendMessage.refreshDailyTask(role, 1);
		}

		// 上线检查充值累计开关
		if (ActivityJson.instance().getPlusMoney().size() == role.getRecPlusMoney().size()) {
			SendMessage.refreshPlusMoneyFlag(role, 1);
		} else {
			SendMessage.refreshPlusMoneyFlag(role, 0);
		}
		// 刷新活力值
		SendMessage.refreshEnergy(role);
	}

	/**
	 * 创建新角色
	 * 
	 * @param roleId
	 *            角色编号
	 * @param nick
	 *            角色名称
	 * @param figure
	 *            形象编号
	 * @param serverId
	 *            服务器编号
	 * @param userId
	 *            用户编号
	 * @return 角色对象
	 */
	public static Role newRole(String nick, long figure, int serverId, String userId, int guildId) {

		// ======根据不同形象分配初始四维======
		FigureRes figureRes = RoleJson.instance().getFigure(figure);
		if (figureRes == null) {
			return null;
		}

		Role role = new Role();
		role.setNick(nick); // 角色名称
		role.setFigure(figure); // 角色形象编号
		role.setFigureRes(figureRes);
		role.setServerId(serverId);
		role.setUserId(userId);
		role.setDate(new Date()); // 角色创建时间
		role.setLevel(1); // 初始等级
		role.setExp(0); // 初始经验值
		role.setGold(100000); // 初始绑银
		role.setCoin(0); // 初始银两
		role.setMoney(0); // 初始元宝
		role.setVocation(Vocation.none); // 初始职业
		role.setMapInfo(new MapInfo());
		role.getMapInfo().setMapId(RoleManager.DEFULT_MAP); // 初始地图编号
		role.getMapInfo().setX(RoleManager.DEFULT_X); // 初始X坐标
		role.getMapInfo().setY(RoleManager.DEFULT_Y); // 初始Y坐标
		role.setYxtTaskNum(0);
		role.setStrength(figureRes.getStrength()); // 初始力量
		role.setIntellect(figureRes.getIntellect()); // 初始智力
		role.setPhysique(figureRes.getPhysique()); // 初始体力
		role.setAgility(figureRes.getAgility()); // 初始敏捷
		// role.setHit(figureRes.getHit()); // 初始命中
		// role.setCrit(figureRes.getCrit()); // 初始暴击
		// role.setDodge(figureRes.getDodge()); // 初始闪避
		// role.setParry(figureRes.getParry()); // 初始格挡
		// role.setSpeed(figureRes.getSpeed()); // 初始速度

		role.setFree(RoleManager.DEFULT_FREE); // 初始自由点数

		role.setTitle(null); // 初始称号索引
		// role.setTittleStr("");

		// ======初始化门派======
		role.setVocation(Vocation.values()[guildId]);
		// ======初始化技能======
		role.setSumSkill(1); // 增加技能点数
		List<RoleSkill> skillList = SkillJson.instance().getRoleDefultSkills(role.getVocation());
		for (RoleSkill roleSkill : skillList) {
			if (roleSkill.getPosition() == 1) { // 门派默认添加技能
				RoleSkill nextRoleSkill = SkillJson.instance().getRoleSkillById(roleSkill.getNextSkill());
				role.getSkillMap().put(roleSkill.getPosition(), nextRoleSkill.getId());
			} else {
				role.getSkillMap().put(roleSkill.getPosition(), roleSkill.getId());
			}
		}

		// ======初始化装备======
		Equip shoe = (Equip) ItemJson.instance().getItem(10046);
		shoe.setIsDeal(1);
		role.getEquipments().put(shoe.getPart(), shoe);
		Equip belt = (Equip) ItemJson.instance().getItem(10061);
		belt.setIsDeal(1);
		role.getEquipments().put(belt.getPart(), belt);
		Equip ring = (Equip) ItemJson.instance().getItem(10076);
		ring.setIsDeal(1);
		role.getEquipments().put(ring.getPart(), ring);
		Equip necklace = (Equip) ItemJson.instance().getItem(10091);
		necklace.setIsDeal(1);
		role.getEquipments().put(necklace.getPart(), necklace);

		// ======初始化背包======
		Bag bag = new Bag(Bag.DEFAULT_CELLS);
		bag.addRole(role);
		role.setBag(bag);

		// ======初始化宝石背包======
		BagStone bagStone = new BagStone(BagStone.DEFAULT_CELLS);
		bagStone.addRole(role);
		Item item = ItemJson.instance().getItem(30051);
		item.setIsDeal(1);
		bagStone.add(item, 2, new HashMap<Integer, Cell>(), EItemGet.createRole);
		role.setBagStone(bagStone);

		// ======初始化仓库======
		role.setWarehouse(new Warehouse(Warehouse.DEFAULT_CELLS));

		// ======初始化部位强化、镶嵌======
		for (EEquip part : EEquip.values()) {
			role.getBodyIntensify().put(part, 0); // 部位强化

			HashMap<Integer, Rabbet> rabbetMap = new HashMap<Integer, Rabbet>();
			for (int i = 0; i < 6; i++) {
				Rabbet rabbet = new Rabbet();
				if (i == 0) {
					rabbet.setOpen(1);
				}
				rabbetMap.put(i, rabbet);
			}
			role.getBodyRabbet().put(part, rabbetMap); // 部位镶嵌
		}

		RoleServer.countAllProperty(role);
		RoleServer.fullHpAndMp(role);

		// ======添加新人任务======
		BaseTask task = TaskJson.instance().getTask(1);
		task.setState(ETaskState.finish);
		role.getTasks().put(task.getId(), task);

		// ======添加客栈伙伴======
		role.setInnTime(0);
		role.setInnTime2(0);
		// ======初始化出战伙伴======
		role.getPartnerFight().put(1, 0l);
		role.getPartnerFight().put(2, 0l);
		role.getPartnerFight().put(3, 0l);
		role.getPartnerFight().put(4, 0l);
		role.getPartnerFight().put(5, 0l);

		// ======初始化等级礼包领取状态======
		role.getLevelGiftStateMap().clear();
		List<LevelGift> giftList = GiftJson.instance().getLevelGiftList();
		for (LevelGift levelGift : giftList) {
			role.getLevelGiftStateMap().put(levelGift.getLevel(), 0);
		}

		// ======烧香======
		role.setShaoXiangLevel(1); // 烧香初始等级1级

		// ======初始化活跃度积分跟积分状态======
		role.setLivenessScore(0);
		role.getLivenessScoreMap().clear();
		List<HelperAward> helperAwards = HelperJson.instance().getHelperAwards();

		for (HelperAward helperAward : helperAwards) {
			role.getLivenessScoreMap().put(helperAward.getScore(), 0);
		}

		// ======初始化在线奖励领取状态======
		role.getOnlineAwardStateMap().clear();
		List<OnlineAwardInfo> onlineAwardInfoList = ActivityJson.instance().getActivityOnlineList();
		for (OnlineAwardInfo onlineAwardInfo : onlineAwardInfoList) {
			role.getOnlineAwardStateMap().put(onlineAwardInfo.getId(), 0);
		}

		// ======初始化活力刷新时间======
		role.setEnergyTime(System.currentTimeMillis());

		// ======初始化物品======
		if (figureRes.getFigure().equals(EFigure.knife)) {
			BagServer.add(role, ItemJson.instance().getItem(10001).bind(), 1, new HashMap<Integer, Cell>(), EItemGet.createRole);
		} else if (figureRes.getFigure().equals(EFigure.sword)) {
			BagServer.add(role, ItemJson.instance().getItem(10016).bind(), 1, new HashMap<Integer, Cell>(), EItemGet.createRole);
		} else if (figureRes.getFigure().equals(EFigure.staff)) {
			BagServer.add(role, ItemJson.instance().getItem(10211).bind(), 1, new HashMap<Integer, Cell>(), EItemGet.createRole);
		}

		return role;
	}

	/**
	 * 增加技能点数
	 * 
	 * @param role
	 *            角色对象
	 * @param skill
	 *            技能点数
	 */
	public static void addSkill(Role role, int skill) {
		if (skill > 0) {
			role.setFreeSkill(role.getFreeSkill() + skill);
			role.setSumSkill(role.getSumSkill() + skill);
			SendMessage.sendRefreshFree(role);
			UptipEvent.技能点.check(role, role.getUptipBoolean(UptipEvent.技能点.getOrdinal()));
		}
	}

	/**
	 * 增加绑银
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            增加银两数量
	 * @param goldType
	 *            类型
	 */
	public static void addGold(Role role, int gold, EGold goldType) {
		if (gold > 0 && role.getGold() < RoleManager.MAX_GOLD) {

			int additionGold = 0;
			switch (goldType) {
			case monster:
				additionGold += GoldServer.additionSystem(role, gold);
				additionGold += GoldServer.additionBuff(role, gold);
				break;
			default:
				break;
			}
			gold += additionGold;

			role.setGold(role.getGold() + gold);
			if (role.getGold() > RoleManager.MAX_GOLD) {
				gold -= role.getGold() - RoleManager.MAX_GOLD;
				role.setGold(RoleManager.MAX_GOLD);
			}
			SendMessage.sendGold(role); // 刷新银两

			AwardServer.addGold(role, gold); // 发送通知

			LogManager.costGold(role, goldType.ordinal(), gold, 1);
		}
	}

	public static void addGoldWithOutNotice(Role role, int gold, EGold goldType) {
		if (gold > 0 && role.getGold() < RoleManager.MAX_GOLD) {

			int additionGold = 0;
			switch (goldType) {
			case monster:
				additionGold += GoldServer.additionSystem(role, gold);
				additionGold += GoldServer.additionBuff(role, gold);
				break;
			default:
				break;
			}
			gold += additionGold;

			role.setGold(role.getGold() + gold);
			if (role.getGold() > RoleManager.MAX_GOLD) {
				gold -= role.getGold() - RoleManager.MAX_GOLD;
				role.setGold(RoleManager.MAX_GOLD);
			}
			SendMessage.sendGold(role); // 刷新银两
			LogManager.costGold(role, goldType.ordinal(), gold, 1);
		}
	}

	/**
	 * 是否可以消耗绑银
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            消耗的绑银数量
	 * @return 可以消耗
	 */
	public static boolean isCanCostGold(Role role, int gold) {
		int costGold = 0;
		int costCoin = 0;
		if (role.getCostGold() + gold > RoleManager.MAX_COST_GOLD_EVERYDAY) {
			costGold = gold - (RoleManager.MAX_COST_GOLD_EVERYDAY - role.getCostGold());
			costCoin = gold - costGold;
		} else {
			costGold = gold;
		}

		if (role.getGold() < costGold) {
			costGold = role.getGold();
			costCoin = gold - costGold;
		}

		if (costCoin > role.getCoin()) {
			return false;
		}
		return true;
	}

	/**
	 * 只消耗绑银
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            消耗的绑银
	 * @param goldType
	 *            消耗类型
	 * @param isEveryDay
	 *            是否记入每日限制
	 * @return 消耗成功
	 */
	public static boolean costGoldOnly(Role role, int gold, EGold goldType, boolean isEveryDay) {
		if (gold <= 0) {
			return false;
		}
		if (role.getGold() < gold) {
			return false;
		}
		if (isEveryDay && role.getCostGold() + gold > RoleManager.MAX_COST_GOLD_EVERYDAY) {
			return false;
		}

		role.setGold(role.getGold() - gold);
		TipServer.costGold(role, gold); // =======通知======
		SendMessage.sendGold(role); // 刷新银两
		if (isEveryDay) {
			SendMessage.sendGoldNum(role); // 刷新今日已使用绑银数量
		}
		LogManager.costGold(role, goldType.ordinal(), gold, 0);
		return true;
	}

	/**
	 * 消耗绑银
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            消耗银两数量
	 * @return 消耗银两成功
	 */
	public static boolean costGold(Role role, int gold, EGold goldType) {
		return costGold(role, gold, goldType, true);
	}

	/**
	 * 消耗绑银（绑银不足，消耗银两）
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            消耗银两数量
	 * @return 消耗银两成功
	 */
	public static boolean costGold(Role role, int gold, EGold goldType, boolean isEveryDay) {
		if (gold <= 0) {
			return false;
		}
		int costGold = 0;
		int costCoin = 0;

		if (role.getCostGold() <= RoleManager.MAX_COST_GOLD_EVERYDAY) {
			if (role.getCostGold() + gold > RoleManager.MAX_COST_GOLD_EVERYDAY) {
				costGold = (RoleManager.MAX_COST_GOLD_EVERYDAY - role.getCostGold());
				costCoin = gold - costGold;
			} else {
				costGold = gold;
			}
		} else {
			costCoin = gold;
		}

		if (role.getGold() < costGold) {
			costGold = role.getGold();
			costCoin = gold - costGold;
		}

		if (costCoin > role.getCoin()) {
			return false;
		}

		role.setGold(role.getGold() - costGold);
		TipServer.costGold(role, costGold); // =======通知======
		role.setCostGold(role.getCostGold() + costGold);

		SendMessage.sendGold(role); // 刷新绑银
		SendMessage.sendGoldNum(role); // 刷新已使用绑银数量
		LogManager.costGold(role, goldType.ordinal(), gold, 0);
		if (costCoin > 0) {
			costCoin(role, costCoin, goldType); // 消耗银两
		}
		return true;
	}

	/**
	 * 增加银两
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            增加银两数量
	 * @param coinType
	 *            类型
	 */
	public static void addCoin(Role role, int coin, EGold coinType) {
		if (coin > 0 && role.getCoin() < RoleManager.MAX_COIN) {

			role.setCoin(role.getCoin() + coin);
			if (role.getCoin() > RoleManager.MAX_COIN) {
				coin -= role.getCoin() - RoleManager.MAX_COIN;
				role.setCoin(RoleManager.MAX_COIN);
			}
			SendMessage.sendCoin(role); // 刷新银两
			AwardServer.addCoin(role, coin); // 发送通知
		}
		LogManager.coin(role, coinType.ordinal(), coin, 1);
	}

	/**
	 * 消耗银两
	 * 
	 * @param role
	 *            角色对象
	 * @param coin
	 *            消耗银两数量
	 * @return 消耗银两成功
	 */
	public static boolean costCoin(Role role, int coin, EGold coinType) {
		if (role.getCoin() < coin || coin <= 0) {
			return false;
		}
		role.setCoin(role.getCoin() - coin);
		TipServer.costCoin(role, coin); // ======提示======
		SendMessage.sendCoin(role); // 刷新银两
		// RankEvent.GoldEvent.handleEvent(role.toCard());
		LogManager.coin(role, coinType.ordinal(), coin, 0);
		return true;
	}

	/**
	 * 添加元气值
	 * 
	 * @param role
	 *            角色对象
	 * @param yuanQi
	 *            增加元气值
	 */
	public static void addYuanQi(Role role, int yuanQi) {
		if (yuanQi > 0 && role.getYuanQi() < RoleManager.MAX_YUANQI) {
			role.setYuanQi(role.getYuanQi() + yuanQi);
			SendMessage.sendYuanQi(role);
			AwardServer.addYuanqi(role, yuanQi); // 发送通知
			UptipEvent.元气值.check(role, role.getUptipBoolean(UptipEvent.元气值.getOrdinal()));
		}
	}

	/**
	 * 消耗元气值
	 * 
	 * @param role
	 *            角色对象
	 * @param yuanQi
	 *            消耗元气值
	 * @return 消耗元气成功
	 */
	public static boolean costYuanQi(Role role, int yuanQi) {
		if (role.getYuanQi() < yuanQi || yuanQi <= 0) {
			return false;
		}
		role.setYuanQi(role.getYuanQi() - yuanQi);
		TipServer.costYuanqi(role, yuanQi); // ======提示======
		SendMessage.sendYuanQi(role);
		UptipEvent.元气值.check(role, role.getUptipBoolean(UptipEvent.元气值.getOrdinal()));
		return true;
	}

	/**
	 * 增加元宝
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            增加元宝数量
	 */
	public static void addMoney(Role role, int money, EMoney emoney) {
		if (money > 0) {
			role.setMoney(role.getMoney() + money);
			// 刷新元宝
			SendMessage.sendMoney(role);
			AwardServer.addMoney(role, money); // 发送通知
			LogManager.money(role, emoney.ordinal(), money, 1);
		}
	}

	/**
	 * 消耗元宝
	 * 
	 * @param role
	 *            角色对象
	 * @param gold
	 *            消耗元宝数量
	 * @return 消耗元宝成功
	 */
	public static boolean costMoney(Role role, int money, EMoney emoney) {
		if (role.getMoney() < money || money <= 0) {
			return false;
		}
		// ==========日常活动之花费=========
		try {
			List<Integer> dailyPay = DailyManager.instance().getDailySpend();
			for (Integer dailyId : dailyPay) {
				if (DailyManager.instance().checkEvent(dailyId)) {
					DailyManager.instance().finishDaily(role.getDailyMap().get(dailyId), role, money);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		role.setMoney(role.getMoney() - money);
		TipServer.costMoney(role, money); // ======提示======
		// 刷新元宝
		SendMessage.sendMoney(role);
		LogManager.money(role, emoney.ordinal(), money, 0);
		return true;
	}

	/**
	 * 增加武魂
	 * 
	 * @param role
	 *            角色对象
	 * @param wuhun
	 *            武魂增加数量
	 */
	public static void addWuhun(Role role, int wuhun) {
		if (wuhun > 0) {
			role.setWuHun(role.getWuHun() + wuhun);
			AwardServer.addWuhun(role, wuhun); // 发送通知
		}
	}

	/**
	 * 重置技能
	 * 
	 * @param role
	 *            角色编号
	 */
	public static void resetSkill(Role role) {
		List<RoleSkill> list = SkillJson.instance().getRoleDefultSkills(role.getVocation());
		for (RoleSkill roleSkill : list) {
			role.getSkillMap().put(roleSkill.getPosition(), roleSkill.getId());
		}
	}

	/**
	 * 检查技能
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkSkill(Role role) {
		if (!role.getVocation().equals(Vocation.none) && role.getSkillMap().isEmpty()) {
			resetSkill(role);
		}
	}

	/**
	 * 检查配件
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkBodyRabbet(Role role) {
		if (role.getBodyRabbet().isEmpty()) {
			for (EEquip part : EEquip.values()) {
				HashMap<Integer, Rabbet> rabbetMap = new HashMap<Integer, Rabbet>();
				for (int i = 0; i < 6; i++) {
					Rabbet rabbet = new Rabbet();
					if (i == 0) {
						rabbet.setOpen(1);
					}
					rabbetMap.put(i, rabbet);
				}
				role.getBodyRabbet().put(part, rabbetMap); // 部位镶嵌
			}
		}
	}

	/**
	 * 自动恢复生命值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void autoRecoverHp(Role role) {
		BuffRole buffRole = role.getBuffs().get(EBuffType.hp);
		if (buffRole != null) {
			int hp = buffRole.getValue();
			if (hp > 0) {
				int need = role.getHpMax() - role.getHp();
				if (need > 0) {
					if (need > hp) {
						role.setHp(role.getHp() + hp);
						BuffServer.removeBuff(role, EBuffType.hp);
					} else {
						role.setHp(role.getHpMax());
						buffRole.setValue(hp - need);
						// ======刷新BUFF协议======
						SendMessage.sendNewBuff(role);
					}
				}
			} else {
				BuffServer.removeBuff(role, EBuffType.hp);
			}
		}
	}

	/**
	 * 自动恢复内力值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void autoRecoverMp(Role role) {
		BuffRole buffRole = role.getBuffs().get(EBuffType.mp);
		if (buffRole != null) {
			int mp = buffRole.getValue();
			if (mp > 0) {
				int need = role.getMpMax() - role.getMp();
				if (need > 0) {
					if (need > mp) {
						role.setMp(role.getMp() + mp);
						BuffServer.removeBuff(role, EBuffType.mp);
					} else {
						role.setMp(role.getMpMax());
						buffRole.setValue(mp - need);
						// ======刷新BUFF协议======
						SendMessage.sendNewBuff(role);
					}
				}
			} else {
				BuffServer.removeBuff(role, EBuffType.mp);
			}
		}
	}

	/**
	 * 角色属性重置
	 * 
	 * @param role
	 *            角色对象
	 * @return 重置成功
	 */
	public static boolean reset(Role role) {

		// ======根据不同形象分配初始四维======
		FigureRes figureRes = RoleJson.instance().getFigure(role.getFigure());
		if (figureRes == null) {
			return false;
		}

		int level = role.getLevel();
		int free = (level - 1) * RoleManager.UPGRADE_FREE;
		int strength = (level - 1) * RoleManager.UPGRADE_STRENGTH;
		int intellect = (level - 1) * RoleManager.UPGRADE_INTELLECT;
		int physique = (level - 1) * RoleManager.UPGRADE_PHYSIQUE;
		int agility = (level - 1) * RoleManager.UPGRADE_AGILITY;

		role.setStrength(figureRes.getStrength() + strength);
		role.setIntellect(figureRes.getIntellect() + intellect);
		role.setPhysique(figureRes.getPhysique() + physique);
		role.setAgility(figureRes.getAgility() + agility);
		role.setFree(free);

		RoleServer.countOriginalProperty(role);
		RoleServer.fullHpAndMp(role);

		SendMessage.sendSttribute(role);
		return true;
	}

	/**
	 * 计算角色属性
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countAllProperty(Role role) {
		// ======计算经脉======
		countJingMai(role);

	}

	/**
	 * 计算经脉
	 * 
	 * @param role
	 */
	public static void countJingMai(Role role) {

		int hpMax = 0;
		int mpMax = 0;
		int pattack = 0;
		int pdefence = 0;
		int mattack = 0;
		int mdefence = 0;
		int speed = 0;
		int hit = 0;
		int dodge = 0;
		int crit = 0;
		int parry = 0;
		int strength = 0;
		int intellect = 0;
		int physique = 0;
		int agility = 0;

		int note = role.getJingMaiId();
		for (int i = 1; i <= note; i++) {
			JingMai jingMai = JingMaiJson.instance().getJingMai(i);
			if (jingMai != null) {

				hpMax += jingMai.getHpMax();
				mpMax += jingMai.getMpMax();
				pattack += jingMai.getPattack();
				pdefence += jingMai.getPdefence();
				mattack += jingMai.getMattack();
				mdefence += jingMai.getMdefence();
				speed += jingMai.getSpeed();
				hit += jingMai.getHit();
				dodge += jingMai.getDodge();
				crit += jingMai.getCrit();
				parry += jingMai.getParry();
				strength += jingMai.getStrength();
				intellect += jingMai.getIntellect();
				physique += jingMai.getPhysique();
				agility += jingMai.getAgility();
			}
		}

		role.getPropertyJingMai().setHpMax(hpMax);
		role.getPropertyJingMai().setMpMax(mpMax);
		role.getPropertyJingMai().setPattack(pattack);
		role.getPropertyJingMai().setPdefence(pdefence);
		role.getPropertyJingMai().setMattack(mattack);
		role.getPropertyJingMai().setMdefence(mdefence);
		role.getPropertyJingMai().setSpeed(speed);
		role.getPropertyJingMai().setHit(hit);
		role.getPropertyJingMai().setDodge(dodge);
		role.getPropertyJingMai().setCrit(crit);
		role.getPropertyJingMai().setParry(parry);
		role.getPropertyJingMai().setStrength(strength);
		role.getPropertyJingMai().setIntellect(intellect);
		role.getPropertyJingMai().setPhysique(physique);
		role.getPropertyJingMai().setAgility(agility);

		// ======计算一级属性======
		countOriginalProperty(role);
	}

	/**
	 * 计算角色原始数值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countOriginalProperty(Role role) {

		int hpMax = role.getPropertyJingMai().getHpMax();
		int mpMax = role.getPropertyJingMai().getMpMax();
		int pattack = role.getPropertyJingMai().getPattack();
		int pdefence = role.getPropertyJingMai().getPdefence();
		int mattack = role.getPropertyJingMai().getMattack();
		int mdefence = role.getPropertyJingMai().getMdefence();
		int speed = role.getPropertyJingMai().getSpeed() + role.getFigureRes().getSpeed();
		int hit = role.getPropertyJingMai().getHit() + role.getFigureRes().getHit();
		int dodge = role.getPropertyJingMai().getDodge() + role.getFigureRes().getDodge();
		int crit = role.getPropertyJingMai().getCrit() + role.getFigureRes().getCrit();
		int parry = role.getPropertyJingMai().getParry() + role.getFigureRes().getParry();
		int strength = role.getPropertyJingMai().getStrength() + role.getStrength();
		int intellect = role.getPropertyJingMai().getIntellect() + role.getIntellect();
		int physique = role.getPropertyJingMai().getPhysique() + role.getPhysique();
		int agility = role.getPropertyJingMai().getAgility() + role.getAgility();

		role.setShowStrength(strength);
		role.setShowIntellect(intellect);
		role.setShowPhysique(physique);
		role.setShowAgility(agility);

		role.getPropertyOriginal().setHpMax(RoleForm.hpMax(role) + hpMax);
		role.getPropertyOriginal().setMpMax(RoleForm.mpMax(role) + mpMax);
		role.getPropertyOriginal().setPattack(RoleForm.pAttack(role) + pattack);
		role.getPropertyOriginal().setPdefence(RoleForm.pDefence(role) + pdefence);
		role.getPropertyOriginal().setMattack(RoleForm.mAttack(role) + mattack);
		role.getPropertyOriginal().setMdefence(RoleForm.mDefence(role) + mdefence);
		role.getPropertyOriginal().setSpeed(RoleForm.speed(role) + speed);
		role.getPropertyOriginal().setHit(hit);
		role.getPropertyOriginal().setDodge(dodge);
		role.getPropertyOriginal().setCrit(RoleForm.crit(role) + crit);
		role.getPropertyOriginal().setParry(RoleForm.parry(role) + parry);
		role.getPropertyOriginal().setStrength(strength);
		role.getPropertyOriginal().setIntellect(intellect);
		role.getPropertyOriginal().setPhysique(physique);
		role.getPropertyOriginal().setAgility(agility);

		// ======计算装备属性======
		countEquipProperty(role);
	}

	public static float startCount(Equip equip) {
		return ((equip.getStar() * equip.getStar() * (equip.getPropertyPercent() / 10000f) * (equip.getStartRate() / 10000f) + equip.getStar() * 200) / 10000f + 1);
	}

	/**
	 * 计算角色装备属性
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countEquipProperty(Role role) {

		int hpMax = role.getPropertyOriginal().getHpMax();
		int mpMax = role.getPropertyOriginal().getMpMax();
		int pattack = role.getPropertyOriginal().getPattack();
		int pdefence = role.getPropertyOriginal().getPdefence();
		int mattack = role.getPropertyOriginal().getMattack();
		int mdefence = role.getPropertyOriginal().getMdefence();
		int speed = role.getPropertyOriginal().getSpeed();
		int hit = role.getPropertyOriginal().getHit();
		int dodge = role.getPropertyOriginal().getDodge();
		int crit = role.getPropertyOriginal().getCrit();
		int parry = role.getPropertyOriginal().getParry();

		Set<Entry<EEquip, Equip>> set = role.getEquipments().entrySet();
		for (Iterator<Entry<EEquip, Equip>> it = set.iterator(); it.hasNext();) {
			Entry<EEquip, Equip> entry = it.next();

			Equip equip = entry.getValue();
			if (equip != null) {

				int equipHpMax = (int) (startCount(equip) * equip.getHpMax());
				int equipMpMax = (int) (startCount(equip) * equip.getMpMax());
				int equipPattack = (int) (startCount(equip) * equip.getPattack());
				int equipPdefence = (int) (startCount(equip) * equip.getPdefence());
				int equipMattack = (int) (startCount(equip) * equip.getMattack());
				int equipMdefence = (int) (startCount(equip) * equip.getMdefence());
				int equipSpeed = (int) (startCount(equip) * equip.getSpeed());
				int equipHit = (int) (startCount(equip) * equip.getHit());
				int equipDodge = (int) (startCount(equip) * equip.getDodge());
				int equipCrit = (int) (startCount(equip) * equip.getCrit());
				int equipParry = (int) (startCount(equip) * equip.getParry());

				hpMax += equipHpMax;
				mpMax += equipMpMax;
				pattack += equipPattack;
				pdefence += equipPdefence;
				mattack += equipMattack;
				mdefence += equipMdefence;
				speed += equipSpeed;
				hit += equipHit;
				dodge += equipDodge;
				crit += equipCrit;
				parry += equipParry;

				for (AddProperty addProperty : equip.getAddPropertyList()) {
					switch (addProperty.getType()) {
					case addMattack:
						mattack += addProperty.getValue();
						break;
					case addPattack:
						pattack += addProperty.getValue();
						break;
					case addMdefence:
						mdefence += addProperty.getValue();
						break;
					case addPdefence:
						pdefence += addProperty.getValue();
						break;
					case addHp:
						hpMax += addProperty.getValue();
						break;
					case addMp:
						mpMax += addProperty.getValue();
						break;
					case addSpeed:
						speed += addProperty.getValue();
						break;
					case addHit:
						hit += addProperty.getValue();
						break;
					case addDodge:
						dodge += addProperty.getValue();
						break;
					case addCrit:
						crit += addProperty.getValue();
						break;
					case addParry:
						parry += addProperty.getValue();
						break;
					default:
						break;
					}
				}
			}
		}

		role.getPropertyEquip().setHpMax(hpMax);
		role.getPropertyEquip().setMpMax(mpMax);
		role.getPropertyEquip().setPattack(pattack);
		role.getPropertyEquip().setPdefence(pdefence);
		role.getPropertyEquip().setMattack(mattack);
		role.getPropertyEquip().setMdefence(mdefence);
		role.getPropertyEquip().setSpeed(speed);
		role.getPropertyEquip().setHit(hit);
		role.getPropertyEquip().setDodge(dodge);
		role.getPropertyEquip().setCrit(crit);
		role.getPropertyEquip().setParry(parry);
		// role.getPropertyEquip().setStrength(strength);
		// role.getPropertyEquip().setIntellect(intellect);
		// role.getPropertyEquip().setPhysique(physique);
		// role.getPropertyEquip().setAgility(agility);

		// ======计算部位强化======
		countIntensify(role);
	}

	/**
	 * 计算部位强化
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countIntensify(Role role) {

		int hpMax = role.getPropertyEquip().getHpMax();
		int mpMax = role.getPropertyEquip().getMpMax();
		int pattack = role.getPropertyEquip().getPattack();
		int pdefence = role.getPropertyEquip().getPdefence();
		int mattack = role.getPropertyEquip().getMattack();
		int mdefence = role.getPropertyEquip().getMdefence();
		int speed = role.getPropertyEquip().getSpeed();
		int hit = role.getPropertyEquip().getHit();
		int dodge = role.getPropertyEquip().getDodge();
		int crit = role.getPropertyEquip().getCrit();
		int parry = role.getPropertyEquip().getParry();
		// int strength = role.getPropertyEquip().getStrength();
		// int intellect = role.getPropertyEquip().getIntellect();
		// int physique = role.getPropertyEquip().getPhysique();
		// int agility = role.getPropertyEquip().getAgility();

		Map<EEquip, Integer> bodyIntensifyMap = role.getBodyIntensify();
		Set<Entry<EEquip, Integer>> set = bodyIntensifyMap.entrySet();
		for (Iterator<Entry<EEquip, Integer>> it = set.iterator(); it.hasNext();) {
			Entry<EEquip, Integer> entry = it.next();
			EEquip part = entry.getKey();
			int level = entry.getValue();
			BodyInfo bodyInfo = IntensifyJson.instance().getEquipIntensifyMap(part, level);
			if (bodyInfo != null && role.getEquipments().get(part) != null) {
				hpMax += bodyInfo.getHp();
				mpMax += bodyInfo.getMp();
				pattack += bodyInfo.getPattack();
				pdefence += bodyInfo.getPdefence();
				mattack += bodyInfo.getMattack();
				mdefence += bodyInfo.getMdefence();
				speed += bodyInfo.getSpeed();
			}
		}

		role.getPropertyIntensify().setHpMax(hpMax);
		role.getPropertyIntensify().setMpMax(mpMax);
		role.getPropertyIntensify().setPattack(pattack);
		role.getPropertyIntensify().setPdefence(pdefence);
		role.getPropertyIntensify().setMattack(mattack);
		role.getPropertyIntensify().setMdefence(mdefence);
		role.getPropertyIntensify().setSpeed(speed);
		role.getPropertyIntensify().setHit(hit);
		role.getPropertyIntensify().setDodge(dodge);
		role.getPropertyIntensify().setCrit(crit);
		role.getPropertyIntensify().setParry(parry);
		// role.getPropertyIntensify().setStrength(strength);
		// role.getPropertyIntensify().setIntellect(intellect);
		// role.getPropertyIntensify().setPhysique(physique);
		// role.getPropertyIntensify().setAgility(agility);

		// ======计算配件======
		countStone(role);
	}

	/**
	 * 计算配件
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countStone(Role role) {
		int hpMax = role.getPropertyIntensify().getHpMax();
		int mpMax = role.getPropertyIntensify().getMpMax();
		int pattack = role.getPropertyIntensify().getPattack();
		int pdefence = role.getPropertyIntensify().getPdefence();
		int mattack = role.getPropertyIntensify().getMattack();
		int mdefence = role.getPropertyIntensify().getMdefence();
		int speed = role.getPropertyIntensify().getSpeed();
		int hit = role.getPropertyIntensify().getHit();
		int dodge = role.getPropertyIntensify().getDodge();
		int crit = role.getPropertyIntensify().getCrit();
		int parry = role.getPropertyIntensify().getParry();
		// int strength = role.getPropertyIntensify().getStrength();
		// int intellect = role.getPropertyIntensify().getIntellect();
		// int physique = role.getPropertyIntensify().getPhysique();
		// int agility = role.getPropertyIntensify().getAgility();

		HashMap<EEquip, HashMap<Integer, Rabbet>> stoneMap = role.getBodyRabbet();
		Set<Entry<EEquip, HashMap<Integer, Rabbet>>> set = stoneMap.entrySet();
		for (Iterator<Entry<EEquip, HashMap<Integer, Rabbet>>> it = set.iterator(); it.hasNext();) {
			Entry<EEquip, HashMap<Integer, Rabbet>> entry = it.next();
			EEquip part = entry.getKey();
			Set<Entry<Integer, Rabbet>> stoneSet = entry.getValue().entrySet();
			for (Iterator<Entry<Integer, Rabbet>> stoneIt = stoneSet.iterator(); stoneIt.hasNext();) {
				Entry<Integer, Rabbet> stoneEntry = stoneIt.next();

				Stone stone = (Stone) ItemJson.instance().getItem(stoneEntry.getValue().getStoneId());
				// Stone stone = stoneEntry.getValue().getStone();
				if (stone != null && role.getEquipments().get(part) != null) {
					hpMax += stone.getHpMax();
					mpMax += stone.getMpMax();
					pattack += stone.getPattack();
					pdefence += stone.getPdefence();
					mattack += stone.getMattack();
					mdefence += stone.getMdefence();
					speed += stone.getSpeed();
					hit += stone.getHit();
					dodge += stone.getDodge();
					crit += stone.getCrit();
					parry += stone.getParry();
				}
			}
		}

		role.getPropertyStone().setHpMax(hpMax);
		role.getPropertyStone().setMpMax(mpMax);
		role.getPropertyStone().setPattack(pattack);
		role.getPropertyStone().setPdefence(pdefence);
		role.getPropertyStone().setMattack(mattack);
		role.getPropertyStone().setMdefence(mdefence);
		role.getPropertyStone().setSpeed(speed);
		role.getPropertyStone().setHit(hit);
		role.getPropertyStone().setDodge(dodge);
		role.getPropertyStone().setCrit(crit);
		role.getPropertyStone().setParry(parry);
		// role.getPropertyStone().setStrength(strength);
		// role.getPropertyStone().setIntellect(intellect);
		// role.getPropertyStone().setPhysique(physique);
		// role.getPropertyStone().setAgility(agility);

		// 计算基本属性
		countProperty(role);
	}

	/**
	 * 计算基本属性
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countProperty(Role role) {
		int hpMax = role.getPropertyStone().getHpMax();
		int mpMax = role.getPropertyStone().getMpMax();
		int pattack = role.getPropertyStone().getPattack();
		int pdefence = role.getPropertyStone().getPdefence();
		int mattack = role.getPropertyStone().getMattack();
		int mdefence = role.getPropertyStone().getMdefence();
		int speed = role.getPropertyStone().getSpeed();
		int hit = role.getPropertyStone().getHit();
		int dodge = role.getPropertyStone().getDodge();
		int crit = role.getPropertyStone().getCrit();
		int parry = role.getPropertyStone().getParry();
		// int strength = role.getPropertyStone().getStrength();
		// int intellect = role.getPropertyStone().getIntellect();
		// int physique = role.getPropertyStone().getPhysique();
		// int agility = role.getPropertyStone().getAgility();

		for (Integer skillId : role.getSkillMap().values()) {
			RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(skillId);

			if (roleSkill != null && roleSkill.getLevel() > 0 && roleSkill.getType().equals(ESkillType.passiveSkill)) {
				switch (roleSkill.getSpecies()) {
				case s3: // 金钟罩 生命值
					hpMax += roleSkill.getValue();
					break;
				case s5: // 罗汉金身 外功攻击
					pattack += roleSkill.getValue();
					break;
				case s9: // 龟背功 格挡
					parry += roleSkill.getValue();
					break;
				case s13: // 冥 内力
					mpMax += roleSkill.getValue();
					break;
				case s15: // 疾 闪避
					dodge += roleSkill.getValue();
					break;
				case s19: // 影 内功攻击
					mattack += roleSkill.getValue();
					break;
				case s23: // 净衣术 内力
					mpMax += roleSkill.getValue();
					break;
				case s25: // 逍遥游 内防
					mdefence += roleSkill.getValue();
					break;
				case s29: // 化生 外防
					pdefence += roleSkill.getValue();
					break;
				case s33: // 月影步 速度
					speed += roleSkill.getValue();
					break;
				case s35: // 我意逍遥 内力
					mpMax += roleSkill.getValue();
					break;
				case s39: // 虎鹰扬视 命中
					hit += roleSkill.getValue();
					break;
				default:
					break;
				}
			}
		}

		role.setHpMax(hpMax);
		role.setMpMax(mpMax);
		role.setPattack(pattack);
		role.setPdefence(pdefence);
		role.setMattack(mattack);
		role.setMdefence(mdefence);
		role.setSpeed(speed);
		role.setHit(hit);
		role.setDodge(dodge);
		role.setCrit(crit);
		role.setParry(parry);

		role.setPower(rolePower(role));
		// 战力排行榜改变
		RankEvent.PowerEvent.handleEvent(role.toCardPower());
		// 刷新战力
		SendMessage.sendPower(role);
	}

	/**
	 * 角色升级，若经验不足返回失败
	 * 
	 * @param role
	 *            角色对象
	 * @return 升级成功
	 */
	public static boolean upgrade(Role role) {

		if (role.getLevel() >= RoleManager.MAX_LEVEL) {
			return false;
		}

		if (role.getExp() < role.getExpMax()) {
			return false;
		}

		role.setLevel(role.getLevel() + 1);
		MapManager.instance().addUpgradeQueue(role);

		role.setExp(role.getExp() - role.getExpMax());

		// ======一级属性各加一点======
		role.setStrength(role.getStrength() + RoleManager.UPGRADE_STRENGTH);
		role.setIntellect(role.getIntellect() + RoleManager.UPGRADE_INTELLECT);
		role.setPhysique(role.getPhysique() + RoleManager.UPGRADE_PHYSIQUE);
		role.setAgility(role.getAgility() + RoleManager.UPGRADE_AGILITY);

		countOriginalProperty(role);
		role.setFree(role.getFree() + RoleManager.UPGRADE_FREE);
		fullHpAndMp(role);
		countExpMax(role);

		if (role.getLevelGiftStateMap().containsKey(role.getLevel()) && role.getLevelGiftStateMap().get(role.getLevel()) == 0) {
			role.getLevelGiftStateMap().put(role.getLevel(), 1);
		}

		SendMessage.sendLevel(role); // 刷新等级通知
		SendMessage.sendSttribute(role); // 刷新属性通知
		TaskServer.refreshTask(role); // 刷新任务
		TaskMessage.sendTask(role);
		if (role.getExp() >= role.getExpMax()) {
			upgrade(role);
		}
		UptipEvent.属性点.check(role, role.getUptipBoolean(UptipEvent.属性点.getOrdinal()));
		return true;
	}

	/**
	 * 满血满蓝
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void fullHpAndMp(Role role) {
		role.setHp(role.getHpMax());
		role.setMp(role.getMpMax());
	}

	/**
	 * 增加经验
	 * 
	 * @param role
	 *            角色对象
	 * @param exp
	 *            增加的经验值
	 * @param expType
	 *            类型
	 * @return 升级了
	 */
	public static void addExp(Role role, int exp, EExp expType) {

		// 角色等级不能超过限制等级
		if (exp > 0 && role.getLevel() < RoleManager.MAX_LEVEL + 1 && role.getExp() < RoleManager.MAX_EXP) {

			int additionExp = 0;
			switch (expType) {
			case monster:
				additionExp += ExpServer.additionSystem(role, exp);
				additionExp += ExpServer.additionBuff(role, exp);
				break;
			default:
				break;
			}
			exp += additionExp;

			LogManager.exp(role, expType, exp);
			role.setExp(role.getExp() + exp);
			AwardServer.addExp(role, exp); // 发送通知
			if (upgrade(role)) {
				if (role.getTeam() != null) {
					TeamMessage.sendTeamAllMsg(role.getTeam());
				}
			} else {
				SendMessage.sendExp(role);
			}
			RankEvent.ExpEvent.handleEvent(role.toCard()); // 等级排行榜
		}

	}

	/**
	 * 穿戴装备
	 * 
	 * @param role
	 *            角色对象
	 * @param equip
	 *            装备对象
	 */
	public static void wield(Role role, Equip equip) {
		role.getEquipments().put(equip.getPart(), equip);
		countEquipProperty(role);
	}

	/**
	 * 卸下装备
	 * 
	 * @param role
	 *            角色对象
	 * @param part
	 *            装备位置
	 */
	public static void unwield(Role role, EEquip part) {
		role.getEquipments().remove(part);
		countEquipProperty(role);
	}

	/**
	 * 角色加点
	 * 
	 * @param role
	 *            角色对象
	 * @param map
	 *            属性
	 */
	public static void changeFree(Role role, Map<Integer, Integer> map) {
		if (map.containsKey(1)) {
			role.setStrength(role.getStrength() + map.get(1));
		}
		if (map.containsKey(2)) {
			role.setIntellect(role.getIntellect() + map.get(2));
		}
		if (map.containsKey(3)) {
			role.setPhysique(role.getPhysique() + map.get(3));
		}
		if (map.containsKey(4)) {
			role.setAgility(role.getAgility() + map.get(4));
		}
		countOriginalProperty(role);
	}

	/**
	 * 计算角色升级所需经验
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void countExpMax(Role role) {
		role.setExpMax(RoleJson.instance().getExpMax(role.getLevel()));
	}

	/**
	 * 增加答题积分
	 * 
	 * @param role
	 *            角色对象
	 * @param score
	 *            积分
	 */
	public static void addScore(Role role, int score) {
		if (score > 0) {
			role.setDailyAnswerScore(role.getDailyAnswerScore() + score);
			SendMessage.sendScore(role); // 刷新答题积分
			AwardServer.addScore(role, score); // 发送通知
		}
	}

	/**
	 * 添加禁言时间
	 * 
	 * @param role
	 *            角色
	 * @param time
	 *            禁言时间
	 */
	public static void addMute(Role role, int time) {
		if (time > 0) {
			LogManager.mute(role.getId(), time); // 禁言日志
			role.setMute(System.currentTimeMillis() + time * Time.MINUTE_MILLISECOND);
		}
	}

	/**
	 * 增加个人活跃度
	 * 
	 * @param role
	 *            角色对象
	 * @param gangActivity
	 *            活跃度
	 */
	public static void addGangActivity(Role role, int gangActivity) {
		if (gangActivity > 0) {
			role.setGangActivity(role.getGangActivity() + gangActivity);
		}
	}

	/**
	 * 增加个人帮贡
	 * 
	 * @param role
	 *            角色对象
	 * @param tribute
	 *            增加个人帮贡值
	 */
	public static void addTribute(Role role, int tribute) {
		if (tribute > 0) {
			if (role.getGangId() != 0) {
				role.setTribute(role.getTribute() + tribute);
				role.setTotalTribute(role.getTotalTribute() + tribute);
				LogManager.gang(role.getId(), role.getUserId(), role.getGangId(), 0, "", 0, tribute, EGang.帮派贡献值.ordinal());
				AwardServer.addTribute(role, tribute);
				GangMessage.refreshTribute(role); // 刷新帮贡
			}
		}
	}

	/**
	 * 消耗个人帮贡值
	 * 
	 * @param role
	 *            角色对象
	 * @param tribute
	 *            消耗个人帮贡值
	 * @return 消耗成功
	 */
	public static boolean costTribute(Role role, int tribute) {
		if (role.getTribute() < tribute || tribute <= 0) {
			return false;
		}
		role.setTribute(role.getTribute() - tribute);
		TipServer.costTribute(role, tribute); // ======提示======
		GangMessage.refreshTribute(role); // 刷新帮贡
		return true;
	}

	/**
	 * 增加烧香经验
	 * 
	 * @param role
	 *            角色对象
	 * @param exp
	 *            增加的经验
	 */
	public static void addShaoXiangExp(Role role, int exp) {
		if (exp > 0 && role.getShaoXiangLevel() < 5) {
			// 本次烧香后的烧香总经验
			role.setShaoXiangExp(role.getShaoXiangExp() + exp);
			upgradeShaoXiangLevel(role);
		}
	}

	/**
	 * 烧香升级
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void upgradeShaoXiangLevel(Role role) {
		int max = EGangSXLevel.getExp(role.getShaoXiangLevel());
		if (max <= role.getShaoXiangExp()) {
			role.setShaoXiangLevel(role.getShaoXiangLevel() + 1);
			role.setShaoXiangExp(role.getShaoXiangExp() - max);
		}
	}

	/**
	 * @function 上线检查在线奖励
	 * @author LuoSR
	 * @param role
	 * @date 2014年5月30日
	 */
	public static void checkOnlineAward(Role role) {
		if (role.getOnlineAwardStateMap().isEmpty()) {
			List<OnlineAwardInfo> onlineAwardInfoList = ActivityJson.instance().getActivityOnlineList();
			for (OnlineAwardInfo onlineAwardInfo : onlineAwardInfoList) {
				role.getOnlineAwardStateMap().put(onlineAwardInfo.getId(), 0);
			}
		}
	}

	/**
	 * role转roleCard
	 * 
	 * @param role
	 *            角色对象
	 * @param roleCard
	 *            角色Card对象
	 */
	public static void roleToRoleCard(Role role, RoleCard roleCard) {
		roleCard.setRole(role);
		roleCard.setServerId(role.getServerId());
		roleCard.setId(role.getId());
		roleCard.setNick(role.getNick());
		roleCard.setVocation(role.getVocation());
		roleCard.setFigure(role.getFigure());
		roleCard.setLevel(role.getLevel());
		roleCard.setExp(role.getExp());
		roleCard.setGangId(role.getGangId());
		// roleCard.setGangName(role.getGang().getName());
		roleCard.setGang(role.getGang());
		roleCard.setLeaveGangTime(role.getLeaveGangTime());
		// roleCard.setJobTitle(role.getJobTitle());
		roleCard.setGold(role.getGold());
		roleCard.setCoin(role.getCoin());
		roleCard.setTotalTribute(role.getTotalTribute());
		roleCard.setTribute(role.getTribute());
		roleCard.setGangActivity(role.getGangActivity());
		roleCard.setShaoXiangNum(role.getShaoXiangNum());
		roleCard.setSurprise(role.getSurprise());
		roleCard.setDailyAnswerQuestions(role.getDailyAnswerQuestions());
		roleCard.setDailyAnswerState(role.getDailyAnswerState());
		roleCard.setDailyAnswerContinuousTrueNum(role.getDailyAnswerContinuousTrueNum());
		roleCard.setDailyAnswerTotleTrueNum(role.getDailyAnswerTotleTrueNum());
		roleCard.setDailyAnswerScore(role.getDailyAnswerScore());
		roleCard.setDailyAnswerTotleGold(role.getDailyAnswerTotleGold());
		roleCard.setRaidsTeamInfo(role.getRaidsTeamInfo());
		// 战力用属性
		roleCard.setPower(role.getPower());
		// 爬塔
		roleCard.setHistoryFloorId(role.getHistoryFloorId());
	}

	/** 战力计算 */
	public static long rolePower(Role role) {
		float hpMax = role.getHpMax() * 0.1f; // 生命值上限
		float mpMax = role.getMpMax() * 0.12f; // 内力值上限
		float pattack = role.getPattack() * 0.3f; // 外功攻击
		float pdefence = role.getPdefence() * 0.15f; // 外功防御
		float mattack = role.getMattack() * 0.36f; // 内功攻击
		float mdefence = role.getMdefence() * 0.23f; // 内功防御
		float hit = role.getHit() * 1.6f; // 命中
		float dodge = role.getDodge() * 1.74f; // 闪避
		float crit = role.getCrit() * 1.42f; // 暴击
		float parry = role.getParry() * 1.27f; // 格挡
		float speed = role.getSpeed() * 1f; // 速度
		int power = (int) (hpMax + mpMax + pattack + pdefence + mattack + mdefence + hit + dodge + crit + parry + speed);
		return power * 10;
	}

	/**
	 * 上线 RoleCard 给 Role赋值
	 * 
	 * @param role
	 */
	public static RoleCard roleCardToRole(Role role) {
		RoleCard roleCard = null;
		if (RoleManager.roleCardMap.containsKey(role.getId())) {
			roleCard = RoleManager.roleCardMap.get(role.getId());

			// ======帮派信息======
			role.setGangId(roleCard.getGangId());
			// role.setGangName(roleCard.getGang().getName());
			role.setGang(roleCard.getGang());
			// role.setJobTitle(roleCard.getJobTitle());
			role.setShaoXiangNum(roleCard.getShaoXiangNum());
			role.setLeaveGangTime(roleCard.getLeaveGangTime());

			// ======每日答题信息======
			role.setDailyAnswerQuestions(roleCard.getDailyAnswerQuestions());
			role.setDailyAnswerState(roleCard.getDailyAnswerState());
			role.setDailyAnswerContinuousTrueNum(roleCard.getDailyAnswerContinuousTrueNum());
			role.setDailyAnswerTotleTrueNum(roleCard.getDailyAnswerTotleTrueNum());
			role.setDailyAnswerTotleGold(roleCard.getDailyAnswerTotleGold());

			// ======大礼包======
			role.setSurprise(roleCard.getSurprise());
			if (roleCard.getRaidsTeamInfo() != null && roleCard.getRaidsTeamInfo().getStartTime() - System.currentTimeMillis() < Time.DAY_MILLISECOND) {
				role.setRaidsTeamInfo(roleCard.getRaidsTeamInfo());
			} else {
				role.setRaidsTeamInfo(null);
			}
			roleCard.setRole(role);
			long power = rolePower(role);
			roleCard.setPower(power);
			role.setPower(power);
		} else {
			roleCard = role.newCard();
			RoleManager.roleCardMap.put(role.getId(), roleCard);
		}

		return roleCard;
	}

	/**
	 * 是否出现精英怪
	 * 
	 * @param role
	 *            角色对象
	 * @return 出现精英怪
	 */
	public static boolean isEliteMonster(Role role) {
		if (role.getLevel() <= 30) {
			return false;
		}
		if (role.isEliteMonster()) {
			return false;
		}
		int num = role.getEliteMonsterNum();
		if (num >= 100) {
			return false;
		}
		if (role.getEliteMonster() >= 10) {
			role.setEliteMonster(true);
			role.setEliteMonsterNum(role.getEliteMonsterNum() + 1);
			return true;
		}
		if (Util.probable(500)) {
			role.setEliteMonster(true);
			role.setEliteMonsterNum(role.getEliteMonsterNum() + 1);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 记录遇怪次数
	 * 
	 * @param role
	 */
	public static void addEliteMonster(Role role) {
		if (role.getLevel() > 30) {
			role.setEliteMonster(role.getEliteMonster() + 1);
			if (role.getEliteMonster() > 10) {
				role.setEliteMonster(0);
				role.setEliteMonster(false);
			}
		}
	}

	/** 判断累计充值该跳到第几页 */
	public static int getPlusMoneyNotRec(Role role) {
		List<Integer> list = role.getRecPlusMoney();
		if (list.size() == 0) {
			return 1;
		}
		Collections.sort(list);
		if (list.size() != list.get(list.size() - 1)) {
			for (int i = 0; i < role.getRecPlusMoney().size(); i++) {
				if (role.getRecPlusMoney().get(i) != i + 1) {
					return i + 1;
				}
			}
		} else {
			return list.size() + 1;
		}
		return 1;
	}

	/**
	 * 刷新活力值
	 * 
	 * @param role
	 */
	public static void energyRefresh(Role role) {
		if (role.getEnergy() >= role.getEnergyMax()) {
			role.setEnergyMax(RoleManager.ENERGY_MAX_DEFULT + role.getVip().getLevel().getEnergyAdd());
			role.setEnergyTime(System.currentTimeMillis());
		} else {
			RoleServer.energyCheck(role);
		}
	}

	/**
	 * 验证活力值
	 * 
	 * @param role
	 */
	public static void energyCheck(Role role) {
		role.setEnergyMax(RoleManager.ENERGY_MAX_DEFULT + role.getVip().getLevel().getEnergyAdd());
		int num = (int) ((System.currentTimeMillis() - role.getEnergyTime()) / (Time.MINUTE_MILLISECOND * 10));
		RoleServer.energyAdd(role, num, false);
	}

	/**
	 * 增加活力值
	 * 
	 * @param role
	 * @param num
	 * @param isMandatory
	 */
	public static void energyAdd(Role role, int num, boolean isMandatory) {
		if (role == null) {
			return;
		}

		if (num < 0) {
			role.setEnergyTime(System.currentTimeMillis());
		}

		if (isMandatory) {
			int sum = role.getEnergy() + num > 9999 ? 9999 : role.getEnergy() + num;
			role.setEnergy(sum);
			role.setEnergyTime(System.currentTimeMillis());
		} else {
			if ((role.getEnergy() + num) >= role.getEnergyMax()) {
				role.setEnergy(role.getEnergyMax());
				role.setEnergyTime(System.currentTimeMillis());
			} else {
				role.setEnergy(role.getEnergy() + num);
				role.setEnergyTime(role.getEnergyTime() + num * Time.MINUTE_MILLISECOND * 10);
			}
		}
		
		SendMessage.refreshEnergy(role);
	}

	/**
	 * 消耗活力值
	 * 
	 * @param role
	 * @param num
	 * @return
	 */
	public static boolean energyCost(Role role, int num) {
		if (role == null || num <= 0) {
			return false;
		}

		if (role.getEnergy() < num) {
			return false;
		}
		role.setEnergy(role.getEnergy() - num);
		SendMessage.refreshEnergy(role);
		return true;
	}
}
