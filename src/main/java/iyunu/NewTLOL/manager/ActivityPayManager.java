package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityInfo;
import iyunu.NewTLOL.model.payActivity.instance.PayActivityList;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.role.RoleCard;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值活动
 * 
 * @author SunHonglei
 * 
 */
public class ActivityPayManager {

	public static Map<Long, Integer> payEveryday = new HashMap<Long, Integer>(); // 角色编号，每日累积充值
	public static Map<Long, Map<Integer, Integer>> payEverydayAwards = new HashMap<Long, Map<Integer, Integer>>(); // <角色编号，<每日充值奖励索引，状态（0.不可领取，1.可以领取，2.已领取）>>

	public static Map<Long, Integer> paySingles = new HashMap<Long, Integer>(); // 角色编号，单笔充值最高额度
	public static Map<Long, Map<Integer, Integer>> paySingleAwards = new HashMap<Long, Map<Integer, Integer>>(); // <角色编号，<单笔充值奖励索引，状态（0.不可领取，1.可以领取，2.已领取）>>

	public static Map<Long, Integer> payAccumulateWeeks = new HashMap<Long, Integer>(); // 角色编号，每周累积充值
	public static Map<Long, Map<Integer, Integer>> payAccumulateWeekAwards = new HashMap<Long, Map<Integer, Integer>>(); // <角色编号，<每周累积充值奖励索引，状态（0.不可领取，1.可以领取，2.已领取）>>

	public static Map<Integer, Map<Long, Map<Integer, Integer>>> activityNewState = new HashMap<Integer, Map<Long, Map<Integer, Integer>>>(); // 新服活动（Integer为活动类型，Long为角色编号，Integer为索引，Integer为领取状态）
	public static Map<Integer, Map<Integer, List<Long>>> activityNewRoles = new HashMap<Integer, Map<Integer, List<Long>>>(); // 活动达到条件成员列表（Integer为活动类型，Integer为索引，Long为角色编号）

	/**
	 * 检查活动状态
	 * 
	 * @param roleId
	 *            角色编号
	 * @param type
	 *            类型
	 * @param index
	 *            索引
	 * @return 状态（0.不可领取，1.可以领取，2.已领取）
	 */
	public static int checkState(long roleId, int type, int index) {
		if (type == 1) {
			if (paySingleAwards.containsKey(roleId)) {
				Map<Integer, Integer> map = paySingleAwards.get(roleId);
				if (map.containsKey(index)) {
					return map.get(index);
				}
			}
		} else if (type == 2) {
			if (payAccumulateWeekAwards.containsKey(roleId)) {
				Map<Integer, Integer> map = payAccumulateWeekAwards.get(roleId);
				if (map.containsKey(index)) {
					return map.get(index);
				}
			}
		} else {
			if (activityNewState.containsKey(type)) {
				Map<Long, Map<Integer, Integer>> roleMap = activityNewState.get(type);
				if (roleMap.containsKey(roleId)) {
					Map<Integer, Integer> indexMap = roleMap.get(roleId);
					if (indexMap.containsKey(index)) {
						return indexMap.get(index);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 修改活动状态
	 * 
	 * @param roleId
	 *            角色编号
	 * @param type
	 *            类型
	 * @param index
	 *            活动索引
	 */
	public static void changeState(long roleId, int type, int index) {
		if (type == 1) {
			if (paySingleAwards.containsKey(roleId)) {
				paySingleAwards.get(roleId).put(index, 2);
			}
		} else if (type == 2) {
			if (payAccumulateWeekAwards.containsKey(roleId)) {
				payAccumulateWeekAwards.get(roleId).put(index, 2);
			}
		} else {
			if (activityNewState.containsKey(type)) {
				Map<Long, Map<Integer, Integer>> roleMap = activityNewState.get(type);
				if (roleMap.containsKey(roleId)) {
					Map<Integer, Integer> indexMap = roleMap.get(roleId);
					if (indexMap.containsKey(index)) {
						indexMap.put(index, 2);
					}
				}
			}
		}
	}

	/**
	 * @function 保存活动状态
	 * @author LuoSR
	 * @param type
	 *            活动类型
	 * @param index
	 *            活动索引
	 * @param roleId
	 *            角色编号
	 * @date 2014年9月20日
	 */
	public static void saveActivityNewState(int type, int index, Long roleId) {
		if (activityNewState.containsKey(type)) {
			Map<Long, Map<Integer, Integer>> roleMap = activityNewState.get(type);
			if (roleMap.containsKey(roleId)) {
				Map<Integer, Integer> indexMap = roleMap.get(roleId);
				if (!indexMap.containsKey(index)) {
					indexMap.put(index, 1);
				}
			} else {
				Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
				indexMap.put(index, 1);
				roleMap.put(roleId, indexMap);
				activityNewState.put(type, roleMap);
			}
		} else {
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			indexMap.put(index, 1);
			Map<Long, Map<Integer, Integer>> roleMap = new HashMap<Long, Map<Integer, Integer>>();
			roleMap.put(roleId, indexMap);
			activityNewState.put(type, roleMap);
		}
	}

	/**
	 * @function 根据伙伴颜色保存玩家编号
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param partner
	 *            伙伴对象
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByPartnerColor(Role role, Partner partner) {
		long now = System.currentTimeMillis();
		PayActivityList activityPayList = ActivityPayJson.instance().getPayActivities().get(4);
		if (activityPayList != null && activityPayList.getStart() < now && now < activityPayList.getFinish()) {

			PayActivityInfo payActivityInfo = ActivityPayJson.instance().get最佳搭档().get(1);
			if (payActivityInfo != null) {

				if (partner.getColor().ordinal() >= payActivityInfo.getValue()) {
					if (activityNewRoles.containsKey(4)) {
						Map<Integer, List<Long>> indexMap = activityNewRoles.get(4);
						if (indexMap.containsKey(1)) {
							indexMap.get(1).add(role.getId());
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(role.getId());
							indexMap.put(1, list);
						}
					} else {
						List<Long> list = new ArrayList<Long>();
						list.add(role.getId());
						Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
						indexMap.put(1, list);
						activityNewRoles.put(4, indexMap);
					}
				}
			}
		}
	}

	/**
	 * @function 根据装备升星保存玩家编号
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param stone
	 *            伙伴对象
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByEquipStart(Role role, Equip equip) {
		long now = System.currentTimeMillis();
		PayActivityList activityPayList = ActivityPayJson.instance().getPayActivities().get(5);
		if (activityPayList != null && activityPayList.getStart() < now && now < activityPayList.getFinish()) {
			PayActivityInfo payActivityInfo = ActivityPayJson.instance().get装备铸造().get(1);
			if (payActivityInfo != null) {
				if (equip.getStar() >= payActivityInfo.getValue()) {
					if (activityNewRoles.containsKey(5)) {
						Map<Integer, List<Long>> indexMap = activityNewRoles.get(5);
						if (indexMap.containsKey(1)) {
							indexMap.get(1).add(role.getId());
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(role.getId());
							indexMap.put(1, list);
						}
					} else {
						List<Long> list = new ArrayList<Long>();
						list.add(role.getId());
						Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
						indexMap.put(1, list);
						activityNewRoles.put(5, indexMap);
					}
				}
			}
		}
	}

	/**
	 * @function 根据等级榜保存玩家编号
	 * @author LuoSR
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByLevelRanking() {

		List<RoleCard> topCards = RankEvent.ExpEvent.getHandler().getTopCards();
		int size = topCards.size() > 20 ? 20 : topCards.size();
		int i = 0;
		for (PayActivityInfo activityInfo : ActivityPayJson.instance().get江湖等级榜().values()) {
			for (; i < size; i++) {
				RoleCard roleCard = topCards.get(i);
				if (i < activityInfo.getValue()) {
					// 保存
					if (activityNewRoles.containsKey(activityInfo.getType())) {
						Map<Integer, List<Long>> indexMap = activityNewRoles.get(activityInfo.getType());
						if (indexMap.containsKey(activityInfo.getIndex())) {
							indexMap.get(activityInfo.getIndex()).add(roleCard.getId());
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(roleCard.getId());
							indexMap.put(activityInfo.getIndex(), list);
						}
					} else {
						List<Long> list = new ArrayList<Long>();
						list.add(roleCard.getId());
						Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
						indexMap.put(activityInfo.getIndex(), list);
						activityNewRoles.put(activityInfo.getType(), indexMap);
					}
				} else {
					break;
				}
			}
		}
	}

	/**
	 * @function 根据洞天榜保存玩家编号
	 * @author LuoSR
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByDongTianRanking() {
		List<RoleCard> topCards = RankEvent.QctEvent.getHandler().getTopCards();
		int size = topCards.size() > 3 ? 3 : topCards.size();
		int i = 0;
		for (PayActivityInfo activityInfo : ActivityPayJson.instance().get斗破洞天().values()) {
			for (; i < size; i++) {
				RoleCard roleCard = topCards.get(i);
				if (i < activityInfo.getValue()) {
					// 保存
					if (activityNewRoles.containsKey(activityInfo.getType())) {
						Map<Integer, List<Long>> indexMap = activityNewRoles.get(activityInfo.getType());
						if (indexMap.containsKey(activityInfo.getIndex())) {
							indexMap.get(activityInfo.getIndex()).add(roleCard.getId());
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(roleCard.getId());
							indexMap.put(activityInfo.getIndex(), list);
						}
					} else {
						List<Long> list = new ArrayList<Long>();
						list.add(roleCard.getId());
						Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
						indexMap.put(activityInfo.getIndex(), list);
						activityNewRoles.put(activityInfo.getType(), indexMap);
					}
				} else {
					break;
				}
			}
		}
	}

	/**
	 * @function 根据神兵等级保存玩家编号
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param stone
	 *            伙伴对象
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByShenbing(Role role, Equip equip) {
		long now = System.currentTimeMillis();
		PayActivityList activityPayList = ActivityPayJson.instance().getPayActivities().get(8);
		if (activityPayList != null && activityPayList.getStart() < now && now < activityPayList.getFinish()) {

			for (PayActivityInfo payActivityInfo : ActivityPayJson.instance().get绝世神兵().values()) {
				if (payActivityInfo != null) {
					if (equip.getShowLevel() >= payActivityInfo.getValue()) {
						if (activityNewRoles.containsKey(payActivityInfo.getType())) {
							Map<Integer, List<Long>> indexMap = activityNewRoles.get(payActivityInfo.getType());
							if (indexMap.containsKey(payActivityInfo.getIndex())) {
								indexMap.get(payActivityInfo.getIndex()).add(role.getId());
							} else {
								List<Long> list = new ArrayList<Long>();
								list.add(role.getId());
								indexMap.put(payActivityInfo.getIndex(), list);
							}
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(role.getId());
							Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
							indexMap.put(payActivityInfo.getIndex(), list);
							activityNewRoles.put(payActivityInfo.getType(), indexMap);
						}
					}
				}
			}
		}
	}

	/**
	 * @function 根据战力榜保存玩家编号
	 * @author LuoSR
	 * @date 2014年9月20日
	 */
	public static void saveRoleIdByPowerRanking() {

		List<RoleCard> topCards = RankEvent.PowerEvent.getHandler().getTopCards();
		int size = topCards.size() > 20 ? 20 : topCards.size();
		int i = 0;
		for (PayActivityInfo activityInfo : ActivityPayJson.instance().get最强战力().values()) {
			for (; i < size; i++) {
				RoleCard roleCard = topCards.get(i);
				if (i < activityInfo.getValue()) {
					// 保存
					if (activityNewRoles.containsKey(activityInfo.getType())) {
						Map<Integer, List<Long>> indexMap = activityNewRoles.get(activityInfo.getType());
						if (indexMap.containsKey(activityInfo.getIndex())) {
							indexMap.get(activityInfo.getIndex()).add(roleCard.getId());
						} else {
							List<Long> list = new ArrayList<Long>();
							list.add(roleCard.getId());
							indexMap.put(activityInfo.getIndex(), list);
						}
					} else {
						List<Long> list = new ArrayList<Long>();
						list.add(roleCard.getId());
						Map<Integer, List<Long>> indexMap = new HashMap<Integer, List<Long>>();
						indexMap.put(activityInfo.getIndex(), list);
						activityNewRoles.put(activityInfo.getType(), indexMap);
					}
				} else {
					break;
				}
			}
		}
	}

	// ==============================================每周累积充值活动===================================================
	/**
	 * 每周累积充值记录重置
	 */
	public static void payAccumulateWeekReset() {
		payAccumulateWeeks.clear();
		payAccumulateWeekAwards.clear();
	}

	// ==============================================每周累积充值活动===================================================

	// ==============================================单笔充值活动===================================================
	/**
	 * 单笔充值记录重置
	 */
	public static void singlePayReset() {
		payEveryday.clear();
		payEverydayAwards.clear();
		paySingles.clear();
		paySingleAwards.clear();
	}

	// ==============================================单笔充值活动===================================================

	/**
	 * 充值记录
	 * 
	 * @param roleId
	 *            角色编号
	 * @param rmb
	 *            充值金额（元）
	 */
	public static void payRecord(long roleId, int rmb) {
		long now = System.currentTimeMillis();

		// 单笔充值
		PayActivityList paySingleList = ActivityPayJson.instance().getPayActivities().get(1);
		if (paySingleList != null && paySingleList.getStart() < now && now < paySingleList.getEnd()) {

			boolean isMore = false;
			if (paySingles.containsKey(roleId)) {
				if (paySingles.get(roleId) < rmb) {
					isMore = true;
				}
			} else {
				isMore = true;
			}

			if (isMore) {
				if (paySingleAwards.containsKey(roleId)) {
					Map<Integer, Integer> map = paySingleAwards.get(roleId);
					for (PayActivityInfo activityInfo : ActivityPayJson.instance().getPaySingles().values()) {
						if (map.get(activityInfo.getIndex()) == 0 && activityInfo.getValue() <= rmb) {
							map.put(activityInfo.getIndex(), 1);
						}
					}
				} else {
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();

					for (PayActivityInfo activityInfo : ActivityPayJson.instance().getPaySingles().values()) {
						if (activityInfo.getValue() <= rmb) {
							map.put(activityInfo.getIndex(), 1);
						} else {
							map.put(activityInfo.getIndex(), 0);
						}
					}
					paySingleAwards.put(roleId, map);
				}
			}

		}

		// 每周累积充值充值
		PayActivityList payAccumulateWeekList = ActivityPayJson.instance().getPayActivities().get(2);
		if (payAccumulateWeekList != null && payAccumulateWeekList.getStart() < now && now < payAccumulateWeekList.getEnd()) {

			int total = 0;
			if (payAccumulateWeeks.containsKey(roleId)) {
				total = payAccumulateWeeks.get(roleId) + rmb;
			} else {
				total = rmb;
			}
			payAccumulateWeeks.put(roleId, total);

			if (payAccumulateWeekAwards.containsKey(roleId)) {
				Map<Integer, Integer> map = payAccumulateWeekAwards.get(roleId);
				for (PayActivityInfo activityInfo : ActivityPayJson.instance().getPayAccumulateWeek().values()) {
					if (map.get(activityInfo.getIndex()) == 0 && activityInfo.getValue() <= total) {
						map.put(activityInfo.getIndex(), 1);
					}
				}
			} else {
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				for (PayActivityInfo activityInfo : ActivityPayJson.instance().getPayAccumulateWeek().values()) {
					if (activityInfo.getValue() <= total) {
						map.put(activityInfo.getIndex(), 1);
					} else {
						map.put(activityInfo.getIndex(), 0);
					}
				}
				payAccumulateWeekAwards.put(roleId, map);
			}

		}
		try {
			// 每日累积充值
			if (ActivityJson.PAY_EVERYDAY != null && ActivityJson.PAY_EVERYDAY.getStartTime() < now && now < ActivityJson.PAY_EVERYDAY.getEndTime()) {
				int num = rmb / ActivityJson.PAY_EVERYDAY.getSingleValue(); // 单笔充值奖励数量

				Map<Item, Integer> itemIds = new HashMap<Item, Integer>();

				int sum = ActivityJson.PAY_EVERYDAY.getAward().getNum() * num;
				if (ActivityJson.PAY_EVERYDAY.getAward().getNum() * num > 0) {
					Item item = ItemJson.instance().getItem(ActivityJson.PAY_EVERYDAY.getAward().getItemId());
					itemIds.put(item, sum);
				}

				int total = 0;
				if (payEveryday.containsKey(roleId)) {
					total = payEveryday.get(roleId) + rmb;
				} else {
					total = rmb;
				}
				payEveryday.put(roleId, total);

				if (payEverydayAwards.containsKey(roleId)) {
					Map<Integer, Integer> map = payEverydayAwards.get(roleId);

					for (int i = 0; i < ActivityJson.PAY_EVERYDAY.getValues().size(); i++) {
						int value = ActivityJson.PAY_EVERYDAY.getValues().get(i);
						if (map.get(value) == 0 && value <= total) {
							map.put(value, 1);

							MonsterDropItem dropItem = ActivityJson.PAY_EVERYDAY.getReward().get(i);
							Item item = ItemJson.instance().getItem(dropItem.getItemId());
							itemIds.put(item, dropItem.getNum());

						}
					}
				} else {
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();
					for (int i = 0; i < ActivityJson.PAY_EVERYDAY.getValues().size(); i++) {
						int value = ActivityJson.PAY_EVERYDAY.getValues().get(i);
						if (value <= total) {
							map.put(value, 1);

							MonsterDropItem dropItem = ActivityJson.PAY_EVERYDAY.getReward().get(i);
							Item item = ItemJson.instance().getItem(dropItem.getItemId());
							itemIds.put(item, dropItem.getNum());
						} else {
							map.put(value, 0);
						}
					}
					payEverydayAwards.put(roleId, map);
				}
				if (itemIds.size() > 0) {
					MailServer.send(roleId, "每日充值福利", "尊敬的龙傲天下用户，您的每日充值福利礼品已发放，请注意查收。", itemIds, 0, 0, 0, 0, null);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
