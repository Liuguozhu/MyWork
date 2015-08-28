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
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 删除邮件
 * 
 * @author SunHonglei
 * 
 */
public class DelMail extends TLOLMessageHandler {

	private int result;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 1;
		reason = "删除失败";
		// ======获取参数======
		int size = msg.readSize("mailIds");

		if (size <= 0) {
			result = 1;
			reason = "请选择邮件！";
			return;
		}
		List<Long> mailIds = new ArrayList<Long>();
		for (int j = 0; j < size; j++) {
			long mailId = msg.readLong("mailIds", j);
			mailIds.add(mailId);
		}

		// ======程序执行======
		List<Mail> delList = new ArrayList<Mail>();
		// try {
		int flag = 0;
		for (Mail mail : online.getMails()) {
			if (mailIds.contains(mail.getMailId())) {
				if (mail.getPartner() == null && mail.getNewItems().isEmpty() && mail.getGold() <= 0 && mail.getCoin() <= 0 && mail.getMoney() == 0 && mail.getExp() == 0) {
					LogManager.mail(online.getId(), mail.getTitle(), mail.getContent(), mail.getNewItems(), mail.getGold(), mail.getCoin(), mail.getMoney(), mail.getExp(), mail.getPartner(), mail.getTribute(), Email.del);
					MailIOTask task = new MailIOTask(EMailIOTask.delete, mail, online.getId(), mail.getMailId());
					IOProcessManager.instance().addMailTask(task);
					delList.add(mail);
					result = 0;
					reason = "删除成功";
				} else {
					flag = 1;
				}
			}
		}
		online.getMails().removeAll(delList);
		if (flag == 1) {
			result = 1;
			reason = "邮件有银两或附件";
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_delMail");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			if (result == 0) {
				int restMailNum = PackageMessage.packageMailMsg(online.getMails(), llpMessage);
				llpMessage.write("restMail", restMailNum);
			}

			channel.write(llpMessage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
