package iyunu.NewTLOL.model.team;

public class TeamInfo {

	int teamId; // 队伍ID
	long headerId; // 队长ID
	long teamerId; // 队员ID
	String describe; // 描述

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(long headerId) {
		this.headerId = headerId;
	}

	public long getTeamerId() {
		return teamerId;
	}

	public void setTeamerId(long teamerId) {
		this.teamerId = teamerId;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
