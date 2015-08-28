package iyunu.NewTLOL.model.item;

import iyunu.NewTLOL.model.shenbing.EAddType;

public class AddProperty {

	private EAddType type;
	private int value;
	private int maxValue;

	/**
	 * @return the type
	 */
	public EAddType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EAddType type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}
