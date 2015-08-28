package iyunu.NewTLOL.model.friend;

public class Friend {
	private long id; // 玩家
	private String nick; // 角色名
	private long figure; // 形象编号
	private String vocation; // 职业名称
	private int level; // 等级

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the figure
	 */
	public long getFigure() {
		return figure;
	}

	/**
	 * @param figure
	 *            the figure to set
	 */
	public void setFigure(long figure) {
		this.figure = figure;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
