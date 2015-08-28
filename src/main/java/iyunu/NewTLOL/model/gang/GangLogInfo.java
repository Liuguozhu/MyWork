package iyunu.NewTLOL.model.gang;

public class GangLogInfo {

	private EGangLog type;
	private String name;
	private String log;

	public GangLogInfo(String name, String log, EGangLog type) {
		this.name = name;
		this.log = log;
		this.type = type;
	}

	public GangLogInfo() {

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
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * @return the type
	 */
	public EGangLog getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EGangLog type) {
		this.type = type;
	}

}
