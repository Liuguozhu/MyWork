package iyunu.NewTLOL.model.io.gang;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.service.iface.gang.GangService;

public abstract class GangIOTask {

	/** 任务编号 **/
	protected Gang gang;

	protected GangService gangService = Spring.instance().getBean("gangService", GangService.class);

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            任务编号
	 * @param role
	 *            角色对象
	 */
	public GangIOTask(Gang gang) {
		this.gang = gang;
	}

	/**
	 * 执行方法
	 */
	public abstract void excute();

	/**
	 * 回调方法
	 */
	public abstract void callBack();

	/**
	 * @return the gang
	 */
	public Gang getGang() {
		return gang;
	}

	/**
	 * @param gang
	 *            the gang to set
	 */
	public void setGang(Gang gang) {
		this.gang = gang;
	}

}
