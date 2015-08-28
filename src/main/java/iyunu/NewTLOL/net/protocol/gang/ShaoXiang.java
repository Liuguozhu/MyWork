package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.event.EGangTributeEvent;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 烧香
 * 
 * @author fenghaiyu
 * 
 */
public class ShaoXiang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "操作成功！";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "操作成功！";

		int type = msg.readInt("type");

		if (online.getGangId() == 0) {
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		Gang gang = GangManager.instance().getGang(online.getGangId());
		if (gang == null) {
			Chat chat = new Chat(EChat.system, 0, "帮派", "您所在的帮派已解散！");
			ChatMessage.sendChat(online, chat);
			online.setGangId(0);
//			online.setJobTitle(GangJobTitle.NULL);
			online.setTribute(0);
			online.setTotalTribute(0);
			result = 1;
			reason = "操作失败,无帮派！";
			return;
		}

		if (online.getShaoXiangNum() >= GangManager.SHAOXIANG_LIMIT + online.getVip().getLevel().getShaoxiangAdd()) {
			result = 1;
			reason = "操作失败，次数不足！";
			return;
		}

		switch (type) {
		case 1:

			int index = online.getBag().isInBagByType(EItem.juanzong, 1);
			if (index == -1) {
				result = 1;
				reason = "没有卷宗！";
				return;
			}
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.shaoxiang);
			BagMessage.sendBag(online, cellsMap);
			GangServer.count(online, gang, EGangTributeEvent.贡酒);
			break;
		case 2:
			if (!RoleServer.costCoin(online, 2000, EGold.shaoXiang)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}
			GangServer.count(online, gang, EGangTributeEvent.香炉);
			break;
		case 3:
			if (!RoleServer.costMoney(online, 30, EMoney.shaoXiang)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;
			}
			GangServer.count(online, gang, EGangTributeEvent.烧鸡);
			break;
		default:
			result = 1;
			reason = "操作失败！";
			return;
		}
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), type, "", 0, 0, EGang.烧香.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_shaoXiang");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
