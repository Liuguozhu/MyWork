package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.partner.res.GetPartnerRes;
import iyunu.NewTLOL.model.partner.res.PartnerExpRes;
import iyunu.NewTLOL.model.partner.res.PartnerHandbook;
import iyunu.NewTLOL.model.partner.res.PartnerRecruitRes;
import iyunu.NewTLOL.model.partner.res.PartnerRes;
import iyunu.NewTLOL.model.partner.res.PartnerWorthRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class PartnerConverter {

	/**
	 * 伙伴资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/伙伴.xlsx";

		String partner = "src/main/resources/json/" + serverRes + "/Partner.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, partner, PartnerRes.class, "资质");
		String partnerExp = "src/main/resources/json/" + serverRes + "/PartnerExp.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, partnerExp, PartnerExpRes.class, "经验");
		String partnerRecruit = "src/main/resources/json/" + serverRes + "/PartnerRecruit.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, partnerRecruit, PartnerRecruitRes.class, "招募数值");
		String getPartner = "src/main/resources/json/" + serverRes + "/PartnerGet.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, getPartner, GetPartnerRes.class, "招募");
		String partnerHandbook = "src/main/resources/json/" + serverRes + "/PartnerHandbook.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, partnerHandbook, PartnerHandbook.class, "伙伴图鉴");
		String partnerWorth = "src/main/resources/json/" + serverRes + "/PartnerWorth.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, partnerWorth, PartnerWorthRes.class, "伙伴评分");
	}
}