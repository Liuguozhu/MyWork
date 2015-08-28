package iyunu.NewTLOL.net.protocol.juhun;

import iyunu.NewTLOL.manager.JuHunManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JuhunGo extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Integer> nums = null;
	private int wuHunNum = 0;// 本次得到的武魂数

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "聚魂成功";
		wuHunNum = 0;

		if (JuHunManager.instance.getMap().keySet().contains(online.getId())) {
			result = 1;
			reason = "非法操作";
			return;
		}

		if (online.getReceiveJuhun() < 1) {
			result = 1;
			reason = "您的收获次数已用完";
			// 如果每日10次收获次数用完则不走下面流程，直接通知结束
			return;
		}

		if (JuHunManager.instance.getMap().containsKey(online.getId())) {
			result = 1;
			reason = "操作错误";
			return;
		}
		nums = JuHunManager.instance.getRandom(online);

		if (nums == null | nums.size() <= 0) {
			result = 1;
			reason = "您的数据错误";
			return;
		}
		// 给本次摇出来的武魂赋值,发给客户端显示用
		wuHunNum = JuHunManager.instance.getNewWuhunNum(nums);
		// 刷新客户端聚魂
		SendMessage.sendJuhun(online);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_juhun");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				for (int i = 0; i < nums.size(); i++) {
					message.write("nums", nums.get(i));
				}
				message.write("wuHunNum", wuHunNum);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
