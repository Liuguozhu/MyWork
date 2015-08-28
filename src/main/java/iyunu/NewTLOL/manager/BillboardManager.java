package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.billboard.level.LevelBoard;
//import iyunu.NewTLOL.model.billboard.power.PowerBoard;
import iyunu.NewTLOL.model.billboard.qct.QctBoard;
import iyunu.NewTLOL.util.log.LogManager;

public class BillboardManager {
	private static BillboardManager instance = new BillboardManager();
	public static final int MAX_NUM = 8;

	public static BillboardManager instance() {
		return instance;
	}

	private BillboardManager() {

	}

	public void load() {

		LevelBoard.INSTANCE.sort();
		// PowerBoard.INSTANCE.sort();
		QctBoard.INSTANCE.sort();
		LogManager.info("【排行榜】加载完成");

	}

}