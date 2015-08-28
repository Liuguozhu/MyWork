package iyunu.NewTLOL.net.protocol.juhun;

import iyunu.NewTLOL.manager.JuHunManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JuhunPick extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

//		System.out.println("进入收聚魂");
		result = 0;
		reason = "收获成功";
		if (online.getReceiveJuhun() < 1) {
			result = 1;
			reason = "您的收获次数已用完";
			// 如果每日10次收获次数用完则不走下面流程，直接通知结束
			return;
		}
		// 玩家点击收获武魂，则看 聚魂时存的武魂为多少，为0说明收获过了，不为0则收获，然后置为0
		if (JuHunManager.instance.getWuHunMap().get(online.getId()) <= 0) {
			JuHunManager.instance.getWuHunMap().put(online.getId(), 0);
			result = 1;
			reason = "无要收获的武魂";
			return;
		} else {
			// 赋值应得的武魂数
			int wuHunNum = JuHunManager.instance.getWuHunMap().get(online.getId());
			// 加武魂
			RoleServer.addWuhun(online, wuHunNum);
			// 减去收获次数
			online.setReceiveJuhun(online.getReceiveJuhun() - 1);
			// 本次应得的武魂数置为0
			JuHunManager.instance.getWuHunMap().put(online.getId(), 0);
			// 清除上一次的牌型
			JuHunManager.instance.getMap().remove(online.getId());
			SendMessage.sendJuhun(online);
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_receivejuhun");
			message.write("result", result);
			message.write("reason", reason);
			message.write("wuHunNumShou", online.getWuHun());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
