package iyunu.NewTLOL.dao.iface;

public interface UserDao {

	/**
	 * 通过用户名查询用户
	 * 
	 * @param name
	 *            用户名
	 * @return 数量
	 */
	int queryByName(String name);

	/**
	 * 更新用户信息
	 * 
	 * @param userId
	 *            用户编号
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 */
	void update(String userId, String userName, String password);

	/**
	 * 修改绑定手机号
	 * 
	 * @param userId
	 *            用户编号
	 * @param phone
	 *            手机号
	 */
	void updatePhone(String userId, String phone);

	/**
	 * 查询绑定手机号
	 * 
	 * @param userId
	 *            用户编号
	 * @return 手机号
	 */
	String queryPhone(String userId);
}
