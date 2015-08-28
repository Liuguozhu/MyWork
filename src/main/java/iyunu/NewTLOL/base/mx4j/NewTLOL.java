package iyunu.NewTLOL.base.mx4j;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.json.IlllegalWordJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PayJson;
import iyunu.NewTLOL.main.TLOLManager;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.CdKeyManager;
import iyunu.NewTLOL.manager.CompensateManager;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.activity.PayExchangeTime;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.service.iface.payActivity.PayActivityService;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTLOL implements NewTLOLMBean {

	protected final void startService() throws Exception {
	}

	@Override
	public final void stopService(String pwd) {
		if (pwd != null && pwd.equals("123456654321")) {
			ServerManager.shutdown();
			LogManager.server("【后台命令】游戏服务器关闭");
		} else {
			LogManager.server("【后台命令】游戏服务器关闭密码错误！！！");
		}
		LogManager.server("【后台命令】停服命令：" + pwd);
	}

	@Override
	public final void execute(final String command) {
		LogManager.server("【后台命令】游戏服务器命令：" + command);
		if (command != null && !"".equals(command)) {
			Command.excute(command);
		}
	}

	@Override
	public void reloadJson() {
		try {
			TLOLManager.initJson();
			LogManager.server("【后台命令】重新加载脚本");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadIlllegalWord() {
		try {
			IlllegalWordJson.instance().init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogManager.server("【后台命令】重新屏蔽文字");
	}

	@Override
	public void sendMail(long roleId) {
		if (ServerManager.instance().isOnline(roleId)) {
			MailServer.send(roleId, "系统邮件", "测试邮件", null, 50000, 600000, 400000, 0, null);
		}
		LogManager.server("【后台命令】发送邮件");
	}

	@Override
	public void sendPayInfo(long roleId, int rmb, String orderId, String state) {
		LogManager.info("【后台命令】充值返回消息");
		if (state.equals("success")) {
			int money = PayManager.instance().countMoney(rmb);
			if (ServerManager.instance().isOnline(roleId)) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);

				role.getVip().addPayScore(money); // 增加充值积分

				// ========= 首充双倍============================
				if (PayJson.instance().getPayInfoMap().containsKey(rmb) && !role.getFirstDouble().contains(rmb)) {
					money = money * 2;
					role.getFirstDouble().add(rmb);
					SendMessage.refreshFirstDouble(role);
				}
				// ========= 首充双倍=============================
				RoleServer.addMoney(role, money, EMoney.pay);

				String content = "恭喜您充值" + rmb + "元成功，获得" + money + "元宝，" + "订单号：" + orderId;
				MailServer.send(roleId, "系统邮件", content, null, 0, 0, 0, 0, null);

				AwardMessage.sendPayFristState(role); // 刷首冲领奖状态
				// 加累积充值
				role.setPlusMoney(role.getPlusMoney() + rmb * 10);
				SendMessage.refreshPlusMoney(role);// 刷新累计充值
				if (role.getVip().payState() == 1) {
					SendMessage.payInform(role, 0);
				}

				if (role.getVip().payState() == 2 || role.getVip().payState() == 0) {
					SendMessage.payInform(role, 1);
				}
				// ==========日常活动之充值=========
				try {
					List<Integer> dailyPay = DailyManager.instance().getDailyPay();
					for (Integer dailyId : dailyPay) {
						if (DailyManager.instance().checkEvent(dailyId)) {
							DailyManager.instance().finishDaily(role.getDailyMap().get(dailyId), role, rmb);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 不在线，增加元宝和充值积分
				RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);

				Role role = roleService.query(roleId);

				if (role != null) {

					role.getVip().addPayScore(money);
					// ========= 首充双倍============================
					if (PayJson.instance().getPayInfoMap().containsKey(rmb) && !role.getFirstDouble().contains(rmb)) {
						money = money * 2;
						role.getFirstDouble().add(rmb);
					}
					// ========= 首充双倍=============================
					// 加累积充值
					role.setPlusMoney(role.getPlusMoney() + rmb * 10);

					RoleServer.addMoney(role, money, EMoney.pay);

					String content = "恭喜您充值" + rmb + "元成功，获得" + money + "元宝，" + "订单号：" + orderId;
					MailServer.send(roleId, "系统邮件", content, null, 0, 0, 0, 0, null);

					// ==========日常活动之充值=========
					try {
						List<Integer> dailyPay = DailyManager.instance().getDailyPay();
						for (Integer dailyId : dailyPay) {
							if (DailyManager.instance().checkEvent(dailyId)) {
								DailyManager.instance().finishDaily(role.getDailyMap().get(dailyId), role, rmb);
								roleService.updateDailyPay(role);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					roleService.updateMoney(role);
					roleService.updateVip(role);
					roleService.updatePlusMoney(role);
					roleService.updateFirstDouble(role);
				}
			}
			ActivityPayManager.payRecord(roleId, rmb);
		} else if (state.equals("fail")) {
			String content = "充值失败，谢谢您对游戏的支持。" + "订单号：" + orderId;
			MailServer.send(roleId, "系统邮件", content, null, 0, 0, 0, 0, null);
		}
	}

	@Override
	public void reloadBulletin() {
		BulletinManager.instance().init();
		LogManager.server("【后台命令】重新加载公告");
	}

	@Override
	public void reloadLogonBulletin() {
		BulletinManager.instance().initLogonBulletin();
		LogManager.server("【后台命令】重新加载登录公告");
	}

	@Override
	public void addItemOnMap(int mapId, int x, int y, int itemId) {
		BaseMap baseMap = MapManager.instance().getMapById(mapId);
		if (baseMap != null) {
			baseMap.addItem(x, y, itemId);
		}
		LogManager.server("【后台命令】地图上添加物品");
	}

	@Override
	public void addMonsterOnMap(int mapId, int x, int y, int monsterGroupId) {
		BaseMap baseMap = MapManager.instance().getMapById(mapId);
		if (baseMap != null) {
			baseMap.addMonster(x, y, monsterGroupId, EMonsterOnMap.none);
		}
		LogManager.server("【后台命令】地图上添加怪物");
	}

	@Override
	public void logManager(boolean open) {
		LogManager.isLlpLog = open;
		LogManager.server("开启服务器日志：" + open);
	}

	@Override
	public String getOnlineNumber(String s) {
		int num = ServerManager.instance().getOnlineCounter();
		String result = "huoyunxieshen" + num + "huoyunxieshen";
		LogManager.server("【后台命令】当前在线人数：" + num);
		return result;
	}

	@Override
	public void sendMail(String ids, String title, String content, String items, int gold, int coin, int money, int exp) {
		try {

			if (title == null || "".equals(title)) {
				title = "系统邮件";
			}
			if (content == null || "".equals(content)) {
				content = "请查收！";
			}

			String[] itemss = null;
			if (items != null && !"".equals(items)) {
				itemss = items.split(";");
			}

			Map<Item, Integer> map = new HashMap<Item, Integer>();
			if (itemss != null && itemss.length > 0) {
				for (int j = 0; j < itemss.length; j++) {
					Item item = ItemJson.instance().getItem(Integer.parseInt(itemss[j].split(",")[0]));
					item.setIsDeal(Integer.parseInt(itemss[j].split(",")[2]));
					map.put(item, Integer.parseInt(itemss[j].split(",")[1]));
				}
			}
			String[] roleids = ids.split(",");
			for (int i = 0; i < roleids.length; i++) {
				MailServer.send(Long.parseLong(roleids[i]), title, content, map, gold, coin, money, exp, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogManager.server("【后台命令】发送邮件");
	}

	@Override
	public void sendWordTalk(String content) {
		LogManager.server("【后台命令】发送系统公告");
		BulletinManager.instance().addBulletinChat(content);
	}

	@Override
	public void sendRockBulletin(String content, int num) {
		LogManager.server("【后台命令】发送滚屏公告");
		BulletinManager.instance().addBulletinRock(content, num);
	}

	@Override
	public void reloadConf(int password) {
		LogManager.server("【后台命令】重新加载===>配置文件");
		if (password == 159357) {
			TLOLManager.initConf();
		}
	}

	@Override
	public void reloadCompensate(int password) {
		LogManager.server("【后台命令】重新加载===>补偿");
		if (password == 159357) {
			CompensateManager.intance().init();
		}
	}

	@Override
	public void reloadCdKey(int password) {
		LogManager.server("【后台命令】重新加载===>CDK");
		if (password == 159357) {
			CdKeyManager.instance().init();
		}
	}

	@Override
	public void allDownAuction(int password) {
		LogManager.server("【后台命令】拍卖行下架");
		if (password == 159357) {
			AuctionManager.instance().downByAdmin();
		}

	}

	@Override
	public void reloadPayActivity(int pwd) {
		LogManager.server("【后台命令】重新加载===>充值活动");
		if (pwd == 159357) {
			ActivityPayJson.instance().init();
		}

	}

	/**
	 * 全部在线的人，发礼包 .物品格式为 itemId,num,bind; itemId,num,bind;
	 * 
	 * */
	@Override
	public void allOnlinesendMail(String title, String content, String items, int gold, int coin, int money, int exp) {
		if (title == null || "".equals(title)) {
			title = "系统邮件";
		}
		if (content == null || "".equals(content)) {
			content = "请查收！";
		}
		String[] itemss = null;

		if (items != null && !"".equals(items)) {
			itemss = items.split(";");
		}
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		if (itemss != null && itemss.length > 0) {
			for (int j = 0; j < itemss.length; j++) {
				Item item = ItemJson.instance().getItem(Integer.parseInt(itemss[j].split(",")[0]));
				item.setIsDeal(Integer.parseInt(itemss[j].split(",")[2]));
				map.put(item, Integer.parseInt(itemss[j].split(",")[1]));
			}
		}
		for (Long roleId : ServerManager.instance().getOnlineRoleId()) {
			MailServer.send(roleId, title, content, map, gold, coin, money, exp, null);
		}
		LogManager.server("【后台命令】全服发送邮件");
	}

	@Override
	public String reloadJiFengBangTime() {
		LogManager.server("【后台命令】重新加载积分榜");
		PayActivityService payActivityService = Spring.instance().getBean("payActivityService", PayActivityService.class);
		PayExchangeTime p = payActivityService.queryPayExchangeTime();
		if (p == null || p.getStartTime() == null) {
			PayExchangeManager.STARTTIME = PayExchangeManager.ENDTIME = System.currentTimeMillis() - 1000;
			return "fail";
		} else {
			PayExchangeManager.STARTTIME = p.getStartTime().getTime();
			PayExchangeManager.ENDTIME = p.getEndTime().getTime();
			return "success";
		}

	}

	@Override
	public void reloadDaily(int pwd) {
		LogManager.server("【后台命令】重新加载每日活动");
		DailyManager.instance().load(2);
	}
}
