package iyunu.NewTLOL.net.protocol.friend;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpMessage;

/**
 * @function 请求添加好友列表
 * @author LuoSR
 * @date 2013年12月13日
 */
public class ApplyList extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {

		List<Long> applyFriendList = online.getApplyFriendList();
		SendMessage.refreshFriendOrBlackList(online, applyFriendList, 1);

	}

}
