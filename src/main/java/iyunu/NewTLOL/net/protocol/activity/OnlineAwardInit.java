package iyunu.NewTLOL.net.protocol.activity;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 在线礼包初始化
 * @author LuoSR
 * @date 2014年5月29日
 */
public class OnlineAwardInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {

		List<OnlineAwardInfo> onlineAwardInfoList = ActivityJson.instance().getActivityOnlineList();

		int onlineTime = online.getOnlineTime() + (int) ((System.currentTimeMillis() - online.getLogonTime()) / Time.MILLISECOND);
		for (OnlineAwardInfo onlineAwardInfo : onlineAwardInfoList) {
			if (onlineAwardInfo.getTime() * Time.MINUTE_SECOND <= onlineTime && online.getOnlineAwardStateMap().get(onlineAwardInfo.getId()) != 2) {
				online.getOnlineAwardStateMap().put(onlineAwardInfo.getId(), 1);
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_onlineAwardInit");
			int time = online.getOnlineTime() + (int) ((System.currentTimeMillis() - online.getLogonTime()) / Time.MILLISECOND);
			message.write("time", time);
			List<OnlineAwardInfo> list = ActivityJson.instance().getActivityOnlineList();
			for (OnlineAwardInfo onlineAwardInfo : list) {
				LlpMessage llpMessage = message.write("onlineAwardInfoList");
				llpMessage.write("id", onlineAwardInfo.getId());
				int state = online.getOnlineAwardStateMap().get(onlineAwardInfo.getId());
				llpMessage.write("state", state);

				Item item = ItemJson.instance().getItem(onlineAwardInfo.getItemId());
				if (item != null) {
					llpMessage.write("itemId", onlineAwardInfo.getItemId());
					llpMessage.write("itemNum", onlineAwardInfo.getItemNum());
					llpMessage.write("icon", item.getIcon());
				}

			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
