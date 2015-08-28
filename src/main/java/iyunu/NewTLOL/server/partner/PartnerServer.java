package iyunu.NewTLOL.server.partner;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.partner.res.GetPartnerRes;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.redis.RedisCenter;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

public class PartnerServer {

	/**
	 * 检查伙伴属性
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkPartner(Role role) {
		for (Partner partner : role.getPartners()) {
			role.getPartnerMap().put(partner.getId(), partner);
		}
		role.getPartners().clear();
		for (Partner partner : role.getPartnerMap().values()) {
			partner.setRole(role);
		}
	}

	/**
	 * 检查酒馆伙伴刷新1
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkInn1(Role role) {
		if (role.getInnTime() < System.currentTimeMillis()) {
			role.setInnTime(-1);
		}
	}

	/**
	 * 检查酒馆伙伴刷新2
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkInn2(Role role) {
		if (role.getInnTime2() < System.currentTimeMillis()) {
			role.setInnTime2(-1);
		}
	}

	public static Partner getRandomParter(int type) {
		List<GetPartnerRes> partnerList = PartnerJson.instance().getGetPartnersByType(type);

		int random = Util.RANDOM.nextInt(10000) + 1;
		int chance = 0;

		for (int i = 0; i < partnerList.size(); i++) {
			chance = partnerList.get(i).getChance() + chance;
			if (random <= chance) {
				return PartnerJson.instance().getNewPartner(partnerList.get(i).getIndex());
			}
		}
		return null;
	}

	/**
	 * 刷新酒馆伙伴
	 * 
	 * @param type
	 *            // 1 方式1级别1, 2 方式1级别2 ,3 方式1级别3, 4 方式2级别1, 5 方式2级别2, 6 方式2级别3
	 *            7 方式3级别1 // 8 方式3级别2 // 9 方式3级别3
	 * @return 伙伴列表
	 */

	public static Partner refresh(int type) {
		Partner partner = null;
		partner = getRandomParter(type);
		if (partner == null) {
			// 此处应该报错，为保证流程，指定一个差的伙伴
			partner = PartnerJson.instance().getNewPartner(109);
			LogManager.exception("刷新伙伴时为null，请检查伙伴概率脚本是否合值小于10000");
		}
		return partner;
	}

	/**
	 * 根据INDEX删除伙伴
	 * 
	 * @param role
	 * @param index
	 */
	public static void delPartnerById(Role role, long id) {
		role.getPartnerMap().remove(id);
	}

	/**
	 * 宠物进阶
	 * 
	 * @param partner
	 */
	public static Partner evolve(Partner partner) {
		Partner evolvePartner = PartnerJson.instance().getPartner(partner.getEvolveId());
		if (evolvePartner == null) {
			return null;
		}
		evolvePartner.setId(partner.getId()); // 伙伴编号不变
		evolvePartner.setLevel(partner.getLevel()); // 等级不变
		evolvePartner.setSkills(partner.getSkills()); // 技能不变
		evolvePartner.setCapability(partner.getCapability()); // 潜力值不变
		evolvePartner.setTalent(partner.getTalent()); // 成长值不变
		countPotential(evolvePartner);
		evolvePartner.setHp(evolvePartner.getHpMax()); // 把血置满
		return evolvePartner;
	}

	/**
	 * 增加经验
	 * 
	 * @param partner
	 *            伙伴对象
	 * @param exp
	 *            增加的经验值
	 * @return 升级了
	 */
	public static boolean addExp(Partner partner, int exp) {
		if (partner != null) {
			if (partner.getLevel() >= 80) {
				partner.setExp(0);
				return false;
			}
			int additionExp = 0;
			exp += additionExp;

			exp += exp * partner.getRole().getVip().getLevel().getPartnerExpAdd();

			partner.setExp(partner.getExp() + exp);

			if (upgrade(partner)) {
				// PartnerMessage.sendPartnerLevel(partner.getRole(), partner);
				// PartnerMessage.sendPartnerHp(partner.getRole(), partner);
			} else {
				// PartnerMessage.sendPartnerExp(partner.getRole(), partner);
			}
			AwardServer.addPartnerExp(partner.getRole(), exp);
			return true;
		}
		return false;
	}

	/**
	 * 伙伴升级，若经验不足返回失败
	 * 
	 * @param partner
	 *            伙伴对象
	 * @return 升级成功
	 */
	public static boolean upgrade(Partner partner) {
		if (partner.getExp() < partner.getExpMax()) {
			return false;
		}
		if (partner.getLevel() >= 80) {
			return false;
		}
		partner.setExp(partner.getExp() - partner.getExpMax());
		partner.setLevel(partner.getLevel() + 1);

		countPotential(partner);
		// partner.setExpMax(PartnerJson.instance().getExpMax(partner.getLevel()));
		partner.setHp(partner.getHpMax());

		if (partner.getExp() >= partner.getExpMax() && partner.getLevel() < 80) {
			return upgrade(partner);
		}
		return true;
	}

	/**
	 * 计算伙伴属性
	 * 
	 * @param partner
	 *            伙伴对象
	 */
	public static void countPotential(Partner partner) {
		int level = partner.getLevel();

		int pattack = 0;
		int mattack = 0;
		if (partner.getVocation().equals(Vocation.wg)) {
			pattack = (int) (partner.getOriginalPattack() + partner.getInnateAttack() / 40f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
			mattack = (int) (partner.getOriginalMattack() + partner.getInnateAttack() / 80f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
		} else if (partner.getVocation().equals(Vocation.ng)) {
			pattack = (int) (partner.getOriginalPattack() + partner.getInnateAttack() / 80f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
			mattack = (int) (partner.getOriginalMattack() + partner.getInnateAttack() / 40f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
		} else {
			pattack = (int) (partner.getOriginalPattack() + partner.getInnateHp() / 80f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
			mattack = (int) (partner.getOriginalMattack() + partner.getInnateHp() / 80f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
		}

		int hpMax = (int) (partner.getOriginalHp() + partner.getInnateHp() / 15f * (level - 1) * ((partner.getTalent() * 0.8f + 1000) / 1000));
		int pdefence = (int) (partner.getOriginalPdefence() + partner.getInnatePdefence() / 60f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
		int mdefence = (int) (partner.getOriginalMdefence() + partner.getInnateMdefence() / 60f * (level - 1) * ((partner.getTalent() * 1f + 1000) / 1000));
		int crit = partner.getOriginalCrit();
		int hit = partner.getOriginalHit();
		int dodge = partner.getOriginalDodge();
		int parry = partner.getOriginalParry();
		int speed = partner.getOriginalSpeed();

		partner.setHpMax(hpMax);
		partner.setPattack(pattack);
		partner.setPdefence(pdefence);
		partner.setMattack(mattack);
		partner.setMdefence(mdefence);
		partner.setCrit(crit);
		partner.setHit(hit);
		partner.setDodge(dodge);
		partner.setParry(parry);
		partner.setSpeed(speed);

		partner.setExpMax(PartnerJson.instance().getExpMax(partner.getLevel()));

		int worth = (int) ((partner.getInnateHp() + partner.getInnateAttack() + partner.getInnatePdefence() + partner.getInnateMdefence()) / 4f);
		worth = worth > 0 ? worth : 0;
		partner.setWorth((int) (worth * (partner.getTalent() + 1000f) / 1000f));
	}

	/**
	 * 生命值成长
	 * 
	 * @param color
	 *            颜色
	 * @return 成长
	 */
	// public static float hpGrowth(EColor color) {
	// switch (color) {
	// case orange:
	// return 4.15f;
	// case purple:
	// return 2.55f;
	// case blue:
	// return 2.45f;
	// case green:
	// return 2.15f;
	// default:
	// return 2.15f;
	// }
	// }

	/**
	 * 攻击值成长
	 * 
	 * @param color
	 *            颜色
	 * @return 成长
	 */
	// public static float attackGrowth(EColor color) {
	// switch (color) {
	// case orange:
	// return 7.5f;
	// case purple:
	// return 4.65f;
	// case blue:
	// return 4.1f;
	// case green:
	// return 3.5f;
	// default:
	// return 3.5f;
	// }
	// }

	/**
	 * 防御值成长
	 * 
	 * @param color
	 *            颜色
	 * @return 成长
	 */
	// public static float defenceGrowth(EColor color) {
	// switch (color) {
	// case orange:
	// return 3.5f;
	// case purple:
	// return 3f;
	// case blue:
	// return 2.5f;
	// case green:
	// return 2f;
	// default:
	// return 2f;
	// }
	// }

	/**
	 * 敏捷值成长
	 * 
	 * @param color
	 *            颜色
	 * @return 成长
	 */
	// public static float speedGrowth(EColor color) {
	// switch (color) {
	// case orange:
	// return 1.6f;
	// case purple:
	// return 0.8f;
	// case blue:
	// return 0.7f;
	// case green:
	// return 0.6f;
	// default:
	// return 0.6f;
	// }
	// }

	/**
	 * 自动恢复伙伴生命值
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void autoRecoverHp(Role role) {

		if (role != null && role.getBuffs().containsKey(EBuffType.php)) {
			BuffRole buffRole = role.getBuffs().get(EBuffType.php);

			for (Long partnerId : role.getPartnerFight().values()) {
				if (buffRole != null && partnerId != null && partnerId != 0) {

					int hp = buffRole.getValue();
					if (hp > 0) {
						Partner partner = role.findPartner(partnerId);
						if (partner != null) {
							int need = partner.getHpMax() - partner.getHp();
							if (need > 0) {
								if (need > hp) {
									partner.setHp(partner.getHp() + hp);
									BuffServer.removeBuff(role, EBuffType.php);
									PartnerMessage.sendPartnerHp(role, partner);
									break;
								} else {
									partner.setHp(partner.getHpMax());
									buffRole.setValue(hp - need);
									PartnerMessage.sendPartnerHp(role, partner);
								}
							}
						}
					} else {
						BuffServer.removeBuff(role, EBuffType.php);
						break;
					}
				}
			}
			// ======刷新BUFF协议======
			SendMessage.sendNewBuff(role);
		}
	}

	/**
	 * 伙伴学习技能
	 * 
	 * @param partner
	 *            伙伴对象
	 * @param skillCategory
	 *            技能种类
	 * @return 学习成功
	 */
	public static boolean partnerStudySkill(Partner partner, int skillId) {

		if (partner.checkSkill(skillId)) {
			return false;
		}

		int size = partner.getSkills().size();
		int result = Util.getRandom(0, 100);
		switch (size) {
		case 0:
			partner.getSkills().add(skillId);
			return true;
		case 1:
			if (result <= 70) {
				partner.getSkills().add(skillId);
			} else {
				partner.getSkills().set(Util.getRandom(size), skillId);
			}
			return true;
		case 2:
			if (result <= 50) {
				partner.getSkills().add(skillId);
			} else {
				partner.getSkills().set(Util.getRandom(size), skillId);
			}
			return true;
		case 3:
			if (result <= 25) {
				partner.getSkills().add(skillId);
			} else {
				partner.getSkills().set(Util.getRandom(size), skillId);
			}
			return true;
		case 4:
			if (result <= 10) {
				partner.getSkills().add(skillId);
			} else {
				partner.getSkills().set(Util.getRandom(size), skillId);
			}
			return true;
		case 5:
			if (result <= 5) {
				partner.getSkills().add(skillId);
			} else {
				partner.getSkills().set(Util.getRandom(size), skillId);
			}
			return true;
		default:
			partner.getSkills().set(Util.getRandom(size), skillId);
			return true;
		}
	}

	/**
	 * 添加伙伴
	 * 
	 * @param partner
	 */
	public static Partner addPartner(Role role, Partner partner, EGetPartner type) {

		RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
		long partnerId = redisCenter.getPartnerId();

		Partner newPartner = partner.copy();
		PartnerServer.countPotential(newPartner); // 计算资质
		newPartner.setId(partnerId);
		newPartner.setHp(newPartner.getHpMax());
		role.getPartnerMap().put(partnerId, newPartner);
		newPartner.setRole(role);

		// 新服活动
		ActivityPayManager.saveRoleIdByPartnerColor(role, newPartner);

		LogManager.partnerGet(role, newPartner, type);
		return newPartner;
	}

	/**
	 * 增加成长率
	 * 
	 * @param mainPartner
	 *            主伙伴
	 * @param subPartner
	 *            副伙伴
	 */
	public static int addTalent(Partner mainPartner, Partner subPartner) {
		float a = (subPartner.getInnateHp() + subPartner.getInnateAttack() + subPartner.getInnateMdefence() + subPartner.getInnatePdefence()) / 4000f;
		int b = (int) (a > 1 ? a : 1);
		int c = Util.getRandom((int) (subPartner.getLevel() / 40f), (subPartner.getTalent() + 10)) * b + subPartner.getColor().getValue();
		mainPartner.setTalent(mainPartner.getTalent() + c);
		return c;
	}
}
