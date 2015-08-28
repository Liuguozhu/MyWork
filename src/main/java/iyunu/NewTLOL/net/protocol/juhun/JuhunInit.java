package iyunu.NewTLOL.net.protocol.juhun;

import iyunu.NewTLOL.manager.JuHunManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class JuhunInit extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Integer> nums = null;
	private int wuHunNum = 0;// 本次得到的武魂数
	private int tum = 0;// 0无上次牌型 1 有上次牌型

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "聚魂初始化成功";
//		System.out.println("进入聚魂初始化");
		wuHunNum = 0;
		tum = 0;
		if (JuHunManager.instance.getMap().containsKey(online.getId())) {
			tum = 1;
			nums = JuHunManager.instance.getMap().get(online.getId());
		}
		// 给本次摇出来的武魂赋值,发给客户端显示用
		if (tum == 1) {
			wuHunNum = JuHunManager.instance.getNewWuhunNum(nums);
		}
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;

		try {
			message = LlpJava.instance().getMessage("s_initJuhun");
			message.write("tum", tum);
			message.write("result", result);
			message.write("reason", reason);
			if (tum == 1) {
				for (int i = 0; i < nums.size(); i++) {
					message.write("nums", nums.get(i));
					System.out.print(nums.get(i));
				}
				message.write("wuHunNum", wuHunNum);
			}
			// System.out.println("聚魂初始化tum:" + tum);
			// if (result == 0) {
			//
			// if (tum == 1) {
			// System.out.println("聚魂初始化有上次牌型");
			// for (int i = 0; i < nums.size(); i++) {
			// message.write("nums", nums.get(i));
			// System.out.print(nums.get(i));
			// }
			// System.out.println();
			// System.out.println("聚魂初始化结束");
			// message.write("wuHunNum", wuHunNum);
			// }
			// }
			channel.write(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
