package iyunu.NewTLOL.model.activity;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分榜物品
 * 
 * @author fhy
 * 
 */
public class PayExchangeRes {

	private int id;
	private int partnerIndex;
	private String itemId;
	private int left;
	private int right;

	public PayExchange toPayeExchange() {
		PayExchange p = new PayExchange();
		p.setId(id);
		p.setPartnerIndex(partnerIndex);
		List<MonsterDropItem> item = new ArrayList<MonsterDropItem>();
		if (itemId != null && !"".equals(itemId)) {
			String[] stings = itemId.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				item.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		p.setItemId(item);
		p.setLeft(left);
		p.setRight(right);
		return p;
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
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
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
