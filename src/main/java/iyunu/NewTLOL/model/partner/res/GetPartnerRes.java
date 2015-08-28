package iyunu.NewTLOL.model.partner.res;

/**
 * @function 伙伴招募
 */
public class GetPartnerRes {

	private int id; // 1 方式1级别1 4 方式2级别1 7 方式3级别1
	// 2 方式1级别2 5 方式2级别2 8 方式3级别2
	// 3 方式1级别3 6 方式2级别3 9 方式3级别3

	private int index;
	private int chance;

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
	 * @return the chance
	 */
	public int getChance() {
		return chance;
	}

	/**
	 * @param chance
	 *            the chance to set
	 */
	public void setChance(int chance) {
		this.chance = chance;
	}

}
