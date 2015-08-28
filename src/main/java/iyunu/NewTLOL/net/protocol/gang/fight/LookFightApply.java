package iyunu.NewTLOL.net.protocol.gang.fight;

import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.FightApplyInfo;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 查看帮派战报名
 * @author LuoSR
 * @date 2014年6月23日
 */
public class LookFightApply extends TLOLMessageHandler {

	private int page;
	private static int NUMBER_OF_PAGE = 9;

	@Override
	public void handleReceived(LlpMessage msg) {
		page = 1;
		page = msg.readInt("page");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_lookFightApply");

			int sum = (GangFightManager.循环赛周积分排名.size() - 1) / NUMBER_OF_PAGE + 1;
			message.write("sum", sum);
			page = Util.matchMin(page, sum);
			message.write("page", page);

			int start = 0 + (page - 1) * NUMBER_OF_PAGE;
			int end = page * NUMBER_OF_PAGE;
			end = Util.matchSmaller(end, GangFightManager.循环赛周积分排名.size());

			for (int i = start; i < end; i++) {
				FightApplyInfo fightApplyInfo = GangFightManager.循环赛周积分排名.get(i);

				Gang gang = GangManager.instance().getGang(fightApplyInfo.getGangId());
				if (gang != null) {

					LlpMessage llpMessage = message.write("fightApplyInfoList");
					llpMessage.write("gangName", fightApplyInfo.getGangName());
					llpMessage.write("num", gang.getMembers().size());
					llpMessage.write("size", gang.getSize());
					llpMessage.write("score", fightApplyInfo.getScore());
					if (GangFightManager.报名列表.contains(fightApplyInfo.getGangId())) {
						llpMessage.write("isApply", 1);
					} else {
						llpMessage.write("isApply", 0);
					}
				}
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
