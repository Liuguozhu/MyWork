package iyunu.NewTLOL.model.activity;

public class Activity {

	/** 编号 **/
	private int id;
	/** 名称 **/
	private String name;
	/** 开始时间 **/
	private long startTime;
	/** 结束时间 **/
	private long endTime;
	/** 类型 **/
	private EActivity type;

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
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the type
	 */
	public EActivity getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EActivity type) {
		this.type = type;
	}

}
