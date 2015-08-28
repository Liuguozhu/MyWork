package iyunu.NewTLOL.model.lessItem;

/**
 * @function 缺少物品提示
 * @author fhy
 */
public class LessItemRes {

	/** 需要 itemId **/
	private int id;
	/** 指向的itemId */
	private int idTo;
	/** 倍数 */
	private int times;
	/** 单价 **/
	private int price;

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
	 * @return the idTo
	 */
	public int getIdTo() {
		return idTo;
	}

	/**
	 * @param idTo
	 *            the idTo to set
	 */
	public void setIdTo(int idTo) {
		this.idTo = idTo;
	}

	/**
	 * @return the times
	 */
	public int getTimes() {
		return times;
	}

	/**
	 * @param times
	 *            the times to set
	 */
	public void setTimes(int times) {
		this.times = times;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}
