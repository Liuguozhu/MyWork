package iyunu.NewTLOL.model.monster.instance;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Monster implements ICharacter {

	@Override
	public byte getType() {
		return 2;
	}

	private long id; // 编号
	private String nick; // 名称
	private int level; // 等级
	private Vocation vocation; // 职业
	private boolean isBoss; // BOSS
	private int hp; // 血
	private int mp; // 蓝
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
	private int gold; // 掉落绑银
	private int roleExp; // 角色经验
	private int partnerExp; // 伙伴经验
	private int tribute; // 帮贡
	private EOpen reward; // 获取类型

	/** 掉落物品 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();
	/** 掉落任务物品 **/
	private ArrayList<MonsterDropItem> taskItems = new ArrayList<MonsterDropItem>();
	/** 掉落伙伴 **/
	private ArrayList<MonsterDropPartner> partners = new ArrayList<MonsterDropPartner>();
	/** 技能 **/
	private List<Integer> skills = new ArrayList<Integer>(); // 技能编号
	private Map<Integer, Integer> activeSkills = new HashMap<Integer, Integer>(); // 主动技能<技能编号，触发概率>
	private List<Integer> tasks = new ArrayList<Integer>(); // 任务集合<任务编号>

	@Override
	public int worth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 物品掉落
	 * 
	 * @return 掉落物品
	 */
	public Set<MonsterDropItem> drop() {
		Set<MonsterDropItem> list = new HashSet<MonsterDropItem>();
		if (!items.isEmpty()) {
			if (reward.equals(EOpen.all)) {
				for (MonsterDropItem monsterItem : items) {
					if (Util.probable(monsterItem.getProbability())) {
						list.add(monsterItem);
					}
				}
			} else {
				int sum = 0;
				int finalRate = Util.getRandom(10000);
				for (MonsterDropItem monsterItem : items) {
					sum += monsterItem.getProbability();
					if (finalRate < sum) {
						list.add(monsterItem);
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 掉落任务物品
	 * 
	 * @return 任务物品
	 */
	public Set<MonsterDropItem> dropTask() {
		Set<MonsterDropItem> list = new HashSet<MonsterDropItem>();
		// 任务物品
		for (MonsterDropItem monsterItem : taskItems) {
			if (Util.probable(monsterItem.getProbability())) {
				list.add(monsterItem);
			}
		}
		return list;
	}

	/**
	 * 掉落伙伴
	 * 
	 * @return 掉落伙伴
	 */
	public Set<MonsterDropPartner> dropPartner() {
		Set<MonsterDropPartner> list = new HashSet<MonsterDropPartner>();
		if (reward.equals(EOpen.all)) {
			for (MonsterDropPartner monsterDropPartner : partners) {
				if (Util.probable(monsterDropPartner.getProbability())) {
					list.add(monsterDropPartner);
				}
			}
		} else {
			int sum = 0;
			int finalRate = Util.getRandom(10000);
			for (MonsterDropPartner monsterDropPartner : partners) {
				sum += monsterDropPartner.getProbability();
				if (finalRate < sum) {
					list.add(monsterDropPartner);
					break;
				}
			}
		}
		return list;
	}

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public Monster copy() {
		Monster monster = new Monster();
		monster.setId(id);
		monster.setNick(nick);
		monster.setLevel(level);
		monster.setVocation(vocation);
		monster.setBoss(isBoss);
		monster.setHp(hpMax);
		monster.setMp(mpMax);
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
		monster.setItems(items);
		monster.setPartners(partners);
		monster.setTaskItems(taskItems);
		monster.setRoleExp(roleExp);
		monster.setPartnerExp(partnerExp);
		monster.setTribute(tribute);
		monster.setSkills(skills);
		monster.setTasks(tasks);
		monster.setActiveSkills(activeSkills);
		monster.setReward(reward);
		return monster;
	}

	/**
	 * 复制
	 * 
	 * @param level
	 *            等级
	 * @return 自身对象
	 */
	public Monster copy(int level) {
		Monster monster = new Monster();
		monster.setId(id);
		monster.setNick(nick);
		monster.setLevel(this.level);
		monster.setVocation(vocation);
		monster.setBoss(isBoss);
		monster.setHp((int) (hpMax * level * 0.55f));
		monster.setMp((int) (mpMax * level * 0.55f));
		monster.setHpMax((int) (hpMax * level * 0.55f));
		monster.setMpMax((int) (mpMax * level * 0.55f));
		monster.setPattack((int) (pattack * level * 0.55f));
		monster.setPdefence((int) (pdefence * level * 0.55f));
		monster.setMattack((int) (mattack * level * 0.55f));
		monster.setMdefence((int) (mdefence * level * 0.55f));
		monster.setHit((int) (hit * level * 0.55f));
		monster.setDodge((int) (dodge * level * 0.55f));
		monster.setCrit((int) (crit * level * 0.55f));
		monster.setParry((int) (parry * level * 0.55f));
		monster.setSpeed((int) (speed * level * 0.55f));
		monster.setGold(gold);
		monster.setItems(items);
		monster.setTaskItems(taskItems);
		monster.setRoleExp(roleExp);
		monster.setPartnerExp(partnerExp);
		monster.setTribute(tribute);
		monster.setSkills(skills);
		monster.setTasks(tasks);
		monster.setActiveSkills(activeSkills);
		monster.setReward(reward);
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
	 * @return the items
	 */
	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}

	/**
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
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

	@Override
	public long getFigure() {
		return id;
	}

	/**
	 * @return the taskItems
	 */
	public ArrayList<MonsterDropItem> getTaskItems() {
		return taskItems;
	}

	/**
	 * @param taskItems
	 *            the taskItems to set
	 */
	public void setTaskItems(ArrayList<MonsterDropItem> taskItems) {
		this.taskItems = taskItems;
	}

	/**
	 * @return the skills
	 */
	public List<Integer> getSkills() {
		return skills;
	}

	/**
	 * @param skills
	 *            the skills to set
	 */
	public void setSkills(List<Integer> skills) {
		this.skills = skills;
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
	 * @return the tasks
	 */
	public List<Integer> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(List<Integer> tasks) {
		this.tasks = tasks;
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
	 * @return the activeSkills
	 */
	public Map<Integer, Integer> getActiveSkills() {
		return activeSkills;
	}

	/**
	 * @param activeSkills
	 *            the activeSkills to set
	 */
	public void setActiveSkills(Map<Integer, Integer> activeSkills) {
		this.activeSkills = activeSkills;
	}

	/**
	 * @return the partners
	 */
	public ArrayList<MonsterDropPartner> getPartners() {
		return partners;
	}

	/**
	 * @param partners
	 *            the partners to set
	 */
	public void setPartners(ArrayList<MonsterDropPartner> partners) {
		this.partners = partners;
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
