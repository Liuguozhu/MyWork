package iyunu.NewTLOL.model.vip;

import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Translate;

public class Vip {

	private EVip level = EVip.common; // vip级别
	private boolean isFirstPay = false; // 是否领取了首充礼包
	private int payScore; // 充值积分
	private long vipTime; // 成为VIP时间
	private long gVipTime; // 黄金VIP时长
	private long pVipTime; // 白金VIP时长
	private long dVipTime; // 钻石VIP时长

	public String toString() {
		return level.ordinal() + "&" + isFirstPay + "&" + payScore + "&" + vipTime + "&" + gVipTime + "&" + pVipTime + "&" + dVipTime;
	}

	public static Vip decode(String string) {
		Vip vip = new Vip();
		if (string != null && !"".equals(string)) {
			String[] strings = string.split("&");

			vip.setLevel(EVip.values()[Translate.stringToInt(strings[0])]);
			vip.setFirstPay(Translate.stringToBoolean(strings[1]));
			vip.setPayScore(Translate.stringToInt(strings[2]));
			vip.setVipTime(Translate.stringToLong(strings[3]));
			vip.setgVipTime(Translate.stringToLong(strings[4]));
			vip.setpVipTime(Translate.stringToLong(strings[5]));
			vip.setdVipTime(Translate.stringToLong(strings[6]));
		}
		return vip;
	}

	public void clear() {
		vipTime = System.currentTimeMillis(); // 成为VIP时间
		gVipTime = 0; // 黄金VIP时长
		pVipTime = 0; // 白金VIP时长
		dVipTime = 0; // 钻石VIP时长
	}

	public void check() {
		if (System.currentTimeMillis() >= vipTime + gVipTime + pVipTime + dVipTime) {
			level = EVip.common;
		} else if (System.currentTimeMillis() >= vipTime + pVipTime + dVipTime) {
			level = EVip.gold;
		} else if (System.currentTimeMillis() >= vipTime + dVipTime) {
			level = EVip.platinum;
		} else {
			level = EVip.diamond;
		}
	}

	public String getVipTime(EVip level) {
		switch (level) {
		case gold:
			return Time.getTimeToStr(vipTime + gVipTime + pVipTime + dVipTime);
		case platinum:
			return Time.getTimeToStr(vipTime + dVipTime + pVipTime);
		case diamond:
			return Time.getTimeToStr(vipTime + dVipTime);
		default:
			return Time.getTimeToStr(System.currentTimeMillis());
		}
	}

	public boolean isVip(EVip level) {
		switch (level) {
		case gold:
			if (System.currentTimeMillis() >= vipTime + gVipTime + pVipTime + dVipTime) {
				check();
				return false;
			} else {
				return true;
			}
		case platinum:
			if (System.currentTimeMillis() >= vipTime + dVipTime + pVipTime) {
				check();
				return false;
			} else {
				return true;
			}
		case diamond:
			if (System.currentTimeMillis() >= vipTime + dVipTime) {
				check();
				return false;
			} else {
				return true;
			}
		default:

			if (System.currentTimeMillis() >= vipTime + gVipTime + pVipTime + dVipTime) {
				return true;
			} else {
				return false;
			}

		}
	}

	/**
	 * 增加充值积分
	 * 
	 * @param score
	 *            新增充值积分
	 */
	public void addPayScore(int score) {
		this.payScore += score;
	}

	/**
	 * 充值状态
	 * 
	 * @return 0.未充值，1.已充值但未领奖，2.已领奖
	 */
	public int payState() {
		if (isFirstPay) {
			return 2;
		}
		if (payScore > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 领奖
	 */
	public void reward() {
		isFirstPay = true;
	}

	/**
	 * @return the level
	 */
	public EVip getLevel() {
		check();
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(EVip level) {
		this.level = level;
	}

	/**
	 * @return the isFirstPay
	 */
	public boolean isFirstPay() {
		return isFirstPay;
	}

	/**
	 * @param isFirstPay
	 *            the isFirstPay to set
	 */
	public void setFirstPay(boolean isFirstPay) {
		this.isFirstPay = isFirstPay;
	}

	/**
	 * @return the payScore
	 */
	public int getPayScore() {
		return payScore;
	}

	/**
	 * @param payScore
	 *            the payScore to set
	 */
	public void setPayScore(int payScore) {
		this.payScore = payScore;
	}

	/**
	 * @return the vipTime
	 */
	public long getVipTime() {
		return vipTime;
	}

	/**
	 * @param vipTime
	 *            the vipTime to set
	 */
	public void setVipTime(long vipTime) {
		this.vipTime = vipTime;
	}

	/**
	 * @return the gVipTime
	 */
	public long getgVipTime() {
		return gVipTime;
	}

	/**
	 * @param gVipTime
	 *            the gVipTime to set
	 */
	public void setgVipTime(long gVipTime) {
		this.gVipTime = gVipTime;
	}

	/**
	 * @return the pVipTime
	 */
	public long getpVipTime() {
		return pVipTime;
	}

	/**
	 * @param pVipTime
	 *            the pVipTime to set
	 */
	public void setpVipTime(long pVipTime) {
		this.pVipTime = pVipTime;
	}

	/**
	 * @return the dVipTime
	 */
	public long getdVipTime() {
		return dVipTime;
	}

	/**
	 * @param dVipTime
	 *            the dVipTime to set
	 */
	public void setdVipTime(long dVipTime) {
		this.dVipTime = dVipTime;
	}

}
