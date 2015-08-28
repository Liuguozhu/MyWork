package iyunu.NewTLOL.net.protocol.mall;

import iyunu.NewTLOL.manager.BaseManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.mall.EMall;
import iyunu.NewTLOL.model.mall.instance.Mall;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 商城初始化
 * 
 * @author SunHonglei
 * 
 */
public class InitMall extends TLOLMessageHandler {

	private int type;
	private int page;

	@Override
	public void handleReceived(LlpMessage msg) {
		type = msg.readInt("type");
		page = msg.readInt("page");

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;

		try {
			llpMessage = LlpJava.instance().getMessage("s_initMall");
			boolean isOpen = true;
			EMall eMall = EMall.values()[type];
			if (eMall.equals(EMall.vip) && !online.getVip().isVip(EVip.diamond)) {
				isOpen = false;
			}

			if (isOpen) {
				int totalPage = (eMall.getMalls().size() - 1) / BaseManager.NUMBER_OF_PAGE + 1;
				llpMessage.write("totalPage", totalPage);
				int start = 0 + (page - 1) * BaseManager.NUMBER_OF_PAGE;
				int end = page * BaseManager.NUMBER_OF_PAGE;
				end = Util.matchSmaller(end, eMall.getMalls().size());

				for (int i = start; i < end; i++) {
					Mall mall = eMall.getMalls().get(i);
					LlpMessage message = llpMessage.write("mallInfoList");

					message.write("index", mall.getIndex());
					message.write("itemId", mall.getItemId());
					message.write("name", mall.getName());
					message.write("price", mall.getPrice());
					message.write("icon", mall.getIcon());

					if (eMall.equals(EMall.gang)) {
						if (online.getGangId() != 0) {
							Gang gang = GangManager.instance().getGang(online.getGangId());
							if (gang.getLevel() >= mall.getLevel()) {
								message.write("isBuy", 0);
							} else {
								message.write("isBuy", 1);
							}
						} else {
							message.write("isBuy", 1);
						}
					} else {
						message.write("isBuy", 0);
					}
					message.write("level", mall.getLevel());
					message.write("off", mall.getOff());
				}
			} else {
				llpMessage.write("totalPage", 1);
			}

			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
