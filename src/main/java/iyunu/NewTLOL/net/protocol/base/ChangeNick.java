package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ChangeNick extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "改名成功";

		String nick = msg.readString("nick");

		// 检查名称是否过长
		int nickLength = nick.length();
		if (nickLength < 2 || nickLength > 6) {
			reason = "角色名称长度过" + (nickLength < 2 ? "短" : "长") + "！";
			result = 1;
			return;
		}

		// 检查名字是否由汉字，字母，数字组成
		if (!IlllegalWordManager.match(nick)) {
			reason = "角色名称只能由汉字，字母，数字组成";
			result = 1;
			return;
		}

		// 检查是否有非法字符
		String str = IlllegalWordManager.instance().existStr(nick);
		if (str != null) {
			reason = "角色名包含非法字符[" + str + "]，请您更换！";
			result = 1;
			return;
		}

		int index = online.getBag().isInBagByType(EItem.gaiming);
		if (index == -1) {
			result = 1;
			reason = "改名卡不足，无法改名";
			return;
		}

		if (ServerManager.instance().checkNick(nick) || this.getRoleService().checkNick(nick)) {
			result = 1;
			reason = "角色名已存在";
			return;
		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		if (!online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.gaiming)) {
			result = 1;
			reason = "改名卡不足，无法改名";
			return;
		}
		nick = "s" + ServerManager.instance().getSrvId() + "." + nick;
		String oldNick = online.getNick();
		online.setNick(nick);
		LogManager.gaiming(oldNick, online);
		BagMessage.sendBag(online, cellsMap, false);
		if (RoleManager.roleCardMap.containsKey(online.getId())) {
			RoleCard roleCard = RoleManager.roleCardMap.get(online.getId());
			roleCard.setNick(nick);
		}
		MapManager.instance().addBaseQueue(online);

		// 更改帮派中的帮主名字
		if (online.getGang() != null) {
			if (online.getGang().getLeader() == online.getId()) {
				online.getGang().setLeaderName(online.getNick());
			}
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_changeNick");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		SendMessage.refreshNick(online);
	}
}