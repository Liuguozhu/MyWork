package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.BulletinDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.bulletin.BulletinChat;
import iyunu.NewTLOL.model.bulletin.BulletinRock;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BulletinDaoImpl extends SqlMapClientDaoSupport implements BulletinDao {

	@Override
	public String querySys() {
		return (String) this.getSqlMapClientTemplate().queryForObject("BulletinSQL.querySys", ServerManager.instance().getServer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BulletinChat> queryChat() {
		return this.getSqlMapClientTemplate().queryForList("BulletinSQL.queryChat", ServerManager.instance().getServer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BulletinRock> queryRock() {
		return this.getSqlMapClientTemplate().queryForList("BulletinSQL.queryRock", ServerManager.instance().getServer());
	}

	@Override
	public String queryLogon() {
		return (String) this.getSqlMapClientTemplate().queryForObject("BulletinSQL.queryLogon", ServerManager.instance().getServer());
	}

}
