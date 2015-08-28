package iyunu.NewTLOL.service.impl.cdKey;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.manager.CdKeyManager;
import iyunu.NewTLOL.model.base.CdKeyInfo;
import iyunu.NewTLOL.service.iface.cdKey.CdKeyService;

import java.util.List;

public class CdKeyServiceImpl implements CdKeyService {

	@Override
	public List<CdKeyInfo> query() {
		return BaseDao.instance().getCdKeyDao().query();
	}

	@Override
	public void delete(String cdKey) {
		CdKeyManager.instance().delete(cdKey); // 从内存中删除
		BaseDao.instance().getCdKeyDao().delete(cdKey); // 去数据库中删除
	}

}
