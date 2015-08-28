package iyunu.NewTLOL.net.protocol.mail;

import iyunu.NewTLOL.message.PackageMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求邮件列表
 * 
 * @author SunHonglei
 * 
 */
public class MailList extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_mailList");
			int restMailNum = PackageMessage.packageMailMsg(online.getMails(), llpMessage);
			llpMessage.write("restMail", restMailNum);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
