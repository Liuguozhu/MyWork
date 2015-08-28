package iyunu.NewTLOL.manager;


/**
 * @function 千层塔
 * @author LuoSR
 * @date 2014年8月14日
 */
public class QiancengtaManager {

	private static QiancengtaManager instance = new QiancengtaManager();
	public static final int TEAM_LEVEL = 16;

	private QiancengtaManager() {

	}

	/**
	 * 获取QiancengtaManager实例
	 * 
	 * @return QiancengtaManager实例
	 */
	public static QiancengtaManager instance() {
		return instance;
	}

	/** 免费重置次数 **/
	public static int NULL_RESET_NUMBER = 1;
	/** 银两重置使用次数 **/
	public static int MONEY_RESET_NUMBER = 0;

}
