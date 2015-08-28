package iyunu.NewTLOL.net.protocol.qiancengta;

import iyunu.NewTLOL.model.billboard.qct.QctBoard;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 千层塔排行榜
 * @author LuoSR
 * @date 2014年8月15日
 */
public class QctRanking extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		LogManager.info("=========千层塔排行榜=========");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_qctRanking");

			List<RoleCard> list = QctBoard.INSTANCE.getTopCards();
			for (RoleCard roleCard : list) {
				LlpMessage qctRankingInfo = message.write("qctRankingInfoList");
				qctRankingInfo.write("roleId", roleCard.getId());
				qctRankingInfo.write("name", roleCard.getNick());
				qctRankingInfo.write("historyFloorId", roleCard.getHistoryFloorId());
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
