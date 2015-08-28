package iyunu.NewTLOL.model.battle;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.battle.buff.EBattleBuffEffect;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.monster.instance.Monster;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.PropertyBattle;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.skill.ESkillCategory;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.instance.PartnerSkill;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.util.CommonConst;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleCharacter {

	private EDirection direction; // 方位
	private int index; // 索引
	private ICharacter character; // 战斗对象
	private PropertyBattle propertyBattle = new PropertyBattle(); // 战斗属性
	private Map<EBattleBuffType, BattleBuff> buffs = new HashMap<EBattleBuffType, BattleBuff>(); // BUFF状态集合<战斗BUFF类型，战斗BUFF>
	private List<Integer> disappearBuffs = new ArrayList<Integer>();
	private CommandInfo commandInfo; // 战斗指令
	private boolean isDead; // 是否死亡
	private boolean isDefense; // 是否防御
	private boolean isEscape; // 是否逃跑
	private List<Long> partnerList = new ArrayList<Long>(); // 出战伙伴编号集合
	private List<String> awards = new ArrayList<String>(); // 战斗奖励
	private PropertyBattle propertyBattleLog = null; // 记录战斗属性
	private boolean isBoss; // 是否BOSS
	private int shenbingId = 0; // 神兵编号
	private int shizhuangId = 0; // 时装编号

	/**
	 * 构造方法
	 * 
	 * @param character
	 *            战斗对象
	 * @param index
	 *            位置索引
	 * @param direction
	 *            方位
	 * @param isDead
	 *            是否死亡
	 */
	public BattleCharacter(ICharacter character, int index, EDirection direction) {
		this.character = character;
		this.index = index;
		this.direction = direction;
		this.isDead = false;

		buffs.clear();

		if (character instanceof Partner) {
			Partner partner = (Partner) character;

			for (Integer skillId : partner.getSkills()) {
				PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(skillId);

				BattleBuff buff = null;
				switch (partnerSkill.getCategory()) {
				case waigong:
				case gaojiwaigong:
					propertyBattle.addAddPattack((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case neigong:
				case gaojineigong:
					propertyBattle.addAddMattack((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case waifang:
				case gaojiwaifang:
					propertyBattle.addAddPdefence((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case neifang:
				case gaojineifang:
					propertyBattle.addAddMdefence((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case mingzhong:
				case gaojimingzhong:
					propertyBattle.addAddHit((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case shanbi:
				case gaojishanbi:
					propertyBattle.addAddDodge((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case guibu:
				case gaojiguibu:
					propertyBattle.addAddSpeed((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
					break;
				case gedang: // 格挡
				case gaojigedang: // 高级格挡
					propertyBattle.setSkillAddParry(propertyBattle.getSkillAddParry() + partnerSkill.getValue());
					break;

				case bisha: // 必杀
				case gaojibisha: // 高级必杀
					if (character.getVocation().equals(Vocation.wg)) {
						propertyBattle.setSkillAddCrit(propertyBattle.getSkillAddCrit() + partnerSkill.getValue());
					}
					break;
				case kuangbao: // 狂暴
				case gaojikuangbao: // 高级狂暴
					if (character.getVocation().equals(Vocation.ng)) {
						propertyBattle.setSkillAddCrit(propertyBattle.getSkillAddCrit() + partnerSkill.getValue());
					}
					break;

				case fanzhen: // 反震
				case gaojifanzhen: // 高级反震
					propertyBattle.setFanzhen(true);
					break;

				case gaojitianxiang: // 高级天相
					propertyBattle.addAddPdefence((int) (partner.getPdefence() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
				case tianxiang: // 天相
					propertyBattle.setTianxiang(true);
					break;

				case gaojiqixi: // 高级奇袭
					propertyBattle.addAddHit((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
				case qixi: // 奇袭
					propertyBattle.setQixi(true);
					break;

				case gaojiguhua: // 高级固化
					propertyBattle.addAddDodge((int) (partner.getLevel() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
				case guhua: // 固化
					propertyBattle.setGuhua(true);
					break;

				case jinghua: // 净化
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff(), partnerSkill.getBuffValue(), partnerSkill.getDuration());
					if (buff != null) {
						addBuff(buff);
					}
					break;

				case gaojijinghua: // 高级净化
					propertyBattle.setGaojijinghua(true);
					break;

				case zaisheng: // 再生
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null && !buffs.containsKey(buff.getType())) {
						buff.setValue((int) (partner.getHpMax() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
						buff.setDuration(partnerSkill.getDuration());
						addBuff(buff);
					}
					break;
				case gaojizaisheng: // 高级再生
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null) {
						buff.setValue((int) (partner.getHpMax() * partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND));
						buff.setDuration(partnerSkill.getDuration());
						addBuff(buff);
					}
					break;

				case shenyou: // 神佑
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null && !buffs.containsKey(buff.getType())) {
						buff.setProbability(partnerSkill.getProbability());
						buff.setValue(partnerSkill.getPercent());
						buff.setDuration(partnerSkill.getDuration());
						buff.setSkillId(partnerSkill.getId());
						addBuff(buff);
						propertyBattle.setShenyou(true);
					}
					break;
				case gaojishenyou: // 高级神佑
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null) {
						buff.setProbability(partnerSkill.getProbability());
						buff.setValue(partnerSkill.getPercent());
						buff.setDuration(partnerSkill.getDuration());
						buff.setSkillId(partnerSkill.getId());
						addBuff(buff);
						propertyBattle.setShenyou(true);
					}
					break;
				default:
					break;
				}
			}
		}

		// 计算人物BUFF
		if (character instanceof Role) {
			Role role = (Role) character;
			if (role.getVocation().equals(Vocation.sl)) { // 增加暴击率
				RoleSkill skill = (RoleSkill) SkillJson.instance().getSkillById(role.getSkillMap().get(8));
				if (skill != null && skill.getLevel() > 0) {
					propertyBattle.addAddCrit((int) (role.getCrit() * (skill.getPercent() / 10000f)));
				}
			}

			// 检查BUFF到期
			BuffServer.checkBuff(role);
			// 计算奖励BUFF
			for (BuffRole buffRole : role.getBuffs().values()) {
				switch (buffRole.getType()) {
				case attack:
					if (buffRole.getValueType() == 0) {
						propertyBattle.addAddPattack(buffRole.getValue());
						propertyBattle.addAddMattack(buffRole.getValue());
					} else {
						propertyBattle.addAddPattack((int) (role.getPattack() * (buffRole.getValue() / 10000f)));
						propertyBattle.addAddMattack((int) (role.getMattack() * (buffRole.getValue() / 10000f)));
					}
					break;
				case defend:
					if (buffRole.getValueType() == 0) {
						propertyBattle.addAddPdefence(buffRole.getValue());
						propertyBattle.addAddMdefence(buffRole.getValue());
					} else {
						propertyBattle.addAddPdefence((int) (role.getPdefence() * (buffRole.getValue() / 10000f)));
						propertyBattle.addAddMdefence((int) (role.getMdefence() * (buffRole.getValue() / 10000f)));
					}
					break;
				default:
					break;
				}
			}

			Equip shenbing = role.getEquipments().get(EEquip.shenbing);
			if (shenbing != null) {
				this.shenbingId = shenbing.getId();
			}

			Equip shizhuang = role.getEquipments().get(EEquip.shizhuang);
			if (shizhuang != null) {
				this.shizhuangId = 1;
			}

		}

		if (character instanceof Monster) {
			Monster monster = (Monster) character;

			isBoss = monster.isBoss();

			for (Integer skillId : monster.getSkills()) {
				PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(skillId);

				BattleBuff buff = null;
				switch (partnerSkill.getCategory()) {
				case waigong:
				case gaojiwaigong:
					propertyBattle.addAddPattack((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case neigong:
				case gaojineigong:
					propertyBattle.addAddMattack((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case waifang:
				case gaojiwaifang:
					propertyBattle.addAddPdefence((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case neifang:
				case gaojineifang:
					propertyBattle.addAddMdefence((int) (monster.getLevel() *( partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case mingzhong:
				case gaojimingzhong:
					propertyBattle.addAddHit((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case shanbi:
				case gaojishanbi:
					propertyBattle.addAddDodge((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case guibu:
				case gaojiguibu:
					propertyBattle.addAddSpeed((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
					break;
				case gedang: // 格挡
				case gaojigedang: // 高级格挡
					propertyBattle.setSkillAddParry(propertyBattle.getSkillAddParry() + partnerSkill.getValue());
					break;

				case bisha: // 必杀
				case gaojibisha: // 高级必杀
					if (character.getVocation().equals(Vocation.wg)) {
						propertyBattle.setSkillAddCrit(propertyBattle.getSkillAddCrit() + partnerSkill.getValue());
					}
					break;
				case kuangbao: // 狂暴
				case gaojikuangbao: // 高级狂暴
					if (character.getVocation().equals(Vocation.ng)) {
						propertyBattle.setSkillAddCrit(propertyBattle.getSkillAddCrit() + partnerSkill.getValue());
					}
					break;

				case fanzhen: // 反震
				case gaojifanzhen: // 高级反震
					propertyBattle.setFanzhen(true);
					break;

				case gaojitianxiang: // 高级天相
					propertyBattle.addAddPdefence((int) (monster.getPdefence() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
				case tianxiang: // 天相
					propertyBattle.setTianxiang(true);
					break;

				case gaojiqixi: // 高级奇袭
					propertyBattle.addAddHit((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
				case qixi: // 奇袭
					propertyBattle.setQixi(true);
					break;

				case gaojiguhua: // 高级固化
					propertyBattle.addAddDodge((int) (monster.getLevel() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
				case guhua: // 固化
					propertyBattle.setGuhua(true);
					break;

				case jinghua: // 净化
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff(), partnerSkill.getBuffValue(), partnerSkill.getDuration());
					if (buff != null) {
						addBuff(buff);
					}
					break;

				case gaojijinghua: // 高级净化
					propertyBattle.setGaojijinghua(true);
					break;

				case zaisheng: // 再生
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null && !buffs.containsKey(buff.getType())) {
						buff.setValue((int) (monster.getHpMax() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
						buff.setDuration(partnerSkill.getDuration());
						addBuff(buff);
					}
					break;
				case gaojizaisheng: // 高级再生
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null) {
						buff.setValue((int) (monster.getHpMax() * (partnerSkill.getPercent() / CommonConst.FOLAT_TEN_THOUSAND)));
						buff.setDuration(partnerSkill.getDuration());
						addBuff(buff);
					}
					break;

				case shenyou: // 神佑
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null && !buffs.containsKey(buff.getType())) {
						buff.setProbability(partnerSkill.getProbability());
						buff.setValue(partnerSkill.getPercent());
						buff.setDuration(partnerSkill.getDuration());
						buff.setSkillId(partnerSkill.getId());
						addBuff(buff);
						propertyBattle.setShenyou(true);
					}
					break;
				case gaojishenyou: // 高级神佑
					buff = SkillJson.instance().getBattleBuff(partnerSkill.getBuff());
					if (buff != null) {
						buff.setProbability(partnerSkill.getProbability());
						buff.setValue(partnerSkill.getPercent());
						buff.setDuration(partnerSkill.getDuration());
						buff.setSkillId(partnerSkill.getId());
						addBuff(buff);
						propertyBattle.setShenyou(true);
					}
					break;
				default:
					break;
				}
			}
		}
		preBattle();
	}

	/**
	 * 添加出战伙伴
	 * 
	 * @param patnerId
	 *            伙伴编号
	 */
	public void addPartner(Long partnerId) {
		partnerList.add(partnerId);
	}

	/**
	 * 检查伙伴是否已出战
	 * 
	 * @param partnerId
	 *            伙伴编号
	 * @return 伙伴已出战
	 */
	public boolean checkpartner(Long partnerId) {
		return partnerList.contains(partnerId);
	}

	/**
	 * 获取防御者技能
	 * 
	 * @param isWithOutSkill
	 *            不会触发技能
	 * @return 技能
	 */
	public BaseSkill defend(boolean isWithOutSkill) {

		if (character.getType() == 0) { // 角色
			Role role = (Role) character;
			for (Integer skillId : role.getSkillMap().values()) {
				RoleSkill roleSkill = SkillJson.instance().getRoleSkillById(skillId);
				if (roleSkill != null && roleSkill.getType().equals(ESkillType.passiveTrigger) && roleSkill.getLevel() > 0 && Util.probable(roleSkill.getProbability())) {
					return roleSkill;
				}
			}

			return SkillJson.instance().getSkillById(0);
		} else { // 伙伴 和 怪物

			for (Integer skillId : character.getSkills()) {
				PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(skillId);
				if (partnerSkill.getType().equals(ESkillType.passiveTrigger) && Util.probable(partnerSkill.getProbability())) {
					if ((partnerSkill.getCategory().equals(ESkillCategory.fanzhen) || partnerSkill.getCategory().equals(ESkillCategory.gaojifanzhen)) && isWithOutSkill) {
						continue;
					}
					return partnerSkill;
				}
			}
			return SkillJson.instance().getSkillById(4000);
		}
	}

	public void propertyBattleLog() {
		propertyBattleLog = new PropertyBattle();
		propertyBattleLog.setHpMax(propertyBattle.getHpMax()); // 生命值上限
		propertyBattleLog.setHp(propertyBattle.getHp()); // 生命值
		// propertyBattleLog.setMpMax(propertyBattle.getMpMax()); // 内力值上限
		// propertyBattleLog.setMp(propertyBattle.getMp()); // 内力值
		// propertyBattleLog.setPattack(propertyBattle.getPattack());// 外功攻击
		// propertyBattleLog.setPdefence(propertyBattle.getPdefence());// 外功防御
		// propertyBattleLog.setMattack(propertyBattle.getMattack());// 内功攻击
		// propertyBattleLog.setMdefence(propertyBattle.getMdefence()); // 内功防御
		// propertyBattleLog.setSpeed(propertyBattle.getSpeed());// 速度
		// propertyBattleLog.setHit(propertyBattle.getHit());// 命中
		// propertyBattleLog.setDodge(propertyBattle.getDodge()); // 闪避
		// propertyBattleLog.setCrit(propertyBattle.getCrit());// 暴击
		// propertyBattleLog.setParry(propertyBattle.getParry()); // 格挡
	}

	/**
	 * 战斗预处理
	 */
	public void preBattle() {
		propertyBattle.setOriginalHpMax(character.getHpMax());
		propertyBattle.setOriginalHp(character.getHp());
		propertyBattle.setOriginalMpMax(character.getMpMax());
		propertyBattle.setOriginalMp(character.getMp());
		propertyBattle.setOriginalPattack(character.getPattack());
		propertyBattle.setOriginalPdefence(character.getPdefence());
		propertyBattle.setOriginalMattack(character.getMattack());
		propertyBattle.setOriginalMdefence(character.getMdefence());
		propertyBattle.setOriginalSpeed(character.getSpeed());
		propertyBattle.setOriginalHit(character.getHit());
		propertyBattle.setOriginalDodge(character.getDodge());
		propertyBattle.setOriginalCrit(character.getCrit());
		propertyBattle.setOriginalParry(character.getParry());
		propertyBattle.init();
	}

	/**
	 * 重置战斗属性
	 */
	public void resetPropertyBattle() {
		propertyBattle.reset();
		isDefense = false;
		disappearBuffs.clear();
	}

	/**
	 * 判断是否可以出手
	 * 
	 * @return 不可以出手
	 */
	public boolean cantMove() {
		if (buffs.containsKey(EBattleBuffType.b1) || buffs.containsKey(EBattleBuffType.b14)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否可以使用技能
	 * 
	 * @return 不能使用技能
	 */
	public boolean cantSkill() {
		if (buffs.containsKey(EBattleBuffType.b3) || buffs.containsKey(EBattleBuffType.b2)) {
			return true;
		}
		return false;
	}

	/**
	 * 不能普通攻击
	 * 
	 * @return 不能普通攻击
	 */
	public boolean cantAttack() {
		if (buffs.containsKey(EBattleBuffType.b4) || buffs.containsKey(EBattleBuffType.b2)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否能封印
	 * 
	 * @return 可以封印
	 */
	public boolean isSeal() {
		if (buffs.containsKey(EBattleBuffType.b17) || buffs.containsKey(EBattleBuffType.b1) || buffs.containsKey(EBattleBuffType.b2) || buffs.containsKey(EBattleBuffType.b3) || buffs.containsKey(EBattleBuffType.b4)) {
			return false;
		}
		return true;
	}

	/**
	 * 移除BUFF
	 * 
	 * @param buffId
	 *            buff编号
	 */
	public void removeBuff(int buffId) {
		buffs.remove(SkillJson.instance().getEBattleBuffType(buffId));
	}

	public boolean addBuff(BattleBuff buff) {
		if (buff != null) {

			if (buff.getDebuff().equals(EBattleBuffEffect.debuff)) {
				if (buffs.containsKey(buff.getType())) { // 负面BUFF不可以叠加
					return false;
				}
				if (propertyBattle.isGaojijinghua()) { // 如果有高级净化，则不受负面BUFF
					return false;
				}
			}
			buffs.put(buff.getType(), buff);
			return true;
		}
		return false;
	}

	/**
	 * 添加buff
	 * 
	 * @param buffId
	 *            buff编号
	 */
	// public boolean addBuff(int buffId) {
	// BattleBuff buff = SkillJson.instance().getBattleBuff(buffId);
	// if (buff != null && !buffs.containsKey(buff.getType())) {
	// buffs.put(buff.getType(), buff);
	// return true;
	// }
	// return false;
	// }

	/**
	 * 置空命令
	 */
	public void resetCommand() {
		this.commandInfo = null;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the character
	 */
	public ICharacter getCharacter() {
		return character;
	}

	/**
	 * @param character
	 *            the character to set
	 */
	public void setCharacter(ICharacter character) {
		this.character = character;
	}

	/**
	 * @return the direction
	 */
	public EDirection getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(EDirection direction) {
		this.direction = direction;
	}

	/**
	 * @return the isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @param isDead
	 *            the isDead to set
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
	 * @return the propertyBattle
	 */
	public PropertyBattle getPropertyBattle() {
		return propertyBattle;
	}

	/**
	 * @param propertyBattle
	 *            the propertyBattle to set
	 */
	public void setPropertyBattle(PropertyBattle propertyBattle) {
		this.propertyBattle = propertyBattle;
	}

	/**
	 * @return the buffs
	 */
	public Map<EBattleBuffType, BattleBuff> getBuffs() {
		return buffs;
	}

	/**
	 * @return the commandInfo
	 */
	public CommandInfo getCommandInfo() {
		return commandInfo;
	}

	/**
	 * @param commandInfo
	 *            the commandInfo to set
	 */
	public void setCommandInfo(CommandInfo commandInfo) {
		this.commandInfo = commandInfo;
	}

	/**
	 * @return the isDefense
	 */
	public boolean isDefense() {
		return isDefense;
	}

	/**
	 * @param isDefense
	 *            the isDefense to set
	 */
	public void setDefense(boolean isDefense) {
		this.isDefense = isDefense;
	}

	/**
	 * @return the isEscape
	 */
	public boolean isEscape() {
		return isEscape;
	}

	/**
	 * @param isEscape
	 *            the isEscape to set
	 */
	public void setEscape(boolean isEscape) {
		this.isEscape = isEscape;
	}

	/**
	 * @return the disappearBuffs
	 */
	public List<Integer> getDisappearBuffs() {
		return disappearBuffs;
	}

	/**
	 * @return the awards
	 */
	public List<String> getAwards() {
		return awards;
	}

	/**
	 * @param awards
	 *            the awards to set
	 */
	public void setAwards(List<String> awards) {
		this.awards = awards;
	}

	/**
	 * @return the propertyBattleLog
	 */
	public PropertyBattle getPropertyBattleLog() {
		return propertyBattleLog;
	}

	/**
	 * @param propertyBattleLog
	 *            the propertyBattleLog to set
	 */
	public void setPropertyBattleLog(PropertyBattle propertyBattleLog) {
		this.propertyBattleLog = propertyBattleLog;
	}

	/**
	 * @return the isBoss
	 */
	public boolean isBoss() {
		return isBoss;
	}

	/**
	 * @param isBoss
	 *            the isBoss to set
	 */
	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	/**
	 * @return the shenbingId
	 */
	public int getShenbingId() {
		return shenbingId;
	}

	/**
	 * @param shenbingId
	 *            the shenbingId to set
	 */
	public void setShenbingId(int shenbingId) {
		this.shenbingId = shenbingId;
	}

	/**
	 * @return the shizhuangId
	 */
	public int getShizhuangId() {
		return shizhuangId;
	}

	/**
	 * @param shizhuangId
	 *            the shizhuangId to set
	 */
	public void setShizhuangId(int shizhuangId) {
		this.shizhuangId = shizhuangId;
	}

}
