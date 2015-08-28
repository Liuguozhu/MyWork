package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mail.Mail;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpMessage;

public final class PackageMessage {

	/**
	 * 私有构造函数
	 */
	private PackageMessage() {

	}

	/**
	 * 封装邮件消息
	 * 
	 * @param mails
	 *            邮件集合
	 * @param llpMessage
	 *            协议对象
	 */
	public static int packageMailMsg(List<Mail> mails, LlpMessage llpMessage) {
		int restMailSize = 0;
		// 大于30封则截断
		if (mails.size() > RoleManager.MAX_MAIL) {
			restMailSize = mails.size() - RoleManager.MAX_MAIL;
			mails = mails.subList(0, RoleManager.MAX_MAIL);

		}

		// 按mailID 倒着排
		Collections.sort(mails, new Comparator<Mail>() {
			@Override
			public int compare(Mail mail1, Mail mail2) {
				if (mail1.getMailId() < mail2.getMailId()) {
					return 1;
				} else if (mail1.getMailId() > mail2.getMailId()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		int sortNum = 0;
		for (Mail mail : mails) {
			sortNum = sortNum + 1;
			LlpMessage message = llpMessage.write("mailInfoList");
			message.write("mailId", mail.getMailId());
			message.write("title", mail.getTitle());
			message.write("content", mail.getContent());
			message.write("read", mail.getRead());
			message.write("senderName", mail.getSendName());
			message.write("gold", mail.getGold());
			message.write("tribute", mail.getTribute());
			message.write("exp", mail.getExp());
			message.write("coin", mail.getCoin());
			message.write("money", mail.getMoney());
			message.write("sortId", sortNum);

			if (mail.getNewItems().size() >= 1) {
				Set<Entry<Item, Integer>> set = mail.getNewItems().entrySet();
				for (Iterator<Entry<Item, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Item, Integer> entry = it.next();
					Item item = entry.getKey();
					if (item != null) {
						LlpMessage msg = message.write("items");
						msg.write("itemId", item.getId());
						msg.write("icon", item.getIcon());
						msg.write("num", entry.getValue());
					}
				}
			}
			if (mail.getPartner() != null) {
				LlpMessage msgPartner = message.write("partners");
				msgPartner.write("partnerId", mail.getPartner().getId());
				msgPartner.write("photo", mail.getPartner().getPhoto());
			}
		}
		return restMailSize;
	}

}
