package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.MultipleDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.multiple.Multiple;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class MultipleDaoImpl extends SqlMapClientDaoSupport implements MultipleDao {

	@Override
	public Multiple query() {
		return (Multiple) this.getSqlMapClientTemplate().queryForObject("MultipleSQL.query", ServerManager.instance().getServer());
	}

}
