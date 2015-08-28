package iyunu.NewTLOL.service.impl.payActivity;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.base.PayBackInfo;
import iyunu.NewTLOL.service.iface.payActivity.PayBackService;

public class PayBackServiceImpl implements PayBackService {

	@Override
	public PayBackInfo query(String userId) {
		return BaseDao.instance().getPayBackDao().query(userId);
	}

	@Override
	public void delete(String userId) {
		BaseDao.instance().getPayBackDao().delete(userId); // 从数据库中删除
	}

}
