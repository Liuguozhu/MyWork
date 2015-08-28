package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.activity.DailyTime;
import iyunu.NewTLOL.model.activity.PayExchangeTime;
import iyunu.NewTLOL.model.daily.DailyModel;
import iyunu.NewTLOL.model.payActivity.res.PayActivityInfoRes;
import iyunu.NewTLOL.model.payActivity.res.PayActivityListRes;

import java.util.List;

public interface PayActivityDao {

	List<PayActivityListRes> queryPayActivityList();

	List<PayActivityInfoRes> queryPayActivityInfo();

	PayExchangeTime queryPayExchangeTime();

	DailyTime queryDailyTime();

	void delDaily();

	List<DailyModel> queryDaily();
}
