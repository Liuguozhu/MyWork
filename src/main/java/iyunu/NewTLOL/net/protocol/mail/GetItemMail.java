package iyunu.NewTLOL.net.protocol.mail;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.io.mail.EMailIOTask;
import iyunu.NewTLOL.model.io.mail.MailIOTask;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.mail.Email;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 领取邮件取附件
 * 
 * @author SunHonglei
 * 
 */
public class GetItemMail extends TLOLMessageHandler {

	private int result;
	private String reason;
	private Map<Integer, Cell> map = new HashMap<Integer, Cell>();
	private boolean hasItem = false;

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======数据重置======
		map.clear();
		result = 0;
		reason = "取出成功";
		hasItem = false;
		// ======获取参数======
		long mailId = msg.readLong("mailId");

		// ======程序执行======
		for (Mail mail : online.getMails()) {
			if (mail.getMailId() == mailId) {

				if (mail.getPartner() != null) {
					if (online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
						result = 1;
						reason = "伙伴数量已满！";
						return;
					}
				}
				if (mail.getPartner() == null && mail.getNewItems().isEmpty() && mail.getGold() <= 0 && mail.getCoin() <= 0 && mail.getMoney() <= 0 && mail.getExp() <= 0 && mail.getTribute() <= 0) {
					result = 1;
					reason = "不存在可领取的物品！";
					return;
				}

				int gold = mail.getGold();
				if (online.getGold() + gold > RoleManager.MAX_GOLD) {
					result = 1;
					reason = "提取银两失败，银两将会超出上限：" + RoleManager.MAX_GOLD;
					return;
				}
				int coin = mail.getCoin();
				if (online.getCoin() + coin > RoleManager.MAX_COIN) {
					result = 1;
					reason = "提取银两失败，银两将会超出上限：" + RoleManager.MAX_COIN;
					return;
				}
				// ======添加物品======
				if (!mail.getNewItems().isEmpty()) {
					if (online.getBag().isFull(mail.getNewItems().size())) {
						result = 1;
						reason = "背包空间不足";
						return;
					}
					hasItem = true;
					Set<Entry<Item, Integer>> set = mail.getNewItems().entrySet();
					for (Iterator<Entry<Item, Integer>> it = set.iterator(); it.hasNext();) {
						Entry<Item, Integer> entry = it.next();
						BagServer.add(online, entry.getKey().copy(), entry.getValue(), map, EItemGet.mail);
						// 刷新物品
						BagMessage.sendBag(online, map);
					}
				}

				if (mail.getPartner() != null) {
					mail.getPartner().setRole(online);
					online.getPartnerMap().put(mail.getPartner().getId(), mail.getPartner());
					List<Partner> partnerList = new ArrayList<>();
					mail.getPartner().setOperateFlag(EpartnerOperate.add);
					partnerList.add(mail.getPartner());
					PartnerMessage.sendPartners(online, partnerList);
					mail.setPartner(null);
				}

				RoleServer.addGold(online, gold, EGold.mail); // 添加银两
				RoleServer.addCoin(online, coin, EGold.mail);// 添加银币
				RoleServer.addExp(online, mail.getExp(), EExp.mail);// 添加经验
				int money = mail.getMoney();
				RoleServer.addMoney(online, money, EMoney.mailGet);
				if (online.getGang() != null && mail.getTribute() != 0) {
					RoleServer.addTribute(online, mail.getTribute());// 添加帮贡
				}
				// 添加日志
				LogManager.mail(online.getId(), mail.getTitle(), mail.getContent(), mail.getNewItems(), mail.getGold(), mail.getCoin(), mail.getMoney(), mail.getExp(), mail.getPartner(), mail.getTribute(), Email.getItem);
				mail.clear();
				MailIOTask task = new MailIOTask(EMailIOTask.update, mail, online.getId(), mail.getMailId());
				IOProcessManager.instance().addMailTask(task);
				return;
			}
		}
		result = 1;
		reason = "取出失败，邮件不存在";
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_getItemMail");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0 && hasItem) {
			BagMessage.sendBag(online, map);
		}
	}

}
