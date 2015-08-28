package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RoleManager {

	// roleCardMap 是存放所有玩家信息的map，用于各种需要显示在线和不在线所有玩家信息的查看
	public static final Map<Long, RoleCard> roleCardMap = new HashMap<Long, RoleCard>();
	public static int UPGRADE_FREE = 4; // 角色升级增加自由点数
	public static int UPGRADE_STRENGTH = 1; // 角色升级增加力量
	public static int UPGRADE_INTELLECT = 1; // 智力
	public static int UPGRADE_PHYSIQUE = 1; // 体力
	public static int UPGRADE_AGILITY = 1; // 敏捷
	public static int DEFULT_FREE = 0; // 角色初始化自由点数
	public static int DEFULT_MAP = 17;
	public static int DEFULT_X = 15;
	public static int DEFULT_Y = 18;
	public static int BASE_MAP = 3;
	public static int BASE_X = 62;
	public static int BASE_Y = 72;
	public static int MAX_LEVEL = 80;// 角色等级上限
	public static int MAX_GOLD = 999999999; // 绑银上限
	public static int MAX_COIN = 999999999; // 银两上限
	public static int MAX_WCOIN = 999999999;// 仓库银两上限
	public static int MAX_EXP = 100000000; // 经验钱上限
	public static int MAX_YUANQI = 100000000; // 元气值上限
	public static int MAX_COST_GOLD_EVERYDAY = 99999999; // 每日使用绑银数量上限
	public static int MAX_MAIL = 30;// 最大显示邮件数
	public static int ENERGY_MAX_DEFULT = 30; // 默认活力上限

	/**
	 * 初始化
	 */
	public static void init() {
		try {
			InputStream in = new FileInputStream("./conf/role.properties");
			Properties p = new Properties();
			p.load(in);
			DEFULT_MAP = Integer.parseInt(p.getProperty("defultMap"));
			DEFULT_X = Integer.parseInt(p.getProperty("defultX"));
			DEFULT_Y = Integer.parseInt(p.getProperty("defultY"));
			BASE_MAP = Integer.parseInt(p.getProperty("baseMap"));
			BASE_X = Integer.parseInt(p.getProperty("baseX"));
			BASE_Y = Integer.parseInt(p.getProperty("baseY"));
			DEFULT_FREE = Integer.parseInt(p.getProperty("defultFree"));
			UPGRADE_FREE = Integer.parseInt(p.getProperty("upgradefree"));
			UPGRADE_STRENGTH = Integer.parseInt(p.getProperty("upgradeStrength"));
			UPGRADE_INTELLECT = Integer.parseInt(p.getProperty("upgradeIntellect"));
			UPGRADE_PHYSIQUE = Integer.parseInt(p.getProperty("upgradePhysique"));
			UPGRADE_AGILITY = Integer.parseInt(p.getProperty("upgradeAgility"));
			MAX_LEVEL = Integer.parseInt(p.getProperty("maxLevel"));
			MAX_GOLD = Integer.parseInt(p.getProperty("maxGold"));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 角色下线
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void logout(Role role, String reason) {
		LogManager.logout(reason, role.getId(), role.getNick()); // 下线日志

		try {
			if (role != null) {
				ServerManager.instance().closeChannel(role.getChannel());
				ServerManager.instance().getIdChannelPair().remove(role.getId());
				ServerManager.instance().removeChatChannel(role);

				role.offline();

				MapManager.instance().remove(role); // 从地图中删除
				// ======角色下线，处理队伍======
				if (role.getTeam() != null) {
					Team team = role.getTeam();
					if (team.getLeader().getId() == role.getId()) {
						TeamManager.instance().removeTeam(team); // 如果是队长，则解散队伍
					} else {
						team.removeMember(role); // 不是队长，则从队伍中删除
					}
				}
				// ======角色下线，处理队伍======

				// ======副本信息处理======
				if (role.getMapInfo().getBaseMap().getType().equals(EMapType.raids)) {

					if (!ServerManager.isRunning()) { // 停服踢下线，归还副本次数
						RaidsManager.decRaidsNumber(role, role.getRaidsTeamInfo().getRaidsInfo().getRaidsId());
					}
					RaidsMessage.refreshMiniMap(role.getRaidsTeamInfo()); // 刷新副本小地图
				}
				// ======副本信息处理======

				// ======如果角色在战斗，处理战斗信息======
				BattleProcessorCenter battleProcessorCenter = BattleManager.instance().getBattleProcessorCenter(role.getBattleId());
				if (battleProcessorCenter != null) {
					BattleInfo battleInfo = battleProcessorCenter.getBattleInfo();
					if (battleInfo.getRoleToBattleCharacter().containsKey(role.getId())) {
						battleInfo.decreaseNumber();

						if (battleInfo.getNumber() == 0) {
							battleProcessorCenter.close();

							if (battleInfo.getType() == 2) {
								battleInfo.getMonsterOnMap().setFighting(false);
							}
						} else {
							battleInfo.checkTime();
						}
					}
				}
				// ======如果角色在战斗，处理战斗信息======

				role.setLogoutTime(System.currentTimeMillis());// 设置下线时间
				role.setOnlineTime(role.getOnlineTime() + (int) ((role.getLogoutTime() - role.getLogonTime()) / Time.MILLISECOND)); // 设置在线时长

				RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);
				roleService.update(role);

				// 下线处理
				RoleManager.saveRoleCard(role); // =========下线存储roleCard=====
				LogManager.itemAllLog(role, 1); // 物品日志
				LogManager.roleLogon(role); // 角色登录日志
				LogManager.info("断开连接【" + role.getId() + "】【" + role.getNick() + "】");
			}
		} catch (Exception e) {
			System.out.println("角色下线异常");
			e.printStackTrace();
		}
	}

	/**
	 * 保存RoleCard对象
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void saveRoleCard(Role role) {
		RoleCard roleCard = role.toCard();
		roleCard.setRole(null);
		RoleManager.saveRoleCard(roleCard);
	}

	/**
	 * 根据角色编号获取RoleCard
	 * 
	 * @param roleId
	 *            角色编号
	 * @return RoleCard对象
	 */
	public static RoleCard getRoleCardByRoleId(long roleId) {
		if (roleCardMap.containsKey(roleId)) {
			return roleCardMap.get(roleId);
		}
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			RoleCard roleCard = role.newCard();
			roleCardMap.put(role.getId(), roleCard);
			return roleCard;
		}

		return null;
	}

	/**
	 * 根据roleId取得 名字
	 * 
	 * @param roleId
	 * @return
	 */
	public static String getNameByRoleId(long roleId) {
		if (roleCardMap.containsKey(roleId) && roleCardMap.get(roleId) != null) {
			return roleCardMap.get(roleId).getNick();
		} else {
			return "";
		}
	}

	/**
	 * 根据roleId在线情况，不在线情况取得 名字
	 * 
	 * @param roleId
	 * @return
	 */
	public static String getNameById(long roleId) {
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			return role.getNick();
		} else {
			if (roleCardMap.get(roleId) != null) {
				return roleCardMap.get(roleId).getNick();
			} else {
				return "";
			}
		}
	}

	/**
	 * 保存roleCard
	 * 
	 * @param roleCard
	 *            角色名片对象
	 */
	public static void saveRoleCard(RoleCard roleCard) {
		roleCard.setSrv(ServerManager.instance().getServer());
		roleCardMap.put(roleCard.getId(), roleCard);
	}

	/**
	 * @return the roleCardMap
	 */
	public static Map<Long, RoleCard> getRoleCardMap() {
		return roleCardMap;
	}
}
