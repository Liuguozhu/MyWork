package iyunu.NewTLOL.model.gift.res;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * @function 七天奖励
 * @author fhy
 */
public class SevenGiftRes {

	/** 天数 **/
	private int id;
	/** 普通物品 **/
	private String common;// 天数，物品对象
	/** VIP物品 **/
	private String vip;// 天数，物品对象

	private String commonP;// 普通伙伴
	private String vipP;// vip伙伴
	private int commonG;// 普通绑银
	private int vipG;// vip绑银
	private int commonC;// 普通银两
	private int vipC;// vip 银两
	private int commonM;// 普通元宝
	private int vipM;// vip元宝

	public List<MonsterDropItem> toCommonGift() {
		List<MonsterDropItem> commonGift = new ArrayList<MonsterDropItem>();
		if (common != null && !"".equals(common)) {
			String[] stings = common.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				commonGift.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		return commonGift;
	}

	public List<MonsterDropItem> toCommonPGift() {
		List<MonsterDropItem> commonPGift = new ArrayList<MonsterDropItem>();
		if (commonP != null && !"".equals(commonP)) {
			String[] stings = commonP.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				commonPGift.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), 1, 0, Translate.stringToInt(itemStr[1])));
			}
		}
		return commonPGift;
	}

	public List<MonsterDropItem> toVipGift() {
		List<MonsterDropItem> vipGift = new ArrayList<MonsterDropItem>();
		if (vip != null && !"".equals(vip)) {
			String[] stings = vip.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				vipGift.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
			}
		}
		return vipGift;
	}

	public List<MonsterDropItem> toVipPGift() {
		List<MonsterDropItem> vipPGift = new ArrayList<MonsterDropItem>();
		if (vipP != null && !"".equals(vipP)) {
			String[] stings = vipP.split(";");
			for (int i = 0; i < stings.length; i++) {
				String[] itemStr = stings[i].split(":");
				vipPGift.add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), 1, 0, Translate.stringToInt(itemStr[1])));
			}
		}
		return vipPGift;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the common
	 */
	public String getCommon() {
		return common;
	}

	/**
	 * @param common
	 *            the common to set
	 */
	public void setCommon(String common) {
		this.common = common;
	}

	/**
	 * @return the vip
	 */
	public String getVip() {
		return vip;
	}

	/**
	 * @param vip
	 *            the vip to set
	 */
	public void setVip(String vip) {
		this.vip = vip;
	}

	/**
	 * @return the commonP
	 */
	public String getCommonP() {
		return commonP;
	}

	/**
	 * @param commonP
	 *            the commonP to set
	 */
	public void setCommonP(String commonP) {
		this.commonP = commonP;
	}

	/**
	 * @return the vipP
	 */
	public String getVipP() {
		return vipP;
	}

	/**
	 * @param vipP
	 *            the vipP to set
	 */
	public void setVipP(String vipP) {
		this.vipP = vipP;
	}

	/**
	 * @return the commonG
	 */
	public int getCommonG() {
		return commonG;
	}

	/**
	 * @param commonG
	 *            the commonG to set
	 */
	public void setCommonG(int commonG) {
		this.commonG = commonG;
	}

	/**
	 * @return the vipG
	 */
	public int getVipG() {
		return vipG;
	}

	/**
	 * @param vipG
	 *            the vipG to set
	 */
	public void setVipG(int vipG) {
		this.vipG = vipG;
	}

	/**
	 * @return the commonC
	 */
	public int getCommonC() {
		return commonC;
	}

	/**
	 * @param commonC
	 *            the commonC to set
	 */
	public void setCommonC(int commonC) {
		this.commonC = commonC;
	}

	/**
	 * @return the vipC
	 */
	public int getVipC() {
		return vipC;
	}

	/**
	 * @param vipC
	 *            the vipC to set
	 */
	public void setVipC(int vipC) {
		this.vipC = vipC;
	}

	/**
	 * @return the commonM
	 */
	public int getCommonM() {
		return commonM;
	}

	/**
	 * @param commonM
	 *            the commonM to set
	 */
	public void setCommonM(int commonM) {
		this.commonM = commonM;
	}

	/**
	 * @return the vipM
	 */
	public int getVipM() {
		return vipM;
	}

	/**
	 * @param vipM
	 *            the vipM to set
	 */
	public void setVipM(int vipM) {
		this.vipM = vipM;
	}

}
