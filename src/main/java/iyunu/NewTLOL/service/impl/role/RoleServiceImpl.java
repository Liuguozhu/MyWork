package iyunu.NewTLOL.service.impl.role;

import iyunu.NewTLOL.common.RoleCommon;
import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.role.MapAgent;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.redis.RedisLogon;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.service.iface.mail.MailService;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.timer.EveryDayCheck;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;

public class RoleServiceImpl implements RoleServiceIfce {

	private RedisLogon redisLogon;
	private MailService mailService;

	@Override
	public long queryRoleId(String userId, int serverId) {
		return BaseDao.instance().getRoleDao().queryRoleId(userId, serverId);
	}

	@Override
	public boolean checkNick(String nick) {
		return BaseDao.instance().getRoleDao().checkNick(nick);
	}

	@Override
	public void insert(Role role) {
		BaseDao.instance().getRoleDao().insert(role);
	}

	@Override
	public Role query(long roleId) {
		return BaseDao.instance().getRoleDao().query(roleId);
	}

	@Override
	public void update(Role role) {
		BaseDao.instance().getRoleDao().update(role);
	}

	@Override
	public void delete(long roleId) {
		BaseDao.instance().getRoleDao().delete(roleId);
	}

	@Override
	public long getIdByName(String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("name", name);
		return BaseDao.instance().getRoleDao().getIdByName(map);

	}

	@Override
	public RoleCard queryRoleCard(long roleId) {
		return BaseDao.instance().getRoleDao().queryRoleCard(roleId);
	}

	// @Override
	// public void updateJob(RoleCard roleCard) {
	// BaseDao.instance().getRoleDao().updateJob(roleCard);
	// }

	@Override
	public void initRole(Role role, int change, Channel channel) {

		role.setChannel(channel);
		role.setChange(change);
		role.setUpdateTime(System.currentTimeMillis() + RoleCommon.UPDATE_TIME); // 设置自动更新时间
		role.setSrv(ServerManager.instance().getServer());
		role.setFigureRes(RoleJson.instance().getFigure(role.getFigure()));

		// ======检查地图======
		MapServer.checkMap(role);
		role.getMapInfo().addRole(role);
		role.getMapInfo().setMapAgent(new MapAgent(role)); // 添加地图信息
		role.getMapInfo().getBaseMap().add(role); // 添加至地图中

		// ======检查配件======
		RoleServer.checkBodyRabbet(role);

		RoleServer.countAllProperty(role); // 计算属性
		RoleServer.countExpMax(role); // 计算经验值上限

		// ======背包======
		role.getBag().addRole(role);
		// ======宝石背包======
		role.getBagStone().addRole(role);

		// ======将背包中宝石移至宝石背包中（临时使用）======
		if (role.getBag().isHasStone()) {
			for (Cell cell : role.getBag().getCells()) {
				if (cell.getItem() != null && cell.getItem().getType().equals(EItem.stone)) {
					role.getBagStone().add(cell.getItem(), cell.getNum(), new HashMap<Integer, Cell>(), EItemGet.sys); // 将宝石添加宝石背包
					role.getBag().clean(cell.getIndex()); // 清除格子信息
				}
			}
		}
		// ======将背包中宝石移至宝石背包中（临时使用）======

		// ======将仓库中宝石移至宝石背包中（临时使用）======
		if (role.getWarehouse().isHasStone()) {
			for (Cell cell : role.getWarehouse().getCells()) {
				if (cell.getItem() != null && cell.getItem().getType().equals(EItem.stone)) {
					role.getBagStone().add(cell.getItem(), cell.getNum(), new HashMap<Integer, Cell>(), EItemGet.sys); // 将宝石添加宝石背包
					role.getWarehouse().clean(cell.getIndex()); // 清除格子信息
				}
			}
		}
		// ======将仓库中宝石移至宝石背包中（临时使用）======

		// ======保存登录信息======
		redisLogon.saveLogonInfo(role.getUserId(), role.getServerId());
		// ======保存在线角色信息======
		ServerManager.instance().online(role.getChannel(), role);
		// ======上线加载我的拍卖======
		AuctionManager.instance().loadMyAuction(role);
		// ======加载邮件======
		role.getMails().addAll(mailService.query(role.getId()));

		// ======刷新检查任务======
		TaskServer.refreshTask(role);
		// ======初始化任务======
		TaskServer.checkTask(role);

		// ======检查伙伴======
		PartnerServer.checkPartner(role);
		// ======检查酒馆伙伴======
		PartnerServer.checkInn1(role);
		PartnerServer.checkInn2(role);
		// ======检查技能======
		RoleServer.checkSkill(role);
		// ======检查在线奖励======
		RoleServer.checkOnlineAward(role);

		// ======查询自己的好友 的roleCard并加入到RoleCardMap======
		friendCard(role);
		// =================签到=========
		checkSign(role);
		// =========登录信息记录============
		checkLogon(role);
		// 连续登录天数设值
		// checkConLogon(role);
		// 上线检查BUFF到期
		BuffServer.checkBuff(role);

		// 自动恢复
		RoleServer.autoRecoverHp(role);
		RoleServer.autoRecoverMp(role);
		PartnerServer.autoRecoverHp(role);

		// ======活力======
		RoleServer.energyCheck(role);

		// 上线roleCard 赋值
		RoleCard roleCard = RoleServer.roleCardToRole(role);
		// 冠军帮帮主和成员
		if (GangManager.championLeader != 0) {
			if (role.getId() == GangManager.championLeader) {
				GangMessage.refreshLeader(role, 1);
				Gang gang = GangManager.instance().getGang(role.getGangId());
				String content = "天下第一帮【" + StringControl.red(gang.getName()) + "】帮主【" + StringControl.grn(role.getNick()) + "】上线，众生膜拜！~	";
				BulletinManager.instance().addBulletinRock(content, 1);
			} else if (role.getGangId() == GangManager.championId) {
				GangMessage.refreshLeader(role, 2);
			}
		}
		// 上线禁言未结束的，继续禁言
		if (role.getMute() - role.getLogoutTime() > 0) {
			role.setMute(System.currentTimeMillis() + role.getMute() - role.getLogoutTime());
		}

		// 排行榜检查
//		RankEvent.ExpEvent.handleEvent(roleCard); // 等级排行榜

		if (roleCard.getHistoryFloorId() > 0) {
			RankEvent.QctEvent.handleEvent(roleCard);// 塔排行榜
		}

		// 初始化千层塔层数
		if (role.getCurrentFloorId() <= 0) {
			role.setCurrentFloorId(1);
		}

		// ======每日需要更新的状态======
		EveryDayCheck.everydayChanges(role);// 这个必须放在设置 上线时间之前
		// ============== 设置上线时间============
		role.setLogonTime(System.currentTimeMillis());
		// ==========上线清除血战状态=========
		changeBlood(role);
		role.setLogon(true);

	}

	@Override
	public void addMute(long roleId, int time) {
		LogManager.mute(roleId, time); // 禁言日志
		BaseDao.instance().getRoleDao().addMute(roleId, time * Time.MINUTE_MILLISECOND);
	}

	public void changeBlood(Role role) {
		if (!ActivityManager.BLOOD_STATE) {
			role.setBlood(0);
		} else if (role.getBlood() != 0) {
			// 如果上线还是有血战状态，但是列表中没有他，说明是上次血战下线的
			// 这次把他删除，如果列表中有他，附加战斗状态Erolefightstate
			if (!BloodManager.instance().getBloodMap().containsKey(role.getId())) {
				role.setBlood(0);
			} else {
				SendMessage.sendBloodTime(role);
				if (role.getBlood() == 1) {
				}
				if (role.getBlood() == 2) {
				}
			}
		}
	}

	public void checkSign(Role role) {
		// 检查应该签到到第几天
		if (Time.getDaysBetween(role.getFirstSign()) + 1 <= 30) {
			role.setSignDay(Time.getDaysBetween(role.getFirstSign()) + 1);
			// System.out.println("上线后设置的应该签到到第几天:" +
			// (Time.getDaysBetween(role.getFirstSign()) + 1));
		} else {
			// 如果签到时间超过30天，把签到天设为1，其它置空
			// 如果是第一次使用此功能，因为firstsign值为0，则天数一定大于30天，此时把签到天数置为1天
			// 如果玩家过了30天之后未签但是登录了，此时签到天数为1，但是因为没签到没存第一次签到时间，第二天再登录天数也大于30天，签到天数还是1
			role.setSignDay(1);
			role.setSignList(new ArrayList<Integer>());
			role.setHavePickSign(new ArrayList<Integer>());
			// System.out.println("如果超过30天：~~~~~~~~~~");
		}
	}

	public void checkLogon(Role role) {
		role.setLogonTotals(role.getLogonTotals() + 1);// 登录一次，加一次登录总次数
		if (Time.isYesterday(role.getLogonTime())) { // 如果上次登录时间正好是昨天，连续登录天数加1
			role.setLogonContinue(role.getLogonContinue() + 1);
			role.setLogonDays(role.getLogonDays() + 1); // 如果上次登录时间不是今天，登录总天数加1

		} else if (Time.beforeYesterday(role.getLogonTime())) { // 如果是昨天以前，置为1
			role.setLogonContinue(1);
			role.setLogonDays(role.getLogonDays() + 1); // 如果上次登录时间不是今天，登录总天数加1
		}
	}

	public void checkConLogon(Role role) {
		if (role.getLogonContinue() > 7) {
			if (role.getLogonContinue() % 7 == 0) {
				role.setCon(7);
			} else {
				role.setCon(role.getLogonContinue() % 7);
			}
		} else {
			role.setCon(role.getLogonContinue());
		}

	}

	public void friendCard(Role role) {
		List<Long> friendList = role.getFriendList();
		for (Long roleId : friendList) {
			if (!RoleManager.getRoleCardMap().containsKey(roleId)) {
				RoleCard rc = queryRoleCard(roleId);
				if (rc != null) {
					RoleManager.saveRoleCard(rc);
				}
			}
		}

	}

	/**
	 * @return the redisLogon
	 */
	public RedisLogon getRedisLogon() {
		return redisLogon;
	}

	/**
	 * @param redisLogon
	 *            the redisLogon to set
	 */
	public void setRedisLogon(RedisLogon redisLogon) {
		this.redisLogon = redisLogon;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService
	 *            the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Override
	public void updateMoney(Role role) {
		BaseDao.instance().getRoleDao().updateMoney(role);

	}

	@Override
	public void updateVip(Role role) {
		BaseDao.instance().getRoleDao().updateVip(role);
	}

	@Override
	public void updatePlusMoney(Role role) {
		BaseDao.instance().getRoleDao().updatePlusMoney(role);
	}

	@Override
	public void updateFirstDouble(Role role) {
		BaseDao.instance().getRoleDao().updateFirstDouble(role);
	}

	// @Override
	// public void updateRoleCard(RoleCard roleCard) {
	// BaseDao.instance().getRoleDao().updateRoleCard(roleCard);
	// }

	@Override
	public Role queryRole(long roleId) {
		if (roleId == 0) {
			return null;
		}
		Role role = BaseDao.instance().getRoleDao().query(roleId);

		// ======检查地图======
		// MapServer.checkMap(role);
		// role.getMapInfo().addRole(role);
		// role.getMapInfo().setMapAgent(new MapAgent(role)); // 添加地图信息
		// role.getMapInfo().getBaseMap().add(role); // 添加至地图中
		role.setFigureRes(RoleJson.instance().getFigure(role.getFigure()));
		// ======检查配件======
		RoleServer.checkBodyRabbet(role);

		RoleServer.countAllProperty(role); // 计算属性
		RoleServer.countExpMax(role); // 计算经验值上限

		// ======背包======
		// role.getBag().addRole(role);
		// ======保存登录信息======
		// redisLogon.saveLogonInfo(role.getUserId(), role.getServerId());
		// ======保存在线角色信息======
		// ServerManager.instance().online(role.getChannel(), role);
		// ======上线加载我的拍卖======
		// AuctionManager.instance().loadMyAuction(role);

		// ======刷新检查任务======
		// TaskServer.refreshTask(role);
		// ======初始化任务======
		// TaskServer.checkTask(role);

		// ======检查伙伴======
		PartnerServer.checkPartner(role);
		// ======检查技能======
		RoleServer.checkSkill(role);

		// ======检查在线奖励======
		// RoleServer.checkOnlineAward(role);

		// 上线检查BUFF到期
		BuffServer.checkBuff(role);

		// 自动恢复
		RoleServer.autoRecoverHp(role);
		RoleServer.autoRecoverMp(role);
		PartnerServer.autoRecoverHp(role);

		return role;
	}

	@Override
	public void updateDailyPay(Role role) {
		BaseDao.instance().getRoleDao().updateDailyPay(role);
	}

}
