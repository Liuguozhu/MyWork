package iyunu.NewTLOL.net.protocol.levelGift;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gift.instance.LevelGift;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.GiftDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 领取礼包
 * @author LuoSR
 * @date 2014年4月29日
 */
public class GetLevelGift extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private int level = 0;

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "领取成功";
		cellsMap.clear();

		level = msg.readInt("level");

		if (online.getLevel() < level) {
			result = 1;
			reason = "等级不足 ！";
			return;
		}

		if (online.getLevelGiftStateMap().get(level) == 0) {
			result = 1;
			reason = "不可领取";
			return;
		}

		if (online.getLevelGiftStateMap().get(level) == 2) {
			result = 1;
			reason = "已领取";
			return;
		}

		if (online.getMails().size() > RoleManager.MAX_MAIL) {
			result = 1;
			reason = "邮件已满，请先清理您的邮件!";
			return;
		}

		LevelGift levelGift = GiftJson.instance().getLevelGiftMap().get(level);

		Map<Item, Integer> itemMap = new HashMap<Item, Integer>();

		for (GiftDropItem monsterDropItem : levelGift.getItems()) {
			Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
			item.setIsDeal(monsterDropItem.getIsBind());
			itemMap.put(item, monsterDropItem.getNum());
		}

		// 修改礼包状态
		online.getLevelGiftStateMap().put(level, 2);
		// 邮件下发
		MailServer.send(online.getId(), "等级礼包", "等级礼包奖励", itemMap, 0, 0, 0, 0, null);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getLevelGift");

			message.write("result", result);
			message.write("reason", reason);

			if (result == 0) {
				message.write("level", level);
				message.write("state", online.getLevelGiftStateMap().get(level));

				// System.out.println("level=" + level + "     state=" +
				// online.getLevelGiftStateMap().get(level));
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
