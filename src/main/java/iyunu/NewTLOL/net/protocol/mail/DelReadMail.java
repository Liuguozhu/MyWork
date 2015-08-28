package iyunu.NewTLOL.net.protocol.mail;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.message.PackageMessage;
import iyunu.NewTLOL.model.io.mail.EMailIOTask;
import iyunu.NewTLOL.model.io.mail.MailIOTask;
import iyunu.NewTLOL.model.mail.Email;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 删除已读邮件
 * 
 * @author SunHonglei
 * 
 */
public class DelReadMail extends TLOLMessageHandler {

	private int result;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "删除成功";

		// ======程序执行======
		ArrayList<Mail> temp = new ArrayList<Mail>();
		for (Mail mail : online.getMails()) {
			if (mail.getRead() == 0 && mail.getGold() == 0 && mail.getCoin() == 0 && mail.getNewItems().isEmpty()) {
				LogManager.mail(online.getId(), mail.getTitle(), mail.getContent(), mail.getNewItems(), mail.getGold(), mail.getCoin(), mail.getMoney(), mail.getExp(), mail.getPartner(), mail.getTribute(), Email.del);
				temp.add(mail);
			}
		}
		online.getMails().removeAll(temp);

		MailIOTask task = new MailIOTask(EMailIOTask.deleteRead, null, online.getId(), 0);
		IOProcessManager.instance().addMailTask(task);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_delReadMail");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
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
