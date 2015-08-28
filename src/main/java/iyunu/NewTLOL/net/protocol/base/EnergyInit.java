package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpMessage;

/**
 * 刷新活力值
 * 
 * @author SunHonglei
 * 
 */
public class EnergyInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		RoleServer.energyCheck(online);
	}

	@Override
	public void handleReply() throws Exception {
		SendMessage.refreshEnergy(online);
	}
}