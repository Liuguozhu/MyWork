package iyunu.NewTLOL.net.protocol.bulletin;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 系统公告
 * 
 * @author SunHonglei
 * 
 */
public class SystemBulletin extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_systemBulletin");
			message.write("content", BulletinManager.instance().getSystemBulletin());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
