package iyunu.NewTLOL.model.billboard;

import iyunu.NewTLOL.event.rank.RankEventHandler;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.StringControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class TopBoard implements RankEventHandler {

	protected ArrayList<RoleCard> topCards = new ArrayList<RoleCard>();
	public final static int MAX = 60; // 保存人数
	public final static int BOARD_LEVEL = 22; // 上榜等级
	public final static int SHOW_MAX = 50; // 显示人数
	protected long lastValue = 0;
	protected final String topRatedName;
	protected final Comparator<RoleCard> comparator;

	public TopBoard(final String topRatedName, final Comparator<RoleCard> comparator) {
		this.topRatedName = topRatedName;
		this.comparator = comparator;
	}

	// protected final int getRawIndex(final RoleCard element) {
	// return topCards.indexOf(element);
	// }

	@Override
	public final void handleEvent(final RoleCard element) {
		if (topCards.isEmpty()) {
			topCards.add(element);
			return;
		}

		if (topCards.size() >= MAX) {
			if (!checkLastValue(element)) {
				return;
			}
		}

		boolean isNew = true;
		int rawindex = -1;

		// 判断榜中是否已存在此人，查看在排行榜中的位置
		for (int i = 0; i < topCards.size(); i++) {
			RoleCard roleCard = topCards.get(i);
			if (element != null && roleCard != null && element.getId() == roleCard.getId()) {
				isNew = false;
				rawindex = i;
			}
		}
		if (!isNew) {
			topCards.remove(rawindex);
			topCards.add(element);
		}

		// 如果是新来的，排行榜满了但是没有挤进榜，返回
		if (isNew && topCards.size() == MAX && comparator.compare(element, topCards.get(topCards.size() - 1)) >= 0) {
			return;
		}
		// 加入
		if (isNew) {
			topCards.add(element);
		}
		// 排序
		Collections.sort(topCards, comparator);

		int index = -1;

		for (int i = 0; i < topCards.size(); i++) {
			RoleCard roleCard = topCards.get(i);
			if (element != null && roleCard != null && element.getId() == roleCard.getId()) {
				index = i;
			}
		}

		if (isNew && topCards.size() > MAX) {
			topCards.remove(topCards.size() - 1);
		}

		lastValue();

		// 如果位置没变，或者前进之后没有进入前三名，不处理，进了前三就发公告
		if (index >= rawindex || Math.min(index, rawindex) > 2 || Math.min(index, rawindex) < 0) {
			return;
		} else {
			String content = "恭喜" + StringControl.grn(element.getNick()) + "成为" + StringControl.grn(topRatedName) + "第" + StringControl.grn((index + 1)) + "名！";
			// BulletinManager.instance().addBulletinChat(content);
			BulletinManager.instance().addBulletinRock(content, 2);
			if (ServerManager.instance().isOnline(element.getId())) {
				Role role = ServerManager.instance().getOnlinePlayer(element.getId());
				SendMessage.sendRankingLevel(role, index + 1);
			}

			for (int i = index + 1; i < 5; i++) {
				if (topCards.size() > i) {
					RoleCard roleCard = topCards.get(i);
					if (roleCard != null && ServerManager.instance().isOnline(roleCard.getId())) {
						Role role = ServerManager.instance().getOnlinePlayer(roleCard.getId());
						SendMessage.sendRankingLevel(role, i);
					}
				}
			}
		}
	}

	/**
	 * @return the topCards
	 */
	public ArrayList<RoleCard> getTopCards() {
		return topCards;
	}

	/**
	 * @return the topRatedName
	 */
	public String getTopRatedName() {
		return topRatedName;
	}

	public String[] getTitle() {
		String[] title = {};
		return title;
	}

	public String[] getTitleIds() {
		String[] titleIds = {};
		return titleIds;
	}

	public abstract void lastValue();

	public abstract boolean checkLastValue(RoleCard roleCard);

	/**
	 * @function 获取排行榜排名
	 * @author LuoSR
	 * @param roleId
	 *            角色编号
	 * @return 排行榜排名
	 * @date 2014年4月11日
	 */
	@SuppressWarnings("unchecked")
	public int getRank(long roleId) {
		ArrayList<RoleCard> list = (ArrayList<RoleCard>) topCards.clone();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == roleId) {
				return i + 1;
			}
		}
		return -1;
	}
}