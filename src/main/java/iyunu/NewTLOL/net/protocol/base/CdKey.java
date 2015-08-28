package iyunu.NewTLOL.net.protocol.base;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.CdKeyManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.base.CdKeyInfo;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.service.iface.cdKey.CdKeyService;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 兑换激活码
 * 
 * @author SunHonglei
 * 
 */
public class CdKey extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "兑换成功！";

		String cdKey = msg.readString("cdKey").toUpperCase();

		CdKeyInfo cdKeyInfo = CdKeyManager.instance().getCdKeyInfo(cdKey);

		if (cdKeyInfo == null) {
			result = 1;
			reason = "此激活码不存在！";
			return;
		}

		if (online.getCdKeys().contains(cdKeyInfo.getType())) {
			result = 1;
			reason = "您已经领取过此类激活码！";
			return;
		}

		if (!CdKeyManager.instance().check(cdKey)) {
			result = 1;
			reason = "此激活码不存在！";
			return;
		}

		Item item = ItemJson.instance().getItem(cdKeyInfo.getItemId());
		if (item == null) {
			result = 1;
			reason = "激活码未生效！";
			return;
		}

		online.getCdKeys().add(cdKeyInfo.getType());

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

//		online.getBag().add(item, 1, cellsMap, EItemGet.cdKey);
		BagServer.add(online, item, 1, cellsMap, EItemGet.cdKey);
		BagMessage.sendBag(online, cellsMap); // 刷新背包

		// ======删除激活码======
		CdKeyService cdKeyService = Spring.instance().getBean("cdKeyService", CdKeyService.class);
		cdKeyService.delete(cdKey);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_cdkey");
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