package iyunu.NewTLOL.net.protocol.shenbing;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 神兵合成（暂时无用）
 * 
 * @author SunHonglei
 * 
 */
public class MakeShenbing extends TLOLMessageHandler {

	private int result = 0;
	private String reason;
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "兑换成功";
		cellsMap.clear();

//		if (online.getBag().isFull()) {
//			result = 1;
//			reason = "兑换失败，背包已满";
//			return;
//		}
//
//		int isBind = msg.readInt("isBind");
//
//		List<Integer> cellIndex = new ArrayList<>();
//		for (EItem eItem : EItem.getJade()) {
//
//			int index = online.getBag().isInBagByType(eItem, isBind);
//			if (index == -1) {
//				result = 1;
//				reason = "兑换失败，材料不足";
//				return;
//			}
//			cellIndex.add(index);
//		}
//
//		for (Integer index : cellIndex) { // 扣除合成材料
//			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.shenbingMake); // 删除物品
//		}
//
//		// 获得一个神兵，添加到背包
//		Item item = ItemJson.instance().getItem(90001);
//		if (isBind == 1) { // 使用绑定材料，物品绑定
//			item.bind();
//		}
//
//		Equip equip = (Equip) item;
//		ShenbingServer.reset(equip);
//		online.getBag().add(item, 1, cellsMap, EItemGet.makeShenbing);
//		BagMessage.sendBag(online, cellsMap);
//
//		LogManager.shenbing(0, equip.getUid(), equip.getSteps(), online); // 兑换神兵日志

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_makeShenbing");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}