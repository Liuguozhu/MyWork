package iyunu.NewTLOL.net.protocol.friend;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.FriendConst;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 好友
 * @author LSR
 * @date 2012-8-27
 */
public class FriendInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_friendInit");
			message.write("totleNum", FriendConst.FRIEND_MAX);
			channel.write(message);

		} finally {
			if (message != null) {
				message.destory();
			}
		}

		List<Long> friendList = online.getFriendList();
		SendMessage.refreshFriendOrBlackList(online, friendList, 2);
	}
}
