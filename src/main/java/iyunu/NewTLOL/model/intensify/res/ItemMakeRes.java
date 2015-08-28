package iyunu.NewTLOL.model.intensify.res;

/**
 * @function 物品合成
 * @author LuoSR
 * @date 2014年10月11日
 */
public class ItemMakeRes {

	/** 物品编号 **/
	private int oldId;
	/** 物品升级编号 **/
	private int newId;
	/** 需要物品数量 **/
	private int makeNum;
	/** 需要银两 **/
	private int makeGold;
	
	public int getOldId() {
		return oldId;
	}

	public void setOldId(int oldId) {
		this.oldId = oldId;
	}

	public int getNewId() {
		return newId;
	}

	public void setNewId(int newId) {
		this.newId = newId;
	}

	public int getMakeNum() {
		return makeNum;
	}

	public void setMakeNum(int makeNum) {
		this.makeNum = makeNum;
	}

	public int getMakeGold() {
		return makeGold;
	}

	public void setMakeGold(int makeGold) {
		this.makeGold = makeGold;
	}

}
