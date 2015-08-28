package iyunu.NewTLOL.model.intensify.instance;

import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;

/**
 * @function 配件制作配方类
 * @author LuoSR
 * @date 2013年12月6日
 */
public class StoneRecipe {

	private int id;
	private int material1;
	private int number1;
	private int material2;
	private int number2;
	private int material3;
	private int number3;
	private int gold;

	/** 生成宝石 **/
	private ArrayList<MonsterDropItem> items = new ArrayList<MonsterDropItem>();

	/**
	 * 物品生成
	 * 
	 * @return 掉落物品
	 */
	public MonsterDropItem drop() {
		int sum = 0;
		int finalRate = Util.getRandom(10000);
		for (MonsterDropItem monsterItem : items) {
			sum += monsterItem.getProbability();
			if (sum > finalRate) {
				return monsterItem;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaterial1() {
		return material1;
	}

	public void setMaterial1(int material1) {
		this.material1 = material1;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getMaterial2() {
		return material2;
	}

	public void setMaterial2(int material2) {
		this.material2 = material2;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public int getMaterial3() {
		return material3;
	}

	public void setMaterial3(int material3) {
		this.material3 = material3;
	}

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public ArrayList<MonsterDropItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MonsterDropItem> items) {
		this.items = items;
	}

}
