package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Book;

/**
 * 技能书实例类
 * 
 * @author SunHonglei
 * 
 */
public class BookRes extends BaseItemRes {

	private int skillId;

	public Item toItem() {
		Book item = (Book) super.toItem();

		item.setSkillId(skillId);
		return item;
	}

	/**
	 * @return the skillId
	 */
	public int getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId
	 *            the skillId to set
	 */
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

}
