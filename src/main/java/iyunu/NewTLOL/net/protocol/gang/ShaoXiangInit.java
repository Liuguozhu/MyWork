package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.json.RoleJson;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.gang.EGangLog;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangLogInfo;
import iyunu.NewTLOL.model.gang.event.EGangSXLevel;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 烧香初始化
 * 
 * @author fenghaiyu
 * 
 */
public class ShaoXiangInit extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;
		try {

			Gang gang = GangManager.instance().getGang(online.getGangId());
			if (gang != null) {

				llpMessage = LlpJava.instance().getMessage("s_shaoXiangInit");
				llpMessage.write("level", online.getShaoXiangLevel());
				llpMessage.write("exp", online.getShaoXiangExp());
				llpMessage.write("expMax", EGangSXLevel.getExp(online.getShaoXiangLevel()));

				BuffRole goldBuff = RoleJson.instance().getbuffRoleResById(EGangSXLevel.getEGangSXLevel(online.getShaoXiangLevel()).getGoldBuffId());
				BuffRole expBuff = RoleJson.instance().getbuffRoleResById(EGangSXLevel.getEGangSXLevel(online.getShaoXiangLevel()).getExpBuffId());
				String addDesc = "无";
				if (goldBuff != null) {
					addDesc = goldBuff.getDesc();
				}
				if (expBuff != null) {
					addDesc += "，" + expBuff.getDesc();
				}

				llpMessage.write("addDesc", addDesc);
				llpMessage.write("numLimit", GangManager.SHAOXIANG_LIMIT + online.getVip().getLevel().getShaoxiangAdd());
				llpMessage.write("num", GangManager.SHAOXIANG_LIMIT + online.getVip().getLevel().getShaoxiangAdd() - online.getShaoXiangNum());

				for (GangLogInfo gangLogInfo : gang.getGangLog()) {
					if (gangLogInfo.getType().equals(EGangLog.shaoxiang)) {
						llpMessage.write("logList", gangLogInfo.getName() + " " + gangLogInfo.getLog());
					}
				}
			}
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
