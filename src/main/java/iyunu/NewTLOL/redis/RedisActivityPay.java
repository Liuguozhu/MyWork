package iyunu.NewTLOL.redis;

import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * 充值活动
 * 
 * @author SunHonglei
 * 
 */
public class RedisActivityPay {

	private StringRedisTemplate redisTemplate;

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static String key_paySingles = "paySingles[" + ServerManager.instance().getServer() + "]";
	private static String key_paySingleAwards = "paySingleAwards[" + ServerManager.instance().getServer() + "]";

	private static String key_payAccumulateWeeks = "payAccumulateWeeks[" + ServerManager.instance().getServer() + "]";
	private static String key_payAccumulateWeekAwards = "payAccumulateWeekAwards[" + ServerManager.instance().getServer() + "]";

	private static String key_payEveryday = "payEveryday[" + ServerManager.instance().getServer() + "]";
	private static String key_payEverydayAwards = "payEverydayAwards[" + ServerManager.instance().getServer() + "]";

	private static String key_activityNewState = "activityNewState[" + ServerManager.instance().getServer() + "]";
	private static String key_activityNewRoles = "activityNewRoles[" + ServerManager.instance().getServer() + "]";

	public void save() {

		List<String> keys = new ArrayList<>();
		keys.add(key_paySingles);
		keys.add(key_paySingleAwards);
		keys.add(key_payAccumulateWeeks);
		keys.add(key_payAccumulateWeekAwards);
		keys.add(key_payEveryday);
		keys.add(key_payEverydayAwards);
		keys.add(key_activityNewState);
		keys.add(key_activityNewRoles);
		redisTemplate.delete(keys);

		// ======单笔充值======
		SerializeWriter paySingles_Out = new SerializeWriter();
		JSONSerializer paySingles_Serializer = new JSONSerializer(paySingles_Out);
		paySingles_Serializer.write(ActivityPayManager.paySingles);
		redisTemplate.opsForValue().set(key_paySingles, paySingles_Serializer.toString());

		SerializeWriter paySingleAwards_Out = new SerializeWriter();
		JSONSerializer paySingleAwards_Serializer = new JSONSerializer(paySingleAwards_Out);
		paySingleAwards_Serializer.write(ActivityPayManager.paySingleAwards);
		redisTemplate.opsForValue().set(key_paySingleAwards, paySingleAwards_Serializer.toString());

		// ======每周累积充值======
		SerializeWriter payAccumulateWeeks_Out = new SerializeWriter();
		JSONSerializer payAccumulateWeeks_Serializer = new JSONSerializer(payAccumulateWeeks_Out);
		payAccumulateWeeks_Serializer.write(ActivityPayManager.payAccumulateWeeks);
		redisTemplate.opsForValue().set(key_payAccumulateWeeks, payAccumulateWeeks_Serializer.toString());

		SerializeWriter payAccumulateWeekAwards_Out = new SerializeWriter();
		JSONSerializer payAccumulateWeekAwards_Serializer = new JSONSerializer(payAccumulateWeekAwards_Out);
		payAccumulateWeekAwards_Serializer.write(ActivityPayManager.payAccumulateWeekAwards);
		redisTemplate.opsForValue().set(key_payAccumulateWeekAwards, payAccumulateWeekAwards_Serializer.toString());

		// ======每日充值优惠======
		SerializeWriter payEveryday_Out = new SerializeWriter();
		JSONSerializer payEveryday_Serializer = new JSONSerializer(payEveryday_Out);
		payEveryday_Serializer.write(ActivityPayManager.payEveryday);
		redisTemplate.opsForValue().set(key_payEveryday, payEveryday_Serializer.toString());

		SerializeWriter payEverydayAwards_Out = new SerializeWriter();
		JSONSerializer payEverydayAwards_Serializer = new JSONSerializer(payEverydayAwards_Out);
		payEverydayAwards_Serializer.write(ActivityPayManager.payEverydayAwards);
		redisTemplate.opsForValue().set(key_payEverydayAwards, payEverydayAwards_Serializer.toString());

		// ======新服活动 ======
		SerializeWriter activityNewState_Out = new SerializeWriter();
		JSONSerializer activityNewState_Out_Serializer = new JSONSerializer(activityNewState_Out);
		activityNewState_Out_Serializer.write(ActivityPayManager.activityNewState);
		redisTemplate.opsForValue().set(key_activityNewState, activityNewState_Out_Serializer.toString());

		SerializeWriter activityNewRoles_Out = new SerializeWriter();
		JSONSerializer activityNewRoles_Serializer = new JSONSerializer(activityNewRoles_Out);
		activityNewRoles_Serializer.write(ActivityPayManager.activityNewRoles);
		redisTemplate.opsForValue().set(key_activityNewRoles, activityNewRoles_Serializer.toString());
	}

	public void query() {
		// ======单笔充值======
		String paySinglesString = redisTemplate.opsForValue().get(key_paySingles);
		HashMap<Long, Integer> paySingles = JsonTool.decode(paySinglesString, new TypeReference<HashMap<Long, Integer>>() {
		});
		if (paySingles != null) {
			ActivityPayManager.paySingles = paySingles;
		}

		String paySingleAwardsString = redisTemplate.opsForValue().get(key_paySingleAwards);
		Map<Long, Map<Integer, Integer>> paySingleAwards = JsonTool.decode(paySingleAwardsString, new TypeReference<HashMap<Long, Map<Integer, Integer>>>() {
		});
		if (paySingleAwards != null) {
			ActivityPayManager.paySingleAwards = paySingleAwards;
		}

		// ======每周累积充值======
		String payAccumulateWeeksString = redisTemplate.opsForValue().get(key_payAccumulateWeeks);
		HashMap<Long, Integer> payAccumulateWeeks = JsonTool.decode(payAccumulateWeeksString, new TypeReference<HashMap<Long, Integer>>() {
		});
		if (paySingles != null) {
			ActivityPayManager.payAccumulateWeeks = payAccumulateWeeks;
		}

		String payAccumulateWeekAwardsString = redisTemplate.opsForValue().get(key_payAccumulateWeekAwards);
		Map<Long, Map<Integer, Integer>> payAccumulateWeekAwards = JsonTool.decode(payAccumulateWeekAwardsString, new TypeReference<HashMap<Long, Map<Integer, Integer>>>() {
		});
		if (paySingleAwards != null) {
			ActivityPayManager.payAccumulateWeekAwards = payAccumulateWeekAwards;
		}

		// ======每日充值优惠======
		String payEverydayString = redisTemplate.opsForValue().get(key_payEveryday);
		HashMap<Long, Integer> payEveryday = JsonTool.decode(payEverydayString, new TypeReference<HashMap<Long, Integer>>() {
		});
		if (payEveryday != null) {
			ActivityPayManager.payEveryday = payEveryday;
		}

		String payEverydayAwardsString = redisTemplate.opsForValue().get(key_payEverydayAwards);
		Map<Long, Map<Integer, Integer>> payEverydayAwards = JsonTool.decode(payEverydayAwardsString, new TypeReference<HashMap<Long, Map<Integer, Integer>>>() {
		});
		if (payEverydayAwards != null) {
			ActivityPayManager.payEverydayAwards = payEverydayAwards;
		}
		
		// ======新服活动 ======
		String activityNewStateString = redisTemplate.opsForValue().get(key_activityNewState);
		HashMap<Integer, Map<Long, Map<Integer, Integer>>> activityNewState = JsonTool.decode(activityNewStateString, new TypeReference<HashMap<Integer, Map<Long, Map<Integer, Integer>>>>() {
		});
		if (activityNewState != null) {
			ActivityPayManager.activityNewState = activityNewState;
		}

		String activityNewRolesString = redisTemplate.opsForValue().get(key_activityNewRoles);
		Map<Integer, Map<Integer, List<Long>>> activityNewRoles = JsonTool.decode(activityNewRolesString, new TypeReference<HashMap<Integer, Map<Integer, List<Long>>>>() {
		});
		if (activityNewRoles != null) {
			ActivityPayManager.activityNewRoles = activityNewRoles;
		}
	}
}
