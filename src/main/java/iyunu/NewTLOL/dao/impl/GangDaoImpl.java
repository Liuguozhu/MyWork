package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.GangDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.gang.Gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class GangDaoImpl extends SqlMapClientDaoSupport implements GangDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Gang> loadAllGang() {
		String srv = ServerManager.instance().getServer();
		return this.getSqlMapClientTemplate().queryForList("GangSQL.query", srv);
	}

	@Override
	public void insertGang(Gang gang) {
		gang.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().insert("GangSQL.insert", gang);

	}

	@Override
	public void updateGang(Gang gang) {
		gang.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("GangSQL.update", gang);
	}

	@Override
	public void deleteGang(long gangId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("srv", ServerManager.instance().getServer());
		map.put("id", String.valueOf(gangId));
		this.getSqlMapClientTemplate().update("GangSQL.delete", map);
	}

}
