package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.role.Role;

import java.util.HashMap;
import java.util.Map;

public class PartnerManager {

	/** 客栈刷新普通间隔时间 **/
	public static long DURATION1 = 6 * 60 * 60 * 1000;
	/** 客栈刷新元宝间隔时间 **/
	public static long DURATION2 = 72 * 60 * 60 * 1000;

	/** 成长状态（是否暴击0为正常,1小暴击,2为大暴击） **/
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

	/** 最大伙伴数 */
	public static int MAX_NUM = 50;
	/** 伙伴最高等级 **/
	public static int MAX_LEVEL = 80;

	/**
	 * 私有构造方法
	 */
	private PartnerManager() {

	}

	private static PartnerManager instance = new PartnerManager();

	/**
	 * 伙伴实例
	 * 
	 * @return 伙伴实例
	 */
	public static PartnerManager instance() {
		return instance;
	}

	/**
	 * @function 根据颜色获取伙伴的资质系数
	 * @author LuoSR
	 * @param color
	 *            颜色
	 * @return 伙伴的资质系数
	 * @date 2014年5月15日
	 */
	// public Coefficient countCoefficient(EColor color) {
	// Coefficient coefficient = new Coefficient();
	// switch (color) {
	// case green:
	// coefficient.setHp_coefficient(850);
	// coefficient.setAttack_coefficient(500);
	// coefficient.setDefence_coefficient(550);
	// coefficient.setSpeed_coefficient(150);
	// return coefficient;
	// case blue:
	// coefficient.setHp_coefficient(750);
	// coefficient.setAttack_coefficient(450);
	// coefficient.setDefence_coefficient(500);
	// coefficient.setSpeed_coefficient(150);
	// return coefficient;
	// case purple:
	// coefficient.setHp_coefficient(650);
	// coefficient.setAttack_coefficient(400);
	// coefficient.setDefence_coefficient(450);
	// coefficient.setSpeed_coefficient(150);
	// return coefficient;
	// case orange:
	// coefficient.setHp_coefficient(550);
	// coefficient.setAttack_coefficient(350);
	// coefficient.setDefence_coefficient(400);
	// coefficient.setSpeed_coefficient(150);
	// return coefficient;
	//
	// default:
	// return null;
	// }
	//
	// }

	// 1-35级
	// 36-68级
	// 69-70级
	/**
	 * 十连抽只有在指定抽到紫伙伴才用，十连抽的单抽要走 2 元宝的概率
	 * 
	 * @param role角色对象
	 * @param chouType
	 *            抽取的方式 1普通 2元宝，3十连抽
	 * @return
	 */
	public int getRefreshType(Role role, int chouType) {
		int type = 0;
		switch (chouType) {
		case 1:
			if (role.getLevel() <= 35) {
				type = 1;
			} else if (role.getLevel() > 35 && role.getLevel() <= 68) {
				type = 2;
			} else {
				type = 3;
			}
			break;
		case 2:
			if (role.getLevel() <= 35) {
				type = 4;
			} else if (role.getLevel() > 35 && role.getLevel() <= 68) {
				type = 5;
			} else {
				type = 6;
			}
			break;
		case 3:
			if (role.getLevel() <= 35) {
				type = 7;
			} else if (role.getLevel() > 35 && role.getLevel() <= 68) {
				type = 8;
			} else {
				type = 9;
			}
			break;

		default:
			type = 1;
			break;
		}
		return type;
	}

	public Map<Integer, Integer> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Integer> map) {
		this.map = map;
	}
}
