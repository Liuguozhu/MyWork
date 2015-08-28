package iyunu.NewTLOL.net.protocol.qiancengta;

import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 千层塔初始化
 * @author LuoSR
 * @date 2014年8月14日
 */
public class QctInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
		// LogManager.info("======千层塔初始化======");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_qctInit");
			message.write("currentFloorId", online.getCurrentFloorId());
			message.write("historyFloorId", online.getHistoryFloorId());
			message.write("nullResetNum", online.getNullResetNum());
			int currentNum = online.getVip().getLevel().getQiancengtaAdd() - online.getMoneyResetNum();
			message.write("moneyResetNum", currentNum > 0 ? currentNum : 0);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
