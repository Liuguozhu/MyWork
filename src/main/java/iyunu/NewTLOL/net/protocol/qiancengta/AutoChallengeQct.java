package iyunu.NewTLOL.net.protocol.qiancengta;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.QiancengtaJson;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.qiancengta.instance.QiancengtaInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 自动挑战
 * @author LuoSR
 * @date 2014年8月15日
 */
public class AutoChallengeQct extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "挑战成功";
		cellsMap.clear();

		int currentFloorId = msg.readInt("currentFloorId");

		if (online.isPrBattle()) {
			result = 1;
			reason = "已申请战斗";
			return;
		}

		if (online.getTeam() != null) {
			result = 1;
			reason = "组队状态时不能挑战千层塔";
			return;
		}

		if (currentFloorId > QiancengtaJson.instance().getQiancengtas().size()) {
			result = 1;
			reason = "已经全部挑战完成";
			return;
		}

		if (online.getCurrentFloorId() != currentFloorId) {
			result = 1;
			reason = "不能挑战";
			return;
		}

		if (currentFloorId > online.getHistoryFloorId()) {
			result = 2;
			reason = "已到历史最高，请手动挑战";
			return;
		}

		QiancengtaInfo qiancengtaInfo = QiancengtaJson.instance().getQiancengtaMap().get(currentFloorId);

		// ======获得银两======
		int gold = qiancengtaInfo.getGold();
		// ======获得经验======
		int exp = qiancengtaInfo.getExp();

		// ======添加银两======
		RoleServer.addGold(online, gold, EGold.qiancengta);
		// ======添加经验======
		RoleServer.addExp(online, exp, EExp.qiancengta);

		// ======获得物品======
		MonsterDropItem monsterItem = qiancengtaInfo.drop();
		if (monsterItem != null) {
			// ======添加物品======
			Item item = ItemJson.instance().getItem(monsterItem.getItemId());
			if (item != null) {
				BagServer.add(online, item, monsterItem.getNum(), cellsMap, EItemGet.qiancengta);

				LogManager.qiancengta(online.getId(), online.getUserId(), EItemGet.qiancengta.ordinal(), item.getId(), monsterItem.getNum(), 0, gold, exp);
			}
		}
		// ------------------每日任务---------------------
		try {
			List<Integer> dailyFudi = DailyManager.instance().getDailyFuDiList();
			if (dailyFudi != null && dailyFudi.size() > 0) {
				for (Integer dailyId : dailyFudi) {
					if (DailyManager.instance().checkEvent(dailyId)) {
						DailyManager.instance().finishDailyFudi(online.getDailyMap().get(dailyId), online, online.getCurrentFloorId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ------------------每日任务---------------------

		online.setCurrentFloorId(online.getCurrentFloorId() + 1);

		SendMessage.sendCurrentFloorId(online);
		BagMessage.sendBag(online, cellsMap); // 刷新背包
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_autoChallengeQct");
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
