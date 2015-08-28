package iyunu.NewTLOL.util.log;

import iyunu.NewTLOL.enumeration.EEquipEvent;
import iyunu.NewTLOL.enumeration.EFriendEvent;
import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.enumeration.EWarehouseEvent;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mail.Email;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Time;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogManager {

	private static Logger info = LoggerFactory.getLogger("info");
	private static Logger log = LoggerFactory.getLogger("log");
	private static Logger error = LoggerFactory.getLogger("error");
	private static Logger battle = LoggerFactory.getLogger("battle");
	private static Logger server = LoggerFactory.getLogger("server");
	private static Logger itemLogger = LoggerFactory.getLogger("item"); // 失去物品
	private static Logger itemGet = LoggerFactory.getLogger("itemGet"); // 获得物品
	private static Logger mute = LoggerFactory.getLogger("mute"); // 禁言
	private static Logger roleLogon = LoggerFactory.getLogger("roleLogon");
	private static Logger roleFirstLogon = LoggerFactory.getLogger("roleFirstLogon");
	private static Logger roleCreate = LoggerFactory.getLogger("roleCreate");
	private static Logger money = LoggerFactory.getLogger("money");
	private static Logger kefu = LoggerFactory.getLogger("kefu");
	private static Logger excep = LoggerFactory.getLogger("excep");
	private static Logger onlineCounter = LoggerFactory.getLogger("onlineCounter");
	private static Logger kuai3 = LoggerFactory.getLogger("kuai3");
	private static Logger kuai3Result = LoggerFactory.getLogger("kuai3Result");
	private static Logger chat = LoggerFactory.getLogger("chat"); // 聊天
	private static Logger mail = LoggerFactory.getLogger("mail");
	private static Logger mall = LoggerFactory.getLogger("mall");
	private static Logger costGold = LoggerFactory.getLogger("costGold");
	private static Logger coin = LoggerFactory.getLogger("coin");
	private static Logger friend = LoggerFactory.getLogger("friend");
	private static Logger jingMai = LoggerFactory.getLogger("jingMai");
	private static Logger intensify = LoggerFactory.getLogger("intensify");
	private static Logger trials = LoggerFactory.getLogger("trials");
	private static Logger auction = LoggerFactory.getLogger("auction");
	private static Logger partnerRefresh = LoggerFactory.getLogger("partnerRefresh");
	private static Logger partnerGet = LoggerFactory.getLogger("partnerGet");
	private static Logger buyGold = LoggerFactory.getLogger("buyGold");
	private static Logger offLineExp = LoggerFactory.getLogger("offLineExp");
	private static Logger equip = LoggerFactory.getLogger("equip");
	private static Logger warehouse = LoggerFactory.getLogger("warehouse");
	private static Logger gang = LoggerFactory.getLogger("gang");
	private static Logger clientLog = LoggerFactory.getLogger("clientLog");
	private static Logger exp = LoggerFactory.getLogger("exp");
	private static Logger shenbing = LoggerFactory.getLogger("shenbing");
	private static Logger gaiming = LoggerFactory.getLogger("gaiming");
	private static Logger partnerUpGrade = LoggerFactory.getLogger("partnerUpGrade");
	private static Logger partnerAdvanced = LoggerFactory.getLogger("partnerAdvanced");
	private static Logger partnerEvolve = LoggerFactory.getLogger("partnerEvolve");
	private static Logger partnerCapability = LoggerFactory.getLogger("partnerCapability");
	private static Logger activityNew = LoggerFactory.getLogger("activityNew");
	private static Logger qiancengta = LoggerFactory.getLogger("qiancengta");
	private static Logger taskLog = LoggerFactory.getLogger("task"); // 任务
	private static Logger logoutLog = LoggerFactory.getLogger("logout");
	private static Logger llpLog = LoggerFactory.getLogger("llp");
	private static Logger itemAllLog = LoggerFactory.getLogger("itemAll");
	private static Logger levelRankingLog = LoggerFactory.getLogger("levelRanking");
	private static Logger powerRankingLog = LoggerFactory.getLogger("powerRanking");
	private static Logger qctRankingLog = LoggerFactory.getLogger("qctRanking");
	public static boolean isLlpLog = false;

	public static void levelRankingLog(int index, RoleCard roleCard) {
		// 名词#$角色id#$昵称#$用户id
		levelRankingLog.info(index + "#$" + roleCard.getId() + "#$" + roleCard.getNick() + "#$" + roleCard.getUserId());
	}

	public static void powerRankingLog(int index, RoleCard roleCard) {
		// 名词#$角色id#$昵称#$用户id
		powerRankingLog.info(index + "#$" + roleCard.getId() + "#$" + roleCard.getNick() + "#$" + roleCard.getUserId());
	}

	public static void qctRankingLog(int index, RoleCard roleCard) {
		// 名词#$角色id#$昵称#$用户id
		qctRankingLog.info(index + "#$" + roleCard.getId() + "#$" + roleCard.getNick() + "#$" + roleCard.getUserId());
	}

	/**
	 * 私有构造方法
	 */
	private LogManager() {

	}

	public static void battle(String content) {
		battle.info(content);
	}

	/**
	 * 日志输出
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void logOut(String content) {
		log.info(content);
	}

	/**
	 * 协议日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void llpLog(String content) {
		if (isLlpLog) {
			log.info(content);
		}
	}

	/**
	 * 日志输出
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void info(String content) {
		info.info(content);
	}

	/**
	 * 异常日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void exception(String content) {
		excep.info(content);
	}

	/**
	 * 错误日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void error(String content) {
		error.error(content);
	}

	/**
	 * 所有物品记录日志
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void itemAllLog(Role role, int state) {
		// roleid#$昵称#$背包物品字符串#$平台#$状态（0.上线，1.下线，2.重新连接）
		itemAllLog.info(role.getId() + "#$" + role.getNick() + "#$" + role.getBag().toString() + "#$" + role.getPlatform() + "#$" + state);
	}

	/**
	 * 协议日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void llp(String content, long time) {
		llpLog.info(content + "#$" + time + "#$" + (time / 1000000));
	}

	/**
	 * 下线日志
	 * 
	 * @param reason
	 *            下线原因
	 */
	public static void logout(String reason, long roleId, String roloName) {
		logoutLog.info(roleId + "#$" + roloName + "#$" + reason);
	}

	/**
	 * 服务器信息日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void server(String content) {
		server.info(content);
	}

	/**
	 * 任务日志
	 * 
	 * @param role
	 *            角色对象
	 * @param taskId
	 *            任务编号
	 * @param type
	 *            类型（0.接取，1.完成，2.放弃）
	 */
	public static void taskLog(Role role, int taskId, int type) {
		// 服务器#$userid#$roleid#$level#$任务编号#$类型（0.接取，1.完成，2.放弃）#$平台
		try {
			taskLog.info(ServerManager.instance().getServer() + "#$" + role.getUserId() + "#$" + role.getId() + "#$" + role.getLevel() + "#$" + taskId + "#$" + type + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param type
	 *            类型（0.兑换，1.升级，2.重置，3.丢弃）
	 * @param uid
	 * @param steps
	 * @param role
	 */
	public static void shenbing(int type, String uid, int steps, Role role, int level) {
		// 服务器#$roleid#$userid#$类型#$唯一编号#$阶#$平台
		try {
			shenbing.info(ServerManager.instance().getServer() + "#$" + role.getId() + "#$" + role.getUserId() + "#$" + type + "#$" + uid + "#$" + steps + "#$" + level + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void gaiming(String oldNick, Role role) {
		// 服务器#$roleid#$userid#$旧的名称#新的名称#$平台
		try {
			gaiming.info(ServerManager.instance().getServer() + "#$" + role.getId() + "#$" + role.getUserId() + "#$" + oldNick + "#$" + role.getNick() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 客户端错误日志
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param content
	 *            内容
	 * @date 2014年6月5日
	 */
	public static void clientLog(Role role, String content) {
		// 角色编号#$角色名称#$服务器#$日志内容
		if (role != null) {

			clientLog.info(role.getId() + "#$" + role.getNick() + "#$" + ServerManager.instance().getServer() + "#$" + content);
		} else {
			clientLog.info("null#$null#$" + ServerManager.instance().getServer() + "#$" + content);
		}
	}

	/**
	 * 提交问题日志
	 * 
	 * @param content
	 *            日志内容
	 */
	public static void kefu(int type, String title, String content, Role role) {
		// 类型#$标题#$内容#$服务器#$roleid#$userid#$平台
		try {
			kefu.info(type + "#$" + title + "#$" + content + "#$" + ServerManager.instance().getServer() + "#$" + role.getId() + "#$" + role.getUserId() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exp(Role role, EExp expType, int expValue) {
		// 服务器#$角色编号#$角色名称#$角色经验#$角色等级#$经验类型#$获得经验#$平台
		try {
			exp.info(ServerManager.instance().getServer() + "#$" + role.getId() + "#$" + role.getNick() + "#$" + role.getExp() + "#$" + role.getLevel() + "#$" + expType.ordinal() + "#$" + expValue + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 物品消耗日志
	 * 
	 * @param role
	 *            角色对象
	 * @param item
	 *            物品对象
	 * @param num
	 *            数量
	 */
	public static void item(Role role, Item item, int num, EItemCost type) {
		// 角色编号#$角色名称#$服务器#$物品索引#$物品唯一编号#$物品名称#$数量#$平台
		try {
			itemLogger.info(role.getId() + "#$" + role.getNick() + "#$" + ServerManager.instance().getServer() + "#$" + item.getId() + "#$" + item.getUid() + "#$" + item.getName() + "#$" + num + "#$" + type.ordinal() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void itemGet(Role role, Item item, int num, EItemGet type) {
		// 角色编号#$角色名称#$服务器#$物品索引#$物品唯一编号#$物品名称#$数量#$平台
		try {
			itemGet.info(role.getId() + "#$" + role.getNick() + "#$" + ServerManager.instance().getServer() + "#$" + item.getId() + "#$" + item.getUid() + "#$" + item.getName() + "#$" + num + "#$" + type.ordinal() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在线人数日志
	 * 
	 * @param num
	 *            在线人数
	 */
	public static void onlineCounter(int num) {
		// 在线人数#$服务器
		try {
			onlineCounter.info(num + "#$" + ServerManager.instance().getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 快三开奖结果日志
	 * 
	 * @param turn
	 *            轮数
	 * @param result
	 *            开奖结果
	 */
	public static void kuai3(int turn, int result) {
		// 轮数#$开奖结果#$服务器
		try {
			kuai3.info(turn + "#$" + result + "#$" + ServerManager.instance().getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 快三发奖日志
	 * 
	 * @param turn
	 *            轮数
	 * @param result
	 *            开奖结果
	 */
	public static void kuai3Result(int turn, int result, int win, long roleId, int type, int num, int times, int gold) {
		// 轮数#$开奖结果#$是否中奖（0.未中奖，1.中奖）#$角色编号#$投注类型#$投注号码#$倍数#$获得银两#$服务器
		try {
			kuai3Result.info(turn + "#$" + result + "#$" + win + "#$" + roleId + "#$" + type + "#$" + num + "#$" + times + "#$" + gold + "#$" + ServerManager.instance().getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 禁言日志
	 * 
	 * @param role
	 *            角色对象
	 * @param time
	 *            禁言时长
	 */
	public static void mute(long roleId, int time) {
		// 角色编号#$服务器编号#$禁言时长
		try {
			mute.info(roleId + "#$" + ServerManager.instance().getServer() + "#$" + time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 角色创建日志
	 * 
	 * @param role
	 */
	public static void roleCreate(Role role) {
		// 角色编号#$角色名称#$用户编号#$服务器编号#$平台
		try {
			roleCreate.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 角色登录日志
	 * 
	 * @param role
	 */
	public static void roleLogon(Role role) {
		// 角色编号#$角色名称#$用户编号#$服务器编号#$上线时间#$下线时间#$在线时长(秒)#$平台
		try {
			roleLogon.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + Time.dateToString(new Date(role.getLogonTime())) + "#$" + Time.dateToString(new Date(role.getLogoutTime())) + "#$"
					+ (role.getLogoutTime() - role.getLogonTime()) / Time.MILLISECOND + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 角色第一次登录日志
	 * 
	 * @param role
	 */
	public static void roleFirstLogon(Role role) {
		// 角色编号#$角色名称#$用户编号#$服务器编号#$平台
		try {
			roleFirstLogon.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param role
	 *            角色
	 * @param type
	 *            方式
	 * @param num
	 *            元宝数
	 * @param ifAdd
	 *            1增加 0减少
	 */
	public static void money(Role role, int type, int num, int ifAdd) {
		// 角色编号#$用户编号#$服务器编号#$使用类型#$金额#$是否增加减少#$剩余元宝数#$平台
		try {
			money.info(role.getId() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + type + "#$" + num + "#$" + ifAdd + "#$" + role.getMoney() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param role
	 *            发送者role
	 * @param type
	 *            聊天类型
	 * @param chat1
	 *            聊天对象
	 */
	public static void chat(Role role, EChat type, Chat chat1) {
		// 发送者编号#$发送者名字#$聊天类型#$内容#$服务器#$userid#$平台
		try {
			chat.info(role.getId() + "#$" + role.getNick() + "#$" + type.ordinal() + "#$" + chat1.getContent().replaceAll("\n", "") + "#$" + ServerManager.instance().getServer() + "#$" + role.getUserId() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param roleId接收者ID
	 * @param title标题
	 * @param content内容
	 * @param items物品ID集合
	 * @param gold银两
	 * @param money元宝
	 * @param partner宠物
	 */
	public static void mail(long roleId, String title, String content, Map<Item, Integer> items, int gold, int coin, int money, int exp, Partner partner, int tribute, Email type) {
		// roleid#$标题#$内容#$物品#$绑银#$银两#$元宝#$经验#$宠物 #$帮贡#$服务器#$邮件操作方式
		try {
			StringBuffer itemids = new StringBuffer();
			long partnerid = 0;
			if (items != null) {
				Iterator<Item> it = items.keySet().iterator();
				while (it.hasNext()) {
					Item en = it.next();
					itemids.append(en.getId() + "," + items.get(en) + ";");
				}
			}
			if (partner != null) {
				partnerid = partner.getId();
			}
			mail.info(roleId + "#$" + title + "#$" + content + "#$" + itemids.toString() + "#$" + gold + "#$" + coin + "#$" + money + "#$" + exp + "#$" + partnerid + "#$" + tribute + "#$" + ServerManager.instance().getServer() + "#$" + type.ordinal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 商城购买日志
	 * 
	 * @param role
	 *            角色
	 * @param item
	 *            物品
	 * @param num
	 *            数量
	 * @param money
	 *            花费元宝
	 */
	public static void mall(Role role, Item item, int num, int money) {
		try {
			// 角色ID#$userID#$物品ID#$物品名字#$数量#$花费元宝#$服务器#$平台
			mall.info(role.getId() + "#$" + role.getUserId() + "#$" + item.getId() + "#$" + item.getName() + "#$" + num + "#$" + money + "#$" + ServerManager.instance().getServer() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑银日志
	 * 
	 * @param role
	 *            角色
	 * @param type
	 *            绑银消费方式
	 * @param gold
	 *            消耗的金币数
	 * 
	 * @param ifAdd
	 *            1 增加 0 减少
	 */

	public static void costGold(Role role, int type, int gold, int ifAdd) {
		try {
			// 角色ID#$userid#$花费方式#$金币消耗数量#$服务器#$增加还是减少#$剩余绑银#$平台
			costGold.info(role.getId() + "#$" + role.getUserId() + "#$" + type + "#$" + gold + "#$" + ServerManager.instance().getServer() + "#$" + ifAdd + "#$" + role.getGold() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 银两日志
	 * 
	 * @param role
	 *            角色
	 * @param type
	 *            金币消费方式
	 * @param gold
	 *            消耗的金币数
	 * 
	 * @param ifAdd
	 *            1 增加 0 减少
	 */
	public static void coin(Role role, int type, int value, int ifAdd) {
		try {
			// 角色ID#$userid#$花费方式#$银两消耗数量#$服务器#$增加还是减少#$剩余银两#$平台
			coin.info(role.getId() + "#$" + role.getUserId() + "#$" + type + "#$" + value + "#$" + ServerManager.instance().getServer() + "#$" + ifAdd + "#$" + role.getCoin() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 好友或黑名单日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param roleId
	 *            好友或黑名单编号
	 * @param type
	 *            好友或黑名单事件
	 * @date 2014年5月21日
	 */
	public static void friend(Role role, long roleId, EFriendEvent type) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$好友或黑名单编号#$好友或黑名单事件#$平台
			friend.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + roleId + "#$" + type.ordinal() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 经脉日志
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param note
	 *            点亮经脉层数
	 * @date 2014年5月21日
	 */
	public static void jingMai(Role role, int note) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$点亮层数#$平台
			jingMai.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + note + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 强化日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param type
	 *            强化事件类型
	 * @param part
	 *            部位
	 * @param position
	 *            宝石位置
	 * @param num
	 *            宝石数量/强化次数
	 * @date 2014年5月21日
	 */

	public static void intensify(Role role, EIntensifyEvent type, int part, int position, int num) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$强化事件类型#$部位#$宝石位置#$宝石数量/强化次数#$平台
			intensify.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + type.ordinal() + "#$" + part + "#$" + position + "#$" + num + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 试炼日志
	 * @author LuoSR
	 * @param role
	 *            玩家编号
	 * @param trialsId
	 *            试炼编号
	 * @param position
	 *            试炼怪物位置
	 * @date 2014年5月21日
	 */
	public static void trials(Role role, int trialsId, int position) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$试炼编号#$试炼怪物位置#$平台
			trials.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + trialsId + "#$" + position + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param role
	 *            角色对象
	 * @param auction
	 *            拍卖对象
	 * @param type
	 *            操作类型
	 */
	public static void auction(long roleId, String userId, Auction auction1, int type) {
		// 角色编号#$userid#$服务器编号#$拍卖编号#$拍卖物品id/宠物id/#$拍卖标题#$出售者id#$价格#$#$操作类型
		try {
			switch (auction1.getType()) {
			case money:
				auction.info(roleId + "#$" + userId + "#$" + ServerManager.instance().getServer() + "#$" + auction1.getId() + "#$" + 0 + "#$" + auction1.getTitle() + "#$" + auction1.getOwnerId() + "#$" + auction1.getBuyoutprice() + "#$" + type);
				break;
			case partner:
				auction.info(roleId + "#$" + userId + "#$" + ServerManager.instance().getServer() + "#$" + auction1.getId() + "#$" + auction1.getPartnerId() + "#$" + auction1.getTitle() + "#$" + auction1.getOwnerId() + "#$" + auction1.getBuyoutprice() + "#$" + type);
				break;
			default:
				auction.info(roleId + "#$" + userId + "#$" + ServerManager.instance().getServer() + "#$" + auction1.getId() + "#$" + auction1.getItem().getId() + "#$" + auction1.getTitle() + "#$" + auction1.getOwnerId() + "#$" + auction1.getBuyoutprice() + "#$" + type);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 酒馆刷新伙伴日志
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param gold
	 *            刷新伙伴消耗银两
	 * @date 2014年5月21日
	 */
	public static void partnerRefresh(Role role, int type, int value) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$类型#$刷新伙伴消耗银两#$平台
			partnerRefresh.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + type + "#$" + value + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 招募伙伴日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param partner
	 *            伙伴对象
	 * @param type
	 *            获得伙伴方式
	 * @date 2014年5月21日
	 */
	public static void partnerGet(Role role, Partner partner, EGetPartner type) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$招募伙伴索引#$招募伙伴唯一编号#$获得伙伴方式#$平台
			partnerGet.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + partner.getIndex() + "#$" + partner.getId() + "#$" + type.ordinal() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 招募伙伴升级
	 * @author fhy
	 */
	public static void partnerUpGrade(Role role, Partner main, Partner... partners) {
		// 角色编号#$角色名称#$用户编号#$服务器编号#$升级的伙伴index#$升级的伙伴id#$被吃的伙伴1index#$伙伴1id#$平台
		try {
			for (Partner partner : partners) {
				if (partner != null) {
					partnerUpGrade.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + main.getIndex() + "#$" + main.getId() + "#$" + partner.getIndex() + "#$" + partner.getId() + "#$" + role.getPlatform());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 招募伙伴升阶
	 * @author fhy
	 */
	public static void partnerAdvanced(Role role, Partner partner, Partner partner1) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$升阶的伙伴index#$升级的伙伴id#$被吃的伙伴1index#$伙伴1id#$平台
			partnerAdvanced.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + partner.getIndex() + "#$" + partner.getId() + "#$" + partner1.getIndex() + "#$" + partner1.getId() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 伙伴进化
	 */
	public static void partnerEvolve(Role role, Partner mainPartner, Partner subPartner) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$升阶的伙伴index#$升级的伙伴id#$被吃的伙伴1index#$伙伴1id#$平台
			partnerEvolve.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + mainPartner.getIndex() + "#$" + mainPartner.getId() + "#$" + subPartner.getIndex() + "#$" + subPartner.getId() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 伙伴增加潜力值
	 */
	public static void partnerCapability(Role role, Partner partner, int value) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$升阶的伙伴index#$升级的伙伴id#$被吃的伙伴1index#$伙伴1id#$平台
			partnerCapability.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + partner.getIndex() + "#$" + partner.getId() + "#$" + partner.getCapability() + "#$" + value + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 招财日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param gold
	 *            招财获得银两
	 * @date 2014年5月21日
	 */
	public static void buyGold(Role role, int gold) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$招财获得银两#$平台
			buyGold.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + gold + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 离线经验日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param exp
	 *            离线经验
	 * @date 2014年5月21日
	 */
	public static void offLineExp(Role role, int exp) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$离线经验#$平台
			offLineExp.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + exp + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 装备日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param equipId
	 *            装备Id
	 * @param type
	 *            装备事件
	 * @date 2014年5月21日
	 */
	public static void equip(Role role, int equipId, EEquipEvent type) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$装备编号#$装备事件#$平台
			equip.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + equipId + "#$" + type.ordinal() + "#$" + role.getPlatform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 仓库日志
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @param itemId
	 *            物品编号
	 * @param num
	 *            物品数量
	 * @param type
	 *            仓库事件
	 * @date 2014年5月21日
	 */
	public static void warehouse(Role role, Item item, int num, EWarehouseEvent type) {
		try {
			// 角色编号#$角色名称#$用户编号#$服务器编号#$物品编号#$物品数量#$仓库事件#$平台
			warehouse.info(role.getId() + "#$" + role.getNick() + "#$" + role.getUserId() + "#$" + ServerManager.instance().getServer() + "#$" + item.getId() + "#$" + num + "#$" + type.ordinal() + "#$" + role.getPlatform() + "#$" + item.getIsDeal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param roleid
	 * @param userid
	 * @param gangid
	 * @param id
	 * @param name
	 * @param num
	 * @param value
	 * @param type
	 */
	public static void gang(long roleid, String userid, long gangid, long id, String name, int num, int value, int type) {
		try {
			// 角色ID#$userid#$帮派ID#$服务器#$事件ID#$事件内容#$数量#$值#$type
			gang.info(roleid + "#$" + userid + "#$" + gangid + "#$" + ServerManager.instance().getServer() + "#$" + id + "#$" + name + "#$" + num + "#$" + value + "#$" + type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 开服活动日志
	 * @author LuoSR
	 * @param roleid
	 *            角色编号
	 * @param userid
	 *            用户编号
	 * @param type
	 *            活动类型
	 * @param index
	 *            活动索引
	 * @param itemId
	 *            物品编号
	 * @param itemNum
	 *            物品数量
	 * @param partnerId
	 *            伙伴编号
	 * @param gold
	 *            绑银
	 * @param coin
	 *            银子
	 * @param money
	 *            元宝
	 * @date 2014年9月23日
	 */
	public static void activityNew(long roleid, String userid, int type, int index, int itemId, int itemNum, long partnerId, int gold, int coin, int money) {
		try {
			// 角色ID#$userid#$服务器#$type#s物品编号#$数量
			activityNew.info(roleid + "#$" + userid + "#$" + ServerManager.instance().getServer() + "#$" + type + "#$" + index + "#$" + itemId + "#$" + itemNum + "#$" + partnerId + "#$" + gold + "#$" + coin + "#$" + money);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 千层塔日志
	 * @author LuoSR
	 * @param roleid
	 *            角色编号
	 * @param userid
	 *            用户编号
	 * @param type
	 *            类型
	 * @param itemId
	 *            物品编号
	 * @param itemNum
	 *            物品数量
	 * @param partnerId
	 *            伙伴编号
	 * @param gold
	 *            绑银
	 * @param coin
	 *            银子
	 * @param money
	 *            元宝
	 * @date 2014年9月23日
	 */
	public static void qiancengta(long roleid, String userid, int type, int itemId, int itemNum, long partnerId, int gold, int exp) {
		try {
			qiancengta.info(roleid + "#$" + userid + "#$" + ServerManager.instance().getServer() + "#$" + type + "#$" + itemId + "#$" + itemNum + "#$" + partnerId + "#$" + gold + "#$" + exp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
