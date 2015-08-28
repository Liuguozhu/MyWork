package iyunu.NewTLOL.model.jingMai.instance;

import iyunu.NewTLOL.model.jingMai.EJingMaiCategory;
import iyunu.NewTLOL.model.jingMai.Eproperty;

/**
 * @function 经脉
 * @author LuoSR
 * @date 2013年12月9日
 */
public class JingMai {

	/** 经脉编号 **/
	private int id;
	/** 经脉名称 **/
	private String name;
	/** 经脉类型 **/
	private String type;
	/** 经脉类别 **/
	private EJingMaiCategory category;
	/** 点亮顺序 **/
	private int order;
	/** 小的脉络 **/
	private String vein;
	/** 增加属性 **/
	private Eproperty property;
	/** 消耗元气 **/
	private int expend;

	private int hpMax;
	private int mpMax;
	private int pattack;
	private int mattack;
	private int hit;
	private int dodge;
	private int speed;
	private int crit;
	private int parry;
	private int pdefence;
	private int mdefence;
	private int strength;
	private int intellect;
	private int physique;
	private int agility;
	private int mark;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public JingMai copy() {
		JingMai jingMai = new JingMai();
		jingMai.setId(id);
		jingMai.setName(name);
		jingMai.setType(type);
		jingMai.setCategory(category);
		jingMai.setOrder(order);
		jingMai.setVein(vein);
		jingMai.setProperty(property);
		jingMai.setExpend(expend);
		jingMai.setHpMax(hpMax);
		jingMai.setMpMax(mpMax);
		jingMai.setPattack(pattack);
		jingMai.setMattack(mattack);
		jingMai.setHit(hit);
		jingMai.setDodge(dodge);
		jingMai.setSpeed(speed);
		jingMai.setCrit(crit);
		jingMai.setParry(parry);
		jingMai.setPdefence(pdefence);
		jingMai.setMdefence(mdefence);
		jingMai.setStrength(strength);
		jingMai.setIntellect(intellect);
		jingMai.setPhysique(physique);
		jingMai.setAgility(agility);
		jingMai.setMark(mark);
		return jingMai;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EJingMaiCategory getCategory() {
		return category;
	}

	public void setCategory(EJingMaiCategory category) {
		this.category = category;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getVein() {
		return vein;
	}

	public void setVein(String vein) {
		this.vein = vein;
	}

	public Eproperty getProperty() {
		return property;
	}

	public void setProperty(Eproperty property) {
		this.property = property;
	}

	public int getExpend() {
		return expend;
	}

	public void setExpend(int expend) {
		this.expend = expend;
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
	 * @return the mpMax
	 */
	public int getMpMax() {
		return mpMax;
	}

	/**
	 * @param mpMax
	 *            the mpMax to set
	 */
	public void setMpMax(int mpMax) {
		this.mpMax = mpMax;
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
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength
	 *            the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * @return the intellect
	 */
	public int getIntellect() {
		return intellect;
	}

	/**
	 * @param intellect
	 *            the intellect to set
	 */
	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	/**
	 * @return the physique
	 */
	public int getPhysique() {
		return physique;
	}

	/**
	 * @param physique
	 *            the physique to set
	 */
	public void setPhysique(int physique) {
		this.physique = physique;
	}

	/**
	 * @return the agility
	 */
	public int getAgility() {
		return agility;
	}

	/**
	 * @param agility
	 *            the agility to set
	 */
	public void setAgility(int agility) {
		this.agility = agility;
	}

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

}
