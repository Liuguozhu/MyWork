package iyunu.NewTLOL.model.billboard.power;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.billboard.TopBoard;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PowerBoard extends TopBoard {

	public static final PowerBoard INSTANCE = new PowerBoard();

	/**
	 * 构造函数
	 */
	public PowerBoard() {
		super("战力榜", new Comparator<RoleCard>() {
			@Override
			public int compare(RoleCard card1, RoleCard card2) {
				if (card2.getPower() > card1.getPower())
					return 1;
				else if (card2.getPower() < card1.getPower())
					return -1;
				else
					return 0;

			}
		});
	}

	public String[] getTitle() {
		String[] levelTitle = { "排名", "角色ID", "名字", "战力", "门派" };
		return levelTitle;
	}

	public String[] getTitleIds() {
		String[] titleIds = { "index", "roleId", "name", "value", "vocation" };
		return titleIds;
	}

	/**
	 * 开服时加载战力排行榜，从帮派中已加载出来的
	 */
	public void sort() {
		ArrayList<RoleCard> list = new ArrayList<RoleCard>();
		list.addAll(RoleManager.getRoleCardMap().values());
		Collections.sort(list, comparator);
		int size = Util.matchSmaller(MAX, list.size());
		topCards.clear();
		for (int i = 0; i < size; i++) {
			RoleCard roleCard = list.get(i);
			if (roleCard.getLevel() >= BOARD_LEVEL) {
				topCards.add(roleCard);
			}
		}
		list.clear();

	}

	@Override
	public void lastValue() {
		this.lastValue = topCards.get(topCards.size() - 1).getPower();
	}

	@Override
	public boolean checkLastValue(RoleCard roleCard) {
		if (roleCard.getPower() > this.lastValue) {
			return true;
		}
		return false;
	}
}
