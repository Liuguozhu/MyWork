package iyunu.NewTLOL.main;

import iyunu.NewTLOL.base.mx4j.HttpAdaptor;
import iyunu.NewTLOL.base.mx4j.NewTLOL;
import iyunu.NewTLOL.base.net.NetService;
import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.util.log.LogManager;

import java.net.MalformedURLException;
import java.util.Date;

import javax.management.JMException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		// 加载配置文件
		TLOLManager.initConf();
		// 初始化Spring
		Spring.instance().init("protocol.xml", "listeners.xml", "services.xml", "dao.xml", "common.xml", "db_oracle.xml", "netty.xml", "redis.xml", "properties.xml", "time.xml");
		// MX4J
		NewTLOL tlolManager = new NewTLOL();
		HttpAdaptor adaptor = new HttpAdaptor(tlolManager, ServerManager.MX4J_URL, ServerManager.MX4J_PORT);

		try {
			adaptor.start(); // 适配器启动
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JMException e) {
			e.printStackTrace();
		}

		// 游戏启动
		NetService netService = Spring.instance().getBean("netService", NetService.class);
		netService.run();
		LogManager.info("NetService started！" + new Date().toString());

		// 服务器启动
		ServerManager.startup();
		LogManager.info("Server started！" + new Date().toString());

		LogManager.server("游戏服务器开启");
	}
}
