package iyunu.NewTLOL.model.map;

import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.model.monster.instance.MonsterBox;

import java.util.ArrayList;
import java.util.List;

public class ItemOnMap {

	private long uid; // 唯一编号
	private int mapId;
	private int x;
	private int y;
	private int monsterBoxId;
	private String name;
	private String icon;
	private List<Long> ownerIds = new ArrayList<>();

	public ItemOnMap(int mapId, int x, int y, MonsterBox monsterBox) {
		this.uid = UidManager.instance().uid();
		this.mapId = mapId;
		this.x = x;
		this.y = y;
		this.monsterBoxId = monsterBox.getId();
		this.name = monsterBox.getName();
		this.icon = monsterBox.getIcon();
	}

	public ItemOnMap() {

	}

	public boolean check(long roleId) {
		if (ownerIds.size() <= 0) {
			return true;
		} else {
			return ownerIds.contains(roleId);
		}

	}

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
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
	 * @return the monsterBoxId
	 */
	public int getMonsterBoxId() {
		return monsterBoxId;
	}

	/**
	 * @param monsterBoxId
	 *            the monsterBoxId to set
	 */
	public void setMonsterBoxId(int monsterBoxId) {
		this.monsterBoxId = monsterBoxId;
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
	 * @return the ownerIds
	 */
	public List<Long> getOwnerIds() {
		return ownerIds;
	}

}
