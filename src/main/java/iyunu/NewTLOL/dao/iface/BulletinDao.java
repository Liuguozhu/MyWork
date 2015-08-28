package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.bulletin.BulletinChat;
import iyunu.NewTLOL.model.bulletin.BulletinRock;

import java.util.List;

public interface BulletinDao {

	/**
	 * 查询系统公告
	 * 
	 * @return 系统公告
	 */
	String querySys();

	/**
	 * 查询聊天公告
	 * 
	 * @return 聊天公告集合
	 */
	List<BulletinChat> queryChat();

	/**
	 * 查询滚屏公告
	 * 
	 * @return 滚屏公告集合
	 */
	List<BulletinRock> queryRock();
	
	/**
	 * 查询登录公告
	 * 
	 * @return 登录公告
	 */
	String queryLogon();
}
