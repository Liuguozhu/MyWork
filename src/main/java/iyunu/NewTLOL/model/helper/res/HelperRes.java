package iyunu.NewTLOL.model.helper.res;

import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.helper.instance.Helper;
import iyunu.NewTLOL.util.Translate;

/**
 * 小助手
 * 
 * @author SunHonglei
 * 
 */
public class HelperRes {

	private int index;
	private String name;
	private EHelper type;
	private int score;
	private int sum;
	private int level;
	private String start;
	private String end;
	private String button;
	private int npcid;

	public Helper toHelper() {
		Helper helper = new Helper();
		helper.setIndex(index);
		helper.setName(name);
		helper.setType(type);
		helper.setScore(score);
		helper.setSum(sum);
		helper.setLevel(level);
		helper.setButton(button);
		helper.setNpcid(npcid);

		String[] startTime = start.split(":");
		helper.setStartH(Translate.stringToInt(startTime[0]));
		helper.setStartM(Translate.stringToInt(startTime[1]));
		helper.setStartS(Translate.stringToInt(startTime[2]));
		String[] endTime = end.split(":");
		helper.setEndH(Translate.stringToInt(endTime[0]));
		helper.setEndM(Translate.stringToInt(endTime[1]));
		helper.setEndS(Translate.stringToInt(endTime[2]));
		return helper;
	}

	/**
	 * @return the sum
	 */
	public int getSum() {
		return sum;
	}

	/**
	 * @param sum
	 *            the sum to set
	 */
	public void setSum(int sum) {
		this.sum = sum;
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
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the type
	 */
	public EHelper getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(EHelper type) {
		this.type = type;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the button
	 */
	public String getButton() {
		return button;
	}

	/**
	 * @param button
	 *            the button to set
	 */
	public void setButton(String button) {
		this.button = button;
	}

	public int getNpcid() {
		return npcid;
	}

	public void setNpcid(int npcid) {
		this.npcid = npcid;
	}

}
