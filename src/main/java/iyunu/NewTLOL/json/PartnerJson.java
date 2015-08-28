package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.partner.res.GetPartnerRes;
import iyunu.NewTLOL.model.partner.res.PartnerExpRes;
import iyunu.NewTLOL.model.partner.res.PartnerRecruitRes;
import iyunu.NewTLOL.model.partner.res.PartnerRes;
import iyunu.NewTLOL.model.partner.res.PartnerSendBack;
import iyunu.NewTLOL.model.partner.res.PartnerWorthRes;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public final class PartnerJson {

	/**
	 * 私有构造方法
	 */
	private PartnerJson() {

	}

	private static PartnerJson instance = new PartnerJson();
	private static final String PARTNER = "json/" + ServerManager.SERVER_RES + "/Partner.json.txt";
	private static final String PARTNER_EXP = "json/" + ServerManager.SERVER_RES + "/PartnerExp.json.txt";
	private static final String PARTNER_RECRUIT = "json/" + ServerManager.SERVER_RES + "/PartnerRecruit.json.txt";
	private static final String GET_PARTNERS = "json/" + ServerManager.SERVER_RES + "/PartnerGet.json.txt";
	private static final String PARTNER_WORTH = "json/" + ServerManager.SERVER_RES + "/PartnerWorth.json.txt";

	private Map<Integer, Integer> partnerExpMap = new HashMap<Integer, Integer>(); // 等级，升级所需经验
	private Map<Integer, Integer> partnerUpExpMap = new HashMap<Integer, Integer>(); // 等级，升级所需经验
//	private ArrayListMultimap<Integer, PartnerRes> partnerResMap = ArrayListMultimap.create(); // 品质，伙伴集合
//	private Map<Integer, List<PartnerRes>> partnerResMap = new HashMap<Integer, List<PartnerRes>>(); // 品质，伙伴集合
	private Map<Integer, Partner> partners = new HashMap<Integer, Partner>(); // 伙伴索引，伙伴对象
	private Map<Integer, PartnerRes> partnerRess = new HashMap<Integer, PartnerRes>(); // 伙伴索引，伙伴对象
	private Map<Integer, PartnerRecruitRes> partnerRecruits = new HashMap<Integer, PartnerRecruitRes>(); // 酒馆刷新等级区概率的集合
	private ArrayListMultimap<Integer, GetPartnerRes> getPartners = ArrayListMultimap.create();// 招募伙伴的规则
//	private Map<Integer, List<GetPartnerRes>> getPartners = new HashMap<>();// 招募伙伴的规则
	private Map<Integer, PartnerSendBack> sendBack = new HashMap<>();// 解除伙伴返回
	private List<PartnerWorthRes> partnerWorths = new ArrayList<>(); // 伙伴评分

	/**
	 * 获取PartnerJson对象
	 * 
	 * @return PartnerJson对象
	 */
	public static PartnerJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		partnerExpMap.clear();
		partnerUpExpMap.clear();
//		partnerResMap.clear();
		partners.clear();
		partnerRecruits.clear();
		partnerRess.clear();
		getPartners.clear();
		sendBack.clear();
		partnerWorths.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<PartnerRes> partnerModelList = JsonImporter.fileImporter(PARTNER, new TypeReference<List<PartnerRes>>() {
		});

		for (PartnerRes partnerRes : partnerModelList) {
//			if (partnerRes.getIndex() != 2210) {
//				int quality = partnerRes.getQuality();

//				partnerResMap.put(quality, partnerRes);
//				if (partnerResMap.containsKey(quality)) {
//					List<PartnerRes> list = partnerResMap.get(quality);
//					list.add(partnerRes);
//				} else {
//					List<PartnerRes> list = new ArrayList<PartnerRes>();
//					list.add(partnerRes);
//					partnerResMap.put(quality, list);
//				}
//			}
			if (partnerRes.getSendBack() != null && !("".equals(partnerRes.getSendBack()) || "0".equals(partnerRes.getSendBack()))) {
				sendBack.put(partnerRes.getIndex(), stringToPartnerSendBack(partnerRes.getSendBack()));
			}

			partners.put(partnerRes.getIndex(), partnerRes.toPartner());
			partnerRess.put(partnerRes.getIndex(), partnerRes);
		}

		List<PartnerExpRes> partnerExpResList = JsonImporter.fileImporter(PARTNER_EXP, new TypeReference<List<PartnerExpRes>>() {
		});
		for (PartnerExpRes partnerExpRes : partnerExpResList) {
			partnerExpMap.put(partnerExpRes.getLevel(), partnerExpRes.getExp());
			partnerUpExpMap.put(partnerExpRes.getLevel(), partnerExpRes.getUpExp());
		}

		List<PartnerRecruitRes> PartnerRecruitResList = JsonImporter.fileImporter(PARTNER_RECRUIT, new TypeReference<List<PartnerRecruitRes>>() {
		});
		for (PartnerRecruitRes partnerRecruitRes : PartnerRecruitResList) {
			partnerRecruits.put(partnerRecruitRes.getId(), partnerRecruitRes);
		}

		// 最新的招募伙伴
		List<GetPartnerRes> GetPartnerResList = JsonImporter.fileImporter(GET_PARTNERS, new TypeReference<List<GetPartnerRes>>() {
		});

		for (GetPartnerRes getPartner : GetPartnerResList) {
			
			getPartners.put(getPartner.getId(), getPartner);
//			if (getPartners.containsKey(getPartner.getId())) {
//				List<GetPartnerRes> list = getPartners.get(getPartner.getId());
//				list.add(getPartner);
//			} else {
//				List<GetPartnerRes> list = new ArrayList<>();
//				list.add(getPartner);
//				getPartners.put(getPartner.getId(), list);
//			}
		}

		partnerWorths = JsonImporter.fileImporter(PARTNER_WORTH, new TypeReference<List<PartnerWorthRes>>() {
		});

		long end = System.currentTimeMillis();
		System.out.println("伙伴脚本加载耗时：" + (end - start));
	}

	public PartnerSendBack stringToPartnerSendBack(String string) {
		String[] strs = string.split(":");
		PartnerSendBack p = new PartnerSendBack();
		p.setId(Translate.stringToInt(strs[0]));
		p.setNum(Translate.stringToInt(strs[1]));
		p.setBind(Translate.stringToInt(strs[2]));
		return p;
	}

	/**
	 * 获取经验
	 * 
	 * @param level
	 *            等级
	 * @return 经验
	 */
	public int getExpMax(int level) {
		if (partnerExpMap.containsKey(level)) {
			return partnerExpMap.get(level);
		}
		return 0;
	}

	/**
	 * 获取被吃时返还经验
	 * 
	 * @param level
	 *            等级
	 * @return 经验
	 */
	public int getUpExpMax(int level) {
		if (partnerUpExpMap.containsKey(level)) {
			return partnerUpExpMap.get(level);
		}
		return 0;
	}

//	public List<PartnerRes> getPartnerResList(Integer quality) {
//		return partnerResMap.get(quality);
//	}

	public Map<Integer, Partner> getPartners() {
		return partners;
	}

	/**
	 * 获取伙伴对象
	 * 
	 * @param index
	 *            伙伴索引
	 * @return 伙伴对象
	 */
	public Partner getPartner(int index) {
		if (partners.containsKey(index)) {
			return partners.get(index).copy();
		}
		return null;
	}

	public Partner getNewPartner(int index) {
		if (partnerRess.containsKey(index)) {
			return partnerRess.get(index).newPartner();
		}
		return null;
	}

	public List<GetPartnerRes>getGetPartnersByType(int type){
		return getPartners.get(type);
	}
	/**
	 * @return the partnerRecruits
	 */
	public Map<Integer, PartnerRecruitRes> getPartnerRecruits() {
		return partnerRecruits;
	}

	/**
	 * @return the getPartners
	 */
//	public Map<Integer, List<GetPartnerRes>> getGetPartners() {
//		return getPartners;
//	}

	/**
	 * @return the sendBack
	 */
	public Map<Integer, PartnerSendBack> getSendBack() {
		return sendBack;
	}

	/**
	 * @param sendBack
	 *            the sendBack to set
	 */
	public void setSendBack(Map<Integer, PartnerSendBack> sendBack) {
		this.sendBack = sendBack;
	}

	/**
	 * @return the partnerWorths
	 */
	public List<PartnerWorthRes> getPartnerWorths() {
		return partnerWorths;
	}

}
