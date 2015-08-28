package iyunu.NewTLOL.net.protocol.kuai3;

import iyunu.NewTLOL.manager.Kuai3Manager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryResult extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "查询快3结果成功";

	@Override
	public void handleReceived(LlpMessage msg) {

	}

	@Override
	public void handleReply() throws Exception {
		// System.out.println("进入快3结果查询");
		result = 0;
		reason = "查询快3结果成功";

		int turns = Kuai3Manager.instance().getTurns();
		int comeOut = Kuai3Manager.instance().getResultNow();
		int timeout = Kuai3Manager.instance().getTimeout();
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_QueryResultKuai3");
			message.write("result", result);
			message.write("reason", reason);
			message.write("turns", turns);
			// System.out.println("猜猜看turns:" + turns);
			message.write("comeOut", comeOut);
			// System.out.println("猜猜看comOut:" + comeOut);
			message.write("timeout", timeout);
			// System.out.println("猜猜看timeOut:" + timeout);
			for (int i = 0; i < Kuai3Manager.instance().getRecord().size(); i++) {
				LlpMessage msg = message.write("records");
				msg.write("turn", Kuai3Manager.instance().getRecord().get(i).getTurn());
				msg.write("record", Kuai3Manager.instance().getRecord().get(i).getResult());
//				System.out.println("第" + Kuai3Manager.instance().getRecord().get(i).getTurn() + "期：" + Kuai3Manager.instance().getRecord().get(i).getResult());
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
