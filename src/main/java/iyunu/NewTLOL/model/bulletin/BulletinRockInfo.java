package iyunu.NewTLOL.model.bulletin;

/**
 * @function 滚屏公告
 */
public class BulletinRockInfo {

	/** 公告内容 **/
	private String content;
	/** 播放次数 **/
	private int number;
	private int mapId = 0;

	public BulletinRockInfo(String content, int number) {
		this.content = content;
		this.number = number;
	}

	public BulletinRockInfo(String content, int number, int mapId) {
		this.content = content;
		this.number = number;
		this.mapId = mapId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the mapId
	 */
	public int getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the mapId to set
	 */
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

}
