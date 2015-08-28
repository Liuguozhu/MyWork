package iyunu.NewTLOL.model.monster.instance;

import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 怪物组合
 * 
 * @author SunHonglei
 * 
 */
public class MonsterGroup {

	private int id; // 编号
	private String name = ""; // 名称
	private int number; // 怪物总数量（-1为按人数计算）
	private List<Long> monsters = new ArrayList<Long>(); // 怪物编号集合
	private String res; // 怪物形象
	private int category; // 种类
	private int type; // 类型
	private int mark;// 血战标识
	private int taskMark; // 任务表示（1剧情任务）
	private int levelLimit = 1; // 等级限制
	private int teamLeader = 0;// 0正常1只有队长掉落

	/**
	 * 随机获取怪物编号
	 * 
	 * @return 怪物编号
	 */
	public long getRandomMonster() {
		if (monsters.size() <= 0) {
			return 0;
		}
		return monsters.get(Util.getRandom(monsters.size()));
	}

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public MonsterGroup copy() {
		MonsterGroup monsterGroup = new MonsterGroup();
		monsterGroup.setId(id);
		monsterGroup.setName(name);
		monsterGroup.setNumber(number);
		monsterGroup.setMonsters(monsters);
		monsterGroup.setRes(res);
		monsterGroup.setCategory(category);
		monsterGroup.setType(type);
		monsterGroup.setLevelLimit(levelLimit);
		monsterGroup.setTeamLeader(teamLeader);
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

	/**
	 * @return the monsters
	 */
	public List<Long> getMonsters() {
		return monsters;
	}

	/**
	 * @param monsters
	 *            the monsters to set
	 */
	public void setMonsters(List<Long> monsters) {
		this.monsters = monsters;
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
