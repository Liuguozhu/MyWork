package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.model.multiple.Multiple;
import iyunu.NewTLOL.service.iface.multiple.MultipleService;
import iyunu.NewTLOL.util.Time;

public class MultipleManager {

	public static long EXP_START_TIME;
	public static long EXP_END_TIME;
	public static int EXP_START_H;
	public static int EXP_END_H;
	public static int EXP_START_M;
	public static int EXP_END_M;
	public static int EXP_MULTIPLE;

	public static long GOLD_START_TIME;
	public static long GOLD_END_TIME;
	public static int GOLD_START_H;
	public static int GOLD_END_H;
	public static int GOLD_START_M;
	public static int GOLD_END_M;
	public static int GOLD_MULTIPLE;

	public static void init() {
		MultipleService multipleService = Spring.instance().getBean("multipleService", MultipleService.class);
		Multiple multiple = multipleService.query();

		if (multiple != null) {
			EXP_START_TIME = Time.dateToLong(multiple.getExpMulStart());
			EXP_END_TIME = Time.dateToLong(multiple.getExpMulEnd());
			EXP_START_H = multiple.getExpMulStartH();
			EXP_START_M = multiple.getExpMulStartM();
			EXP_END_H = multiple.getExpMulEndH();
			EXP_END_M = multiple.getExpMulEndM();
			EXP_MULTIPLE = multiple.getExpMul();

			GOLD_START_TIME = Time.dateToLong(multiple.getGoldMulStart());
			GOLD_END_TIME = Time.dateToLong(multiple.getGoldMulEnd());
			GOLD_START_H = multiple.getGoldMulStartH();
			GOLD_END_H = multiple.getGoldMulEndH();
			GOLD_START_M = multiple.getGoldMulStartM();
			GOLD_END_M = multiple.getGoldMulEndM();
			GOLD_MULTIPLE = multiple.getGoldMul();
		}
	}
}
