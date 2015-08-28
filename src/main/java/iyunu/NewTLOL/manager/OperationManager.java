package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Translate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 活动
 * 
 * @author SunHonglei
 * 
 */
public class OperationManager {

	public static long PAY_START = 0;
	public static long PAY_END = 0;
	public static long ONLINE_START = 0;
	public static long ONLINE_END = 0;
	public static long NEW_AWARD_END = 0;
	public static long OPEN_FU = 0;
	public static boolean PAY_BACK = false;

	/**
	 * 活动信息初始化
	 */
	public static void init() {

		try {
			InputStream in = new FileInputStream("./conf/operation.properties");
			Properties p = new Properties();
			p.load(in);

			PAY_START = Time.getStrToTime(p.getProperty("payStart"));
			PAY_END = Time.getStrToTime(p.getProperty("payEnd"));
			ONLINE_START = Time.getStrToTime(p.getProperty("onlineStart"));
			ONLINE_END = Time.getStrToTime(p.getProperty("onlineEnd"));
			NEW_AWARD_END = Time.getStrToTime(p.getProperty("newAwardEnd"));
			OPEN_FU = Time.getStrToTime(p.getProperty("openFu"));
			PAY_BACK = Translate.stringToBoolean(p.getProperty("payBack"));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
