package iyunu.NewTLOL.server.billboard;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;

public class BillboardServer {

	public static int worldLevel() {
		ArrayList<RoleCard> tops = RankEvent.ExpEvent.getHandler().getTopCards();
		int sum = 0;
		int size = Util.matchMin(10, tops.size());
		for (int i = 0; i < size; i++) {
			sum += tops.get(i).getLevel();
		}
		return (int) (sum * 1f / size);
	}
}
