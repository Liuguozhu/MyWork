package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.instance.Book;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.partner.PartnerServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 伙伴学习技能
 * @author LuoSR
 * @date 2013-11-28
 */
public class PartnerStudySkill extends TLOLMessageHandler {
	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "成功";
		cellsMap.clear();
		partnerList.clear();

		long partnerId = msg.readLong("id");
		int index = msg.readInt("index");

		Cell[] cells = online.getBag().getCells();
		Cell cell = cells[index];
		if (cell.getItem() == null) {
			result = 1;
			reason = "物品不存在！";
			return;
		}

		Book book = (Book) cell.getItem();
		if (!book.getType().name().equals(EItem.book.name())) {
			result = 1;
			reason = "不是技能书！";
			return;
		}

		Partner partner = online.findPartner(partnerId);
		if (partner == null) {
			result = 1;
			reason = "伙伴不存在！";
			return;
		}

		// 打书 检查是否已学次技能
		if (!PartnerServer.partnerStudySkill(partner, book.getSkillId())) {
			result = 1;
			reason = "伙伴已学习此技能！";
			return;
		}

		// 修改绑定
		if (book.getIsDeal() == 1) {
			partner.setIsBind(1);
		}

		// 扣除背包中的技能书
		online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.partnerSkill);
		BagMessage.sendBag(online, cellsMap, false);

		partner.setOperateFlag(EpartnerOperate.update);
		partnerList.add(partner);
		
		// ======检查是否有任务（伙伴学习技能任务）======
//		TaskServer.finishTaskById(online, TaskManager.instance().getPartnerStudyTask());

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_studySkill");
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
