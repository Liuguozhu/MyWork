package iyunu.NewTLOL.model.map.res;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.CollectedInfo;
import iyunu.NewTLOL.util.Translate;

/**
 * 资源类 采集物品信息
 * 
 * @author SunHonglei
 * 
 */
public class CollectedInfoRes {

	/** 传送点编号 **/
	private int id;
	private int map; // 所在地图编号
	private String name;
	/** x坐标 **/
	private int x;
	/** y坐标 **/
	private int y;
	/** 资源路径 **/
	private String url = "";
	/** 资源名称 **/
	private String resName = "";
	private int itemId; // 获得物品编号
	private String taskId; // 任务编号

	public CollectedInfo toCollectedInfo() {
		CollectedInfo transferInfo = new CollectedInfo();
		transferInfo.setId(id);
		transferInfo.setMap(map);
		transferInfo.setX(x);
		transferInfo.setY(y);
		transferInfo.setItemId(itemId);
		if (taskId != null && !"".equals(taskId)) {
			String[] str = taskId.split(";");
			for (String string : str) {
				transferInfo.getTaskIds().add(Translate.stringToInt(string));
			}
		}
		BaseMap mapInfo = MapJson.instance().getMapById(map);
		mapInfo.getCollecteds().add(id);
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName
	 *            the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
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
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

}
