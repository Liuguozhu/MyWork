package iyunu.NewTLOL.net.protocol.levelGift;

import iyunu.NewTLOL.json.GiftJson;
import iyunu.NewTLOL.model.gift.instance.LevelGift;
import iyunu.NewTLOL.model.monster.GiftDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 等级礼包初始化
 * @author LuoSR
 * @date 2014年4月29日
 */
public class LevelGiftInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_levelGiftInit");
			for (LevelGift levelGift : GiftJson.instance().getLevelGiftList()) {
				LlpMessage levelGiftInfo = message.write("levelGiftInfoList");
				levelGiftInfo.write("level", levelGift.getLevel());
				levelGiftInfo.write("state", online.getLevelGiftStateMap().get(levelGift.getLevel()));

				for (GiftDropItem monsterDropItem : levelGift.getItems()) {
					LlpMessage levelGiftItemInfo = levelGiftInfo.write("levelGiftItemInfoList");
					levelGiftItemInfo.write("itemId", monsterDropItem.getItemId());
					levelGiftItemInfo.write("itemNum", monsterDropItem.getNum());
					levelGiftItemInfo.write("icon", monsterDropItem.getIcon());
				}
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
