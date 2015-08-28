package iyunu.NewTLOL.model.intensify.res;

import iyunu.NewTLOL.enumeration.common.ECostType;

/**
 * 装备分解
 * 
 * @author SunHonglei
 * 
 */
public class IntensifyStoneUpRes {

	private int oldId;
	private int newId;
	private ECostType costType;
	private int costValue;

	/**
	 * @return the oldId
	 */
	public int getOldId() {
		return oldId;
	}

	/**
	 * @param oldId
	 *            the oldId to set
	 */
	public void setOldId(int oldId) {
		this.oldId = oldId;
	}

	/**
	 * @return the newId
	 */
	public int getNewId() {
		return newId;
	}

	/**
	 * @param newId
	 *            the newId to set
	 */
	public void setNewId(int newId) {
		this.newId = newId;
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

}
