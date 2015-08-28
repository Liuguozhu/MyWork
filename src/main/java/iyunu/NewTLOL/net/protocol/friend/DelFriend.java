package iyunu.NewTLOL.net.protocol.friend;

import iyunu.NewTLOL.enumeration.EFriendEvent;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.friend.FriendServer;
import iyunu.NewTLOL.util.FriendConst;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 删除好友
 * @author LSR
 * @date 2012-8-27
 */
public class DelFriend extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "删除好友成功";
	private long friendId;

	@Override
	public void handleReceived(LlpMessage msg) {
		friendId = msg.readLong("friendId");
		FriendServer.delFriend(online, friendId);
		LogManager.friend(online, friendId, EFriendEvent.删除好友);
		result = 0;
		reason = "删除好友成功";
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_delFriend");
			message.write("result", result);
			message.write("reason", reason);
			message.write("totleNum", FriendConst.FRIEND_MAX);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			List<Long> friendList = online.getFriendList();
			SendMessage.refreshFriendOrBlackList(online, friendList, 2);
		}
	}

}
