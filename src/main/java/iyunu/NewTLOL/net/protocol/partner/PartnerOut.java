package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.partner.res.PartnerSendBack;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.TipServer;
import iyunu.NewTLOL.server.bag.BagServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 解雇伙伴
 * @author LuoSR
 * @date 2014年4月8日
 */
public class PartnerOut extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "成功";
		partnerList.clear();
		long partnerId = msg.readLong("partnerId");

		Partner partner = online.findPartner(partnerId);
		if (partner == null) {
			result = 1;
			reason = "伙伴不存在";
			return;
		}

		PartnerSendBack partnerSendBack = PartnerJson.instance().getSendBack().get(partner.getIndex());
		if (partnerSendBack != null) {
			Map<Integer, Cell> cellsMap = new HashMap<>();

			Item item = ItemJson.instance().getItem(partnerSendBack.getId());
			if (partnerSendBack.getBind() == 1) { // 使用绑定材料，物品绑定
				item.bind();
			}
			BagServer.add(online, item, partnerSendBack.getNum(), cellsMap, EItemGet.partnerOut);
			BagMessage.sendBag(online, cellsMap);
		}

		online.getPartnerMap().remove(partner.getId());
		partner.setOperateFlag(EpartnerOperate.delete);
		partnerList.add(partner);
		TipServer.costPartner(online, partner); // ======提示======
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerOut");
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
