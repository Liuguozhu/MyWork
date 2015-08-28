package iyunu.NewTLOL.model.role.title.res;

import iyunu.NewTLOL.model.role.title.instance.Title;

public class TitleRes {

	private int index;
	private String name;
	private String desc;

	public Title toTitle() {
		Title title = new Title();
		title.setIndex(index);
		title.setName(name);
		title.setDesc(desc);
		return title;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
