package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.service.iface.user.UserService;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 绑定手机号
 * 
 * @author SunHonglei
 * 
 */
public class Binding extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "绑定成功";

		String phone = msg.readString("phone");

		if (!IlllegalWordManager.matchPhone(phone)) {
			result = 1;
			reason = "手机号码格式不对";
			return;
		}

		UserService userService = Spring.instance().getBean("userService", UserService.class);
		String oldPhone = userService.queryPhone(online.getUserId());
		if (oldPhone != null && !oldPhone.equals("")) {
			result = 1;
			reason = "已绑定过手机号";
			return;
		}

		userService.updatePhone(online.getUserId(), phone);
		reason = "绑定成功，绑定手机号为：" + phone;
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_binding");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}

}
