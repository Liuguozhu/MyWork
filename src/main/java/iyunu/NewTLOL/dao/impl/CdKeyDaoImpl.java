package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.CdKeyDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.base.CdKeyInfo;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CdKeyDaoImpl extends SqlMapClientDaoSupport implements CdKeyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CdKeyInfo> query() {
		return this.getSqlMapClientTemplate().queryForList("CdKeySQL.query", ServerManager.instance().getServer());
	}

	@Override
	public void delete(String cdKey) {
		this.getSqlMapClientTemplate().update("CdKeySQL.delete", cdKey);
	}

}
