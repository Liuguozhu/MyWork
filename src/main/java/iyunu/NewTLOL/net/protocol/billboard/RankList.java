package iyunu.NewTLOL.net.protocol.billboard;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.manager.BillboardManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.billboard.TopBoard;
import iyunu.NewTLOL.model.billboard.level.LevelBoard;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 排行榜列表请求
 * 
 * @author SunHonglei
 * 
 */
public class RankList extends TLOLMessageHandler {
	private int type;
	private int page;
	private int result;
	private String reason = "";
	private RankEvent rankEvent = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		type = msg.readInt("type");
		page = msg.readInt("page");
		result = 0;
		reason = "请求成功";
		rankEvent = null;
		if (type > RankEvent.values().length + 1 || type < 1) {
			result = 1;
			reason = "请求排行榜错误";
			return;
		}
		// TODO 判断page
		rankEvent = RankEvent.values()[type - 1];
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_rankList");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {

				LlpMessage msg = message.write("rankInfoLists");
				String topRatedName = rankEvent.getHandler().getTopRatedName();
				msg.write("rankName", topRatedName);
				String[] titles = rankEvent.getHandler().getTitle();
				String[] titleIds = rankEvent.getHandler().getTitleIds();
				List<RoleCard> topCards = rankEvent.getHandler().getTopCards();
				for (int k = 0; k < titles.length; k++) {
					LlpMessage msg1 = msg.write("titles");
					msg1.write("name", titles[k]);
					msg1.write("id", titleIds[k]);
				}

				int size = Util.matchSmaller(TopBoard.SHOW_MAX, topCards.size());
				int page_max = BillboardManager.MAX_NUM;
				int start = (page - 1) * page_max + 1;
				int end = start + page_max > size ? size + 1 : start + page_max;

				int totalpage = (size - 1) / page_max + 1;
				totalpage = totalpage == 0 ? 1 : totalpage;// 总页数
				message.write("page", page);
				message.write("totalpage", totalpage);
				for (int j = start - 1; j < end - 1; j++) {
					LlpMessage msg1 = msg.write("rankInfos");
					RoleCard rc = topCards.get(j);
					msg1.write("index", j + 1);
					msg1.write("roleId", rc.getId());
					msg1.write("name", rc.getNick());
//					System.out.println(rc.getNick() + "#######");
					msg1.write("vocation", rc.getVocation().getName());
					// if ("绑银榜".equals(topRatedName)) {
					// msg1.write("value", (long) rc.getGold());
					// }
					if ("等级榜".equals(topRatedName)) {
						msg1.write("value", (long) rc.getLevel());
					}
					if ("战力榜".equals(topRatedName)) {
						msg1.write("value", rc.getPower());
					}
					if ("洞天榜".equals(topRatedName)) {
						msg1.write("value", (long) rc.getHistoryFloorId());
					}
				}
			}
			channel.write(message);

			SendMessage.sendRankingLevel(online, LevelBoard.INSTANCE.getRank(online.getId()));
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
