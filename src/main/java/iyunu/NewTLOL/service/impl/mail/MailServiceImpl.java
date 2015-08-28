package iyunu.NewTLOL.service.impl.mail;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.mail.Mail;
import iyunu.NewTLOL.redis.Redis;
import iyunu.NewTLOL.service.iface.mail.MailService;

import java.util.List;

public class MailServiceImpl extends Redis implements MailService {

	@Override
	public void insert(Mail mail) {
		BaseDao.instance().getMailDao().insert(mail);
	}

	@Override
	public List<Mail> query(long roleId) {
		List<Mail> list = BaseDao.instance().getMailDao().query(roleId);

		for (Mail mail : list) {
			mail.change();
		}

		return list;
	}

	@Override
	public void delete(long mailId) {
		BaseDao.instance().getMailDao().delete(mailId);
	}

	@Override
	public void deleteRead(long roleId) {
		BaseDao.instance().getMailDao().deleteRead(roleId);
	}

	@Override
	public void clean(long roleId) {
		BaseDao.instance().getMailDao().clean(roleId);
	}

	@Override
	public void mark(Mail mail) {
		mail.setRead(1);
		BaseDao.instance().getMailDao().mark(mail);
	}

	@Override
	public void update(Mail mail) {
		// mail.setGold(0);
		// mail.getItems().clear();
		mail.clear();
		BaseDao.instance().getMailDao().update(mail);
	}

	@Override
	public void check(long roleId) {
		BaseDao.instance().getMailDao().check(roleId);
	}

}
