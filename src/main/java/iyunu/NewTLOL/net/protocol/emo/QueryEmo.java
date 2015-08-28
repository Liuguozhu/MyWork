package iyunu.NewTLOL.net.protocol.emo;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.model.mining.emoMap.EmoMapDesRes;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryEmo extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryEmo");
			EmoMapDesRes emo1 = MiningJson.instance().getEmoDes().get(MiningJson.instance().getEmoMapInfoRes().getEmo1Map());
			EmoMapDesRes emo2 = MiningJson.instance().getEmoDes().get(MiningJson.instance().getEmoMapInfoRes().getEmo2Map());
			message.write("restCount", EmoMapManager.MAX - online.getEmoCount());
			LlpMessage msg1 = message.write("emos");
			msg1.write("map", 1);
			msg1.write("flag", EmoMapManager.EMO1_STATE);
			msg1.write("level", emo1.getLevel());
			msg1.write("name", emo1.getName());
			msg1.write("time", emo1.getTime());
			msg1.write("awardDes", emo1.getAwardDes());
			LlpMessage msg2 = message.write("emos");
			msg2.write("map", 2);
			msg2.write("flag", EmoMapManager.EMO2_STATE);
			msg2.write("level", emo2.getLevel());
			msg2.write("name", emo2.getName());
			msg2.write("time", emo2.getTime());
			msg2.write("awardDes", emo2.getAwardDes());

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
