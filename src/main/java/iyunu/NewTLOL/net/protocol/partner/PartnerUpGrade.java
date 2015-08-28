package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 伙伴升级
 * 
 * @author fhy
 * 
 */
public class PartnerUpGrade extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "成功";
		partnerList.clear();
		long id = msg.readLong("id");
		long id1 = msg.readLong("id1");
		long id2 = msg.readLong("id2");
		long id3 = msg.readLong("id3");
		Partner partner = online.findPartner(id);
		if (id1 == 0) {
			id1 = -1;
		}
		if (id2 == 0) {
			id2 = -1;
		}
		if (id3 == 0) {
			id3 = -1;
		}
		if (partner == null) {
			result = 1;
			reason = "请选择要升级的伙伴！";
			return;
		}
		if (partner.getLevel() >= 80) {
			result = 1;
			reason = "伙伴已满级！";
			return;
		}
		if (partner.getLevel() >= online.getLevel() + 5) {
			result = 1;
			reason = "伙伴不可以高于角色5个等级~";
			return;
		}

		if (id == id1 || id == id2 || id == id3) {
			result = 1;
			reason = "不可以选择！";
			return;
		}
		// Partner onParter = online.getPartner();
		// if (onParter != null) {
		// if (onParter.getId() == id1 || onParter.getId() == id2 ||
		// onParter.getId() == id3) {
		// result = 1;
		// reason = "出战伙伴不可以选择！";
		// return;
		// }
		// }
		Partner partner1 = online.findPartner(id1);
		if (id1 != -1 && partner1 == null) {
			result = 1;
			reason = "伙伴1数据错误！";
			return;
		}
		Partner partner2 = online.findPartner(id2);
		if (id2 != -1 && partner2 == null) {
			result = 1;
			reason = "伙伴2数据错误！";
			return;
		}
		Partner partner3 = online.findPartner(id3);
		if (id3 != -1 && partner3 == null) {
			result = 1;
			reason = "伙伴3数据错误！";
			return;
		}

		int expAll = upExp(partner1, partner2, partner3);
		PartnerServer.addExp(partner, expAll);
		if (partner.getLevel() >= 80) {
			partner.setLevel(80);
			partner.setExp(0);
		}
		// 高于自身等级5级时，设为 自身等级+5，经验0
		if (partner.getLevel() >= online.getLevel() + 5) {
			partner.setLevel(online.getLevel() + 5);
			partner.setExp(0);
		}
		partner.setOperateFlag(EpartnerOperate.update);
		partnerList.add(partner);
		// 记日志
		LogManager.partnerUpGrade(online, partner, partner1, partner2, partner3);
	}

	public int upExp(Partner... partnerFu) {
		int expAll = 0;
		for (Partner partner : partnerFu) {
			if (partner != null) {
				expAll += partner.getOwnExp() + PartnerJson.instance().getUpExpMax(partner.getLevel());
				online.getPartnerMap().remove(partner.getId());
				partner.setOperateFlag(EpartnerOperate.delete);
				partnerList.add(partner);
			}
		}
		return expAll;
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerUpGrade");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				PartnerMessage.sendPartners(online, partnerList);
				PartnerMessage.refreshFightPartner(online);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
