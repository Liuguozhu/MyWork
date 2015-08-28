package iyunu.NewTLOL.model.intensify.res;

import iyunu.NewTLOL.model.intensify.instance.StoneRecipe;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.util.Translate;

/**
 * @function 配件制作配方资源类
 * @author LuoSR
 * @date 2013年12月6日
 */
public class StoneRecipeRes {

	private int id;
	private int material1;
	private int number1;
	private int material2;
	private int number2;
	private int material3;
	private int number3;
	private int gold;
	private String item;

	public StoneRecipe toEquipStone() {
		StoneRecipe equipStone = new StoneRecipe();
		equipStone.setId(id);
		equipStone.setMaterial1(material1);
		equipStone.setNumber1(number1);
		equipStone.setMaterial2(material2);
		equipStone.setNumber2(number2);
		equipStone.setMaterial3(material3);
		equipStone.setNumber3(number3);
		equipStone.setGold(gold);

		String[] str = item.split(";");

		for (String string : str) {
			String[] itemStr = string.split(":");
			equipStone.getItems().add(new MonsterDropItem(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2]), 0));
		}

		return equipStone;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
