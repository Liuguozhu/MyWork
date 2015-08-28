package iyunu.NewTLOL.model.map.res;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.DistributionHidden;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.map.instance.MapCommonInfo;
import iyunu.NewTLOL.model.map.instance.MapGangFightInfo;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.Util;

/**
 * 资源类 地图信息
 * 
 * @author SunHonglei
 * 
 */
public class MapInfoRes {

	/** 地图编号 **/
	private int id;
	/** 地图名称 **/
	private String name;
	private EMapType type; // 地图类型
	/** 是否可以遇怪(0.可以，1.不可以) **/
	private int pve;
	/** 是否可以战斗(0.可以，1.不可以) **/
	private int pvp;
	/** 是否可以组队(0.可以，1.不可以) **/
	private int team;
	private String resName = ""; // 资源名称
	/** 资源路径 **/
	private String url = "";
	/** 宽 **/
	private int width = 960;
	/** 高 **/
	private int height = 540;
	private String distribution; // 怪物分配
	private String eliteDistribution; // 精英怪物分配
	private int transmitX; // 传送X坐标
	private int transmitY; // 传送Y坐标
	private int guaji; // 是否可以挂机

	public BaseMap toMapInfo() {

		if (type.equals(EMapType.raids)) { // 副本地图

			MapRaidsInfo mapInfo = new MapRaidsInfo();
			mapInfo.setId(id);
			mapInfo.setName(name);
			mapInfo.setPve(pve);
			mapInfo.setPvp(pvp);
			mapInfo.setTeam(Util.trueOrFalse(team));
			mapInfo.setWidth(width / 16);
			mapInfo.setHeight(height / 16);
			mapInfo.setType(type);
			mapInfo.setTransmitX(transmitX);
			mapInfo.setTransmitY(transmitY);
			// mapInfo.init(); // 初始化
			return mapInfo;
		} else if (type.equals(EMapType.gang)) { // 帮派地图
			MapGangInfo mapInfo = new MapGangInfo();
			mapInfo.setId(id);
			mapInfo.setName(name);
			mapInfo.setPve(pve);
			mapInfo.setPvp(pvp);
			mapInfo.setTeam(Util.trueOrFalse(team));
			mapInfo.setWidth(width / 16);
			mapInfo.setHeight(height / 16);
			mapInfo.setType(type);
			mapInfo.setTransmitX(transmitX);
			mapInfo.setTransmitY(transmitY);
			// mapInfo.init(); // 初始化
			return mapInfo;
		} else if (type.equals(EMapType.gangFight)) { // 帮派战地图
			MapGangFightInfo mapGangFightInfo = new MapGangFightInfo();
			mapGangFightInfo.setId(id);
			mapGangFightInfo.setName(name);
			mapGangFightInfo.setPve(pve);
			mapGangFightInfo.setPvp(pvp);
			mapGangFightInfo.setTeam(Util.trueOrFalse(team));
			mapGangFightInfo.setWidth(width / 16);
			mapGangFightInfo.setHeight(height / 16);
			mapGangFightInfo.setType(type);
			mapGangFightInfo.setTransmitX(transmitX);
			mapGangFightInfo.setTransmitY(transmitY);
			// mapInfo.init(); // 初始化
			return mapGangFightInfo;
		} else { // 普通地图
			MapCommonInfo mapInfo = new MapCommonInfo();
			mapInfo.setId(id);
			mapInfo.setName(name);
			mapInfo.setPve(pve);
			mapInfo.setPvp(pvp);
			mapInfo.setTeam(Util.trueOrFalse(team));
			mapInfo.setWidth(width / 16);
			mapInfo.setHeight(height / 16);
			mapInfo.setType(type);
			mapInfo.setTransmitX(transmitX);
			mapInfo.setTransmitY(transmitY);
			if (pve == 0 && distribution != null && !distribution.equals("")) {
				String[] strings = distribution.split(";");
				for (String string : strings) {

					String[] strs = string.split(":");
					int monsterGroup = Translate.stringToInt(strs[4]);
					if (MonsterJson.instance().checkMonsterGroupId(monsterGroup)) {
						DistributionHidden distribution = new DistributionHidden();
						distribution.setMonsterGroup(monsterGroup);
						distribution.setxMin(Translate.stringToInt(strs[0]));
						distribution.setyMin(Translate.stringToInt(strs[1]));
						distribution.setxMax(Translate.stringToInt(strs[2]));
						distribution.setyMax(Translate.stringToInt(strs[3]));
						mapInfo.getDistributions().add(distribution);
					}
				}
			}
			
			if (pve == 0 && eliteDistribution != null && !eliteDistribution.equals("")) {
				String[] strings = eliteDistribution.split(";");
				for (String string : strings) {
					
					String[] strs = string.split(":");
					int monsterGroup = Translate.stringToInt(strs[4]);
					if (MonsterJson.instance().checkMonsterGroupId(monsterGroup)) {
						DistributionHidden distribution = new DistributionHidden();
						distribution.setMonsterGroup(monsterGroup);
						distribution.setxMin(Translate.stringToInt(strs[0]));
						distribution.setyMin(Translate.stringToInt(strs[1]));
						distribution.setxMax(Translate.stringToInt(strs[2]));
						distribution.setyMax(Translate.stringToInt(strs[3]));
						mapInfo.getEliteDistributions().add(distribution);
					}
				}
			}
			// mapInfo.init(); // 初始化
			return mapInfo;
		}

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
	 * @return the pve
	 */
	public int getPve() {
		return pve;
	}

	/**
	 * @param pve
	 *            the pve to set
	 */
	public void setPve(int pve) {
		this.pve = pve;
	}

	/**
	 * @return the pvp
	 */
	public int getPvp() {
		return pvp;
	}

	/**
	 * @param pvp
	 *            the pvp to set
	 */
	public void setPvp(int pvp) {
		this.pvp = pvp;
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
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
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
	 * @return the distribution
	 */
	public String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution
	 *            the distribution to set
	 */
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	/**
	 * @return the type
	 */
	public EMapType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EMapType type) {
		this.type = type;
	}

	public int getTransmitX() {
		return transmitX;
	}

	public void setTransmitX(int transmitX) {
		this.transmitX = transmitX;
	}

	public int getTransmitY() {
		return transmitY;
	}

	public void setTransmitY(int transmitY) {
		this.transmitY = transmitY;
	}

	/**
	 * @return the guaji
	 */
	public int getGuaji() {
		return guaji;
	}

	/**
	 * @param guaji
	 *            the guaji to set
	 */
	public void setGuaji(int guaji) {
		this.guaji = guaji;
	}

	/**
	 * @return the team
	 */
	public int getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * @return the eliteDistribution
	 */
	public String getEliteDistribution() {
		return eliteDistribution;
	}

	/**
	 * @param eliteDistribution
	 *            the eliteDistribution to set
	 */
	public void setEliteDistribution(String eliteDistribution) {
		this.eliteDistribution = eliteDistribution;
	}

}
