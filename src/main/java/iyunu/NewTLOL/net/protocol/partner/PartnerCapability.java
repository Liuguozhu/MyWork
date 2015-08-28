package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 伙伴使用潜力丹
 * 
 */
public class PartnerCapability extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "成功";
		partnerList.clear();

		long partnerId = msg.readLong("partnerId");
		int index = online.getBag().isInBagByType(EItem.partnerCapability);
		if (index == -1) {
			result = 1;
			reason = "潜力丹不足";
			return;
		}

		Partner partner = online.findPartner(partnerId);
		if (partner == null) {
			result = 1;
			reason = "伙伴不存在";
			return;
		}

		Cell cell = online.getBag().getCells()[index];
		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "潜力丹不存在";
			return;
		}
		if (cell.getNum() < 1) {
			result = 1;
			reason = "潜力丹数量不足";
			return;
		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.partnerCapability); // 删除物品
		BagMessage.sendBag(online, cellsMap, false);

		partner.setCapability(partner.getCapability() + item.getValue());
		partner.setOperateFlag(EpartnerOperate.update);
		partnerList.add(partner);

		// 记日志
		LogManager.partnerCapability(online, partner, item.getValue());
		AwardServer.addCapability(online, partner, item.getValue());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerCapability");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				PartnerMessage.sendPartners(online, partnerList);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
