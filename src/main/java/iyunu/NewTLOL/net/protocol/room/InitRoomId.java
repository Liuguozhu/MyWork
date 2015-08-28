package iyunu.NewTLOL.net.protocol.room;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 进入房间（无用）
 * 
 * @author SunHonglei
 * 
 */
public class InitRoomId extends TLOLMessageHandler {

	private String roomId;

	@Override
	public void handleReceived(LlpMessage msg) {
		roomId = ServerManager.instance().getServer();

		int type = msg.readInt("type");

		switch (type) {
		case 2:
			if (online.getTeam() != null) {
				roomId += "_team_" + online.getTeam().getId();
				return;
			}
		case 1:
			if (online.getGang() != null) {
				roomId += "_gang_" + online.getGang().getId();
				return;
			}
		default:
			roomId += "_world_001";
			break;
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_initRoomId");
			llpMessage.write("roomId", roomId);
//			System.out.println("房间号===" + roomId + "    角色名称【" + online.getNick() + "】");
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}
}