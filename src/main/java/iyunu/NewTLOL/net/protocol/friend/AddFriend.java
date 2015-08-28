package iyunu.NewTLOL.net.protocol.friend;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.FriendConst;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 申请添加好友
 * @author LSR
 * @date 2012-8-27
 */
public class AddFriend extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Role friend;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "申请成功";

		if (online.getLevel() < 15) {
			result = 1;
			reason = "少侠您的等级还不足以在结交江湖上朋友（15级开启）";
			return;
		}

		Long friendId = msg.readLong("friendId");
		List<Long> friendList = online.getFriendList();
		if (friendId == 0) {
			result = 1;
			reason = "该玩家不存在！";
			return;
		}

		if (!ServerManager.instance().isOnline(friendId)) {
			result = 1;
			reason = "该玩家不在线！";
			return;
		}
		friend = ServerManager.instance().getOnlinePlayer(friendId);
		if (friendList.contains(friendId)) {
			result = 1;
			reason = "该玩家已经是您的好友！";
			return;
		}
		if (friendList.size() >= FriendConst.FRIEND_MAX) {
			result = 1;
			reason = "您的好友数量已达到上限！";
			return;
		}

		if (friendId == online.getId()) {
			result = 1;
			reason = "您不能加自己为好友！";
			return;
		}

		if (friend.getLevel() < 15) {
			result = 1;
			reason = "对方等级不足（15级开启）";
			return;
		}

		if (friend.isCloseFriend()) {
			result = 1;
			reason = "添加失败，对方已屏蔽添加好友";
			return;
		}

		List<Long> list = friend.getApplyFriendList();

		if (list.contains(online.getId())) {
			result = 1;
			reason = "您已在申请列表中";
			return;
		}

		list.add(online.getId());

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addFriend");
			message.write("result", result);
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			SendMessage.refreshFriendHint(friend);
			SendMessage.friendInform(online, "您的好友请求已发出，请等待！");
		}
	}

}
