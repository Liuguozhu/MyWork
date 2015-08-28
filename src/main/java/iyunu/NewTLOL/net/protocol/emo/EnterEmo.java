package iyunu.NewTLOL.net.protocol.emo;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapDesRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class EnterEmo extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "进入恶魔岛成功";
	int map = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "进入恶魔岛成功";
		map = msg.readInt("map");
		if (map != 1 && map != 2) {
			result = 1;
			reason = "进入恶魔岛失败，请重新尝试";
			return;
		}
		if (map == 1) {
			if (!EmoMapManager.EMO1_STATE) {
				result = 1;
				reason = "活动未开启";
				return;
			}
			EmoMapDesRes emo1 = MiningJson.instance().getEmoDes().get(MiningJson.instance().getEmoMapInfoRes().getEmo1Map());
			if (online.getLevel() < emo1.getLevel()) {
				result = 1;
				reason = "等级不足" + emo1.getLevel() + "级";
				return;
			}
			if (online.getEmoCount() >= EmoMapManager.MAX) {
				result = 1;
				reason = "次数不足";
				return;
			}
		} else {
			if (!EmoMapManager.EMO2_STATE) {
				result = 1;
				reason = "活动未开启";
				return;
			}
			EmoMapDesRes emo2 = MiningJson.instance().getEmoDes().get(MiningJson.instance().getEmoMapInfoRes().getEmo2Map());
			if (online.getLevel() < emo2.getLevel()) {
				result = 1;
				reason = "等级不足" + emo2.getLevel() + "级";
				return;
			}
			if (online.getEmoCount() >= EmoMapManager.MAX) {
				result = 1;
				reason = "次数不足";
				return;
			}
		}
		online.setEmoCount(online.getEmoCount() + 1);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_enterEmo");
			message.write("result", result);
			if (result == 0) {
				if (map == 1) {
					BaseMap b = MapManager.instance().getMapById(MiningJson.instance().getEmoMapInfoRes().getEmo1Map());
					if (online.getTeam() != null) {
						MapServer.changeMap(online.getTeam(), b.getTransmitX(), b.getTransmitY(), b, online.getMapInfo().getBaseMap());
					} else {
						MapServer.changeMap(online, b.getTransmitX(), b.getTransmitY(), b, online.getMapInfo().getBaseMap());
					}
				}
				if (map == 2) {
					BaseMap b = MapManager.instance().getMapById(MiningJson.instance().getEmoMapInfoRes().getEmo2Map());
					if (online.getTeam() != null) {
						MapServer.changeMap(online.getTeam(), b.getTransmitX(), b.getTransmitY(), b, online.getMapInfo().getBaseMap());
					} else {
						MapServer.changeMap(online, b.getTransmitX(), b.getTransmitY(), b, online.getMapInfo().getBaseMap());
					}
				}
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
