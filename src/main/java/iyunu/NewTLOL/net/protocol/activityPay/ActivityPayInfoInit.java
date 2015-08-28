package iyunu.NewTLOL.net.protocol.activityPay;

import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import java.util.LinkedHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ActivityPayInfoInit extends TLOLMessageHandler {

	private PayActivityList payActivity;

	@Override
	public void handleReceived(LlpMessage msg) {
		int type = msg.readInt("type");

		payActivity = ActivityPayJson.instance().getPayActivities().get(type);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_activityPayInfoInit");

			LinkedHashMap<Integer, PayActivityInfo> linkedHashMap = null;
			switch (payActivity.getType()) {
			case 1:
				linkedHashMap = ActivityPayJson.instance().getPaySingles();
				break;
			case 2:
				linkedHashMap = ActivityPayJson.instance().getPayAccumulateWeek();
				break;
			case 3:
				linkedHashMap = ActivityPayJson.instance().get冲击狂人();
				break;
			case 4:
				linkedHashMap = ActivityPayJson.instance().get最佳搭档();
				break;
			case 5:
				linkedHashMap = ActivityPayJson.instance().get装备铸造();
				break;
			case 6:
				linkedHashMap = ActivityPayJson.instance().get江湖等级榜();
				break;
			case 7:
				linkedHashMap = ActivityPayJson.instance().get斗破洞天();
				break;
			case 8:
				linkedHashMap = ActivityPayJson.instance().get绝世神兵();
				break;
			case 9:
				linkedHashMap = ActivityPayJson.instance().get最强战力();
				break;
			case 10:
				linkedHashMap = ActivityPayJson.instance().get副本达人();
				break;
			case 11:
				linkedHashMap = ActivityPayJson.instance().get每日挑战();
				break;
			case 12:
				linkedHashMap = ActivityPayJson.instance().get铸造大匠();
				break;
			default:
				linkedHashMap = new LinkedHashMap<Integer, PayActivityInfo>();
				break;
			}

			if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
				for (PayActivityInfo activityInfo : linkedHashMap.values()) {
					LlpMessage message = llpMessage.write("activityMsgList");
					message.write("index", activityInfo.getIndex());
					message.write("des", activityInfo.getDesc());
					for (MonsterDropItem dropItem : activityInfo.getItems()) {
						LlpMessage msg = message.write("itemAwards");
						msg.write("itemId", dropItem.getItemId());
						msg.write("number", dropItem.getNum());
					}
					for (MonsterDropPartner dropPartner : activityInfo.getPartners()) {
						LlpMessage msg = message.write("partnerAwards");
						msg.write("partnerId", dropPartner.getPartnerIndex());
						msg.write("number", dropPartner.getNum());
					}
					message.write("gold", activityInfo.getGold());
					message.write("coin", activityInfo.getCoin());
					message.write("money", activityInfo.getMoney());
					message.write("state", ActivityPayManager.checkState(online.getId(), activityInfo.getType(), activityInfo.getIndex()));
				}

			}
			llpMessage.write("time", Time.dateToString(payActivity.getStartTime()) + " 至 " + Time.dateToString(payActivity.getEndTime()));

			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}