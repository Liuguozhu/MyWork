package iyunu.NewTLOL.message;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PayJson;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.model.mining.MiningPage;
import iyunu.NewTLOL.model.pay.PayInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.model.role.title.instance.Title;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class SendMessage {

	/**
	 * 主动踢人
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void kickOff(Role role, String content) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_kickOff");
				llpMessage.write("content", content);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：主动踢人");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新领取日常任务奖励
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshRecDaily(Role role, int id, int flag) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshRecDaily");
				llpMessage.write("id", id);
				llpMessage.write("flag", flag);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新领取日常任务奖励");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 积分榜刷新
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshPayExchangeContent(Role role) {

		LlpMessage llpMessage = null;
		try {
			if (PayExchangeManager.FLAG && (PayExchangeManager.ENDTIME - System.currentTimeMillis()) > 0) {
				if (role != null && role.getChannel() != null && role.isLogon()) {
					llpMessage = LlpJava.instance().getMessage("s_refreshPayExchangeContent");

					llpMessage.write("leftTime", (int) ((PayExchangeManager.ENDTIME - System.currentTimeMillis()) / 1000));

					List<Long> list = PayExchangeManager.instance().getList();
					int ming = 0;

					for (int j = 0; j < list.size(); j++) {
						LlpMessage msg = llpMessage.write("pays");
						msg.write("mingCi", j + 1);
						if (role.getId() == list.get(j)) {
							ming = j + 1;
						}
						// System.out.println(j + 1);
						msg.write("roleId", list.get(j));
						// System.out.println(list.get(j));
						msg.write("nick", RoleManager.getNameById(list.get(j)));
						// System.out.println(RoleManager.getNameById(list.get(j)));
						msg.write("mark", PayExchangeManager.instance().getPayExchangeMap().get(list.get(j)));
						// System.out.println(PayExchangeManager.instance().getPayExchangeMap().get(list.get(j)));
					}
					llpMessage.write("mark", PayExchangeManager.instance().getPayExchangeMap().containsKey(role.getId()) ? PayExchangeManager.instance().getPayExchangeMap().get(role.getId()) : 0);
					llpMessage.write("mingCi", ming);
					llpMessage.write("money", role.getMoney());
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：积分榜刷新");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新积分榜功能开关 state 0关 1开
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshPayExchange(Role role, int state) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPayExchange");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新积分榜功能开关");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新日常任务开关 state 0关 1开
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshDailyTask(Role role, int state) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshDailyTask");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新日常任务功能开关");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新仓库银两
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshWareHouseCoin(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshWareHouseCoin");
				llpMessage.write("coin", role.getWarehouseCoin());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新仓库银两");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新缺少银两
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshNoCoin(Role role, int type) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNoCoin");
				llpMessage.write("type", type);// 1绑银 2银两 3元宝
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新缺少银两");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新缺少物品
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshNoitem(Role role, int itemId, int num, int price) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNoitem");
				llpMessage.write("itemId", itemId);
				llpMessage.write("num", num);
				llpMessage.write("price", price);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新缺少物品");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 矿区抢夺通知
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void miningNotice(Role role, String notice, long roleId) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_robNotice");
				llpMessage.write("roleId", roleId);
				llpMessage.write("notice", notice);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：矿区抢夺");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 开服七天标记图标
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshSevenEnd(Role role, int flag) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshSevenEnd");
				llpMessage.write("flag", flag);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：开服七天图标");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新帮派战状态
	 * 
	 * @param role
	 *            角色对象
	 */
	// public static void refreshGangFightState() {
	// LlpMessage llpMessage = null;
	// try {
	// llpMessage = LlpJava.instance().getMessage("s_refreshGangFightState");
	// llpMessage.write("type", EGangFightState.state());
	//
	// for (Role role : ServerManager.instance().getOnlinePlayers().values()) {
	// if (role.getGangId() != 0) {
	// role.getChannel().write(llpMessage);
	// }
	// }
	//
	// } catch (Exception e) {
	// LogManager.info("异常报告：刷新帮派战状态");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	//
	// }

	/**
	 * 刷新剩余寻宝次数
	 * 
	 * @param role
	 *            角色对象
	 */
	// public static void refreshHuntTreasureNum(Role role) {
	// LlpMessage llpMessage = null;
	// try {
	// if (role != null && role.getChannel() != null && role.isLogon()) {
	// llpMessage = LlpJava.instance().getMessage("s_refreshHuntTreasureNum");
	// llpMessage.write("huntTreasureNum", role.getHuntTreasureNum());
	// role.getChannel().write(llpMessage);
	// }
	// } catch (Exception e) {
	// LogManager.info("异常报告：发送剩余寻宝次数");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	//
	// }

	/**
	 * 刷新新手引导
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGuide(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshGuide");
				llpMessage.write("index", role.getGuide());
				llpMessage.write("specialIndex", role.getSpecialGuide());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送新手引导");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新掉线原因
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshLogOutReason(Role role, String reason) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshLogOutReason");
				llpMessage.write("reason", reason);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送掉线原因");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新绑银
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendGold(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshGold");
				llpMessage.write("gold", role.getGold());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送绑银信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新BUFF
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendNewBuff(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshBuff");
				for (BuffRole buffRole : role.getBuffs().values()) {
					LlpMessage message = llpMessage.write("buffInfoList");
					message.write("id", buffRole.getId());
					message.write("name", buffRole.getName());
					message.write("state", 0);
					switch (buffRole.getType()) {
					case hp:
					case mp:
					case php:
						message.write("time", -1);
						break;
					default:
						message.write("time", (int) Util.matchZero((buffRole.getFinishTime() - System.currentTimeMillis()) / Time.MILLISECOND));
						break;
					}
					message.write("value", buffRole.getValue());
					switch (buffRole.getType()) {
					case php:
						message.write("type", 1);
						break;
					default:
						message.write("type", 0);
						break;
					}
					message.write("icon", buffRole.getIcon());
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送BUFF信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新聚魂状态
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendJuhun(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshJuhun");
				llpMessage.write("receiveJuhun", role.getReceiveJuhun());
				llpMessage.write("juhunNum", role.getJuHunNum());
				llpMessage.write("wuHun", role.getWuHun());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送聚魂信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新血战标记
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendBlood(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null) {
				llpMessage = LlpJava.instance().getMessage("s_refreshBlood");
				llpMessage.write("flag", role.getBlood());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送血战信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新血战倒计时
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendBloodTime(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTime");
				llpMessage.write("time", ActivityManager.BLOOD_END_TIME - System.currentTimeMillis());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送血战倒计时");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新战力
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendPower(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPower");
				llpMessage.write("power", role.getPower());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送战力");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新连续登录
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendContinueLogon(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshEveryLogon");
				llpMessage.write("day", role.getLogonContinue());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送连续登录信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新经验
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendExp(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshExp");
				llpMessage.write("exp", role.getExp());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送经验信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 升级
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendLevel(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_upgrade");
				llpMessage.write("level", role.getLevel());
				llpMessage.write("expMax", role.getExpMax());
				llpMessage.write("exp", role.getExp());
				llpMessage.write("free", role.getFree());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送等级信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新元宝
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendMoney(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshMoney");
				llpMessage.write("money", role.getMoney());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送元宝信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新签到
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendSign(Role role, int newPickSign, int newSign) {
		LlpMessage llpMessage = null;

		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshSign");
				llpMessage.write("newSign", newSign);
				llpMessage.write("newPickSign", newPickSign);
				llpMessage.write("canSign", role.getHaveSign());
				llpMessage.write("canAddSign", ((role.getHaveSign() == 0 && role.getSignList().size() < role.getSignDay()) ? 1 : 0));
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送签到信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新元气值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendYuanQi(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshYuanQi");
				llpMessage.write("yuanQi", role.getYuanQi());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送元气值信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新累积充值的金额
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshPlusMoney(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPlusMoney");
				llpMessage.write("plusMoney", role.getPlusMoney());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新累积充值的金额");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送技能信息
	 * 
	 * @param role
	 */
	public static void sendSkill(Role role) {
		LlpMessage message = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				message = LlpJava.instance().getMessage("s_refreshSkill");

				Set<Entry<Integer, Integer>> set = role.getSkillMap().entrySet();
				for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Integer, Integer> entry = it.next();
					RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(entry.getValue());
					if (roleSkill != null) {
						LlpMessage llpMessage = message.write("skillList");
						llpMessage.write("skillId", roleSkill.getId());
						llpMessage.write("position", entry.getKey());
						int state = 0;

						if (roleSkill.getLimitPosition() == 0) {
							state = 1;
						} else {
							RoleSkill limitRoleSkill = SkillJson.instance().getRoleSkillById(role.getSkillMap().get(roleSkill.getLimitPosition()));
							if (limitRoleSkill != null && limitRoleSkill.getLevel() >= roleSkill.getLimitLevel()) {
								state = 1;
							}
						}

						if (roleSkill.getLevel() == roleSkill.getLevelMax()) {
							state = 0;
						}
						if (roleSkill.getNextSkill() == 0) {
							state = 1;
						}

						llpMessage.write("state", state);
					}
				}

				role.getChannel().write(message);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送技能信息");
			e.printStackTrace();
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

	/**
	 * 发送角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendRole(Role role) {
		LlpMessage message = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				message = LlpJava.instance().getMessage("s_role");
				message.write("id", role.getId());
				message.write("nick", role.getNick());
				message.write("figure", role.getFigure());
				message.write("vocation", role.getVocation().getName());
				for (Title tittle : role.getTitles()) {
					message.write("titles", tittle.getName());
				}
				if (role.getTitle() != null) {
					message.write("titleIndex", role.getTitle().getIndex());
				} else {
					message.write("titleIndex", -1);
				}

				message.write("level", role.getLevel());
				message.write("exp", role.getExp());
				message.write("expMax", role.getExpMax());
				message.write("gold", role.getGold());
				message.write("money", role.getMoney());
				message.write("hp", role.getHp());
				message.write("hpMax", role.getHpMax());
				message.write("mp", role.getMp());
				message.write("mpMax", role.getMpMax());
				message.write("strength", role.getShowStrength());
				message.write("intellect", role.getShowIntellect());
				message.write("physique", role.getShowPhysique());
				message.write("agility", role.getShowAgility());
				message.write("free", role.getFree());
				message.write("pAttack", role.getPattack());
				message.write("pDefence", role.getPdefence());
				message.write("mAttack", role.getMattack());
				message.write("mDefence", role.getMdefence());
				message.write("hit", role.getHit());
				message.write("dodge", role.getDodge());
				message.write("crit", role.getCrit());
				message.write("parry", role.getParry());
				message.write("speed", role.getSpeed());
				message.write("freeSkill", role.getFreeSkill());
				message.write("gangId", role.getGangId());
				if (role.getGang() != null) {
					message.write("gangName", role.getGang().getName());
					message.write("jobTitle", role.getGang().getJobTitle(role).ordinal());
				} else {
					message.write("gangName", "");
					message.write("jobTitle", GangJobTitle.NULL.ordinal());
				}
				message.write("tribute", role.getTribute());
				message.write("totalTribute", role.getTotalTribute());
				message.write("power", role.getPower());

				role.getChannel().write(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

	/**
	 * 通知客户端有新邮件
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendNewMail(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_newMail");
				llpMessage.write("roleId", role.getId());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：通知客户端有新邮件");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 可提升提示
	 * 
	 * @param role
	 *            index提示编号，type true加 false删 角色对象
	 */
	public static void refreshUpTipOne(Role role, int index, boolean type) {
		// System.out.println(UptipEvent.values()[index].name() +
		// "  $$$$$$$$$   " + type);
	}

	/**
	 * 可提升提示 上线多发
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshUpTip(Role role) {
		Iterator<Entry<Integer, Boolean>> it = role.getUpTipsInit().entrySet().iterator();
		Set<Integer> set = new HashSet<>();
		while (it.hasNext()) {
			Entry<Integer, Boolean> entry = it.next();
			if (entry.getValue()) {
				set.add(entry.getKey());
			}
		}
		for (Integer index : set) {
			if (UptipEvent.values()[index] != UptipEvent.伙伴升级 && UptipEvent.values()[index] != UptipEvent.伙伴进阶) {
				refreshUpTipOne(role, index, true);
			}
		}
	}

	/**
	 * 发送血和蓝
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendHpAndMp(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshHpAndMp");
				llpMessage.write("hp", role.getHp());
				llpMessage.write("hpMax", role.getHpMax());
				llpMessage.write("mp", role.getMp());
				llpMessage.write("mpMax", role.getMpMax());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送血和蓝");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送部位强化
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendBodyIntensify(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshBody");

				Set<Entry<EEquip, Integer>> set = role.getBodyIntensify().entrySet();
				for (Iterator<Entry<EEquip, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<EEquip, Integer> entry = it.next();
					EEquip part = entry.getKey();
					int level = entry.getValue();
					BodyInfo bodyInfo = IntensifyJson.instance().getEquipIntensifyMap(part, level);
					if (bodyInfo != null) {
						LlpMessage message = llpMessage.write("bodyInfoList");
						message.write("part", part.ordinal());
						message.write("level", level);
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送部位强化");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送配件信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendBodyRabbet(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshStone");

				Set<Entry<EEquip, HashMap<Integer, Rabbet>>> set = role.getBodyRabbet().entrySet();
				for (Iterator<Entry<EEquip, HashMap<Integer, Rabbet>>> it = set.iterator(); it.hasNext();) {
					LlpMessage message = llpMessage.write("stonePartList");

					Entry<EEquip, HashMap<Integer, Rabbet>> entry = it.next();
					EEquip part = entry.getKey();
					message.write("part", part.ordinal());

					Map<Integer, Rabbet> rabbetMap = entry.getValue();
					Set<Entry<Integer, Rabbet>> stoneSet = rabbetMap.entrySet();
					for (Iterator<Entry<Integer, Rabbet>> itr = stoneSet.iterator(); itr.hasNext();) {
						LlpMessage msg = message.write("stoneInfoList");
						Entry<Integer, Rabbet> stoneEntry = itr.next();
						Integer position = stoneEntry.getKey();
						Rabbet rabbet = stoneEntry.getValue();
						msg.write("position", position);
						msg.write("open", rabbet.getOpen());

						if (rabbet.getOpen() == 1) {
							Item stone = ItemJson.instance().getItem(rabbet.getStoneId());
							if (stone != null) {
								msg.write("id", stone.getId());
								msg.write("icon", stone.getIcon());
							}
						}
					}
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送部位镶嵌");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送充值比例
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendPayRatio(Role role) {
		LlpMessage message = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				message = LlpJava.instance().getMessage("s_payRatio");

				List<PayInfo> payInfoList = PayJson.instance().getPayInfos();
				for (PayInfo payInfo : payInfoList) {
					LlpMessage payInfoMsg = message.write("payInfoList");
					payInfoMsg.write("rmb", payInfo.getRMB());
					payInfoMsg.write("yb", payInfo.getYB());
				}
				role.getChannel().write(message);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送充值比例");
			e.printStackTrace();
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

	/**
	 * @function 发送剩余屏蔽怪时间
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendCloaking(Role role) {
		LlpMessage message = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				message = LlpJava.instance().getMessage("s_refreshCloaking");
				BuffRole buffRole = role.getBuffs().get(EBuffType.xunyicao);
				if (buffRole == null) {
					message.write("cloaking", 0);
				} else {
					int cloaking = (int) Util.matchZero((buffRole.getFinishTime() - System.currentTimeMillis()) / Time.MILLISECOND);
					message.write("cloaking", cloaking);
				}
				role.getChannel().write(message);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送剩余屏蔽怪时间");
			e.printStackTrace();
		} finally {
			if (message != null) {
				message.destory();
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
	 * @function 发送答题积分
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendScore(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshScore");
				llpMessage.write("score", role.getDailyAnswerScore());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送答题积分信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送玩家等级排行榜位置
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param rank
	 *            排名
	 * @date 2014年4月10日
	 */
	public static void sendRankingLevel(Role role, int rank) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshRankingLevel");
				llpMessage.write("rankingLevel", rank);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送等级排行榜位置信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送累计充值开关
	 */
	public static void refreshPlusMoneyFlag(Role role, int flag) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPlusMoneyFlag");
				llpMessage.write("plusMoneyFlag", flag);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送累计充值开关");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送玩家技能点
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendRefreshFree(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshFree");
				llpMessage.write("freeSkill", role.getFreeSkill());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送玩家技能点");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新属性
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendSttribute(Role role) {
		LlpMessage message = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				message = LlpJava.instance().getMessage("s_attributeInfo");

				message.write("hp", role.getHp());
				message.write("hpMax", role.getHpMax());
				message.write("mp", role.getMp());
				message.write("mpMax", role.getMpMax());
				message.write("strength", role.getShowStrength());
				message.write("intellect", role.getShowIntellect());
				message.write("physique", role.getShowPhysique());
				message.write("agility", role.getShowAgility());
				message.write("free", role.getFree());
				message.write("pAttack", role.getPattack());
				message.write("pDefence", role.getPdefence());
				message.write("mAttack", role.getMattack());
				message.write("mDefence", role.getMdefence());
				message.write("hit", role.getHit());
				message.write("dodge", role.getDodge());
				message.write("crit", role.getCrit());
				message.write("parry", role.getParry());
				message.write("speed", role.getSpeed());
				message.write("power", role.getPower());

				role.getChannel().write(message);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新属性");
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

	/**
	 * @function 发送VIP
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendRefreshVip(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshVip");
				llpMessage.write("vip", role.getVip().getLevel().ordinal());

				EVip[] vips = EVip.values();
				for (int i = 1; i < vips.length; i++) {
					EVip vip = vips[i];
					if (role.getVip().isVip(vip)) {
						LlpMessage message = llpMessage.write("vipInfoList");
						message.write("vip", vip.ordinal());
						message.write("desc", role.getVip().getVipTime(vip));
					}
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送VIP");
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送已击杀怪物的位置
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */

	/**
	 * 刷新小助手信息
	 * 
	 * @param role
	 *            角色对象
	 */
	// public static void refreshHelper(Role role) {
	// LlpMessage llpMessage = null;
	// try {
	// if (role != null && role.getChannel() != null && role.isLogon()) {
	// llpMessage = LlpJava.instance().getMessage("s_refreshHelper");
	// llpMessage.write("myScore", role.getLivenessScore());
	//
	// for (Helper helper : HelperJson.instance().getHelpers().values()) {
	// if (!helper.getType().equals(EHelper.mall)) {
	// LlpMessage message = llpMessage.write("helperInfoList");
	// message.write("index", helper.getIndex());
	// message.write("name", helper.getName());
	// message.write("type", helper.getType().ordinal());
	// int num =
	// Util.matchSmaller(Translate.integerToInt(role.getHelper().get(helper.getType())),
	// helper.getSum());
	// message.write("num", num);
	// message.write("sum", helper.getSum());
	// message.write("button", helper.getButton());
	// message.write("time", 0);
	//
	// // long start = Time.getTodayTime(helper.getStartH(),
	// // helper.getStartM(), helper.getStartS());
	// // long end = Time.getTodayTime(helper.getEndH(),
	// // helper.getEndM(), helper.getEndS());
	// // long now = System.currentTimeMillis();
	// // if (now < start) {
	// // int time = (int) ((start - now) / Time.MILLISECOND);
	// // message.write("time", time);
	// // System.out.println("小助手活动时间======================" +
	// // time);
	// // } else if (now <= end) {
	// // message.write("time", 0); //
	// // System.out.println("小助手活动时间======================" +
	// // 0);
	// // } else {
	// // message.write("time", -1);
	// // System.out.println("小助手活动时间======================" +
	// // (-1));
	// // }
	//
	// if (num >= helper.getSum()) {
	// message.write("finish", 1);
	// } else {
	// message.write("finish", 0);
	// }
	//
	// message.write("score", helper.getScore());
	// message.write("level", helper.getLevel());
	// }
	// }
	// role.getChannel().write(llpMessage);
	// }
	// } catch (Exception e) {
	// LogManager.info("异常报告：发送小助手信息");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	// }

	/**
	 * 刷新小助手状态变化
	 * 
	 * @param role
	 *            角色对象
	 * @param index
	 *            小助手索引
	 */
	// public static void refreshHelperState(Role role, EHelper type) {
	// LlpMessage llpMessage = null;
	// try {
	// if (role != null && role.getChannel() != null && role.isLogon()) {
	// llpMessage = LlpJava.instance().getMessage("s_refreshHelperState");
	//
	// Helper helper = HelperJson.instance().getHelperByType(type);
	// if (helper != null) {
	//
	// llpMessage.write("index", helper.getIndex());
	// int num =
	// Util.matchSmaller(Translate.integerToInt(role.getHelper().get(helper.getType())),
	// helper.getSum());
	// llpMessage.write("num", num);
	// llpMessage.write("time", 0);
	//
	// // long start = Time.getNextTime(helper.getStartH(),
	// // helper.getStartM(), helper.getStartS());
	// // long end = Time.getNextTime(helper.getEndH(),
	// // helper.getEndM(), helper.getEndS());
	// // long now = System.currentTimeMillis();
	// //
	// // if (now < start) {
	// // int time = (int) ((start - now) / Time.MILLISECOND);
	// // llpMessage.write("time", time);
	// // System.out.println("小助手活动时间======================" +
	// // time);
	// // } else if (now <= end) {
	// // llpMessage.write("time", 0); //
	// // System.out.println("小助手活动时间======================" + 0);
	// // } else {
	// // llpMessage.write("time", -1);
	// // System.out.println("小助手活动时间======================" +
	// // (-1));
	// // }
	//
	// if (num >= helper.getSum()) {
	// llpMessage.write("finish", 1);
	// } else {
	// llpMessage.write("finish", 0);
	// }
	// llpMessage.write("myScore", role.getLivenessScore());
	// role.getChannel().write(llpMessage);
	// }
	// }
	// } catch (Exception e) {
	// LogManager.info("异常报告：发送小助手状态");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	// }

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
	 * @function 刷新添加好友提示
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年5月9日
	 */
	public static void refreshFriendHint(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshFriendHint");
				llpMessage.write("state", 1);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新添加好友提示");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新大礼包
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshSurprise(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				if (!role.getSurprise().isEmpty()) {
					llpMessage = LlpJava.instance().getMessage("s_refreshSurprise");
					llpMessage.write("id", role.getSurprise().get(0));
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新大礼包");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 等级礼包通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param state
	 *            通知状态
	 * @date 2014年5月23日
	 */
	public static void levelGiftInform(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_levelGiftInform");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：等级礼包通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 活跃度礼包通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param state
	 *            通知状态
	 * @date 2014年5月23日
	 */
	public static void helperAwardInform(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_helperAwardInform");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：活跃度礼包通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 首冲礼包通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param state
	 *            通知状态
	 * @date 2014年5月23日
	 */
	public static void payInform(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_payInform");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：首冲礼包通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 在线奖励通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param state
	 *            通知状态
	 * @date 2014年5月23日
	 */
	public static void onlineAwardInform(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_onlineAwardInform");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：在线奖励通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新好友列表或黑名单列表或好友申请列表
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param list
	 *            好友列表或黑名单列表或好友申请列表
	 * @param type
	 *            列表类型
	 * @date 2014年6月6日
	 */
	public static void refreshFriendOrBlackList(Role role, List<Long> list, int type) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshFriendOrBlack");
				llpMessage.write("type", type);
				List<Long> temporaryList = new ArrayList<Long>();
				for (Long friendId : list) {
					LlpMessage friendOrBlackInfo = llpMessage.write("friendOrBlackInfoList");
					RoleCard roleCard = RoleManager.getRoleCardByRoleId(friendId);

					if (roleCard != null) {
						friendOrBlackInfo.write("id", roleCard.getId());
						friendOrBlackInfo.write("nick", roleCard.getNick());
						friendOrBlackInfo.write("figure", roleCard.getFigure());
						friendOrBlackInfo.write("vocation", roleCard.getVocation().getName());
						friendOrBlackInfo.write("level", roleCard.getLevel());
						friendOrBlackInfo.write("isOnline", Util.trueOrFalse(ServerManager.instance().isOnline(roleCard.getId())));
					} else {
						temporaryList.add(friendId);
					}
				}

				role.getFriendList().removeAll(temporaryList);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新好友列表或黑名单列表或好友申请列表");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 好友通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param reason
	 *            通知
	 * @date 2014年5月23日
	 */
	public static void friendInform(Role role, String reason) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_friendInform");
				llpMessage.write("reason", reason);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：好友通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新名称
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshNick(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshNick");
				llpMessage.write("nick", role.getNick());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新名称");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 发送千层塔当前层数
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年8月14日
	 */
	public static void sendCurrentFloorId(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshCurrentFloor");
				llpMessage.write("currentFloorId", role.getCurrentFloorId());
				llpMessage.write("historyFloorId", role.getHistoryFloorId());
				llpMessage.write("nullResetNum", role.getNullResetNum());
				int currentNum = role.getVip().getLevel().getQiancengtaAdd() - role.getMoneyResetNum();
				llpMessage.write("moneyResetNum", currentNum > 0 ? currentNum : 0);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送千层塔当前层数");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新银两
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendCoin(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshCoin");
				llpMessage.write("coin", role.getCoin());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送银两信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新绑银使用上限
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendGoldLimit(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshGoldLimit");
				llpMessage.write("goldLimit", RoleManager.MAX_COST_GOLD_EVERYDAY);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新绑银使用上限");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新今日已使用绑银数量
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendGoldNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshGoldNum");
				llpMessage.write("goldNum", role.getCostGold());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新今日已使用绑银数量");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新首充双倍
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshFirstDouble(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshFirstDouble");
				for (int i = 0; i < role.getFirstDouble().size(); i++) {
					llpMessage.write("haveFirst", role.getFirstDouble().get(i));
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新首充双倍");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新聊天Vip开关
	 * @author LuoSR
	 * @param role
	 * @date 2014年9月1日
	 */
	public static void sendOpenVipChat(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshVipChatOpen");
				llpMessage.write("openVipChat", role.getOpenVipChat());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新聊天Vip开关");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 每日答题通知(所有人)
	 * @author LuoSR
	 * @date 2014年9月10日
	 */
	public static void answerInformForAll() {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_answerInform");
			llpMessage.write("type", 1);

			for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role != null && role.getChannel() != null && role.isLogon() && role.getLevel() >= 15) {
					role.getChannel().write(llpMessage);

					HelperMessage.refreshDailyInfo(role); // 刷新每日活动状态
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：每日答题通知(所有人)");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 每日答题通知(单人)
	 * @author LuoSR
	 * @date 2014年9月10日
	 */
	public static void answerInformForOne(Role role) {
		LlpMessage llpMessage = null;
		try {

			if (role != null && role.getChannel() != null && role.isLogon() && role.getLevel() >= 15) {
				llpMessage = LlpJava.instance().getMessage("s_answerInform");
				llpMessage.write("type", 1);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：每日答题通知(单人)");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 血战开始通知
	 * @author fhy
	 * @date 2014年9月15日
	 */
	public static void refreshBloodSend(Role role) {
		LlpMessage llpMessage = null;
		try {

			if (role != null && role.getChannel() != null && role.isLogon() && role.getLevel() >= 35) {
				llpMessage = LlpJava.instance().getMessage("s_refreshBloodSend");
				llpMessage.write("type", 1);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告： 血战开始通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 延迟通知
	 * @author fhy
	 * @date 2014年9月15日
	 */
	public static void refreshRewardDelay(Role role, String word) {
		LlpMessage llpMessage = null;
		try {

			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshRewardDelay");
				llpMessage.write("word", word);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：延迟通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 恶魔岛开始通知
	 * @author fhy
	 * @date 2014年12月10日
	 */
	public static void refreshEmoSend(Role role, int type) {
		LlpMessage llpMessage = null;
		try {

			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshEmoSend");
				llpMessage.write("type", type);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告： 恶魔岛开始通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新矿区外框
	 * @author fhy
	 * @param role
	 * @date 2014年10月11日
	 */
	public static void refreshMiningList(Role role, int page) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshMiningList");

				MiningPage miningPage = MiningManger.instance().getPageMining(page);
				List<Mining> list = miningPage.getMiningList();

				for (int i = 0; i < list.size(); i++) {
					LlpMessage msg = llpMessage.write("minings");
					Mining mining = list.get(i);
					msg.write("index", mining.getIndex());
					msg.write("flag", mining.getFlag());
					if (mining.getRole() != null) {
						msg.write("name", RoleManager.getNameById(mining.getRole().getId()));
						msg.write("roleId", mining.getRole().getId());
					} else {
						msg.write("name", "");
						msg.write("roleId", 0l);
					}
					msg.write("rate", MiningManger.instance().countRate(mining.getStartTime()));
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新矿区外框");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新活力值
	 * 
	 * @param role
	 */
	public static void refreshEnergy(Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_energyInit");
			llpMessage.write("energy", role.getEnergy());
			llpMessage.write("energyMax", role.getEnergyMax());
			int timeOut = (int) ((role.getEnergyTime() + Time.MINUTE_MILLISECOND * 10 - System.currentTimeMillis()) / Time.MILLISECOND) + 1;
			llpMessage.write("timeOut", timeOut);
			role.getChannel().write(llpMessage);
		} catch (Exception e) {
			LogManager.info("异常报告：刷新活力值");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
