package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.Iterator;
import java.util.Map.Entry;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 帮派战对阵列表
 * @author LuoSR
 * @date 2014年6月24日
 */
public class AgainstList extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_againstList");

//			int type = 2;
//			int state = 0;
			int time = 0;
//			int week = Time.getWeek();
//			if (week == 7) {
//				if (System.currentTimeMillis() < 淘汰赛ProcessorCenter.结束时间_决赛3) {
//					type = 1;
//				}
//			}
//			if (type != 1 && 循环赛ProcessorCenter.state != 0 && 循环赛ProcessorCenter.state != 1) {
//				type = 0;
//			}
			llpMessage.write("type", GangFightManager.GANG_FIGHT_STATE);
			llpMessage.write("fightNum", 1);
			long now = System.currentTimeMillis();
			if (GangFightManager.GANG_FIGHT_STATE == 1) { // ====================淘汰赛==============================

//				state = 淘汰赛ProcessorCenter.state();
				time = (int) ((GangFightManager.FINISH_TIME - now) / 1000 + 1);

				Iterator<Entry<Integer, FightApplyInfo>> it = GangFightManager.淘汰赛对阵表.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, FightApplyInfo> entry = it.next();
					LlpMessage againstOutInfoMsg = llpMessage.write("againstOutInfoList");
					againstOutInfoMsg.write("index", entry.getKey());
//					System.out.println("index===" + entry.getKey());
					if (entry.getValue() == null) {
						againstOutInfoMsg.write("gangName", "");
//						System.out.println("name===  ");
					} else {
						againstOutInfoMsg.write("gangName", entry.getValue().getGangName());
//						System.out.println("name===" + entry.getValue().getGangName());
					}
				}

			} else { // ====================循环赛==============================

				time = (int) ((GangFightManager.FINISH_TIME - now) / 1000 + 1);
//				state = 循环赛ProcessorCenter.state();

				for (int i = 0; i < GangFightManager.循环赛列表.size(); i = i + 2) {

					FightApplyInfo fightApplyInfo1 = GangFightManager.循环赛列表.get(i);
					FightApplyInfo fightApplyInfo2 = GangFightManager.循环赛列表.get(i + 1);

					LlpMessage againstRoundInfoMsg = llpMessage.write("againstRoundInfoList");
					againstRoundInfoMsg.write("firstGangName", fightApplyInfo1.getGangName());
					againstRoundInfoMsg.write("secondGangName", fightApplyInfo2.getGangName());

					int self = 1;
					if (online.getGangId() != 0 && (fightApplyInfo1.getGangId() == online.getGangId() || fightApplyInfo2.getGangId() == online.getGangId())) {
						self = 0;
					}
					againstRoundInfoMsg.write("type", self);
				}

			}

			llpMessage.write("state", GangFightManager.STATE);
			llpMessage.write("time", time);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
