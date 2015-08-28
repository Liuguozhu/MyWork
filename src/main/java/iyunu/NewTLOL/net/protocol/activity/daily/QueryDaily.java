package iyunu.NewTLOL.net.protocol.activity.daily;

import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.daily.DailyModelRole;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import java.util.Iterator;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查看日常任务
 * 
 * @author fhy
 * 
 */
public class QueryDaily extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
//		DailyManager.instance().compareDaily(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_queryDaily");
			Iterator<DailyModelRole> it = online.getDailyMap().values().iterator();
			while (it.hasNext()) {
				DailyModelRole d = it.next();
				DailyModel dd = DailyManager.instance().getMap().get(d.getId());
				LlpMessage msg = llpMessage.write("dailys");
				msg.write("name", dd.getName());
				msg.write("des", Time.getDateMDStr(dd.getStartTime().getTime()) + "-" + Time.getDateMDStr(dd.getEndTime().getTime()) + "0点，" + dd.getDes());
				msg.write("type", dd.getType().ordinal());
				msg.write("id", d.getId());
				msg.write("target", d.getTarget());
				msg.write("count", d.getCount());
				msg.write("flag", d.getRec());
				List<MonsterDropItem> itemList = dd.getAward();
				for (int i = 0; i < itemList.size(); i++) {
					LlpMessage message = msg.write("items");
					message.write("itemId", itemList.get(i).getItemId());
					message.write("num", itemList.get(i).getNum());
					message.write("bind", itemList.get(i).getIsBind());
				}
				msg.write("partner", dd.getPartner());
				msg.write("money", dd.getMoney());
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}