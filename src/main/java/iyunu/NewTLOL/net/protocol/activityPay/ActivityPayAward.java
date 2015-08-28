package iyunu.NewTLOL.net.protocol.activityPay;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ActivityPayAward extends TLOLMessageHandler {

	private int result = 0;
	private String reason;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "领取成功";

		int type = msg.readInt("type");
		int index = msg.readInt("index");

		PayActivityInfo activityInfo = null;
		int getType = -1;
		switch (type) {
		case 1:
			activityInfo = ActivityPayJson.instance().getPaySingles().get(index);
			getType = EItemGet.paySingle.ordinal();
			break;
		case 2:
			activityInfo = ActivityPayJson.instance().getPayAccumulateWeek().get(index);
			getType = EItemGet.payAccumulateWeek.ordinal();
			break;
		case 3:
			activityInfo = ActivityPayJson.instance().get冲击狂人().get(index);
			getType = EItemGet.chongjikuangren.ordinal();
			break;
		case 4:
			activityInfo = ActivityPayJson.instance().get最佳搭档().get(index);
			getType = EItemGet.zuijiadadang.ordinal();
			break;
		case 5:
			activityInfo = ActivityPayJson.instance().get装备铸造().get(index);
			getType = EItemGet.zhuangbeiduandao.ordinal();
			break;
		case 6:
			activityInfo = ActivityPayJson.instance().get江湖等级榜().get(index);
			getType = EItemGet.jianghudengjibang.ordinal();
			break;
		case 7:
			activityInfo = ActivityPayJson.instance().get斗破洞天().get(index);
			getType = EItemGet.doupodongtian.ordinal();
			break;
		case 8:
			activityInfo = ActivityPayJson.instance().get绝世神兵().get(index);
			getType = EItemGet.jueshishenbing.ordinal();
			break;
		case 9:
			activityInfo = ActivityPayJson.instance().get最强战力().get(index);
			getType = EItemGet.zuiqiangzhanli.ordinal();
			break;

		default:
			result = 1;
			reason = "领取失败！";
			return;
		}

		// 判断是否满足条件
		int state = ActivityPayManager.checkState(online.getId(), activityInfo.getType(), activityInfo.getIndex());
		if (state == 0) {
			result = 0;
			reason = "条件不满足";
			return;
		} else if (state == 2) {
			result = 0;
			reason = "不可重复领取";
			return;
		}

		if (online.getBag().isFull(activityInfo.getItems().size())) {
			result = 1;
			reason = "背包空间不足";
			return;
		}

		if (online.getPartnerMap().size() + activityInfo.getPartners().size() > PartnerManager.MAX_NUM) {
			result = 1;
			reason = "伙伴背包空间不足";
			return;
		}

		ActivityPayManager.changeState(online.getId(), activityInfo.getType(), activityInfo.getIndex());

		EGold goldType = EGold.paySingle;
		EMoney moneyType = EMoney.paySingle;
		EItemGet itemType = EItemGet.paySingle;
		EGetPartner partnerType = EGetPartner.paySingle;
		switch (activityInfo.getType()) {
		case 1:
			goldType = EGold.paySingle;
			moneyType = EMoney.paySingle;
			itemType = EItemGet.paySingle;
			partnerType = EGetPartner.paySingle;
			break;
		case 2:
			goldType = EGold.payAccumulateWeek;
			moneyType = EMoney.payAccumulateWeek;
			itemType = EItemGet.payAccumulateWeek;
			partnerType = EGetPartner.payAccumulateWeek;
			break;

		default:
			goldType = EGold.newActivity;
			moneyType = EMoney.newActivity;
			itemType = EItemGet.newActivity;
			partnerType = EGetPartner.newActivity;
			break;
		}
		RoleServer.addGold(online, activityInfo.getGold(), goldType);
		RoleServer.addCoin(online, activityInfo.getCoin(), goldType);
		RoleServer.addMoney(online, activityInfo.getMoney(), moneyType);
		LogManager.activityNew(online.getId(), online.getUserId(), getType, index, 0, 0, 0, activityInfo.getGold(), activityInfo.getCoin(), activityInfo.getMoney());

		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		for (MonsterDropItem dropItem : activityInfo.getItems()) {
			Item item = ItemJson.instance().getItem(dropItem.getItemId());
			if (item != null) {
				item.setIsDeal(dropItem.getIsBind());
//				online.getBag().add(item, dropItem.getNum(), cellsMap, itemType);
				BagServer.add(online, item, dropItem.getNum(), cellsMap, itemType);
				LogManager.activityNew(online.getId(), online.getUserId(), getType, index, item.getId(), dropItem.getNum(), 0, 0, 0, 0);
			}
		}
		BagMessage.sendBag(online, cellsMap); // 刷新背包

		List<Partner> partnerList = new ArrayList<>();
		for (MonsterDropPartner dropPartner : activityInfo.getPartners()) {
			Partner newPartner = PartnerJson.instance().getNewPartner(dropPartner.getPartnerIndex());
			// 获得伙伴日志
			newPartner = PartnerServer.addPartner(online, newPartner, partnerType);
			newPartner.setIsBind(dropPartner.getIsBind());
			newPartner.setOperateFlag(EpartnerOperate.add);
			partnerList.add(newPartner);
			LogManager.activityNew(online.getId(), online.getUserId(), getType, index, 0, 0, newPartner.getId(), 0, 0, 0);
		}
		PartnerMessage.sendPartners(online, partnerList);

	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_activityPayAward");
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