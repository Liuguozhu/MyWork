package iyunu.NewTLOL.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Time {

	/**
	 * 私有构造方法
	 */
	private Time() {

	}

	/** 秒转为毫秒 **/
	public static final int MILLISECOND = 1000;
	/** 秒转为毫秒 **/
	public static final long MILLISECOND_L = 1000L;
	/** 秒转为毫秒 **/
	public static final float MILLISECOND_F = 1000f;
	/** 分转为毫秒 **/
	public static final long MINUTE_MILLISECOND = 60 * 1000;
	/** 分转为秒 **/
	public static final int MINUTE_SECOND = 60;
	/** 分转为秒 **/
	public static final float MINUTE_SECOND_F = 60f;
	/** 时转为分 **/
	public static final int HOUR_MINUTE = 60;
	/** 时转为毫秒 **/
	public static final int HOUR_MILLISECOND = 60 * 60 * 1000;
	/** 时转为秒 **/
	public static final int HOUR_SECOND = 60 * 60;
	/** 天转为毫秒 **/
	public static final long DAY_MILLISECOND = 24 * 60 * 60 * 1000;
	/** 天转为秒 **/
	public static final long DAY_SECOND = 24 * 60 * 60;
	/** 24小时 **/
	public static final long DAY_HOUR = 24;
	/** 7天 **/
	public static final long WEEK_DAY = 7;
	/** 天数每年 **/
	public static final int DAYS_YEAR = 365;

	/**
	 * long的时间能转成 多少时 多少分 多少秒 时:分:秒
	 * 
	 * @param time
	 * @return
	 */
	public static String longTimeToString(long time) {
		long sec = time % 60;
		long allmin = time / 60;
		long min = allmin % 60;
		long hour = allmin / 60;

		return (hour / 10 == 0 ? "0" + hour : hour) + ":" + (min / 10 == 0 ? "0" + min : min) + ":" + (sec / 10 == 0 ? "0" + sec : sec);
	}

	/**
	 * 拍卖行剩余时间
	 * 
	 * @param time
	 *            到期时间
	 * @param now
	 *            当前时间
	 * @return 剩余时间
	 */
	public static String timeOut(long time, long now) {
		long out = (time - now) / HOUR_MILLISECOND;
		if (out < 1) {
			return "很短";
		} else if (out < 6) {
			return "短";
		} else if (out < 12) {
			return "长";
		} else {
			return "很长";
		}
	}

	/**
	 * 获取时
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 时
	 */
	public static int getHour(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 分
	 */
	public static int getMinute(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 秒
	 */
	public static int getSecond(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取时
	 * 
	 * @param time
	 *            时间差（秒）
	 * @return 时
	 */
	public static int getHour(final int time) {
		if (time <= 0) {
			return 0;
		}
		return time / HOUR_SECOND;
	}

	/**
	 * 获取分
	 * 
	 * @param time
	 *            时间差（秒）
	 * @return 分
	 */
	public static int getMinute(final int time) {
		if (time <= 0) {
			return 0;
		}
		return time % HOUR_SECOND / MINUTE_SECOND;
	}

	/**
	 * 获取秒
	 * 
	 * @param time
	 *            时间差（秒）
	 * @return 秒
	 */
	public static int getSecond(final int time) {
		if (time <= 0) {
			return 0;
		}
		return time % MINUTE_SECOND;
	}

	/**
	 * 获取剩余时间字符串
	 * 
	 * @param time
	 *            时间（秒）
	 * @return 剩余时间字符串
	 */
	public static String getTimeStr(final int time) {
		int hour = getHour(time);
		int minute = getMinute(time);
		int second = getSecond(time);
		if (hour != 0) {
			return hour + "小时" + minute + "分" + second + "秒";
		}
		if (minute != 0) {
			return minute + "分" + second + "秒";
		}
		return second + "秒";
	}

	/**
	 * 获取时间字符串
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 时间字符串
	 */
	public static String getTimeToStr(final long time) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取时间字符串
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 时间字符串
	 */
	public static String getTimeToTimeStr(final long time) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获得一定规则的字符串时间
	 * 
	 * @param time
	 *            时间（毫秒）
	 * @return 字符串时间
	 */
	public static String getStrTime(final long time) {
		int hour = getHour(time);
		DateFormat formatter = null;
		if (hour == 0) {
			formatter = new SimpleDateFormat("mm:ss");
		} else {
			formatter = new SimpleDateFormat("HH:mm");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 将字符串转为时间
	 * 
	 * @param str
	 *            字符串
	 * @return 时间
	 */
	public static long getStrToTime(final String str) {
		if (str == null) {
			return -1;
		}
		Calendar c1 = Calendar.getInstance();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			c1.setTime(sd.parse(str));
		} catch (ParseException e) {
			System.out.println("输入的日期格式有误！");
			return -1;
		}
		return c1.getTimeInMillis();
	}

	/**
	 * 获取下一个指定星期的long
	 * 
	 * @param day
	 *            星期
	 * @return 时间long
	 */
	public static long getDayOfWeek(final int day) {
		long time = 0;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_WEEK, day);
		String monday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(monday);
			cal.setTime(date);
			time = cal.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 获取下一天
	 * 
	 * @param now
	 *            当前时间
	 * @return 下一天
	 */
	public static long getDayOfTomorrow(long now) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now);
		int number = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, number + 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 判断某时间和当前时间，是否跨越了某一特定小时 这功能当前为 帮派任务跨越 0 6 12 18点而做
	 * 
	 * @param now
	 *            当前时间
	 * @return 下一天
	 */
	public static boolean getIfOverTime(long time, int... hour) {
		long now = System.currentTimeMillis();
		// 传入时间大于现在，说明传入时间有问题，这里给予刷新
		if (time >= now) {
			return true;
		}

		Calendar calTime = Calendar.getInstance();
		calTime.setTimeInMillis(time);
		int timeDay = calTime.get(Calendar.DAY_OF_YEAR);
		int timeHour = calTime.get(Calendar.HOUR_OF_DAY);
		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(now);
		int nowDay = calNow.get(Calendar.DAY_OF_YEAR);
		int nowHour = calNow.get(Calendar.HOUR_OF_DAY);
		// 如果不在同一年的情况属于不在同一天，所以只判断天
		// 如果停服的那天后面还有时间点，或者今天大于时间点就要刷新
		if (timeDay != nowDay) {
			for (int i = 0; i < hour.length; i++) {
				if (nowHour >= hour[i] || timeHour <= hour[i]) {
					return true;
				}
			}
		} else {
			// 同一天，对于每个时间点， 停服时间小于时间点，开服时间大于等于时间点的小时
			for (int i = 0; i < hour.length; i++) {
				if (hour[i] > timeHour && hour[i] <= nowHour) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取下个指定时间
	 * 
	 * @param hour
	 *            时
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @return 时间
	 */
	public static long getNextTime(int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		if (cal.get(Calendar.HOUR_OF_DAY) > hour) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else if (cal.get(Calendar.HOUR_OF_DAY) == hour && cal.get(Calendar.MINUTE) >= min) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getNextTime(long time, int hour, int min, int sec, int... withOut) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		if (cal.get(Calendar.HOUR_OF_DAY) > hour) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else if (cal.get(Calendar.HOUR_OF_DAY) == hour && cal.get(Calendar.MINUTE) >= min) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		for (int i : withOut) {
			if (i == week) {
				return getNextTime(cal.getTimeInMillis(), hour, min, sec, withOut);
			}
		}

		return cal.getTimeInMillis();
	}

	/**
	 * @function 获取指定时间的下一个指定时间
	 * @author LuoSR
	 * @param time
	 *            指定时间
	 * @param dayNum
	 *            间隔几天
	 * @param hour
	 *            是
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @param withOut
	 *            其他
	 * @return 时间
	 * @date 2014年9月17日
	 */
	public static long getNextTime(long time, int dayNum, int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		if (cal.get(Calendar.HOUR_OF_DAY) > hour) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + dayNum);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else if (cal.get(Calendar.HOUR_OF_DAY) == hour && cal.get(Calendar.MINUTE) >= min) {
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + dayNum);
			cal.set(Calendar.HOUR_OF_DAY, hour);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 下一个指定星期、时、分、秒的时间
	 * 
	 * @param dayOfWeek
	 * @param hour
	 * @param min
	 * @param sec
	 * @return
	 */
	public static long getNextDayOfWeek(int dayOfWeek, int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		long now = System.currentTimeMillis();

		cal.setTimeInMillis(now);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week > dayOfWeek) {

			cal.add(Calendar.DATE, Calendar.DAY_OF_WEEK);
			cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			cal.set(Calendar.SECOND, sec);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTimeInMillis();

		} else if (week < dayOfWeek) {

			cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			cal.set(Calendar.SECOND, sec);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTimeInMillis();

		} else {

			if (cal.get(Calendar.HOUR_OF_DAY) > hour) {

				cal.add(Calendar.DATE, Calendar.DAY_OF_WEEK);
				cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, min);
				cal.set(Calendar.SECOND, sec);
				cal.set(Calendar.MILLISECOND, 0);
				return cal.getTimeInMillis();

			} else if (cal.get(Calendar.HOUR_OF_DAY) < hour) {

				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, min);
				cal.set(Calendar.SECOND, sec);
				cal.set(Calendar.MILLISECOND, 0);
				return cal.getTimeInMillis();

			} else {

				if (cal.get(Calendar.MINUTE) > min) {

					cal.add(Calendar.DATE, Calendar.DAY_OF_WEEK);
					cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
					cal.set(Calendar.HOUR_OF_DAY, hour);
					cal.set(Calendar.MINUTE, min);
					cal.set(Calendar.SECOND, sec);
					cal.set(Calendar.MILLISECOND, 0);
					return cal.getTimeInMillis();

				} else if (cal.get(Calendar.MINUTE) < min) {

					cal.set(Calendar.MINUTE, min);
					cal.set(Calendar.SECOND, sec);
					cal.set(Calendar.MILLISECOND, 0);
					return cal.getTimeInMillis();

				} else {

					if (cal.get(Calendar.SECOND) > sec) {

						cal.add(Calendar.DATE, Calendar.DAY_OF_WEEK);
						cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
						cal.set(Calendar.HOUR_OF_DAY, hour);
						cal.set(Calendar.MINUTE, min);
						cal.set(Calendar.SECOND, sec);
						cal.set(Calendar.MILLISECOND, 0);
						return cal.getTimeInMillis();

					} else {

						cal.set(Calendar.SECOND, sec);
						cal.set(Calendar.MILLISECOND, 0);
						return cal.getTimeInMillis();

					}
				}
			}
		}

	}

	public static long getThisDayOfWeek(int dayOfWeek, int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getDayOfNextWeek(int dayOfWeek, int hour, int min, int sec) {
		return getThisDayOfWeek(dayOfWeek, hour, min, sec) + Time.DAY_MILLISECOND * Time.WEEK_DAY;
	}

	public static long getTodayTime(int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getTodayTime(long time, int hour, int min, int sec, int... without) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		for (int i : without) {
			if (i == week) {
				return getNextTime(cal.getTimeInMillis(), hour, min, sec, without);
			}
		}

		return cal.getTimeInMillis();
	}

	/**
	 * 验证时间是否是昨天以前
	 * 
	 * @param time
	 *            时间
	 * @return 昨天
	 */
	public static boolean beforeYesterday(final long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(System.currentTimeMillis());

		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			return true;
		}
		if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证时间正好是昨天
	 * 
	 * @param time
	 *            时间
	 * @return 是否是昨天
	 */
	public static boolean isYesterday(final long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(System.currentTimeMillis());
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			if (c2.get(Calendar.DAY_OF_YEAR) == 1 && c1.get(Calendar.DAY_OF_YEAR) >= 365) {
				return true;
			} else {
				return false;
			}
		}
		if (c1.get(Calendar.DAY_OF_YEAR) + 1 == c2.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}

	/**
	 * 计算今天与指定时间相差天数
	 * 
	 * @param time
	 *            指定时间
	 * @return 相差天数
	 */
	public static int getDaysBetween(final long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(System.currentTimeMillis());

		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {

			int year = c1.get(Calendar.YEAR);
			int sum = 365;
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
				sum = 366;
			}

			return sum - c1.get(Calendar.DAY_OF_YEAR) + 1 + c2.get(Calendar.DAY_OF_YEAR);
		}

		return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);

	}

	/**
	 * 验证时间是否是上周
	 * 
	 * @param time
	 *            时间
	 * @return 上周
	 */
	public static boolean lastWeek(final long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(System.currentTimeMillis());

		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) + c2.get(Calendar.DAY_OF_YEAR) >= DAYS_YEAR) {
			return true;
		}
		if (c1.get(Calendar.WEEK_OF_YEAR) < c2.get(Calendar.WEEK_OF_YEAR)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否在指定上周星期
	 * 
	 * @param time
	 *            日期
	 * @param week
	 *            星期（1.周日，2.周一，3.周二，4.周三，5.周四，6.周五，7.周六）
	 * @return
	 */
	public static boolean lastDayOfWeek(long time, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.DAY_OF_WEEK, week);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long lastTime = cal.getTimeInMillis() - Time.DAY_MILLISECOND * 7;
		if (time < lastTime) {
			return true;
		}
		return false;
	}

	/**
	 * 获取日期字符串
	 * 
	 * @param time
	 *            时间
	 * @return 日期字符串
	 */
	public static String getDateStr(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日";
	}

	/**
	 * 获取日期字符串
	 * 
	 * @param time
	 *            时间
	 * @return 日期字符串
	 */
	public static String getDateMDStr(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日";
	}

	/**
	 * 获取日期字符串
	 * 
	 * @param time
	 *            时间
	 * @return 日期字符串
	 */
	public static String getTimeStr(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日" + c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分";
	}

	/**
	 * 获取日期字符串
	 * 
	 * @param time
	 *            时间
	 * @return 日期字符串
	 */
	public static String getDayTimeStr(final long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00";
	}

	/**
	 * Date转成字符串
	 * 
	 * @param date
	 *            日期
	 * @return 字符串
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * Date转成字符串
	 * 
	 * @param date
	 *            日期
	 * @return 字符串
	 */
	public static String dateToTimeString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 日期类型转换成时间long型
	 * 
	 * @param date
	 *            日期
	 * @return Long型时间
	 */
	public static long dateToLong(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.getTimeInMillis();
	}

	public static String matchTime(int hour, int min) {
		String str = "";
		if (hour < 10) {
			str += "0" + hour;
		} else {
			str += hour;
		}
		str += ":";

		if (min < 10) {
			str += "0" + min;
		} else {
			str += min;
		}
		return str;
	}

	public static long getTime(int hour, int min, int sec) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 获取星期
	 * 
	 * @return 星期
	 */
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

}
