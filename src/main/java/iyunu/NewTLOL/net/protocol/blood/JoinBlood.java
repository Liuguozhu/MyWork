package iyunu.NewTLOL.net.protocol.blood;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.blood.ReBornRes;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.StringControl;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JoinBlood extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "报名血战成功";
	ReBornRes r1 = BloodJson.instance().getReBorn().get(1);
	ReBornRes r2 = BloodJson.instance().getReBorn().get(2);
	BaseMap map1 = MapManager.instance().getMapById(r1.getMap());
	BaseMap map2 = MapManager.instance().getMapById(r2.getMap());

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "报名血战成功";

		if (!ActivityManager.BLOOD_STATE) {
			result = 1;
			reason = "血战还没有开始。";
			return;
		}
		if (online.getBlood() != 0) {
			result = 1;
			reason = "您已有阵营，不需要重复报名。";
			return;
		}
		if (BloodManager.instance().getQuitList().contains(online.getId())) {
			result = 1;
			reason = "您已退出，不可继续报名。";
			return;
		}
		if (online.getLevel() < 30) {
			result = 1;
			reason = "角色等级不足30级，无法参与。";
			return;
		}
		if (online.getTeam() != null) {
			result = 1;
			reason = "不可组队报名。";
			return;
		}

		HelperServer.helper(online, EHelper.xuezhan); // 小助手记录
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_joinBlood");
			message.write("result", result);
			if (result == 0) {
				{
					BloodManager.instance().randomCamp(online);
					// 传送到出生点
					ReBornRes r = BloodJson.instance().getReBorn().get(online.getBlood());
					MapServer.changeMap(online, r.getX(), r.getY(), MapManager.instance().getMapById(r.getMap()), online.getMapInfo().getBaseMap());

					String word = "";
					switch (online.getBlood()) {
					case 1:
						word = StringControl.yel("您已加入红方阵营，攻击蓝方阵营的敌人，守护" + StringControl.grn(map1.getName()) + "的守护者，攻击" + StringControl.grn(map2.getName()) + "的守护者。");
						break;
					case 2:
						word = StringControl.yel("您已加入蓝方阵营，攻击红方阵营的敌人，守护" + StringControl.grn(map2.getName()) + "的守护者，攻击" + StringControl.grn(map1.getName()) + "的守护者。");
						break;
					default:
						break;
					}
					if (!"".equals(word)) {
						Chat chat = new Chat(EChat.system, 0, "血战", word);
						ChatMessage.sendChat(online, chat);
					}
				}
				// 刷新倒计时
				SendMessage.sendBloodTime(online);
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
