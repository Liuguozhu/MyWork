package iyunu.NewTLOL.event.rank;

import iyunu.NewTLOL.model.billboard.TopBoard;
import iyunu.NewTLOL.model.billboard.level.LevelBoard;
import iyunu.NewTLOL.model.billboard.power.PowerBoard;
import iyunu.NewTLOL.model.billboard.qct.QctBoard;
import iyunu.NewTLOL.model.role.RoleCard;

/** 角色基本事件处理 */
public enum RankEvent implements RankEventHandler {

	/** 经验变化事件 */
	ExpEvent(LevelBoard.INSTANCE), //
	/** 战力变化事件 */
	PowerEvent(PowerBoard.INSTANCE), //
	/** 洞天榜 */
	QctEvent(QctBoard.INSTANCE), //
	;

	/** 固定的事件处理器 */
	private TopBoard handler;

	/**
	 * @param handlers
	 *            事件处理器
	 */
	private RankEvent(final TopBoard handler) {
		this.handler = handler;
	}

	@Override
	public void handleEvent(final RoleCard roleCard) {
		if (handler == null)
			return;

		if (roleCard.getLevel() < TopBoard.BOARD_LEVEL)
			return;

		if (roleCard != null) {
			handler.handleEvent(roleCard);
		}
	}

	/**
	 * @return the handler
	 */
	public TopBoard getHandler() {
		return handler;
	}

}
