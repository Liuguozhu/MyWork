package iyunu.NewTLOL.server.blood;

public class BloodServer {

	public static long hongMark = 0;
	public static long lanMark = 0;

	/**
	 * @return the hongMark
	 */
	public static long getHongMark() {
		return hongMark;
	}

	/**
	 * @return the lanMark
	 */
	public static long getLanMark() {
		return lanMark;
	}

	/**
	 * 同步的增加红方总分的方法 ， 不知道会不会有性能问题
	 * 
	 * @param mark
	 */
	public synchronized static void addHongMark(int mark) {
		hongMark = hongMark + mark;
	}

	/**
	 * 同步的增加蓝方总分
	 * 
	 * @param mark
	 */
	public synchronized static void addLanMark(int mark) {
		lanMark = lanMark + mark;
	}

}
