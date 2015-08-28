package iyunu.NewTLOL.model.shenbing.res;

import iyunu.NewTLOL.enumeration.common.ECostType;

/**
 * @function 神兵升星升阶
 * @author LuoSR
 * @date 2014年7月14日
 */
public class ShenbingUpRes {

	/** 神兵编号 **/
	private int id;
	/** 升阶升星后编号 **/
	private int upId;
	private int stoneId;
	/** 消耗强化石数量 **/
	private int stoneNum;
	/** 消耗货币类型 **/
	private ECostType costType;
	/** 消耗货币数量 **/
	private int costValue;
	private int lingshiId;
	private int lingshiNumber;
	private int money;

	private int cstoneId; // 水晶石编号
	private int cstoneNum; // 水晶石数量

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
	 * @return the upId
	 */
	public int getUpId() {
		return upId;
	}

	/**
	 * @param upId
	 *            the upId to set
	 */
	public void setUpId(int upId) {
		this.upId = upId;
	}

	/**
	 * @return the stoneId
	 */
	public int getStoneId() {
		return stoneId;
	}

	/**
	 * @param stoneId
	 *            the stoneId to set
	 */
	public void setStoneId(int stoneId) {
		this.stoneId = stoneId;
	}

	/**
	 * @return the stoneNum
	 */
	public int getStoneNum() {
		return stoneNum;
	}

	/**
	 * @param stoneNum
	 *            the stoneNum to set
	 */
	public void setStoneNum(int stoneNum) {
		this.stoneNum = stoneNum;
	}

	/**
	 * @return the costType
	 */
	public ECostType getCostType() {
		return costType;
	}

	/**
	 * @param costType
	 *            the costType to set
	 */
	public void setCostType(ECostType costType) {
		this.costType = costType;
	}

	/**
	 * @return the costValue
	 */
	public int getCostValue() {
		return costValue;
	}

	/**
	 * @param costValue
	 *            the costValue to set
	 */
	public void setCostValue(int costValue) {
		this.costValue = costValue;
	}

	/**
	 * @return the lingshiId
	 */
	public int getLingshiId() {
		return lingshiId;
	}

	/**
	 * @param lingshiId
	 *            the lingshiId to set
	 */
	public void setLingshiId(int lingshiId) {
		this.lingshiId = lingshiId;
	}

	/**
	 * @return the lingshiNumber
	 */
	public int getLingshiNumber() {
		return lingshiNumber;
	}

	/**
	 * @param lingshiNumber
	 *            the lingshiNumber to set
	 */
	public void setLingshiNumber(int lingshiNumber) {
		this.lingshiNumber = lingshiNumber;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the cstoneId
	 */
	public int getCstoneId() {
		return cstoneId;
	}

	/**
	 * @param cstoneId
	 *            the cstoneId to set
	 */
	public void setCstoneId(int cstoneId) {
		this.cstoneId = cstoneId;
	}

	/**
	 * @return the cstoneNum
	 */
	public int getCstoneNum() {
		return cstoneNum;
	}

	/**
	 * @param cstoneNum
	 *            the cstoneNum to set
	 */
	public void setCstoneNum(int cstoneNum) {
		this.cstoneNum = cstoneNum;
	}

}
