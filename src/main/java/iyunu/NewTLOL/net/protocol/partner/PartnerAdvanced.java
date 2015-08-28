package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.TipServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 伙伴进阶
 * 
 * @author fhy
 * 
 */
public class PartnerAdvanced extends TLOLMessageHandler {

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
		Partner partner = online.findPartner(id);
		if (partner == null) {
			result = 1;
			reason = "伙伴不存在";
			return;
		}
		if (partner.getGrade() >= 4) {
			result = 1;
			reason = "伙伴已满阶";
			return;
		}
		Partner partner1 = online.findPartner(id1);

		if (partner1 == null) {
			result = 1;
			reason = "两个相同伙伴才能合成一个更高品质的！";
			return;
		}
//		if (partner1.isBattle()) {
//			result = 1;
//			reason = "出战伙伴不能被吞噬";
//			return;
//		}
		if (partner.getIndex() != partner1.getIndex()) {
			result = 1;
			reason = "伙伴阶数不同";
			return;
		}

		// 获得新宠物对象
		Partner newPartner = PartnerServer.evolve(partner);
		// 新伙伴继承老主伙伴的等级和经验
		newPartner.setLevel(partner.getLevel());
		newPartner.setExp(partner.getExp());
		newPartner.setRole(online);
		newPartner.setOperateFlag(EpartnerOperate.update);
		// 和绑定（如果原来两个中有一个绑定，那么全绑定）
		if (partner.getIsBind() == 1 || partner1.getIsBind() == 1) {
			newPartner.setIsBind(1);
		}
		partnerList.add(newPartner);

		// 删除老宠物
		PartnerServer.delPartnerById(online, partner.getId());
		PartnerServer.delPartnerById(online, partner1.getId());

		partner1.setOperateFlag(EpartnerOperate.delete);
		partnerList.add(partner1);
		// 添加新宠物
		online.getPartnerMap().put(newPartner.getId(), newPartner);

		// 新服活动
		ActivityPayManager.saveRoleIdByPartnerColor(online, newPartner);

		// 记日志
		LogManager.partnerAdvanced(online, partner, partner1);
		TipServer.costPartner(online, partner1); // ======提示======
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerAdvanced");
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
