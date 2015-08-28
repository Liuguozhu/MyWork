package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.question.Question;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class QuestionJson {

	/**
	 * 私有构造方法
	 */
	private QuestionJson() {

	}

	private static QuestionJson instance = new QuestionJson();
	private static final String QUESTION = "json/" + ServerManager.SERVER_RES + "/Question.json.txt";
	
	private Map<Integer, Question> questionMap = new HashMap<Integer, Question>(); // 题库
	private List<Integer> questionList = new ArrayList<Integer>(); // 题库
//	private Map<EQuestionType, List<Integer>> questionsMap = new HashMap<EQuestionType, List<Integer>>(); // 题库(分题型)
//	private ArrayListMultimap<EQuestionType, Integer> questionsMap = ArrayListMultimap.create(); // 题库(分题型)

	/**
	 * 获取AnswerJson对象
	 * 
	 * @return AnswerJson对象
	 */
	public static QuestionJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		questionMap.clear();
		questionList.clear();
//		questionsMap.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<Question> questions = JsonImporter.fileImporter(QUESTION, new TypeReference<List<Question>>() {
		});
		for (Question question : questions) {
			questionMap.put(question.getId(), question);
			questionList.add(question.getId());

//			questionsMap.put(question.getType(), question.getId());
//			if (questionsMap.get(question.getType()) != null) {
//				questionsMap.get(question.getType()).add(question.getId());
//			} else {
//				List<Integer> list = new ArrayList<Integer>();
//				list.add(question.getId());
//				questionsMap.put(question.getType(), list);
//			}
		}
		long end = System.currentTimeMillis();
		System.out.println("每日答题脚本加载耗时：" + (end - start));
	}

	public Map<Integer, Question> getQuestionMap() {
		return questionMap;
	}

	public List<Integer> getQuestionList() {
		return questionList;
	}

}
