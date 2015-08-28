package iyunu.NewTLOL.model.auction;

import java.util.ArrayList;

public enum EAuctionType {

	/** 武器 */
	weapon("武器"), //
	/** 药品 */
	drug("药品"), //
	/** 防具 */
	armor("防具"), //
	/** 首饰 */
	jewelry("首饰"), //
	/** 伙伴 */
	partner("伙伴"), //
	/** 元宝 */
	money("元宝"), //
	/** 其它 */
	other("其它"), //
	/** 技能书 **/
	book("技能书"), //
	/** 材料 **/
	material("材料"), //
	/** 宝石 **/
	stone("宝石"), //
	/**神兵**/
	shenbing("神兵"), //

	;

	private final String name;
	private ArrayList<Long> list = new ArrayList<Long>();

	private EAuctionType(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the list
	 */
	public ArrayList<Long> getList() {
		return list;
	}

	/**
	 * 添加分类拍卖产品
	 * 
	 * @param id
	 */
	public boolean add(long id) {
		return list.add(id);
	}

	/**
	 * 删除分类拍卖产品
	 * 
	 * @param id
	 */
	public void minus(long id) {
		if (list.contains(id)) {
			list.remove(id);
		}
	}
}
