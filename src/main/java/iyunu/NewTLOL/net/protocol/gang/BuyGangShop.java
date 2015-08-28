package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpMessage;

/**
 * 暂时无用
 * @function 帮派商店购买
 */
public class BuyGangShop extends TLOLMessageHandler {

//	private int result = 0;
//	private String reason = "购买成功";
//	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
//		LogManager.logOut("进入帮派商店购买");
//
//		result = 0;
//		reason = "购买成功";
//		cellsMap.clear();
//
//		int index = msg.readInt("index");
//		int number = msg.readInt("number");
//		if (online.getGangId() == 0) {
//			result = 1;
//			reason = "操作失败,无帮派！";
//			return;
//		}
//
//		if (number <= CommonConst.INT_ZORE) {
//			result = 1;
//			reason = "请选择物品数量！";
//			return;
//		}
//
//		// 检查帮派是否解散
//		if (!GangManager.instance().map.containsKey(online.getGangId())) {
//
//			ChatServer.sendSystem(online, "帮派系统", "您所在的帮派已解散！");
//			online.setGangId(0);
//			online.setJobTitle(GangJobTitle.NULL);
//			online.setTribute(0);
//			GangMessage.sendJoinGang(online); // 刷新角色帮派信息
//
//			result = 1;
//			reason = "操作失败,无帮派！";
//			return;
//		}
//
//		GangShop gangShop = GangJson.instance().getShopByIndex(index);
//		Item item = ItemJson.instance().getItem(gangShop.getItemId());
//
//		if (online.getBag().isFull((number - 1) / item.getMax() + 1)) {
//			result = 1;
//			reason = "您的背包空间不足！";
//			return;
//		}
//
//		int costTribute = gangShop.getPrice() * number;
//		if (!RoleServer.costTribute(online, costTribute)) {
//			result = 1;
//			reason = "您的帮贡数量不足！";
//			return;
//		}
//		if (gangShop.getLevel() > GangManager.instance().getGang(online.getGangId()).getLevel()) {
//			result = 1;
//			reason = "帮派等级未达到！";
//			return;
//		}
//
//		// 添加商店物品
//		online.getBag().add(item, number, cellsMap);
//		// 购买增加帮派活跃值
//		Gang gang = GangManager.instance().getMap().get(online.getGangId());
//		gang.addActive(EGangActiveEvent.商店兑换.getActive(), EGangActiveEvent.商店兑换.name(), online.getNick());
//		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), item.getId(), item.getName(), 0, costTribute, EGang.帮派商店购买.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
//		LlpMessage message = null;
//
//		try {
//			message = LlpJava.instance().getMessage("s_buyGangShop");
//			message.write("result", result);
//			message.write("reason", reason);
//			BagMessage.sendBag(online, cellsMap);
//			channel.write(message);
//		} finally {
//			if (message != null) {
//				message.destory();
//			}
//		}

	}

}
