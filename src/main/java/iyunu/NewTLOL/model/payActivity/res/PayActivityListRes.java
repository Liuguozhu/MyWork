package iyunu.NewTLOL.model.payActivity.res;

import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.util.Time;

import java.util.Date;

/**
 * @author SunHonglei
 * 
 */
public class PayActivityListRes {
	private int id;
	private int type;
	private String name;
	private Date startTime;
	private Date endTime;
	private Date finishTime;
	
	public PayActivityList toPayActivity() {
		PayActivityList activityList = new PayActivityList();
		activityList.setId(id);
		activityList.setType(type);
		activityList.setName(name);
		activityList.setStartTime(startTime);
		activityList.setEndTime(endTime);
		activityList.setFinishTime(finishTime);
		
		activityList.setStart(Time.dateToLong(startTime));
		activityList.setEnd(Time.dateToLong(endTime));
		activityList.setFinish(Time.dateToLong(finishTime));
		return activityList;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
