package iyunu.NewTLOL.net.protocol.trials;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.message.TrialsMessage;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.CommonConst;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 重置试炼
 * @author LuoSR
 * @date 2014年4月22日
 */
public class ResetTrials extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "重置试炼成功";

		// ======获取参数======
		int state = msg.readInt("state");

		// 判断重置试练次数
		int trialsResetAdd = online.getVip().getLevel().getTrialsResetAdd();
		int useReSetNum = 0;
		if (online.getResetTrials().containsKey(state)) {
			useReSetNum = online.getResetTrials().get(state);
		}

		if (trialsResetAdd - useReSetNum < 1) {
			result = 1;
			reason = "重置试练次数不足";
			return;
		}

		if (!RoleServer.costCoin(online, CommonConst.INT_FIVE_THOUSAND, EGold.trials)) {
			result = 1;
			reason = "银两不足5000，不能重置试练";
			return;
		}

		// 计算使用试练重置次数
		online.getResetTrials().put(state, useReSetNum + 1);

		// 清除试练数据
		List<TrialsInfo> list = RaidsJson.instance().getTrialsListMap().get(state);
		for (TrialsInfo trialsInfo : list) {
			if (online.getTrials().containsKey(trialsInfo.getTrialsId())) {
				online.getTrials().get(trialsInfo.getTrialsId()).clear();
			}
		}

		// 刷新试炼重置次数
		TrialsMessage.refreshTrialsReSetNum(online);
		TrialsMessage.refreshTrialsState(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_resetTrials");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}