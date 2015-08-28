package iyunu.NewTLOL.net.protocol.jingMai;

import iyunu.NewTLOL.manager.JingMaiManager;
import iyunu.NewTLOL.model.role.Property;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 经脉说明
 * @author fenghaiyu
 * @date 2014-5-31
 */
public class JingMaiText extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_jinMaiText");
			Property pro = online.getPropertyJingMai();
			for (int i = 0; i < 15; i++) {
				LlpMessage msg = message.write("jingMais");
				msg.write("name", JingMaiManager.names[i]);
				msg.write("value", JingMaiManager.getValueByProperty(pro, i));
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
