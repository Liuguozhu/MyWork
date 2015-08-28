package iyunu.NewTLOL.model.map.instance;

import iyunu.NewTLOL.model.map.EOrientation;

/**
 * 传送点信息
 * 
 * @author SunHonglei
 * 
 */
public class TransferInfo {

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
	private EOrientation orientation;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public TransferInfo copy() {
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setId(id);
		transferInfo.setMap(map);
		transferInfo.setX(x);
		transferInfo.setY(y);
		transferInfo.setDestination(destination);
		transferInfo.setArriveX(arriveX);
		transferInfo.setArriveY(arriveY);
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
