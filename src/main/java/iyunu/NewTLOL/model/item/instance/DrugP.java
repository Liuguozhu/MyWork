package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.item.ERecover;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.util.Util;

import java.util.Map;

/**
 * 药品实例类
 * 
 * @author SunHonglei
 * 
 */
public class DrugP extends Item {

	private ERecover recover; // 恢复类型
	private int hp; // 恢复HP
	private int mp; // 恢复MP
	private int revival; // 是否能救人,0不能，1能

	public DrugP copy() {
		DrugP item = new DrugP();
		Item.init(item, this);

		item.setRecover(recover);
		item.setHp(hp);
		item.setMp(mp);
		item.setRevival(revival);
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {

		if (this.way == 0 && this.apply == 1) {

			int recoverHp = 0;
			if (recover.equals(ERecover.definite)) {
				recoverHp = hp * num;
			} else if (recover.equals(ERecover.percent)) {
				recoverHp = (int) (role.getHpMax() * (hp / 10000f) * num);
			}

			Long partnerId = role.getPartnerFight().get(1);
			if (partnerId == null || partnerId == 0) {
				return false;
			}
			Partner partner = role.findPartner(partnerId);
			if (partner == null) {
				return false;
			}
			if (partner.getHp() + recoverHp >= partner.getHpMax()) {
				partner.setHp(partner.getHpMax());
			} else {
				partner.setHp(partner.getHp() + recoverHp);
			}

			PartnerMessage.sendPartnerHp(role, partner);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {

		if (this.apply == 0 || this.apply == 2) {
			BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 3, id); // 创建战斗动画对象
			BattleCharacter foe = battleInfo.getBattleCharacter(foeIndex, self.getDirection(), (revival == 1 ? true : false));
			if (foe == null) {
				return false;
			}

			if (foe.isDead()) {
				if (revival == 1) { // 可以救人

					BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
					battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

					int recoverHp = 0;
					int recoverMp = 0;
					if (recover.equals(ERecover.definite)) {
						recoverHp = hp;
						recoverMp = mp;
					} else if (recover.equals(ERecover.percent)) {
						recoverHp = (int) (foe.getPropertyBattle().getHpMax() * (hp / 10000f));
						recoverMp = (int) (foe.getPropertyBattle().getMpMax() * (mp / 10000f));
					}

					foe.setDead(false);

					recoverHp = Util.matchSmaller(foe.getPropertyBattle().getHpMax(), recoverHp);
					defenderAmin.addHp(recoverHp); // 战斗动画——防守者——记录血量变化
					foe.getPropertyBattle().setHp(foe.getPropertyBattle().getHp() + recoverHp);
					defenderAmin.setIsDead(0); // 战斗动画——防守者——记录复活
					// battleInfo.addCharacterInToOrder(foe); // 添加至战斗队列中

					recoverMp = Util.matchSmaller(foe.getPropertyBattle().getMpMax(), recoverMp);
					defenderAmin.setMp(recoverMp); // 战斗动画——防守者——记录血量变化
					foe.getPropertyBattle().setMp(foe.getPropertyBattle().getMp() + recoverMp);
					return true;
				}
			} else {

				BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
				battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

				int recoverHp = 0;
				int recoverMp = 0;
				if (recover.equals(ERecover.definite)) {
					recoverHp = hp;
					recoverMp = mp;
				} else if (recover.equals(ERecover.percent)) {
					recoverHp = (int) (foe.getPropertyBattle().getHpMax() * (hp / 10000f));
					recoverMp = (int) (foe.getPropertyBattle().getMpMax() * (mp / 10000f));
				}

				int needHp = foe.getPropertyBattle().getHpMax() - foe.getPropertyBattle().getHp();
				recoverHp = Util.matchSmaller(needHp, recoverHp);
				defenderAmin.addHp(recoverHp); // 战斗动画——防守者——记录血量变化
				foe.getPropertyBattle().addHp(recoverHp);

				int needMp = foe.getPropertyBattle().getMpMax() - foe.getPropertyBattle().getMp();
				recoverMp = Util.matchSmaller(needMp, recoverMp);
				defenderAmin.setMp(recoverMp); // 战斗动画——防守者——记录血量变化
				foe.getPropertyBattle().addMp(recoverMp);
				return true;
			}
		}

		return false;
	}

	/**
	 * @return the recover
	 */
	public ERecover getRecover() {
		return recover;
	}

	/**
	 * @param recover
	 *            the recover to set
	 */
	public void setRecover(ERecover recover) {
		this.recover = recover;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the mp
	 */
	public int getMp() {
		return mp;
	}

	/**
	 * @param mp
	 *            the mp to set
	 */
	public void setMp(int mp) {
		this.mp = mp;
	}

	/**
	 * @return the revival
	 */
	public int getRevival() {
		return revival;
	}

	/**
	 * @param revival
	 *            the revival to set
	 */
	public void setRevival(int revival) {
		this.revival = revival;
	}

}
