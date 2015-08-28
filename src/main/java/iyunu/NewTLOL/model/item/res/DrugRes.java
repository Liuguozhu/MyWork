package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.model.item.ERecover;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Drug;
import iyunu.NewTLOL.model.item.instance.DrugP;

/**
 * 药品实例类
 * 
 * @author SunHonglei
 * 
 */
public class DrugRes extends BaseItemRes {

	private ERecover recover; // 恢复类型
	private int hp; // 恢复HP
	private int mp; // 恢复MP
	private int revival; // 是否能救人,0不能，1能

	public Item toItem() {
		Item item = super.toItem();
		if (item instanceof Drug) {
			Drug drug = (Drug) item;
			drug.setRecover(recover);
			drug.setHp(hp);
			drug.setMp(mp);
			drug.setRevival(revival);
		} else if (item instanceof DrugP) {
			DrugP drugP = (DrugP) item;
			drugP.setRecover(recover);
			drugP.setHp(hp);
			drugP.setMp(mp);
			drugP.setRevival(revival);
		}

		return item;
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
