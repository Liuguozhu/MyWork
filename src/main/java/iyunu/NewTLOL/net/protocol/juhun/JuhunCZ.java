package iyunu.NewTLOL.net.protocol.juhun;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.JuHunManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JuhunCZ extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Integer> nums = null;
	private int wuHunNum = 0;// 本次得到的武魂数

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "重置聚魂成功";
		wuHunNum = 0;

		if (online.getReceiveJuhun() < 1) {
			result = 1;
			reason = "您的收获次数已用完";
			return;
		}
		if (JuHunManager.instance.getMap().get(online.getId()) == null) {
			result = 1;
			reason = "重置聚魂数据错误";
			return;
		}

		if (online.getJuHunNum() < 1) {
			if (online.getMoney() < 2) {
				result = 1;
				reason = "您的元宝不足";
				return;
			}
			RoleServer.costMoney(online, 2, EMoney.juhunCZ);
		}

		nums = JuHunManager.instance.getRandom(online);

		if (nums == null) {
			result = 1;
			reason = "您的数据错误";
			return;
		}
		// 给本次摇出来的武魂赋值,发给客户端显示用
		wuHunNum = JuHunManager.instance.getNewWuhunNum(nums);
		// 减去重置武魂次数
		if (online.getJuHunNum() > 0) {
			online.setJuHunNum(online.getJuHunNum() - 1);
		}
		// 刷新客户端聚魂
		SendMessage.sendJuhun(online);
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_juhunCZ");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				for (int i = 0; i < nums.size(); i++) {
					message.write("nums", nums.get(i));
				}
				message.write("wuHunNumCZ", wuHunNum);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
