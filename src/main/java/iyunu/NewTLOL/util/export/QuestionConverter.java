package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.question.Question;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class QuestionConverter {

	/**
	 * @function 题库资源转换器
	 * @author LuoSR
	 * @date 2014年3月18日
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/题库.xlsx";
		
		String question = "src/main/resources/json/" + serverRes + "/Question.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, question, Question.class, "每日答题");
	}
}