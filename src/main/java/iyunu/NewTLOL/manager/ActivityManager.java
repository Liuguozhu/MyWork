package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.map.Site;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 活动
 * 
 * @author SunHonglei
 * 
 */
public class ActivityManager {

	// ===========================帮派入侵=============================
	public static boolean INVASION_STATE = false;
	/** 帮派入侵每次刷新怪物数量 **/
	public static int INVASION_MONSTER_NUM = 10;

	private static List<Integer> invasionMonsters = new ArrayList<Integer>();
	private static List<Site> invasionSties = new ArrayList<Site>();

	// ===========================猎技=============================
	public static long HUNTSKILL_END_TIME; // 猎技结束时间
	public static boolean HUNTSKILL_STATE = false;

	// ============================血战=============================
	public static boolean BLOOD_STATE = false;
	public static long BLOOD_START_TIME;
	public static long BLOOD_END_TIME;
	private static int BLOOD_START_TIME_H1 = 19;
	private static int BLOOD_START_TIME_M1 = 00;
	private static int BLOOD_END_TIME_H1 = 19;
	private static int BLOOD_END_TIME_M1 = 25;

	// ============================每日答题=============================
	public static boolean ANSWER_STATE = false;

	// ============================七大恶人=============================
	public static long QIDAEREN_START_TIME; // 每日答题开始时间
	public static long QIDAEREN_END_TIME; // 每日答题结束时间
	public static boolean QIDAEREN_STATE = false;

	private static int QIDAEREN_START_TIME_H = 21;
	private static int QIDAEREN_START_TIME_M = 30;
	private static int QIDAEREN_END_TIME_H = 22;
	private static int QIDAEREN_END_TIME_M = 00;

	// ============================发布令=============================
	public static ConcurrentHashMap<Long, ActivityFblInfo> activityFblInfos = new ConcurrentHashMap<Long, ActivityFblInfo>();
	public static CopyOnWriteArrayList<Long> activityFblIds = new CopyOnWriteArrayList<Long>();
	public static ConcurrentHashMap<Long, List<ActivityFblInfo>> activityFblMy = new ConcurrentHashMap<Long, List<ActivityFblInfo>>();
	public static ConcurrentHashMap<Long, ActivityFblInfo> activityFblTimer = new ConcurrentHashMap<Long, ActivityFblInfo>();

	/**
	 * 发布任务
	 * 
	 * @param activityFblInfo
	 */
	public static void addActivityFbl(ActivityFblInfo activityFblInfo) {
		activityFblIds.add(activityFblInfo.getId());
		activityFblInfos.put(activityFblInfo.getId(), activityFblInfo);
		if (activityFblMy.containsKey(activityFblInfo.getRoleId())) {
			activityFblMy.get(activityFblInfo.getRoleId()).add(activityFblInfo);
		} else {
			ArrayList<ActivityFblInfo> list = new ArrayList<ActivityFblInfo>();
			list.add(activityFblInfo);
			activityFblMy.put(activityFblInfo.getRoleId(), list);
		}
	}

	/**
	 * 取消任务
	 * 
	 * @param activityFblInfo
	 */
	public static void cancelActivityFbl(ActivityFblInfo activityFblInfo) {
		activityFblIds.remove(activityFblInfo.getId());
		activityFblInfos.remove(activityFblInfo.getId());
		activityFblMy.get(activityFblInfo.getRoleId()).remove(activityFblInfo);
	}

	/**
	 * 查询角色的发布任务数量
	 * 
	 * @param roleId
	 * @return
	 */
	public static int countFblNum(long roleId) {
		if (activityFblMy.containsKey(roleId)) {
			return activityFblMy.get(roleId).size();
		}
		return 0;
	}

	/**
	 * 取消任务
	 * 
	 * @param id
	 */
	public static void cancelActivityFbl(long roleId) {
		if (activityFblMy.containsKey(roleId)) {
			for (ActivityFblInfo activityFblInfo : activityFblMy.get(roleId)) {
				activityFblIds.remove(activityFblInfo.getId());
				activityFblInfos.remove(activityFblInfo.getId());
				// 发送邮件
				MailServer.send(activityFblInfo.getRoleId(), "发布令撤销", "您发布的任务已被撤销，返回" + activityFblInfo.getCoin() + "银两", null, 0, activityFblInfo.getCoin(), 0, 0, 0, null);
			}
		}
		activityFblMy.remove(roleId);
	}

	// ============================发布令=============================
	/**
	 * 血战初始化
	 */
	public static void bloodInit() {
		long time = Time.getTodayTime(BLOOD_END_TIME_H1, BLOOD_END_TIME_M1, 00);
		long now = System.currentTimeMillis();
		if (now < time) {
			BLOOD_START_TIME = Time.getTodayTime(BLOOD_START_TIME_H1, BLOOD_START_TIME_M1, 00);
			BLOOD_END_TIME = time;
		} else {
			BLOOD_START_TIME = Time.getNextTime(BLOOD_START_TIME_H1, BLOOD_START_TIME_M1, 00);
			BLOOD_END_TIME = Time.getNextTime(BLOOD_END_TIME_H1, BLOOD_END_TIME_M1, 00);
		}

	}

	/**
	 * 七大恶人活动信息初始化
	 */
	public static void qiDaERenInit() {
		long time = Time.getTodayTime(QIDAEREN_END_TIME_H, QIDAEREN_END_TIME_M, 00);
		long now = System.currentTimeMillis();
		if (now < time) {
			QIDAEREN_START_TIME = Time.getTodayTime(QIDAEREN_START_TIME_H, QIDAEREN_START_TIME_M, 00);
			QIDAEREN_END_TIME = time;
		} else {
			QIDAEREN_START_TIME = Time.getNextTime(QIDAEREN_START_TIME_H, QIDAEREN_START_TIME_M, 00);
			QIDAEREN_END_TIME = Time.getNextTime(QIDAEREN_END_TIME_H, QIDAEREN_END_TIME_M, 00);
		}
	}

	/**
	 * 重置七大恶人时间
	 */
	public static void resetQiDaERenTime() {
		QIDAEREN_START_TIME = Time.getNextTime(QIDAEREN_START_TIME_H, QIDAEREN_START_TIME_M, 00);
		QIDAEREN_END_TIME = Time.getNextTime(QIDAEREN_END_TIME_H, QIDAEREN_END_TIME_M, 00);
		LogManager.logOut("重置七大恶人时间，开始：" + Time.getTimeToStr(QIDAEREN_START_TIME) + "，结束：" + Time.getTimeToStr(QIDAEREN_END_TIME));
	}

	public static void addMonsterGroup(int monsterGroupId) {
		invasionMonsters.add(monsterGroupId);
	}

	public static void addSite(int x, int y) {
		invasionSties.add(new Site(x, y));
	}

	public static Integer randomMonsterGroup() {
		return invasionMonsters.get(Util.getRandom(invasionMonsters.size()));
	}

	public static Site randomSite() {
		return invasionSties.get(Util.getRandom(invasionSties.size()));
	}

	/**
	 * 重置每日答题信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void resetDailyAnswer(Role role) {
		role.setDailyAnswerNum(1);
		role.setDailyAnswerContinuousTrueNum(0);
		role.setDailyAnswerTotleTrueNum(0);
		role.setDailyAnswerTotleGold(0);
		role.setDailyAnswerState(0);
		role.getDailyAnswerQuestions().clear();
	}
}
