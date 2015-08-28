package iyunu.NewTLOL.net.protocol.question;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.json.QuestionJson;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.question.Question;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.question.QuestionServer;
import iyunu.NewTLOL.server.role.RoleServer;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 每日答题
 * @author LuoSR
 * @date 2014年3月17日
 */
public class Answer extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

	@Override
	public void handleReceived(LlpMessage msg) {
		cellsMap.clear();
		result = 0;
		reason = "回答正确";

		int id = msg.readInt("id");
		int submitAnswer = msg.readInt("submitAnswer");
		int type = msg.readInt("type");

		if (online.getDailyAnswerQuestions().size() == 0) {
			result = 3;
			reason = "您已完成本日的答题";
			return;
		}

		if (!online.getDailyAnswerQuestions().contains(id)) {
			result = 3;
			reason = "您已完成本日的答题";
			return;
		}

		if (type == 0) {
			// 从问题列表中删除该题
			online.getDailyAnswerQuestions().remove(Integer.valueOf(id));

			Question question = QuestionJson.instance().getQuestionMap().get(id);

			int exp = 0;
			int gold = 0;
			if (submitAnswer != question.getAnswerTure()) {
				online.setDailyAnswerContinuousTrueNum(0);
				exp = (int) (QuestionServer.countAnswerAwardExp(online) * 0.6);
				gold = (int) (QuestionServer.countAnswerAwardGold(online) * 0.6);
				result = 1;
				reason = "回答错误";
			} else {
				// 连续答对题目次数(不做存储)
				online.setDailyAnswerContinuousTrueNum(online.getDailyAnswerContinuousTrueNum() + 1);

				exp = QuestionServer.countAnswerAwardExp(online);
				gold = QuestionServer.countAnswerAwardGold(online);

				// 答对题目次数(存储)
				online.setDailyAnswerTotleTrueNum(online.getDailyAnswerTotleTrueNum() + 1);
			}

			int score = QuestionServer.countScore(online);
			// 回答加积分
			RoleServer.addScore(online, score);

			// 回答发奖
			RoleServer.addGold(online, gold, EGold.answer);
			RoleServer.addExp(online, exp, EExp.answer);

			// 答题获得金钱(存储)
			online.setDailyAnswerTotleGold(online.getDailyAnswerTotleGold() + gold);

		}

		if (type == 1) {
			// 扣除元宝
			if (!RoleServer.costMoney(online, 20, EMoney.answer)) {
				result = 2;
				reason = "元宝不足";
				return;
			}

			int sumGold = 0;
			int sumExp = 0;
			int sumScore = 0;
			// 连续答对题目次数(不做存储)
			for (int i = 1; i <= online.getDailyAnswerQuestions().size(); i++) {
				online.setDailyAnswerContinuousTrueNum(online.getDailyAnswerContinuousTrueNum() + 1);
				sumGold += QuestionServer.countAnswerAwardGold(online);
				sumExp += QuestionServer.countAnswerAwardExp(online);
				sumScore += QuestionServer.countScore(online);
			}
			// 回答发奖
			RoleServer.addGold(online, sumGold, EGold.answer);
			RoleServer.addExp(online, sumExp, EExp.answer);
			// 回答正确加积分
			RoleServer.addScore(online, sumScore);

			// 答对题目次数加(存储)
			online.setDailyAnswerTotleTrueNum(online.getDailyAnswerTotleTrueNum() + online.getDailyAnswerQuestions().size());

			// 答题获得金钱(存储)
			online.setDailyAnswerTotleGold(online.getDailyAnswerTotleGold() + sumGold);

			// 从问题列表中删除该题
			online.getDailyAnswerQuestions().clear();

		}

		if (type == 2) {
			// 扣除元宝
			if (!RoleServer.costMoney(online, 2, EMoney.answer)) {
				result = 2;
				reason = "元宝不足";
				return;
			}
			// 从问题列表中删除该题
			online.getDailyAnswerQuestions().remove(Integer.valueOf(id));

			// 连续答对题目次数(不做存储)
			online.setDailyAnswerContinuousTrueNum(online.getDailyAnswerContinuousTrueNum() + 1);

			int gold = QuestionServer.countAnswerAwardGold(online);
			int exp = QuestionServer.countAnswerAwardExp(online);
			int score = QuestionServer.countScore(online);
			// 回答发奖
			RoleServer.addGold(online, gold, EGold.answer);
			RoleServer.addExp(online, exp, EExp.answer);
			// 回答正确加积分
			RoleServer.addScore(online, score);

			// 答对题目次数(存储)
			online.setDailyAnswerTotleTrueNum(online.getDailyAnswerTotleTrueNum() + 1);
			// 答题获得金钱(存储)
			online.setDailyAnswerTotleGold(online.getDailyAnswerTotleGold() + gold);

		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_answer");
			message.write("result", result);
			message.write("reason", reason);
			message.write("totleTureNum", online.getDailyAnswerTotleTrueNum());
			message.write("totleGold", online.getDailyAnswerTotleGold());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
