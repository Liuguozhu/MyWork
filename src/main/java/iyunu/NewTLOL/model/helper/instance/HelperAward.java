package iyunu.NewTLOL.model.helper.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;

import java.util.ArrayList;

/**
 * 活跃度奖励
 * 
 * @author SunHonglei
 * 
 */
public class HelperAward {

	private int score;
	/** 活跃度奖励物品 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}

}
