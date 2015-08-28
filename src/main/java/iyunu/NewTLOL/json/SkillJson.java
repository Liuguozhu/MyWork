package iyunu.NewTLOL.json;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.skill.ESkillCategory;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.instance.PartnerSkill;
import iyunu.NewTLOL.model.skill.instance.RoleSkill;
import iyunu.NewTLOL.model.skill.res.PartnerSkillRes;
import iyunu.NewTLOL.model.skill.res.RoleSkillRes;
import iyunu.NewTLOL.util.json.JsonImporter;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public final class SkillJson {

	/**
	 * 私有构造方法
	 */
	private SkillJson() {

	}

	private static SkillJson instance = new SkillJson();
	private static final String SKILL_PARTNER = "json/" + ServerManager.SERVER_RES + "/SkillPartner.json.txt";
	private static final String SKILL_ROLE = "json/" + ServerManager.SERVER_RES + "/SkillRole.json.txt";
	private static final String BUFF_BATTLE = "json/" + ServerManager.SERVER_RES + "/BuffBattle.json.txt";

	private Map<Integer, PartnerSkill> parnterSkills = new HashMap<Integer, PartnerSkill>();
	private Map<ESkillCategory, PartnerSkill> parnterSkillsByCategory = new HashMap<ESkillCategory, PartnerSkill>();
	private Map<Integer, RoleSkill> roleSkills = new HashMap<Integer, RoleSkill>(); //
	private Map<Integer, BaseSkill> skills = new HashMap<Integer, BaseSkill>(); // 技能集合
	private ArrayListMultimap<Vocation, RoleSkill> roleDefultSkillMap = ArrayListMultimap.create();
	// private Map<Vocation, List<RoleSkill>> roleDefultSkillMap = new
	// HashMap<Vocation, List<RoleSkill>>(); //
	private Map<Integer, BattleBuff> battleBuffMap = new HashMap<Integer, BattleBuff>();

	/**
	 * 获取SkillBookJson对象
	 * 
	 * @return SkillBookJson对象
	 */
	public static SkillJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		parnterSkills.clear();
		roleSkills.clear();
		roleDefultSkillMap.clear();
		skills.clear();
		parnterSkillsByCategory.clear();
		battleBuffMap.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<PartnerSkillRes> partnerSkillList = JsonImporter.fileImporter(SKILL_PARTNER, new TypeReference<List<PartnerSkillRes>>() {
		});

		for (PartnerSkillRes skill : partnerSkillList) {
			parnterSkills.put(skill.getId(), skill.toPartnerSkill());
			skills.put(skill.getId(), skill.toPartnerSkill());
			parnterSkillsByCategory.put(skill.getCategory(), skill.toPartnerSkill());
		}

		List<RoleSkillRes> roleSkillList = JsonImporter.fileImporter(SKILL_ROLE, new TypeReference<List<RoleSkillRes>>() {
		});

		for (RoleSkillRes roleSkill : roleSkillList) {
			roleSkills.put(roleSkill.getId(), roleSkill.toRoleSkill());
			skills.put(roleSkill.getId(), roleSkill.toRoleSkill());
			if (roleSkill.getLevel() == 0) {
				roleDefultSkillMap.put(roleSkill.getVocation(), roleSkill.toRoleSkill());
			}
		}

		List<BattleBuff> buffBattleList = JsonImporter.fileImporter(BUFF_BATTLE, new TypeReference<List<BattleBuff>>() {
		});
		for (BattleBuff battleBuff : buffBattleList) {
			battleBuffMap.put(battleBuff.getId(), battleBuff);
		}

		long end = System.currentTimeMillis();
		System.out.println("技能脚本加载耗时：" + (end - start));
	}

	/**
	 * 根据技能编号获取伙伴技能
	 * 
	 * @param skillId
	 *            技能编号
	 * @return 伙伴技能对象
	 */
	public PartnerSkill getPartnerSkillById(int skillId) {
		if (parnterSkills.containsKey(skillId)) {
			return parnterSkills.get(skillId).copy();
		}
		return null;
	}

	/**
	 * 根据技能编号获取技能，若技能不存在，返回普通攻击技能
	 * 
	 * @param skillId
	 *            技能编号
	 * @return 技能
	 */
	public BaseSkill getSkillById(Integer skillId) {
		if (skills.containsKey(skillId)) {
			return skills.get(skillId);
		}
		return skills.get(0);
	}

	/**
	 * 根据技能编号获取角色技能
	 * 
	 * @param skillId
	 *            技能编号
	 * @return 角色技能对象
	 */
	public RoleSkill getRoleSkillById(int skillId) {
		if (roleSkills.containsKey(skillId)) {
			return roleSkills.get(skillId);
		}
		return null;
	}

	public List<RoleSkill> getRoleDefultSkills(Vocation vocation) {
		return roleDefultSkillMap.get(vocation);
	}

	/**
	 * 根据编号获取BUFF
	 * 
	 * @param buffId
	 *            编号
	 * @return BUFF对象
	 */
	public BattleBuff getBattleBuff(int buffId) {
		if (battleBuffMap.containsKey(buffId)) {
			return battleBuffMap.get(buffId).copy();
		}
		return null;
	}

	/**
	 * 根据编号获取BUFF
	 * 
	 * @param buffId
	 *            编号
	 * @return BUFF对象
	 */
	public BattleBuff getBattleBuff(int buffId, int value, int duration) {
		if (battleBuffMap.containsKey(buffId)) {
			return battleBuffMap.get(buffId).copy(value, duration);
		} else {
			LogManager.exception("获取BUFF异常，buffId=" + buffId);
			return null;
		}
	}

	/**
	 * 根据BUFF编号获取BUFF类型
	 * 
	 * @param buffId
	 *            BUFF编号
	 * @return BUFF类型
	 */
	public EBattleBuffType getEBattleBuffType(int buffId) {
		if (battleBuffMap.containsKey(buffId)) {
			return battleBuffMap.get(buffId).getType();
		}
		return null;
	}
}
