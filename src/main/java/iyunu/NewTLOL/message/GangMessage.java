package iyunu.NewTLOL.message;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.manager.gang.GangTaskManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.gang.GangTaskModel;
import iyunu.NewTLOL.model.gang.GangTaskSingle;
import iyunu.NewTLOL.model.gang.res.GangTask;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class GangMessage {

	/**
	 * @function 帮派任务
	 * @author fhy
	 * @date 2014年10月21日
	 */
	public static void refreshGangTaskList(Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refreshGangTaskList");

			if (role != null && role.getChannel() != null && role.isLogon() && role.getGang() != null && !(role.getGang().getLevel() < 2)) {
				ConcurrentMap<Long, GangTaskModel> map = GangTaskManager.instance().getGangTaskMap();
				List<GangTaskSingle> list;
				if (map.containsKey(role.getId())) {
					list = map.get(role.getId()).getTaskList();
				} else {
					list = GangTaskManager.instance().initRoleGangTask(role.getId()).getTaskList();
				}
				Map<Integer, GangTask> taskJsonMap = GangJson.instance().getGangTask();

				// 开始写 gangTaskList,里面有index，和完成情况
				for (int i = 0; i < list.size(); i++) {
					LlpMessage gangTaskListMsg;
					gangTaskListMsg = llpMessage.write("gangTaskList");
					gangTaskListMsg.write("index", list.get(i).getIndex());// 每个
					gangTaskListMsg.write("finish", list.get(i).getFinish());
					// 开始写gangTask， 每行的物品
					ArrayList<MonsterDropItem> items = taskJsonMap.get(list.get(i).getIndex()).getItems();
					for (int j = 0; j < items.size(); j++) {
						LlpMessage gangTaskMsg;
						gangTaskMsg = gangTaskListMsg.write("gangTask");
						gangTaskMsg.write("itemId", items.get(j).getItemId());
						gangTaskMsg.write("num", items.get(j).getNum());
					}
					// 写每行的奖励
					ArrayList<MonsterDropItem> awards = taskJsonMap.get(list.get(i).getIndex()).getAwards();
					for (int j = 0; j < awards.size(); j++) {
						LlpMessage gangTaskMsg = gangTaskListMsg.write("awards");
						gangTaskMsg.write("itemId", awards.get(j).getItemId());
						gangTaskMsg.write("num", awards.get(j).getNum());
					}
				}

				// 写最后的奖励，包括，里面的物品信息和完成情况
				llpMessage.write("finalFinish", map.get(role.getId()).getFinish());
				LlpMessage finalItem = llpMessage.write("finalAward");
				// 取得奖励的index
				int awardIndex = map.get(role.getId()).getAwardIndex();
				finalItem.write("itemId", GangJson.instance().getGangTaskAllAward().get(awardIndex).getItemId());
				finalItem.write("num", GangJson.instance().getGangTaskAllAward().get(awardIndex).getNum());

				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：帮派任务");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新加入帮派或刷新人物身上帮派信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendJoinGang(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshJoinGang");
				llpMessage.write("gangId", role.getGangId());
				if (role.getGang() != null) {
					llpMessage.write("name", role.getGang().getName());
					llpMessage.write("jobTitle", role.getGang().getJobTitle(role).ordinal());
				} else {
					llpMessage.write("name", "");
					llpMessage.write("jobTitle", GangJobTitle.NULL.ordinal());
				}

				llpMessage.write("tribute", role.getTribute());
				llpMessage.write("totalTribute", role.getTotalTribute());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：加入帮派");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送帮派邀请
	 * @param role
	 *            被邀请的角色对象
	 */
	public static void sendInvite(Role role, String gangName, String inviterName, long gangId, long roleId) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshInviteGang");
				llpMessage.write("word", inviterName + " 邀请你加入他的帮派 " + gangName + "，是否同意加入？");
				llpMessage.write("gangId", gangId);
				llpMessage.write("roleId", roleId);

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：帮派邀请");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送帮派邀请确认
	 * @param role
	 *            角色对象
	 */
	public static void sendConfirmInvite(Role role, String word) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshConfirmInvite");
				llpMessage.write("word", word);

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：帮派邀请确认");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮贡
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshTribute(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTribute");
				llpMessage.write("tribute", role.getTribute());
				llpMessage.write("totalTribute", role.getTotalTribute());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送帮贡");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮等级
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangLevel(Gang gang) {
		LlpMessage llpMessage = null;
		try {
			if (gang != null) {
				for (RoleCard rc : gang.getMembers()) {
					if (rc != null && ServerManager.instance().isOnline(rc.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(rc.getId());
						llpMessage = LlpJava.instance().getMessage("s_refreshGangLevel");
						llpMessage.write("level", GangManager.instance().map.get(role.getGangId()).getLevel());
						// System.out.println(role.getNick() + "》帮派等级>>>" +
						// GangManager.instance().map.get(role.getGangId()).getLevel());
						role.getChannel().write(llpMessage);
					}
				}
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送帮派等级");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮等级2
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangLevel2(Role role, int level) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshGangLevel");
				llpMessage.write("level", level);
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送帮派等级2");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 城主身份，冠军帮成员身份
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshLeader(Role role, int identity) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && ServerManager.instance().isOnline(role.getId())) {

				llpMessage = LlpJava.instance().getMessage("s_refreshLeader");
				llpMessage.write("identity", identity);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：城主身份，冠军帮成员身份");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮当前人数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangNum(Gang gang) {
		LlpMessage llpMessage = null;
		try {
			if (gang != null) {
				for (RoleCard rc : gang.getMembers()) {
					if (rc != null && ServerManager.instance().isOnline(rc.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(rc.getId());
						llpMessage = LlpJava.instance().getMessage("s_refreshGangNum");
						llpMessage.write("num", GangManager.instance().map.get(role.getGangId()).getMembers().size());

						// System.out.println(role.getNick() + "》当前人数>>>" +
						// GangManager.instance().map.get(role.getGangId()).getMembers().size());
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送帮派当前人数");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮总人数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangSize(Gang gang) {
		LlpMessage llpMessage = null;
		try {
			if (gang != null) {
				for (RoleCard rc : gang.getMembers()) {
					if (rc != null && ServerManager.instance().isOnline(rc.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(rc.getId());
						llpMessage = LlpJava.instance().getMessage("s_refreshGangSize");
						llpMessage.write("size", GangManager.instance().map.get(role.getGangId()).getSize());

						// System.out.println(role.getNick() + "》总人数>>>" +
						// GangManager.instance().map.get(role.getGangId()).getSize());
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送帮派当前总人数");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮经验
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangExp(Gang gang) {
		LlpMessage llpMessage = null;
		try {
			if (gang != null) {
				int nextExp = 0;
				if (gang.getLevel() < 5) {
					nextExp = GangJson.instance().getGangLevelByIndex(gang.getLevel() + 1).getExp();
				}
				for (RoleCard rc : gang.getMembers()) {
					if (rc != null && ServerManager.instance().isOnline(rc.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(rc.getId());
						llpMessage = LlpJava.instance().getMessage("s_refreshGangExp");
						llpMessage.write("exp", GangManager.instance().map.get(role.getGangId()).getTotalTribute() + "/" + nextExp);
						// System.out.println(role.getNick() + ">帮经验>>>" +
						// GangManager.instance().map.get(role.getGangId()).getTotalTribute());
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送帮派经验");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮人气值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGangActive(Gang gang) {
		LlpMessage llpMessage = null;
		try {
			if (gang != null) {
				for (RoleCard rc : gang.getMembers()) {
					if (rc != null && ServerManager.instance().isOnline(rc.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(rc.getId());
						llpMessage = LlpJava.instance().getMessage("s_refreshGangActive");
						llpMessage.write("active", GangManager.instance().map.get(role.getGangId()).getActive());
						// System.out.println(role.getNick() + ">>人气值>>>" +
						// GangManager.instance().map.get(role.getGangId()).getActive());
						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送帮人气值");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 帮派战奖励通知
	 * 
	 * @param role
	 * @param name
	 * @param result
	 * @param number
	 * @param gangExpBase
	 * @param expBase
	 * @param goldBase
	 * @param gangExp
	 * @param tribute
	 * @param coin
	 */
	public static void refreshGangFightAward(Role role, String name, int result, int number, int gangExpBase, int expBase, int goldBase, int gangExp, int tribute, int coin) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_gangFightAward");
			llpMessage.write("name", name);
			llpMessage.write("result", result);
			llpMessage.write("number", number);
			llpMessage.write("gangExpBase", gangExpBase);
			llpMessage.write("expBase", expBase);
			llpMessage.write("goldBase", goldBase);
			llpMessage.write("gangExp", gangExp);
			llpMessage.write("tribute", tribute);
			llpMessage.write("coin", coin);

			if (role != null && ServerManager.instance().isOnline(role.getId())) {
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：帮派战奖励通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
