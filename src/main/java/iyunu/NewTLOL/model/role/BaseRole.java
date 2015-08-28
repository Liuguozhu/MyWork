package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.gang.GangJobTitle;

import java.util.HashSet;

public class BaseRole {

	protected int serverId; // 服务器
	protected String srv; // 服务器名
	// 基本属性
	protected long id; // 编号
	protected String nick; // 名称
	protected Vocation vocation; // 职业
	protected long figure; // 形象
	protected int level; // 等级
	protected int gold; // 金币
	protected int exp; // 经验

	// 帮派
	protected long gangId;// 帮派id
	protected String gangName;// 帮派名称
	protected GangJobTitle jobTitle = GangJobTitle.NULL;// 帮派职位
	protected int tribute; // 帮贡
	protected HashSet<Long> askGang = new HashSet<Long>();// 已申请加入的帮派
	protected int shaoXiangNum;// 烧香次数

	protected long raidsUid = 0; // 副本唯一编号

	/** 武魂 **/
	protected int juHun;// 聚魂次数
	protected int juHunNum; // 重置聚魂次数
	protected int receiveJuhun; // 收获聚魂次数

	/**
	 * @return the serverId
	 */
	public int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
	}

	/**
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

	/**
	 * @return the figure
	 */
	public long getFigure() {
		return figure;
	}

	/**
	 * @param figure
	 *            the figure to set
	 */
	public void setFigure(long figure) {
		this.figure = figure;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the gangId
	 */
	public long getGangId() {
		return gangId;
	}

	/**
	 * @param gangId
	 *            the gangId to set
	 */
	public void setGangId(long gangId) {
		this.gangId = gangId;
	}

	/**
	 * @return the jobTitle
	 */
	public GangJobTitle getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle
	 *            the jobTitle to set
	 */
	public void setJobTitle(GangJobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the tribute
	 */
	public int getTribute() {
		return tribute;
	}

	/**
	 * @param tribute
	 *            the tribute to set
	 */
	public void setTribute(int tribute) {
		this.tribute = tribute;
	}

	/**
	 * @return the srv
	 */
	public String getSrv() {
		return srv;
	}

	/**
	 * @param srv
	 *            the srv to set
	 */
	public void setSrv(String srv) {
		this.srv = srv;
	}

	/**
	 * @return the gangName
	 */
	public String getGangName() {
		return gangName;
	}

	/**
	 * @param gangName
	 *            the gangName to set
	 */
	public void setGangName(String gangName) {
		this.gangName = gangName;
	}

	/**
	 * @return the askGang
	 */
	public HashSet<Long> getAskGang() {
		return askGang;
	}

	/**
	 * 添加申请的帮派
	 */
	public void addAskGang(long gangId) {
		this.askGang.add(gangId);
	}

	/**
	 * @param askGang
	 *            the askGang to set
	 */
	public void setAskGang(HashSet<Long> askGang) {
		this.askGang = askGang;
	}

	/**
	 * @return the raidsUid
	 */
	public long getRaidsUid() {
		return raidsUid;
	}

	/**
	 * @param raidsUid
	 *            the raidsUid to set
	 */
	public void setRaidsUid(long raidsUid) {
		this.raidsUid = raidsUid;
	}

	/**
	 * @return the shaoXiangNum
	 */
	public int getShaoXiangNum() {
		return shaoXiangNum;
	}

	/**
	 * @param shaoXiangNum
	 *            the shaoXiangNum to set
	 */
	public void setShaoXiangNum(int shaoXiangNum) {
		this.shaoXiangNum = shaoXiangNum;
	}

	/**
	 * @return the juHun
	 */
	public int getJuHun() {
		return juHun;
	}

	/**
	 * @param juHun
	 *            the juHun to set
	 */
	public void setJuHun(int juHun) {
		this.juHun = juHun;
	}

	/**
	 * @return the juHunNum
	 */
	public int getJuHunNum() {
		return juHunNum;
	}

	/**
	 * @param juHunNum
	 *            the juHunNum to set
	 */
	public void setJuHunNum(int juHunNum) {
		this.juHunNum = juHunNum;
	}

	/**
	 * @return the receiveJuhun
	 */
	public int getReceiveJuhun() {
		return receiveJuhun;
	}

	/**
	 * @param receiveJuhun
	 *            the receiveJuhun to set
	 */
	public void setReceiveJuhun(int receiveJuhun) {
		this.receiveJuhun = receiveJuhun;
	}

}
