package iyunu.NewTLOL.dao.impl;

import iyunu.NewTLOL.dao.iface.RoleDao;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Translate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class RoleDaoImpl extends SqlMapClientDaoSupport implements RoleDao {

	@Override
	public long queryRoleId(String userId, int serverId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("srv", ServerManager.instance().getServer());
		map.put("serverId", String.valueOf(serverId));
		return Translate.longToLong((Long) this.getSqlMapClientTemplate().queryForObject("RoleSQL.queryRoleId", map));
	}

	@Override
	public boolean checkNick(String nick) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nick", nick);
		map.put("srv", ServerManager.instance().getServer());
		int number = (int) this.getSqlMapClientTemplate().queryForObject("RoleSQL.check", map);
		if (number == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void insert(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().insert("RoleSQL.insert", role);
	}

	@Override
	public Role query(long id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("srv", ServerManager.instance().getServer());
		Role role = (Role) this.getSqlMapClientTemplate().queryForObject("RoleSQL.query", map);
		return role;
	}

	@Override
	public void update(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.update", role);
	}

	@Override
	public void delete(long roleId) {
		this.getSqlMapClientTemplate().update("RoleSQL.delete", roleId);
	}

	@Override
	public long getIdByName(Map<String, String> map) {
		return (long) this.getSqlMapClientTemplate().queryForObject("RoleSQL.getIdByName", map);
	}

	@Override
	public RoleCard queryRoleCard(long roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(roleId));
		map.put("srv", ServerManager.instance().getServer());
		return (RoleCard) this.getSqlMapClientTemplate().queryForObject("RoleSQL.getRoleCard", map);
	}

//	@Override
//	public void updateJob(RoleCard roleCard) {
//		roleCard.setSrv(ServerManager.instance().getServer());
//		this.getSqlMapClientTemplate().update("RoleSQL.updateJob", roleCard);
//
//	}

	@Override
	public void addMute(long roleId, long time) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(roleId));
		map.put("time", String.valueOf(time));
		this.getSqlMapClientTemplate().update("mute", map);

	}

	@Override
	public void updateMoney(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.updateMoney", role);
	}

	@Override
	public void updateVip(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.updateVip", role);
	}

	@Override
	public void updatePlusMoney(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.updatePlusMoney", role);
	}

	@Override
	public void updateFirstDouble(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.updateFirstDouble", role);
	}

//	@Override
//	public void updateRoleCard(RoleCard roleCard) {
//		this.getSqlMapClientTemplate().update("RoleSQL.updateRoleCard", roleCard);
//
//	}

	@Override
	public void updateDailyPay(Role role) {
		role.setSrv(ServerManager.instance().getServer());
		this.getSqlMapClientTemplate().update("RoleSQL.updateDailyPay", role);
	}
}