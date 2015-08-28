package iyunu.NewTLOL.message;

import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class PartnerMessage {

	/**
	 * 刷新伙伴经验
	 * 
	 * @param role
	 *            角色对象
	 * @param partner
	 *            伙伴对象
	 */
	public static void sendPartnerExp(Role role, Partner partner) {
		// LogManager.info("报告：发送伙伴经验信息");
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPExp");
				llpMessage.write("partnerId", partner.getId());
				llpMessage.write("exp", partner.getExp());
				role.getChannel().write(llpMessage);
				LogManager.info("刷新伙伴经验");
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送伙伴经验信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新伙伴招募标记
	 * 
	 * @param role
	 *            角色对象
	 * @param stone
	 *            伙伴对象
	 */
	public static void sendPartnerRefresh(Role role) {
		// LogManager.info("报告：发送伙伴招募标记");
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPartnerFlag");
				long time1 = role.getInnTime() - System.currentTimeMillis() > 0 ? (role.getInnTime() - System.currentTimeMillis()) : -1l;
				llpMessage.write("innTime1", time1 / 1000);
				long time2 = role.getInnTime2() - System.currentTimeMillis() > 0 ? (role.getInnTime2() - System.currentTimeMillis()) : -1l;
				llpMessage.write("innTime2", time2 / 1000);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送招募伙伴标记");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 伙伴升级
	 * 
	 * @param role
	 *            角色对象
	 * @param partner
	 *            伙伴对象
	 */
	public static void sendPartnerLevel(Role role, Partner partner) {
		// LogManager.info("报告：发送伙伴升级信息");
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_pUpgrade");
				llpMessage.write("partnerId", partner.getId());
				llpMessage.write("level", partner.getLevel());
				llpMessage.write("expMax", partner.getExpMax());
				llpMessage.write("exp", partner.getExp());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送伙伴升级信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	// TODO 新加的1 记得加协议
	/**
	 * @function 发送List伙伴信息
	 * @author fhy
	 */
	// public static void sendListPartners(Role role, List<Partner> list) {
	// LlpMessage llpMessage = null;
	// try {
	// if (role != null && role.getChannel() != null && role.isLogon()) {
	// llpMessage = LlpJava.instance().getMessage("s_refreshListPartner");
	// for (Partner partner : list) {
	// LlpMessage message = llpMessage.write("partnerList");
	// message.write("operateFlag", partner.getOperateFlag().ordinal());
	// message.write("id", partner.getId());
	// message.write("index", partner.getIndex());
	// message.write("level", partner.getLevel());
	// message.write("exp", partner.getExp());
	// message.write("potentialHp", partner.getPotentialHp());
	// message.write("potentialAttack", partner.getPotentialAttack());
	// message.write("potentialDefence", partner.getPotentialDefence());
	// message.write("potentialSpeed", partner.getPotentialSpeed());
	// message.write("hp", partner.getHp());
	// message.write("hpMax", partner.getHpMax());
	// message.write("pAttack", partner.getPattack());
	// message.write("pDefence", partner.getPdefence());
	// message.write("mAttack", partner.getMattack());
	// message.write("mDefence", partner.getMdefence());
	// message.write("speed", partner.getSpeed());
	// message.write("hit", partner.getHit());
	// message.write("dodge", partner.getDodge());
	// message.write("crit", partner.getCrit());
	// message.write("parry", partner.getParry());
	// message.write("evolveExp", partner.getEvolveExp());
	// message.write("turnState", partner.getTurnState());
	//
	// for (Integer skillId : partner.getSkills()) {
	// message.write("partnerSkillList", skillId);
	// }
	// }
	// role.getChannel().write(llpMessage);
	// }
	// LogManager.info("发送伙伴协议");
	// } catch (Exception e) {
	// LogManager.info("异常报告：发送list伙伴信息");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	// }

	/**
	 * 
	 * @function 发送所有伙伴信息
	 * @author fhy
	 */
	public static void sendPartners(Role role, List<Partner> list) {
		if (!list.isEmpty()) {
			LlpMessage llpMessage = null;

			try {
				if (role != null && role.getChannel() != null && role.isLogon()) {
//					UptipEvent.伙伴进阶.check(role, role.getUptipBoolean(UptipEvent.伙伴进阶.getOrdinal()));
//					UptipEvent.伙伴升级.check(role, role.getUptipBoolean(UptipEvent.伙伴升级.getOrdinal()));
					llpMessage = LlpJava.instance().getMessage("s_refreshPartner");
					// List<Partner> list = role.getPartners();
					for (Partner partner : list) {
						LlpMessage message = llpMessage.write("partnerList");
						message.write("operateFlag", partner.getOperateFlag().ordinal());
						message.write("id", partner.getId());
						message.write("index", partner.getIndex());
						message.write("level", partner.getLevel());
						message.write("exp", partner.getExp());
						message.write("innateHp", partner.getInnateHp());
						message.write("innateAttack", partner.getInnateAttack());
						message.write("innatePdefence", partner.getInnatePdefence());
						message.write("innateMdefence", partner.getInnateMdefence());
						message.write("hp", partner.getHp());
						message.write("hpMax", partner.getHpMax());
						message.write("pAttack", partner.getPattack());
						message.write("pDefence", partner.getPdefence());
						message.write("mAttack", partner.getMattack());
						message.write("mDefence", partner.getMdefence());
						message.write("speed", partner.getSpeed());
						message.write("hit", partner.getHit());
						message.write("dodge", partner.getDodge());
						message.write("crit", partner.getCrit());
						message.write("parry", partner.getParry());
						message.write("evolveExp", partner.getEvolveExp());
						message.write("turnState", partner.getTurnState());
						message.write("bind", partner.getIsBind());
						message.write("worth", partner.getWorth());
						message.write("talent", partner.getTalent());
						message.write("capability", partner.getCapability());

						for (Integer skillId : partner.getSkills()) {
							message.write("partnerSkillList", skillId);
						}

					}
					role.getChannel().write(llpMessage);
				}
			} catch (Exception e) {
				LogManager.info("异常报告：发送伙伴信息");
				e.printStackTrace();
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}
	}

	/**
	 * 刷新伙伴HP,HP最大值
	 * 
	 * @param role
	 *            角色对象
	 * @param partner
	 *            伙伴对象
	 */
	public static void sendPartnerHp(Role role, Partner partner) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && partner != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPartnerHp");
				llpMessage.write("partnerId", partner.getId());
				llpMessage.write("hp", partner.getHp());
				llpMessage.write("hpMax", partner.getHpMax());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送伙伴HP信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新出战伙伴
	 * 
	 * @param role
	 * @param partnerId
	 */
	public static void refreshFightPartner(Role role) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				// UptipEvent.伙伴进阶.check(role,
				// role.getUptipBoolean(UptipEvent.伙伴进阶.getOrdinal()));
				// UptipEvent.伙伴升级.check(role,
				// role.getUptipBoolean(UptipEvent.伙伴升级.getOrdinal()));
				llpMessage = LlpJava.instance().getMessage("s_refreshFightPartner");

				Iterator<Entry<Integer, Long>> it = role.getPartnerFight().entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Long> entry = it.next();
					LlpMessage message = llpMessage.write("fightPartnerInfoList");
					message.write("index", entry.getKey());
					if (role.checkPartner(entry.getValue())) {
						message.write("partnerId", entry.getValue());
					} else {
						message.write("partnerId", 0l);
					}
				}

				role.getChannel().write(llpMessage);
				LogManager.info("报告：刷新出战伙伴");
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新出战伙伴");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 封装伙伴信息
	 * @author LuoSR
	 * @param message
	 * @param partner
	 * @date 2013年12月17日
	 */
	public static void packagePartner(LlpMessage message, Partner partner) {
		if (partner != null) {

			message.write("id", partner.getId());
			message.write("index", partner.getIndex());
			message.write("level", partner.getLevel());
			message.write("exp", partner.getExp());
			message.write("innateHp", partner.getInnateHp());
			message.write("innateAttack", partner.getInnateAttack());
			message.write("innatePdefence", partner.getInnatePdefence());
			message.write("innateMdefence", partner.getInnateMdefence());
			message.write("hp", partner.getHp());
			message.write("hpMax", partner.getHpMax());
			message.write("pAttack", partner.getPattack());
			message.write("pDefence", partner.getPdefence());
			message.write("mAttack", partner.getMattack());
			message.write("mDefence", partner.getMdefence());
			message.write("speed", partner.getSpeed());
			message.write("hit", partner.getHit());
			message.write("dodge", partner.getDodge());
			message.write("crit", partner.getCrit());
			message.write("parry", partner.getParry());
			message.write("evolveExp", partner.getEvolveExp());
			message.write("turnState", partner.getTurnState());
			message.write("bind", partner.getIsBind());
			message.write("worth", partner.getWorth());
			message.write("talent", partner.getTalent());
			message.write("capability", partner.getCapability());

			for (Integer skillId : partner.getSkills()) {
				message.write("partnerSkillList", skillId);
			}
		}
	}
}
