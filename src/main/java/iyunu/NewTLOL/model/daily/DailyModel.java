package iyunu.NewTLOL.model.daily;

import iyunu.NewTLOL.enumeration.EDaily;
import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyModel {
	private int id;
	private EDaily type;
	private int target;
	private long monsterId;
	private int itemId;
	private String awardString;
	private List<MonsterDropItem> award = new ArrayList<>();
	private int partner;
	private int money;
	private boolean finish = false;
	private String name;// 任务名字
	private String des;// 任务描述
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间

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
	 * @return the type
	 */
	public EDaily getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EDaily type) {
		this.type = type;
	}

	/**
	 * @return the target
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * @return the monsterId
	 */
	public long getMonsterId() {
		return monsterId;
	}

	/**
	 * @param monsterId
	 *            the monsterId to set
	 */
	public void setMonsterId(long monsterId) {
		this.monsterId = monsterId;
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
	 * @return the awardString
	 */
	public String getAwardString() {
		return awardString;
	}

	/**
	 * @param awardString
	 *            the awardString to set
	 */
	public void setAwardString(String awardString) {
		this.awardString = awardString;
	}

	/**
	 * @return the award
	 */
	public List<MonsterDropItem> getAward() {
		return award;
	}

	/**
	 * @param award
	 *            the award to set
	 */
	public void setAward(List<MonsterDropItem> award) {
		this.award = award;
	}

	/**
	 * @return the finish
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des
	 *            the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @return the partner
	 */
	public int getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(int partner) {
		this.partner = partner;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

}
