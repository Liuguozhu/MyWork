package iyunu.NewTLOL.model.map.instance;

import java.util.ArrayList;
import java.util.List;

/**
 * 传送点信息
 * 
 * @author SunHonglei
 * 
 */
public class CollectedInfo {

	/** 传送点编号 **/
	private int id;
	private int map; // 所在地图编号
	/** x坐标 **/
	private int x;
	/** y坐标 **/
	private int y;
	private int itemId; // 获得物品编号
	// private int taskId; // 任务编号
	private List<Integer> taskIds = new ArrayList<Integer>();

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public CollectedInfo copy() {
		CollectedInfo transferInfo = new CollectedInfo();
		transferInfo.setId(id);
		transferInfo.setMap(map);
		transferInfo.setX(x);
		transferInfo.setY(y);
		transferInfo.setItemId(itemId);
		transferInfo.setTaskIds(taskIds);
		return transferInfo;
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
	 * @return the map
	 */
	public int getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(int map) {
		this.map = map;
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
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the taskIds
	 */
	public List<Integer> getTaskIds() {
		return taskIds;
	}

	/**
	 * @param taskIds
	 *            the taskIds to set
	 */
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}

}
