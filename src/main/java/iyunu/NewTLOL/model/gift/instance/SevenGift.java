package iyunu.NewTLOL.model.gift.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @function VIP奖励
 * @author fhy
 */
public class SevenGift {

	/** 天数 **/
	private int id;
	/** 普通物品 **/
	private List<MonsterDropItem> commonItems = new ArrayList<MonsterDropItem>();//
	/** VIP物品 **/
	private List<MonsterDropItem> vipItems = new ArrayList<MonsterDropItem>();//

	/** 普通伙伴 **/
	private List<MonsterDropItem> commonP = new ArrayList<MonsterDropItem>();//

	/** VIP伙伴 **/
	private List<MonsterDropItem> vipP = new ArrayList<MonsterDropItem>();//

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
	 * @return the commonItems
	 */
	public List<MonsterDropItem> getCommonItems() {
		return commonItems;
	}

	/**
	 * @param commonItems
	 *            the commonItems to set
	 */
	public void setCommonItems(List<MonsterDropItem> commonItems) {
		this.commonItems = commonItems;
	}

	/**
	 * @return the vipItems
	 */
	public List<MonsterDropItem> getVipItems() {
		return vipItems;
	}

	/**
	 * @param vipItems
	 *            the vipItems to set
	 */
	public void setVipItems(List<MonsterDropItem> vipItems) {
		this.vipItems = vipItems;
	}

	/**
	 * @return the commonP
	 */
	public List<MonsterDropItem> getCommonP() {
		return commonP;
	}

	/**
	 * @param commonP
	 *            the commonP to set
	 */
	public void setCommonP(List<MonsterDropItem> commonP) {
		this.commonP = commonP;
	}

	/**
	 * @return the vipP
	 */
	public List<MonsterDropItem> getVipP() {
		return vipP;
	}

	/**
	 * @param vipP
	 *            the vipP to set
	 */
	public void setVipP(List<MonsterDropItem> vipP) {
		this.vipP = vipP;
	}

}
