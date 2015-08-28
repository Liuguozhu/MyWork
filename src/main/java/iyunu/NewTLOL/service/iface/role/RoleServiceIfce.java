package iyunu.NewTLOL.service.iface.role;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;

import org.jboss.netty.channel.Channel;

public interface RoleServiceIfce {

	/**
	 * 查询角色编号
	 * 
	 * @param userId
	 *            用户编号
	 * @return 角色编号
	 */
	long queryRoleId(String userId,int serverId);

	/**
	 * 验证角色名称
	 * 
	 * @param nick
	 *            角色名称
	 * @return 角色名称已存在
	 */
	boolean checkNick(String nick);

	/**
	 * 插入角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	void insert(Role role);

	/**
	 * 查询角色信息
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 角色对象
	 */
	Role query(long roleId);

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	void update(Role role);

	/**
	 * 删除角色信息
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void delete(long roleId);

	/**
	 * 根据角色名称查询角色编号
	 * 
	 * @param name
	 *            角色名称
	 * @return 角色编号
	 */
	long getIdByName(String name);

	/**
	 * @param roleId
	 *            角色ID
	 * @return RoleCard
	 */
	RoleCard queryRoleCard(long roleId);

	/**
	 * 修改帮派职务
	 * 
	 * @param roleCard
	 *            角色信息对象
	 */
//	void updateJob(RoleCard roleCard);

	/**
	 * 初始化角色信息
	 * 
	 * @param role
	 *            角色对象
	 * @param change
	 *            是否修改用户名
	 * @param channel
	 *            连接
	 */
	void initRole(Role role, int change, Channel channel);

	/**
	 * 设置禁言时间
	 * 
	 * @param roleId
	 *            角色编号
	 * @param time
	 *            禁言时间
	 */
	void addMute(long roleId, int time);

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

	/**
	 * @function 更新累积充值
	 * @author fhy
	 * @param role
	 *            角色对象
	 * @date 2014年11月12日
	 */
	void updatePlusMoney(Role role);

	/**
	 * @function 更新日常任务
	 * @author fhy
	 * @param role
	 *            角色对象
	 * @date 2014年12月1日
	 */
	void updateDailyPay(Role role);
	/**
	 * @function 更新首次双倍
	 * @author fhy
	 * @param role
	 *            角色对象
	 * @date 2014年11月12日
	 */
	void updateFirstDouble(Role role);

//	void updateRoleCard(RoleCard roleCard);

	/**
	 * 金矿查询Role对象
	 * 
	 * @param roleId
	 *            角色编号
	 * @return Role对象
	 */
	Role queryRole(long roleId);
}
