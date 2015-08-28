package iyunu.NewTLOL.service.impl.multiple;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.multiple.Multiple;
import iyunu.NewTLOL.service.iface.multiple.MultipleService;

public class MultipleServiceImpl implements MultipleService {

	@Override
	public Multiple query() {
		return BaseDao.instance().getMultipleDao().query();
	}

}
