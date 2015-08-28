package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;

import java.util.Map;

public interface RoleDao {

	/**
	 * 查询角色编号
	 * 
	 * @param userId
	 *            用户编号
	 * @return 角色编号
	 */
	long queryRoleId(String userId, int serverId);

	/**
	 * 验证角色名称
	 * 
	 * @param nick
	 *            角色名称
	 * @return 角色名称已存在
	 */
	boolean checkNick(String nick);

	/**
	 * 添加角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	void insert(Role role);

	/**
	 * 查询角色信息
	 * 
	 * @param id
	 *            角色编号
	 * @return 角色对象
	 */
	Role query(long id);

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	void update(Role role);

	/**
	 * 删除角色信息（置标志位）
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void delete(long roleId);

	/**
	 * @param name
	 *            角色名称
	 */
	long getIdByName(Map<String, String> map);

	RoleCard queryRoleCard(long roleId);

	/**
	 * 修改帮派职务
	 * 
	 * @param roleCard
	 *            角色信息对象
	 */
//	void updateJob(RoleCard roleCard);

	/**
	 * 设置禁言
	 * 
	 * @param roleId
	 *            角色编号
	 * @param time
	 *            禁言时间
	 */
	void addMute(long roleId, long time);

	/**
	 * @function 更新元宝
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年5月5日
	 */
	void updateMoney(Role role);

	/**
	 * @function 更新VIP
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年5月5日
	 */
	void updateVip(Role role);

//	void updateRoleCard(RoleCard roleCard);

	void updatePlusMoney(Role role);

	void updateDailyPay(Role role);

	void updateFirstDouble(Role role);
}
