package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.enumeration.EIntensifyEvent;
import iyunu.NewTLOL.enumeration.common.ECostType;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.intensify.res.IntensifyStoneUpRes;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 宝石镶嵌升级
 * 
 * @author SunHonglei
 * 
 */
public class IntensifyStoneUp extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int part = msg.readInt("part"); // 部位
		int position = msg.readInt("position"); // 配件位置

		EEquip eEquip = EEquip.values()[part];
		Map<Integer, Rabbet> map = online.getBodyRabbet().get(eEquip); // 部位镶嵌集合

		Rabbet rabbet = map.get(position);
		if (rabbet.getOpen() == 0) {
			result = 1;
			reason = "需要打孔";
			return;
		}

		if (rabbet.getStoneId() == 0) {
			result = 1;
			reason = "请先镶嵌一级宝石";
			return;
		}

		IntensifyStoneUpRes intensifyStoneUpRes = IntensifyJson.instance().getIntensifyStoneUp(rabbet.getStoneId());
		if (intensifyStoneUpRes == null) {
			result = 1;
			reason = "请先镶嵌一级宝石";
			return;
		}

		if (intensifyStoneUpRes.getNewId() == 0) {
			result = 1;
			reason = "已达到最高级";
			return;
		}

		if (intensifyStoneUpRes.getCostType().equals(ECostType.money)) {

			if (intensifyStoneUpRes.getCostValue() > online.getMoney()) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;
			}

		} else if (intensifyStoneUpRes.getCostType().equals(ECostType.coin)) {

			if (intensifyStoneUpRes.getCostValue() > online.getCoin()) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

		} else {

			if (intensifyStoneUpRes.getCostValue() > online.getGold()) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 1);
				return;
			}

		}

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		if (!online.getBagStone().removeById(intensifyStoneUpRes.getNewId(), 1, cellsMap, EItemCost.intensifyStoneUp)) {
			result = 1;
			reason = "所需宝石不足";
			return;
		}

		if (intensifyStoneUpRes.getCostType().equals(ECostType.money)) {
			RoleServer.costMoney(online, intensifyStoneUpRes.getCostValue(), EMoney.intensifyStoneUp);
		} else if (intensifyStoneUpRes.getCostType().equals(ECostType.coin)) {
			RoleServer.costCoin(online, intensifyStoneUpRes.getCostValue(), EGold.intensifyStoneUp);
		} else {
			RoleServer.costGold(online, intensifyStoneUpRes.getCostValue(), EGold.intensifyStoneUp);
		}

		rabbet.setStoneId(intensifyStoneUpRes.getNewId());

		RoleServer.countStone(online); // 计算角色属性
		BagMessage.sendBagStone(online, cellsMap); // 刷新背包
		SendMessage.sendBodyRabbet(online);

		LogManager.intensify(online, EIntensifyEvent.宝石镶嵌, part, position, 0);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_intensifyStoneUp");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

		if (result == 0) {
			SendMessage.sendSttribute(online);
		}
	}
}
