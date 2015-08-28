package iyunu.NewTLOL.model.trials.res;

import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.util.Translate;

/**
 * @function 试炼资源类
 * @author LuoSR
 * @date 2014年4月16日
 */
public class TrialsInfoRes {

	private int id; // 试炼编号
	private String name; // 试炼名称
	private int degree; // 难度
	private int levelLimit; // 等级限制
	private int recommendLevel; // 推荐等级
	private int memberLimit; // 人数限制
	private int numberLimit; // 次数限制
	private String monster; // 怪物
	private String icon; // icon
	private String des; // 奖励描述
	private int num; // 敌人数量

	public TrialsInfo toTrialsInfo() {
		TrialsInfo trialsInfo = new TrialsInfo();

		trialsInfo.setTrialsId(id);
		trialsInfo.setName(name);
		trialsInfo.setDegree(degree);
		trialsInfo.setLevelLimit(levelLimit);
		trialsInfo.setRecommendLevel(recommendLevel);
		trialsInfo.setMemberLimit(memberLimit);
		trialsInfo.setNumberLimit(numberLimit);
		trialsInfo.setIcon(icon);
		trialsInfo.setDes(des);
		trialsInfo.setNum(num);

		if (monster != null && !"".equals(monster)) {

			String[] str = monster.split(";");
			for (int i = 0; i < str.length; i++) {
				trialsInfo.getMonsterMap().put(i + 1, Translate.stringToInt(str[i]));
			}
		}

		return trialsInfo;
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

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public int getRecommendLevel() {
		return recommendLevel;
	}

	public void setRecommendLevel(int recommendLevel) {
		this.recommendLevel = recommendLevel;
	}

	public int getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	public int getNumberLimit() {
		return numberLimit;
	}

	public void setNumberLimit(int numberLimit) {
		this.numberLimit = numberLimit;
	}

	public String getMonster() {
		return monster;
	}

	public void setMonster(String monster) {
		this.monster = monster;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
