package iyunu.NewTLOL.model.activity;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分榜物品
 * 
 * @author fhy
 * 
 */
public class PayExchange {

	private int id;
	private int partnerIndex;
	private List<MonsterDropItem> itemId = new ArrayList<MonsterDropItem>();//
	private int left;
	private int right;

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
	 * @return the partnerIndex
	 */
	public int getPartnerIndex() {
		return partnerIndex;
	}

	/**
	 * @param partnerIndex
	 *            the partnerIndex to set
	 */
	public void setPartnerIndex(int partnerIndex) {
		this.partnerIndex = partnerIndex;
	}

	/**
	 * @return the itemId
	 */
	public List<MonsterDropItem> getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(List<MonsterDropItem> itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}

}
