package iyunu.NewTLOL.net.protocol.activityPay;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 每日充值优惠活动初始化
 * @author SunHonglei
 *
 */
public class PayEverydayInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_payEverydayInit");

			if (ActivityJson.PAY_EVERYDAY != null) {
				llpMessage.write("time", Time.getTimeToStr(ActivityJson.PAY_EVERYDAY.getStartTime()) + "至" + Time.getTimeToStr(ActivityJson.PAY_EVERYDAY.getEndTime()));

				if (ActivityPayManager.payEveryday.containsKey(online.getId())) {
					llpMessage.write("amt", ActivityPayManager.payEveryday.get(online.getId()));
				} else {
					llpMessage.write("amt", 0);
				}

				for (Integer value : ActivityJson.PAY_EVERYDAY.getValues()) {
					llpMessage.write("values", value);
				}
				for (MonsterDropItem dropItem : ActivityJson.PAY_EVERYDAY.getReward()) {
					LlpMessage message = llpMessage.write("items");
					message.write("itemId", dropItem.getItemId());
					message.write("number", dropItem.getNum());
				}

				LlpMessage msg = llpMessage.write("items");
				msg.write("itemId", ActivityJson.PAY_EVERYDAY.getAward().getItemId());
				msg.write("number", ActivityJson.PAY_EVERYDAY.getAward().getNum());

				for (MonsterDropItem dropItem : ActivityJson.PAY_EVERYDAY.getAwardShow()) {
					LlpMessage message = llpMessage.write("items");
					message.write("itemId", dropItem.getItemId());
					message.write("number", dropItem.getNum());
				}
			}

			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}