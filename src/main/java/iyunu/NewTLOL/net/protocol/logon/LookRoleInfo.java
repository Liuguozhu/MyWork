package iyunu.NewTLOL.net.protocol.logon;

import iyunu.NewTLOL.json.IntensifyJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.billboard.level.LevelBoard;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 查看玩家信息
 * @author LuoSR
 * @date 2013年12月31日
 */
public class LookRoleInfo extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private long roleId;
	private Role role;

	@Override
	public void handleReceived(LlpMessage msg) {
		roleId = msg.readLong("roleId");

		if (!ServerManager.instance().isOnline(roleId)) {
			result = 1;
			reason = "该玩家不在线！";
			return;
		}

		role = ServerManager.instance().getOnlinePlayer(roleId);
		result = 0;
		reason = "";
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_lookRoleInfo");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				message.write("id", role.getId());
				message.write("nick", role.getNick());
				message.write("figure", role.getFigure());
				message.write("vocation", role.getVocation().getName());
				Gang gang = GangManager.instance().getGang(role.getGangId());
				message.write("gangName", gang == null ? "" : gang.getName());
				message.write("level", role.getLevel());
				Set<Entry<EEquip, Equip>> set = role.getEquipments().entrySet();
				for (Iterator<Entry<EEquip, Equip>> it = set.iterator(); it.hasNext();) {
					Entry<EEquip, Equip> entry = it.next();
					Equip equip = entry.getValue();
					LlpMessage equipmentInfo = message.write("equipMsgList");
					BagMessage.packageEquip(equipmentInfo, equip);
				}
				message.write("vip", role.getVip().getLevel().ordinal());
				message.write("rankingLevel", LevelBoard.INSTANCE.getRank(role.getId()));
				message.write("isFriend", Util.trueOrFalse(online.getFriendList().contains(roleId)));

				message.write("hpMax", role.getHpMax());
				message.write("mpMax", role.getMpMax());
				message.write("strength", role.getShowStrength());
				message.write("intellect", role.getShowIntellect());
				message.write("physique", role.getShowPhysique());
				message.write("agility", role.getShowAgility());
				message.write("free", role.getFree());
				message.write("pAttack", role.getPattack());
				message.write("pDefence", role.getPdefence());
				message.write("mAttack", role.getMattack());
				message.write("mDefence", role.getMdefence());
				message.write("hit", role.getHit());
				message.write("dodge", role.getDodge());
				message.write("crit", role.getCrit());
				message.write("parry", role.getParry());
				message.write("speed", role.getSpeed());
				message.write("power", role.getPower());

				// ======发送部位强化======
				Set<Entry<EEquip, Integer>> bodySet = role.getBodyIntensify().entrySet();
				for (Iterator<Entry<EEquip, Integer>> bodyIt = bodySet.iterator(); bodyIt.hasNext();) {

					Entry<EEquip, Integer> entry = bodyIt.next();
					EEquip part = entry.getKey();
					int level = entry.getValue();
					BodyInfo bodyInfo = IntensifyJson.instance().getEquipIntensifyMap(part, level);
					if (bodyInfo != null) {
						LlpMessage llpMessage = message.write("bodyInfoList");
						llpMessage.write("part", part.ordinal());
						llpMessage.write("level", level);
					}
				}

				// ======发送宝石镶嵌======
				Set<Entry<EEquip, HashMap<Integer, Rabbet>>> stoneSet = role.getBodyRabbet().entrySet();
				for (Iterator<Entry<EEquip, HashMap<Integer, Rabbet>>> stoneIt = stoneSet.iterator(); stoneIt.hasNext();) {
					LlpMessage llpMessage = message.write("stonePartList");

					Entry<EEquip, HashMap<Integer, Rabbet>> entry = stoneIt.next();
					EEquip part = entry.getKey();
					llpMessage.write("part", part.ordinal());

					Map<Integer, Rabbet> rabbetMap = entry.getValue();
					Set<Entry<Integer, Rabbet>> stoneInfoSet = rabbetMap.entrySet();
					for (Iterator<Entry<Integer, Rabbet>> itr = stoneInfoSet.iterator(); itr.hasNext();) {
						LlpMessage msg = llpMessage.write("stoneInfoList");
						Entry<Integer, Rabbet> stoneEntry = itr.next();
						Integer position = stoneEntry.getKey();
						Rabbet rabbet = stoneEntry.getValue();
						msg.write("position", position);
						msg.write("open", rabbet.getOpen());

						if (rabbet.getOpen() == 1) {
							Item stone = ItemJson.instance().getItem(rabbet.getStoneId());
							if (stone != null) {
								msg.write("id", stone.getId());
								msg.write("icon", stone.getIcon());
							}
						}
					}
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