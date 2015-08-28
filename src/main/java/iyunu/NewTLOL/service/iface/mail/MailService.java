package iyunu.NewTLOL.service.iface.mail;

import iyunu.NewTLOL.model.mail.Mail;

import java.util.List;

public interface MailService {

	void insert(final Mail mail);

	List<Mail> query(final long roleId);

	void delete(final long mailId);

	/**
	 * 删除已读邮件
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void deleteRead(final long roleId);

	void clean(final long roleId);

	/**
	 * 标记为已读
	 * 
	 * @param roleId
	 * @param mail
	 */
	void mark(final Mail mail);

	/**
	 * 邮件更新
	 * 
	 * @param mail
	 *            邮件对象
	 */
	void update(final Mail mail);

	/**
	 * 邮件检查
	 * 
	 * @param roleId
	 *            角色编号
	 */
	void check(long roleId);

	/**
	 * 发送邮件
	 * 
	 * @param roleId
	 *            角色编号
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param itemIds
	 *            物品编号集合
	 * @param gold
	 *            金币
	 * @param money
	 *            元宝
	 * @param partner
	 *            伙伴
	 */
//	void send(long roleId, String title, String content, HashMap<Integer, Integer> itemIds, int gold, int money, Partner partner);

//	void send(long roleId, String title, String content, int itemId, int num, int gold, int money, Partner partner, String senName);
}
