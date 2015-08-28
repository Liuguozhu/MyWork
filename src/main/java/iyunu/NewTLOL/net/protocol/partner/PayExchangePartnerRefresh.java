package iyunu.NewTLOL.net.protocol.partner;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 积分榜招募伙伴
 * 
 * @author fhy
 * 
 */
public class PayExchangePartnerRefresh extends TLOLMessageHandler {

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

		// type 1 抽 2 十连抽
		int type = msg.readInt("type");

		if (type != 1 && type != 2) {
			result = 1;
			reason = "数据错误！";
			return;
		}

		if (type == 2) {
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
		if (type == 1) {
			Partner partner = null;
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
				SendMessage.refreshPayExchangeContent(online);
			}

			int types = PartnerManager.instance().getRefreshType(online, 2);
			partner = PartnerServer.refresh(types);

			// 加入伙伴
			// 获得伙伴日志
			partner = PartnerServer.addPartner(online, partner, EGetPartner.money);
			partner.setOperateFlag(EpartnerOperate.add);
			partnerList.add(partner);
			PartnerMessage.sendPartners(online, partnerList);
			PartnerMessage.sendPartnerRefresh(online);
			// 返回获得伙伴列表
			getPartList.add(partner.getIndex());
		} else if (type == 2) {
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
				// 返回获得伙伴列表
				getPartList.add(purple.getIndex());
			}

			PartnerMessage.sendPartners(online, partnerList);
			PartnerMessage.sendPartnerRefresh(online);

		} else {
			return;
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_partnerRefreshActive");
			message.write("result", result);
			message.write("reason", reason);
			for (Partner partner : partnerList) {
				if (partner.getColor() == EColor.purple) {
					String content = StringControl.grn(online.getNick()) + "在不懈的努力下，终于获得了" + StringControl.pur(partner.getNick()) + "，大家击掌同庆！";
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
