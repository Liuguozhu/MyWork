package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.manager.HelperManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.activity.BloodProcessorCenter;
import iyunu.NewTLOL.processor.activity.EmoMap1ProcessorCenter;
import iyunu.NewTLOL.processor.activity.EmoMap2ProcessorCenter;
import iyunu.NewTLOL.processor.activity.QiDaERenProcessorCenter;
import iyunu.NewTLOL.server.blood.BloodServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

public class ActivityProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private BloodProcessorCenter bloodProcessorCenter;
	private QiDaERenProcessorCenter qiDaERenProcessorCenter;
	private EmoMap1ProcessorCenter emoMapProcessorCenter;
	private EmoMap2ProcessorCenter emoMapProcessorCenter2;

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		ActivityManager.bloodInit();
		ActivityManager.qiDaERenInit();
		EmoMapManager.emo1Init();
		EmoMapManager.emo2Init();
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;
			// ======血战======
			if (!ActivityManager.BLOOD_STATE && startTime >= ActivityManager.BLOOD_START_TIME && startTime < ActivityManager.BLOOD_END_TIME) {
				HelperManager.DAILYINFO_XUEZHAN.setState(0);
				String content = StringControl.yel("！！！" + StringControl.red("血战") + "！！！已开启！");
				BulletinManager.instance().addBulletinRock(content, 2);
				ActivityManager.BLOOD_STATE = true;
				// 此处清空上一次的所有结果
				BloodManager.instance().bloodMap.clear();
				BloodManager.instance().getHong().clear();
				BloodManager.instance().getLan().clear();
				BloodManager.instance().shouSha = 0;
				BloodManager.instance().shouShaMonster = 0;
				BloodManager.instance().hKill = 0;
				BloodServer.hongMark = 0;
				BloodServer.lanMark = 0;
				BloodManager.lanPower = 0;
				BloodManager.hongPower = 0;
				// 启动
				bloodProcessorCenter = new BloodProcessorCenter();
				bloodProcessorCenter.start();
				// 刷新血战开始通知
				for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					SendMessage.refreshBloodSend(role);
				}

			}
			if (ActivityManager.BLOOD_STATE && startTime > ActivityManager.BLOOD_END_TIME) {
				HelperManager.DAILYINFO_XUEZHAN.setState(1);
				String content = StringControl.yel("！！！" + StringControl.red("血战") + "！！！结束！");
				BulletinManager.instance().addBulletinRock(content, 2);
				ActivityManager.BLOOD_STATE = false;
				bloodProcessorCenter.shutdown();
				ActivityManager.bloodInit();
			}
			// ======恶魔岛1======
			if (!EmoMapManager.EMO1_STATE && startTime >= EmoMapManager.EMO1_START_TIME && startTime < EmoMapManager.EMO1_END_TIME) {
				String content = StringControl.yel("！！！" + StringControl.red("恶魔岛—万竹涧") + "！！！已开启！");
				BulletinManager.instance().addBulletinRock(content, 2);
				EmoMapManager.EMO1_STATE = true;
				// 启动
				emoMapProcessorCenter = new EmoMap1ProcessorCenter();
				emoMapProcessorCenter.start();
				// 刷新恶魔岛开始通知
				for (Long roleId1 : ServerManager.instance().getIdChannelPair().keySet()) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId1);
					if (role != null) {
						SendMessage.refreshEmoSend(role, 1);
					}
				}
			}
			if (EmoMapManager.EMO1_STATE && startTime > EmoMapManager.EMO1_END_TIME) {
				String content = StringControl.yel("！！！" + StringControl.red("恶魔岛—万竹涧") + "！！！结束！");
				BulletinManager.instance().addBulletinRock(content, 2);
				EmoMapManager.EMO1_STATE = false;
				emoMapProcessorCenter.shutdown();
				EmoMapManager.emo1Init();
			}
			// ======恶魔岛2======
			if (!EmoMapManager.EMO2_STATE && startTime >= EmoMapManager.EMO2_START_TIME && startTime < EmoMapManager.EMO2_END_TIME) {
				String content = StringControl.yel("！！！" + StringControl.red("恶魔岛—幻影谷") + "！！！已开启！");
				BulletinManager.instance().addBulletinRock(content, 2);
				EmoMapManager.EMO2_STATE = true;
				// 启动
				emoMapProcessorCenter2 = new EmoMap2ProcessorCenter();
				emoMapProcessorCenter2.start();
				// 刷新恶魔岛开始通知
				for (Long roleId1 : ServerManager.instance().getIdChannelPair().keySet()) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId1);
					if (role != null) {
						SendMessage.refreshEmoSend(role, 1);
					}
				}

			}
			if (EmoMapManager.EMO2_STATE && startTime > EmoMapManager.EMO2_END_TIME) {
				String content = StringControl.yel("！！！" + StringControl.red("恶魔岛—幻影谷") + "！！！结束！");
				BulletinManager.instance().addBulletinRock(content, 2);
				EmoMapManager.EMO2_STATE = false;
				emoMapProcessorCenter2.shutdown();
				EmoMapManager.emo2Init();
			}
			// ====== 七大恶人 ======
			if (!ActivityManager.QIDAEREN_STATE && startTime >= ActivityManager.QIDAEREN_START_TIME && startTime < ActivityManager.QIDAEREN_END_TIME) {
				ActivityManager.QIDAEREN_STATE = true;
				qiDaERenProcessorCenter = new QiDaERenProcessorCenter();
				qiDaERenProcessorCenter.start();
			}

			if (ActivityManager.QIDAEREN_STATE && startTime >= ActivityManager.QIDAEREN_END_TIME) {
				ActivityManager.QIDAEREN_STATE = false;
				qiDaERenProcessorCenter.shutdown();
				qiDaERenProcessorCenter = null;
				ActivityManager.resetQiDaERenTime(); // 重置开启时间
			}
			// ====== 七大恶人 ======

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
		LogManager.info("【活动处理器】已关闭");
	}
}
