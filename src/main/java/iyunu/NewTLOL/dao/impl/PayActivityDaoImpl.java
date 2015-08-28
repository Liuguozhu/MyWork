package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.PayActivityDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.activity.DailyTime;
import iyunu.NewTLOL.model.activity.PayExchangeTime;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.payActivity.res.PayActivityInfoRes;
import iyunu.NewTLOL.model.payActivity.res.PayActivityListRes;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PayActivityDaoImpl extends SqlMapClientDaoSupport implements PayActivityDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PayActivityListRes> queryPayActivityList() {
		return this.getSqlMapClientTemplate().queryForList("PayActivitySQL.queryPayActivityList", ServerManager.instance().getServer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayActivityInfoRes> queryPayActivityInfo() {
		return this.getSqlMapClientTemplate().queryForList("PayActivitySQL.queryPayActivityInfo", ServerManager.instance().getServer());
	}

	@Override
	public PayExchangeTime queryPayExchangeTime() {
		return (PayExchangeTime) this.getSqlMapClientTemplate().queryForObject("PayActivitySQL.queryPayExchangeTime", ServerManager.instance().getServer());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DailyModel> queryDaily() {
		return (List<DailyModel>) this.getSqlMapClientTemplate().queryForList("PayActivitySQL.queryDaily", ServerManager.instance().getServer());
	}

	@Override
	public DailyTime queryDailyTime() {
		return (DailyTime) this.getSqlMapClientTemplate().queryForObject("PayActivitySQL.queryDailyTime", ServerManager.instance().getServer());
	}

	@Override
	public void delDaily() {
		this.getSqlMapClientTemplate().delete("PayActivitySQL.delDaily", ServerManager.instance().getServer());
	}
}
