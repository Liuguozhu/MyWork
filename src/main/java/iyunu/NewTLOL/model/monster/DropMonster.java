package iyunu.NewTLOL.model.monster;

/**
 * 遇怪
 * 
 * @author SunHonglei
 * 
 */
public class DropMonster {

	private int monsterGroupId; // 物品编号
	private int mapId; // 地图编号
	private int num; // 数量
	private int probability; // 概率

	public DropMonster(int monsterGroupId, int mapId, int num, int probability) {
		this.monsterGroupId = monsterGroupId;
		this.mapId = mapId;
		this.num = num;
		this.probability = probability;
	}

	/**
	 * @return the monsterGroupId
	 */
	public int getMonsterGroupId() {
		return monsterGroupId;
	}

	/**
	 * @param monsterGroupId
	 *            the monsterGroupId to set
	 */
	public void setMonsterGroupId(int monsterGroupId) {
		this.monsterGroupId = monsterGroupId;
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
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the probability
	 */
	public int getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(int probability) {
		this.probability = probability;
	}

}
