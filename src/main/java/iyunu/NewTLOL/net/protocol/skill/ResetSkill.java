package iyunu.NewTLOL.net.protocol.skill;

import iyunu.NewTLOL.event.uptip.UptipEvent;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 重置技能
 * 
 * @author SunHonglei
 * 
 */
public class ResetSkill extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "洗点成功";
		cellsMap.clear();

		if (online.getLevel() > 40) {
			int index = online.getBag().isInBagByType(EItem.xidianquan);
			if (index == -1) {
				result = 1;
				reason = "没有洗点券";
				return;
			}

			// Cell cell = online.getBag().getCells()[index];
			// LogManager.item(online, cell.getItem(), 1, EItemCost.skill); //
			// 物品消耗日志

			// 扣除背包中的洗点券
			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.skill);
			BagMessage.sendBag(online, cellsMap, false); // 刷新背包
		}

		// 重置技能点
		online.getSkillMap().clear();
		online.setFreeSkill(online.getSumSkill());

		RoleServer.countProperty(online); // 重新计算技能属性
		SendMessage.sendSttribute(online); // 通知客户端属性变化
		UptipEvent.技能点.check(online, online.getUptipBoolean(UptipEvent.技能点.getOrdinal()));
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_resetSkill");
			message.write("result", result);
			message.write("reason", reason);
			message.write("freeSkill", online.getFreeSkill());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
		if (result == 0) {
			RoleServer.resetSkill(online);
			// ======发送角色技能======
			SendMessage.sendSkill(online);
		}
	}
}