package iyunu.NewTLOL.net.protocol.huntskill;

import iyunu.NewTLOL.json.HuntskillJson;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.skillBook.HuntskillMapInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.map.MapServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 退出猎技地图
 * @author LSR
 * @date 2012-8-27
 */
public class QuitMap extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "退出猎技地图成功";
	private int x = 0;
	private int y = 0;
	private BaseMap oldMapInfo = null;
	private BaseMap mapInfo = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		
		oldMapInfo = online.getMapInfo().getBaseMap();

		HuntskillMapInfo huntskillMapInfo = HuntskillJson.instance().getHuntskillMapInfo();
		mapInfo = MapManager.instance().getMapById(huntskillMapInfo.getComeOutMapId());

		x = huntskillMapInfo.getComeOutX();
		y = huntskillMapInfo.getComeOutY();

		result = 0;
		reason = "退出猎技地图成功";
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_quitMap");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			MapServer.changeMap(online, x, y, mapInfo, oldMapInfo);
		}
	}

}
