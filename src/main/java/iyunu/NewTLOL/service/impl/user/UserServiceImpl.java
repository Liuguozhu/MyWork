package iyunu.NewTLOL.service.impl.user;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.service.iface.user.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public boolean checkName(String name) {
		int num = BaseDao.instance().getUserDao().queryByName(name);
		if (num == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void update(String userId, String userName, String password) {
		BaseDao.instance().getUserDao().update(userId, userName, password);
	}

	@Override
	public void updatePhone(String userId, String phone) {
		BaseDao.instance().getUserDao().updatePhone(userId, phone);
	}

	@Override
	public String queryPhone(String userId) {
		return BaseDao.instance().getUserDao().queryPhone(userId);
	}

}
