package iyunu.NewTLOL.net.protocol.intensify;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 宝石合成（暂时无用）
 */
public class StoneUp extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
//	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
	int finalIndex = -1;
	// private int ifHasBlind = 0; // 0是没有绑定的物品 1是有
//	private int isBindGold = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
//		result = 0;
//		reason = " 合成成功";
//		cellsMap.clear();
//		// ifHasBlind = 0;
//		isBindGold = msg.readInt("isBind");
//		int index = msg.readInt("index5");
//
//		int index1 = msg.readInt("index1");
//		int index2 = msg.readInt("index2");
//		int index3 = msg.readInt("index3");
//		int index4 = msg.readInt("index4");
//
//		Cell[] cells = online.getBag().getCells();
//		if (index == -1) {
//			result = 1;
//			reason = " 请放入主宝石";
//			return;
//		}
//		if (online.getBag().isFull()) {
//			result = 1;
//			reason = " 请保证背包至少有一个空位";
//			return;
//		}
//
//		Cell stoneCell = cells[index];
//
//		if (stoneCell.getItem() == null) {
//			result = 1;
//			reason = "宝石不存在！";
//			return;
//		}
//		if (stoneCell.getItem().getType() != EItem.stone) {
//			result = 1;
//			reason = "宝石错误，请重新放入再试！";
//			return;
//		}
//		if (stoneCell.getItem().getLevel() >= 10) {
//			result = 1;
//			reason = "宝石已满级";
//			return;
//		}
//		if (isBindGold == 1) {
//			if (!RoleServer.isCanCostGold(online, IntensifyJson.instance().getStoneUp().get(stoneCell.getItem().getLevel()).getGold())) {
//				result = 1;
//				reason = "";
//				SendMessage.refreshNoCoin(online, 2);
//				return;
//			}
//		} else {
//			if (online.getCoin() < IntensifyJson.instance().getStoneUp().get(stoneCell.getItem().getLevel()).getGold()) {
//				result = 1;
//				reason = "";
//				SendMessage.refreshNoCoin(online, 2);
//				return;
//			}
//		}
//		if (stoneCell.getItem().getIsDeal() == 1 && isBindGold == 0) {
//			result = 1;
//			reason = "数据错误";
//			return;
//		}
//		// if (stoneCell.getItem().getIsDeal() == 1) {
//		// ifHasBlind = 1;
//		// }
//
//		// 判断副宝石情况，并返回副宝石的等级list
//		List<Integer> list = judge(cells, index1, index2, index3, index4);
//		if (result == 1) {
//			return;
//		}
//
//		if (list.size() < 1) {
//			result = 1;
//			reason = " 请放入副宝石";
//			return;
//		}
//
//
//		// 如果成功
//		if (StoneManager.instance().up(stoneCell.getItem().getLevel(), list)) {
//			// 宝石合成 成功直接取当前物品ID 加1即为高一级宝石，要求脚本不能随意改变ID
//			Stone store = (Stone) ItemJson.instance().getItem(stoneCell.getItem().getId() + 1);
//			if (isBindGold == 1) {// || ifHasBlind == 1
//				store.bind();
//			}
//			Set<Integer> set = online.getBag().add_returnIndex(store, 1, cellsMap, EItemGet.stoneUp);
//			cost(index1, index2, index3, index4, index, stoneCell, isBindGold);
//			BagMessage.sendBag(online, cellsMap); // 刷新背包
//			for (Integer integer : set) {
//				Cell cell = cells[integer];
//				finalIndex = integer;
//				result = 0;
//				reason = cell.getItem().getName();
//				return;
//			}
//
//		} else {
//			// 如果失败
//			if (StoneManager.instance().reduceLevel(stoneCell.getItem().getLevel())) {
//				// 失败，取低一级宝石
//				Stone store = (Stone) ItemJson.instance().getItem(stoneCell.getItem().getId() - 1);
//				if (isBindGold == 1) {
//					store.bind();
//				}
//				Set<Integer> set = online.getBag().add_returnIndex(store, 1, cellsMap, EItemGet.stoneUpFail);
//
//				cost(index1, index2, index3, index4, index, stoneCell, isBindGold);
//				BagMessage.sendBag(online, cellsMap); // 刷新背包
//				for (Integer integer : set) {
//					Cell cell = cells[integer];
//					finalIndex = integer;
//					result = 2;
//					reason = cell.getItem().getName();
//					return;
//
//				}
//			} else {
//				// 失败，不降级取原宝石
//				Stone store = (Stone) ItemJson.instance().getItem(stoneCell.getItem().getId());
//				if (isBindGold == 1) {
//					store.bind();
//				}
//				Set<Integer> set = online.getBag().add_returnIndex(store, 1, cellsMap, EItemGet.stoneUpFail);
//				cost(index1, index2, index3, index4, index, stoneCell, isBindGold);
//				BagMessage.sendBag(online, cellsMap); // 刷新背包
//				for (Integer integer : set) {
//					Cell cell = cells[integer];
//					finalIndex = integer;
//					result = 2;
//					reason = cell.getItem().getName();
//					return;
//				}
//			}
//		}

	}

	public List<Integer> judge(Cell[] cells, int... index) {
		result = 0;
		reason = " 合成成功";
		List<Integer> list = new ArrayList<>();
		for (int in : index) {
			Cell cell = null;
			if (in != -1) {
				cell = cells[in];
			}
			if (cell != null) {
				if (cell.getItem() == null) {
					result = 1;
					reason = "宝石不存在！";
					return null;
				}
				if (cell.getItem().getType() != EItem.stone) {
					result = 1;
					reason = "宝石错误，请重新放入再试！";
					return null;
				}
				list.add(cell.getItem().getLevel());
			}
		}
		return list;
	}

	public void cost(int index1, int index2, int index3, int index4, int index, Cell stoneCell, int isBindGold) {
		// 扣钱
		// if (isBindGold == 1) {
		// RoleServer.costGold(online,
		// IntensifyJson.instance().getStoneUp().get(stoneCell.getItem().getLevel()).getGold(),
		// EGold.itemMake);
		// } else {
		// RoleServer.costCoin(online,
		// IntensifyJson.instance().getStoneUp().get(stoneCell.getItem().getLevel()).getGold(),
		// EGold.itemMake);
		// }
		//
		// // 扣除材料
		// online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.stoneUp);
		// if (index1 != -1) {
		// online.getBag().removeByIndex(index1, 1, cellsMap,
		// EItemCost.stoneUp);
		// }
		// if (index2 != -1) {
		// online.getBag().removeByIndex(index2, 1, cellsMap,
		// EItemCost.stoneUp);
		// }
		// if (index3 != -1) {
		// online.getBag().removeByIndex(index3, 1, cellsMap,
		// EItemCost.stoneUp);
		// }
		// if (index4 != -1) {
		// online.getBag().removeByIndex(index4, 1, cellsMap,
		// EItemCost.stoneUp);
		// }

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_stoneUp");
			message.write("index", finalIndex);
			message.write("result", result);
			message.write("reason", reason);
			// System.out.println("成功是0：" + result);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
