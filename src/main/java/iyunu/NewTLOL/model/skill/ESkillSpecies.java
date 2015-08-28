package iyunu.NewTLOL.model.skill;

import iyunu.NewTLOL.model.skill.species.SkillSpecies;
import iyunu.NewTLOL.model.skill.species.攻击;
import iyunu.NewTLOL.model.skill.species.dl.一阳指;
import iyunu.NewTLOL.model.skill.species.dl.净衣术;
import iyunu.NewTLOL.model.skill.species.dl.化生;
import iyunu.NewTLOL.model.skill.species.dl.吞云吐纳;
import iyunu.NewTLOL.model.skill.species.dl.回风拂柳;
import iyunu.NewTLOL.model.skill.species.dl.大道无相;
import iyunu.NewTLOL.model.skill.species.dl.梵音天籁;
import iyunu.NewTLOL.model.skill.species.dl.落英缤纷;
import iyunu.NewTLOL.model.skill.species.dl.逍遥游;
import iyunu.NewTLOL.model.skill.species.dl.金玉满堂;
import iyunu.NewTLOL.model.skill.species.gsmr.伤;
import iyunu.NewTLOL.model.skill.species.gsmr.冥;
import iyunu.NewTLOL.model.skill.species.gsmr.影;
import iyunu.NewTLOL.model.skill.species.gsmr.抵;
import iyunu.NewTLOL.model.skill.species.gsmr.灭;
import iyunu.NewTLOL.model.skill.species.gsmr.狂;
import iyunu.NewTLOL.model.skill.species.gsmr.疾;
import iyunu.NewTLOL.model.skill.species.gsmr.破;
import iyunu.NewTLOL.model.skill.species.gsmr.转;
import iyunu.NewTLOL.model.skill.species.gsmr.震;
import iyunu.NewTLOL.model.skill.species.ljg.凝血术;
import iyunu.NewTLOL.model.skill.species.ljg.化骨掌;
import iyunu.NewTLOL.model.skill.species.ljg.嗜血咒;
import iyunu.NewTLOL.model.skill.species.ljg.我意逍遥;
import iyunu.NewTLOL.model.skill.species.ljg.扑朔迷离;
import iyunu.NewTLOL.model.skill.species.ljg.月影步;
import iyunu.NewTLOL.model.skill.species.ljg.生死咒;
import iyunu.NewTLOL.model.skill.species.ljg.虎鹰扬视;
import iyunu.NewTLOL.model.skill.species.ljg.融雪掌;
import iyunu.NewTLOL.model.skill.species.ljg.逍遥诀;
import iyunu.NewTLOL.model.skill.species.partner.免疫;
import iyunu.NewTLOL.model.skill.species.partner.内功攻击;
import iyunu.NewTLOL.model.skill.species.partner.内功防御;
import iyunu.NewTLOL.model.skill.species.partner.再生;
import iyunu.NewTLOL.model.skill.species.partner.净化;
import iyunu.NewTLOL.model.skill.species.partner.反震;
import iyunu.NewTLOL.model.skill.species.partner.吸血;
import iyunu.NewTLOL.model.skill.species.partner.命中;
import iyunu.NewTLOL.model.skill.species.partner.固化;
import iyunu.NewTLOL.model.skill.species.partner.外功攻击;
import iyunu.NewTLOL.model.skill.species.partner.外功防御;
import iyunu.NewTLOL.model.skill.species.partner.天机;
import iyunu.NewTLOL.model.skill.species.partner.天相;
import iyunu.NewTLOL.model.skill.species.partner.奇袭;
import iyunu.NewTLOL.model.skill.species.partner.必杀;
import iyunu.NewTLOL.model.skill.species.partner.普通攻击;
import iyunu.NewTLOL.model.skill.species.partner.格挡;
import iyunu.NewTLOL.model.skill.species.partner.狂爆;
import iyunu.NewTLOL.model.skill.species.partner.眩晕;
import iyunu.NewTLOL.model.skill.species.partner.神佑;
import iyunu.NewTLOL.model.skill.species.partner.虚弱;
import iyunu.NewTLOL.model.skill.species.partner.连击;
import iyunu.NewTLOL.model.skill.species.partner.连环;
import iyunu.NewTLOL.model.skill.species.partner.追击;
import iyunu.NewTLOL.model.skill.species.partner.闪避;
import iyunu.NewTLOL.model.skill.species.partner.鬼步;
import iyunu.NewTLOL.model.skill.species.sl.亦枯亦荣;
import iyunu.NewTLOL.model.skill.species.sl.如来千手法;
import iyunu.NewTLOL.model.skill.species.sl.摩柯无量;
import iyunu.NewTLOL.model.skill.species.sl.罗汉金身;
import iyunu.NewTLOL.model.skill.species.sl.菩提心法;
import iyunu.NewTLOL.model.skill.species.sl.金钟罩;
import iyunu.NewTLOL.model.skill.species.sl.锁指功;
import iyunu.NewTLOL.model.skill.species.sl.阿罗汉神功;
import iyunu.NewTLOL.model.skill.species.sl.韦陀献杵;
import iyunu.NewTLOL.model.skill.species.sl.龟背功;

public enum ESkillSpecies {

	s0(攻击.instance()), //
	
	s1(韦陀献杵.instance()), //
	s2(如来千手法.instance()), //
	s3(金钟罩.instance()), //
		s4(亦枯亦荣.instance()), //
	s5(罗汉金身.instance()), //
		s6(菩提心法.instance()), //
		s7(阿罗汉神功.instance()), //
		s8(摩柯无量.instance()), //
	s9(龟背功.instance()), //
		s10(锁指功.instance()), //
			
		s11(伤.instance()), //
		s12(转.instance()), //
		s13(冥.instance()), //
		s14(震.instance()), //
		s15(疾.instance()), //
		s16(破.instance()), //
		s17(狂.instance()), //
		s18(抵.instance()), //
		s19(影.instance()), //
		s20(灭.instance()), //
			
		s21(一阳指.instance()), //
		s22(金玉满堂.instance()), //
		s23(净衣术.instance()), //
		s24(梵音天籁.instance()), //
		s25(逍遥游.instance()), //
		s26(大道无相.instance()), //
		s27(回风拂柳.instance()), //
		s28(吞云吐纳.instance()), //
		s29(化生.instance()), //
		s30(落英缤纷.instance()), //
			
		s31(化骨掌.instance()), //
		s32(融雪掌.instance()), //
		s33(月影步.instance()), //
		s34(凝血术.instance()), //
		s35(我意逍遥.instance()), //
		s36(生死咒.instance()), //
		s37(逍遥诀.instance()), //
		s38(扑朔迷离.instance()), //
		s39(虎鹰扬视.instance()), //
		s40(嗜血咒.instance()), //

	p0(普通攻击.instance()), //
	p1(外功攻击.instance()), //
	p2(内功攻击.instance()), //
	p3(外功防御.instance()), //
	p4(内功防御.instance()), //
	p5(命中.instance()), //
	p6(闪避.instance()), //
	p7(鬼步.instance()), //
	p8(格挡.instance()), //
	p9(必杀.instance()), //
	p10(反震.instance()), //
	p11(吸血.instance()), //
	p12(眩晕.instance()), //
	p13(虚弱.instance()), //
	p14(天相.instance()), //
	p15(净化.instance()), //
	p16(连击.instance()), //
	p17(奇袭.instance()), //
	p18(固化.instance()), //
	p19(狂爆.instance()), //
	p20(天机.instance()), //
	p21(免疫.instance()), //
	p22(连环.instance()), //
	p23(再生.instance()), //
	p24(神佑.instance()), //
	p25(追击.instance()), //
	;

	private SkillSpecies species;

	ESkillSpecies(SkillSpecies species) {
		this.species = species;
	}

	/**
	 * @return the species
	 */
	public SkillSpecies getSpecies() {
		return species;
	}

}
