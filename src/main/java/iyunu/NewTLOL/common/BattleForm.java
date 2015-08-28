package iyunu.NewTLOL.common;

import iyunu.NewTLOL.util.Util;

/**
 * 公式
 * 
 * @author iyunu
 * 
 */
public class BattleForm {

	/**
	 * 伤害公式
	 * 
	 * @version 2014-08-22 孙洪磊 （攻击x攻击）/（攻击x2+防御x3）
	 * @param attack
	 *            攻击者的攻击力
	 * @param defence
	 *            防守者的防御力
	 * @param rate
	 *            伤害系数
	 * @return 伤害值
	 */
	public static float harm(int attack, int defence, int rate) {
		float value = attack * 1f / (attack * 2 + defence * 3) * attack * (rate / 10000f);
//		System.out.println("伤害=" + value);
		return value;
	}

	/**
	 * 命中公式
	 * 
	 * @version 2014-08-22 孙洪磊 （命中-闪避）/1000 + 等级差影响 不低于30%
	 * @param hit
	 *            攻击者的命中
	 * @param dodge
	 *            防守者的闪避
	 * @return 是否命中
	 */
	public static boolean hit(int hit, int dodge, int attackerLevel, int defencerLevel) {
		if (hit <= 0 || dodge < 0) { // 数字安全验证
			return false;
		}
		hit += 990; // 命中基数
		float value = (hit - dodge) / 1000f + levelBalance(attackerLevel, defencerLevel);
		value = value < 0.3f ? 0.3f : value;
		// System.out.println("命中=" + value);
		return Util.probable(value);
	}

	/**
	 * 暴击公式
	 * 
	 * @version 2014-08-22 孙洪磊 暴击/1000 + 等级差影响 不超过75%
	 * @param crit
	 *            攻击者的暴击
	 * @return 是否暴击
	 */
	public static boolean crit(int crit, int addCrit, int attackerLevel, int defencerLevel) {
		if (crit < 0) { // 数字安全验证
			return false;
		}
		float value = (crit + addCrit) / 1000f + levelBalance(attackerLevel, defencerLevel);
		value = value > 0.75f ? 0.75f : value;
		// System.out.println("暴击=" + value);
		return Util.probable(value);
	}

	/**
	 * 格挡公式
	 * 
	 * @version 2014-08-22 孙洪磊 格挡/1000 + 等级差影响 不超过75%
	 * @param parry
	 *            格挡
	 * @return 是否格挡
	 */
	public static boolean parry(int parry, int addParry, int attackerLevel, int defencerLevel) {
		if (parry < 0) { // 数字安全验证
			return false;
		}
		float value = (parry + addParry) / 1000f + levelBalance(defencerLevel, attackerLevel);
		value = value > 0.75f ? 0.75f : value;
		// System.out.println("格挡=" + value);
		return Util.probable(value);
	}

	/**
	 * 封印公式
	 * 
	 * @version 2014-08-22 孙洪磊 基础命中率 + （命中-闪避）/2000 + 等级差影响
	 * @param base
	 *            技能基础概率
	 * @param attackerLevel
	 *            攻击者等级
	 * @param defencerLevel
	 *            防御者等级
	 * @return 封印命中
	 */
	public static boolean seal(int base, int attackerLevel, int defencerLevel, int hit, int dodge, int mattack, int mdefence) {
		float levelRate = levelBalance(attackerLevel, defencerLevel);
		hit += 990; // 命中基数
		float hitRate = (hit - dodge) / 4000f;

		float value = (levelRate + hitRate + base / 10000f);
		// System.out.println("封印=" + value);
		return Util.probable(value);
	}

	/**
	 * 恢复公式
	 * 
	 * @param attack
	 *            攻击者的攻击力
	 * @param skillIndex
	 *            攻击者的技能指数
	 * @return 恢复数值
	 */
	public static int recover(int attack, int skillIndex) {
		return attack * skillIndex;
	}

	/**
	 * 等级差影响
	 * 
	 * @param level1
	 *            等级1
	 * @param level2
	 *            等级2
	 * @return 等级差影响
	 */
	public static float levelBalance(int level1, int level2) {
		float value = (level1 - level2) * 0.0025f;
		if (value > 0.05f) {
			return 0.05f;
		}
		if (value < -0.05f) {
			return -0.05f;
		}
		return value;
	}
}
