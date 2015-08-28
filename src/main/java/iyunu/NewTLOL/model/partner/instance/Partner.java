package iyunu.NewTLOL.model.partner.instance;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonTool;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class Partner implements ICharacter {

	@Override
	public byte getType() {
		return 1;
	}

	private Role role; // 伙伴拥有者
	// 存储信息
	private long id; // 编号
	private int index; // 伙伴索引
	private String nick; // 名称
	private int level; // 等级
	private int exp; // 经验
	private int hp; // 生命值
	private int evolveExp; // 进阶经验
	private int turnState; // 酒馆武将卡牌翻转状态（0为正，1为反）

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

	// 获取信息
	private Vocation vocation; // 类型
	private EColor color; // 颜色
	private int quality; // 品质
	private int grade; // 级别
	private int expMax; // 升级所需经验
	private int evolveExpMax; // 进阶所需经验
	private int evolveId; // 进阶后编号
	private int growWuhun; // 成长消耗武魂
	private int evolveWuhun; // 进阶消耗武魂
	private int evolveGold; // 进阶消耗银两
	private int star; // 星

	/** 技能 **/
	private List<Integer> skills = new ArrayList<Integer>(); // 技能编号
	/** 成长值数据对象列表 **/
	private List<GrowthDate> growthList = new ArrayList<GrowthDate>();
	private String photo;

	// 实时属性
	private int hpMax; // 生命值上限
	private int pattack; // 外功攻击
	private int pdefence; // 外功防御
	private int mattack; // 内功攻击
	private int mdefence; // 内功防御
	private int speed; // 速度
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡

	private int fightLevel; // 出战等级
	private int steps; // 阶
	private boolean isBattle = false; // 是否出战
	private int isBind; // 绑定（0.不绑定，1.绑定）

	private int ownExp;// 被吃伙伴的经验
	private EpartnerOperate operateFlag;// 操作标记，0 新增，1 更新，2删除。 用于发送给客户端，标记操作
	private int worth; // 评分
	private int talent; // 成长率
	private int capability; // 潜力值
	private int isfu; // 是否可以当副伙伴 1、可以 0，不可以

	@Override
	public int worth() {
		return worth;
	}

	/**
	 * 检查是否已学习次技能
	 * 
	 * @param category
	 *            技能种类
	 * @return 已学习
	 */
	public boolean checkSkill(int skillId) {
		if (skills.contains(skillId)) {
			return true;
		}
		return false;
	}

	public String encode() {
		String result = id + "&" + nick + "&" + level + "&" + exp + "&" + hp + "&" + evolveExp + "&" + isBattle;
		// 技能
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(skills);
		result += "&" + serializer.toString() + "&" + isBind + "&" + talent + "&" + capability;
		return result;
	}

	public void decode(String string) {

		String[] str = string.split("&");

		this.setId(Translate.stringToInt(str[0]));
		// this.setNick(str[1]);
		this.setLevel(Translate.stringToInt(str[2]));
		if (this.getLevel() > PartnerManager.MAX_LEVEL) {
			this.setLevel(PartnerManager.MAX_LEVEL);
		}
		this.setExp(Translate.stringToInt(str[3]));
		this.setHp(Translate.stringToInt(str[4]));
		this.setEvolveExp(Translate.stringToInt(str[5]));
		// this.setBattle(Translate.stringToBoolean(str[6]));
		skills.clear();
		skills.addAll(JsonTool.decode(str[7], new TypeReference<ArrayList<Integer>>() {
		}));
		if (str.length > 8) {
			this.setIsBind(Translate.stringToInt(str[8]));
		}
		
		if(str.length > 10){
			this.setTalent(Translate.stringToInt(str[9]));
			this.setCapability(Translate.stringToInt(str[10]));
		}

		if (capability <= 0) {
			setCapability(250);
		}
	}

	public String encodeInn() {
		String result = id + "&" + turnState;
		// 技能
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(skills);
		result += "&" + serializer.toString();
		return result;
	}

	public void decodeInn(String string) {
		String[] str = string.split("&");
		this.setId(Translate.stringToInt(str[0]));
		this.setTurnState(Translate.stringToInt(str[1]));
		skills.clear();
		skills.addAll(JsonTool.decode(str[2], new TypeReference<ArrayList<Integer>>() {
		}));
	}

	public Partner copy() {
		Partner partner = new Partner();

		partner.setRole(role);
		partner.setId(id);
		partner.setIndex(index);
		partner.setNick(nick);
		partner.setVocation(vocation);
		partner.setColor(color);
		partner.setQuality(quality); // 品质
		partner.setGrade(grade);
		partner.setLevel(level); // 等级
		partner.setExp(exp); // 经验
		partner.setExpMax(expMax); // 升级所需经验
		partner.setTurnState(0); // 酒馆武将卡牌翻转状态（0为正，1为反）

		partner.setHp(hp); // 生命值

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

		partner.setEvolveExp(evolveExp); // 进阶经验
		partner.setEvolveExpMax(evolveExpMax); // 进阶所需经验
		partner.setEvolveId(evolveId); // 进阶后编号
		partner.setGrowWuhun(growWuhun); // 成长消耗武魂
		partner.setEvolveWuhun(evolveWuhun);// 进阶消耗武魂
		partner.setEvolveGold(evolveGold); // 进阶消耗银两
		partner.setStar(star);
		// 实时属性
		partner.setHpMax(hpMax); // 生命值上限
		partner.setPattack(pattack); // 外功攻击
		partner.setPdefence(pdefence); // 外功防御
		partner.setMattack(mattack); // 内功攻击
		partner.setMdefence(mdefence); // 内功防御
		partner.setSpeed(speed); // 速度
		partner.setHit(hit); // 命中
		partner.setDodge(dodge); // 闪避
		partner.setCrit(crit); // 暴击
		partner.setParry(parry); // 格挡
		partner.setPhoto(photo);// 头像
		partner.setOwnExp(ownExp);// 自己的经验

		for (GrowthDate growthDate : growthList) {
			partner.getGrowthList().add(growthDate);
		}

		for (Integer skillId : skills) {
			partner.getSkills().add(skillId);
		}

		partner.setWorth(worth);
		partner.setCapability(capability);
		partner.setTalent(talent);
		partner.setIsfu(isfu);

		return partner;
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
	 * 伙伴进阶随机成长值
	 * 
	 * @return 成长值
	 */
	public int randomGrowth() {
		for (GrowthDate growthDate : growthList) {
			if (Util.probable(growthDate.getGrowthProbability())) {
				return growthDate.getGrowth();
			}
		}
		return 0;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
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
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the expMax
	 */
	public int getExpMax() {
		return expMax;
	}

	/**
	 * @param expMax
	 *            the expMax to set
	 */
	public void setExpMax(int expMax) {
		this.expMax = expMax;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
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
	 * @return the evolveExp
	 */
	public int getEvolveExp() {
		return evolveExp;
	}

	/**
	 * @param evolveExp
	 *            the evolveExp to set
	 */
	public void setEvolveExp(int evolveExp) {
		this.evolveExp = evolveExp;
	}

	public int getEvolveId() {
		return evolveId;
	}

	public void setEvolveId(int evolveId) {
		this.evolveId = evolveId;
	}

	public int getEvolveWuhun() {
		return evolveWuhun;
	}

	public void setEvolveWuhun(int evolveWuhun) {
		this.evolveWuhun = evolveWuhun;
	}

	public int getEvolveGold() {
		return evolveGold;
	}

	public void setEvolveGold(int evolveGold) {
		this.evolveGold = evolveGold;
	}

	/**
	 * @return the hpMax
	 */
	public int getHpMax() {
		return hpMax;
	}

	/**
	 * @param hpMax
	 *            the hpMax to set
	 */
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	/**
	 * @return the pattack
	 */
	public int getPattack() {
		return pattack;
	}

	/**
	 * @param pattack
	 *            the pattack to set
	 */
	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	/**
	 * @return the pdefence
	 */
	public int getPdefence() {
		return pdefence;
	}

	/**
	 * @param pdefence
	 *            the pdefence to set
	 */
	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	/**
	 * @return the mattack
	 */
	public int getMattack() {
		return mattack;
	}

	/**
	 * @param mattack
	 *            the mattack to set
	 */
	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	/**
	 * @return the mdefence
	 */
	public int getMdefence() {
		return mdefence;
	}

	/**
	 * @param mdefence
	 *            the mdefence to set
	 */
	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the hit
	 */
	public int getHit() {
		return hit;
	}

	/**
	 * @param hit
	 *            the hit to set
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}

	/**
	 * @return the dodge
	 */
	public int getDodge() {
		return dodge;
	}

	/**
	 * @param dodge
	 *            the dodge to set
	 */
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	/**
	 * @return the crit
	 */
	public int getCrit() {
		return crit;
	}

	/**
	 * @param crit
	 *            the crit to set
	 */
	public void setCrit(int crit) {
		this.crit = crit;
	}

	/**
	 * @return the parry
	 */
	public int getParry() {
		return parry;
	}

	/**
	 * @param parry
	 *            the parry to set
	 */
	public void setParry(int parry) {
		this.parry = parry;
	}

	/**
	 * @return the skills
	 */
	public List<Integer> getSkills() {
		return skills;
	}

	/**
	 * @param skills
	 *            the skills to set
	 */
	public void setSkills(List<Integer> skills) {
		this.skills = skills;
	}

	public List<GrowthDate> getGrowthList() {
		return growthList;
	}

	public int getTurnState() {
		return turnState;
	}

	public void setTurnState(int turnState) {
		this.turnState = turnState;
	}

	@Override
	public int getMp() {
		return 0;
	}

	@Override
	public void setMp(int mp) {

	}

	@Override
	public int getMpMax() {
		return 0;
	}

	@Override
	public void setMpMax(int mpMax) {

	}

	@Override
	public long getFigure() {
		return index;
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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the isBattle
	 */
	public boolean isBattle() {
		return isBattle;
	}

	/**
	 * @param isBattle
	 *            the isBattle to set
	 */
	public void setBattle(boolean isBattle) {
		this.isBattle = isBattle;
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
	 * @return the operateFlag
	 */
	public EpartnerOperate getOperateFlag() {
		return operateFlag;
	}

	/**
	 * @param operateFlag
	 *            the operateFlag to set
	 */
	public void setOperateFlag(EpartnerOperate operateFlag) {
		this.operateFlag = operateFlag;
	}

	/**
	 * @return the isBind
	 */
	public int getIsBind() {
		return isBind;
	}

	/**
	 * @param isBind
	 *            the isBind to set
	 */
	public void setIsBind(int isBind) {
		this.isBind = isBind;
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
	 * @param growthList
	 *            the growthList to set
	 */
	public void setGrowthList(List<GrowthDate> growthList) {
		this.growthList = growthList;
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
	 * @return the talent
	 */
	public int getTalent() {
		return talent;
	}

	/**
	 * @param talent
	 *            the talent to set
	 */
	public void setTalent(int talent) {
		this.talent = talent;
	}

	/**
	 * @return the capability
	 */
	public int getCapability() {
		return capability;
	}

	/**
	 * @param capability
	 *            the capability to set
	 */
	public void setCapability(int capability) {
		this.capability = capability;
	}

	/**
	 * @return the worth
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * @param worth
	 *            the worth to set
	 */
	public void setWorth(int worth) {
		this.worth = worth;
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
