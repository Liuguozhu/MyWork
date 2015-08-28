package iyunu.NewTLOL.model.monster.res;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.monster.instance.Monster;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.Util;

/**
 * 资源类 怪物
 * 
 * @author SunHonglei
 * 
 */
public class MonsterRes {

	private long id; // 编号
	private String nick; // 名称
	private Vocation vocation; // 职业
	private int boss; // 是否BOSS
	private int hpMax; // 血上限
	private int mpMax; // 蓝上限
	private int pattack; // 外功攻击
	private int pdefence; // 外功防御
	private int mattack; // 内功攻击
	private int mdefence; // 内功防御
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡
	private int speed; // 速度
	private int gold; // 掉落金币
	private int roleExp; // 角色经验
	private int partnerExp; // 伙伴经验
	private int tribute; // 帮贡
	private String item; // 掉落物品
	private String taskItem; // 掉落任务物品
	private String res; // 怪物形象
	private String skill; // 技能
	private int level; // 等级
	private String taskId; // 任务集合
	private String activeSkill; // 主动技能
	private String partner; // 掉落伙伴
	private EColor color;
	private EOpen reward;

	/**
	 * 获取怪物对象
	 * 
	 * @return 怪物对象
	 */
	public Monster toMonster() {
		Monster monster = new Monster();
		monster.setId(id);
		monster.setNick(nick);
		monster.setVocation(vocation);
		monster.setBoss(Util.falseOrTrue(boss));
		monster.setHp(hpMax);
		monster.setMpMax(mpMax);
		monster.setHpMax(hpMax);
		monster.setMpMax(mpMax);
		monster.setPattack(pattack);
		monster.setPdefence(pdefence);
		monster.setMattack(mattack);
		monster.setMdefence(mdefence);
		monster.setHit(hit);
		monster.setDodge(dodge);
		monster.setCrit(crit);
		monster.setParry(parry);
		monster.setSpeed(speed);
		monster.setGold(gold);
		monster.setRoleExp(roleExp);
		monster.setPartnerExp(partnerExp);
		monster.setTribute(tribute);
		monster.setLevel(level);
		monster.setReward(reward);

		if (item != null && !"".equals(item)) {
			String[] str = item.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				monster.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
			}
		}

		if (taskItem != null && !"".equals(taskItem)) {
			String[] str = taskItem.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				monster.getTaskItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
			}
		}

		if (partner != null && !"".equals(partner)) {
			String[] str = partner.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				monster.getPartners().add(new MonsterDropPartner(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
			}
		}

		if (skill != null && !"".equals(skill)) {
			String[] str = skill.split(";");
			for (String string : str) {
				monster.getSkills().add(Translate.stringToInt(string));
			}
		}

		if (taskId != null && !"".equals(taskId)) {
			String[] str = taskId.split(";");
			for (String string : str) {
				monster.getTasks().add(Translate.stringToInt(string));
			}
		}

		if (activeSkill != null && !"".equals(activeSkill)) {
			String[] str = activeSkill.split(";");
			for (String string : str) {
				String[] skills = string.split(":");
				monster.getActiveSkills().put(Translate.stringToInt(skills[0]), Translate.stringToInt(skills[1]));
			}
		}

		return monster;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
	}

	/**
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
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
	 * @return the pattack
	 */
	public int getPattack() {
		return pattack;
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
		return pdefence;
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
		return mattack;
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
		return mdefence;
	}

	/**
	 * @param mdefence
	 *            the mdefence to set
	 */
	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	/**
	 * @return the hit
	 */
	public int getHit() {
		return hit;
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
		return dodge;
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
		return crit;
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
		return parry;
	}

	/**
	 * @param parry
	 *            the parry to set
	 */
	public void setParry(int parry) {
		this.parry = parry;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public int getRoleExp() {
		return roleExp;
	}

	public void setRoleExp(int roleExp) {
		this.roleExp = roleExp;
	}

	public int getPartnerExp() {
		return partnerExp;
	}

	public void setPartnerExp(int partnerExp) {
		this.partnerExp = partnerExp;
	}

	/**
	 * @return the taskItem
	 */
	public String getTaskItem() {
		return taskItem;
	}

	/**
	 * @param taskItem
	 *            the taskItem to set
	 */
	public void setTaskItem(String taskItem) {
		this.taskItem = taskItem;
	}

	/**
	 * @return the skill
	 */
	public String getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the tribute
	 */
	public int getTribute() {
		return tribute;
	}

	/**
	 * @param tribute
	 *            the tribute to set
	 */
	public void setTribute(int tribute) {
		this.tribute = tribute;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the boss
	 */
	public int getBoss() {
		return boss;
	}

	/**
	 * @param boss
	 *            the boss to set
	 */
	public void setBoss(int boss) {
		this.boss = boss;
	}

	/**
	 * @return the activeSkill
	 */
	public String getActiveSkill() {
		return activeSkill;
	}

	/**
	 * @param activeSkill
	 *            the activeSkill to set
	 */
	public void setActiveSkill(String activeSkill) {
		this.activeSkill = activeSkill;
	}

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

}
