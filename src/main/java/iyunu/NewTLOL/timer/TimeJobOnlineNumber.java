package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 在线人数日志
 * 
 * @author SunHonglei
 * 
 */
public class TimeJobOnlineNumber {

	public void excute() {
		LogManager.onlineCounter(ServerManager.instance().getOnlineCounter()); // 在线人数日志记录
	}
}