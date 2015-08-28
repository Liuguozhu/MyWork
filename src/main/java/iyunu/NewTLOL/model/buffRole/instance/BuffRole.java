package iyunu.NewTLOL.model.buffRole.instance;

import iyunu.NewTLOL.model.buffRole.EBuffType;

public class BuffRole {

	private int id;
	private String name;
	private EBuffType type;
	private int level; // 级别
	private int valueType;
	private int value;
	private int time;
	private long finishTime;
	private String icon;
	private String desc;

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

	/**
	 * @return the type
	 */
	public EBuffType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EBuffType type) {
		this.type = type;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the valueType
	 */
	public int getValueType() {
		return valueType;
	}

	/**
	 * @param valueType
	 *            the valueType to set
	 */
	public void setValueType(int valueType) {
		this.valueType = valueType;
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
	 * @return the finishTime
	 */
	public long getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime
	 *            the finishTime to set
	 */
	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

}
