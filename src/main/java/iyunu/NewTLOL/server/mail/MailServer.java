package iyunu.NewTLOL.server.mail;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.io.mail.EMailIOTask;
import iyunu.NewTLOL.model.io.mail.MailIOTask;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mail.Email;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.redis.RedisCenter;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MailServer {

	/**
	 * 检查是否有未读的邮件
	 * 
	 * @return 有未读邮件
	 */
	public static boolean hasNoRead(Role role) {
		for (Mail mail : role.getMails()) {
			if (mail.getRead() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return 根据邮件ID取得邮件
	 */
	public static Mail getMail(long mailId, List<Mail> mails) {
		for (Mail mail : mails) {
			if (mail.getMailId() == mailId) {
				return mail;
			}
		}
		return null;
	}

	/**
	 * 系统发送邮件,带物品
	 * 
	 * @param roleId
	 * @param title
	 * @param content
	 * @param itemIds
	 * @param gold
	 * @param money
	 * @param partner
	 */
	public static void send(long roleId, String title, String content, Map<Item, Integer> itemIds, int gold, int coin, int money, int exp, Partner partner) {
		RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
		Mail mail = new Mail(redisCenter.getMailId(), title, content, roleId, "系统");
		if (itemIds != null) {
			Set<Entry<Item, Integer>> set = itemIds.entrySet();
			for (Iterator<Entry<Item, Integer>> it = set.iterator(); it.hasNext();) {
				Entry<Item, Integer> entry = it.next();
				Item item = entry.getKey();
				int itemNum = entry.getValue();
				int max = item.getMax();
				if (itemNum > max) {
					itemIds.put(item, max);
				}
			}
			// mail.setItems(itemIds);
			MailServer.putItem(mail, itemIds);
		}
		if (partner != null) {
			mail.setPartner(partner);
		}
		mail.setGold(gold);
		mail.setCoin(coin);
		mail.setMoney(money);
		mail.setExp(exp);
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			role.getMails().add(mail);
		}
		MailIOTask task = new MailIOTask(EMailIOTask.insert, mail, roleId, mail.getMailId());
		IOProcessManager.instance().addMailTask(task);
		LogManager.mail(roleId, title, content, itemIds, gold, coin, money, exp, partner, 0, Email.send);
	}

	/**
	 * 系统拍卖行返回邮件,带物品
	 * 
	 * @param roleId
	 * @param mail
	 */
	public static void send(long roleId, String title, String content, Item item, int num, int gold, int coin, int money, int exp, Partner partner, String sendName) {
		RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
		Mail mail = new Mail(redisCenter.getMailId(), title, content, roleId, sendName);
		if (item != null) {
			// mail.getItems().put(item, num);
			MailServer.putItem(mail, item, num);
		}
		mail.setGold(gold);
		mail.setCoin(coin);
		mail.setMoney(money);
		mail.setExp(exp);
		mail.setPartner(partner);
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			role.getMails().add(mail);
		}
		MailIOTask task = new MailIOTask(EMailIOTask.insert, mail, roleId, mail.getMailId());
		IOProcessManager.instance().addMailTask(task);
		LogManager.mail(roleId, title, content, mail.getNewItems(), gold, coin, money, exp, partner, 0, Email.send);

	}

	/**
	 * 系统发送邮件,带物品，带帮派个人贡献
	 * 
	 * @param roleId
	 * @param title
	 * @param content
	 * @param itemIds
	 * @param gold
	 * @param money
	 * @param partner
	 */
	public static void send(long roleId, String title, String content, Map<Item, Integer> itemIds, int gold, int coin, int money, int exp, int tribute, Partner partner) {
		RedisCenter redisCenter = Spring.instance().getBean("redisCenter", RedisCenter.class);
		Mail mail = new Mail(redisCenter.getMailId(), title, content, roleId, "系统");
		if (itemIds != null) {
			Set<Entry<Item, Integer>> set = itemIds.entrySet();
			for (Iterator<Entry<Item, Integer>> it = set.iterator(); it.hasNext();) {
				Entry<Item, Integer> entry = it.next();
				Item item = entry.getKey();
				int itemNum = entry.getValue();
				int max = item.getMax();
				if (itemNum > max) {
					itemIds.put(item, max);
				}
			}
			// mail.setItems(itemIds);
			MailServer.putItem(mail, itemIds);
		}
		if (partner != null) {
			mail.setPartner(partner);
		}
		mail.setGold(gold);
		mail.setCoin(coin);
		mail.setMoney(money);
		mail.setExp(exp);
		mail.setTribute(tribute);
		Role role = ServerManager.instance().getOnlinePlayer(roleId);
		if (role != null) {
			role.getMails().add(mail);
		}
		MailIOTask task = new MailIOTask(EMailIOTask.insert, mail, roleId, mail.getMailId());
		IOProcessManager.instance().addMailTask(task);
		LogManager.mail(roleId, title, content, itemIds, gold, coin, money, exp, partner, tribute, Email.send);
	}

	public static void putItem(Mail mail, Map<Item, Integer> items) {
		mail.setNewItems(items);
	}

	public static void putItem(Mail mail, Item item, int num) {
		mail.getNewItems().put(item, num);
	}
}