package iyunu.NewTLOL.net.protocol.mining;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.battle.FightServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class RobMining extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "抢矿成功";
	private int index = 0;
	Mining mining = null;
	private int page = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "抢矿成功";
		index = msg.readInt("index");
		page = msg.readInt("page");
		SendMessage.refreshMiningList(online, page);

		if (index < 1 || index > MiningJson.instance().getMining().size()) {
			result = 1;
			reason = "抢矿错误！";
			return;
		}
		mining = MiningManger.instance().getMiningMap().get(index);
		if (mining == null) {
			result = 1;
			reason = "抢矿信息错误！";
			return;
		}
		if (!MiningManger.instance().ifPageIndex(page, index)) {
			result = 1;
			reason = "抢矿页数信息发生错误！";
			return;
		}
		if (mining.getRole() != null) {
			if (mining.getRole().getId() == online.getId()) {
				result = 1;
				reason = "这是自己的矿！";
				return;
			}
		}
		if (online.getTeam() != null) {
			result = 1;
			reason = "不可组队进入！";
			return;
		}
		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能抢矿";
			return;
		}

		int random = Util.getRandom(1, 100);
		if (online.getVip().getLevel().getMiningFragmentCost() * 100 < random) {
			int index = online.getBag().isInBagByType(EItem.fragment, 1);
			if (index == -1) {
				index = online.getBag().isInBagByType(EItem.fragment, 0);
				if (index == -1) {
					result = 1;
					reason = "没有残玉碎片！";
					return;
				}
			}

			// 删除1个残玉碎片
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.mining);
			BagMessage.sendBag(online, cellsMap);
		}

		// 已占有一个矿的提示
		if (MiningManger.instance().ifHadAMining(online.getId())) {
			result = 1;
			reason = "每人只能同时占一个矿！";
			return;
		}

		if (mining.getFlag() == 0) {
			mining.setFlag(1);
			mining.setRole(online.copyMinning());
			mining.setStartTime(System.currentTimeMillis());
			result = 3;
			reason = "矿区已无人，直接占领矿区成功";
			return;
		}

		// 进入战斗
		boolean ifWin = FightServer.miningFight(online, mining.getRole());
		String notice = "";
		// 进入战斗后,取得战斗结果，发送给战斗人弹窗小消息
		Role role = ServerManager.instance().getOnlinePlayer(mining.getRole().getId());
		// 被打人id
		long beRobId = mining.getRole().getId();
		if (ifWin) {
			int totalValue = MiningManger.instance().countRec(mining.getStartTime(), mining.getValue());
			totalValue += totalValue * online.getVip().getLevel().getMiningGoldAdd();
			RoleServer.addGoldWithOutNotice(online, totalValue, EGold.mining);
			SendMessage.refreshRewardDelay(online, StringControl.yel("战斗胜利，您获得了" + StringControl.grn(totalValue) + "绑银！"));
			mining.setRole(online.copyMinning());
			mining.setStartTime(System.currentTimeMillis());
			notice = StringControl.yel(online.getNick()) + "在矿区成功抢占了你的矿，" + StringControl.red("太令人气愤了");
		} else {
			notice = StringControl.yel(online.getNick()) + "在矿区试图抢占你的矿，" + StringControl.grn("被你打的落荒而逃!");
		}
		if (role != null) {
			SendMessage.miningNotice(role, notice, online.getId());
		} else {
			ConcurrentHashMap<Long, List<String>> tipMap = MiningManger.instance().getTipMap();
			if (tipMap.containsKey(beRobId)) {
				tipMap.get(beRobId).add(notice);
			} else {
				List<String> tipList = new ArrayList<>();
				tipList.add(notice);
				tipMap.put(beRobId, tipList);
			}
		}

		result = 2;
		reason = "进入战斗！";
		// SendMessage.refreshMiningList(online, page);
		return;
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_robMining");
			message.write("result", result);
			message.write("reason", reason);
			// if (result == 0) {
			// MiningPage miningPage =
			// MiningManger.instance().getPageMining(page);
			// List<Mining> list = miningPage.getMiningList();
			//
			// for (int i = 0; i < list.size(); i++) {
			// LlpMessage msg = message.write("minings");
			// Mining mining = list.get(i);
			// msg.write("index", i + 1);
			// msg.write("type", mining.getType());
			// msg.write("flag", mining.getFlag());
			// msg.write("name", mining.getName());
			// msg.write("rec",
			// MiningManger.instance().countRec(mining.getStartTime(),
			// mining.getValue()));
			// }
			// message.write("page", miningPage.getPage());
			// message.write("totalPage", miningPage.getTotalPage());
			// }
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
