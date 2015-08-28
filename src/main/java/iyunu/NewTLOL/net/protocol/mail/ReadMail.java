package iyunu.NewTLOL.net.protocol.mail;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.model.io.mail.EMailIOTask;
import iyunu.NewTLOL.model.io.mail.MailIOTask;
import iyunu.NewTLOL.model.mail.Email;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 阅读邮件
 * 
 * @author SunHonglei
 * 
 */
public class ReadMail extends TLOLMessageHandler {
	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		// ======获取参数======
		long mailId = msg.readLong("mailId");

		// ======程序执行======
		for (Mail mail : online.getMails()) {
			if (mail.getMailId() == mailId) {
				mail.setRead(1);
				LogManager.mail(online.getId(), mail.getTitle(), mail.getContent(), mail.getNewItems(), mail.getGold(), mail.getCoin(), mail.getMoney(), mail.getExp(), mail.getPartner(), mail.getTribute(), Email.read);
				MailIOTask task = new MailIOTask(EMailIOTask.mark, mail, online.getId(), mail.getMailId());
				IOProcessManager.instance().addMailTask(task);
				return;
			}
		}

		result = 1;
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_readMail");
			message.write("result", result);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

}
