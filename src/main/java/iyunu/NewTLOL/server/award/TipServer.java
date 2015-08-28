package iyunu.NewTLOL.server.award;

import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.StringControl;

public class TipServer {

	public static void costGold(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "绑银"));
	}

	public static void costCoin(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "银两"));
	}

	public static void costYuanqi(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "元气值"));
	}

	public static void costMoney(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "元宝"));
	}

	public static void costExp(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "经验"));
	}

	public static void costScore(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "答题积分"));
	}

	public static void costTribute(Role role, int value) {
		ChatMessage.sendTip(role, StringControl.yel("失去" + StringControl.grn(value) + "帮派贡献"));
	}

	public static void costItem(Role role, Item item, int num) {
		ChatMessage.sendTip(role, StringControl.yel("失去物品" + StringControl.color(item.getColor(), item.getName()) + " x " + num));
	}

	public static void costPartner(Role role, Partner partner) {
		ChatMessage.sendTip(role, StringControl.yel("失去伙伴" + StringControl.color(partner.getColor(), partner.getNick())));
	}

}
