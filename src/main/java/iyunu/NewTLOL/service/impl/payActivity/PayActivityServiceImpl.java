package iyunu.NewTLOL.service.impl.payActivity;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.activity.DailyTime;
import iyunu.NewTLOL.model.activity.PayExchangeTime;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.payActivity.res.PayActivityInfoRes;
import iyunu.NewTLOL.model.payActivity.res.PayActivityListRes;
import iyunu.NewTLOL.service.iface.payActivity.PayActivityService;

import java.util.List;

public class PayActivityServiceImpl implements PayActivityService {

	@Override
	public List<PayActivityListRes> queryPayActivityList() {
		return BaseDao.instance().getPayActivityDao().queryPayActivityList();
	}

	@Override
	public List<PayActivityInfoRes> queryPayActivityInfo() {
		return BaseDao.instance().getPayActivityDao().queryPayActivityInfo();
	}

	@Override
	public List<DailyModel> queryDaily() {
		return BaseDao.instance().getPayActivityDao().queryDaily();
	}

	@Override
	public PayExchangeTime queryPayExchangeTime() {
		return BaseDao.instance().getPayActivityDao().queryPayExchangeTime();
	}

	@Override
	public DailyTime queryDailyTime() {
		return BaseDao.instance().getPayActivityDao().queryDailyTime();
	}

	@Override
	public void delDaily() {
		BaseDao.instance().getPayActivityDao().delDaily();
	}

}
