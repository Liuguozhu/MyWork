package iyunu.NewTLOL.manager.gang;

import iyunu.NewTLOL.json.GangJson;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangFightMapInfo;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.MapGangFightInfo;
import iyunu.NewTLOL.model.role.ERoleFightState;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.processor.gangFight.OutFightProcessorCenter;
import iyunu.NewTLOL.processor.gangFight.RoundFightProcessorCenter;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @function 帮派战管理类
 * @author LuoSR
 * @date 2014年6月24日
 */
public class GangFightManager {

	public static String CHAMPION = "无";

	public static boolean 报名状态 = false; // 是否可以报名
	/** 0.循环赛，1.淘汰赛，2.报名 **/
	public static int GANG_FIGHT_STATE = 2;
	/** 本阶段结束时间 **/
	public static long FINISH_TIME = Time.getNextTime(20, 0, 0);

	/** 0.不显示，1.报名，2.距离开始，3.入场，4.战斗，5.距离报名 **/
	public static int STATE = 0;

	public static ArrayList<Long> 报名列表 = new ArrayList<Long>(); // 存储
	public static HashMap<Long, FightApplyInfo> 参赛列表 = new HashMap<Long, FightApplyInfo>(); // 存储
	public static ArrayList<FightApplyInfo> 循环赛周积分排名 = new ArrayList<FightApplyInfo>();
	public static ArrayList<FightApplyInfo> gangFightOutMap = new ArrayList<FightApplyInfo>();

	public static void resetApply() {
		报名列表.clear();
		参赛列表.clear();
		循环赛周积分排名.clear();
	}

	public static ArrayList<FightApplyInfo> 循环赛列表 = new ArrayList<FightApplyInfo>();

	public static ArrayList<Long> 参加当前淘汰赛帮派编号 = new ArrayList<Long>();
	public static HashMap<Integer, FightApplyInfo> 淘汰赛对阵表 = new HashMap<Integer, FightApplyInfo>();

	public static Set<Long> 参战角色编号 = new HashSet<Long>();

	public static long 开战时间;
	public static long 结束时间;

	public static void 报名(Gang gang) {
		报名列表.add(gang.getId());
		if (!参赛列表.containsKey(gang.getId())) {
			FightApplyInfo fightApplyInfo = new FightApplyInfo();
			fightApplyInfo.setGangId(gang.getId());
			fightApplyInfo.setGangName(gang.getName());
			参赛列表.put(gang.getId(), fightApplyInfo);
			循环赛周积分排名.add(fightApplyInfo);
		}
	}

	public static void apply(FightApplyInfo fightApplyInfo) {
		fightApplyInfo.setScore(0);
		报名列表.add(fightApplyInfo.getGangId());
		参赛列表.put(fightApplyInfo.getGangId(), fightApplyInfo);
		循环赛周积分排名.add(fightApplyInfo);
	}

	public static void 生成循环赛列表() {
		循环赛列表.clear();

		for (FightApplyInfo fightApplyInfo : 循环赛周积分排名) {
			if (报名列表.contains(fightApplyInfo.getGangId())) {
				循环赛列表.add(fightApplyInfo);
			}
		}

		if (循环赛列表.size() % 2 == 1) {
			FightApplyInfo fightApplyInfo = new FightApplyInfo();
			fightApplyInfo.setGangId(0);
			fightApplyInfo.setGangName("--");
			fightApplyInfo.setNull(true);
			循环赛列表.add(fightApplyInfo);
		}

		Collections.shuffle(循环赛列表); // 乱序
	}

	@SuppressWarnings("unchecked")
	public static void save() {
		gangFightOutMap = (ArrayList<FightApplyInfo>) 循环赛周积分排名.clone();
	}

	public static int 生成淘汰赛对阵表() {
		GangFightManager.淘汰赛对阵表.clear();
		参加当前淘汰赛帮派编号.clear();

		int size = gangFightOutMap.size();
		int result = 0;

		if (size == 0) { // 上届冠军继续

		} else if (size == 1) { // 直接冠军

			for (int i = 1; i <= 14; i++) {
				GangFightManager.淘汰赛对阵表.put(i, null);
			}

			FightApplyInfo fightApplyInfo = GangFightManager.gangFightOutMap.get(0);
			GangFightManager.淘汰赛对阵表.put(15, fightApplyInfo);
			result = 1;
			BulletinManager.instance().addBulletinRock("帮派战决赛圆满结束，恭喜" + StringControl.red(fightApplyInfo.getGangName()) + "帮派加冕成功，成为本周" + StringControl.red("冠军帮！"), 1);
			GangManager.instance().changeChampion(fightApplyInfo.getGangId()); // 转变冠军榜
			GangFightManager.CHAMPION = fightApplyInfo.getGangName();
		} else if (size == 2) { // 直接决赛

			for (int i = 1; i <= 12; i++) {
				GangFightManager.淘汰赛对阵表.put(i, null);
			}

			for (int i = 0; i < size; i++) {
				GangFightManager.淘汰赛对阵表.put(13 + i, GangFightManager.gangFightOutMap.get(i));
			}

			GangFightManager.淘汰赛对阵表.put(15, null);
			result = 2;
		} else if (size <= 4) { // 直接半决赛

			for (int i = 1; i <= 8; i++) {
				GangFightManager.淘汰赛对阵表.put(i, null);
			}

			for (int i = 0; i < 4; i++) {
				int index = GangFightManager.four(i);
				if (size > i) {
					FightApplyInfo fightApplyInfo = GangFightManager.gangFightOutMap.get(i);
					fightApplyInfo.setIndex(index);
					GangFightManager.淘汰赛对阵表.put(index, fightApplyInfo);
				} else {
					FightApplyInfo fightApplyInfo = new FightApplyInfo();
					fightApplyInfo.setIndex(index);
					// Gang gang = new Gang();
					// gang.setId(0);
					// gang.setName("--");
					// fightApplyInfo.setGang(gang);
					fightApplyInfo.setGangId(0);
					fightApplyInfo.setGangName("--");
					fightApplyInfo.setNull(true);
					GangFightManager.淘汰赛对阵表.put(index, fightApplyInfo);

				}
			}

			for (int i = 13; i <= 15; i++) {
				GangFightManager.淘汰赛对阵表.put(i, null);
			}
			result = 3;
		} else {

			for (int i = 0; i < 8; i++) {
				int index = GangFightManager.eight(i);
				if (size > i) {
					FightApplyInfo fightApplyInfo = GangFightManager.gangFightOutMap.get(i);
					fightApplyInfo.setIndex(index);
					GangFightManager.淘汰赛对阵表.put(index, fightApplyInfo);
				} else {
					FightApplyInfo fightApplyInfo = new FightApplyInfo();
					fightApplyInfo.setIndex(index);
					fightApplyInfo.setGangId(0);
					fightApplyInfo.setGangName("--");
					fightApplyInfo.setNull(true);
					GangFightManager.淘汰赛对阵表.put(index, fightApplyInfo);
				}
			}

			for (int i = 9; i <= 15; i++) {
				GangFightManager.淘汰赛对阵表.put(i, null);
			}
			result = 4;
		}
		return result;
	}

	public static void 循环赛比赛场地() {
		for (int i = 0; i < GangFightManager.循环赛列表.size(); i = i + 2) {

			MapGangFightInfo mapGangFightInfo = MapJson.instance().getMapGangFightInfo();

			FightApplyInfo fightApplyInfo1 = GangFightManager.循环赛列表.get(i);
			FightApplyInfo fightApplyInfo2 = GangFightManager.循环赛列表.get(i + 1);

			fightApplyInfo1.initMapGangFightInfo(mapGangFightInfo);
			fightApplyInfo1.setUpOrDown(1);
			fightApplyInfo2.initMapGangFightInfo(mapGangFightInfo);
			fightApplyInfo2.setUpOrDown(2);

			RoundFightProcessorCenter gangFightProcessorCenter = new RoundFightProcessorCenter(fightApplyInfo1, fightApplyInfo2, GangFightManager.开战时间, GangFightManager.结束时间);
			gangFightProcessorCenter.start();

		}
	}

	public static void 淘汰赛比赛场地(int num) {
		if (num == 0) { // 无参赛

		} else if (num == 1) { // 直接冠军

		} else if (num == 2) { // 决赛

			MapGangFightInfo mapGangFightInfo = MapJson.instance().getMapGangFightInfo();

			FightApplyInfo fightApplyInfo1 = 淘汰赛对阵表.get(13);
			FightApplyInfo fightApplyInfo2 = 淘汰赛对阵表.get(14);

			fightApplyInfo1.initMapGangFightInfo(mapGangFightInfo);
			fightApplyInfo1.setUpOrDown(1);
			fightApplyInfo2.initMapGangFightInfo(mapGangFightInfo);
			fightApplyInfo2.setUpOrDown(2);

			fightApplyInfo1.setIndex(13);
			fightApplyInfo2.setIndex(14);

			OutFightProcessorCenter gangFightProcessorCenter = new OutFightProcessorCenter(fightApplyInfo1, fightApplyInfo2, GangFightManager.开战时间, GangFightManager.结束时间, 2);
			gangFightProcessorCenter.start();

			参加当前淘汰赛帮派编号.clear();
			参加当前淘汰赛帮派编号.add(fightApplyInfo1.getGangId());
			参加当前淘汰赛帮派编号.add(fightApplyInfo2.getGangId());
		} else if (num == 3) { // 4进2
			参加当前淘汰赛帮派编号.clear();
			for (int i = 9; i <= 10; i = i + 1) {

				MapGangFightInfo mapGangFightInfo = MapJson.instance().getMapGangFightInfo();

				FightApplyInfo fightApplyInfo1 = 淘汰赛对阵表.get(i);
				FightApplyInfo fightApplyInfo2 = 淘汰赛对阵表.get(i + 2);

				fightApplyInfo1.initMapGangFightInfo(mapGangFightInfo);
				fightApplyInfo1.setUpOrDown(1);
				fightApplyInfo2.initMapGangFightInfo(mapGangFightInfo);
				fightApplyInfo2.setUpOrDown(2);

				fightApplyInfo1.setIndex(i);
				fightApplyInfo2.setIndex(i + 2);

				OutFightProcessorCenter gangFightProcessorCenter = new OutFightProcessorCenter(fightApplyInfo1, fightApplyInfo2, GangFightManager.开战时间, GangFightManager.结束时间, 5);
				gangFightProcessorCenter.start();

				参加当前淘汰赛帮派编号.add(fightApplyInfo1.getGangId());
				参加当前淘汰赛帮派编号.add(fightApplyInfo2.getGangId());
			}

		} else if (num == 4) { // 8进4
			参加当前淘汰赛帮派编号.clear();
			for (int i = 1; i <= 8; i = i + 2) {

				MapGangFightInfo mapGangFightInfo = MapJson.instance().getMapGangFightInfo();

				FightApplyInfo fightApplyInfo1 = 淘汰赛对阵表.get(i);
				FightApplyInfo fightApplyInfo2 = 淘汰赛对阵表.get(i + 1);

				fightApplyInfo1.initMapGangFightInfo(mapGangFightInfo);
				fightApplyInfo1.setUpOrDown(1);
				fightApplyInfo2.initMapGangFightInfo(mapGangFightInfo);
				fightApplyInfo2.setUpOrDown(2);

				fightApplyInfo1.setIndex(i);
				fightApplyInfo2.setIndex(i + 1);

				OutFightProcessorCenter gangFightProcessorCenter = new OutFightProcessorCenter(fightApplyInfo1, fightApplyInfo2, GangFightManager.开战时间, GangFightManager.结束时间, 6);
				gangFightProcessorCenter.start();

				参加当前淘汰赛帮派编号.add(fightApplyInfo1.getGangId());
				参加当前淘汰赛帮派编号.add(fightApplyInfo2.getGangId());
			}

		}
	}

	public static void 周积分排序() {
		Collections.sort(GangFightManager.循环赛周积分排名, GangFightManager.ComparatFightApply);
	}

	public static int eight(int i) {
		switch (i) {
		case 0:
			return 1;
		case 1:
			return 8;
		case 2:
			return 3;
		case 3:
			return 6;
		case 4:
			return 5;
		case 5:
			return 4;
		case 6:
			return 7;
		case 7:
			return 2;
		default:
			return 0;
		}
	}

	public static int four(int i) {
		switch (i) {
		case 0:
			return 9;
		case 1:
			return 12;
		case 2:
			return 11;
		case 3:
			return 10;
		default:
			return 0;
		}
	}

	/**
	 * @function 将该玩家传送出帮派战地图
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @date 2014年7月1日
	 */
	public static void quitGangFight(Role role) {

		GangFightMapInfo gangFightMapInfo = GangJson.instance().getGangFightMapInfo();

		BaseMap oldMapInfo = role.getMapInfo().getBaseMap();
		BaseMap newMapInfo = MapManager.instance().getMapById(gangFightMapInfo.getComeOutMapId());

		int x = gangFightMapInfo.getComeOutX();
		int y = gangFightMapInfo.getComeOutY();
		MapServer.changeMap(role, x, y, newMapInfo, oldMapInfo);

		role.setFightState(ERoleFightState.none);

		FightApplyInfo fightApplyInfo = GangFightManager.参赛列表.get(role.getGangId());

		if (fightApplyInfo != null) {
			fightApplyInfo.out(1);
		}
	}

	/**
	 * 退出帮战地图
	 * 
	 * @param role
	 *            角色对象
	 * @param team
	 *            队伍对象
	 */
	public static void quitGangFight(Role role, Team team) {

		GangFightMapInfo gangFightMapInfo = GangJson.instance().getGangFightMapInfo();

		BaseMap oldMapInfo = role.getMapInfo().getBaseMap();
		BaseMap newMapInfo = MapManager.instance().getMapById(gangFightMapInfo.getComeOutMapId());

		int x = gangFightMapInfo.getComeOutX();
		int y = gangFightMapInfo.getComeOutY();

		MapServer.changeMap(team, x, y, newMapInfo, oldMapInfo);

		FightApplyInfo fightApplyInfo = GangFightManager.参赛列表.get(role.getGangId());

		if (fightApplyInfo != null) {
			fightApplyInfo.out(team.getMember().size());
		}
	}

	public final static Comparator<FightApplyInfo> ComparatFightApply = new Comparator<FightApplyInfo>() {
		@Override
		public int compare(FightApplyInfo fightApplyInfo1, FightApplyInfo fightApplyInfo2) {

			// 帮级别高排在前，经验高排在前
			if (fightApplyInfo1.getScore() > fightApplyInfo2.getScore())
				return -1;
			else if (fightApplyInfo1.getScore() < fightApplyInfo2.getScore())
				return 1;
			else {
				if (fightApplyInfo1.getTime() < fightApplyInfo2.getTime()) {
					return -1;
				} else if (fightApplyInfo1.getTime() > fightApplyInfo2.getTime()) {
					return 1;
				} else {
					return 0;
				}

			}
		}

	};
}