package iyunu.NewTLOL.service.impl.bulletin;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.bulletin.BulletinChat;
import iyunu.NewTLOL.model.bulletin.BulletinRock;
import iyunu.NewTLOL.service.iface.bulletin.BulletinService;

import java.util.List;

public class BulletinServiceImpl implements BulletinService {

	@Override
	public String querySys() {
		return BaseDao.instance().getBulletinDao().querySys();
	}

	@Override
	public List<BulletinChat> queryChat() {
		return BaseDao.instance().getBulletinDao().queryChat();
	}

	@Override
	public List<BulletinRock> queryRock() {
		return BaseDao.instance().getBulletinDao().queryRock();
	}

	@Override
	public String queryLogon() {
		return BaseDao.instance().getBulletinDao().queryLogon();
	}

}
