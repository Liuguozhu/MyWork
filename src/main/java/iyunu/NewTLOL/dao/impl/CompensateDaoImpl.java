package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.CompensateDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.compensate.Compensate;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CompensateDaoImpl extends SqlMapClientDaoSupport implements CompensateDao {

	@Override
	public Compensate query() {
		return (Compensate) this.getSqlMapClientTemplate().queryForObject("CompensateSQL.query", ServerManager.instance().getServer());
	}

}
