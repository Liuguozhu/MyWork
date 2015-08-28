package iyunu.NewTLOL.model.activity.res;

import iyunu.NewTLOL.model.activity.instance.DrawingAward;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.monster.DropMonster;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

public class DrawingAwardRes {

	private int index;
	private EOpen reward;

	private String type;
	private String items;
	private String fight;
	private String monster;

	public DrawingAward toDrawingAward() {
		DrawingAward drawingAward = new DrawingAward();
		drawingAward.setIndex(index);
		drawingAward.setReward(reward);

		if (type != null && !"".equals(type)) {
			String[] str = type.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				drawingAward.getType().put(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]));
			}
		}

		if (items != null && !"".equals(items)) {
			String[] str = items.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				drawingAward.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
			}
		}

		if (fight != null && !"".equals(fight)) {
			String[] str = fight.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				drawingAward.getFight().put(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]));
			}
		}

		if (monster != null && !"".equals(monster)) {
			String[] str = monster.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				drawingAward.getMonsters().add(new DropMonster(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), Translate.stringToInt(itemStr[3])));
			}
		}

		return drawingAward;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the reward
	 */
	public EOpen getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(EOpen reward) {
		this.reward = reward;
	}

	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the fight
	 */
	public String getFight() {
		return fight;
	}

	/**
	 * @param fight
	 *            the fight to set
	 */
	public void setFight(String fight) {
		this.fight = fight;
	}

	/**
	 * @return the monster
	 */
	public String getMonster() {
		return monster;
	}

	/**
	 * @param monster
	 *            the monster to set
	 */
	public void setMonster(String monster) {
		this.monster = monster;
	}

}
