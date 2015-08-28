package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.common.RoleCommon;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.ActivityMessage;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.GangMessage;
import iyunu.NewTLOL.message.HelperMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.RaidsMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.billboard.level.LevelBoard;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class Relogon extends TLOLMessageHandler {

	private int result = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;

		if (!ServerManager.isRunning()) { // 服务器维护
			result = 2;
			return;
		}

		long roleId = msg.readLong("roleId");
		int mapId = msg.readInt("mapId");
		int x = msg.readInt("x");
		int y = msg.readInt("y");

		if (!ServerManager.instance().isOnline(roleId)) {
			result = 1;
			return;
		}

		Role role = ServerManager.instance().getOnlinePlayer(roleId);

		role.setChannel(channel);
		role.setUpdateTime(System.currentTimeMillis() + RoleCommon.UPDATE_TIME); // 设置自动更新时间
		role.setLogon(true);

		// ======地图=====
		role.setAround(false);
		role.getMapInfo().getBaseMap().remove(role);

		BaseMap mapInfo = MapManager.instance().getMapById(mapId);
		if (mapInfo instanceof MapRaidsInfo) {
			mapInfo = (MapRaidsInfo) role.getMapInfo().getBaseMap();
		}

		if (MapManager.checkCoord(mapInfo, x, y)) {
			MapManager.instance().changeGrid(role, mapInfo, x, y, x, y); // 改变所在格子
		} else {
			MapManager.instance().changeGrid(role, mapInfo, x, y, mapInfo.getTransmitX(), mapInfo.getTransmitY()); // 改变所在格子
		}

		if (role.getRaidsTeamInfo() != null) {
			if (role.getMapInfo().isMapRaids()) {
				RaidsMessage.refreshRaids(role); // 刷新副本信息
			} else {
				role.setRaidsTeamInfo(null);
			}
		}
		role.getMapInfo().getMapAgent().clear();
		// ======地图=====

		online = role;
		// ======保存在线角色信息======
		ServerManager.instance().online(role.getChannel(), role);
		
		LogManager.itemAllLog(role, 2); // 物品日志
	}

	@Override
	public void handleReply() throws Exception {

		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_relogon");
			llpMessage.write("result", result);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

		if (result == 0) {
			// ======发送角色信息======
			SendMessage.sendRole(online);
			// ======刷新新手引导======
			SendMessage.refreshGuide(online);
			// ======刷新银两======
			SendMessage.sendCoin(online);
			// ======刷新绑银使用上限======
			SendMessage.sendGoldLimit(online);
			// ======刷新今日已使用绑银数量======
			SendMessage.sendGoldNum(online);
			// ======刷新地图======
			// MapMessage.sendMap(online, online.getMapInfo().getBaseMap());
			// ======刷新任务信息======
			TaskMessage.sendTask(online);
			// ======刷新背包======
			BagMessage.logonSendBag(online);
			// ======刷新装备======
			BagMessage.sendEquip(online);
			// ======刷新伙伴======

			List<Partner> partnerList = new ArrayList<>();
			for (Partner partner : online.getPartnerMap().values()) {
				partner.setOperateFlag(EpartnerOperate.add);
				partnerList.add(partner);
			}

			PartnerMessage.sendPartners(online, partnerList);
			// ======检查未读邮件======
			if (MailServer.hasNoRead(online)) {
				SendMessage.sendNewMail(online);
			}
			// ======发送角色技能======
			SendMessage.sendSkill(online);
			// ======发送部位强化======
			SendMessage.sendBodyIntensify(online);
			// ======发送部位镶嵌======
			SendMessage.sendBodyRabbet(online);
			// ==========发送聚魂=========
			// SendMessage.sendJuhun(online);
			// ======发送元气值======
			SendMessage.sendYuanQi(online);
			// ======发送充值比例======
			SendMessage.sendPayRatio(online);
			// ======刷新仓库======
			BagMessage.sendWarehouse(online);
			// ======发送剩余屏蔽怪时间======
			SendMessage.sendCloaking(online);
			// ======发送答题积分======
			SendMessage.sendScore(online);
			// ======发送玩家等级排行榜位置======
			SendMessage.sendRankingLevel(online, LevelBoard.INSTANCE.getRank(online.getId()));
			// ======发送VIP======
			SendMessage.sendRefreshVip(online);
			// ======刷新BUFF协议======
			SendMessage.sendNewBuff(online);
			// ======刷首充领奖状态======
			AwardMessage.sendPayFristState(online);
			// ======刷新小助手======
			// SendMessage.refreshHelper(online);
			// ======刷新活跃度======
			HelperMessage.refreshHelperAward(online);
			// ======刷新门派任务数量======
			TaskMessage.refreshGuildTaskNum(online);
			// ======刷新江湖追杀令任务数量======
			TaskMessage.refreshGhostTaskNum(online);
			// ======刷新寻宝次数======
			// SendMessage.refreshHuntTreasureNum(online);
			// ===========刷新血战信息==============
			SendMessage.sendBlood(online);
			// ======刷新新服活动数======
			ActivityMessage.refreshActivityNew(online);
			// ======刷新每日优惠活动数======
			ActivityMessage.refreshActivityPay(online);
			// ========刷新开服七天图标=======================
			SendMessage.refreshSevenEnd(online, Time.getDaysBetween(OperationManager.OPEN_FU) + 1);
			// =============刷新招伙伴标记以及时间======================
			PartnerMessage.sendPartnerRefresh(online);
			// ===================刷新VIP奖励标记====================
			AwardMessage.sendVipGift(online);
			// ===================刷新聊天发送VIP开关====================
			SendMessage.sendOpenVipChat(online);
			// ==============上线刷新帮派等级=====================
			if (online.getGangId() != 0 && GangManager.instance().getGang(online.getGangId()) != null) {
				GangMessage.refreshGangLevel2(online, GangManager.instance().getGang(online.getGangId()).getLevel());
			} else {
				GangMessage.refreshGangLevel2(online, 0);
			}
			// 刷新战力
			SendMessage.sendPower(online);
			// 刷新活力值
			SendMessage.refreshEnergy(online);
		}
	}
}