package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.util.Translate;

/**
 * 物品资源类
 * 
 * @author SunHonglei
 * 
 */
public class BaseItemRes {

	/** 编号 **/
	protected int id;
	/** 名称 **/
	protected String name;
	/** 类型 **/
	protected EItem type;
	/** 类型名称 **/
	protected String typeName;
	/** 颜色 **/
	protected EColor color = EColor.white;
	/** 使用提示 **/
	protected int mark;
	/** 使用等级 **/
	protected int level;
	/** 展示等级 **/
	protected int showLevel;
	/** 描述 **/
	protected String desc;
	/** 最大叠加数量 **/
	protected int max;
	/** 使用方式(0使用，1消耗) **/
	protected int way;
	/** 是否可以战斗使用(0均可使用，1非战斗使用，2战斗使用) **/
	protected int apply;
	/** 丢弃返还金钱 **/
	protected int returnGold;
	/** 背包显示优先级 **/
	protected int priority;
	/** 数值 **/
	protected int value;
	/** 是否可以出售 **/
	protected int isSell;
	/** 图标 **/
	protected String icon;
	/** 是否可以交易（0.可以，1.不可以） **/
	protected int isDeal;
	/** 是否公告通知 **/
	protected int isNotice;
	/** 时间 **/
	protected int time;
	/** 收集任务编号 **/
	protected String tasks; // 任务集合

	public Item toItem() {
		Item item = null;
		try {
			item = type.getObject().newInstance();
			item.setId(id);
			item.setName(name);
			item.setType(type);
			item.setTypeName(typeName);
			item.setColor(color);
			item.setMark(mark);
			item.setLevel(level);
			item.setShowLevel(showLevel);
			item.setDesc(desc);
			item.setMax(max);
			item.setWay(way);
			item.setApply(apply);
			item.setReturnGold(returnGold);
			item.setPriority(priority);
			item.setValue(value);
			item.setIsSell(isSell);
			item.setIcon(icon);
			item.setIsDeal(isDeal);
			item.setIsNotice(isNotice);
			item.setTime(time);

			if (tasks != null && !"".equals(tasks)) {
				String[] taskIds = tasks.split(";");
				for (String taskId : taskIds) {
					item.getTaskIds().add(Translate.stringToInt(taskId));
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return item;
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
	public EItem getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EItem type) {
		this.type = type;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
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
	 * @return the showLevel
	 */
	public int getShowLevel() {
		return showLevel;
	}

	/**
	 * @param showLevel
	 *            the showLevel to set
	 */
	public void setShowLevel(int showLevel) {
		this.showLevel = showLevel;
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
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the way
	 */
	public int getWay() {
		return way;
	}

	/**
	 * @param way
	 *            the way to set
	 */
	public void setWay(int way) {
		this.way = way;
	}

	/**
	 * @return the apply
	 */
	public int getApply() {
		return apply;
	}

	/**
	 * @param apply
	 *            the apply to set
	 */
	public void setApply(int apply) {
		this.apply = apply;
	}

	/**
	 * @return the returnGold
	 */
	public int getReturnGold() {
		return returnGold;
	}

	/**
	 * @param returnGold
	 *            the returnGold to set
	 */
	public void setReturnGold(int returnGold) {
		this.returnGold = returnGold;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
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
	 * @return the isSell
	 */
	public int getIsSell() {
		return isSell;
	}

	/**
	 * @param isSell
	 *            the isSell to set
	 */
	public void setIsSell(int isSell) {
		this.isSell = isSell;
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
	 * @return the isDeal
	 */
	public int getIsDeal() {
		return isDeal;
	}

	/**
	 * @param isDeal
	 *            the isDeal to set
	 */
	public void setIsDeal(int isDeal) {
		this.isDeal = isDeal;
	}

	/**
	 * @return the isNotice
	 */
	public int getIsNotice() {
		return isNotice;
	}

	/**
	 * @param isNotice
	 *            the isNotice to set
	 */
	public void setIsNotice(int isNotice) {
		this.isNotice = isNotice;
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
	 * @return the tasks
	 */
	public String getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(String tasks) {
		this.tasks = tasks;
	}

}
