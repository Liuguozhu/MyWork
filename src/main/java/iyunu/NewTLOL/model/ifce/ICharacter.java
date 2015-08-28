package iyunu.NewTLOL.model.ifce;

import iyunu.NewTLOL.enumeration.Vocation;

import java.util.List;

public interface ICharacter {
	/**
	 * 获取对象类型
	 * 
	 * @return 对象类型(0.角色，1.伙伴，2.怪物)
	 */
	byte getType();

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	long getId();

	/**
	 * 设置编号
	 * 
	 * @param id
	 *            编号
	 */
	void setId(long id);

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	String getNick();

	/**
	 * 设置名称
	 * 
	 * @param nick
	 *            名称
	 */
	void setNick(String nick);

	/**
	 * 获取等级
	 * 
	 * @return 当前等级
	 */
	int getLevel();

	/**
	 * 设置等级
	 * 
	 * @param level
	 *            等级
	 */
	void setLevel(int level);

	/**
	 * 获取门派
	 * 
	 * @return 门派
	 */
	Vocation getVocation();

	/**
	 * 获取生命值
	 * 
	 * @return 生命值
	 */
	int getHp();

	/**
	 * 设置生命值
	 * 
	 * @param hp
	 *            生命值
	 */
	void setHp(int hp);

	/**
	 * 获取内力值
	 * 
	 * @return 内力值
	 */
	int getMp();

	/**
	 * 设置内力值
	 * 
	 * @param mp
	 *            内力值
	 */
	void setMp(int mp);

	/**
	 * 获取生命上限
	 * 
	 * @return 生命上限
	 */
	int getHpMax();

	/**
	 * 设置生命上限
	 * 
	 * @param hpMax
	 *            生命上限
	 */
	void setHpMax(int hpMax);

	/**
	 * 获取内力上限
	 * 
	 * @return 内力上限
	 */
	int getMpMax();

	/**
	 * 设置内力上限
	 * 
	 * @param mpMax
	 *            内力上限
	 */
	void setMpMax(int mpMax);

	/**
	 * 获取外功攻击
	 * 
	 * @return 外功攻击
	 */
	int getPattack();

	/**
	 * 设置外功攻击
	 * 
	 * @param pattack
	 *            外功攻击
	 */
	void setPattack(int pattack);

	/**
	 * 获取外功防御
	 * 
	 * @return 外功防御
	 */
	int getPdefence();

	/**
	 * 设置外功防御
	 * 
	 * @param pdefence
	 *            外功防御
	 */
	void setPdefence(int pdefence);

	/**
	 * 获取内功攻击
	 * 
	 * @return 内功攻击
	 */
	int getMattack();

	/**
	 * 设置内功攻击
	 * 
	 * @param mattack
	 *            内功攻击
	 */
	void setMattack(int mattack);

	/**
	 * 获取内功防御
	 * 
	 * @return 内功防御
	 */
	int getMdefence();

	/**
	 * 设置内功防御
	 * 
	 * @param mdefence
	 *            内功防御
	 */
	void setMdefence(int mdefence);

	/**
	 * 获取命中
	 * 
	 * @return 命中
	 */
	int getHit();

	/**
	 * 设置命中
	 * 
	 * @param hit
	 *            命中
	 */
	void setHit(int hit);

	/**
	 * 获取闪避
	 * 
	 * @return 闪避
	 */
	int getDodge();

	/**
	 * 设置闪避
	 * 
	 * @param dodge
	 *            闪避
	 */
	void setDodge(int dodge);

	/**
	 * 获取暴击
	 * 
	 * @return 暴击
	 */
	int getCrit();

	/**
	 * 设置暴击
	 * 
	 * @param crit
	 *            暴击
	 */
	void setCrit(int crit);

	/**
	 * 获取格挡
	 * 
	 * @return 格挡
	 */
	int getParry();

	/**
	 * 设置格挡
	 * 
	 * @param parry
	 *            格挡
	 */
	void setParry(int parry);

	/**
	 * 获取速度
	 * 
	 * @return 速度
	 */
	int getSpeed();

	/**
	 * 设置速度
	 * 
	 * @param speed
	 *            速度
	 */
	void setSpeed(int speed);

	/**
	 * @return the figure
	 */
	 long getFigure();
	
	 List<Integer> getSkills();
	 
	 int worth();
}
