package iyunu.NewTLOL.model.helper.res;

import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

/**
 * 活跃度奖励
 * 
 * @author SunHonglei
 * 
 */
public class HelperAwardRes {

	private int score;
	private String item;

	public HelperAward toHelperAward() {
		HelperAward helperAward = new HelperAward();
		helperAward.setScore(score);
		String[] stings = item.split(";");
		for (int i = 0; i < stings.length; i++) {
			String[] itemStr = stings[i].split(":");
			helperAward.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), 0, Translate.stringToInt(itemStr[2])));
		}
		return helperAward;

	}

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

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

}
