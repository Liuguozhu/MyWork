package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.raids.instance.RaidsTeamInfo;
import iyunu.NewTLOL.model.role.title.instance.Title;
import iyunu.NewTLOL.model.vip.Vip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 角色名片对象
 * 
 * @author SunHonglei
 * 
 */
public class RoleCard {

	private Role role;

	protected int serverId; // 服务器
	protected String srv; // 服务器名

	// 基本属性
	protected long id; // 编号
	protected String userId; // 帐户编号
	protected String nick; // 名称
	protected Vocation vocation; // 职业
	protected long figure; // 形象
	protected int level; // 等级
	protected int gold; // 金币（绑银）
	protected int coin; // 银两
	protected int exp; // 经验

	protected boolean isLogon; // 是否在线
	protected long cancelBan; // 解封日期
	protected long mute; // 禁言剩余时间

	// 帮派
	protected long gangId = 0;// 帮派id
	// protected String gangName = "";// 帮派名称
//	protected GangJobTitle jobTitle = GangJobTitle.NULL;// 帮派职位
	protected int tribute = 0; // 帮贡
	protected int totalTribute = 0;// 个人总帮贡，帮贡是可以消耗的，总帮贡是记录在本帮派的总贡献
	protected int gangActivity; // 帮派活跃值
	protected HashSet<Long> askGang = new HashSet<Long>();// 已申请加入的帮派
	protected int shaoXiangNum; // 烧香次数
	protected long leaveGangTime;// 上次退帮时间，包括被踢，帮派被解散，自己退出
	protected Gang gang; // 帮派信息

	/** 称号 **/
	protected ArrayList<Title> titles = new ArrayList<Title>(); // 称号列表
	protected Title title; // 当前称号

	/** 副本 **/
	protected RaidsTeamInfo raidsTeamInfo; // 副本团队

	/** 每日答题 **/
	protected int dailyAnswerScore; // 答题积分（DB存储）
	protected List<Integer> dailyAnswerQuestions = new ArrayList<Integer>(); // 题目列表
	protected int dailyAnswerState; // 答题状态（0为没答过，1为答过）
	protected int dailyAnswerContinuousTrueNum; // 连续答对题目数量
	protected int dailyAnswerTotleTrueNum; // 答对题目数量
	protected int dailyAnswerTotleGold; // 答题获得金钱

	/** vip **/
	protected Vip vip = new Vip();
	/** 大礼包 **/
	protected List<Integer> surprise = new ArrayList<Integer>();
	/** 战力 */
	protected long power;// 战力

	/** 历史千层塔层数 **/
	private int historyFloorId;

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

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
	 * @return the isLogon
	 */
	public boolean isLogon() {
		return isLogon;
	}

	/**
	 * @param isLogon
	 *            the isLogon to set
	 */
	public void setLogon(boolean isLogon) {
		this.isLogon = isLogon;
	}

	/**
	 * @return the cancelBan
	 */
	public long getCancelBan() {
		return cancelBan;
	}

	/**
	 * @param cancelBan
	 *            the cancelBan to set
	 */
	public void setCancelBan(long cancelBan) {
		this.cancelBan = cancelBan;
	}

	/**
	 * @return the mute
	 */
	public long getMute() {
		return mute;
	}

	/**
	 * @param mute
	 *            the mute to set
	 */
	public void setMute(long mute) {
		this.mute = mute;
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
	 * @return the gangName
	 */
	// public String getGangName() {
	// return gangName;
	// }

	/**
	 * @param gangName
	 *            the gangName to set
	 */
	// public void setGangName(String gangName) {
	// this.gangName = gangName;
	// }

	/**
	 * @return the jobTitle
	 */
//	public GangJobTitle getJobTitle() {
//		return jobTitle;
//	}

	/**
	 * @param jobTitle
	 *            the jobTitle to set
	 */
//	public void setJobTitle(GangJobTitle jobTitle) {
//		this.jobTitle = jobTitle;
//	}

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
	 * @return the askGang
	 */
	public HashSet<Long> getAskGang() {
		return askGang;
	}

	/**
	 * @param askGang
	 *            the askGang to set
	 */
	public void setAskGang(HashSet<Long> askGang) {
		this.askGang = askGang;
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
	 * @return the titles
	 */
	public ArrayList<Title> getTitles() {
		return titles;
	}

	/**
	 * @param titles
	 *            the titles to set
	 */
	public void setTitles(ArrayList<Title> titles) {
		this.titles = titles;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return the dailyAnswerQquestions
	 */
	public List<Integer> getDailyAnswerQuestions() {
		return dailyAnswerQuestions;
	}

	/**
	 * @param dailyAnswerQquestions
	 *            the dailyAnswerQquestions to set
	 */
	public void setDailyAnswerQuestions(List<Integer> dailyAnswerQuestions) {
		this.dailyAnswerQuestions = dailyAnswerQuestions;
	}

	/**
	 * @return the dailyAnswerState
	 */
	public int getDailyAnswerState() {
		return dailyAnswerState;
	}

	/**
	 * @param dailyAnswerState
	 *            the dailyAnswerState to set
	 */
	public void setDailyAnswerState(int dailyAnswerState) {
		this.dailyAnswerState = dailyAnswerState;
	}

	/**
	 * @return the dailyAnswerContinuousTrueNum
	 */
	public int getDailyAnswerContinuousTrueNum() {
		return dailyAnswerContinuousTrueNum;
	}

	/**
	 * @param dailyAnswerContinuousTrueNum
	 *            the dailyAnswerContinuousTrueNum to set
	 */
	public void setDailyAnswerContinuousTrueNum(int dailyAnswerContinuousTrueNum) {
		this.dailyAnswerContinuousTrueNum = dailyAnswerContinuousTrueNum;
	}

	/**
	 * @return the dailyAnswerTotleTrueNum
	 */
	public int getDailyAnswerTotleTrueNum() {
		return dailyAnswerTotleTrueNum;
	}

	/**
	 * @param dailyAnswerTotleTrueNum
	 *            the dailyAnswerTotleTrueNum to set
	 */
	public void setDailyAnswerTotleTrueNum(int dailyAnswerTotleTrueNum) {
		this.dailyAnswerTotleTrueNum = dailyAnswerTotleTrueNum;
	}

	/**
	 * @return the dailyAnswerScore
	 */
	public int getDailyAnswerScore() {
		return dailyAnswerScore;
	}

	/**
	 * @param dailyAnswerScore
	 *            the dailyAnswerScore to set
	 */
	public void setDailyAnswerScore(int dailyAnswerScore) {
		this.dailyAnswerScore = dailyAnswerScore;
	}

	/**
	 * @return the dailyAnswerTotleGold
	 */
	public int getDailyAnswerTotleGold() {
		return dailyAnswerTotleGold;
	}

	/**
	 * @param dailyAnswerTotleGold
	 *            the dailyAnswerTotleGold to set
	 */
	public void setDailyAnswerTotleGold(int dailyAnswerTotleGold) {
		this.dailyAnswerTotleGold = dailyAnswerTotleGold;
	}

	// /**
	// * @return the battleCloaking
	// */
	// public long getBattleCloaking() {
	// return battleCloaking;
	// }
	//
	// /**
	// * @param battleCloaking
	// * the battleCloaking to set
	// */
	// public void setBattleCloaking(long battleCloaking) {
	// this.battleCloaking = battleCloaking;
	// }

	/**
	 * @return the vip
	 */
	public Vip getVip() {
		return vip;
	}

	/**
	 * @param vip
	 *            the vip to set
	 */
	public void setVip(Vip vip) {
		this.vip = vip;
	}

	/**
	 * @return the leaveGangTime
	 */
	public long getLeaveGangTime() {
		return leaveGangTime;
	}

	/**
	 * @param leaveGangTime
	 *            the leaveGangTime to set
	 */
	public void setLeaveGangTime(long leaveGangTime) {
		this.leaveGangTime = leaveGangTime;
	}

	/**
	 * @return the surprise
	 */
	public List<Integer> getSurprise() {
		return surprise;
	}

	/**
	 * @param surprise
	 *            the surprise to set
	 */
	public void setSurprise(List<Integer> surprise) {
		this.surprise = surprise;
	}

	/**
	 * @return the raidsTeamInfo
	 */
	public RaidsTeamInfo getRaidsTeamInfo() {
		return raidsTeamInfo;
	}

	/**
	 * @param raidsTeamInfo
	 *            the raidsTeamInfo to set
	 */
	public void setRaidsTeamInfo(RaidsTeamInfo raidsTeamInfo) {
		this.raidsTeamInfo = raidsTeamInfo;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	/**
	 * @return the gang
	 */
	public Gang getGang() {
		return gang;
	}

	/**
	 * @param gang
	 *            the gang to set
	 */
	public void setGang(Gang gang) {
		this.gang = gang;
	}

	public int getHistoryFloorId() {
		return historyFloorId;
	}

	public void setHistoryFloorId(int historyFloorId) {
		this.historyFloorId = historyFloorId;
	}

	/**
	 * @return the coin
	 */
	public int getCoin() {
		return coin;
	}

	/**
	 * @param coin
	 *            the coin to set
	 */
	public void setCoin(int coin) {
		this.coin = coin;
	}

	/**
	 * @return the gangActivity
	 */
	public int getGangActivity() {
		return gangActivity;
	}

	/**
	 * @param gangActivity
	 *            the gangActivity to set
	 */
	public void setGangActivity(int gangActivity) {
		this.gangActivity = gangActivity;
	}

	/**
	 * @return the totalTribute
	 */
	public int getTotalTribute() {
		return totalTribute;
	}

	/**
	 * @param totalTribute
	 *            the totalTribute to set
	 */
	public void setTotalTribute(int totalTribute) {
		this.totalTribute = totalTribute;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
