package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 招募伙伴
 * 
 * @author SunHonglei
 * 
 */
public class PartnerRefresh extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private List<Integer> getPartList = new ArrayList<>();
	private List<Partner> partnerList = new ArrayList<>();

	@Override
	public void handleReceived(LlpMessage msg) {

		result = 0;
		reason = "成功";
		getPartList.clear();
		partnerList.clear();
		boolean isFirst = false;
		boolean isSecFirst = false;// 第二排是否是第一次

		// type 1 手动 2 自动时间到免费
		int type = msg.readInt("type");
		// chouType 是抽 的哪个 1普通 2元宝 3十连抽
		int chouType = msg.readInt("chouType");

		if (type != 1 && type != 2) {
			result = 1;
			reason = "数据错误！";
			return;
		}
		if (chouType != 1 && chouType != 2 && chouType != 3) {
			result = 1;
			reason = "数据错误！";
			return;
		}

		if (chouType == 3) {
			if (online.getPartnerMap().size() + 10 >= PartnerManager.MAX_NUM) {
				result = 1;
				reason = "伙伴数量空闲位置不足10个，请先清理多余的伙伴";
				return;
			}
		} else {
			if (online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
				result = 1;
				reason = "伙伴数量已满" + PartnerManager.MAX_NUM + "个，请先清理多余的伙伴";
				return;
			}
		}
		boolean isDeal = false;
		if (chouType == 1) {
			EGetPartner getType = EGetPartner.commonFree;

			if (type == 1) {
				int index = online.getBag().isInBagByType(EItem.zhaomuling, 1);
				if (index == -1) {
					index = online.getBag().isInBagByType(EItem.zhaomuling, 0);
					if (index == -1) {
						result = 1;
						reason = "没有招募令！";
						return;
					}
				}

				// 删除1个招募令
				Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
				if (online.getBag().getCells()[index].getItem().getIsDeal() == 1) {
					isDeal = true;
				}
				online.getBag().removeByIndex(index, 1, cellsMap, EItemCost.zhaomu);

				BagMessage.sendBag(online, cellsMap);
				LogManager.partnerRefresh(online, 1, 10000);

				getType = EGetPartner.commonItem;

			} else if (type == 2) {
				if (online.getInnTime() > System.currentTimeMillis()) {
					result = 1;
					reason = "时间未到";
					return;
				}
				if (online.getFirstPartner() == 0) {
					isFirst = true;
				}
				isDeal = true;
				LogManager.partnerRefresh(online, 2, 10000);
				online.setInnTime(System.currentTimeMillis() + PartnerManager.DURATION1);
			}
			Partner partner = null;
			if (isFirst) {
				partner = PartnerJson.instance().getNewPartner(10411);// 指定ID伙伴
			} else {
				int types = PartnerManager.instance().getRefreshType(online, 1);
				partner = PartnerServer.refresh(types);
			}

			// 加入伙伴
			// 获得伙伴日志
			partner = PartnerServer.addPartner(online, partner, getType);
			if (isDeal) {
				partner.setIsBind(1);
			}
			partner.setOperateFlag(EpartnerOperate.add);
			partnerList.add(partner);
			PartnerMessage.sendPartners(online, partnerList);
			// PartnerMessage.sendPartners(online);
			PartnerMessage.sendPartnerRefresh(online);
			// 返回获得伙伴列表
			getPartList.add(partner.getIndex());
			online.setFirstPartner(1);
		} else if (chouType == 2) {
			EGetPartner getType = EGetPartner.moneyFree;

			Partner partner = null;

			if (type == 1) {
				getType = EGetPartner.money;
				if (online.getGuide() != 10025 && !RoleServer.costMoney(online, 188, EMoney.refreshPartner)) {
					result = 1;
					reason = "";
					SendMessage.refreshNoCoin(online, 3);
					return;
				}
				LogManager.partnerRefresh(online, 21, 188);
				if (PayExchangeManager.FLAG) {
					// 积分榜加分
					PayExchangeManager.instance().add(online.getId(), 188);
					// 刷新积分榜
					SendMessage.refreshPayExchangeContent(online);
				}
			} else if (type == 2) {
				if (online.getInnTime2() > System.currentTimeMillis()) {
					result = 1;
					reason = "时间未到";
					return;
				}
				if (online.getSecPartner() == 0) {
					isSecFirst = true;
				}
				isDeal = true;
				LogManager.partnerRefresh(online, 22, 0);
				online.setInnTime2(System.currentTimeMillis() + PartnerManager.DURATION2);
			}

			if (isSecFirst) {
				if (Util.RANDOM.nextInt(2) == 0) {
					partner = PartnerJson.instance().getNewPartner(10292);// 指定ID伙伴
				} else {
					partner = PartnerJson.instance().getNewPartner(10302);// 指定ID伙伴
				}
			} else {
				int types = PartnerManager.instance().getRefreshType(online, 2);
				partner = PartnerServer.refresh(types);
			}

			// 加入伙伴
			// 获得伙伴日志
			partner = PartnerServer.addPartner(online, partner, getType);
			if (isDeal) {
				partner.setIsBind(1);
			}
			partner.setOperateFlag(EpartnerOperate.add);
			partnerList.add(partner);
			PartnerMessage.sendPartners(online, partnerList);
			// PartnerMessage.sendPartners(online);
			PartnerMessage.sendPartnerRefresh(online);
			// 返回获得伙伴列表
			getPartList.add(partner.getIndex());
			// 第二排第一次招伙伴
			online.setSecPartner(1);
		} else if (chouType == 3) {
			if (online.getGuide() != 10025 && !RoleServer.costMoney(online, 1880, EMoney.refreshPartner)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 3);
				return;

			}
			LogManager.partnerRefresh(online, 31, 1880);
			if (PayExchangeManager.FLAG) {
				// 积分榜加分
				PayExchangeManager.instance().add(online.getId(), 1880);
				// 刷新积分榜
				SendMessage.refreshPayExchangeContent(online);
			}
			boolean appear = false;
			// 加入伙伴
			for (int i = 0; i < 9; i++) {
				int types = PartnerManager.instance().getRefreshType(online, 2);
				Partner partner = PartnerServer.refresh(types);
				// 获得伙伴日志
				partner = PartnerServer.addPartner(online, partner, EGetPartner.tenTimes);
				// 返回获得伙伴列表
				getPartList.add(partner.getIndex());
				partner.setOperateFlag(EpartnerOperate.add);
				partnerList.add(partner);
				if (partner.getColor() == EColor.purple) {
					appear = true;
				}
			}
			// 除了抽的9个，如果抽的9个中有紫，最后一次按普通单抽，如果没有，必给一个紫色的宝宝，十连抽的必给才走十连抽的概率，单抽还是走 元宝
			// 的概率
			if (appear) {
				int types = PartnerManager.instance().getRefreshType(online, 2);
				Partner partner = PartnerServer.refresh(types);
				// 获得伙伴日志
				partner = PartnerServer.addPartner(online, partner, EGetPartner.tenTimes);
				// 返回获得伙伴列表
				getPartList.add(partner.getIndex());
				partner.setOperateFlag(EpartnerOperate.add);
				partnerList.add(partner);
			} else {
				int types = PartnerManager.instance().getRefreshType(online, 3);
				Partner purple = PartnerServer.refresh(types);
				purple = PartnerServer.addPartner(online, purple, EGetPartner.tenTimes);
				purple.setOperateFlag(EpartnerOperate.add);
				partnerList.add(purple);
				// 获得伙伴日志

				// 返回获得伙伴列表
				getPartList.add(purple.getIndex());
			}

			PartnerMessage.sendPartners(online, partnerList);
			// PartnerMessage.sendPartners(online);
			PartnerMessage.sendPartnerRefresh(online);

		} else {
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerRefresh");
			message.write("result", result);
			message.write("reason", reason);
			for (Partner partner : partnerList) {
				if (partner.getCapability() >= 800) {
					String content = StringControl.grn(online.getNick()) + "鸿运当头，获得了潜力值" + partner.getCapability() + "点的" + StringControl.color(partner.getColor(), partner.getNick());
					BulletinManager.instance().addBulletinRock(content, 1);
				} else if (partner.getColor().ordinal() >= EColor.purple.ordinal()) {
					String content = StringControl.grn(online.getNick()) + "在不懈的努力下，终于获得了" + StringControl.color(partner.getColor(), partner.getNick()) + "，大家击掌同庆！";
					BulletinManager.instance().addBulletinRock(content, 1);
				}
			}
			if (result == 0) {
				for (Integer partnerIndex : getPartList) {
					message.write("partnerIndex", partnerIndex);
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
