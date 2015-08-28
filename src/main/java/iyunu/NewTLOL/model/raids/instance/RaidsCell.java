package iyunu.NewTLOL.model.raids.instance;

import iyunu.NewTLOL.model.raids.ERadisCell;

public class RaidsCell {

	private ERadisCell type; // 类型
	private long uid; // 唯一编号

	/**
	 * 构造方法
	 * 
	 * @param floor
	 *            层数
	 * @param index
	 *            格子索引
	 * @param type
	 *            类型
	 */
	public RaidsCell(ERadisCell type, long uid) {
		this.type = type;
		this.uid = uid;
	}

	/**
	 * @return the type
	 */
	public ERadisCell getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ERadisCell type) {
		this.type = type;
	}

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

}
