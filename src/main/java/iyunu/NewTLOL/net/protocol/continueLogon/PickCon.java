//package iyunu.NewTLOL.net.protocol.continueLogon;
//
//import iyunu.NewTLOL.json.ItemJson;
//import iyunu.NewTLOL.json.RoleJson;
//import iyunu.NewTLOL.manager.RoleManager;
//import iyunu.NewTLOL.model.item.Item;
//import iyunu.NewTLOL.model.monster.MonsterDropItem;
//import iyunu.NewTLOL.net.TLOLMessageHandler;
//import iyunu.NewTLOL.server.mail.MailServer;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.liteProto.LlpJava;
//import com.liteProto.LlpMessage;
//
///**
// * 领取签到奖励
// * 
// * @author fenghaiyu
// * 
// */
//public class PickCon extends TLOLMessageHandler {
//	private int result = 0;
//	private String reason = "";
//
//	@Override
//	public void handleReceived(LlpMessage msg) {
//		result = 0;
//		reason = "礼包领取成功!";
//		int pickId = msg.readInt("pickId");
//		if (!RoleJson.instance().getConDates().contains(pickId)) {
//			result = 1;
//			reason = "领取失败，不存在此登录礼包!";
//			return;
//		}
//		if (online.getMails().size() > RoleManager.MAX_MAIL) {
//			result = 1;
//			reason = "邮件已满，请先清理您的邮件!";
//			return;
//		}
//		if (!online.getConPick().contains(pickId)) {
//			result = 1;
//			reason = "领取失败，不符合条件!";
//			return;
//		}
//		online.getConPick().remove((Integer) pickId);
//		Map<Item, Integer> itemIds = new HashMap<Item, Integer>();
//		for (Iterator<Entry<Integer, MonsterDropItem>> it = RoleJson.instance().getContinueItem(pickId).entrySet().iterator(); it.hasNext();) {
//			Entry<Integer, MonsterDropItem> entry = it.next();
//			Item item = ItemJson.instance().getItem(entry.getKey());
//			item.setIsDeal(entry.getValue().getIsBind());
//			itemIds.put(item, entry.getValue().getNum());
//		}
//		MailServer.send(online.getId(), "连续登录礼包", "请查收！", itemIds, 0, 0, 0, 0, null);
//	}
//
//	@Override
//	public void handleReply() throws Exception {
//
//		LlpMessage message = null;
//		try {
//			message = LlpJava.instance().getMessage("s_pickCon");
//			message.write("result", result);
//			message.write("reason", reason);
//			if (result == 0) {
//				for (int i = 0; i < online.getConPick().size(); i++) {
//					message.write("conPick", online.getConPick().get(i).intValue());
//				}
//			}
//			channel.write(message);
//		} finally {
//			if (message != null) {
//				message.destory();
//			}
//		}
//	}
//}
