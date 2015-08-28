package iyunu.NewTLOL.model.raids.res;

import iyunu.NewTLOL.model.raids.instance.RaidsInfo;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * 副本资源类
 * 
 * @author SunHonglei
 * 
 */
public class RaidsInfoRes {

	private int id; // 副本编号
	private int index; // 副本索引
	private String name; // 副本名称
	private int degree; // 难度
	private int arriveMap; // 对应地图
	private int arriveX; // x坐标
	private int arriveY;// y坐标
	private int outMap; // 离开后地图
	private int outX; // 离开后x坐标
	private int outY; // 离开后y坐标
	private int levelLimit; // 等级限制
	private int memberLimit; // 人数限制
	private int numberLimit; // 次数限制

	private int time; // 副本时间
	private int floorMax; // 层数
	private int size; // 格子数量
	private int boxNum;// 宝箱数量
	private int box; // 宝箱
	private String monster; // 怪物
	private String boss; // BOSS
	private String imgName; // 资源名称
	private String item; // 奖励物品
	private String partner; // 奖励伙伴
	private int surprise; // 大礼包

	public RaidsInfo toRaidsInfo() {
		RaidsInfo raidsTeamInfo = new RaidsInfo();

		raidsTeamInfo.setRaidsId(id);
		raidsTeamInfo.setIndex(index);
		raidsTeamInfo.setName(name);
		raidsTeamInfo.setDegree(degree);

		raidsTeamInfo.setArriveMap(arriveMap);
		raidsTeamInfo.setArriveX(arriveX);
		raidsTeamInfo.setArriveY(arriveY);
		raidsTeamInfo.setOutMap(outMap);
		raidsTeamInfo.setOutX(outX);
		raidsTeamInfo.setOutY(outY);

		raidsTeamInfo.setLevelLimit(levelLimit);
		raidsTeamInfo.setMemberLimit(memberLimit);
		raidsTeamInfo.setNumberLimit(numberLimit);

		raidsTeamInfo.setTime(time);
		raidsTeamInfo.setFloorMax(floorMax);
		raidsTeamInfo.setSize(size);
		raidsTeamInfo.setBox(box);
		raidsTeamInfo.setSurprise(surprise);

		if (boss != null && !"".equals(boss)) {
			String[] strs = boss.split(";");
			for (int i = 0; i < strs.length; i++) {
				raidsTeamInfo.getBoss().put(i + 1, Translate.stringToInt(strs[i]));
			}
		}

		if (monster != null && !"".equals(monster)) {
			String[] strs = monster.split(";");
			for (int i = 0; i < strs.length; i++) {
				List<Integer> list = null;
				if (raidsTeamInfo.getMonsters().containsKey(i + 1)) {
					list = raidsTeamInfo.getMonsters().get(i + 1);
				} else {
					list = new ArrayList<Integer>();
					raidsTeamInfo.getMonsters().put(i + 1, list);
				}

				for (String string : strs[i].split(":")) {
					list.add(Translate.stringToInt(string));
				}
			}
		}

		return raidsTeamInfo;
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
	 * @return the boxNum
	 */
	public int getBoxNum() {
		return boxNum;
	}

	/**
	 * @param boxNum
	 *            the boxNum to set
	 */
	public void setBoxNum(int boxNum) {
		this.boxNum = boxNum;
	}

	/**
	 * @return the monster
	 */
	public String getMonster() {
		return monster;
	}

	/**
	 * @param monster
	 *            the monster to set
	 */
	public void setMonster(String monster) {
		this.monster = monster;
	}

	/**
	 * @return the boss
	 */
	public String getBoss() {
		return boss;
	}

	/**
	 * @param boss
	 *            the boss to set
	 */
	public void setBoss(String boss) {
		this.boss = boss;
	}

	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}

	/**
	 * @param imgName
	 *            the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
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

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

}
