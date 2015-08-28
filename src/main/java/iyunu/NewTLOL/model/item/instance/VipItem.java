package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.message.AwardMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.seven.Seven;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;

import java.util.Map;

/**
 * @function vip道具
 * @author LuoSR
 * @date 2014年3月11日
 */
public class VipItem extends Item {

	/** 种类 **/
	private EVip category;

	@Override
	public VipItem copy() {
		VipItem item = new VipItem();
		Item.init(item, this);

		item.setCategory(category);

		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {

		if (role.getVip().isVip(EVip.common)) { // 如果以前不是vip
			role.getVip().clear();
		}
		switch (category) {
		case diamond:
			role.getVip().setdVipTime(role.getVip().getdVipTime() + value * num * Time.MINUTE_MILLISECOND);
			break;
		case platinum:
			role.getVip().setpVipTime(role.getVip().getpVipTime() + value * num * Time.MINUTE_MILLISECOND);
			break;
		default:
			role.getVip().setgVipTime(role.getVip().getgVipTime() + value * num * Time.MINUTE_MILLISECOND);
			break;
		}
		int days = Time.getDaysBetween(OperationManager.OPEN_FU) + 1;
		if (days < 8 && days >= 1) {
			Seven seven = role.getSevenMap().get(days);
			if (seven != null) {
				if (seven.getVip() == 0) {
					seven.setVip(1);
				}
			}
		}

		SendMessage.sendRefreshVip(role);
		AwardMessage.sendVipGift(role);
		MapManager.instance().addBaseQueue(role);
		
		RoleServer.energyRefresh(role);
		SendMessage.refreshEnergy(role);
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

	/**
	 * @return the category
	 */
	public EVip getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(EVip category) {
		this.category = category;
	}

}
