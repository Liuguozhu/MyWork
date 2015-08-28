package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.ArrayList;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 请求充值领奖数据
 * @author LuoSR
 * @date 2014年4月25日
 */
public class LookFirstAward extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_lookFirstAward");
			ArrayList<MonsterDropItem> itemList = ActivityJson.instance().getPayFirstInfo().getItems();

			for (MonsterDropItem monsterDropItem : itemList) {
				LlpMessage awardInfo = message.write("awardInfoList");
				Item item = ItemJson.instance().getItem(monsterDropItem.getItemId());
				if (item != null) {
					awardInfo.write("itemId", monsterDropItem.getItemId());
					awardInfo.write("icon", item.getIcon());
				}
			}

			int index = ActivityJson.instance().getPayFirstInfo().getIndex();
			message.write("index", index);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
