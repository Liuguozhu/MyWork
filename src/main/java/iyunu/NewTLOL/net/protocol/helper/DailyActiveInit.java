package iyunu.NewTLOL.net.protocol.helper;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpMessage;

/**
 * 暂时无用
 * @author SunHonglei
 *
 */
public class DailyActiveInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

//		LlpMessage llpMessage = null;
//		try {
//			llpMessage = LlpJava.instance().getMessage("s_dailyActive");
//			long now = System.currentTimeMillis();
//			int hour = Time.getHour(now);
//			int min = Time.getMinute(now);
//			for (DailyActive dailyActive : HelperJson.instance().getDailyActives()) {
//				LlpMessage message = llpMessage.write("dailyActiveInfoList");
//				message.write("index", dailyActive.getIndex());
//
//				if (dailyActive.getTimeType() == 0) {
//					message.write("state", 0);
//				} else {
//
//					if (dailyActive.getStartH() < hour && hour < dailyActive.getEndH()) {
//						message.write("state", 0);
//					} else if (dailyActive.getStartH() == hour || hour == dailyActive.getEndH()) {
//
//						if (dailyActive.getStartM() <= min && min < dailyActive.getEndM()) {
//							message.write("state", 0);
//						} else {
//							message.write("state", 1);
//						}
//
//					} else {
//						message.write("state", 1);
//					}
//				}
//
//			}
//			channel.write(llpMessage);
//		} finally {
//			if (llpMessage != null) {
//				llpMessage.destory();
//			}
//		}

	}
}