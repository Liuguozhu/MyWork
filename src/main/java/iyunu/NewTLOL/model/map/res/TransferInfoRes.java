package iyunu.NewTLOL.model.map.res;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EOrientation;
import iyunu.NewTLOL.model.map.instance.TransferInfo;

/**
 * 资源类 传送点信息
 * 
 * @author SunHonglei
 * 
 */
public class TransferInfoRes {

	/** 传送点编号 **/
	private int id;
	private int map; // 所在地图编号
	/** x坐标 **/
	private int x;
	/** y坐标 **/
	private int y;
	/** 目的地地图编号 **/
	private int destination;
	/** 目标x坐标 **/
	private int arriveX;
	/** 目标y坐标 **/
	private int arriveY;
	/** 资源名称 **/
	private String resName = "";
	/** 目标地图名称 **/
	private String arriveName = "";
	/** 资源路径 **/
	private String url = "";
	private EOrientation orientation;

	public TransferInfo toTransferInfo() {
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setId(id);
		transferInfo.setMap(map);
		transferInfo.setX(x);
		transferInfo.setY(y);
		transferInfo.setDestination(destination);
		transferInfo.setArriveX(arriveX);
		transferInfo.setArriveY(arriveY);
		BaseMap mapInfo = MapJson.instance().getMapById(map);
		mapInfo.getTransfers().add(id);
		transferInfo.setOrientation(orientation);
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
	 * @return the destination
	 */
	public int getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(int destination) {
		this.destination = destination;
	}

	/**
	 * @return the arriveX
	 */
	public int getArriveX() {
		return arriveX;
	}

	/**
	 * @param arriveX
	 *            the arriveX to set
	 */
	public void setArriveX(int arriveX) {
		this.arriveX = arriveX;
	}

	/**
	 * @return the arriveY
	 */
	public int getArriveY() {
		return arriveY;
	}

	/**
	 * @param arriveY
	 *            the arriveY to set
	 */
	public void setArriveY(int arriveY) {
		this.arriveY = arriveY;
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

	public String getArriveName() {
		return arriveName;
	}

	public void setArriveName(String arriveName) {
		this.arriveName = arriveName;
	}

	/**
	 * @return the orientation
	 */
	public EOrientation getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(EOrientation orientation) {
		this.orientation = orientation;
	}

}
