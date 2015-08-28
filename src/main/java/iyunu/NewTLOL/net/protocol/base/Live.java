package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Live extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
//		if (online != null) {
//			long now = System.currentTimeMillis();
//			if (now - online.getLiveTime() < 1000 * 5) {
//
//				if (now - online.getLogonTime() < online.getLiveNum() * 1000 * 8) {
//					try {
//						RoleManager.logout(online, "角色【" + online.getNick() + "】加速异常，下线");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					result = 1;
//					return;
//				} else {
//					online.setLiveNum(online.getLiveNum() + 1);
//					online.setLiveTime(now);
//				}
//
//			} else {
//				online.setLiveNum(online.getLiveNum() + 1);
//				online.setLiveTime(now);
//			}
//
//		} else {
//			LogManager.info("心跳");
//		}

	}

	@Override
	public void handleReply() throws Exception {

		if (result == 0) {
			LlpMessage llpMessage = null;
			try {
				llpMessage = LlpJava.instance().getMessage("s_Live");

				llpMessage.write("result", result);
				channel.write(llpMessage);
			} finally {
				if (llpMessage != null) {
					llpMessage.destory();
				}
			}
		}

	}
}