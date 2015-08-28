package iyunu.NewTLOL.service.impl.compensate;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.compensate.Compensate;
import iyunu.NewTLOL.service.iface.compensate.CompensateService;

public class CompensateServiceImpl implements CompensateService {

	@Override
	public Compensate query() {
		return BaseDao.instance().getCompensateDao().query();
	}

}
