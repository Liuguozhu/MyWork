package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.PayBackDao;
import iyunu.NewTLOL.model.base.PayBackInfo;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PayBackInfoDaoImpl extends SqlMapClientDaoSupport implements PayBackDao {

	@Override
	public PayBackInfo query(String userId) {
		return (PayBackInfo) this.getSqlMapClientTemplate().queryForObject("PayBackSQL.query", userId);
	}

	@Override
	public void delete(String userId) {
		this.getSqlMapClientTemplate().update("PayBackSQL.delete", userId);
	}

}
