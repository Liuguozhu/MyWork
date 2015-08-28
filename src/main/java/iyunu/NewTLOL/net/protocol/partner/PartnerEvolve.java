package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.partner.res.PartnerWorthRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.award.TipServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 伙伴进化
 * 
 * @author fhy
 * 
 */
public class PartnerEvolve extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "成功";
		partnerList.clear();

		long mainId = msg.readLong("mainId");
		long subId = msg.readLong("subId");

		Partner mainPartner = online.findPartner(mainId);
		if (mainPartner == null) {
			result = 1;
			reason = "主伙伴不存在";
			return;
		}

		if (mainPartner.getLevel() < 40) {
			result = 1;
			reason = "主伙伴不足40级";
			return;
		}

		if (mainPartner.getCapability() < 20) {
			result = 1;
			reason = "主伙伴的潜力值均不得低于20点";
			return;
		}

		if (mainPartner.getTalent() >= 4000) {
			result = 1;
			reason = "主伙伴成长率必须低于4000";
			return;
		}

		Partner subPartner = online.findPartner(subId);
		if (subPartner == null) {
			result = 1;
			reason = "副伙伴不存在";
			return;
		}

		if (subPartner.getLevel() < 40) {
			result = 1;
			reason = "副伙伴不足40级";
			return;
		}

		// 绑定（如果副伙伴绑定，那么绑定）
		if (subPartner.getIsBind() == 1) {
			mainPartner.setIsBind(1);
		}

		int value = PartnerServer.addTalent(mainPartner, subPartner);
		mainPartner.setCapability(mainPartner.getCapability() - 20);
		PartnerServer.countPotential(mainPartner);

		// 删除副伙伴
		PartnerServer.delPartnerById(online, subPartner.getId());

		mainPartner.setOperateFlag(EpartnerOperate.update);
		partnerList.add(mainPartner);
		subPartner.setOperateFlag(EpartnerOperate.delete);
		partnerList.add(subPartner);

		// 记日志
		LogManager.partnerEvolve(online, mainPartner, subPartner);
		TipServer.costPartner(online, subPartner); // ======提示======

		if (mainPartner.getTalent() > 3627) {
			for (PartnerWorthRes partnerWorthRes : PartnerJson.instance().getPartnerWorths()) {
				if (mainPartner.getTalent() >= partnerWorthRes.getWorth()) {
					String content = "恭喜" + StringControl.grn(online.getNick()) + "获得" + "的" + StringControl.color(mainPartner.getColor(), mainPartner.getNick());
					BulletinManager.instance().addBulletinRock(content, 1);
					break;
				}
			}
		}

		AwardServer.addTalet(online, mainPartner, value);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerEvolve");
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
