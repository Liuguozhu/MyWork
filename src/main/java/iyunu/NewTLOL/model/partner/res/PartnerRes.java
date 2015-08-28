package iyunu.NewTLOL.model.partner.res;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.model.partner.instance.GrowthDate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.partner.msg.RateMsg;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 伙伴资质模版
 * 
 * @author SunHonglei
 * 
 */
public class PartnerRes {

	private int index; // 伙伴索引
	private String nick; // 名称
	private Vocation type; // 类型
	private EColor color; // 颜色
	private int quality; // 品质
	private int grade; // 级别

	private int steps; // 阶数
	private int fightLevel;
	private int innateHp;
	private int innateAttack;
	private int innatePdefence;
	private int innateMdefence;
	private int originalHp;
	private int originalPattack;
	private int originalMattack;
	private int originalPdefence;
	private int originalMdefence;
	private int originalHit;
	private int originalCrit;
	private int originalDodge;
	private int originalParry;
	private int originalSpeed;

	private int star; // 星

	private int evolveExpMax; // 进阶所需经验
	private String skill; // 技能
	private int skillType; // 技能类型
	private int evolveId; // 进阶后编号
	private int growWuhun; // 成长消耗武魂
	private int evolveWuhun; // 进阶消耗武魂
	private int evolveGold; // 进阶消耗银两
	private String growth; // 成长值增加

	private String url; // 资源路径
	private String resName; // 资源名称
	private String headIcon; // 招募卡牌头像资源名称
	private String photo; // 头像
	private int ownExp;// 被吃的每个伙伴的经验
	private int chance;// 每个颜色中被抽出来的概率
	private int starMax;// 可升的最大星级
	private int order;// 伙伴排序专用列，目前只是客户端用

	private String sendBack;// 解雇伙伴返回
	private String talent;
	private String capability;
	private int isfu; // 是否可以当副伙伴 1、可以  0，不可以

	private List<RateMsg> talentList = new ArrayList<>();
	private List<RateMsg> capabilityList = new ArrayList<>();

	public Partner newPartner() {
		Partner partner = new Partner();
		partner.setIndex(index);
		partner.setNick(nick);
		partner.setQuality(quality);
		partner.setGrade(grade);
		partner.setVocation(type);
		partner.setColor(color);

		partner.setFightLevel(fightLevel);
		partner.setSteps(steps);

		partner.setInnateAttack(innateAttack);
		partner.setInnateHp(innateHp);
		partner.setInnatePdefence(innatePdefence);
		partner.setInnateMdefence(innateMdefence);

		partner.setOriginalHp(originalHp);
		partner.setOriginalPattack(originalPattack);
		partner.setOriginalMattack(originalMattack);
		partner.setOriginalPdefence(originalPdefence);
		partner.setOriginalMdefence(originalMdefence);
		partner.setOriginalHit(originalHit);
		partner.setOriginalCrit(originalCrit);
		partner.setOriginalDodge(originalDodge);
		partner.setOriginalParry(originalParry);
		partner.setOriginalSpeed(originalSpeed);

		partner.setEvolveExpMax(evolveExpMax);
		partner.setEvolveId(evolveId); // 进阶后编号
		partner.setGrowWuhun(growWuhun); // 成长消耗武魂
		partner.setEvolveWuhun(evolveWuhun);// 进阶消耗武魂
		partner.setEvolveGold(evolveGold); // 进阶消耗银两
		partner.setStar(star);
		partner.setTurnState(0); // 酒馆武将卡牌翻转状态（0为正，1为反）
		partner.setLevel(1); // 新伙伴为1级
		partner.setPhoto(photo);
		partner.setOwnExp(ownExp);
		partner.setIsfu(isfu);
		String[] growthStrs = growth.split(";");

		for (String growthStr : growthStrs) {
			GrowthDate growthDate = new GrowthDate();
			String[] strs = growthStr.split(":");
			growthDate.setGrowth(Translate.stringToInt(strs[0]));
			growthDate.setGrowthProbability(Translate.stringToInt(strs[1]));
			partner.getGrowthList().add(growthDate);
		}

		if (skill != null && !skill.equals("")) {
			String[] strs = skill.split(";");

			if (skillType == 0) {
				int num = Util.getRandom(4);

				for (int i = 0; i < num; i++) {
					int sumRate = 0;
					int finalRate = Util.getRandom(10000);
					for (String string : strs) {
						String[] skills = string.split(":");
						Integer skillId = Translate.stringToInt(skills[0]);
						sumRate += Translate.stringToInt(skills[1]);
						if (finalRate < sumRate && !partner.getSkills().contains(skillId)) {
							partner.getSkills().add(skillId);
							break;
						}
					}
				}

			} else {
				for (String string : strs) {
					String[] skills = string.split(":");
					int id = Translate.stringToInt(skills[0]);
					int rate = Translate.stringToInt(skills[1]);

					if (Util.probable(rate)) {
						partner.getSkills().add(id);
					}
				}
			}

		}

		partner.setTalent(randomTalent());
		partner.setCapability(randomCapability());

		return partner;
	}

	/**
	 * 获取伙伴资质
	 * 
	 * @return 伙伴资质对象
	 */
	public Partner toPartner() {
		Partner partner = new Partner();
		partner.setIndex(index);
		partner.setNick(nick);
		partner.setQuality(quality);
		partner.setGrade(grade);
		partner.setVocation(type);
		partner.setColor(color);

		partner.setFightLevel(fightLevel);
		partner.setSteps(steps);

		partner.setInnateAttack(innateAttack);
		partner.setInnateHp(innateHp);
		partner.setInnatePdefence(innatePdefence);
		partner.setInnateMdefence(innateMdefence);

		partner.setOriginalHp(originalHp);
		partner.setOriginalPattack(originalPattack);
		partner.setOriginalMattack(originalMattack);
		partner.setOriginalPdefence(originalPdefence);
		partner.setOriginalMdefence(originalMdefence);
		partner.setOriginalHit(originalHit);
		partner.setOriginalCrit(originalCrit);
		partner.setOriginalDodge(originalDodge);
		partner.setOriginalParry(originalParry);
		partner.setOriginalSpeed(originalSpeed);

		partner.setEvolveExpMax(evolveExpMax);
		partner.setEvolveId(evolveId); // 进阶后编号
		partner.setGrowWuhun(growWuhun); // 成长消耗武魂
		partner.setEvolveWuhun(evolveWuhun);// 进阶消耗武魂
		partner.setEvolveGold(evolveGold); // 进阶消耗银两
		partner.setStar(star);
		partner.setTurnState(0); // 酒馆武将卡牌翻转状态（0为正，1为反）
		partner.setLevel(1); // 新伙伴为1级
		partner.setPhoto(photo);// 设置头像
		partner.setOwnExp(ownExp);
		partner.setIsfu(isfu);
		String[] growthStrs = growth.split(";");

		for (int i = 0; i < growthStrs.length; i++) {
			GrowthDate growthDate = new GrowthDate();
			String[] strs = growthStrs[i].split(":");
			growthDate.setGrowth(Translate.stringToInt(strs[0]));
			growthDate.setGrowthProbability(Translate.stringToInt(strs[1]));
			partner.getGrowthList().add(growthDate);
			PartnerManager.instance().getMap().put(Translate.stringToInt(strs[0]), i);
		}

		if (talent != null && !"".equals(talent)) {
			String[] str = talent.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				talentList.add(new RateMsg(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2])));
			}
		}

		if (capability != null && !"".equals(capability)) {
			String[] str = capability.split(";");
			for (String string : str) {
				String[] itemStr = string.split(":");
				capabilityList.add(new RateMsg(Translate.stringToInt(itemStr[0]), Translate.stringToInt(itemStr[1]), Translate.stringToInt(itemStr[2])));
			}
		}

		return partner;
	}

	public int randomTalent() {
		int finalRate = Util.getRandom(10000);
		int num = 0;
		for (RateMsg rateMsg : talentList) {
			num += rateMsg.getProbability();
			if (num >= finalRate) {
				return Util.getRandom(rateMsg.getMin(), rateMsg.getMax());
			}
		}
		return 1;
	}

	public int randomCapability() {
		int finalRate = Util.getRandom(10000);
		int num = 0;
		for (RateMsg rateMsg : capabilityList) {
			num += rateMsg.getProbability();
			if (num >= finalRate) {
				return Util.getRandom(rateMsg.getMin(), rateMsg.getMax());
			}
		}
		return 1;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the type
	 */
	public Vocation getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Vocation type) {
		this.type = type;
	}

	/**
	 * @return the color
	 */
	public EColor getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(EColor color) {
		this.color = color;
	}

	/**
	 * @return the quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * @param quality
	 *            the quality to set
	 */
	public void setQuality(int quality) {
		this.quality = quality;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @return the evolveExpMax
	 */
	public int getEvolveExpMax() {
		return evolveExpMax;
	}

	/**
	 * @param evolveExpMax
	 *            the evolveExpMax to set
	 */
	public void setEvolveExpMax(int evolveExpMax) {
		this.evolveExpMax = evolveExpMax;
	}

	/**
	 * @return the skill
	 */
	public String getSkill() {
		return skill;
	}

	/**
	 * @param skill
	 *            the skill to set
	 */
	public void setSkill(String skill) {
		this.skill = skill;
	}

	/**
	 * @return the evolveId
	 */
	public int getEvolveId() {
		return evolveId;
	}

	/**
	 * @param evolveId
	 *            the evolveId to set
	 */
	public void setEvolveId(int evolveId) {
		this.evolveId = evolveId;
	}

	/**
	 * @return the evolveWuhun
	 */
	public int getEvolveWuhun() {
		return evolveWuhun;
	}

	/**
	 * @param evolveWuhun
	 *            the evolveWuhun to set
	 */
	public void setEvolveWuhun(int evolveWuhun) {
		this.evolveWuhun = evolveWuhun;
	}

	/**
	 * @return the evolveGold
	 */
	public int getEvolveGold() {
		return evolveGold;
	}

	/**
	 * @param evolveGold
	 *            the evolveGold to set
	 */
	public void setEvolveGold(int evolveGold) {
		this.evolveGold = evolveGold;
	}

	/**
	 * @return the growth
	 */
	public String getGrowth() {
		return growth;
	}

	/**
	 * @param growth
	 *            the growth to set
	 */
	public void setGrowth(String growth) {
		this.growth = growth;
	}

	/**
	 * @return the growWuhun
	 */
	public int getGrowWuhun() {
		return growWuhun;
	}

	/**
	 * @param growWuhun
	 *            the growWuhun to set
	 */
	public void setGrowWuhun(int growWuhun) {
		this.growWuhun = growWuhun;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName
	 *            the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the headIcon
	 */
	public String getHeadIcon() {
		return headIcon;
	}

	/**
	 * @param headIcon
	 *            the headIcon to set
	 */
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSkillType() {
		return skillType;
	}

	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

	/**
	 * @return the star
	 */
	public int getStar() {
		return star;
	}

	/**
	 * @param star
	 *            the star to set
	 */
	public void setStar(int star) {
		this.star = star;
	}

	/**
	 * @return the ownExp
	 */
	public int getOwnExp() {
		return ownExp;
	}

	/**
	 * @param ownExp
	 *            the ownExp to set
	 */
	public void setOwnExp(int ownExp) {
		this.ownExp = ownExp;
	}

	/**
	 * @return the chance
	 */
	public int getChance() {
		return chance;
	}

	/**
	 * @param chance
	 *            the chance to set
	 */
	public void setChance(int chance) {
		this.chance = chance;
	}

	/**
	 * @return the starMax
	 */
	public int getStarMax() {
		return starMax;
	}

	/**
	 * @param starMax
	 *            the starMax to set
	 */
	public void setStarMax(int starMax) {
		this.starMax = starMax;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the fightLevel
	 */
	public int getFightLevel() {
		return fightLevel;
	}

	/**
	 * @param fightLevel
	 *            the fightLevel to set
	 */
	public void setFightLevel(int fightLevel) {
		this.fightLevel = fightLevel;
	}

	/**
	 * @return the innateHp
	 */
	public int getInnateHp() {
		return innateHp;
	}

	/**
	 * @param innateHp
	 *            the innateHp to set
	 */
	public void setInnateHp(int innateHp) {
		this.innateHp = innateHp;
	}

	/**
	 * @return the innateAttack
	 */
	public int getInnateAttack() {
		return innateAttack;
	}

	/**
	 * @param innateAttack
	 *            the innateAttack to set
	 */
	public void setInnateAttack(int innateAttack) {
		this.innateAttack = innateAttack;
	}

	/**
	 * @return the innatePdefence
	 */
	public int getInnatePdefence() {
		return innatePdefence;
	}

	/**
	 * @param innatePdefence
	 *            the innatePdefence to set
	 */
	public void setInnatePdefence(int innatePdefence) {
		this.innatePdefence = innatePdefence;
	}

	/**
	 * @return the innateMdefence
	 */
	public int getInnateMdefence() {
		return innateMdefence;
	}

	/**
	 * @param innateMdefence
	 *            the innateMdefence to set
	 */
	public void setInnateMdefence(int innateMdefence) {
		this.innateMdefence = innateMdefence;
	}

	/**
	 * @return the originalHp
	 */
	public int getOriginalHp() {
		return originalHp;
	}

	/**
	 * @param originalHp
	 *            the originalHp to set
	 */
	public void setOriginalHp(int originalHp) {
		this.originalHp = originalHp;
	}

	/**
	 * @return the originalPattack
	 */
	public int getOriginalPattack() {
		return originalPattack;
	}

	/**
	 * @param originalPattack
	 *            the originalPattack to set
	 */
	public void setOriginalPattack(int originalPattack) {
		this.originalPattack = originalPattack;
	}

	/**
	 * @return the originalMattack
	 */
	public int getOriginalMattack() {
		return originalMattack;
	}

	/**
	 * @param originalMattack
	 *            the originalMattack to set
	 */
	public void setOriginalMattack(int originalMattack) {
		this.originalMattack = originalMattack;
	}

	/**
	 * @return the originalPdefence
	 */
	public int getOriginalPdefence() {
		return originalPdefence;
	}

	/**
	 * @param originalPdefence
	 *            the originalPdefence to set
	 */
	public void setOriginalPdefence(int originalPdefence) {
		this.originalPdefence = originalPdefence;
	}

	/**
	 * @return the originalMdefence
	 */
	public int getOriginalMdefence() {
		return originalMdefence;
	}

	/**
	 * @param originalMdefence
	 *            the originalMdefence to set
	 */
	public void setOriginalMdefence(int originalMdefence) {
		this.originalMdefence = originalMdefence;
	}

	/**
	 * @return the originalHit
	 */
	public int getOriginalHit() {
		return originalHit;
	}

	/**
	 * @param originalHit
	 *            the originalHit to set
	 */
	public void setOriginalHit(int originalHit) {
		this.originalHit = originalHit;
	}

	/**
	 * @return the originalCrit
	 */
	public int getOriginalCrit() {
		return originalCrit;
	}

	/**
	 * @param originalCrit
	 *            the originalCrit to set
	 */
	public void setOriginalCrit(int originalCrit) {
		this.originalCrit = originalCrit;
	}

	/**
	 * @return the originalDodge
	 */
	public int getOriginalDodge() {
		return originalDodge;
	}

	/**
	 * @param originalDodge
	 *            the originalDodge to set
	 */
	public void setOriginalDodge(int originalDodge) {
		this.originalDodge = originalDodge;
	}

	/**
	 * @return the originalParry
	 */
	public int getOriginalParry() {
		return originalParry;
	}

	/**
	 * @param originalParry
	 *            the originalParry to set
	 */
	public void setOriginalParry(int originalParry) {
		this.originalParry = originalParry;
	}

	/**
	 * @return the originalSpeed
	 */
	public int getOriginalSpeed() {
		return originalSpeed;
	}

	/**
	 * @param originalSpeed
	 *            the originalSpeed to set
	 */
	public void setOriginalSpeed(int originalSpeed) {
		this.originalSpeed = originalSpeed;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * @return the sendBack
	 */
	public String getSendBack() {
		return sendBack;
	}

	/**
	 * @param sendBack
	 *            the sendBack to set
	 */
	public void setSendBack(String sendBack) {
		this.sendBack = sendBack;
	}

	/**
	 * @return the talent
	 */
	public String getTalent() {
		return talent;
	}

	/**
	 * @param talent
	 *            the talent to set
	 */
	public void setTalent(String talent) {
		this.talent = talent;
	}

	/**
	 * @return the capability
	 */
	public String getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
	 */
	public void setCapability(String capability) {
		this.capability = capability;
	}

	/**
	 * @return the isfu
	 */
	public int getIsfu() {
		return isfu;
	}

	/**
	 * @param isfu
	 *            the isfu to set
	 */
	public void setIsfu(int isfu) {
		this.isfu = isfu;
	}

}
