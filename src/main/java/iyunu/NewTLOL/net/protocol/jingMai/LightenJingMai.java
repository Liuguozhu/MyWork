package iyunu.NewTLOL.net.protocol.jingMai;

import iyunu.NewTLOL.json.JingMaiJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.jingMai.instance.JingMai;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 点亮经脉
 * @author LSR
 * @date 2012-8-27
 */
public class LightenJingMai extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======数据重置======
		result = 0;
		reason = "点亮经脉成功";

		// ======获取参数======
		int note = msg.readInt("note");

		// ======程序执行======
		if (note != online.getJingMaiId() + 1) {
			result = 1;
			reason = "经脉错误！";
			return;
		}

		JingMai jingMai = JingMaiJson.instance().getJingMai(note);

		if (jingMai == null) {
			result = 1;
			reason = "您的经脉已达到最高级";
			return;
		}

		if (!RoleServer.costYuanQi(online, jingMai.getExpend())) {
			result = 1;
			reason = "元气不足！";
			return;
		}

		// 保存下一层点亮层数
		online.setJingMaiId(note);

		// 计算经脉增加的属性
		RoleServer.countJingMai(online);

		LogManager.jingMai(online, note);

		if (jingMai.getMark() == 1) {
			String content = StringControl.grn(online.getNick()) + "成功打通" + StringControl.grn(jingMai.getName()) + "，战力飙升。各路大神纷纷前来恭贺。";
			BulletinManager.instance().addBulletinRock(content, 2);
		}

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_lightenJingMai");
			message.write("result", result);
			message.write("reason", reason);
			message.write("note", online.getJingMaiId() + 1);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			SendMessage.sendSttribute(online);
		}
	}

}
