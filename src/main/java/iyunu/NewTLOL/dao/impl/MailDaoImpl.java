package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.MailDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.mail.Mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class MailDaoImpl extends SqlMapClientDaoSupport implements MailDao {

	@Override
	public void insert(Mail mail) {
		this.getSqlMapClientTemplate().insert("MailSQL.insert", mail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mail> query(long roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("receiveId", String.valueOf(roleId));
		return this.getSqlMapClientTemplate().queryForList("MailSQL.query", map);
	}

	@Override
	public void update(Mail mail) {
		mail.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("MailSQL.update", mail);
	}

	@Override
	public void mark(Mail mail) {
		mail.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("MailSQL.mark", mail);
	}

	@Override
	public void delete(long mailId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("mailId", String.valueOf(mailId));
		this.getSqlMapClientTemplate().delete("MailSQL.delete", map);
	}

	@Override
	public void deleteRead(long roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("receiveId", String.valueOf(roleId));
		this.getSqlMapClientTemplate().delete("MailSQL.deleteRead", map);
	}

	@Override
	public void clean(long roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("receiveId", String.valueOf(roleId));
		this.getSqlMapClientTemplate().delete("MailSQL.clean", map);
	}

	@Override
	public void check(long roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("receiveId", String.valueOf(roleId));
		this.getSqlMapClientTemplate().delete("MailSQL.check", map);
	}

}
