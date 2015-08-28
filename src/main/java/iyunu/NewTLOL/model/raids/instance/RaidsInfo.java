package iyunu.NewTLOL.model.raids.instance;

import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 副本资源类
 * 
 * @author SunHonglei
 * 
 */
public class RaidsInfo {

	private int raidsId; // 副本编号
	private int index; // 副本索引
	private String name; // 副本名称
	private int degree; // 难度
	private int arriveMap; // 对应地图
	private int arriveX; // x坐标
	private int arriveY;// y坐标

	private int outMap; // 离开后地图
	private int outX; // 离开后x坐标
	private int outY; // 离开后y坐标

	private int memberLimit; // 人数限制
	private int numberLimit; // 次数限制
	private int levelLimit; // 等级限制

	private int time; // 副本时间
	private int floorMax; // 总层数
	private int size; // 格子数量
	private int box;// 宝箱编号
	private int surprise; // 大礼包

	private Map<Integer, List<Integer>> monsters = new HashMap<Integer, List<Integer>>(); // 怪物<副本层数，怪物集合>
	private Map<Integer, Integer> boss = new HashMap<Integer, Integer>();

	public int getBossByFloor(int floor) {
		return boss.get(floor);
	}

	public int randomMonster(int floor) {
		if (monsters.containsKey(floor)) {
			List<Integer> list = monsters.get(floor);
			return list.get(Util.getRandom(list.size()));
		} else {
			LogManager.exception("副本怪物异常，floor=" + floor);
			return 0;
		}
	}

	/**
	 * @return the raidsId
	 */
	public int getRaidsId() {
		return raidsId;
	}

	/**
	 * @param raidsId
	 *            the raidsId to set
	 */
	public void setRaidsId(int raidsId) {
		this.raidsId = raidsId;
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
	 * @return the degree
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}

	/**
	 * @return the arriveMap
	 */
	public int getArriveMap() {
		return arriveMap;
	}

	/**
	 * @param arriveMap
	 *            the arriveMap to set
	 */
	public void setArriveMap(int arriveMap) {
		this.arriveMap = arriveMap;
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
	 * @return the outMap
	 */
	public int getOutMap() {
		return outMap;
	}

	/**
	 * @param outMap
	 *            the outMap to set
	 */
	public void setOutMap(int outMap) {
		this.outMap = outMap;
	}

	/**
	 * @return the outX
	 */
	public int getOutX() {
		return outX;
	}

	/**
	 * @param outX
	 *            the outX to set
	 */
	public void setOutX(int outX) {
		this.outX = outX;
	}

	/**
	 * @return the outY
	 */
	public int getOutY() {
		return outY;
	}

	/**
	 * @param outY
	 *            the outY to set
	 */
	public void setOutY(int outY) {
		this.outY = outY;
	}

	/**
	 * @return the memberLimit
	 */
	public int getMemberLimit() {
		return memberLimit;
	}

	/**
	 * @param memberLimit
	 *            the memberLimit to set
	 */
	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	/**
	 * @return the numberLimit
	 */
	public int getNumberLimit() {
		return numberLimit;
	}

	/**
	 * @param numberLimit
	 *            the numberLimit to set
	 */
	public void setNumberLimit(int numberLimit) {
		this.numberLimit = numberLimit;
	}

	/**
	 * @return the levelLimit
	 */
	public int getLevelLimit() {
		return levelLimit;
	}

	/**
	 * @param levelLimit
	 *            the levelLimit to set
	 */
	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
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
	 * @return the floorMax
	 */
	public int getFloorMax() {
		return floorMax;
	}

	/**
	 * @param floorMax
	 *            the floorMax to set
	 */
	public void setFloorMax(int floorMax) {
		this.floorMax = floorMax;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the monsters
	 */
	public Map<Integer, List<Integer>> getMonsters() {
		return monsters;
	}

	/**
	 * @param monsters
	 *            the monsters to set
	 */
	public void setMonsters(Map<Integer, List<Integer>> monsters) {
		this.monsters = monsters;
	}

	/**
	 * @return the boss
	 */
	public Map<Integer, Integer> getBoss() {
		return boss;
	}

	/**
	 * @param boss
	 *            the boss to set
	 */
	public void setBoss(Map<Integer, Integer> boss) {
		this.boss = boss;
	}

	/**
	 * @return the surprise
	 */
	public int getSurprise() {
		return surprise;
	}

	/**
	 * @param surprise
	 *            the surprise to set
	 */
	public void setSurprise(int surprise) {
		this.surprise = surprise;
	}

	/**
	 * @return the box
	 */
	public int getBox() {
		return box;
	}

	/**
	 * @param box
	 *            the box to set
	 */
	public void setBox(int box) {
		this.box = box;
	}

}
