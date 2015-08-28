package iyunu.NewTLOL.net.protocol.sign;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查询签到
 * 
 * @author fenghaiyu
 * 
 */
public class QuerySign extends TLOLMessageHandler {
	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入查询签到-----------------------------");
		result = 0;
		reason = "查询成功!";

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_querySign");

			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				// System.out.print("已签到的天数");
				for (int i = 0; i < online.getSignList().size(); i++) {
					message.write("signList", online.getSignList().get(i).intValue());
					// System.out.print(online.getSignList().get(i) + ",");
				}
				// System.out.println();
				// System.out.print("已签到的礼包");
				for (int i = 0; i < online.getHavePickSign().size(); i++) {
					message.write("havePickSign", online.getHavePickSign().get(i).intValue());
					// System.out.print(online.getHavePickSign().get(i) + ",");
				}
				// System.out.println();
			}
			// 今天是第几天
			message.write("nowdate", online.getSignDay());
			// System.out.println("cansign:   "+online.getHaveSign());
			message.write("canSign", online.getHaveSign());
			// 今天已经签到，并且签到数小于30才可以补签
			message.write("canAddSign", (online.getHaveSign() == 0 && online.getSignList().size() < online.getSignDay()) ? 1 : 0);
			// System.out.println("canAddSign:  "+((online.getHaveSign() == 0 &&
			// online.getSignList().size() < online.getSignDay()) ? 1 : 0));
			// System.out.println("进入查询签到结束 result=" + result + "---reason=" +
			// reason + "------------------------------------");
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
