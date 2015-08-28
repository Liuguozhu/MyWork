package iyunu.NewTLOL.server.award;

import iyunu.NewTLOL.enumeration.EAward;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.StringControl;

import java.util.List;

public class AwardServer {

	public static void addIntensify(Role role, String content) {
		AwardMessage.sendAward(role, content, EAward.绑银);
	}

	public static void addGold(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "绑银");
		AwardMessage.sendAward(role, content, EAward.绑银);
		ChatMessage.sendTip(role, content);
	}

	public static void addCoin(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value + "银两"));
		AwardMessage.sendAward(role, content, EAward.银两);
		ChatMessage.sendTip(role, content);
	}

	public static void addYuanqi(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "元气值");
		AwardMessage.sendAward(role, content, EAward.元气值);
		ChatMessage.sendTip(role, content);
	}

	public static void addMoney(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "元宝");
		AwardMessage.sendAward(role, content, EAward.元宝);
		ChatMessage.sendTip(role, content);
	}

	public static void addWuhun(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "武魂");
		AwardMessage.sendAward(role, content, EAward.武魂);
		ChatMessage.sendTip(role, content);
	}

	public static void addExp(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "经验");
		AwardMessage.sendAward(role, content, EAward.经验);
		ChatMessage.sendTip(role, content);
	}

	public static void addPartnerExp(Role role, int value) {
		String content = StringControl.yel("您的伙伴获得" + StringControl.grn(value) + "经验");
		AwardMessage.sendAward(role, content, EAward.伙伴经验);
		ChatMessage.sendTip(role, content);
	}

	public static void addScore(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "答题积分");
		AwardMessage.sendAward(role, content, EAward.答题积分);
		ChatMessage.sendTip(role, content);
	}

	public static void addTribute(Role role, int value) {
		String content = StringControl.yel("获得" + StringControl.grn(value) + "帮派贡献");
		AwardMessage.sendAward(role, content, EAward.帮派贡献);
		ChatMessage.sendTip(role, content);
	}

	public static void addItem(Role role, Item item, int num) {
		String content = StringControl.yel("获得物品" + StringControl.color(item.getColor(), item.getName()) + " x " + num);
		AwardMessage.sendAward(role, content, EAward.物品);
		ChatMessage.sendTip(role, content);
	}

	public static void addPartner(Role role, Partner partner) {
		String content = StringControl.yel("获得伙伴" + StringControl.color(partner.getColor(), partner.getNick()));
		AwardMessage.sendAward(role, content, EAward.伙伴);
		ChatMessage.sendTip(role, content);
	}

	public static void addCapability(Role role, Partner partner, int value) {
		String content = StringControl.yel("伙伴" + StringControl.color(partner.getColor(), partner.getNick()) + "增加" + StringControl.grn(value) + "潜力值");
		AwardMessage.sendAward(role, content, EAward.伙伴);
		ChatMessage.sendTip(role, content);
	}
	
	public static void addTalet(Role role, Partner partner, int value) {
		String content = StringControl.yel("进化成功，成长率+" + StringControl.grn(value));
		AwardMessage.sendAward(role, content, EAward.伙伴);
		ChatMessage.sendTip(role, content);
	}

	public static void bagFull(Role role) {
		AwardMessage.sendAward(role, StringControl.yel("背包已满，无法获得任务物品"), EAward.背包已满);
	}

	public static void intoRoom(List<Long> roleIds, String nick) {
		for (Long roleId : roleIds) {
			AwardMessage.sendAward(roleId, nick + "加入房间", EAward.进入房间);
		}
	}
}
