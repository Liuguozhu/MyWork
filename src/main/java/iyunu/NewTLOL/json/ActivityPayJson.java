package iyunu.NewTLOL.json;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.model.payActivity.res.PayActivityInfoRes;
import iyunu.NewTLOL.model.payActivity.res.PayActivityListRes;
import iyunu.NewTLOL.processor.ActivityNewProcessorCenter;
import iyunu.NewTLOL.service.iface.payActivity.PayActivityService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityPayJson {

	/**
	 * 私有构造方法
	 */
	private ActivityPayJson() {

	}

	private static final ActivityPayJson instance = new ActivityPayJson();

	public static ActivityPayJson instance() {
		return instance;
	}

	public static long START;
	public static long END;
	private LinkedHashMap<Integer, PayActivityList> payActivities = new LinkedHashMap<Integer, PayActivityList>();
	private LinkedHashMap<Integer, PayActivityInfo> paySingles = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> payAccumulateWeek = new LinkedHashMap<Integer, PayActivityInfo>();

	private LinkedHashMap<Integer, PayActivityInfo> 冲击狂人 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 最佳搭档 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 装备铸造 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 江湖等级榜 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 斗破洞天 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 绝世神兵 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 最强战力 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 副本达人 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 每日挑战 = new LinkedHashMap<Integer, PayActivityInfo>();
	private LinkedHashMap<Integer, PayActivityInfo> 铸造大匠 = new LinkedHashMap<Integer, PayActivityInfo>();

	public void clear() {
		payActivities.clear();
		paySingles.clear();
		冲击狂人.clear();
		最佳搭档.clear();
		装备铸造.clear();
		江湖等级榜.clear();
		斗破洞天.clear();
		绝世神兵.clear();
		最强战力.clear();
		副本达人.clear();
		每日挑战.clear();
		铸造大匠.clear();
		START = 0;
		END = 0;
	}

	public void init() {
		long start = System.currentTimeMillis();
		clear();

		PayActivityService payActivityService = Spring.instance().getBean("payActivityService", PayActivityService.class);

		List<PayActivityListRes> payActivityResList = payActivityService.queryPayActivityList();
		for (PayActivityListRes payActivityRes : payActivityResList) {
			PayActivityList payActivityList = payActivityRes.toPayActivity();
			payActivities.put(payActivityRes.getType(), payActivityList);

			if (payActivityRes.getType() >= 3) {
				ActivityNewProcessorCenter activityNewProcessorCenter = new ActivityNewProcessorCenter(payActivityList);
				activityNewProcessorCenter.init();
			}

			if (ActivityPayJson.START == 0) {
				ActivityPayJson.START = payActivityList.getStart();
			} else if (start > payActivityList.getStart()) {
				ActivityPayJson.START = payActivityList.getStart();
			}

			if (ActivityPayJson.END < payActivityList.getEnd()) {
				ActivityPayJson.END = payActivityList.getEnd();
			}
		}

		List<PayActivityInfoRes> payActivityInfoResList = payActivityService.queryPayActivityInfo();
		for (PayActivityInfoRes activityInfoRes : payActivityInfoResList) {
			switch (activityInfoRes.getType()) {
			case 1:
				paySingles.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 2:
				payAccumulateWeek.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 3:
				冲击狂人.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 4:
				最佳搭档.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 5:
				装备铸造.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 6:
				江湖等级榜.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 7:
				斗破洞天.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 8:
				绝世神兵.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 9:
				最强战力.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 10:
				副本达人.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 11:
				每日挑战.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			case 12:
				铸造大匠.put(activityInfoRes.getIndex(), activityInfoRes.toPaySingle());
				break;
			default:
				break;
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("充值活动加载耗时：" + (end - start));
	}

	/**
	 * @return the paySingles
	 */
	public LinkedHashMap<Integer, PayActivityInfo> getPaySingles() {
		return paySingles;
	}

	/**
	 * @return the payActivities
	 */
	public HashMap<Integer, PayActivityList> getPayActivities() {
		return payActivities;
	}

	/**
	 * @return the payAccumulateWeek
	 */
	public LinkedHashMap<Integer, PayActivityInfo> getPayAccumulateWeek() {
		return payAccumulateWeek;
	}

	/**
	 * @return the 冲击狂人
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get冲击狂人() {
		return 冲击狂人;
	}

	/**
	 * @return the 最佳搭档
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get最佳搭档() {
		return 最佳搭档;
	}

	/**
	 * @return the 装备铸造
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get装备铸造() {
		return 装备铸造;
	}

	/**
	 * @return the 江湖等级榜
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get江湖等级榜() {
		return 江湖等级榜;
	}

	/**
	 * @return the 斗破洞天
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get斗破洞天() {
		return 斗破洞天;
	}

	/**
	 * @return the 绝世神兵
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get绝世神兵() {
		return 绝世神兵;
	}

	/**
	 * @return the 最强战力
	 */
	public LinkedHashMap<Integer, PayActivityInfo> get最强战力() {
		return 最强战力;
	}

	public LinkedHashMap<Integer, PayActivityInfo> get副本达人() {
		return 副本达人;
	}

	public LinkedHashMap<Integer, PayActivityInfo> get每日挑战() {
		return 每日挑战;
	}

	public LinkedHashMap<Integer, PayActivityInfo> get铸造大匠() {
		return 铸造大匠;
	}

}