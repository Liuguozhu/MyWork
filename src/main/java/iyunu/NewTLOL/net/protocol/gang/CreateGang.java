package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.enumeration.common.EGang;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.MapJson;
import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.GangJobTitle;
import iyunu.NewTLOL.model.io.gang.GangIOTask;
import iyunu.NewTLOL.model.io.gang.instance.GangInsertTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Date;
import java.util.HashSet;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 创建帮派
 * 
 * @author fenghaiyu
 * 
 */
public class CreateGang extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "创建帮派成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "创建帮派成功";
		String name = msg.readString("name");

		if (online.getLevel() < 20) {
			result = 1;
			reason = "创建帮派失败，等级未达到20级~";
			return;
		}

		if (online.getGangId() != 0) {
			result = 1;
			reason = "创建帮派失败，已有帮派~";
			return;
		}

		if (name.length() > 6) {
			result = 1;
			reason = "创建帮派失败，帮派名字超过6个字了~";
			return;
		}

		// 检查名字是否由汉字，字母，数字组成
		if (!IlllegalWordManager.match(name)) {
			reason = "帮派名称只能由汉字，字母，数字组成";
			result = 1;
			return;
		}

		String str = IlllegalWordManager.instance().existStr(name);
		if (str != null) {
			reason = "帮派名包含非法字符[" + str + "]，请您更换！";
			result = 1;
			return;
		}

		name = "s" + ServerManager.instance().getSrvId() + "." + name;
		if (GangManager.instance().getNameList().contains(name)) {
			result = 1;
			reason = "创建帮派失败，帮派名字已存在~";
			return;
		}

//		if (!Time.beforeYesterday(online.getLeaveGangTime())) {
//			result = 1;
//			reason = "退出帮派当天不可以创建帮派！";
//			return;
//		}

		if (!RoleServer.costCoin(online, 120000, EGold.createGang)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 2);
			return;
		}

		Gang gang = new Gang();
		long gangId = redisCenter.getGangId();
		gang.setId(gangId);
		gang.setLeader(online.getId());
		gang.setLeaderName(online.getNick());
		gang.setLevel(1);
		// gang.setNum(1);
		gang.setCreateDate(new Date());
		gang.setName(name);
		gang.setViceLeader(new HashSet<Long>());
		gang.setPresbyter(new HashSet<Long>());
		gang.addMembers(online.toCard());
		gang.setActive(GangManager.INITACTIVE);

		MapManager.instance().initGangMap(gang, MapJson.instance().getMapGangInfo(gang)); // 设置帮派地图

		// 创建帮派
		GangManager.instance().map.put(gangId, gang);
		// 帮派名称列表加入
		GangManager.instance().addName(name);
		// 人物身上帮派赋值
		// online.setGangId(gangId);
		// online.setJobTitle(GangJobTitle.Leader);
		// online.setGangName(name);
		// online.setGang(gang);
		GangServer.jionGang(online, gang, GangJobTitle.Leader);

		GangIOTask task = new GangInsertTask(gang);
		IOProcessManager.instance().addGangTask(task);
		LogManager.gang(online.getId(), online.getUserId(), online.getGangId(), 0, gang.getName(), 0, 1000000, EGang.创建帮派.ordinal());

		MapManager.instance().addGangStateQueue(online);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_createGang");
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
