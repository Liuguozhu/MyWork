package iyunu.NewTLOL.model.billboard.qct;

import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.billboard.TopBoard;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QctBoard extends TopBoard {

	public static final QctBoard INSTANCE = new QctBoard();

	/**
	 * 构造函数
	 */
	public QctBoard() {
		super("洞天榜", new Comparator<RoleCard>() {
			@Override
			public int compare(RoleCard card1, RoleCard card2) {
				if (card2.getHistoryFloorId() > card1.getHistoryFloorId())
					return 1;
				else if (card2.getHistoryFloorId() < card1.getHistoryFloorId())
					return -1;
				else {
					return 0;
				}

			}
		});
	}

	public String[] getTitle() {
		String[] levelTitle = { "排名", "角色ID", "名字", "层数", "门派" };
		return levelTitle;
	}

	public String[] getTitleIds() {
		String[] titleIds = { "index", "roleId", "name", "value", "vocation" };
		return titleIds;
	}

	/**
	 * 开服时加载等级排行榜，从帮派中已加载出来的
	 */
	public void sort() {
		ArrayList<RoleCard> list = new ArrayList<RoleCard>();
		list.addAll(RoleManager.getRoleCardMap().values());
		Collections.sort(list, comparator);
		int size = Util.matchSmaller(MAX, list.size());
		topCards.clear();
		for (int i = 0; i < size; i++) {
			RoleCard roleCard = list.get(i);
			if (roleCard.getLevel() >= BOARD_LEVEL && roleCard.getHistoryFloorId() > 0) {
				topCards.add(roleCard);
			}
		}
		list.clear();

	}

	@Override
	public void lastValue() {
		this.lastValue = topCards.get(topCards.size() - 1).getHistoryFloorId();
	}

	@Override
	public boolean checkLastValue(RoleCard roleCard) {
		if (roleCard.getHistoryFloorId() > this.lastValue) {
			return true;
		}
		return false;
	}
}
