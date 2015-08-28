package iyunu.NewTLOL.net.protocol.trials;

import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.message.TrialsMessage;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 进入试炼
 * @author LuoSR
 * @date 2014年4月17日
 */
public class TrialsEnter extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int trialsId = 0;

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "进入试炼成功";

		// ======获取参数======
		trialsId = msg.readInt("trialsId");

		TrialsInfo trialsInfo = RaidsJson.instance().getTrialsMap().get(trialsId);
		if (online.getLevel() < trialsInfo.getLevelLimit()) {
			result = 1;
			reason = "等级不足";
			return;
		}

		if (online.getTeam() != null) {
			result = 1;
			reason = "组队不能进入试炼";
			return;
		}

		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能进入试炼";
			return;
		}

		if (!online.getTrials().containsKey(trialsId)) {
			result = 1;
			reason = "试炼暂未开启";
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_enterTrials");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);

			if (result == 0) {
				TrialsMessage.sendKillPosition(online, trialsId);
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}