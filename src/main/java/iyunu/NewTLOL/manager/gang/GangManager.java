package iyunu.NewTLOL.manager.gang;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.redis.RedisChampion;
import iyunu.NewTLOL.redis.RedisGangWelfare;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author fenghaiyu 帮派管理类
 * 
 */
public class GangManager {
	private static GangManager instance = new GangManager();

	public static GangManager instance() {
		return instance;
	}

	public long often = 3;

	public TreeMap<Long, Gang> map = new TreeMap<Long, Gang>(); // <帮派编号，帮派对象>
	public ArrayList<Gang> orderList = null;
	public List<String> nameList = new ArrayList<String>();
	/** 每日烧香次数 */
	public static int SHAOXIANG_LIMIT = 3;
	/** 最高活跃分数 */
	public static int MAXACTIVE = 350;
	/** 初始活跃分数 */
	public static int INITACTIVE = 150;

	/** 活跃值减少到0 ，要解散的帮派 */
	public List<Long> delGangList = new ArrayList<>();
	/** 冠军帮ID */
	public static long championId = 0;
	/** 冠军帮帮主 */
	public static long championLeader = 0;
	/** 当天禁言列表,不允许重复禁言同一人 */
	public static Set<Long> muteIds = new HashSet<>();
	/** 本周已领取过帮派福利的人 */
	public List<Long> hadGetWelfare = new ArrayList<>();

	private GangManager() {

	}

	/**
	 * @return the orderList
	 */
	public ArrayList<Gang> getOrderList() {
		return orderList;
	}

	/**
	 * 请求帮派列表的排序
	 */
	public void sortGangs() {
		orderList = new ArrayList<Gang>(map.values());
		Collections.sort(orderList, comparator);
	}

	private final Comparator<Gang> comparator = new Comparator<Gang>() {
		@Override
		public int compare(Gang gang1, Gang gang2) {
			// 帮级别高排在前，经验高排在前
			if (gang1.getLevel() > gang2.getLevel())
				return -1;
			else if (gang1.getLevel() < gang2.getLevel())
				return 1;
			else {
				if (gang1.getTotalTribute() > gang2.getTotalTribute())
					return -1;
				else if (gang1.getTotalTribute() < gang2.getTotalTribute())
					return 1;
				else
					return 0;
			}
		}

	};

	/**
	 * 取得帮派名字，不存在就返回空字符串
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 帮派名字
	 */
	public String getGangName(long roleId) {
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			if (role.getGang() == null) {
				return "";
			} else {
				return role.getGang().getName();
			}
		} else {
			RoleCard rc = RoleManager.getRoleCardByRoleId(roleId);
			if (rc != null) {
				long gangId = rc.getGangId();
				if (gangId == 0) {
					return "";
				} else {
					Gang gang = GangManager.instance().getGang(gangId);
					if (gang == null) {
						return "";
					} else {
						return gang.getName();
					}
				}
			}
		}
		return "";
	}

	/**
	 * 取得帮派
	 * 
	 * @param gangId
	 *            帮派ID
	 * @return 帮对象
	 */
	public Gang getGang(long gangId) {
		return map.get(gangId);
	}

	/**
	 * @param name
	 *            新建帮派名字加入内存
	 */
	public void addName(String name) {
		nameList.add(name);
	}

	/**
	 * 产生总冠军
	 * 
	 * @param gang
	 */
	public void changeChampion(long gangId) {
		Gang gang = GangManager.instance().getGang(gangId);
		changeChampion(gang);
	}

	/**
	 * 产生总冠军
	 * 
	 * @param gang
	 */
	public void changeChampion(Gang gang) {
		if (gang != null) {
			championId = gang.getId();
			championLeader = gang.getLeader();
			// 帮派帮主刷新
			Role leader = ServerManager.instance().getOnlinePlayer(gang.getLeader());
			if (leader != null) {
				GangMessage.refreshLeader(leader, 1);
			}
			// 帮派成员刷新
			for (RoleCard rc : gang.getMembers()) {
				if (rc != null) {
					Role member = ServerManager.instance().getOnlinePlayer(rc.getId());
					if (member != null) {
						GangMessage.refreshLeader(member, 2);
					}
				}
			}
		}

	}

	public boolean ifChampionGang(Role role) {
		if (role.getGang() == null) {
			return false;
		}
		if (role.getGangId() == championId) {
			return true;
		}
		return false;
	}

	/**
	 * 存数据库时，把MEMBER 转成ID的Set
	 * 
	 * @param set
	 * @return
	 */
	public HashSet<Long> memberToSet(ArrayList<RoleCard> list) {
		HashSet<Long> hashSet = new HashSet<Long>();
		for (RoleCard roleCard : list) {
			hashSet.add(roleCard.getId());
		}
		return hashSet;
	}

	public boolean isChampion(Role role) {
		if (role == null) {
			return false;
		}
		if (role.getGang() == null) {
			return false;
		}
		if (role.getGang().getId() == championId) {
			return true;
		}
		return false;
	}

	/**
	 * 启动时加载所有帮派，把数据库查出来的list,put到map中，并将名字存入nameList
	 */
	public void load() {

		List<Gang> list = new ArrayList<Gang>();
		list = BaseDao.instance().getGangDao().loadAllGang();
		RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);
		if (list != null) {
			for (Gang gang : list) {

				HashSet<Long> set = new HashSet<>();
				// 将字符串查询出来 赋到members
				// 去查找帮中所有人roleCard
				for (Long roleId : gang.getMembersString()) {
					RoleCard rc = roleService.queryRoleCard(roleId);

					if (rc != null && gang.getId() == rc.getGangId()) {
						gang.addMembers(rc);
						rc.setGang(gang);
						// 启动时为了加载帮派的所有人物的roleCard,顺便也加入到总roleCard中，
						// 这样上线加载人物关系card时就可以在里面直接寻找，
						// 没有时才去数据库查询，这样玩家上线减轻很多压力
						RoleManager.saveRoleCard(rc);

						if (gang.getLeader() == rc.getId()) {
							gang.setLeaderName(rc.getNick());
						}
					} else { // 异常帮派成员
						set.add(roleId);
					}
				}
				gang.getMembersString().removeAll(set); // 删除异常帮派成员
				// 加入名字list，加入map
				nameList.add(gang.getName());
				MapManager.instance().initGangMap(gang, MapJson.instance().getMapGangInfo(gang)); // 设置帮派地图
				map.put(gang.getId(), gang);
			}
			list.clear();
			RedisGangWelfare redisGangWelfare = Spring.instance().getBean("redisGangWelfare", RedisGangWelfare.class);
			List<Long> fare = redisGangWelfare.getGangWelfare();
			if (fare != null) {
				hadGetWelfare = fare;
			}
		}

		// ======冠军榜======
		if (map != null && !map.isEmpty()) {

			// 赋值冠军帮
			RedisChampion redisChampion = Spring.instance().getBean("redisChampion", RedisChampion.class);
			championId = redisChampion.getChampionId();

			if (championId != 0 && map.containsKey(championId)) {
				// 冠军帮帮主ID赋值
				championLeader = map.get(championId).getLeader();
			} else {
				championId = 0;
			}

		}

		// 开启活跃值线程
		// startGangActive();
		LogManager.info("【帮派】加载完成");
	}

	/**
	 * 给全帮派人员发送提示
	 * 
	 * @param gang
	 * @param allContent
	 */
	public void sendGangWarnToAll(Gang gang, String allContent) {
		Chat chatAll = new Chat(EChat.gang, 0, "帮派提示", allContent);
		for (RoleCard rc : gang.getMembers()) {
			if (rc != null) {
				if (ServerManager.instance().getOnlinePlayer((rc.getId())) != null) {
					ChatMessage.sendChat(ServerManager.instance().getOnlinePlayer((rc.getId())), chatAll);
				}
			}
		}
	}

	/**
	 * 用于批准进帮，踢人，等 公告
	 * 
	 * @param role
	 *            个人的role
	 * @param personContent
	 *            发给个人的提示内容，用密语频道。
	 */
	public void sendGangWarn(Role role, String personContent) {
		if (role != null) {
			Chat chat = new Chat(EChat.whisper, 0, "帮派提示", personContent);
			ChatMessage.sendChat(role, chat);
		}
	}

	/**
	 * @return the map
	 */
	public TreeMap<Long, Gang> getMap() {
		return map;
	}

	/**
	 * @return the nameList
	 */
	public List<String> getNameList() {
		return nameList;
	}

	// public void startGangActive() {
	//
	// gangActiveExec = new ScheduledThreadPoolExecutor(1);
	//
	// long start = (Time.getTodayTime(23, 59, 59) -
	// System.currentTimeMillis());
	//
	// Runnable runnable = new Runnable() {
	//
	// @Override
	// public void run() {
	// Iterator<Gang> it = map.values().iterator();
	// synchronized (map) {
	// while (it.hasNext()) {
	// Gang g = it.next();
	// // 每天减少10个活跃值
	// g.minnusActive(10, EGangActiveEvent.活跃税.name(), "帮派");
	// }
	// }
	//
	// // 在此处真正删除低于10活跃值的帮派
	// for (Long gangId : getDelGangList()) {
	// map.get(gangId).delGang();
	// }
	// delGangList.clear();
	// }
	// };
	//
	// // gangActiveExec.scheduleAtFixedRate(runnable, start, 24 * 60 * 60 *
	// 1000, TimeUnit.MILLISECONDS);
	//
	// }

	/**
	 * @return the delGangList
	 */
	public List<Long> getDelGangList() {
		return delGangList;
	}

	/**
	 * @param delGangList
	 *            the delGangList to set
	 */
	public void setDelGangList(List<Long> delGangList) {
		this.delGangList = delGangList;
	}

}