package iyunu.NewTLOL.model.monster.res;

import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.instance.MonsterBox;
import iyunu.NewTLOL.util.Translate;

/**
 * 地图上刷新的宝箱
 * 
 * @author SunHonglei
 * 
 */
public class MonsterBoxRes {

	private int id;
	private String name;
	private int boxType;
	private int gold;
	private int roleExp; // 角色经验
	private int partnerExp; // 伙伴经验
	private EOpen reward;
	private String item;
	private String res;
	private String partner; // 掉落伙伴

	public MonsterBox toMonsterBox() {
		MonsterBox monsterBox = new MonsterBox();
		monsterBox.setId(id);
		monsterBox.setName(name);
		monsterBox.setBoxType(boxType);
		monsterBox.setGold(gold);
		monsterBox.setRoleExp(roleExp);
		monsterBox.setPartnerExp(partnerExp);
		monsterBox.setReward(reward);
		monsterBox.setIcon(res);
		if (item != null && !"".equals(item)) {
			for (String str : item.split(";")) {
				String[] strs = str.split(":");
				monsterBox.getAward().add(new MonsterDropItem(Translate.stringToInt(strs[0]), Translate.stringToInt(strs[1]), Translate.stringToInt(strs[2]), Translate.stringToInt(strs[3])));
			}
		}

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

	/**
	 * @return the res
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res) {
		this.res = res;
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

}
