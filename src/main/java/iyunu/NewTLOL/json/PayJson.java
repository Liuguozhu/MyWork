package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.pay.PayInfo;
import iyunu.NewTLOL.model.pay.PayRatio;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class PayJson {

	/**
	 * 私有构造方法
	 */
	private PayJson() {

	}

	private static PayJson instance = new PayJson();
	private static final String PAY_INFO = "json/" + ServerManager.SERVER_RES + "/PayInfo.json.txt";
	private static final String PAY_RATIO = "json/" + ServerManager.SERVER_RES + "/PayRatio.json.txt";
	
	private Map<Integer, PayInfo> payInfoMap = new HashMap<Integer, PayInfo>();
	private List<PayInfo> payInfos = new ArrayList<PayInfo>();
	private List<PayRatio> payRatios = new ArrayList<PayRatio>();

	/**
	 * 获取MonsterJson对象
	 * 
	 * @return MonsterJson对象
	 */
	public static PayJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		payInfoMap.clear();
		payInfos.clear();
		payRatios.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<PayInfo> payInfoList = JsonImporter.fileImporter(PAY_INFO, new TypeReference<List<PayInfo>>() {
		});
		for (PayInfo payInfo : payInfoList) {
			payInfoMap.put(payInfo.getRMB(), payInfo);
		}
		payInfos.addAll(payInfoList);

		List<PayRatio> payRatioList = JsonImporter.fileImporter(PAY_RATIO, new TypeReference<List<PayRatio>>() {
		});
		payRatios.addAll(payRatioList);
		long end = System.currentTimeMillis();
		System.out.println("充值脚本加载耗时：" + (end - start));
	}

	public Map<Integer, PayInfo> getPayInfoMap() {
		return payInfoMap;
	}

	public List<PayInfo> getPayInfos() {
		return payInfos;
	}

	public List<PayRatio> getPayRatios() {
		return payRatios;
	}

	public void setPayRatios(List<PayRatio> payRatios) {
		this.payRatios = payRatios;
	}

}
