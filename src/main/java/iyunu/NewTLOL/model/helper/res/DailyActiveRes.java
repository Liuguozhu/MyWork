package iyunu.NewTLOL.model.helper.res;

import iyunu.NewTLOL.model.helper.instance.DailyActive;
import iyunu.NewTLOL.util.Translate;

public class DailyActiveRes {

	private int index;
	private String name;
	private int level;
	private int type;
	private String button;
	private String icon;
	private int mapId;
	private int x;
	private int y;
	private String start;
	private String end;
	private String reward;
	private int timeType;
	private int npcid;

	public DailyActive toDailyActive() {
		DailyActive dailyActive = new DailyActive();

		dailyActive.setIndex(index);
		dailyActive.setName(name);
		dailyActive.setLevel(level);
		dailyActive.setType(type);
		dailyActive.setButton(button);
		dailyActive.setIcon(icon);
		dailyActive.setMapId(mapId);
		dailyActive.setX(index);
		dailyActive.setY(type);
		dailyActive.setTimeType(timeType);
		dailyActive.setNpcid(npcid);

		String[] startTime = start.split(":");
		dailyActive.setStartH(Translate.stringToInt(startTime[0]));
		dailyActive.setStartM(Translate.stringToInt(startTime[1]));
		dailyActive.setStartS(Translate.stringToInt(startTime[2]));
		String[] endTime = end.split(":");
		dailyActive.setEndH(Translate.stringToInt(endTime[0]));
		dailyActive.setEndM(Translate.stringToInt(endTime[1]));
		dailyActive.setEndS(Translate.stringToInt(endTime[2]));

		return dailyActive;
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
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the button
	 */
	public String getButton() {
		return button;
	}

	/**
	 * @param button
	 *            the button to set
	 */
	public void setButton(String button) {
		this.button = button;
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
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the mapId to set
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(String reward) {
		this.reward = reward;
	}

	/**
	 * @return the timeType
	 */
	public int getTimeType() {
		return timeType;
	}

	/**
	 * @param timeType
	 *            the timeType to set
	 */
	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public int getNpcid() {
		return npcid;
	}

	public void setNpcid(int npcid) {
		this.npcid = npcid;
	}

}
