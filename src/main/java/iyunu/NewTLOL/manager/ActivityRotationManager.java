package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.activity.ActivityRotation;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 转盘活动
 * 
 * @author SunHonglei
 * 
 */
public class ActivityRotationManager {

	private static List<ActivityRotation> activityRotations = new ArrayList<ActivityRotation>();

	public static void addRotation(ActivityRotation activityRotation) {
		activityRotations.add(activityRotation);
	}

	/**
	 * 获取物品
	 * 
	 * @return 物品
	 */
	public static ActivityRotation random() {
		int probabilit = 0;
		int finalRate = Util.getRandom(10000);
		for (ActivityRotation activityRotation : activityRotations) {
			probabilit += activityRotation.getProbability();
			if (finalRate < probabilit) {
				return activityRotation;
			}
		}
		return null;
	}

	public static void clearActivityRotations() {
		activityRotations.clear();
	}
}
