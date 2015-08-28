package iyunu.NewTLOL.net.protocol.activityPay;

import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ActivityPayInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_activityPayInit");
			long now = System.currentTimeMillis();
			for (PayActivityList payActivity : ActivityPayJson.instance().getPayActivities().values()) {
				if (payActivity.getStart() < now && now < payActivity.getEnd()) {
					LlpMessage message = llpMessage.write("activityInfoList");
					message.write("type", payActivity.getType());
					message.write("name", payActivity.getName());
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