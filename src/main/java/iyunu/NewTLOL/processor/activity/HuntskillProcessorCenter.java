package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.json.HuntskillJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.skillBook.instance.HuntskillCoord;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 猎技活动
 * 
 * @author SunHonglei
 * 
 */
public class HuntskillProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private long refreshTime = 0; // 刷新猎技宝箱时间
	private boolean isRefresh = false;

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		long timeOut = System.currentTimeMillis() + Time.MINUTE_MILLISECOND * 30;
		ActivityManager.HUNTSKILL_END_TIME = timeOut;
		ActivityManager.HUNTSKILL_STATE = true;
		
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;
			
			if(startTime >= timeOut){
				process = false;
				ActivityManager.HUNTSKILL_STATE = false;
				
				String content = StringControl.yel("猎技活动已结束！");
				BulletinManager.instance().addBulletinRock(content, 2);
				break;
			}

			BaseMap baseMap = MapManager.instance().getMapById(10);

			if (!isRefresh && baseMap.getBoxs().isEmpty()) {
				refreshTime = startTime + 30 * Time.MILLISECOND;
				isRefresh = true;
			}

			if (isRefresh && refreshTime < startTime) {
				for (int i = 0; i < 20; i++) {
					HuntskillCoord huntskillCoord = HuntskillJson.instance().radomHuntskillCoord();
					baseMap.addItem(huntskillCoord.getX(), huntskillCoord.getY(), MonsterJson.instance().randomSkillBoxs());
				}
				isRefresh = false;
			}

			spent = System.currentTimeMillis() - startTime;
			if (spent < PERIOD_WAIT) {
				synchronized (this) {
					try {
						wait(PERIOD_WAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		LogManager.info("【猎技处理器】已关闭");
	}
}
