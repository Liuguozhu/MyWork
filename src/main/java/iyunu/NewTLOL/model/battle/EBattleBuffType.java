package iyunu.NewTLOL.model.battle;

import iyunu.NewTLOL.model.battle.buff.IBuff;
import iyunu.NewTLOL.model.battle.buff.不中封;
import iyunu.NewTLOL.model.battle.buff.不能操作;
import iyunu.NewTLOL.model.battle.buff.再生;
import iyunu.NewTLOL.model.battle.buff.净化;
import iyunu.NewTLOL.model.battle.buff.减少内功防御;
import iyunu.NewTLOL.model.battle.buff.减少外功防御;
import iyunu.NewTLOL.model.battle.buff.减少攻击;
import iyunu.NewTLOL.model.battle.buff.减少防御;
import iyunu.NewTLOL.model.battle.buff.双封;
import iyunu.NewTLOL.model.battle.buff.吸血;
import iyunu.NewTLOL.model.battle.buff.增加伤害;
import iyunu.NewTLOL.model.battle.buff.增加免伤;
import iyunu.NewTLOL.model.battle.buff.增加内伤;
import iyunu.NewTLOL.model.battle.buff.增加内功防御;
import iyunu.NewTLOL.model.battle.buff.增加命中;
import iyunu.NewTLOL.model.battle.buff.增加外伤;
import iyunu.NewTLOL.model.battle.buff.增加外功防御;
import iyunu.NewTLOL.model.battle.buff.增加攻击;
import iyunu.NewTLOL.model.battle.buff.增加暴击;
import iyunu.NewTLOL.model.battle.buff.增加闪避;
import iyunu.NewTLOL.model.battle.buff.增加防御;
import iyunu.NewTLOL.model.battle.buff.封技能;
import iyunu.NewTLOL.model.battle.buff.封攻击;
import iyunu.NewTLOL.model.battle.buff.持续回血;
import iyunu.NewTLOL.model.battle.buff.持续掉血;
import iyunu.NewTLOL.model.battle.buff.眩晕;
import iyunu.NewTLOL.model.battle.buff.神佑;
import iyunu.NewTLOL.model.battle.buff.附加持续掉血;

public enum EBattleBuffType {

	/** 不能任何操作 **/
	b1(不能操作.instance()), //
	/** 不能攻击（内功，外功） **/
	b2(双封.instance()), //
	/** 不能使用技能 **/
	b3(封技能.instance()), //
	/** 不能普通攻击 **/
	b4(封攻击.instance()), //

	/** 持续回血 **/
	b5(持续回血.instance()), //
	/** 持续掉血 **/
	b6(持续掉血.instance()), //

	/** 增加闪避 **/
	b7(增加闪避.instance()), //
	/** 增加命中 **/
	b8(增加命中.instance()), //
	/** 增加外功防御 **/
	b9(增加外功防御.instance()), //
	/** 增加内功防御 **/
	b10(增加内功防御.instance()), //
	/** 增加暴击 **/
	b11(增加暴击.instance()), //

	/** 降低外功防御 **/
	b12(减少外功防御.instance()), //
	/** 降低内功防御 **/
	b13(减少内功防御.instance()), //

	/** 眩晕 **/
	b14(眩晕.instance()), //
	/** 净化 **/
	b15(净化.instance()), //
	/** 再生 **/
	b16(再生.instance()), //

	/** 不中封 **/
	b17(不中封.instance()), //
	/** 神佑 **/
	b18(神佑.instance()), //

	/** 增加伤害 **/
	b19(增加伤害.instance()), //
	/** 增加免伤 **/
	b20(增加免伤.instance()), //
	/** 吸血 **/
	b21(吸血.instance()), //
	/** 增加防御 **/
	b22(增加防御.instance()), //
	/** 减少防御 **/
	b23(减少防御.instance()), //
	/** 增加外伤 **/
	b24(增加外伤.instance()), //
	/** 增加内伤 **/
	b25(增加内伤.instance()), //
	/** 增加攻击 **/
	b26(增加攻击.instance()), //
	/** 减少攻击 **/
	b27(减少攻击.instance()), //
	/** 附加持续掉血 **/
	b28(附加持续掉血.instance()), //
	;

	private IBuff buff;

	EBattleBuffType(IBuff buff) {
		this.buff = buff;
	}

	/**
	 * @return the buff
	 */
	public IBuff getBuff() {
		return buff;
	}

}
