package iyunu.NewTLOL.model.gang;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangDeleteTask;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Gang {

	private long id; // 编号
	private String name; // 名称
	private Date createDate; // 创建时间
	private int level; // 帮等级
	private int totalTribute; // 帮派帮贡（经验）
	private long leader; // 帮主
	private String leaderName; // 帮主名称
	private HashSet<Long> viceLeader = new HashSet<Long>(); // 副帮主
	private HashSet<Long> presbyter = new HashSet<Long>(); // 长老
	private ArrayList<RoleCard> members = new ArrayList<RoleCard>(); // 全体人员
	private HashSet<Long> membersString = new HashSet<Long>(); // 全体人员需转存，不然存在数据库里太大了
	private HashSet<Long> askGang = new HashSet<Long>(); // 申请入帮的人员
	private String srv; // 服务器
	private String gangBulletin; // 帮派公告
	private int active; // 帮派活跃值
	private MapGangInfo mapGangInfo; // 帮派地图
	private List<GangLogInfo> gangLog = new ArrayList<GangLogInfo>();
	private long invasionTime;

	public void sortMembers() {
		Collections.sort(members, comparator);
	}

	private final Comparator<RoleCard> comparator = new Comparator<RoleCard>() {
		@Override
		public int compare(RoleCard card1, RoleCard card2) {

			// 在线玩家排名靠前、职位高排名靠前、等级高排名靠前、经验多排名靠前
			if (ServerManager.instance().isOnline(card2.getId()) && !ServerManager.instance().isOnline(card1.getId())) {
				return 1;
			} else if (ServerManager.instance().isOnline(card1.getId()) && !ServerManager.instance().isOnline(card2.getId()))
				return -1;
			else {
				if (getJobTitle(card2).ordinal() < getJobTitle(card1).ordinal())
					return 1;
				else if (getJobTitle(card2).ordinal() > getJobTitle(card1).ordinal())
					return -1;

				else {
					if (card2.getLevel() > card1.getLevel())
						return 1;
					else if (card2.getLevel() < card1.getLevel())
						return -1;
					else {
						return 0;
					}
				}
				// else {
				// if (card2.getExp() > card1.getExp())
				// return 1;
				// else if (card2.getExp() < card1.getExp())
				// return -1;
				// else
				// return 0;
				// }
				// }
			}
		}
		// }
	};

	/** 获取成员职位 */
	public GangJobTitle getJobTitle(RoleCard card) {
		return getJobTitle(card.getId());
	}

	/** 获取成员职位 */
	public GangJobTitle getJobTitle(long roleid) {
		if (roleid == leader) {
			return GangJobTitle.Leader;
		}

		if (viceLeader.contains(roleid)) {
			return GangJobTitle.ViceLeader;
		}

		if (presbyter.contains(roleid)) {
			return GangJobTitle.Presbyter;
		}

		if (membersString.contains(roleid)) {
			return GangJobTitle.Member;
		}

		return GangJobTitle.NULL;
	}

	/**
	 * 添加副帮主
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void addViceLeader(long roleId) {
		this.getViceLeader().add(roleId);
	}

	/**
	 * 添加长老
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void addPresbyter(long roleId) {
		this.getPresbyter().add(roleId);
	}

	/**
	 * 添加成员
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void addMembers(RoleCard rc) {
		members.add(rc);
	}

	/**
	 * 添加申请加入帮派
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void addAskGang(long roleId) {
		askGang.add(roleId);
	}

	/**
	 * 删除一个成员
	 * 
	 * @param roleId
	 */
	public void del(long roleId) {
		viceLeader.remove(roleId);
		presbyter.remove(roleId);
		membersString.remove(roleId);

		for (int i = 0; i < members.size(); i++) {
			RoleCard rc = members.get(i);
			if (rc.getId() == roleId) {
				members.remove(i);
				break;
			}
		}
	}

	/**
	 * 增加帮派经验
	 * 
	 * @param tributeNum
	 *            要加的帮贡
	 */
	public void addTribute(int tributeNum, Role role) {
		// 加上总帮贡
		this.setTotalTribute(this.getTotalTribute() + tributeNum);
		GangMessage.refreshGangExp(role.getGang());
		// 尝试升级帮派
		GangServer.gangUpgrade(role.getGang());
	}

	/**
	 * 判断一个roleId是否在帮派中
	 * 
	 * */
	public boolean isInGang(long roleId) {
		return membersString.contains(roleId);
	}

	/**
	 * 解散帮派
	 */
	public void delGang() {
		try {

			for (RoleCard roleCard : this.getMembers()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleCard.getId());
				MailServer.send(roleCard.getId(), "帮派已解散！", "您所在的帮派《" + this.getName() + "》已解散！", null, 0, 0, 0, 0, null);
				if (role != null) {
					// 删除角色身上的帮信息
					role.setGangId(0);
					// role.setJobTitle(GangJobTitle.NULL);
					role.setTribute(0); // 清除个人帮贡
					role.setTotalTribute(0);// 清除个人总帮贡
					role.setGangActivity(0); // 清除个人帮派活跃值
					// role.setGangName("");
					role.setGang(null);
					// role.setLeaveGangTime(System.currentTimeMillis());
					// 刷新人物信息
					GangMessage.sendJoinGang(role);
					MapManager.instance().addGangStateQueue(role);
				} else {
					roleCard.setGangId(0);
					// roleCard.setJobTitle(GangJobTitle.NULL);
					roleCard.setTribute(0); // 清除个人帮贡
					roleCard.setTotalTribute(0);
					roleCard.setGangActivity(0); // 清除个人帮派活跃值
					// roleCard.setGangName("");
					roleCard.setGang(null);
					roleCard.setLeaveGangTime(System.currentTimeMillis());
				}
			}
			// 在map中删除此gang
			GangManager.instance().map.remove(this.getId());
			GangIOTask task = new GangDeleteTask(this);
			IOProcessManager.instance().addGangTask(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 帮派日志
	 * 
	 * @param name
	 * @param log
	 * @param type
	 */
	public void addLog(String name, String log, EGangLog type) {
		gangLog.add(new GangLogInfo(name, log, type));
		if (gangLog.size() > 20) {
			gangLog.remove(0);
		}
	}

	/**
	 * @param active
	 *            the active to add
	 */
	public void addActive(int active, String name, String roleName) {
		if (!(this.active + active > GangManager.MAXACTIVE)) {
			this.active = this.active + active;
		} else {
			this.active = GangManager.MAXACTIVE;
		}
		// this.addLog(StringControl.yel(roleName), name + "增加" +
		// StringControl.grn(active + "") + "点人气值", EGangLog.active);
		GangMessage.refreshGangActive(this);
	}

	/**
	 * 减少帮派活跃值
	 * 
	 * @param active
	 *            the active to set
	 */
	// public void minnusActive(int active, String name, String roleName) {
	// if (this.getActive() < 60) {
	// MailServer.send(this.getLeader(), "帮派告急通知", "您的帮派人气值过低，请注意！每日帮派活跃值减" +
	// active + "点，当帮派人气值小于0时，帮派将自动解散！", null, 0, 0, 0, 0, 0, null, "帮派");
	// }
	//
	// if (this.getActive() - active < 0 || this.getActive() < 0) {
	// // 解散帮派
	// // delGang(); 循环删除会导致线程停止， 新加入一个LIST中，集中删除
	// GangManager.instance().getDelGangList().add(this.getId());
	// } else {
	// this.active = this.getActive() - active;
	// }
	// this.addLog(StringControl.yel(roleName), name + "减少" +
	// StringControl.grn(active + "") + "点人气值", EGangLog.active);
	// LogManager.gang(0, "", this.id, 0, name, 0, active,
	// EGang.帮派活跃值.ordinal());
	// }

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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the leader
	 */
	public long getLeader() {
		return leader;
	}

	/**
	 * @param leader
	 *            the leader to set
	 */
	public void setLeader(long leader) {
		this.leader = leader;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName
	 *            the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the viceLeader
	 */
	public HashSet<Long> getViceLeader() {
		return viceLeader;
	}

	/**
	 * @param viceLeader
	 *            the viceLeader to set
	 */
	public void setViceLeader(HashSet<Long> viceLeader) {
		this.viceLeader = viceLeader;
	}

	/**
	 * @return the presbyter
	 */
	public HashSet<Long> getPresbyter() {
		return presbyter;
	}

	/**
	 * @param presbyter
	 *            the presbyter to set
	 */
	public void setPresbyter(HashSet<Long> presbyter) {
		this.presbyter = presbyter;
	}

	/**
	 * @return the members
	 */
	public ArrayList<RoleCard> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(ArrayList<RoleCard> members) {
		this.members = members;
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
	 * @return the size
	 */
	public int getSize() {
		return 20 + (level) * 5;
	}

	/**
	 * @return the gangBulletin
	 */
	public String getGangBulletin() {
		return gangBulletin;
	}

	/**
	 * @param gangBulletin
	 *            the gangBulletin to set
	 */
	public void setGangBulletin(String gangBulletin) {
		this.gangBulletin = gangBulletin;
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
	 * @return the membersString
	 */
	public HashSet<Long> getMembersString() {
		return membersString;
	}

	/**
	 * @param membersString
	 *            the membersString to set
	 */
	public void setMembersString(HashSet<Long> membersString) {
		this.membersString = membersString;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * @return the mapGangInfo
	 */
	public MapGangInfo getMapGangInfo() {
		return mapGangInfo;
	}

	/**
	 * @param mapGangInfo
	 *            the mapGangInfo to set
	 */
	public void setMapGangInfo(MapGangInfo mapGangInfo) {
		this.mapGangInfo = mapGangInfo;
	}

	/**
	 * @return the gangLog
	 */
	public List<GangLogInfo> getGangLog() {
		return gangLog;
	}

	/**
	 * @param gangLog
	 *            the gangLog to set
	 */
	public void setGangLog(List<GangLogInfo> gangLog) {
		this.gangLog = gangLog;
	}

	/**
	 * @return the invasionTime
	 */
	public long getInvasionTime() {
		return invasionTime;
	}

	/**
	 * @param invasionTime
	 *            the invasionTime to set
	 */
	public void setInvasionTime(long invasionTime) {
		this.invasionTime = invasionTime;
	}

}
