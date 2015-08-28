package iyunu.NewTLOL.net.protocol.activity.plusMoney;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.model.activity.plusMoney.PlusMoney;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查询累计充值
 * 
 * @author fhy
 * 
 */
public class QueryPlusMoney extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int id = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查询累计充值成功";
		id = msg.readInt("id");
		if (id == -1) {
			int max = ActivityJson.instance().getPlusMoney().size();
			if (online.getRecPlusMoney().size() == max) {
				id = max;
			} else if (online.getRecPlusMoney().size() == 0) {
				id = 1;
			} else {
				id = RoleServer.getPlusMoneyNotRec(online);
			}
		}
		if (id > ActivityJson.instance().getPlusMoney().size() || id <= 0) {
			result = 1;
			reason = "查询累计充值不正确";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_queryPlusMoney");
			llpMessage.write("result", result);
			if (result == 0) {
				PlusMoney p = ActivityJson.instance().getPlusMoney().get(id);
				llpMessage.write("flag", online.getRecPlusMoney().contains(id) ? 1 : 0);
				llpMessage.write("total", ActivityJson.instance().getPlusMoney().size());
				llpMessage.write("id", id);
				llpMessage.write("money", p.getMoney());
				llpMessage.write("dec", p.getDec());
				for (MonsterDropItem m : p.getItems()) {
					LlpMessage msg = llpMessage.write("items");
					msg.write("itemId", m.getItemId());
					msg.write("num", m.getNum());
					msg.write("bind", m.getIsBind());
				}
			}
			llpMessage.write("reason", reason);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}