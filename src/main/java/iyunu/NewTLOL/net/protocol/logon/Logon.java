package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.CompensateManager;
import iyunu.NewTLOL.manager.InformMamager;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Logon extends TLOLMessageHandler {

	public static void testCode(Role role) {
		// role.setLevel(80); // ======测试代码======
		// role.setFree(30); // ======测试代码======
		// role.setFreeSkill(24); // ======测试代码======
		// role.setWuHun(100000000); // ======测试代码======
		// RoleServer.fullHpAndMp(role); // ======测试代码======
		// RoleServer.addGold(role, 100000); // ======测试代码======
		// RoleServer.addMoney(role, 100000, EMoney.answer); // ======测试代码======
		// role.getBag().add(ItemJson.instance().getItem(30001), 1, new
		// HashMap<Integer, Cell>());// ======测试代码======薰衣草
		// role.getBag().add(ItemJson.instance().getItem(20040), 10, new
		// HashMap<Integer, Cell>());// ======测试代码======灵芝
		// 刷新重置聚魂次数
		// role.setJuHunNum(10); // 测试代码
		// 刷新收获聚魂次数
		// role.setReceiveJuhun(10); // 测试代码

	}

	private int result = 0; // 0.成功，1.无角色,，2.登录失败
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "登录成功";
		int serverId = msg.readInt("serverId");
		// ======验证服务器编号======
		if (!ServerManager.instance().checkServerId(serverId)) {
			result = 2;
			reason = "验证失败登录异常！服务器选择错误！";
			return;
		}

		if (!ServerManager.isRunning()) {
			result = 2;
			reason = "登录失败，服务器维护中！！！";
			return;
		}

		String userId = msg.readString("userId");
		// ======验证登录信息======
		if (!redisLogon.checkLogonInfo(userId)) {
			result = 2;
			reason = "验证失败登录异常！帐号不存在！";
			return;
		}

		long roleId = this.getRoleService().queryRoleId(userId, serverId);

		if (ServerManager.instance().isOnline(roleId)) { // 如果在线，则踢人，需要重新登录
			ServerManager.instance().offline(ServerManager.instance().getOnlinePlayer(roleId).getChannel(), "玩家下线，断开连接");
//			RoleManager.logout(ServerManager.instance().getOnlinePlayer(roleId), "上线踢人下线");
			result = 2;
			reason = "登录异常！请重新登录！";
			return;
		}

		// ======验证角色======
		if (roleId == 0) { // 角色不存在，返回创建角色消息
			result = 1;
			reason = "无角色";
			return;
		}

		Role role = roleService.query(roleId);
		if (role == null) {
			result = 1;
			reason = "登录失败！角色信息异常！";
			return;
		}

		if (role.getState() == 1) {
			result = 1;
			reason = "无角色";
			return;
		}

		int change = msg.readInt("change");
		roleService.initRole(role, change, channel);

		if (role.getState() == 2) {
			long time = role.getCancelBan();
			long now = System.currentTimeMillis();
			if (time > now) {
				result = 2;
				reason = "登录失败，由于您的不正当游戏，您的角色已被禁封，距离解封时间还剩" + ((time - now) / 1000 / 60 + 1) + "分钟";
				return;
			} else {
				role.setState(0);
			}

		} else if (role.getState() == 3) {
			result = 2;
			reason = "登录失败，由于您的不正当游戏，您的角色已被禁封";
			return;
		}
		// ======设置平台编号======
		String platform = msg.readString("platform");
		role.setPlatform(platform);

		online = role;

		testCode(online); // 测试
		LogManager.itemAllLog(role, 0); // 物品日志
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_logon");
			message.write("result", result);
			message.write("reason", reason);

			if (result == 0) {
				LogManager.logOut("角色登录====角色编号【" + online.getId() + "】角色名称【" + online.getNick() + "】");
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			RoleServer.sendLogonMsg(online);

			// ======检查首冲礼包通知======
			InformMamager.instance().checkPayFirst(online);
			// ======检查活跃度礼包通知======
			// InformMamager.instance().checkHelperAward(online);
			// ======检查补偿======
			CompensateManager.intance().compensate(online);

			// ======检查每日答题通知======
			InformMamager.instance().checkAnswer(online);
			// 刷新血战开始通知
			if (online.getBlood() == 0 && System.currentTimeMillis() >= ActivityManager.BLOOD_START_TIME && System.currentTimeMillis() < ActivityManager.BLOOD_END_TIME) {
				SendMessage.refreshBloodSend(online);
			}
			// ======检查在线奖励通知======
			InformMamager.instance().checkOnlineAward(online);
			// ======刷新出战伙伴======
			PartnerMessage.refreshFightPartner(online);
			// 检查矿区被抢通知
			ConcurrentHashMap<Long, List<String>> tipMap = MiningManger.instance().getTipMap();
			if (tipMap.containsKey(online.getId())) {
				List<String> tipList = tipMap.get(online.getId());
				for (String notice : tipList) {
					SendMessage.miningNotice(online, notice, online.getId());
				}
				tipMap.remove(online.getId());
			}
			// 检查可提升提示
			for (UptipEvent up : UptipEvent.values()) {
				if (up != UptipEvent.伙伴升级 && up != UptipEvent.伙伴进阶) {
					up.countBefore(online);
				}
			}
			// 发送可提升提示
			SendMessage.refreshUpTip(online);

			// 离线奖励
			int sendNum = (int) ((System.currentTimeMillis() - online.getLogoutTime()) / Time.DAY_MILLISECOND) * 5;
			sendNum = Util.matchBigger(0, sendNum);
			if (sendNum > 0) {
				sendNum = Util.matchSmaller(sendNum, 35);
				Item item = ItemJson.instance().getItem(21105);
				item.bind();
				MailServer.send(online.getId(), "系统邮件", "离线礼包", item, sendNum, 0, 0, 0, 0, null, "系统");
			}

		}
	}

}
