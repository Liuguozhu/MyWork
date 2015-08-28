package iyunu.NewTLOL.model.role;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.bag.Bag;
import iyunu.NewTLOL.model.bag.BagStone;
import iyunu.NewTLOL.model.bag.Warehouse;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.daily.DailyModelRole;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.res.FigureRes;
import iyunu.NewTLOL.model.room.Room;
import iyunu.NewTLOL.model.seven.Seven;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.model.trials.Trials;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.channel.Channel;

/**
 * @author SunHonglei
 * 
 */
public class Role extends RoleCard implements ICharacter {

	@Override
	public byte getType() {
		return 0;
	}

	private boolean isPrBattle = false;
	private boolean isBattle = false;
	private long battleId; // 战斗编号

	private Channel channel; // 连接状态
	private long updateTime; // 自动更新时间
	private int change = 1; // 是否系统默认帐号(0.是，1.否)
	private boolean isAround = false; // 下发周围玩家
	private long logonTime; // 上线时间
	private long logoutTime; // 下线时间
	private int compensate; // 补偿版本号
	private ERoleFightState fightState = ERoleFightState.none; // 战斗状态
	private int costGold; // 消耗绑银数量
	private int eliteMonsterNum; // 击杀精英怪物数量
	private int eliteMonster; // 精英怪数量
	private boolean isEliteMonster = false; // 是否已刷新精英怪

	private boolean closeTeam = false; // 队伍开关
	private boolean closeFriend = false; // 还有开关
	private boolean closeWhisper = false; // 私聊开关

	private int liveNum = 0; // 心跳次数
	private long liveTime = 0; // 上次心跳时间
	private Room room; // 房间

	// 基本属性
	// private String userId; // 帐户编号
	private Date date; // 创建时间
	private int money; // 元宝
	private int state; // 状态(0.活跃，1.删除)

	// 登录记录
	private int logonTotals;// 总登录次数
	private int logonDays;// 总登录天数
	private int logonContinue;// 连续登录天数

	// 一级属性
	private int strength; // 力量
	private int intellect; // 智力
	private int physique; // 体质
	private int agility; // 敏捷
	private int free; // 自由点数
	private int hp; // 生命值
	private int mp; // 内力值

	// 原始属性
	private Property propertyJingMai = new Property(); // 经脉属性
	private Property propertyOriginal = new Property(); // 原始属性
	private Property propertyEquip = new Property(); // 装备属性
	private Property propertyIntensify = new Property(); // 强化属性
	private Property propertyStone = new Property(); // 配件属性

	// 二级属性
	private int expMax; // 升级所需经验
	private int hpMax; // 生命值上限
	private int mpMax; // 内力值上限
	private int pattack; // 外功攻击
	private int pdefence; // 外功防御
	private int mattack; // 内功攻击
	private int mdefence; // 内功防御
	private int showStrength; // 力量
	private int showIntellect; // 智力
	private int showPhysique; // 体质
	private int showAgility; // 敏捷
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡
	private int speed; // 速度

	private FigureRes figureRes = null; // 形象数据

	/** CD-KEY **/
	private List<Integer> cdKeys = new ArrayList<>();

	/** 背包、仓库 **/
	private Bag bag; // 背包
	private BagStone bagStone; // 宝石背包
	private Warehouse warehouse; // 仓库
	private int warehouseCoin = 0;// 仓库存银两
	/** 技能 **/
	private int freeSkill; // 剩余技能点数
	private int sumSkill; // 总技能点数
	private Map<Integer, Integer> skillMap = new HashMap<Integer, Integer>();// Integer为技能位置，Integer为技能编号

	/** 猎技 **/
	private int huntskillState; // 0，在猎技；1，没有猎技
	private int huntskillScore; // 猎技积分
	// private int huntskillNum;// 猎技次数

	/** 地图 **/
	private MapInfo mapInfo; // 地图信息

	/** 任务 **/
	private Map<Integer, BaseTask> tasks = new HashMap<Integer, BaseTask>(); // 任务列表<任务编号，任务对象>
	private List<Integer> finishTasks = new ArrayList<Integer>(); // 已完成任务列表
	private int guildTaskNum; // 门派任务数量
	private int guildTaskSum; // 门派任务总数量
	private BaseTask guildTask; // 门派任务
	private int ghostTaskNum; // 江湖追杀令任务数量
	private int ghostTaskSum; // 江湖追杀令任务总数量
	private BaseTask ghostTask; // 江湖追杀令
	private BaseTask yingxiongtie; // 英雄帖
	private int yxtTaskNum; // 英雄帖任务数量
	private int taskCycleRow; // 已接环任务轮数
	private int taskCycleNum; // 当前环任务数
	private BaseTask taskCycle; // 环任务

	/** 试炼 **/
	private int trialsState; // 试炼状态
	private HashMap<Integer, Trials> trials = new HashMap<Integer, Trials>(); // <试练编号，试练信息>
	private HashMap<Integer, Integer> resetTrials = new HashMap<Integer, Integer>(); // <试炼状态，使用试练重置次数>

	// =======================================伙伴=========================================
	/** 伙伴 **/
	private long innTime = -1; // 客栈刷新时间1
	private long innTime2 = -1; // 客栈刷新时间2
	private ArrayList<Partner> partners = new ArrayList<Partner>(); // 拥有的伙伴
	// private Partner partner; // 出战伙伴
	private int firstPartner = 0;// 0 没抽过第一排卡 1 抽过卡
	private int secPartner = 0; // 0没抽过第二排卡 1 抽过第二排卡

	private HashMap<Long, Partner> partnerMap = new HashMap<>(); // 伙伴集合<伙伴编号，伙伴对象>
	private HashMap<Integer, Long> partnerFight = new HashMap<>(); // 出战伙伴集合<位置，伙伴编号>

	/** 武魂 **/
	protected int juHunNum = 10; // 重置聚魂次数
	protected int receiveJuhun = 10; // 收获聚魂次数

	/** 拍卖行 **/
	private List<Auction> auctions = new ArrayList<Auction>(4); // 我的拍卖
	private int auctionGold;

	/** 武魂 **/
	private int wuHun; // 角色拥有武魂数

	/*** 组队 */
	private Team team; // 队伍信息
	private Set<Integer> inviteList = new HashSet<Integer>(); // 邀请列表

	/** 邮件 **/
	private List<Mail> mails = new ArrayList<Mail>();

	/** 帮派 **/
	private Date quitGangTime;// 退帮时间
	private int shaoXiangLevel;// 烧香等级
	private int shaoXiangExp;// 烧香经验
	private int activityType; // 帮派活跃值使用状态(0未使用，1已使用)

	/** 交互 **/
	private List<Long> friendList = new ArrayList<Long>(); // 好友
	// private List<Long> blackList = new ArrayList<Long>(); // 黑名单
	private List<Long> applyFriendList = new ArrayList<Long>(); // 申请好友列表

	/** 装备 **/
	private HashMap<EEquip, Equip> equipments = new HashMap<EEquip, Equip>(); // 装备列表（EEquip是部位，Equip是装备）

	/** 强化 **/
	private HashMap<EEquip, Integer> bodyIntensify = new HashMap<EEquip, Integer>(); // 强化（EEquip是部位，Integer是强化等级）
	private HashMap<EEquip, HashMap<Integer, Rabbet>> bodyRabbet = new HashMap<EEquip, HashMap<Integer, Rabbet>>(); // 宝石（EEquip是部位，第一个Integer是部位的配件位置，Rabbet是插槽对象）

	/** 奇经八脉 **/
	private int yuanQi; // 元气值
	private int jingMaiId; // 经脉编号

	/** 副本 **/
	private Map<Integer, Integer> raidsNumber = new HashMap<Integer, Integer>(); // 进入副本次数（副本索引，使用次数）

	/** 招财 **/
	private long buyGoldTime; // 下次招财时间
	private int useBuyGoldNum; // 使用招财次数
	private int freeBuyGoldNum; // 免费招财剩余次数
	private int moneyBuyGoldNum; // 元宝招财剩余次数

	/*** BUFF */
	private Map<EBuffType, BuffRole> buffs = new HashMap<EBuffType, BuffRole>();

	/** 签到 */
	private List<Integer> signList = new ArrayList<Integer>();// 签到列表
	private int haveSign = 0;// 今天是否已签到 0已签到 1 未签到
	private List<Integer> havePickSign = new ArrayList<Integer>();// 已领取的签到礼包
	private int signDay = 1;// 签到应该签第几天,没有说明第一次登录，默认为1天
	private long firstSign;// 第一天签到的日期

	/** 连续登录 */
	private int con = 1;// 连续登录
	private List<Integer> conPick = new ArrayList<Integer>();// 已领取的签到礼包

	/** 等级礼包状态 **/
	private Map<Integer, Integer> levelGiftStateMap = new HashMap<Integer, Integer>(); // (第一个Integer为等级，第二个为领取状态(0为不可领取，1为可领取，2为已领取))

	/** 平台标识（默认LZJH） **/
	private String platform = "WXXO";

	/** 聊天内容跟重复次数 **/
	private String content; // 上次聊天内容
	private int contentNum; // 相同内容聊天次数

	/** 小助手信息 **/
	public Map<EHelper, Integer> helper = new HashMap<EHelper, Integer>(); // 小助手<类型，次数>
	/** 活跃度 **/
	private int livenessScore;
	/** 活跃度礼包状态 **/
	private LinkedHashMap<Integer, Integer> livenessScoreMap = new LinkedHashMap<Integer, Integer>(); // (第一个Integer为活跃度，第二个为领取状态(0为不可领取，1为可领取，2为已领取))

	/** 在线奖励 **/
	private int onlineTime; // 每日在线时长
	private Map<Integer, Integer> onlineAwardStateMap = new HashMap<Integer, Integer>(); // (第一个Integer为编号，第二个为领取状态(0为不可领取，1为可领取，2为已领取))

	/** 新手引导 **/
	private int guide; // 新手引导
	private int specialGuide; // 特殊新手引导
	/** 寻宝 **/
	// private int huntTreasureNum; // 剩余寻宝次数
	/** 血战标识 */
	private int blood;// 0 无，1 红 ，2 蓝
	/** 血战积分 */
	private int bmark;
	/** 血战被杀时间 */
	private long bloodKillTime = 0;
	/** 开服七天 */
	private Map<Integer, Seven> sevenMap = new HashMap<Integer, Seven>(); // (第一个Integer为编号，第二个为领取状态(0为不可领取，1为可领取，2为已领取))

	/** VIP奖励 */
	private int vipGift = 0;

	/** 当前千层塔层数 **/
	private int currentFloorId;
	/** 免费重置次数 **/
	private int nullResetNum;
	/** 元宝重置使用次数 **/
	private int moneyResetNum;

	/** 聊天发送Vip开关 **/
	private int openVipChat;

	/** 帮派入侵击杀怪物数量 **/
	private int killMonsterNum;

	/** 每日答题次数 **/
	private int dailyAnswerNum;

	// ================================发布令=================================
	/** 剩余发布令次数 **/
	private int taskFblNum;
	private long taskFblId;
	private ActivityFblInfo fabuling; // 发布令

	// ================================发布令=================================
	/** 可提升提示的存信息 */
	private Map<Integer, Boolean> upTipsInit = new HashMap<>();

	// ----------------------累计充值-------------
	private int plusMoney = 0;// 累计充值金额
	private List<Integer> recPlusMoney = new ArrayList<>();// 领取到哪个充值礼包

	// ----------------------累计充值-------------

	// ==============8个首次充值双倍========================
	private List<Integer> firstDouble = new ArrayList<>();

	// ==============8个首次充值双倍========================

	// =======================日常任务============================
	private Map<Integer, DailyModelRole> dailyMap = new HashMap<>();

	// ========================日常任务===========================

	// ========================活力===========================
	/** 活力值 **/
	private int energy;
	/** 活力上限 **/
	private int energyMax;
	/** 上次刷新活力时间 **/
	private long energyTime;

	// ========================活力===========================
	private int emoCount;// 恶魔岛次数

	/**
	 * 抢矿Copy方法
	 * 
	 * @return 临时角色对象
	 */
	public Role copyMinning() {

		Role role = new Role();
		role.setId(id);
		role.setNick(nick);
		role.setFigure(figure);
		role.setVocation(vocation);
		// role.setPartner(partner); // 伙伴
		role.setPartnerFight(partnerFight);
		role.setSkillMap(skillMap); // 技能
		role.setEquipments(equipments); // 装备
		role.setBuffs(buffs); // BUFF

		role.setHp(hp);
		role.setMp(mp);
		role.setHpMax(hpMax); // 生命值上限
		role.setMpMax(mpMax); // 内力值上限
		role.setPattack(pattack); // 外功攻击
		role.setPdefence(pdefence); // 外功防御
		role.setMattack(mattack); // 内功攻击
		role.setMdefence(mdefence); // 内功防御
		role.setShowStrength(showStrength); // 力量
		role.setShowIntellect(showIntellect); // 智力
		role.setShowPhysique(showPhysique); // 体质
		role.setShowAgility(showAgility); // 敏捷
		role.setHit(hit); // 命中
		role.setDodge(dodge); // 闪避
		role.setCrit(crit); // 暴击
		role.setParry(parry); // 格挡
		role.setSpeed(speed); // 速度

		return role;

	}

	/**
	 * 玩家下线
	 */
	public void offline() {
		isLogon = false;
	}

	/**
	 * 刷新地图
	 * 
	 * @param mapId
	 *            地图编号
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void freshCoords(final int mapId, final int x, int y, final int arriveX, final int arriveY) {
		this.getMapInfo().setMapId(mapId);
		this.freshCoords(x, y, arriveX, arriveY);
	}

	/**
	 * 刷新坐标
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void freshCoords(final int x, int y, final int arriveX, final int arriveY) {
		this.getMapInfo().setX(x);
		this.getMapInfo().setY(y);
		this.getMapInfo().setArriveX(arriveX);
		this.getMapInfo().setArriveY(arriveY);
	}

	/**
	 * 取得物品
	 * 
	 * */
	public Item getItem(int cellId) {
		return this.getBag().getCells()[cellId].getItem();
	}

	/**
	 * 获取伙伴
	 * 
	 * @param partnerId
	 *            伙伴编号
	 * @return 伙伴对象
	 */
	public Partner findPartner(long partnerId) {
		if (partnerMap.containsKey(partnerId)) {
			return partnerMap.get(partnerId);
		}
		return null;
	}

	public boolean checkPartner(long partnerId) {
		return partnerMap.containsKey(partnerId);
	}

	/**
	 * role转换为roleCard
	 * */
	public RoleCard toCard() {
		RoleCard roleCard = RoleManager.getRoleCardByRoleId(this.getId());
		if (roleCard == null) {
			roleCard = newCard();
			RoleManager.saveRoleCard(roleCard);
		} else {
			RoleServer.roleToRoleCard(this, roleCard);
		}

		return roleCard;
	}

	public RoleCard toCardPower() {
		RoleCard roleCard = RoleManager.getRoleCardByRoleId(this.getId());
		if (roleCard == null) {
			roleCard = newCard();
			RoleManager.saveRoleCard(roleCard);
		} else {
			roleCard.setPower(this.getPower());
		}

		return roleCard;
	}

	/**
	 * role转换为roleCard
	 * */
	public RoleCard newCard() {
		RoleCard roleCard = new RoleCard();
		RoleServer.roleToRoleCard(this, roleCard);
		return roleCard;
	}

	/** 返回提升提示 */
	public boolean getUptipBoolean(int ordinal) {
		if (upTipsInit.get(ordinal) == null) {
			upTipsInit.put(ordinal, false);
			return false;
		}
		boolean b = upTipsInit.get(ordinal);
		return b;
	}

	@Override
	public int worth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * @return the expMax
	 */
	public int getExpMax() {
		return expMax;
	}

	/**
	 * @param expMax
	 *            the expMax to set
	 */
	public void setExpMax(int expMax) {
		this.expMax = expMax;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength
	 *            the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * @return the intellect
	 */
	public int getIntellect() {
		return intellect;
	}

	/**
	 * @param intellect
	 *            the intellect to set
	 */
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	/**
	 * @return the physique
	 */
	public int getPhysique() {
		return physique;
	}

	/**
	 * @param physique
	 *            the physique to set
	 */
	public void setPhysique(int physique) {
		this.physique = physique;
	}

	/**
	 * @return the agility
	 */
	public int getAgility() {
		return agility;
	}

	/**
	 * @param agility
	 *            the agility to set
	 */
	public void setAgility(int agility) {
		this.agility = agility;
	}

	/**
	 * @return the free
	 */
	public int getFree() {
		return free;
	}

	/**
	 * @param free
	 *            the free to set
	 */
	public void setFree(int free) {
		this.free = free;
	}

	@Override
	public int getHp() {
		return hp;
	}

	@Override
	public void setHp(int hp) {
		this.hp = hp;
	}

	@Override
	public int getMp() {
		return mp;
	}

	@Override
	public void setMp(int mp) {
		this.mp = mp;
	}

	@Override
	public int getHpMax() {
		return hpMax;
	}

	@Override
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	@Override
	public int getMpMax() {
		return mpMax;
	}

	@Override
	public void setMpMax(int mpMax) {
		this.mpMax = mpMax;
	}

	@Override
	public int getHit() {
		return hit;
	}

	@Override
	public void setHit(int hit) {
		this.hit = hit;
	}

	@Override
	public int getDodge() {
		return dodge;
	}

	@Override
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	@Override
	public int getCrit() {
		return crit;
	}

	@Override
	public void setCrit(int crit) {
		this.crit = crit;
	}

	@Override
	public int getParry() {
		return parry;
	}

	@Override
	public void setParry(int parry) {
		this.parry = parry;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the innTime1
	 */
	public long getInnTime() {
		return innTime;
	}

	/**
	 * @param innTime1
	 *            the innTime1 to set
	 */
	public void setInnTime(long innTime) {
		this.innTime = innTime;
	}

	/**
	 * @return the innTime2
	 */
	public long getInnTime2() {
		return innTime2;
	}

	/**
	 * @param innTime2
	 *            the innTime2 to set
	 */
	public void setInnTime2(long innTime2) {
		this.innTime2 = innTime2;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the partners
	 */
	// public List<Partner> getPartners() {
	// return partners;
	// }

	/**
	 * @return the auction
	 */
	public List<Auction> getAuctions() {
		return auctions;
	}

	/**
	 * @return the auctionGold
	 */
	public int getAuctionGold() {
		return auctionGold;
	}

	/**
	 * @param auctionGold
	 *            the auctionGold to set
	 */
	public void setAuctionGold(int auctionGold) {
		this.auctionGold = auctionGold;
	}

	/**
	 * @return the partner
	 */
	// public Partner getPartner() {
	// return partner;
	// }

	/**
	 * @param partner
	 *            the partner to set
	 */
	// public void setPartner(Partner partner) {
	// this.partner = partner;
	// }

	/**
	 * @return the updateTime
	 */
	public long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the wuHun
	 */
	public int getWuHun() {
		return wuHun;
	}

	/**
	 * @param wuHun
	 *            the wuHun to set
	 */
	public void setWuHun(int wuHun) {
		this.wuHun = wuHun;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	/**
	 * @return the bag
	 */
	public Bag getBag() {
		return bag;
	}

	/**
	 * @param bag
	 *            the bag to set
	 */
	public void setBag(Bag bag) {
		this.bag = bag;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	/**
	 * @return the change
	 */
	public int getChange() {
		return change;
	}

	/**
	 * @param change
	 *            the change to set
	 */
	public void setChange(int change) {
		this.change = change;
	}

	/**
	 * @return the mails
	 */
	public List<Mail> getMails() {
		return mails;
	}

	/**
	 * @return the huntskillState
	 */
	public int getHuntskillState() {
		return huntskillState;
	}

	/**
	 * @param huntskillState
	 *            the huntskillState to set
	 */
	public void setHuntskillState(int huntskillState) {
		this.huntskillState = huntskillState;
	}

	/**
	 * @return the huntskillScore
	 */
	public int getHuntskillScore() {
		return huntskillScore;
	}

	/**
	 * @param huntskillScore
	 *            the huntskillScore to set
	 */
	public void setHuntskillScore(int huntskillScore) {
		this.huntskillScore = huntskillScore;
	}

	/**
	 * @return the friendList
	 */
	public List<Long> getFriendList() {
		return friendList;
	}

	public List<Long> getApplyFriendList() {
		return applyFriendList;
	}

	public void setFriendList(List<Long> friendList) {
		this.friendList = friendList;
	}

	public void setApplyFriendList(List<Long> applyFriendList) {
		this.applyFriendList = applyFriendList;
	}

	/**
	 * @return the equipments
	 */
	public HashMap<EEquip, Equip> getEquipments() {
		return equipments;
	}

	/**
	 * @param equipments
	 *            the equipments to set
	 */
	public void setEquipments(HashMap<EEquip, Equip> equipments) {
		this.equipments = equipments;
	}

	/**
	 * @return the pattack
	 */
	public int getPattack() {
		return pattack;
	}

	/**
	 * @param pattack
	 *            the pattack to set
	 */
	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	/**
	 * @return the pdefence
	 */
	public int getPdefence() {
		return pdefence;
	}

	/**
	 * @param pdefence
	 *            the pdefence to set
	 */
	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	/**
	 * @return the mattack
	 */
	public int getMattack() {
		return mattack;
	}

	/**
	 * @param mattack
	 *            the mattack to set
	 */
	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	/**
	 * @return the mdefence
	 */
	public int getMdefence() {
		return mdefence;
	}

	/**
	 * @param mdefence
	 *            the mdefence to set
	 */
	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	/**
	 * @return the bodyIntensify
	 */
	public HashMap<EEquip, Integer> getBodyIntensify() {
		return bodyIntensify;
	}

	/**
	 * @param bodyIntensify
	 *            the bodyIntensify to set
	 */
	public void setBodyIntensify(HashMap<EEquip, Integer> bodyIntensify) {
		this.bodyIntensify = bodyIntensify;
	}

	/**
	 * @return the freeSkill
	 */
	public int getFreeSkill() {
		return freeSkill;
	}

	/**
	 * @param freeSkill
	 *            the freeSkill to set
	 */
	public void setFreeSkill(int freeSkill) {
		this.freeSkill = freeSkill;
	}

	/**
	 * @return the sumSkill
	 */
	public int getSumSkill() {
		return sumSkill;
	}

	/**
	 * @param sumSkill
	 *            the sumSkill to set
	 */
	public void setSumSkill(int sumSkill) {
		this.sumSkill = sumSkill;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @param partners
	 *            the partners to set
	 */
	// public void setPartners(ArrayList<Partner> partners) {
	// this.partners = partners;
	// }

	public Map<Integer, Integer> getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(Map<Integer, Integer> skillMap) {
		this.skillMap = skillMap;
	}

	public int getYuanQi() {
		return yuanQi;
	}

	public void setYuanQi(int yuanQi) {
		this.yuanQi = yuanQi;
	}

	public int getJingMaiId() {
		return jingMaiId;
	}

	public void setJingMaiId(int jingMaiId) {
		this.jingMaiId = jingMaiId;
	}

	/**
	 * @return the quitGangTime
	 */
	public Date getQuitGangTime() {
		return quitGangTime;
	}

	/**
	 * @param quitGangTime
	 *            the quitGangTime to set
	 */
	public void setQuitGangTime(Date quitGangTime) {
		this.quitGangTime = quitGangTime;
	}

	/**
	 * @return the raidsNumber
	 */
	public Map<Integer, Integer> getRaidsNumber() {
		return raidsNumber;
	}

	/**
	 * @param raidsNumber
	 *            the raidsNumber to set
	 */
	public void setRaidsNumber(Map<Integer, Integer> raidsNumber) {
		this.raidsNumber = raidsNumber;
	}

	/**
	 * @return the isAround
	 */
	public boolean isAround() {
		return isAround;
	}

	/**
	 * @param isAround
	 *            the isAround to set
	 */
	public void setAround(boolean isAround) {
		this.isAround = isAround;
	}

	/**
	 * @return the tasks
	 */
	public Map<Integer, BaseTask> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(Map<Integer, BaseTask> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the finishTasks
	 */
	public List<Integer> getFinishTasks() {
		return finishTasks;
	}

	/**
	 * @param finishTasks
	 *            the finishTasks to set
	 */
	public void setFinishTasks(List<Integer> finishTasks) {
		this.finishTasks = finishTasks;
	}

	/**
	 * @return the logonTime
	 */
	public long getLogonTime() {
		return logonTime;
	}

	/**
	 * @param logonTime
	 *            the logonTime to set
	 */
	public void setLogonTime(long logonTime) {
		this.logonTime = logonTime;
	}

	/**
	 * @return the logoutTime
	 */
	public long getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @param logoutTime
	 *            the logoutTime to set
	 */
	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * @return the inviteList
	 */
	public Set<Integer> getInviteList() {
		return inviteList;
	}

	/**
	 * @param inviteList
	 *            the inviteList to set
	 */
	public void setInviteList(Set<Integer> inviteList) {
		this.inviteList = inviteList;
	}

	/**
	 * @return the propertyJingMai
	 */
	public Property getPropertyJingMai() {
		return propertyJingMai;
	}

	/**
	 * @param propertyJingMai
	 *            the propertyJingMai to set
	 */
	public void setPropertyJingMai(Property propertyJingMai) {
		this.propertyJingMai = propertyJingMai;
	}

	/**
	 * @return the propertyOriginal
	 */
	public Property getPropertyOriginal() {
		return propertyOriginal;
	}

	/**
	 * @param propertyOriginal
	 *            the propertyOriginal to set
	 */
	public void setPropertyOriginal(Property propertyOriginal) {
		this.propertyOriginal = propertyOriginal;
	}

	/**
	 * @return the propertyIntensify
	 */
	public Property getPropertyIntensify() {
		return propertyIntensify;
	}

	/**
	 * @param propertyIntensify
	 *            the propertyIntensify to set
	 */
	public void setPropertyIntensify(Property propertyIntensify) {
		this.propertyIntensify = propertyIntensify;
	}

	/**
	 * @return the propertyStone
	 */
	public Property getPropertyStone() {
		return propertyStone;
	}

	/**
	 * @param propertyStone
	 *            the propertyStone to set
	 */
	public void setPropertyStone(Property propertyStone) {
		this.propertyStone = propertyStone;
	}

	public long getBuyGoldTime() {
		return buyGoldTime;
	}

	public void setBuyGoldTime(long buyGoldTime) {
		this.buyGoldTime = buyGoldTime;
	}

	public int getUseBuyGoldNum() {
		return useBuyGoldNum;
	}

	public void setUseBuyGoldNum(int useBuyGoldNum) {
		this.useBuyGoldNum = useBuyGoldNum;
	}

	public int getFreeBuyGoldNum() {
		return freeBuyGoldNum;
	}

	public void setFreeBuyGoldNum(int freeBuyGoldNum) {
		this.freeBuyGoldNum = freeBuyGoldNum;
	}

	public int getMoneyBuyGoldNum() {
		return moneyBuyGoldNum;
	}

	public void setMoneyBuyGoldNum(int moneyBuyGoldNum) {
		this.moneyBuyGoldNum = moneyBuyGoldNum;
	}

	/**
	 * @return the propertyEquip
	 */
	public Property getPropertyEquip() {
		return propertyEquip;
	}

	/**
	 * @param propertyEquip
	 *            the propertyEquip to set
	 */
	public void setPropertyEquip(Property propertyEquip) {
		this.propertyEquip = propertyEquip;
	}

	/**
	 * @return the items
	 */
	// public Map<Integer, CollectionTask> getItems() {
	// return items;
	// }

	/**
	 * @return the bodyRabbet
	 */
	public HashMap<EEquip, HashMap<Integer, Rabbet>> getBodyRabbet() {
		return bodyRabbet;
	}

	/**
	 * @param bodyRabbet
	 *            the bodyRabbet to set
	 */
	public void setBodyRabbet(HashMap<EEquip, HashMap<Integer, Rabbet>> bodyRabbet) {
		this.bodyRabbet = bodyRabbet;
	}

	/**
	 * @return the logonTotals
	 */
	public int getLogonTotals() {
		return logonTotals;
	}

	/**
	 * @param logonTotals
	 *            the logonTotals to set
	 */
	public void setLogonTotals(int logonTotals) {
		this.logonTotals = logonTotals;
	}

	/**
	 * @return the logonDays
	 */
	public int getLogonDays() {
		return logonDays;
	}

	/**
	 * @param logonDays
	 *            the logonDays to set
	 */
	public void setLogonDays(int logonDays) {
		this.logonDays = logonDays;
	}

	/**
	 * @return the logonContinue
	 */
	public int getLogonContinue() {
		return logonContinue;
	}

	/**
	 * @param logonContinue
	 *            the logonContinue to set
	 */
	public void setLogonContinue(int logonContinue) {
		this.logonContinue = logonContinue;
	}

	/**
	 * @return the mapInfo
	 */
	public MapInfo getMapInfo() {
		return mapInfo;
	}

	/**
	 * @param mapInfo
	 *            the mapInfo to set
	 */
	public void setMapInfo(MapInfo mapInfo) {
		this.mapInfo = mapInfo;
	}

	/**
	 * @return the shaoXiangLevel
	 */
	public int getShaoXiangLevel() {
		return shaoXiangLevel;
	}

	/**
	 * @param shaoXiangLevel
	 *            the shaoXiangLevel to set
	 */
	public void setShaoXiangLevel(int shaoXiangLevel) {
		this.shaoXiangLevel = shaoXiangLevel;
	}

	/**
	 * @return the shaoXiangExp
	 */
	public int getShaoXiangExp() {
		return shaoXiangExp;
	}

	/**
	 * @param shaoXiangExp
	 *            the shaoXiangExp to set
	 */
	public void setShaoXiangExp(int shaoXiangExp) {
		this.shaoXiangExp = shaoXiangExp;
	}

	/**
	 * @return the signList
	 */
	public List<Integer> getSignList() {
		return signList;
	}

	/**
	 * @param signList
	 *            the signList to set
	 */
	public void setSignList(List<Integer> signList) {
		this.signList = signList;
	}

	/**
	 * @return the haveSign
	 */
	public int getHaveSign() {
		return haveSign;
	}

	/**
	 * @param haveSign
	 *            the haveSign to set
	 */
	public void setHaveSign(int haveSign) {
		this.haveSign = haveSign;
	}

	/**
	 * @return the signDay
	 */
	public int getSignDay() {
		return signDay;
	}

	/**
	 * @param signDay
	 *            the signDay to set
	 */
	public void setSignDay(int signDay) {
		this.signDay = signDay;
	}

	/**
	 * @return the firstSign
	 */
	public long getFirstSign() {
		return firstSign;
	}

	/**
	 * @param firstSign
	 *            the firstSign to set
	 */
	public void setFirstSign(long firstSign) {
		this.firstSign = firstSign;
	}

	/**
	 * @return the havePickSign
	 */
	public List<Integer> getHavePickSign() {
		return havePickSign;
	}

	/**
	 * @param havePickSign
	 *            the havePickSign to set
	 */
	public void setHavePickSign(List<Integer> havePickSign) {
		this.havePickSign = havePickSign;
	}

	/**
	 * @return the con
	 */
	public int getCon() {
		return con;
	}

	/**
	 * @param con
	 *            the con to set
	 */
	public void setCon(int con) {
		this.con = con;
	}

	/**
	 * @return the conPick
	 */
	public List<Integer> getConPick() {
		return conPick;
	}

	/**
	 * 添加连续登录礼包
	 */
	public void addConPick(int day) {
		if (!conPick.contains(day)) {
			conPick.add(day);
		}
	}

	/**
	 * @param conPick
	 *            the conPick to set
	 */
	public void setConPick(List<Integer> conPick) {
		this.conPick = conPick;
	}

	public Map<Integer, Integer> getLevelGiftStateMap() {
		return levelGiftStateMap;
	}

	public void setLevelGiftStateMap(Map<Integer, Integer> levelGiftStateMap) {
		this.levelGiftStateMap = levelGiftStateMap;
	}

	/**
	 * @return the helper
	 */
	public Map<EHelper, Integer> getHelper() {
		return helper;
	}

	/**
	 * @param helper
	 *            the helper to set
	 */
	public void setHelper(Map<EHelper, Integer> helper) {
		this.helper = helper;
	}

	/**
	 * @return the buffs
	 */
	public Map<EBuffType, BuffRole> getBuffs() {
		return buffs;
	}

	/**
	 * @param buffs
	 *            the buffs to set
	 */
	public void setBuffs(Map<EBuffType, BuffRole> buffs) {
		this.buffs = buffs;
	}

	/**
	 * @return the isPrBattle
	 */
	public boolean isPrBattle() {
		return isPrBattle;
	}

	/**
	 * @param isPrBattle
	 *            the isPrBattle to set
	 */
	public void setPrBattle(boolean isPrBattle) {
		this.isPrBattle = isPrBattle;
	}

	/**
	 * @return the isBattle
	 */
	public boolean isBattle() {
		return isBattle;
	}

	/**
	 * @param isBattle
	 *            the isBattle to set
	 */
	public void setBattle(boolean isBattle) {
		this.isBattle = isBattle;
		MapManager.instance().addBattleQueue(this);
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the showStrength
	 */
	public int getShowStrength() {
		return showStrength;
	}

	/**
	 * @param showStrength
	 *            the showStrength to set
	 */
	public void setShowStrength(int showStrength) {
		this.showStrength = showStrength;
	}

	/**
	 * @return the showIntellect
	 */
	public int getShowIntellect() {
		return showIntellect;
	}

	/**
	 * @param showIntellect
	 *            the showIntellect to set
	 */
	public void setShowIntellect(int showIntellect) {
		this.showIntellect = showIntellect;
	}

	/**
	 * @return the showPhysique
	 */
	public int getShowPhysique() {
		return showPhysique;
	}

	/**
	 * @param showPhysique
	 *            the showPhysique to set
	 */
	public void setShowPhysique(int showPhysique) {
		this.showPhysique = showPhysique;
	}

	/**
	 * @return the showAgility
	 */
	public int getShowAgility() {
		return showAgility;
	}

	/**
	 * @param showAgility
	 *            the showAgility to set
	 */
	public void setShowAgility(int showAgility) {
		this.showAgility = showAgility;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getContentNum() {
		return contentNum;
	}

	public void setContentNum(int contentNum) {
		this.contentNum = contentNum;
	}

	@Override
	public List<Integer> getSkills() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLivenessScore() {
		return livenessScore;
	}

	public void setLivenessScore(int livenessScore) {
		this.livenessScore = livenessScore;
	}

	public LinkedHashMap<Integer, Integer> getLivenessScoreMap() {
		return livenessScoreMap;
	}

	public void setLivenessScoreMap(LinkedHashMap<Integer, Integer> livenessScoreMap) {
		this.livenessScoreMap = livenessScoreMap;
	}

	/**
	 * @return the onlineTime
	 */
	public int getOnlineTime() {
		return onlineTime;
	}

	/**
	 * @param onlineTime
	 *            the onlineTime to set
	 */
	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * @return the guide
	 */
	public int getGuide() {
		return guide;
	}

	/**
	 * @param guide
	 *            the guide to set
	 */
	public void setGuide(int guide) {
		this.guide = guide;
	}

	/**
	 * @return the battleId
	 */
	public long getBattleId() {
		return battleId;
	}

	/**
	 * @param battleId
	 *            the battleId to set
	 */
	public void setBattleId(long battleId) {
		this.battleId = battleId;
	}

	/**
	 * @return the guildTaskNum
	 */
	public int getGuildTaskNum() {
		return guildTaskNum;
	}

	/**
	 * @param guildTaskNum
	 *            the guildTaskNum to set
	 */
	public void setGuildTaskNum(int guildTaskNum) {
		this.guildTaskNum = guildTaskNum;
	}

	/**
	 * @return the guildTask
	 */
	public BaseTask getGuildTask() {
		return guildTask;
	}

	/**
	 * @param guildTask
	 *            the guildTask to set
	 */
	public void setGuildTask(BaseTask guildTask) {
		this.guildTask = guildTask;
	}

	/**
	 * @return the huntTreasureNum
	 */
	// public int getHuntTreasureNum() {
	// return huntTreasureNum;
	// }

	/**
	 * @param huntTreasureNum
	 *            the huntTreasureNum to set
	 */
	// public void setHuntTreasureNum(int huntTreasureNum) {
	// this.huntTreasureNum = huntTreasureNum;
	// }

	public Map<Integer, Integer> getOnlineAwardStateMap() {
		return onlineAwardStateMap;
	}

	public void setOnlineAwardStateMap(Map<Integer, Integer> onlineAwardStateMap) {
		this.onlineAwardStateMap = onlineAwardStateMap;
	}

	/**
	 * @return the ghostTask
	 */
	public BaseTask getGhostTask() {
		return ghostTask;
	}

	/**
	 * @param ghostTask
	 *            the ghostTask to set
	 */
	public void setGhostTask(BaseTask ghostTask) {
		this.ghostTask = ghostTask;
	}

	/**
	 * @return the ghostTaskNum
	 */
	public int getGhostTaskNum() {
		return ghostTaskNum;
	}

	/**
	 * @param ghostTaskNum
	 *            the ghostTaskNum to set
	 */
	public void setGhostTaskNum(int ghostTaskNum) {
		this.ghostTaskNum = ghostTaskNum;
	}

	/**
	 * @return the trials
	 */
	public HashMap<Integer, Trials> getTrials() {
		return trials;
	}

	/**
	 * @param trials
	 *            the trials to set
	 */
	public void setTrials(HashMap<Integer, Trials> trials) {
		this.trials = trials;
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

	/**
	 * @return the fightState
	 */
	public ERoleFightState getFightState() {
		return fightState;
	}

	/**
	 * @param fightState
	 *            the fightState to set
	 */
	public void setFightState(ERoleFightState fightState) {
		this.fightState = fightState;
	}

	/**
	 * @return the blood
	 */
	public int getBlood() {
		return blood;
	}

	/**
	 * @param blood
	 *            the blood to set
	 */
	public void setBlood(int blood) {
		this.blood = blood;
	}

	/**
	 * @return the bmark
	 */
	public int getBmark() {
		return bmark;
	}

	/**
	 * @param bmark
	 *            the bmark to set
	 */
	public void setBmark(int bmark) {
		this.bmark = bmark;
	}

	/**
	 * @return the liveNum
	 */
	public int getLiveNum() {
		return liveNum;
	}

	/**
	 * @param liveNum
	 *            the liveNum to set
	 */
	public void setLiveNum(int liveNum) {
		this.liveNum = liveNum;
	}

	/**
	 * @return the liveTime
	 */
	public long getLiveTime() {
		return liveTime;
	}

	/**
	 * @param liveTime
	 *            the liveTime to set
	 */
	public void setLiveTime(long liveTime) {
		this.liveTime = liveTime;
	}

	/**
	 * @return the compensate
	 */
	public int getCompensate() {
		return compensate;
	}

	/**
	 * @param compensate
	 *            the compensate to set
	 */
	public void setCompensate(int compensate) {
		this.compensate = compensate;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room
	 *            the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * @return the bloodKillTime
	 */
	public long getBloodKillTime() {
		return bloodKillTime;
	}

	/**
	 * @param bloodKillTime
	 *            the bloodKillTime to set
	 */
	public void setBloodKillTime(long bloodKillTime) {
		this.bloodKillTime = bloodKillTime;
	}

	/**
	 * @return the cdKeys
	 */
	public List<Integer> getCdKeys() {
		return cdKeys;
	}

	/**
	 * @param cdKeys
	 *            the cdKeys to set
	 */
	public void setCdKeys(List<Integer> cdKeys) {
		this.cdKeys = cdKeys;
	}

	/**
	 * @return the sevenMap
	 */
	public Map<Integer, Seven> getSevenMap() {
		return sevenMap;
	}

	/**
	 * @param sevenMap
	 *            the sevenMap to set
	 */
	public void setSevenMap(Map<Integer, Seven> sevenMap) {
		this.sevenMap = sevenMap;
	}

	/**
	 * @return the guildTaskSum
	 */
	public int getGuildTaskSum() {
		return guildTaskSum;
	}

	/**
	 * @param guildTaskSum
	 *            the guildTaskSum to set
	 */
	public void setGuildTaskSum(int guildTaskSum) {
		this.guildTaskSum = guildTaskSum;
	}

	/**
	 * @return the ghostTaskSum
	 */
	public int getGhostTaskSum() {
		return ghostTaskSum;
	}

	/**
	 * @param ghostTaskSum
	 *            the ghostTaskSum to set
	 */
	public void setGhostTaskSum(int ghostTaskSum) {
		this.ghostTaskSum = ghostTaskSum;
	}

	public int getCurrentFloorId() {
		return currentFloorId;
	}

	public void setCurrentFloorId(int currentFloorId) {
		this.currentFloorId = currentFloorId;
	}

	public int getNullResetNum() {
		return nullResetNum;
	}

	public void setNullResetNum(int nullResetNum) {
		this.nullResetNum = nullResetNum;
	}

	public int getMoneyResetNum() {
		return moneyResetNum;
	}

	public void setMoneyResetNum(int moneyResetNum) {
		this.moneyResetNum = moneyResetNum;
	}

	/**
	 * @return the vipGift
	 */
	public int getVipGift() {
		return vipGift;
	}

	/**
	 * @param vipGift
	 *            the vipGift to set
	 */
	public void setVipGift(int vipGift) {
		this.vipGift = vipGift;
	}

	/**
	 * @return the costGold
	 */
	public int getCostGold() {
		return costGold;
	}

	/**
	 * @param costGold
	 *            the costGold to set
	 */
	public void setCostGold(int costGold) {
		this.costGold = costGold;
	}

	public int getOpenVipChat() {
		return openVipChat;
	}

	public void setOpenVipChat(int openVipChat) {
		this.openVipChat = openVipChat;
	}

	/**
	 * @return the figureRes
	 */
	public FigureRes getFigureRes() {
		return figureRes;
	}

	/**
	 * @param figureRes
	 *            the figureRes to set
	 */
	public void setFigureRes(FigureRes figureRes) {
		this.figureRes = figureRes;
	}

	// public int getHuntskillNum() {
	// return huntskillNum;
	// }
	//
	// public void setHuntskillNum(int huntskillNum) {
	// this.huntskillNum = huntskillNum;
	// }

	public int getKillMonsterNum() {
		return killMonsterNum;
	}

	public void setKillMonsterNum(int killMonsterNum) {
		this.killMonsterNum = killMonsterNum;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the firstPartner
	 */
	public int getFirstPartner() {
		return firstPartner;
	}

	/**
	 * @param firstPartner
	 *            the firstPartner to set
	 */
	public void setFirstPartner(int firstPartner) {
		this.firstPartner = firstPartner;
	}

	public int getDailyAnswerNum() {
		return dailyAnswerNum;
	}

	public void setDailyAnswerNum(int dailyAnswerNum) {
		this.dailyAnswerNum = dailyAnswerNum;
	}

	/**
	 * @return the eliteMonsterNum
	 */
	public int getEliteMonsterNum() {
		return eliteMonsterNum;
	}

	/**
	 * @param eliteMonsterNum
	 *            the eliteMonsterNum to set
	 */
	public void setEliteMonsterNum(int eliteMonsterNum) {
		this.eliteMonsterNum = eliteMonsterNum;
	}

	/**
	 * @return the eliteMonster
	 */
	public int getEliteMonster() {
		return eliteMonster;
	}

	/**
	 * @param eliteMonster
	 *            the eliteMonster to set
	 */
	public void setEliteMonster(int eliteMonster) {
		this.eliteMonster = eliteMonster;
	}

	/**
	 * @return the isEliteMonster
	 */
	public boolean isEliteMonster() {
		return isEliteMonster;
	}

	/**
	 * @param isEliteMonster
	 *            the isEliteMonster to set
	 */
	public void setEliteMonster(boolean isEliteMonster) {
		this.isEliteMonster = isEliteMonster;
	}

	public BaseTask getYingxiongtie() {
		return yingxiongtie;
	}

	public void setYingxiongtie(BaseTask yingxiongtie) {
		this.yingxiongtie = yingxiongtie;
	}

	public int getYxtTaskNum() {
		return yxtTaskNum;
	}

	public void setYxtTaskNum(int yxtTaskNum) {
		this.yxtTaskNum = yxtTaskNum;
	}

	// public void setItems(Map<Integer, CollectionTask> items) {
	// this.items = items;
	// }

	public void setMails(List<Mail> mails) {
		this.mails = mails;
	}

	/**
	 * @return the specialGuide
	 */
	public int getSpecialGuide() {
		return specialGuide;
	}

	/**
	 * @param specialGuide
	 *            the specialGuide to set
	 */
	public void setSpecialGuide(int specialGuide) {
		this.specialGuide = specialGuide;
	}

	/**
	 * @return the taskFblNum
	 */
	public int getTaskFblNum() {
		return taskFblNum;
	}

	/**
	 * @param taskFblNum
	 *            the taskFblNum to set
	 */
	public void setTaskFblNum(int taskFblNum) {
		this.taskFblNum = taskFblNum;
	}

	/**
	 * @return the fabuling
	 */
	public ActivityFblInfo getFabuling() {
		return fabuling;
	}

	/**
	 * @param fabuling
	 *            the fabuling to set
	 */
	public void setFabuling(ActivityFblInfo fabuling) {
		this.fabuling = fabuling;
	}

	/**
	 * @return the taskFblId
	 */
	public long getTaskFblId() {
		return taskFblId;
	}

	/**
	 * @param taskFblId
	 *            the taskFblId to set
	 */
	public void setTaskFblId(long taskFblId) {
		this.taskFblId = taskFblId;
	}

	/**
	 * @return the warehouseCoin
	 */
	public int getWarehouseCoin() {
		return warehouseCoin;
	}

	/**
	 * @param warehouseCoin
	 *            the warehouseCoin to set
	 */
	public void setWarehouseCoin(int warehouseCoin) {
		this.warehouseCoin = warehouseCoin;
	}

	/**
	 * @return the upTipsInit
	 */
	public Map<Integer, Boolean> getUpTipsInit() {
		return upTipsInit;
	}

	/**
	 * @param upTipsInit
	 *            the upTipsInit to set
	 */
	public void setUpTipsInit(Map<Integer, Boolean> upTipsInit) {
		this.upTipsInit = upTipsInit;
	}

	/**
	 * @return the bagStone
	 */
	public BagStone getBagStone() {
		return bagStone;
	}

	/**
	 * @param bagStone
	 *            the bagStone to set
	 */
	public void setBagStone(BagStone bagStone) {
		this.bagStone = bagStone;
	}

	/**
	 * @return the trialsState
	 */
	public int getTrialsState() {
		return trialsState;
	}

	/**
	 * @param trialsState
	 *            the trialsState to set
	 */
	public void setTrialsState(int trialsState) {
		this.trialsState = trialsState;
	}

	/**
	 * @return the plusMoney
	 */
	public int getPlusMoney() {
		return plusMoney;
	}

	/**
	 * @param plusMoney
	 *            the plusMoney to set
	 */
	public void setPlusMoney(int plusMoney) {
		this.plusMoney = plusMoney;
	}

	/**
	 * @return the recPlusMoney
	 */
	public List<Integer> getRecPlusMoney() {
		return recPlusMoney;
	}

	/**
	 * @param recPlusMoney
	 *            the recPlusMoney to set
	 */
	public void setRecPlusMoney(List<Integer> recPlusMoney) {
		this.recPlusMoney = recPlusMoney;
	}

	/**
	 * @return the closeTeam
	 */
	public boolean isCloseTeam() {
		return closeTeam;
	}

	/**
	 * @param closeTeam
	 *            the closeTeam to set
	 */
	public void setCloseTeam(boolean closeTeam) {
		this.closeTeam = closeTeam;
	}

	/**
	 * @return the closeFriend
	 */
	public boolean isCloseFriend() {
		return closeFriend;
	}

	/**
	 * @param closeFriend
	 *            the closeFriend to set
	 */
	public void setCloseFriend(boolean closeFriend) {
		this.closeFriend = closeFriend;
	}

	/**
	 * @return the closeWhisper
	 */
	public boolean isCloseWhisper() {
		return closeWhisper;
	}

	/**
	 * @param closeWhisper
	 *            the closeWhisper to set
	 */
	public void setCloseWhisper(boolean closeWhisper) {
		this.closeWhisper = closeWhisper;
	}

	/**
	 * @return the firstDouble
	 */
	public List<Integer> getFirstDouble() {
		return firstDouble;
	}

	/**
	 * @param firstDouble
	 *            the firstDouble to set
	 */
	public void setFirstDouble(List<Integer> firstDouble) {
		this.firstDouble = firstDouble;
	}

	/**
	 * @return the taskCycleNum
	 */
	public int getTaskCycleNum() {
		return taskCycleNum;
	}

	/**
	 * @param taskCycleNum
	 *            the taskCycleNum to set
	 */
	public void setTaskCycleNum(int taskCycleNum) {
		this.taskCycleNum = taskCycleNum;
	}

	/**
	 * @return the taskCycle
	 */
	public BaseTask getTaskCycle() {
		return taskCycle;
	}

	/**
	 * @param taskCycle
	 *            the taskCycle to set
	 */
	public void setTaskCycle(BaseTask taskCycle) {
		this.taskCycle = taskCycle;
	}

	/**
	 * @return the taskCycleRow
	 */
	public int getTaskCycleRow() {
		return taskCycleRow;
	}

	/**
	 * @param taskCycleRow
	 *            the taskCycleRow to set
	 */
	public void setTaskCycleRow(int taskCycleRow) {
		this.taskCycleRow = taskCycleRow;
	}

	/**
	 * @return the dailyMap
	 */
	public Map<Integer, DailyModelRole> getDailyMap() {
		return dailyMap;
	}

	/**
	 * @param dailyMap
	 *            the dailyMap to set
	 */
	public void setDailyMap(Map<Integer, DailyModelRole> dailyMap) {
		this.dailyMap = dailyMap;
	}

	/**
	 * @return the secPartner
	 */
	public int getSecPartner() {
		return secPartner;
	}

	/**
	 * @param secPartner
	 *            the secPartner to set
	 */
	public void setSecPartner(int secPartner) {
		this.secPartner = secPartner;
	}

	public HashMap<Integer, Integer> getResetTrials() {
		return resetTrials;
	}

	public void setResetTrials(HashMap<Integer, Integer> resetTrials) {
		this.resetTrials = resetTrials;
	}

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * @return the energyTime
	 */
	public long getEnergyTime() {
		return energyTime;
	}

	/**
	 * @param energyTime
	 *            the energyTime to set
	 */
	public void setEnergyTime(long energyTime) {
		this.energyTime = energyTime;
	}

	/**
	 * @return the energyMax
	 */
	public int getEnergyMax() {
		return energyMax;
	}

	/**
	 * @param energyMax
	 *            the energyMax to set
	 */
	public void setEnergyMax(int energyMax) {
		this.energyMax = energyMax;
	}

	/**
	 * @return the partnerMap
	 */
	public HashMap<Long, Partner> getPartnerMap() {
		return partnerMap;
	}

	/**
	 * @param partnerMap
	 *            the partnerMap to set
	 */
	public void setPartnerMap(HashMap<Long, Partner> partnerMap) {
		this.partnerMap = partnerMap;
	}

	/**
	 * @return the partnerFight
	 */
	public HashMap<Integer, Long> getPartnerFight() {
		return partnerFight;
	}

	/**
	 * @param partnerFight
	 *            the partnerFight to set
	 */
	public void setPartnerFight(HashMap<Integer, Long> partnerFight) {
		this.partnerFight = partnerFight;
	}

	/**
	 * @return the partners
	 */
	public ArrayList<Partner> getPartners() {
		return partners;
	}

	/**
	 * @param partners
	 *            the partners to set
	 */
	public void setPartners(ArrayList<Partner> partners) {
		this.partners = partners;
	}

	/**
	 * @return the emoCount
	 */
	public int getEmoCount() {
		return emoCount;
	}

	/**
	 * @param emoCount
	 *            the emoCount to set
	 */
	public void setEmoCount(int emoCount) {
		this.emoCount = emoCount;
	}

}
