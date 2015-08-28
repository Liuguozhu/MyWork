package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.model.activity.HuntTreasureInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 寻宝
 * 
 * @author SunHonglei
 * 
 */
public class HuntTreasure extends TLOLMessageHandler {

	public static int DAN_SHOU_MO_JIN = 500;
	public static int SHUANG_SHOU_ZHUA_BAO = 1000;
	private int result = 0;
	private String reason;
	private int index = 0;
	private int type = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "寻宝成功";
		index = 0;
		type = 0;

		if (online.getLevel() < 30) {
			result = 1;
			reason = "寻宝失败，等级不足";
			return;
		}

		type = msg.readInt("type"); // 2.1000银两，3.500银两，

		if (type == 0) {
			result = 1;
			reason = "寻宝失败，类型错误";
			return;
		}
		HuntTreasureInfo huntTreasureInfo = ActivityJson.instance().randomHuntTreasure(type);
		if (huntTreasureInfo == null) {
			result = 1;
			reason = "寻宝失败";
			return;
		}

		if (type == 2) {
			if (online.getCoin() < HuntTreasure.SHUANG_SHOU_ZHUA_BAO) {
				result = 1;
				reason = "寻宝失败，银两不足";
				return;
			}
		} else {
			if (online.getCoin() < HuntTreasure.DAN_SHOU_MO_JIN) {
				result = 1;
				reason = "寻宝失败，银两不足";
				return;
			}
		}
		index = huntTreasureInfo.getIndex();
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_huntTreasure");
			message.write("result", result);
			message.write("reason", reason);
			message.write("index", index);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}