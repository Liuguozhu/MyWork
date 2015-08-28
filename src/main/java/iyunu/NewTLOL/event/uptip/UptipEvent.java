package iyunu.NewTLOL.event.uptip;

import iyunu.NewTLOL.event.uptip.uptips.HuoBanJinJie;
import iyunu.NewTLOL.event.uptip.uptips.HuoBanShengJi;
import iyunu.NewTLOL.event.uptip.uptips.JiNengDian;
import iyunu.NewTLOL.event.uptip.uptips.ShuXingDian;
import iyunu.NewTLOL.event.uptip.uptips.YuanQiZhi;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;

/** 角色提升提醒 */
public enum UptipEvent implements UptipInstance {

	/** 经验变化事件 */
	技能点(JiNengDian.INSTANCE, 0), //
	属性点(ShuXingDian.INSTANCE, 1), //
	元气值(YuanQiZhi.INSTANCE, 2), //
	伙伴进阶(HuoBanJinJie.INSTANCE, 3), //
	伙伴升级(HuoBanShengJi.INSTANCE, 4), //
	;

	/** 固定的事件处理器 */
	private Uptips handler;
	private int ordinal;

	/**
	 * @param handlers
	 *            事件处理器
	 */
	private UptipEvent(final Uptips handler, int ordinal) {
		this.handler = handler;
		this.handler.setOrdinal(ordinal);
		this.ordinal = ordinal;
	}

	@Override
	public boolean check(Role role, boolean tag) {
		boolean haveChange = handler.check(role, tag);
		if (haveChange) {
			// 如果有某个变化了，刷新协议，把可提升的页面刷一遍
			SendMessage.refreshUpTipOne(role, handler.getOrdinal(), role.getUptipBoolean(handler.getOrdinal()));
		}
		return haveChange;
	}

	/**
	 * @return the handler
	 */
	public Uptips getHandler() {
		return handler;
	}

	@Override
	public boolean countBefore(Role role) {
		return handler.countBefore(role);
	}

	/**
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @param ordinal
	 *            the ordinal to set
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

}
