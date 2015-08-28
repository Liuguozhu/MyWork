package iyunu.NewTLOL.net.protocol.raids;

import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.manager.RaidsManager;
import iyunu.NewTLOL.model.raids.instance.RaidsInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import com.google.common.collect.LinkedHashMultimap;
import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 副本初始化
 * 
 * @author SunHonglei
 * 
 */
public class RaidsInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_raids_init");

			LinkedHashMultimap<Integer, RaidsInfo> map = RaidsJson.instance().getRaidsListMap();
			for (Integer index : map.keySet()) {
				LlpMessage message = llpMessage.write("raidsInfoList");
				message.write("raidsIndex", index);
				for (RaidsInfo raidsInfoRes : map.get(index)) {
					LlpMessage msg = message.write("degreeInfoList");
					msg.write("raidsId", raidsInfoRes.getRaidsId());
					msg.write("degree", raidsInfoRes.getDegree());
				}
				message.write("number", RaidsManager.getRaidsNumber(online, index));
			}
			
			
//			Iterator<Entry<Integer, ArrayList<RaidsInfo>>> it = RaidsJson.instance().getRaidsListMap().entrySet().iterator();
//			while (it.hasNext()) {
//				Entry<Integer, ArrayList<RaidsInfo>> entry = it.next();
//				LlpMessage message = llpMessage.write("raidsInfoList");
//				message.write("raidsIndex", entry.getKey());
//
//				for (RaidsInfo raidsInfoRes : entry.getValue()) {
//					LlpMessage msg = message.write("degreeInfoList");
//					msg.write("raidsId", raidsInfoRes.getRaidsId());
//					msg.write("degree", raidsInfoRes.getDegree());
//				}
//
//				message.write("number", RaidsManager.getRaidsNumber(online, entry.getKey()));
//			}

			long now = System.currentTimeMillis();
			if (RaidsManager.isDouble()) {
				int time = (int) ((Time.getNextTime(21, 00, 00) - now) / 1000);
				llpMessage.write("doubleTime", time);
			} else {
				llpMessage.write("doubleTime", -1);
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}