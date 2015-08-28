package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.message.TeamMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamManager {

	/** 队伍集合 **/
	public Map<Integer, Team> map = new HashMap<Integer, Team>();
	private static TeamManager instance = new TeamManager();
//	public static final int TEAM_LEVEL = 14;

	private TeamManager() {

	}

	/**
	 * 获取TeamManager实例
	 * 
	 * @return TeamManager实例
	 */
	public static TeamManager instance() {
		return instance;
	}

	/**
	 * @param id
	 *            队伍编号
	 * @return 队伍对象
	 */
	public Team getTeam(int id) {
		return map.get(id);
	}

	/**
	 * @param id
	 *            队伍编号
	 * @param team
	 *            队伍对象
	 * @return 是否保存成功
	 */
	public boolean saveTeam(int id, Team team) {
		if (!map.containsKey(id)) {
			map.put(id, team);
			return true;
		}
		return false;
	}

	/**
	 * 清除队伍信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public void clearTeam(Role role) {
		role.setTeam(null);
	}

	/**
	 * 解散队伍
	 * 
	 * @param team
	 *            队伍对象
	 */
	public void removeTeam(Team team) {
		// ======从队伍集合中删除该队伍======
		TeamManager.instance().getMap().remove(team.getId());
		// ======从地图中删除该队伍======
		MapManager.instance().removeTeam(team.getLeader().getMapInfo().getMapId(), team.getId());

		// ======改变队员的队伍状态======
		for (Role role : team.getMember()) {
			TeamManager.instance().clearTeam(role);
			TeamMessage.sendTeamState(role, 1); // 通知所有成员队伍解散
			TeamMessage.teamInform(role, "队伍解散");
		}
	}

	/**
	 * @return the map
	 */
	public Map<Integer, Team> getMap() {
		return map;
	}

}