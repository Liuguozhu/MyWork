package iyunu.NewTLOL.model.base;

/**
 * 激活码
 * 
 * @author SunHonglei
 * 
 */
public class CdKeyInfo {

	private String cdKey; // 激活码
	private int itemId; // 物品编号
	private int state; // 状态（0.可用，1.不可用）
	private int type; // 类型

	/**
	 * @return the cdKey
	 */
	public String getCdKey() {
		return cdKey;
	}

	/**
	 * @param cdKey
	 *            the cdKey to set
	 */
	public void setCdKey(String cdKey) {
		this.cdKey = cdKey;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
