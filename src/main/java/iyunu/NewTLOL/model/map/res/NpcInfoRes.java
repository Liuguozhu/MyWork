package iyunu.NewTLOL.model.map.res;

import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.NpcInfo;

/**
 * 资源类 NPC信息
 * 
 * @author SunHonglei
 * 
 */
public class NpcInfoRes {

	/** NPC编号 **/
	private int id;
	/** NPC名称 **/
	private String name;
	private int map; // 所在地图编号
	/** x坐标 **/
	private int x;
	/** y坐标 **/
	private int y;
	/** 资源路径 **/
	private String url = "";
	/** 资源名称 **/
	private String resName = "";
	private String dialogue = ""; // 对话
	private int dir; // 朝向
	private String fnctn; // 功能
	private String photo; // 头像

	public NpcInfo toNpcInfo() {
		NpcInfo npcInfo = new NpcInfo();
		npcInfo.setId(id);
		npcInfo.setName(name);
		npcInfo.setMap(map);
		npcInfo.setX(x);
		npcInfo.setY(y);
		BaseMap mapInfo = MapJson.instance().getMapById(map);
		mapInfo.getNpcs().add(id);
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
	 * @return the dialogue
	 */
	public String getDialogue() {
		return dialogue;
	}

	/**
	 * @param dialogue
	 *            the dialogue to set
	 */
	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	/**
	 * @return the dir
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * @return the fnctn
	 */
	public String getFnctn() {
		return fnctn;
	}

	/**
	 * @param fnctn
	 *            the fnctn to set
	 */
	public void setFnctn(String fnctn) {
		this.fnctn = fnctn;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
