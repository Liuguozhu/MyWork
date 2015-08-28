package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.mail.Mail;

import java.util.List;

public interface MailDao {

	/**
	 * 添加新邮件
	 * 
	 * @param mail
	 *            邮件对象
	 */
	void insert(final Mail mail);

	/**
	 * 获取邮件
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 邮件序列化集合
	 */
	List<Mail> query(final long roleId);

	/**
	 * 标记邮件已读
	 * 
	 * @param mail
	 *            邮件对象
	 */
	void update(final Mail mail);

	/**
	 * 标记已读
	 * 
	 * @param mail
	 */
	void mark(Mail mail);

	/**
	 * 删除邮件
	 * 
	 * @param mailId
	 *            邮件编号
	 */
	void delete(final long mailId);
	
	/**
	 * 删除已读邮件
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void deleteRead(final long roleId);

	/**
	 * 删除所有邮件
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void clean(final long roleId);

	/**
	 * 邮箱检查
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void check(long roleId);

}
