package iyunu.NewTLOL.processor.activity;


/**
 * 积分榜 无用
 * 
 * @author fenghaiyu
 * 
 */
public class DailyProcessorCenter extends Thread {

	// private volatile boolean process = true; // 是否运行
	// private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	// static{
	// DailyManager.instance().getMap();
	// }
	// /**
	// * 关闭进程
	// */
	// public void shutdown() {
	// process = false;
	// }
	//
	// public void run() {
	//
	// while (process) {
	// long startTime = System.currentTimeMillis();
	// long spent = 0;
	//
	// // 如果处于时间段之内且活动开关为关闭
	// if (startTime >= DailyManager.STARTTIME && startTime <
	// DailyManager.ENDTIME && !DailyManager.FLAG) {
	// DailyManager.FLAG = true;
	// System.out.println("日常任务 =====开始");
	// // 打开日常任务图标
	// for (Role role : ServerManager.instance().getOnlinePlayers().values()) {
	// SendMessage.refreshDailyTask(role, 1);
	// }
	// }
	// // 如果活动开关为打开，并且到时，则结算
	// if (DailyManager.FLAG && startTime >= DailyManager.ENDTIME) {
	// System.out.println("日常任务====结束");
	// DailyManager.FLAG = false;
	// // 清空
	// DailyManager.instance().clear();
	//
	// }
	//
	// spent = System.currentTimeMillis() - startTime;
	// if (spent < PERIOD_WAIT) {
	// synchronized (this) {
	// try {
	// wait(PERIOD_WAIT);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// LogManager.info("【积分榜处理器】已关闭");
	// }

}
