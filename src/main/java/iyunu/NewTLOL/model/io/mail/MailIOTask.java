package iyunu.NewTLOL.model.io.mail;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.service.iface.mail.MailService;

public class MailIOTask {

	private EMailIOTask id; // 任务编号
	private Mail mail; // 邮件对象
	private long roleId; // 角色编号
	private long mailId; // 邮件编号

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            任务编号
	 * @param role
	 *            角色对象
	 */
	public MailIOTask(EMailIOTask id, Mail mail, long roleId, long mailId) {
		this.id = id;
		this.mail = mail;
		this.roleId = roleId;
		this.mailId = mailId;
	}

	/**
	 * 执行方法
	 */
	public void excute() {
		MailService mailService = Spring.instance().getBean("mailService", MailService.class);
		switch (id) {
		case insert:
			if (mail.getRead() == 0 && ServerManager.instance().isOnline(roleId)) {
				SendMessage.sendNewMail(ServerManager.instance().getOnlinePlayer(roleId));
			}
			mailService.insert(mail);
			break;
		case update:
			mailService.update(mail);
			break;
		case mark:
			mailService.mark(mail);
			break;
		case delete:
			mailService.delete(mailId);
			break;
		case deleteRead:
			mailService.deleteRead(roleId);
			break;
		case clean:
			mailService.clean(roleId);
			break;
		case check:
//			mailService.check(roleId);
			break;

		default:
			break;
		}
	}

	/**
	 * @return the id
	 */
	public EMailIOTask getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(EMailIOTask id) {
		this.id = id;
	}

	/**
	 * @return the mail
	 */
	public Mail getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(Mail mail) {
		this.mail = mail;
	}

	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the mailId
	 */
	public long getMailId() {
		return mailId;
	}

	/**
	 * @param mailId
	 *            the mailId to set
	 */
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

}
