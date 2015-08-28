package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;

/**
 * 排行榜
 * 
 * @author SunHonglei
 * 
 */
public class RankingManager {

	@SuppressWarnings("unchecked")
	public static void printRanking() {

		// 等级榜
		ArrayList<RoleCard> levelRankingList = (ArrayList<RoleCard>) RankEvent.ExpEvent.getHandler().getTopCards().clone();
		for (int i = 0; i < levelRankingList.size(); i++) {
			RoleCard roleCard = levelRankingList.get(i);
			LogManager.levelRankingLog(i, roleCard);
		}

		// 战力榜
		ArrayList<RoleCard> powerRankingList = (ArrayList<RoleCard>) RankEvent.PowerEvent.getHandler().getTopCards().clone();
		for (int i = 0; i < powerRankingList.size(); i++) {
			RoleCard roleCard = powerRankingList.get(i);
			LogManager.powerRankingLog(i, roleCard);
		}

		// 洞天榜
		ArrayList<RoleCard> qctRankingList = (ArrayList<RoleCard>) RankEvent.QctEvent.getHandler().getTopCards().clone();
		for (int i = 0; i < qctRankingList.size(); i++) {
			RoleCard roleCard = qctRankingList.get(i);
			LogManager.qctRankingLog(i, roleCard);
		}
	}

}
