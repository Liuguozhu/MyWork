package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.util.Util;

public class PropertyBattle {

	private int originalHpMax; // 生命值上限
	private int originalHp; // 生命值
	private int originalMpMax; // 内力值上限
	private int originalMp; // 内力值
	private int originalPattack; // 外功攻击
	private int originalPdefence; // 外功防御
	private int originalMattack; // 内功攻击
	private int originalMdefence; // 内功防御
	private int originalSpeed; // 速度
	private int originalHit; // 命中
	private int originalDodge; // 闪避
	private int originalCrit; // 暴击
	private int originalParry; // 格挡

	private int addHpMax = 0; // 附加生命值上限
	private int addMpMax = 0; // 附加内力值上限
	private int addPattack = 0; // 附加外功攻击
	private int addPdefence = 0; // 附加外功防御
	private int addMattack = 0; // 附加内功攻击
	private int addMdefence = 0; // 附加内功防御
	private int addSpeed = 0; // 附加速度
	private int addHit = 0; // 附加命中
	private int addDodge = 0; // 附加闪避
	private int addCrit = 0; // 附加暴击
	private int addParry = 0; // 附加格挡

	private int hpMax; // 生命值上限
	private int hp; // 生命值
	private int mpMax; // 内力值上限
	private int mp; // 内力值
	private int pattack; // 外功攻击
	private int pdefence; // 外功防御
	private int mattack; // 内功攻击
	private int mdefence; // 内功防御
	private int speed; // 速度
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡

	private boolean tianxiang = false; // 不被眩晕
	private boolean qixi = false; // 不被反震
	private boolean guhua = false; // 不被暴击
	private boolean fanzhen = false; // 不被吸血
	private boolean gaojijinghua = false; // 不受任何负面状态
	private boolean shenyou = false; // 是否神佑

	private int skillAddCrit = 0; // 技能增加暴击
	private int skillAddParry = 0; // 技能增加格挡

	private int buffPharm = 0; // BUFF附加外功伤害
	private int buffMharm = 0; // BUFF附加内功伤害

	private int buffPattack = 0; // 附加外功攻击
	private int buffPdefence = 0; // 附加外功防御
	private int buffMattack = 0; // 附加内功攻击
	private int buffMdefence = 0; // 附加内功防御
	private int buffSpeed = 0; // 附加速度
	private int buffHit = 0; // 附加命中
	private int buffDodge = 0; // 附加闪避
	private int buffCrit = 0; // 附加暴击
	private int buffParry = 0; // 附加格挡

	public void addBuffPharm(int buffPharm) {
		this.buffPharm += buffPharm;
	}

	public void addBuffMharm(int buffMharm) {
		this.buffMharm += buffMharm;
	}

	public void addBuffPattack(int buffPattack) {
		this.buffPattack += buffPattack;
	}

	public void addBuffPdefence(int buffPdefence) {
		this.buffPdefence += buffPdefence;
	}

	public void addBuffMattack(int buffMattack) {
		this.buffMattack += buffMattack;
	}

	public void addBuffMdefence(int buffMdefence) {
		this.buffMdefence += buffMdefence;
	}

	public void addBuffSpeed(int buffSpeed) {
		this.buffSpeed += buffSpeed;
	}

	public void addBuffHit(int buffHit) {
		this.buffHit += buffHit;
	}

	public void addBuffDodge(int buffDodge) {
		this.buffDodge += buffDodge;
	}

	public void addBuffCrit(int buffCrit) {
		this.buffCrit += buffCrit;
	}

	public void addBuffParry(int buffParry) {
		this.buffParry += buffParry;
	}

	/**
	 * 重置
	 */
	public void init() {
		this.hpMax = originalHpMax + addHpMax; // 生命值上限
		this.hp = originalHp + addHpMax; // 生命值上限
		this.mpMax = originalMpMax + addMpMax; // 内力值上限
		this.mp = originalMp + addMpMax; // 内力值上限
		this.pattack = originalPattack + addPattack; // 外功攻击
		this.pdefence = originalPdefence + addPdefence; // 外功防御
		this.mattack = originalMattack + addMattack; // 内功攻击
		this.mdefence = originalMdefence + addMdefence; // 内功防御
		this.speed = originalSpeed + addSpeed; // 速度
		this.hit = originalHit + addHit; // 命中
		this.dodge = originalDodge + addDodge; // 闪避
		this.crit = originalCrit + addCrit; // 暴击
		this.parry = originalParry + addParry; // 格挡
	}

	public void reset() {
		this.pattack = originalPattack + addPattack; // 外功攻击
		this.pdefence = originalPdefence + addPdefence; // 外功防御
		this.mattack = originalMattack + addMattack; // 内功攻击
		this.mdefence = originalMdefence + addMdefence; // 内功防御
		this.speed = originalSpeed + addSpeed; // 速度
		this.hit = originalHit + addHit; // 命中
		this.dodge = originalDodge + addDodge; // 闪避
		this.crit = originalCrit + addCrit; // 暴击
		this.parry = originalParry + addParry; // 格挡

		if (this.pdefence < 0) {
			this.pdefence = 0;
		}

		if (this.mdefence < 0) {
			this.mdefence = 0;
		}

		this.buffPharm = 0; // BUFF附加外功伤害
		this.buffMharm = 0; // BUFF附加内功伤害
		this.buffPattack = 0; // 附加外功攻击
		this.buffPdefence = 0; // 附加外功防御
		this.buffMattack = 0; // 附加内功攻击
		this.buffMdefence = 0; // 附加内功防御
		this.buffSpeed = 0; // 附加速度
		this.buffHit = 0; // 附加命中
		this.buffDodge = 0; // 附加闪避
		this.buffCrit = 0; // 附加暴击
		this.buffParry = 0; // 附加格挡
	}

	public void addHpMax(int hpMax) {
		this.hpMax += hpMax;
		if (this.hpMax < 0) {
			this.hpMax = 0;
		}
	}

	public int addHp(int hp) {
		this.hp += hp;
		if (this.hp < 0) {
			hp = hp + 0 - this.hp;
			this.hp = 0;
		}
		if (this.hp > this.hpMax) {
			hp = hp - this.hp + this.hpMax;
			this.hp = this.hpMax;
		}
		return hp;
	}

	public void addMpMax(int mpMax) {
		this.mpMax += mpMax;
		if (this.mpMax < 0) {
			this.mpMax = 0;
		}
	}

	public int addMp(int mp) {
		this.mp += mp;
		if (this.mp < 0) {
			mp = mp + 0 - this.mp;
			this.mp = 0;
		}
		if (this.mp > this.mpMax) {
			mp = mp - this.mp + this.mpMax;
			this.mp = this.mpMax;
		}
		return mp;
	}

	public void addPattack(int pattack) {
		this.pattack += pattack;
		if (this.pattack < 0) {
			this.pattack = 0;
		}
	}

	public void addPdefence(int pdefence) {
		this.pdefence += pdefence;
		if (this.pdefence < 0) {
			this.pdefence = 0;
		}
	}

	public void addMattack(int mattack) {
		this.mattack += mattack;
		if (this.mattack < 0) {
			this.mattack = 0;
		}
	}

	public void addMdefence(int mdefence) {
		this.mdefence += mdefence;
		if (this.mdefence < 0) {
			this.mdefence = 0;
		}
	}

	public void addSpeed(int speed) {
		this.speed += speed;
		if (this.speed < 0) {
			this.speed = 0;
		}
	}

	public void addHit(int hit) {
		this.hit += hit;
		if (this.hit < 0) {
			this.hit = 0;
		}
	}

	public void addDodge(int dodge) {
		this.dodge += dodge;
		if (this.dodge < 0) {
			this.dodge = 0;
		}
	}

	public void addCrit(int crit) {
		this.crit += crit;
		if (this.crit < 0) {
			this.crit = 0;
		}
	}

	public void addParry(int parry) {
		this.parry += parry;
		if (this.parry < 0) {
			this.parry = 0;
		}
	}

	/**
	 * @return the hpMax
	 */
	public int getHpMax() {
		return hpMax;
	}

	/**
	 * @param hpMax
	 *            the hpMax to set
	 */
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the mpMax
	 */
	public int getMpMax() {
		return mpMax;
	}

	/**
	 * @param mpMax
	 *            the mpMax to set
	 */
	public void setMpMax(int mpMax) {
		this.mpMax = mpMax;
	}

	/**
	 * @return the mp
	 */
	public int getMp() {
		return mp;
	}

	/**
	 * @param mp
	 *            the mp to set
	 */
	public void setMp(int mp) {
		this.mp = mp;
	}

	/**
	 * @return the pattack
	 */
	public int getPattack() {
		return Util.matchZero(pattack + buffPattack);
	}

	/**
	 * @param pattack
	 *            the pattack to set
	 */
	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	/**
	 * @return the pdefence
	 */
	public int getPdefence() {
		return Util.matchZero(pdefence + buffPdefence);
	}

	/**
	 * @param pdefence
	 *            the pdefence to set
	 */
	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	/**
	 * @return the mattack
	 */
	public int getMattack() {
		return Util.matchZero(mattack + buffMattack);
	}

	/**
	 * @param mattack
	 *            the mattack to set
	 */
	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	/**
	 * @return the mdefence
	 */
	public int getMdefence() {
		return Util.matchZero(mdefence + buffMdefence);
	}

	/**
	 * @param mdefence
	 *            the mdefence to set
	 */
	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return Util.matchZero(speed + buffSpeed);
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the hit
	 */
	public int getHit() {
		return Util.matchZero(hit + buffHit);
	}

	/**
	 * @param hit
	 *            the hit to set
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}

	/**
	 * @return the dodge
	 */
	public int getDodge() {
		return Util.matchZero(dodge + buffDodge);
	}

	/**
	 * @param dodge
	 *            the dodge to set
	 */
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	/**
	 * @return the crit
	 */
	public int getCrit() {
		return Util.matchZero(crit + buffCrit);
	}

	/**
	 * @param crit
	 *            the crit to set
	 */
	public void setCrit(int crit) {
		this.crit = crit;
	}

	/**
	 * @return the parry
	 */
	public int getParry() {
		return Util.matchZero(parry + buffParry);
	}

	/**
	 * @param parry
	 *            the parry to set
	 */
	public void setParry(int parry) {
		this.parry = parry;
	}

	/**
	 * @return the originalHpMax
	 */
	public int getOriginalHpMax() {
		return originalHpMax;
	}

	/**
	 * @param originalHpMax
	 *            the originalHpMax to set
	 */
	public void setOriginalHpMax(int originalHpMax) {
		this.originalHpMax = originalHpMax;
	}

	/**
	 * @return the originalHp
	 */
	public int getOriginalHp() {
		return originalHp;
	}

	/**
	 * @param originalHp
	 *            the originalHp to set
	 */
	public void setOriginalHp(int originalHp) {
		this.originalHp = originalHp;
	}

	/**
	 * @return the originalMpMax
	 */
	public int getOriginalMpMax() {
		return originalMpMax;
	}

	/**
	 * @param originalMpMax
	 *            the originalMpMax to set
	 */
	public void setOriginalMpMax(int originalMpMax) {
		this.originalMpMax = originalMpMax;
	}

	/**
	 * @return the originalMp
	 */
	public int getOriginalMp() {
		return originalMp;
	}

	/**
	 * @param originalMp
	 *            the originalMp to set
	 */
	public void setOriginalMp(int originalMp) {
		this.originalMp = originalMp;
	}

	/**
	 * @return the originalPattack
	 */
	public int getOriginalPattack() {
		return originalPattack;
	}

	/**
	 * @param originalPattack
	 *            the originalPattack to set
	 */
	public void setOriginalPattack(int originalPattack) {
		this.originalPattack = originalPattack;
	}

	/**
	 * @return the originalPdefence
	 */
	public int getOriginalPdefence() {
		return originalPdefence;
	}

	/**
	 * @param originalPdefence
	 *            the originalPdefence to set
	 */
	public void setOriginalPdefence(int originalPdefence) {
		this.originalPdefence = originalPdefence;
	}

	/**
	 * @return the originalMattack
	 */
	public int getOriginalMattack() {
		return originalMattack;
	}

	/**
	 * @param originalMattack
	 *            the originalMattack to set
	 */
	public void setOriginalMattack(int originalMattack) {
		this.originalMattack = originalMattack;
	}

	/**
	 * @return the originalMdefence
	 */
	public int getOriginalMdefence() {
		return originalMdefence;
	}

	/**
	 * @param originalMdefence
	 *            the originalMdefence to set
	 */
	public void setOriginalMdefence(int originalMdefence) {
		this.originalMdefence = originalMdefence;
	}

	/**
	 * @return the originalSpeed
	 */
	public int getOriginalSpeed() {
		return originalSpeed;
	}

	/**
	 * @param originalSpeed
	 *            the originalSpeed to set
	 */
	public void setOriginalSpeed(int originalSpeed) {
		this.originalSpeed = originalSpeed;
	}

	/**
	 * @return the originalHit
	 */
	public int getOriginalHit() {
		return originalHit;
	}

	/**
	 * @param originalHit
	 *            the originalHit to set
	 */
	public void setOriginalHit(int originalHit) {
		this.originalHit = originalHit;
	}

	/**
	 * @return the originalDodge
	 */
	public int getOriginalDodge() {
		return originalDodge;
	}

	/**
	 * @param originalDodge
	 *            the originalDodge to set
	 */
	public void setOriginalDodge(int originalDodge) {
		this.originalDodge = originalDodge;
	}

	/**
	 * @return the originalCrit
	 */
	public int getOriginalCrit() {
		return originalCrit;
	}

	/**
	 * @param originalCrit
	 *            the originalCrit to set
	 */
	public void setOriginalCrit(int originalCrit) {
		this.originalCrit = originalCrit;
	}

	/**
	 * @return the originalParry
	 */
	public int getOriginalParry() {
		return originalParry;
	}

	/**
	 * @param originalParry
	 *            the originalParry to set
	 */
	public void setOriginalParry(int originalParry) {
		this.originalParry = originalParry;
	}

	/**
	 * @return the tianxiang
	 */
	public boolean isTianxiang() {
		return tianxiang;
	}

	/**
	 * @param tianxiang
	 *            the tianxiang to set
	 */
	public void setTianxiang(boolean tianxiang) {
		this.tianxiang = tianxiang;
	}

	/**
	 * @return the qixi
	 */
	public boolean isQixi() {
		return qixi;
	}

	/**
	 * @param qixi
	 *            the qixi to set
	 */
	public void setQixi(boolean qixi) {
		this.qixi = qixi;
	}

	/**
	 * @return the guhua
	 */
	public boolean isGuhua() {
		return guhua;
	}

	/**
	 * @param guhua
	 *            the guhua to set
	 */
	public void setGuhua(boolean guhua) {
		this.guhua = guhua;
	}

	/**
	 * @return the fanzhen
	 */
	public boolean isFanzhen() {
		return fanzhen;
	}

	/**
	 * @param fanzhen
	 *            the fanzhen to set
	 */
	public void setFanzhen(boolean fanzhen) {
		this.fanzhen = fanzhen;
	}

	/**
	 * @return the gaojijinghua
	 */
	public boolean isGaojijinghua() {
		return gaojijinghua;
	}

	/**
	 * @param gaojijinghua
	 *            the gaojijinghua to set
	 */
	public void setGaojijinghua(boolean gaojijinghua) {
		this.gaojijinghua = gaojijinghua;
	}

	/**
	 * @return the shenyou
	 */
	public boolean isShenyou() {
		return shenyou;
	}

	/**
	 * @param shenyou
	 *            the shenyou to set
	 */
	public void setShenyou(boolean shenyou) {
		this.shenyou = shenyou;
	}

	public void addAddHpMax(int addHpMax) {
		this.addHpMax += addHpMax;
	}

	public void addAddMpMax(int addMpMax) {
		this.addMpMax += addMpMax;
	}

	public void addAddPattack(int addPattack) {
		this.addPattack += addPattack;
	}

	public void addAddPdefence(int addPdefence) {
		this.addPdefence += addPdefence;
	}

	public void addAddMattack(int addMattack) {
		this.addMattack += addMattack;
	}

	public void addAddMdefence(int addMdefence) {
		this.addMdefence += addMdefence;
	}

	public void addAddSpeed(int addSpeed) {
		this.addSpeed += addSpeed;
	}

	public void addAddHit(int addHit) {
		this.addHit += addHit;
	}

	public void addAddDodge(int addDodge) {
		this.addDodge += addDodge;
	}

	public void addAddCrit(int addCrit) {
		this.addCrit += addCrit;
	}

	public void addAddParry(int addParry) {
		this.addParry += addParry;
	}

	/**
	 * @return the addHpMax
	 */
	public int getAddHpMax() {
		return addHpMax;
	}

	/**
	 * @param addHpMax
	 *            the addHpMax to set
	 */
	public void setAddHpMax(int addHpMax) {
		this.addHpMax = addHpMax;
	}

	/**
	 * @return the addMpMax
	 */
	public int getAddMpMax() {
		return addMpMax;
	}

	/**
	 * @param addMpMax
	 *            the addMpMax to set
	 */
	public void setAddMpMax(int addMpMax) {
		this.addMpMax = addMpMax;
	}

	/**
	 * @return the addPattack
	 */
	public int getAddPattack() {
		return addPattack;
	}

	/**
	 * @param addPattack
	 *            the addPattack to set
	 */
	public void setAddPattack(int addPattack) {
		this.addPattack = addPattack;
	}

	/**
	 * @return the addPdefence
	 */
	public int getAddPdefence() {
		return addPdefence;
	}

	/**
	 * @param addPdefence
	 *            the addPdefence to set
	 */
	public void setAddPdefence(int addPdefence) {
		this.addPdefence = addPdefence;
	}

	/**
	 * @return the addMattack
	 */
	public int getAddMattack() {
		return addMattack;
	}

	/**
	 * @param addMattack
	 *            the addMattack to set
	 */
	public void setAddMattack(int addMattack) {
		this.addMattack = addMattack;
	}

	/**
	 * @return the addMdefence
	 */
	public int getAddMdefence() {
		return addMdefence;
	}

	/**
	 * @param addMdefence
	 *            the addMdefence to set
	 */
	public void setAddMdefence(int addMdefence) {
		this.addMdefence = addMdefence;
	}

	/**
	 * @return the addSpeed
	 */
	public int getAddSpeed() {
		return addSpeed;
	}

	/**
	 * @param addSpeed
	 *            the addSpeed to set
	 */
	public void setAddSpeed(int addSpeed) {
		this.addSpeed = addSpeed;
	}

	/**
	 * @return the addHit
	 */
	public int getAddHit() {
		return addHit;
	}

	/**
	 * @param addHit
	 *            the addHit to set
	 */
	public void setAddHit(int addHit) {
		this.addHit = addHit;
	}

	/**
	 * @return the addDodge
	 */
	public int getAddDodge() {
		return addDodge;
	}

	/**
	 * @param addDodge
	 *            the addDodge to set
	 */
	public void setAddDodge(int addDodge) {
		this.addDodge = addDodge;
	}

	/**
	 * @return the addCrit
	 */
	public int getAddCrit() {
		return addCrit;
	}

	/**
	 * @param addCrit
	 *            the addCrit to set
	 */
	public void setAddCrit(int addCrit) {
		this.addCrit = addCrit;
	}

	/**
	 * @return the addParry
	 */
	public int getAddParry() {
		return addParry;
	}

	/**
	 * @param addParry
	 *            the addParry to set
	 */
	public void setAddParry(int addParry) {
		this.addParry = addParry;
	}

	/**
	 * @return the skillAddCrit
	 */
	public int getSkillAddCrit() {
		return skillAddCrit;
	}

	/**
	 * @param skillAddCrit
	 *            the skillAddCrit to set
	 */
	public void setSkillAddCrit(int skillAddCrit) {
		this.skillAddCrit = skillAddCrit;
	}

	/**
	 * @return the skillAddParry
	 */
	public int getSkillAddParry() {
		return skillAddParry;
	}

	/**
	 * @param skillAddParry
	 *            the skillAddParry to set
	 */
	public void setSkillAddParry(int skillAddParry) {
		this.skillAddParry = skillAddParry;
	}

	/**
	 * @return the buffPharm
	 */
	public int getBuffPharm() {
		return buffPharm;
	}

	/**
	 * @param buffPharm
	 *            the buffPharm to set
	 */
	public void setBuffPharm(int buffPharm) {
		this.buffPharm = buffPharm;
	}

	/**
	 * @return the buffMharm
	 */
	public int getBuffMharm() {
		return buffMharm;
	}

	/**
	 * @param buffMharm
	 *            the buffMharm to set
	 */
	public void setBuffMharm(int buffMharm) {
		this.buffMharm = buffMharm;
	}

	/**
	 * @return the buffPattack
	 */
	public int getBuffPattack() {
		return buffPattack;
	}

	/**
	 * @param buffPattack
	 *            the buffPattack to set
	 */
	public void setBuffPattack(int buffPattack) {
		this.buffPattack = buffPattack;
	}

	/**
	 * @return the buffPdefence
	 */
	public int getBuffPdefence() {
		return buffPdefence;
	}

	/**
	 * @param buffPdefence
	 *            the buffPdefence to set
	 */
	public void setBuffPdefence(int buffPdefence) {
		this.buffPdefence = buffPdefence;
	}

	/**
	 * @return the buffMattack
	 */
	public int getBuffMattack() {
		return buffMattack;
	}

	/**
	 * @param buffMattack
	 *            the buffMattack to set
	 */
	public void setBuffMattack(int buffMattack) {
		this.buffMattack = buffMattack;
	}

	/**
	 * @return the buffMdefence
	 */
	public int getBuffMdefence() {
		return buffMdefence;
	}

	/**
	 * @param buffMdefence
	 *            the buffMdefence to set
	 */
	public void setBuffMdefence(int buffMdefence) {
		this.buffMdefence = buffMdefence;
	}

	/**
	 * @return the buffSpeed
	 */
	public int getBuffSpeed() {
		return buffSpeed;
	}

	/**
	 * @param buffSpeed
	 *            the buffSpeed to set
	 */
	public void setBuffSpeed(int buffSpeed) {
		this.buffSpeed = buffSpeed;
	}

	/**
	 * @return the buffHit
	 */
	public int getBuffHit() {
		return buffHit;
	}

	/**
	 * @param buffHit
	 *            the buffHit to set
	 */
	public void setBuffHit(int buffHit) {
		this.buffHit = buffHit;
	}

	/**
	 * @return the buffDodge
	 */
	public int getBuffDodge() {
		return buffDodge;
	}

	/**
	 * @param buffDodge
	 *            the buffDodge to set
	 */
	public void setBuffDodge(int buffDodge) {
		this.buffDodge = buffDodge;
	}

	/**
	 * @return the buffCrit
	 */
	public int getBuffCrit() {
		return buffCrit;
	}

	/**
	 * @param buffCrit
	 *            the buffCrit to set
	 */
	public void setBuffCrit(int buffCrit) {
		this.buffCrit = buffCrit;
	}

	/**
	 * @return the buffParry
	 */
	public int getBuffParry() {
		return buffParry;
	}

	/**
	 * @param buffParry
	 *            the buffParry to set
	 */
	public void setBuffParry(int buffParry) {
		this.buffParry = buffParry;
	}

}
