package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.main.TLOLManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.ActivityProcessorCenter;
import iyunu.NewTLOL.processor.FblProcessorCenter;
import iyunu.NewTLOL.processor.MonsterProcessorCenter;
import iyunu.NewTLOL.processor.OnlineProcessorCenter;
import iyunu.NewTLOL.processor.TimeProcessorCenter;
import iyunu.NewTLOL.processor.map.BaseProcessorCenter;
import iyunu.NewTLOL.processor.map.BattleStateProcessorCenter;
import iyunu.NewTLOL.processor.map.BloodFlagProcessorCenter;
import iyunu.NewTLOL.processor.map.GangStateProcessorCenter;
import iyunu.NewTLOL.processor.map.ShenbingProcessorCenter;
import iyunu.NewTLOL.processor.map.ShizhuangProcessorCenter;
import iyunu.NewTLOL.processor.map.TeamProcessorCenter;
import iyunu.NewTLOL.processor.map.UpgradeProcessorCenter;
import iyunu.NewTLOL.redis.RedisActivityPay;
import iyunu.NewTLOL.redis.RedisChampion;
import iyunu.NewTLOL.redis.RedisFbl;
import iyunu.NewTLOL.redis.RedisGangFight;
import iyunu.NewTLOL.redis.RedisGangTask;
import iyunu.NewTLOL.redis.RedisGangWelfare;
import iyunu.NewTLOL.redis.RedisMining;
import iyunu.NewTLOL.redis.RedisPayExchange;
import iyunu.NewTLOL.redis.RedisStopTime;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.log.LogManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.netty.channel.Channel;

public final class ServerManager {

	/**
	 * 私有构造方法
	 */
	private ServerManager() {
	}

	private static ServerManager instance = new ServerManager();

	/**
	 * 获取ServerManager对象
	 * 
	 * @return ServerManager对象
	 */
	public static ServerManager instance() {
		return instance;
	}

	/** 服务器运行状态 **/
	private static boolean isRunning = false;
	private static OnlineProcessorCenter onlineProcessorCenter = null;
	private static ActivityProcessorCenter activityProcessorCenter = null;
	private static MonsterProcessorCenter monsterProcessorCenter = null;

	private static BaseProcessorCenter baseProcessorCenter = null;
	private static BattleStateProcessorCenter battleProcessorCenter = null;
	private static BloodFlagProcessorCenter bloodFlagProcessorCenter = null;
	private static GangStateProcessorCenter gangStateProcessorCenter = null;
	private static TeamProcessorCenter teamProcessorCenter = null;
	private static UpgradeProcessorCenter upgradeProcessorCenter = null;
	private static ShenbingProcessorCenter shenbingProcessorCenter = null;
	private static ShizhuangProcessorCenter shizhuangProcessorCenter = null;
	private static FblProcessorCenter fblProcessorCenter = null;

	private ConcurrentMap<Channel, Role> onlinePlayers = new ConcurrentHashMap<Channel, Role>();
	private ConcurrentMap<Long, Channel> idChannelPair = new ConcurrentHashMap<Long, Channel>();
	private Set<Role> sl = new HashSet<Role>();
	private Set<Role> gsmr = new HashSet<Role>();
	private Set<Role> dl = new HashSet<Role>();
	private Set<Role> ljg = new HashSet<Role>();

	/** 服务器编号 **/
	private String server;
	/** 服务器名称 **/
	private String name;
	/** 服务器集合 **/
	private List<Integer> servers = new ArrayList<Integer>();
	private int srvId = 0;
	/** 临时角色名称 **/
	private Set<String> nicks = new HashSet<String>();
	/** mx4j端口 **/
	public static int MX4J_PORT;
	/** mx4j地址 **/
	public static String MX4J_URL;
	public static String SERVER_RES;

	/**
	 * 服务器启动
	 * 
	 * @throws Exception
	 */
	public static void startup() throws Exception {

		// 加载协议文件
		TLOLManager.initLpb();
		// 加载资源文件
		TLOLManager.initJson();
		// 加载充值活动
		ActivityPayJson.instance().init();
		// 加载玩家补偿
		CompensateManager.intance().init();
		// 加载拍卖行
		AuctionManager.instance().load();
		// 加载帮派
		GangManager.instance().load();
		/** 加载排行榜 */
		BillboardManager.instance().load();
		// 加载激活码
		CdKeyManager.instance().init();
		LogManager.info("【激活码】加载完成");

		QiDaERenManager.instance().init();
		LogManager.info("【七大恶人】加载完成");
		// IO处理器
		IOProcessManager.instance().init();
		onlineProcessorCenter = new OnlineProcessorCenter();
		onlineProcessorCenter.start();
		LogManager.info("【在线角色处理器】加载完成");

		activityProcessorCenter = new ActivityProcessorCenter();
		activityProcessorCenter.start();
		LogManager.info("【活动处理器】加载完成");

		monsterProcessorCenter = new MonsterProcessorCenter();
		monsterProcessorCenter.start();
		LogManager.info("【怪物处理器】加载完成");

		baseProcessorCenter = new BaseProcessorCenter();
		baseProcessorCenter.start();
		LogManager.info("【角色基本信息变化处理器】加载完成");

		battleProcessorCenter = new BattleStateProcessorCenter();
		battleProcessorCenter.start();
		LogManager.info("【战斗状态变化处理器】加载完成");

		bloodFlagProcessorCenter = new BloodFlagProcessorCenter();
		bloodFlagProcessorCenter.start();
		LogManager.info("【血战状态变化处理器】加载完成");

		teamProcessorCenter = new TeamProcessorCenter();
		teamProcessorCenter.start();
		LogManager.info("【队伍变化处理器】加载完成");

		upgradeProcessorCenter = new UpgradeProcessorCenter();
		upgradeProcessorCenter.start();
		LogManager.info("【角色升级处理器】加载完成");

		gangStateProcessorCenter = new GangStateProcessorCenter();
		gangStateProcessorCenter.start();
		LogManager.info("【帮派状态处理器】加载完成");

		shenbingProcessorCenter = new ShenbingProcessorCenter();
		shenbingProcessorCenter.start();
		LogManager.info("【神兵状态处理器】加载完成");

		shizhuangProcessorCenter = new ShizhuangProcessorCenter();
		shizhuangProcessorCenter.start();
		LogManager.info("【时装状态处理器】加载完成");

		BulletinManager.instance().initBulletion();
		LogManager.info("【公告处理器】加载完成");

		fblProcessorCenter = new FblProcessorCenter();
		fblProcessorCenter.start();

		/** 加载公告 **/
		TLOLManager.initBulletin();
		/** 加载双倍 **/
		TLOLManager.initMultiple();

		/** 快3 */
		Kuai3Manager.instance().start();

		/** 帮战 **/
		RedisGangFight redisGangFight = Spring.instance().getBean("redisGangFight", RedisGangFight.class);
		redisGangFight.getApply();

		/** 充值活动 **/
		RedisActivityPay redisActivityPay = Spring.instance().getBean("redisActivityPay", RedisActivityPay.class);
		redisActivityPay.query();

		// CopyOfGangFightManager.init();
		// 时间计数器
		TimeProcessorCenter.instance().init();
		// 加载矿区
		MiningManger.instance().load();
		// 加载帮派任务
		GangTaskManager.instance().load();

		// 查询发布令
		RedisFbl redisFbl = Spring.instance().getBean("redisFbl", RedisFbl.class);
		redisFbl.query();
		// 加载积分榜
		PayExchangeManager.instance().load();
		// 加载日常任务
		DailyManager.instance().load(1);

		isRunning = true;
	}

	/**
	 * 服务器关闭
	 */
	public static void shutdown() {

		isRunning = false;
		try {
			Kuai3Manager.instance().sendAward();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			onlineProcessorCenter.shutdown(); // 关闭【在线角色处理器】
			activityProcessorCenter.shutdown();
			monsterProcessorCenter.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ServerManager.instance().kickAll(); // 踢所有玩家下线
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			RedisChampion redisChampion = Spring.instance().getBean("redisChampion", RedisChampion.class);
			redisChampion.saveChampionId(); // 保存冠军帮ID
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			baseProcessorCenter.shutdown();
			battleProcessorCenter.shutdown();
			gangStateProcessorCenter.shutdown();
			teamProcessorCenter.shutdown();
			upgradeProcessorCenter.shutdown();
			bloodFlagProcessorCenter.shutdown();
			shenbingProcessorCenter.shutdown();
			shizhuangProcessorCenter.shutdown();
			fblProcessorCenter.close(); // 发布令处理器
			BulletinManager.instance().stop(); // 关闭公告处理器
			BattleManager.instance().stopBattleProcessor(); // 关闭【战斗处理器】
			IOProcessManager.instance().shutdown(); // 关闭【IO处理器】
		} catch (Exception e) {
			e.printStackTrace();
		}

		// RoleServiceIfce roleServiceIfce =
		// Spring.instance().getBean("roleService", RoleServiceIfce.class);
		// LogManager.info("存储RoleCard开始");
		// for (RoleCard roleCard : RoleManager.getRoleCardMap().values()) {
		// roleServiceIfce.updateRoleCard(roleCard);
		// }
		// LogManager.info("存储RoleCard结束");

		// 帮战
		try {
			RedisGangFight redisGangFight = Spring.instance().getBean("redisGangFight", RedisGangFight.class);
			redisGangFight.saveApply();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 充值活动
		try {
			RedisActivityPay redisActivityPay = Spring.instance().getBean("redisActivityPay", RedisActivityPay.class);
			redisActivityPay.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储矿区
		try {
			RedisMining redisMining = Spring.instance().getBean("redisMining", RedisMining.class);
			redisMining.saveMiningMap();
			// 存储矿区被抢提示
			redisMining.saveMiningRobMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储帮派福利已领取的ID
		try {
			RedisGangWelfare redisGangWelfare = Spring.instance().getBean("redisGangWelfare", RedisGangWelfare.class);
			redisGangWelfare.saveGangWelFare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储帮派任务
		try {
			RedisGangTask redisGangTask = Spring.instance().getBean("redisGangTask", RedisGangTask.class);
			redisGangTask.saveGangTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储停服时间
		try {
			RedisStopTime redisStopTime = Spring.instance().getBean("redisStopTime", RedisStopTime.class);
			redisStopTime.saveStopTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储发布令
		try {
			RedisFbl redisFbl = Spring.instance().getBean("redisFbl", RedisFbl.class);
			redisFbl.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 存储积分榜
		try {
			if (PayExchangeManager.FLAG) {
				RedisPayExchange redisPayExchange = Spring.instance().getBean("redisPayExchange", RedisPayExchange.class);
				redisPayExchange.savePayExchange();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeProcessorCenter.instance().stop();
		LogManager.info("**********服务器已关闭***************");
	}

	private void clear() {
		server = "";
		name = "";
		MX4J_PORT = 1234;
		MX4J_URL = "127.0.0.1";
		SERVER_RES = "latx";
		srvId = 0;
		servers.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		try {
			InputStream in = new FileInputStream("./conf/server.properties");
			Properties p = new Properties();
			p.load(in);
			clear();
			server = p.getProperty("server");
			name = p.getProperty("name");
			srvId = Translate.stringToInt(p.getProperty("srvId"));
			MX4J_PORT = Translate.stringToInt(p.getProperty("mx4jPort"));
			MX4J_URL = p.getProperty("mx4jUrl");
			SERVER_RES = p.getProperty("server.res");
			String[] strs = p.getProperty("serverId").split(",");
			for (String string : strs) {
				servers.add(Translate.stringToInt(string));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据角色编号检查玩家是否在线
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 是否在线
	 */
	public boolean isOnline(final long roleId) {
		return idChannelPair.containsKey(roleId);
	}

	/**
	 * 获取所有在线角色编号
	 * 
	 * @return 角色编号集合
	 */
	public ArrayList<Long> getOnlineRoleId() {
		return new ArrayList<Long>(idChannelPair.keySet());
	}

	/**
	 * 获取在线人数
	 * 
	 * @return 在线人数
	 */
	public int getOnlineCounter() {
		return idChannelPair.size();
	}

	/**
	 * 通过角色编号获取在线角色对象
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 角色对象
	 */
	public Role getOnlinePlayer(long roleId) {
		Channel channel = idChannelPair.get(roleId);
		if (channel == null) {
			return null;
		}
		return getOnlinePlayer(channel);
	}

	/**
	 * 通过网络连接获取在线角色对象
	 * 
	 * @param channel
	 *            网络连接
	 * @return 角色对象
	 */
	public Role getOnlinePlayer(Channel channel) {
		return onlinePlayers.get(channel);
	}

	/**
	 * 角色上线
	 * 
	 * @param channel
	 *            网络连接
	 * @param role
	 *            角色对象
	 */
	public void online(Channel channel, Role role) {
		onlinePlayers.put(channel, role);
		idChannelPair.put(role.getId(), channel);
		addChatChannel(role);
	}

	/**
	 * 添加聊天频道
	 * 
	 * @param role
	 *            角色对象
	 */
	public void addChatChannel(Role role) {
		switch (role.getVocation()) {
		case sl:
			sl.add(role);
			break;
		case gsmr:
			gsmr.add(role);
			break;
		case dl:
			dl.add(role);
			break;
		case ljg:
			ljg.add(role);
			break;
		default:
			break;
		}
	}

	/**
	 * 删除聊天频道
	 * 
	 * @param role
	 *            角色对象
	 */
	public void removeChatChannel(Role role) {
		switch (role.getVocation()) {
		case sl:
			sl.remove(role);
			break;
		case gsmr:
			gsmr.remove(role);
			break;
		case dl:
			dl.remove(role);
			break;
		case ljg:
			ljg.remove(role);
			break;
		default:
			break;
		}
	}

	/**
	 * 获取聊天频道
	 * 
	 * @param vocation
	 *            职业
	 * @return 角色集合
	 */
	public Set<Role> getChatChannel(Vocation vocation) {
		switch (vocation) {
		case sl:
			return sl;
		case gsmr:
			return gsmr;
		case dl:
			return dl;
		case ljg:
			return ljg;
		default:
			return new HashSet<Role>();
		}
	}

	public void closeChannel(Channel channel) {
		if (channel != null) {
			onlinePlayers.remove(channel);
			channel.close();
			channel = null;
		}
	}

	/**
	 * 角色下线
	 * 
	 * @param channel
	 *            网络连接
	 */
	public void offline(Channel channel, String reason) {
		Role role = onlinePlayers.get(channel);

		if (role != null) {
			if (idChannelPair.containsKey(role.getId())) {
				Channel newChannel = idChannelPair.get(role.getId());
				if (channel == newChannel) {
					RoleManager.logout(role, reason);
				}
			}else{
				closeChannel(channel);
			}
		} else {
			closeChannel(channel);
			LogManager.logout(reason, 0, "查询角色失败"); // 下线日志
		}
	}

	/**
	 * 踢角色下线
	 * 
	 * @param roleId
	 *            角色编号
	 */
	public void kickOffline(long roleId) {
		if (isOnline(roleId)) {
			Role role = getOnlinePlayer(roleId);
			if (role != null) {
				RoleManager.logout(role, "kick命令踢角色下线");
			}
		}
	}

	/**
	 * 提所有玩家下线
	 */
	public void kickAll() {
		for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			RoleManager.logout(role, "kickAll命令踢角色下线");
		}
	}

	/**
	 * 验证服务器编号
	 * 
	 * @param serverId
	 *            服务器编号
	 * @return 验证成功
	 */
	public boolean checkServerId(int serverId) {
		return servers.contains(serverId);
	}

	/**
	 * 验证角色名
	 * 
	 * @param nick
	 *            角色名
	 * @return 角色名已存在
	 */
	public boolean checkNick(String nick) {
		boolean result = nicks.contains(nick);
		nicks.add(nick);
		return result;
	}

	/**
	 * @return the idChannelPair
	 */
	public ConcurrentMap<Long, Channel> getIdChannelPair() {
		return idChannelPair;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the isRunning
	 */
	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * @return the sl
	 */
	public Set<Role> getSl() {
		return sl;
	}

	/**
	 * @return the gsmr
	 */
	public Set<Role> getGsmr() {
		return gsmr;
	}

	/**
	 * @return the dl
	 */
	public Set<Role> getDl() {
		return dl;
	}

	/**
	 * @return the ljg
	 */
	public Set<Role> getLjg() {
		return ljg;
	}

	/**
	 * @return the srvId
	 */
	public int getSrvId() {
		return srvId;
	}

}
