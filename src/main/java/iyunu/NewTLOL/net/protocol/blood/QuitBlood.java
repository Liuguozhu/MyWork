package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.Iterator;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QuitBlood extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "退出血战成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "退出血战成功";

		if (online.getBlood() == 0) {
			result = 1;
			reason = "您没有阵营！";
			return;
		}

		if (online.getTeam() != null) {
			result = 1;
			reason = "请先离开队伍！";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_quitBlood");
			message.write("result", result);
			if (result == 0) {
				// 加入临时退出集合
				BloodManager.instance().getQuitList().add(online.getId());
				// 删除退出的人
				if (online.getBlood() == 1) {
					Iterator<Long> it = BloodManager.instance().getHong().iterator();
					while (it.hasNext()) {
						long rem = it.next();
						if (rem == online.getId()) {
							it.remove();
						}
					}
					BloodManager.hongPower = BloodManager.hongPower - online.getPower();
					// BloodManager.instance().getHong().remove(online.getId());
				}
				if (online.getBlood() == 2) {
					Iterator<Long> it = BloodManager.instance().getHong().iterator();
					while (it.hasNext()) {
						long rem = it.next();
						if (rem == online.getId()) {
							it.remove();
						}
					}
					BloodManager.lanPower = BloodManager.lanPower - online.getPower();
					// BloodManager.instance().getLan().remove(online.getId());
				}
				// 去掉状态
				online.setBlood(0);
				SendMessage.sendBlood(online);

				// 加入血战状态改变队列
				MapManager.instance().addBloodQueue(online);
			}
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
