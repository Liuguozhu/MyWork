package iyunu.NewTLOL.net.protocol.trials;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.message.TrialsMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.EMapType;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.instance.Monster;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 扫荡
 * @author LuoSR
 * @date 2014年4月17日
 */
public class SweepingTrials extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {

		// ======重置数据======
		result = 0;
		reason = "扫荡成功";

		// ======获取参数======
		int trialsId = msg.readInt("trialsId");

		if (online.getTeam() != null) {
			result = 1;
			reason = "组队状态时不能扫荡试炼";
			return;
		}

		if (!online.getMapInfo().getBaseMap().getType().equals(EMapType.common)) {
			result = 1;
			reason = "此地图不能扫荡试炼";
			return;
		}

		if (online.getTrials().get(trialsId).getState() != 1) {
			result = 1;
			reason = "请通关后在扫荡";
			return;
		}

		if (online.getTrials().get(trialsId).getPosition() >= 8) {
			result = 1;
			reason = "不能扫荡";
			return;
		}

		if (!online.getVip().isVip(EVip.gold)) {
			result = 1;
			reason = "黄金以上的VIP大侠才能使用扫荡";
			return;
		}

		online.getTrials().get(trialsId).setPosition(8);

		TrialsInfo trialsInfo = RaidsJson.instance().getTrialsMap().get(trialsId);

		Map<Integer, Integer> monsterMap = trialsInfo.getMonsterMap();

		int sumGold = 0;
		int roleExp = 0;
		HashMap<Integer, MonsterDropItem> itemIds = new HashMap<Integer, MonsterDropItem>();
		Set<Entry<Integer, Integer>> set = monsterMap.entrySet();
		for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
			Entry<Integer, Integer> entry = it.next();
			int position = entry.getKey();
			int monsterGroupId = entry.getValue();

			MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(monsterGroupId);
			for (Long monsterId : monsterGroup.getMonsters()) {
				Monster monster = MonsterJson.instance().getMonster(monsterId);

				sumGold += monster.getGold();
				roleExp += monster.getRoleExp();

				// ======掉落物品======
				for (MonsterDropItem monsterDropItem : monster.drop()) {
					itemIds.put(monsterDropItem.getItemId(), monsterDropItem);
				}
			}

			LogManager.trials(online, trialsId, position); // 试炼日志
		}

		// ======获得银两======
		RoleServer.addGold(online, sumGold, EGold.trials);
		// ======获得经验======
		RoleServer.addExp(online, roleExp, EExp.trials);

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

		// ======获得物品======
		Iterator<Entry<Integer, MonsterDropItem>> itItem = itemIds.entrySet().iterator();
		while (itItem.hasNext()) {
			Entry<Integer, MonsterDropItem> entryItrm = itItem.next();
			Item item = ItemJson.instance().getItem(entryItrm.getKey());
			if (item != null) {
				item.setIsDeal(entryItrm.getValue().getIsBind());
				BagServer.add(online, item, entryItrm.getValue().getNum(), cellsMap, EItemGet.trials);
			}
		}

		TrialsMessage.sendKillPosition(online, trialsId);
		TrialsMessage.refreshTrialsState(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_sweepingTrials");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}