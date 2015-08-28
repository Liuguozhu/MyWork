package iyunu.NewTLOL.model.trials.instance;

import java.util.HashMap;
import java.util.Map;

/**
 * @function 试炼资源类
 * @author LuoSR
 * @date 2014年4月16日
 */
public class TrialsInfo {

	private int trialsId; // 试炼编号
	private String name; // 试炼名称
	private int degree; // 难度
	private int levelLimit; // 等级限制
	private int recommendLevel; // 推荐等级
	private int memberLimit; // 人数限制
	private int numberLimit; // 次数限制
	private String icon; // icon
	private String des; // 奖励描述
	private int num; // 敌人数量

	private Map<Integer, Integer> monsterMap = new HashMap<Integer, Integer>(); // 第一个Integer为怪物位置，第二个Integer为怪物组ID

	public int getTrialsId() {
		return trialsId;
	}

	public void setTrialsId(int trialsId) {
		this.trialsId = trialsId;
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

	public Map<Integer, Integer> getMonsterMap() {
		return monsterMap;
	}

}
