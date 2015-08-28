package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.redis.RedisGangWelfare;
import iyunu.NewTLOL.util.Time;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeProcessorCenter {

	/**
	 * 私有构造方法
	 */
	private TimeProcessorCenter() {

	}

	private static TimeProcessorCenter instance = new TimeProcessorCenter();

	/**
	 * 返回TimeProcessorCenter实例
	 * 
	 * @return TimeProcessorCenter实例
	 */
	public static TimeProcessorCenter instance() {
		return instance;
	}

	private ScheduledThreadPoolExecutor midnightExec; // 每日零点检查
	private ScheduledThreadPoolExecutor weekExec; // 每周零点检查
	private ScheduledThreadPoolExecutor weekExec5; // 每周五零点检查
	private ScheduledThreadPoolExecutor weekExec6; // 每周六零点检查

	public void init() {
		startMidnightExec();
		startWeekExecExec();
	}

	public void stop() {
		stopMidnightExec();
		stopWeekExecExec();
	}

	public void startMidnightExec() {

		midnightExec = new ScheduledThreadPoolExecutor(1);

		long start = (Time.getNextTime(0, 0, 0) - System.currentTimeMillis()) / Time.MILLISECOND;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				ActivityPayManager.singlePayReset();
			}
		};

		midnightExec.scheduleAtFixedRate(runnable, start, Time.DAY_SECOND, TimeUnit.SECONDS);

	}

	public void stopMidnightExec() {
		if (midnightExec != null) {
			midnightExec.shutdown();
		}
	}

	public void startWeekExecExec() {

		weekExec = new ScheduledThreadPoolExecutor(1);

		long start = (Time.getDayOfNextWeek(2, 0, 0, 0) - System.currentTimeMillis()) / Time.MILLISECOND;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					ActivityPayManager.payAccumulateWeekReset();
					GangManager.instance().hadGetWelfare.clear();
					RedisGangWelfare redisGangWelfare = Spring.instance().getBean("redisGangWelfare", RedisGangWelfare.class);
					redisGangWelfare.deleteGangWelFare();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		weekExec.scheduleAtFixedRate(runnable, start, Time.DAY_SECOND, TimeUnit.SECONDS);

	}

	/**
	 * 每周五检查 还未使用
	 */
	public void startWeekExecExec5() {

		weekExec5 = new ScheduledThreadPoolExecutor(1);

		long start = (Time.getDayOfNextWeek(6, 0, 0, 0) - System.currentTimeMillis()) / Time.MILLISECOND;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO 每周五干什么

			}
		};

		weekExec5.scheduleAtFixedRate(runnable, start, Time.DAY_SECOND, TimeUnit.SECONDS);

	}

	/**
	 * 每周六检查 还未使用
	 */
	public void startWeekExecExec6() {

		weekExec6 = new ScheduledThreadPoolExecutor(1);

		long start = (Time.getDayOfNextWeek(7, 0, 0, 0) - System.currentTimeMillis()) / Time.MILLISECOND;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO 每周六干什么

			}
		};

		weekExec6.scheduleAtFixedRate(runnable, start, Time.DAY_SECOND, TimeUnit.SECONDS);

	}

	public void stopWeekExecExec() {
		if (weekExec != null) {
			weekExec.shutdown();
		}
	}

}
