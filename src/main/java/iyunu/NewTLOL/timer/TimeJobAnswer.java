package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.processor.activity.AnswerProcessorCenter;

/**
 * 每日答题
 * 
 * @author SunHonglei
 * 
 */
public class TimeJobAnswer {

	public void excute() {
		AnswerProcessorCenter answerProcessorCenter = new AnswerProcessorCenter();
		answerProcessorCenter.start();
	}
}
