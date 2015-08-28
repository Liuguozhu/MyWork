package iyunu.NewTLOL.model.monster.instance;

import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 地图上刷新的宝箱
 * 
 * @author SunHonglei
 * 
 */
public class MonsterBox {

	private int id;
	private String name;
	private int boxType;
	private int gold;
	private int roleExp; // 角色经验
	private int partnerExp; // 伙伴经验
	private EOpen reward;
	private ArrayList<MonsterDropItem> award = new ArrayList<MonsterDropItem>();
	// private Map<Integer, Integer> award = new HashMap<Integer, Integer>(); //
	// <物品编号，概率>
	private String icon;

	public Set<MonsterDropItem> drop() {
		Set<MonsterDropItem> list = new HashSet<MonsterDropItem>();

		if (reward.equals(EOpen.one)) {
			int probable = 0;
			int finalRate = Util.getRandom(10000);
			for (MonsterDropItem monsterItem : award) {
				probable += monsterItem.getProbability();
				if (finalRate < probable) {
					list.add(monsterItem);
					break;
				}
			}
		} else {
			// 物品
			for (MonsterDropItem monsterItem : award) {
				if (Util.probable(monsterItem.getProbability())) {
					list.add(monsterItem);
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
	public MonsterBox copy() {
		MonsterBox monsterBox = new MonsterBox();
		monsterBox.setId(id);
		monsterBox.setName(name);
		monsterBox.setBoxType(boxType);
		monsterBox.setGold(gold);
		monsterBox.setRoleExp(roleExp);
		monsterBox.setPartnerExp(partnerExp);
		monsterBox.setReward(reward);
		monsterBox.setIcon(icon);
		monsterBox.setAward(award);
		return monsterBox;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getBoxType() {
		return boxType;
	}

	public void setBoxType(int boxType) {
		this.boxType = boxType;
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
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	public ArrayList<MonsterDropItem> getAward() {
		return award;
	}

	public void setAward(ArrayList<MonsterDropItem> award) {
		this.award = award;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
