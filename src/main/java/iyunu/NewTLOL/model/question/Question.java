package iyunu.NewTLOL.model.question;

import iyunu.NewTLOL.enumeration.EQuestionType;

/**
 * @function 每日答题实体类
 * @author LuoSR
 * @date 2014年3月18日
 */
public class Question {
	/** 问题编号 **/
	private int id;
	private EQuestionType type;
	/** 问题 **/
	private String question;
	/** 答案1 **/
	private String answerOne;
	/** 答案2 **/
	private String answerTwo;
	/** 答案3 **/
	private String answerThree;
	/** 答案4 **/
	private String answerFour;
	/** 正确答案 **/
	private int answerTure;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EQuestionType getType() {
		return type;
	}

	public void setType(EQuestionType type) {
		this.type = type;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerOne() {
		return answerOne;
	}

	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}

	public String getAnswerTwo() {
		return answerTwo;
	}

	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}

	public String getAnswerThree() {
		return answerThree;
	}

	public void setAnswerThree(String answerThree) {
		this.answerThree = answerThree;
	}

	public String getAnswerFour() {
		return answerFour;
	}

	public void setAnswerFour(String answerFour) {
		this.answerFour = answerFour;
	}

	public int getAnswerTure() {
		return answerTure;
	}

	public void setAnswerTure(int answerTure) {
		this.answerTure = answerTure;
	}

}
