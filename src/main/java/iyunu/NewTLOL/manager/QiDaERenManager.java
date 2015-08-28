package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.model.activity.DrawingSite;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.server.billboard.BillboardServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 七大恶人活动
 * 
 * @author SunHonglei
 * 
 */
public final class QiDaERenManager {

	/**
	 * 私有构造方法
	 */
	private QiDaERenManager() {

	}

	/**
	 * 返回QiDaERenManager实例
	 * 
	 * @return QiDaERenManager实例
	 */
	public static QiDaERenManager instance() {
		return instance;
	}

	private static QiDaERenManager instance = new QiDaERenManager();
	private ScheduledThreadPoolExecutor qiDaERenExec;

	public void clear() {
		qiDaERenExec = null;
	}

	public void init() {
		stopQiDaERen();
		clear();
		startQiDaERen();
	}

	/**
	 * 启动七大恶人活动
	 */
	public void startQiDaERen() {

		qiDaERenExec = new ScheduledThreadPoolExecutor(1);

		// long start = 10;
		long start = Time.HOUR_SECOND;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				QiDaERenManager.instance().refreshQiDaERen();
			}
		};

		qiDaERenExec.scheduleAtFixedRate(runnable, start, Time.HOUR_SECOND, TimeUnit.SECONDS);
	}

	/**
	 * 停止七大恶人活动
	 */
	public void stopQiDaERen() {
		if (qiDaERenExec != null) {
			qiDaERenExec.shutdown();
		}
	}

	/**
	 * @function 刷新七大恶人
	 * @author LuoSR
	 * @date 2014年9月13日
	 */
	public void refreshQiDaERen() {
		int level = Util.matchMax(40, BillboardServer.worldLevel());
		List<Integer> list = ActivityJson.instance().randomQiDaERen(level);
		MonsterManager.instance().check();
		for (Integer monsterId : list) {
			DrawingSite drawingSite = ActivityJson.instance().randomSite();
			BaseMap baseMap = MapManager.instance().getMapById(drawingSite.getMapId());

			LogManager.logOut("七大恶人   地图【" + baseMap.getName() + "】(" + drawingSite.getX() + "，" + drawingSite.getY() + ")");
			MonsterOnMap monsterOnMap = new MonsterOnMap(baseMap.getId(), drawingSite.getX(), drawingSite.getY(), monsterId, EMonsterOnMap.qidaeren);
			// monsterOnMap.setTeam(true);
			MonsterManager.instance().addMonsterOnMap(monsterOnMap, Time.MILLISECOND * 60 * 10); // 添加至怪物定时器
			baseMap.addMonster(monsterOnMap);

			String content = "七大恶人" + StringControl.pur(monsterOnMap.getMonsterName()) + "出现了，请各位大侠极力寻找，为武林除害";
			BulletinManager.instance().addBulletinRock(content, 1);
		}
	}
}
