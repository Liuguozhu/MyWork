package iyunu.NewTLOL.common;

import iyunu.NewTLOL.model.role.Role;

/**
 * 公式
 * 
 * @author iyunu
 * 
 */
public class RoleForm {

	public static int taskCycleExp(int level) {
		return 100 * (level - 30) + 12500;
	}

	public static int taskExp(int level, int n) {
		float exp = 0;
		if (level < 36) {
			exp = (level * 500 + 5000) / 3f;
		} else {
			exp = (level * 7500 + 225000) / 3f;
		}

		switch (n) {
		case 1:
		case 2:
			exp = exp * 0.1f;
			break;
		case 3:
		case 4:
			exp = exp * 0.15f;
			break;
		default:
			exp = exp * 0.5f;
			break;
		}
		return (int) exp;
	}

	public static int taskGold(int level, int n) {
		float exp = 0;
		if (level < 36) {
			exp = (level * 400 + 4000) / 3f;
		} else {
			exp = (level * 2000 + 90000) / 3f;
		}

		switch (n) {
		case 1:
		case 2:
			exp = exp * 0.1f;
			break;
		case 3:
		case 4:
			exp = exp * 0.15f;
			break;
		default:
			exp = exp * 0.5f;
			break;
		}
		return (int) exp;
	}

	public static int ghostTaskGold(int level) {
		return level * 240 - 7200;
	}

	public static int ghostTaskExp(int level) {
		return 50 * (level - 30) + 125;
	}

	/**
	 * @function 招财计算金钱
	 * @author LuoSR
	 * @param level
	 *            角色等级
	 * @return 获得绑银或银两
	 * @date 2014年9月9日
	 */
	public static int buyGold(int level) {
		int gold = 0;
		if (level <= 35) {
			gold = (level * 400 + 4000) / 24;
		} else {
			gold = (level * 2000 + 90000) / 24;
		}
		return gold;
	}

	/**
	 * 生命上限公式
	 * 
	 * @param role
	 *            角色
	 * @return 生命上限
	 */
	public static int hpMax(Role role) {
		float hpMax = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			hpMax = role.getShowStrength() * 0.3f + role.getShowIntellect() * 0f + role.getShowPhysique() * 1.95f + role.getShowAgility() * 0f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			hpMax = role.getShowStrength() * 0.6f + role.getShowIntellect() * 0f + role.getShowPhysique() * 2.4f + role.getShowAgility() * 0f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			hpMax = role.getShowStrength() * 0.0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 2f + role.getShowAgility() * 0f;
			break;
		default: // 其他
			hpMax = role.getShowStrength() * 0.1f + role.getShowIntellect() * 0f + role.getShowPhysique() * 1f + role.getShowAgility() * 0f;
		}

		return (int) hpMax;
	}

	/**
	 * 内力上限公式
	 * 
	 * @param role
	 *            角色
	 * @return 内力上限
	 */
	public static int mpMax(Role role) {
		float mpMax = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			mpMax = role.getShowStrength() * 0f + role.getShowIntellect() * 0.5f + role.getShowPhysique() * 1.25f + role.getShowAgility() * 0f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			mpMax = role.getShowStrength() * 0f + role.getShowIntellect() * 1.25f + role.getShowPhysique() * 0.75f + role.getShowAgility() * 0f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			mpMax = role.getShowStrength() * 0f + role.getShowIntellect() * 1f + role.getShowPhysique() * 0.5f + role.getShowAgility() * 0f;
			break;
		default: // 其他
			mpMax = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
		}

		return (int) mpMax;
	}

	/**
	 * 外功攻击公式
	 * 
	 * @param role
	 *            角色
	 * @return 外功攻击
	 */
	public static int pAttack(Role role) {
		float pAttack = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			pAttack = role.getShowStrength() * 1.625f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			pAttack = role.getShowStrength() * 1.5f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			pAttack = role.getShowStrength() * 1.625f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		default: // 其他
			pAttack = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
		}
		return (int) pAttack;
	}

	/**
	 * 外功防御公式
	 * 
	 * @param role
	 *            角色
	 * @return 外功防御
	 */
	public static int pDefence(Role role) {
		float pDefence = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			pDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0.5f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			pDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 1f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			pDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0.75f;
			break;
		default: // 其他
			pDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
		}
		return (int) pDefence;
	}

	/**
	 * 内功攻击公式
	 * 
	 * @param role
	 *            角色
	 * @return 内功攻击
	 */
	public static int mAttack(Role role) {
		float mAttack = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			mAttack = role.getShowStrength() * 0f + role.getShowIntellect() * 1.5f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			mAttack = role.getShowStrength() * 0f + role.getShowIntellect() * 1.625f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			mAttack = role.getShowStrength() * 0f + role.getShowIntellect() * 1.625f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
			break;
		default: // 其他
			mAttack = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
		}
		return (int) mAttack;
	}

	/**
	 * 内功防御公式
	 * 
	 * @param role
	 *            角色
	 * @return 内功防御
	 */
	public static int mDefence(Role role) {
		float mDefence = 0;
		switch ((int) role.getFigure()) {
		case 1: // 形象剑客女
		case 2: // 形象剑客男
			mDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0.54f;
			break;
		case 3: // 形象居士女
		case 4: // 形象居士男
			mDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 1f;
			break;
		case 5: // 形象刀客女
		case 6: // 形象刀客男
			mDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0.625f;
			break;
		default: // 其他
			mDefence = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 1f;
		}
		return (int) mDefence;
	}

	/**
	 * 命中公式
	 * 
	 * @param role
	 *            角色
	 * @return 命中
	 */
	// public static int hit(Role role) {
	// float hit = 0;
	// switch ((int) role.getFigure()) {
	// case 0: // 形象剑客女
	// case 1: // 形象剑客男
	// hit = role.getShowStrength() * 0.7f + role.getShowIntellect() * 0.7f +
	// role.getShowPhysique() * 0.7f + role.getShowAgility() * 0.7f;
	// break;
	// case 2: // 形象居士女
	// case 3: // 形象居士男
	// hit = role.getShowStrength() * 0.7f + role.getShowIntellect() * 0.7f +
	// role.getShowPhysique() * 0.7f + role.getShowAgility() * 0.7f;
	// break;
	// case 4: // 形象刀客女
	// case 5: // 形象刀客男
	// hit = role.getShowStrength() * 0.7f + role.getShowIntellect() * 0.7f +
	// role.getShowPhysique() * 0.7f + role.getShowAgility() * 0.7f;
	// break;
	// default: // 其他
	// hit = role.getShowStrength() * 0.7f + role.getShowIntellect() * 0.7f +
	// role.getShowPhysique() * 0.7f + role.getShowAgility() * 0.7f;
	// }
	// return (int) hit;
	// }

	/**
	 * 闪避公式
	 * 
	 * @param role
	 *            角色
	 * @return 闪避
	 */
	// public static int dodge(Role role) {
	// float pDefence = 0;
	// switch ((int) role.getFigure()) {
	// case 0: // 形象剑客女
	// case 1: // 形象剑客男
	// pDefence = role.getShowStrength() * 0.1f + role.getShowIntellect() * 0.1f
	// + role.getShowPhysique() * 0.1f + role.getShowAgility() * 1f;
	// break;
	// case 2: // 形象居士女
	// case 3: // 形象居士男
	// pDefence = role.getShowStrength() * 0.1f + role.getShowIntellect() * 0.1f
	// + role.getShowPhysique() * 0.1f + role.getShowAgility() * 1f;
	// break;
	// case 4: // 形象刀客女
	// case 5: // 形象刀客男
	// pDefence = role.getShowStrength() * 0.1f + role.getShowIntellect() * 0.1f
	// + role.getShowPhysique() * 0.1f + role.getShowAgility() * 1f;
	// break;
	// default: // 其他
	// pDefence = role.getShowStrength() * 0.1f + role.getShowIntellect() * 0.1f
	// + role.getShowPhysique() * 0.1f + role.getShowAgility() * 1f;
	// }
	// return (int) pDefence;
	// }

	/**
	 * 速度公式
	 * 
	 * @param role
	 *            角色
	 * @return 速度
	 */
	public static int speed(Role role) {
		float speed = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0.1f;
		// switch ((int) role.getFigure()) {
		// case 0: // 形象剑客女
		// case 1: // 形象剑客男
		// speed = role.getShowStrength() * 0.1f + role.getShowIntellect() *
		// 0.1f + role.getShowPhysique() * 0.1f + role.getShowAgility() * 2f;
		// break;
		// case 2: // 形象居士女
		// case 3: // 形象居士男
		// speed = role.getShowStrength() * 0.1f + role.getShowIntellect() *
		// 0.1f + role.getShowPhysique() * 0.1f + role.getShowAgility() * 2f;
		// break;
		// case 4: // 形象刀客女
		// case 5: // 形象刀客男
		// speed = role.getShowStrength() * 0.1f + role.getShowIntellect() *
		// 0.1f + role.getShowPhysique() * 0.1f + role.getShowAgility() * 2f;
		// break;
		// default: // 其他
		// speed = role.getShowStrength() * 0f + role.getShowIntellect() * 0f +
		// role.getShowPhysique() * 0f + role.getShowAgility() * 0.1f;
		// }
		return (int) speed;
	}

	/**
	 * 暴击
	 * 
	 * @param role
	 * @return
	 */
	public static int crit(Role role) {
		float crit = role.getShowStrength() * 0.05f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0f + role.getShowAgility() * 0f;
		return (int) crit;
	}

	/**
	 * 格挡
	 * 
	 * @param role
	 * @return
	 */
	public static int parry(Role role) {
		float parry = role.getShowStrength() * 0f + role.getShowIntellect() * 0f + role.getShowPhysique() * 0.05f + role.getShowAgility() * 0f;
		return (int) parry;
	}
}
