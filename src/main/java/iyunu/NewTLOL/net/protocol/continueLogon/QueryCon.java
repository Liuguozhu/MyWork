//package iyunu.NewTLOL.net.protocol.continueLogon;
//
//import iyunu.NewTLOL.net.TLOLMessageHandler;
//
//import com.liteProto.LlpJava;
//import com.liteProto.LlpMessage;
//
///**
// * 查询签到
// * 
// * @author fenghaiyu
// * 
// */
//public class QueryCon extends TLOLMessageHandler {
//	private int result = 0;
//	private String reason = "";
//
//	@Override
//	public void handleReceived(LlpMessage msg) {
////		System.out.println("进入查询连续登录-----------------------------");
//		result = 0;
//		reason = "查询成功!";
//	}
//
//	@Override
//	public void handleReply() throws Exception {
//
//		LlpMessage message = null;
//		try {
//			message = LlpJava.instance().getMessage("s_getContinue");
//
//			message.write("result", result);
//			message.write("reason", reason);
//			if (result == 0) {
//				message.write("con", online.getCon());
////				System.out.println("已连续登录的天数:" + online.getCon());
////				System.out.print("未领取的登录的礼包");
//				for (int i = 0; i < online.getConPick().size(); i++) {
//					message.write("conPick", online.getConPick().get(i).intValue());
////					System.out.print(online.getConPick().get(i) + ",");
//				}
////				System.out.println();
//			}
////			System.out.println("进入查询连续登录结束 result=" + result + "---reason=" + reason + "------------------------------------");
//			channel.write(message);
//		} finally {
//			if (message != null) {
//				message.destory();
//			}
//		}
//	}
// }
