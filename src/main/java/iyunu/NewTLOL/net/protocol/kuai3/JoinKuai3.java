package iyunu.NewTLOL.net.protocol.kuai3;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.Kuai3Manager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.kuai3.Kuai3Enum;
import iyunu.NewTLOL.model.kuai3.Kuai3Model;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JoinKuai3 extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "下注成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入快3下注");
		result = 0;
		reason = "下注成功";
		int allNum = msg.readInt("allNum");

		int turns = msg.readInt("turns");
		// System.out.println("猜猜看总注数" + allNum);
		// System.out.println("猜猜看发上来的期数" + turns);
		int nowTurns = Kuai3Manager.instance().getTurns();
		if (turns < nowTurns) {
			result = 2;
			reason = "已是" + nowTurns + "期，下注失败，点击确定下注到本期";
			return;
		}

		if (online.getCoin() < (allNum * 1000)) {
			result = 1;
			reason = "银两不足，不能下注";
			return;
		}

		int size = msg.readSize("joinKuai3");
		// System.out.println("size:" + size);

		try {
			List<Kuai3Model> roles = Kuai3Manager.instance().getKuai3Roles();
			int typeNum = Kuai3Enum.values().length;
			for (int i = 0; i < size; i++) {
				String msgElement = msg.readString("joinKuai3", i);
				String[] element = msgElement.split("=");
				int num = Integer.parseInt(element[0]);
				int times = Integer.parseInt(element[1]);
				int type = Integer.parseInt(element[2]);
				if (times == 0) {
					result = 1;
					reason = "数据错误！";
					return;
				}
				if (type < 0 || type > typeNum) {
					result = 1;
					reason = "数据错误！";
					return;
				}

				// System.out.println("第" + (i + 1) + "注num：" + num);
				// System.out.println("第" + (i + 1) + "注times：" + times);
				// System.out.println("第" + (i + 1) + "注type：" + type);

				if (online.getCoin() < times * 1000) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 2);
					return;
				}
				// 扣钱
				RoleServer.costCoin(online, times * 1000, EGold.kuai3);
				Kuai3Model k = new Kuai3Model();
				k.setNum(num);
				k.setTimes(times);
				k.setType(Kuai3Enum.values()[type]);
				k.setRoleId(online.getId());
				roles.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_joinKuai3");
			message.write("turns", Kuai3Manager.instance().getTurns());
			message.write("comeOut", Kuai3Manager.instance().getResultNow());
			message.write("timeout", Kuai3Manager.instance().getTimeout());
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
