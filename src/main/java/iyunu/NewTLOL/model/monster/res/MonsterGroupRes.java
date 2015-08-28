package iyunu.NewTLOL.model.monster.res;

import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.util.Translate;

/**
 * 资源类 怪物组合
 * 
 * @author SunHonglei
 * 
 */
public class MonsterGroupRes {

	private int id; // 编号
	private String name; // 名称
	private int number; // 怪物总数量（-1为按人数计算）
	private int category; // 种类
	private int type; // 类型
	private String monster; // 怪物组合
	private String res; // 形象（0固定怪物等级，1根据角色队伍等级计算）
	private int mark;// 血战标识
	private int taskMark;// 任务表示（1剧情任务）
	private int levelLimit = 1; // 等级限制
	private int teamLeader = 0;// 0正常掉落1只有队长掉落

	public MonsterGroup toMonsterGroupRes() {
		MonsterGroup monsterGroup = new MonsterGroup();
		monsterGroup.setId(id);
		monsterGroup.setName(name);
		monsterGroup.setNumber(number);
		monsterGroup.setRes(res);
		monsterGroup.setMark(mark);
		monsterGroup.setCategory(category);
		monsterGroup.setType(type);
		monsterGroup.setTaskMark(taskMark);
		monsterGroup.setLevelLimit(levelLimit);
		monsterGroup.setTeamLeader(teamLeader);
		String[] monsterIds = monster.split(";");
		for (String string : monsterIds) {
			long monsterId = Translate.stringToLong(string);
			if (MonsterJson.instance().checkMonsterId(monsterId)) {
				monsterGroup.getMonsters().add(monsterId);
			}
		}
		return monsterGroup;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getMonster() {
		return monster;
	}

	public void setMonster(String monster) {
		this.monster = monster;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the res
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res) {
		this.res = res;
	}

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * @return the taskMark
	 */
	public int getTaskMark() {
		return taskMark;
	}

	/**
	 * @param taskMark
	 *            the taskMark to set
	 */
	public void setTaskMark(int taskMark) {
		this.taskMark = taskMark;
	}

	/**
	 * @return the levelLimit
	 */
	public int getLevelLimit() {
		return levelLimit;
	}

	/**
	 * @param levelLimit
	 *            the levelLimit to set
	 */
	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	/**
	 * @return the teamLeader
	 */
	public int getTeamLeader() {
		return teamLeader;
	}

	/**
	 * @param teamLeader
	 *            the teamLeader to set
	 */
	public void setTeamLeader(int teamLeader) {
		this.teamLeader = teamLeader;
	}

}
