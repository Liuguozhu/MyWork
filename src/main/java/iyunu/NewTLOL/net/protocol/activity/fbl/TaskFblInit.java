package iyunu.NewTLOL.net.protocol.activity.fbl;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.message.ActivityMessage;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.concurrent.CopyOnWriteArrayList;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskFblInit extends TLOLMessageHandler {

	private int page;
	private static int NUMBER_OF_PAGE = 8;

	@Override
	public void handleReceived(LlpMessage msg) {
//		LogManager.logOut("------>发布令任务初始化<------");
		page = msg.readInt("page");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFblInit");
			CopyOnWriteArrayList<Long> list = (CopyOnWriteArrayList<Long>) ActivityManager.activityFblIds.clone();
			int sum = list.size();
			int sumPage = (sum - 1) / NUMBER_OF_PAGE + 1;

			page = Util.matchBigger(page, 0);
			page = Util.matchSmaller(page, sumPage);

			llpMessage.write("page", page);
			llpMessage.write("sumPage", sumPage);

			int start = 0 + (page - 1) * NUMBER_OF_PAGE;
			int end = page * NUMBER_OF_PAGE;
			end = Util.matchSmaller(end, sum);

			for (int i = start; i < end; i++) {
				long id = list.get(i);
				if (ActivityManager.activityFblInfos.containsKey(id)) {
					ActivityFblInfo activityFblInfo = ActivityManager.activityFblInfos.get(id);
					LlpMessage message = llpMessage.write("taskFblInfoList");
					message.write("id", id);

					message.write("color", activityFblInfo.getColor().ordinal());
					message.write("senderName", activityFblInfo.getSenderName());
					message.write("awardCoin", activityFblInfo.getCoin());
					message.write("awardExp", activityFblInfo.getExp());
					message.write("taskId", activityFblInfo.getTaskId());
					message.write("state", activityFblInfo.getState());

					message.write("time", 0);
				}
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

		ActivityMessage.refreshTaskFblNum(online);

//		LogManager.logOut("======>发布令任务初始化<======");
	}
}