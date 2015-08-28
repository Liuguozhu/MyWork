package iyunu.NewTLOL.net.protocol.question;

import iyunu.NewTLOL.json.QuestionJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.question.Question;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.util.Util;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 每日答题
 * @author LuoSR
 * @date 2014年3月17日
 */
public class ReqQuestion extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		if (!ActivityManager.ANSWER_STATE) {
			result = 1;
			reason = "现在不是答题时间";
			return;
		}

		if (online.getDailyAnswerNum() <= 0) {
			result = 1;
			reason = "你已完成本日答题";
			return;
		}

		if (online.getLevel() < 15) {
			result = 1;
			reason = "15级开启";
			return;
		}

		if (online.getDailyAnswerState() == 1 && online.getDailyAnswerQuestions().size() == 0) {
			result = 1;
			reason = "您已完成本日的答题";
			return;
		}

		if (online.getDailyAnswerState() == 0) { // 更新题目
			int size = QuestionJson.instance().getQuestionList().size();
			for (int i = 0; i < size; i++) {
				int index = Util.getRandom(QuestionJson.instance().getQuestionList().size());
				int answerId = QuestionJson.instance().getQuestionList().get(Integer.valueOf(index));
				if (!online.getDailyAnswerQuestions().contains(answerId)) {
					online.getDailyAnswerQuestions().add(answerId);
				}
				if (online.getDailyAnswerQuestions().size() >= 20) {
					break;
				}
			}

			// 设置答题状态（改为已答题）(存储)
			online.setDailyAnswerState(1);
			HelperServer.helper(online, EHelper.answer); // 小助手记录
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_reqQuestion");
			message.write("result", result);
			message.write("reason", reason);
			message.write("totleTureNum", online.getDailyAnswerTotleTrueNum());
			message.write("totleGold", online.getDailyAnswerTotleGold());
			if (result == 0) {
				List<Integer> list = online.getDailyAnswerQuestions();
				for (Integer questionId : list) {
					LlpMessage questionInfo = message.write("questionInfoList");
					Question question = QuestionJson.instance().getQuestionMap().get(questionId);
					questionInfo.write("id", question.getId());
					questionInfo.write("type", question.getType().name());
					questionInfo.write("question", question.getQuestion());
					questionInfo.write("answerOne", question.getAnswerOne());
					questionInfo.write("answerTwo", question.getAnswerTwo());
					questionInfo.write("answerThree", question.getAnswerThree());
					questionInfo.write("answerFour", question.getAnswerFour());
					questionInfo.write("answerTure", question.getAnswerTure());
				}
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
