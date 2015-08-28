package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.ServerManager;

public class RedisKey {

	private static final String MAIL = "mail"; // 邮件
	public static final String AUCTION = "auctionGold";// 拍卖金币
	public static final String SESSION = "session"; // 登录验证
	public static final String CHAMPION_ID = "championId";// 冠军帮ID
	/** 帮派战报名列表 **/
	public static final String GANG_FIGHT_APPLY = "gang_fight_apply";
	public static final String GANG_FIGHT = "gang_fight";
	/** 矿区 */
	public static final String MINING_MAP = "mining_map";
	/** 矿区被抢提示 */
	public static final String MINING_ROB_MAP = "mining_rob_map";
	/** 已领取帮派福利的ID */
	public static final String IF_HAD_WELFARE = "if_had_welfare";
	/** 帮派任务 */
	public static final String GANG_TASK = "gang_task";
	/** 停服时间 */
	public static final String STOP_TIME = "stop_time";
	/** 发布令 */
	public static final String FBL = "fbl";
	/** 积分榜 */
	public static final String PAYEXCHANGE = "pay_exchange";

	/**
	 * 获取登录验证的key
	 * 
	 * @return 登录验证的key
	 */
	public static String getSession() {
		return SESSION;
	}

	/**
	 * 获取邮件key
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 邮件key
	 */
	public static String getMailKey(long roleId) {
		return MAIL + "[" + roleId + "]";
	}

	/**
	 * 
	 * @return 拍卖编号
	 */
	public static String getAuctionGold() {
		return AUCTION + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * 帮派战报名的key
	 * 
	 * @return 帮派战报名的key
	 */
	public static String getGangFightApply() {
		return GANG_FIGHT_APPLY + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * 帮派战报名的key
	 * 
	 * @return 帮派战报名的key
	 */
	public static String getGangFight() {
		return GANG_FIGHT + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * 
	 * @return 冠军帮的key
	 */
	public static String getChampionId() {
		return CHAMPION_ID + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 矿区的key
	 */
	public static String getMiningMap() {
		return MINING_MAP + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 矿区的key
	 */
	public static String getMiningRobMap() {
		return MINING_ROB_MAP + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 帮派福利
	 */
	public static String getGangWelfare() {
		return IF_HAD_WELFARE + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 停服时间
	 */
	public static String getStopTime() {
		return STOP_TIME + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 帮派福利
	 */
	public static String getGangTask() {
		return GANG_TASK + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 发布令
	 */
	public static String getFbl() {
		return FBL + "[" + ServerManager.instance().getServer() + "]";
	}

	/**
	 * @return 积分榜
	 */
	public static String getPayExchange() {
		return PAYEXCHANGE + "[" + ServerManager.instance().getServer() + "]";
	}
}
