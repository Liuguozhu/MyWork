package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.role.Role;

/**
 * 试炼
 * 
 * @author SunHonglei
 * 
 */
public class TrialsManager {

	/** 非VIP玩家默认重置次数 **/
	public static int DEFULT_COMM_RESET_NUMBER = 0;
	/** VIP玩家默认重置次数 **/
	public static int DEFULT_VIP_RESET_NUMBER = 1;

	/**
	 * 重置试炼可重置次数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void resetNum(Role role) {
//		role.setTrialsResetNum(DEFULT_VIP_RESET_NUMBER);
	}
}
