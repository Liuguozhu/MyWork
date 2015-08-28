package iyunu.NewTLOL.service.impl.gang;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.service.iface.gang.GangService;

import java.util.List;

public class GangServiceImpl implements GangService {

	@Override
	public List<Gang> loadAllGang() {
		return BaseDao.instance().getGangDao().loadAllGang();
	}

	@Override
	public void insertGang(Gang gang) {
		BaseDao.instance().getGangDao().insertGang(gang);

	}

	@Override
	public void updateGang(Gang gang) {
		BaseDao.instance().getGangDao().updateGang(gang);

	}

	@Override
	public void deleteGang(long gangId) {
		BaseDao.instance().getGangDao().deleteGang(gangId);
	}

}
