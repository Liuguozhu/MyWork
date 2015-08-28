package iyunu.NewTLOL.model.payActivity.instance;

import java.util.Date;

public class PayActivityList {

	private int id;
	private int type;
	private String name;
	private Date startTime;
	private Date endTime;
	private Date finishTime;
	private long start;
	private long end;
	private long finish;

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
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(long end) {
		this.end = end;
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
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime
	 *            the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the finish
	 */
	public long getFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(long finish) {
		this.finish = finish;
	}

}
