package iyunu.NewTLOL.model.buffRole.res;

import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.util.Time;

public class BuffRoleRes {

	private int id;
	private String name;
	private EBuffType type;
	private int level; // 级别
	private int valueType;
	private int value;
	private int time;
	private String icon;
	private String desc;

	public BuffRole toBuffRole() {
		BuffRole buffRole = new BuffRole();
		buffRole.setId(id);
		buffRole.setName(name);
		buffRole.setType(type);
		buffRole.setLevel(level);
		buffRole.setValueType(valueType);
		buffRole.setValue(value);
		buffRole.setTime(time);
		if (time == -1) {
			buffRole.setFinishTime(-1);
		} else {
			buffRole.setFinishTime(System.currentTimeMillis() + time * Time.MILLISECOND);
		}

		buffRole.setIcon(icon);
		buffRole.setDesc(desc);
		return buffRole;
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

}
