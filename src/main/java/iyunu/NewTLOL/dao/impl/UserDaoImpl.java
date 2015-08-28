package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.UserDao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {

	@Override
	public int queryByName(String name) {
		return (int) this.getSqlMapClientTemplate().queryForObject("UserSQL.queryByName", name);
	}

	@Override
	public void update(String userId, String userName, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("password", password);
		this.getSqlMapClientTemplate().update("UserSQL.update", map);
	}

	@Override
	public void updatePhone(String userId, String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("phone", phone);
		this.getSqlMapClientTemplate().update("UserSQL.updatePhone", map);

	}

	@Override
	public String queryPhone(String userId) {
		return (String) this.getSqlMapClientTemplate().queryForObject("UserSQL.queryPhone", userId);
	}

}
