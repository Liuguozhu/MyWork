package iyunu.NewTLOL.net.protocol.billboard;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 排行榜请求
 * 
 * @author SunHonglei
 * 
 */
public class Rank extends TLOLMessageHandler {

	private int index;

	@Override
	public void handleReceived(LlpMessage msg) {

		index = msg.readInt("index");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_rank");

			RankEvent rankEvent = RankEvent.values()[index];

			List<RoleCard> roleCards = rankEvent.getHandler().getTopCards();
			for (int i = 0; i < roleCards.size(); i++) {
				LlpMessage msg = message.write("rankInfos");

				RoleCard roleCard = roleCards.get(i);
				msg.write("index", i);
				msg.write("roleId", roleCard.getId());
				msg.write("name", roleCard.getNick());
				msg.write("level", roleCard.getLevel());
				msg.write("vocation", roleCard.getVocation().getName());
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
