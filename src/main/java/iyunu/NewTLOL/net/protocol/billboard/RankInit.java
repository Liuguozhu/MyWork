package iyunu.NewTLOL.net.protocol.billboard;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 排行榜列表请求初始化
 * 
 * @author SunHonglei
 * 
 */
public class RankInit extends TLOLMessageHandler {
	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_rankInit");
			for (int i = 0; i < RankEvent.values().length; i++) {
				LlpMessage msg = message.write("names");
				msg.write("type", RankEvent.values()[i].ordinal() + 1);
				msg.write("name", RankEvent.values()[i].getHandler().getTopRatedName());
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
