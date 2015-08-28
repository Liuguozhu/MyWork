package iyunu.NewTLOL.model.map.instance;

/**
 * NPC信息
 * 
 * @author SunHonglei
 * 
 */
public class NpcInfo {

	/** NPC编号 **/
	private int id;
	/** NPC名称 **/
	private String name;
	private int map; // 所在地图编号
	/** x坐标 **/
	private int x;
	/** y坐标 **/
	private int y;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public NpcInfo copy() {
		NpcInfo npcInfo = new NpcInfo();
		npcInfo.setId(id);
		npcInfo.setName(name);
		npcInfo.setMap(map);
		npcInfo.setX(x);
		npcInfo.setY(y);
		return npcInfo;
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

}
